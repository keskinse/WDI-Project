package ir;

import java.io.File;
import java.util.Scanner;
import org.slf4j.Logger;
import blocking.MovieBlockingKeyByDirector;
import blocking.MovieBlockingKeyByProducer;
import blocking.MovieBlockingKeyByTitleAndYearGenerator;
import blocking.MovieBlockingKeyByTitleGenerator;
import blocking.MovieBlockingKeyByYearAndDirector;
import blocking.MovieBlockingKeyByYearGenerator;
import comparators.MovieDateComparator1Year;
import comparators.MovieDateComparator2Years;
import comparators.MovieDateComparator5Years;
import comparators.MovieDateComparator10Years;
import comparators.MovieDirectorMongeElkan;
import comparators.MovieProducerMongeElkan;
import comparators.MovieTitleMongeElkan;
import model.Movie;
import model.MovieXMLReader;
import de.uni_mannheim.informatik.dws.winter.matching.MatchingEngine;
import de.uni_mannheim.informatik.dws.winter.matching.MatchingEvaluator;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.AbstractBlocker;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.Blocker;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.NoBlocker;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.SortedNeighbourhoodBlocker;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.StandardRecordBlocker;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.generators.RecordBlockingKeyGenerator;
import de.uni_mannheim.informatik.dws.winter.matching.rules.LinearCombinationMatchingRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.HashedDataSet;
import de.uni_mannheim.informatik.dws.winter.model.MatchingGoldStandard;
import de.uni_mannheim.informatik.dws.winter.model.Performance;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.CSVCorrespondenceFormatter;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import de.uni_mannheim.informatik.dws.winter.utils.WinterLogManager;

public class LinearCombination {

	/*
	 * Logging Options: default: level INFO - console trace: level TRACE - console
	 * infoFile: level INFO - console/file traceFile: level TRACE - console/file
	 * 
	 * To set the log level to trace and write the log to winter.log and console,
	 * activate the "traceFile" logger as follows: private static final Logger
	 * logger = WinterLogManager.activateLogger("traceFile");
	 *
	 */

	private static final Logger logger = WinterLogManager.activateLogger("default");

	public static void main(String[] args) throws Exception {
		// loading data

		logger.info("*\tLoading datasets\t*");
		HashedDataSet<Movie, Attribute> data1 = new HashedDataSet<>();
		new MovieXMLReader().loadFromXML(new File("data/input/DBpedia_target.xml"), "/movies/movie", data1);
		logger.info("*\tLoading datasets\t*");
		HashedDataSet<Movie, Attribute> data2 = new HashedDataSet<>();
		new MovieXMLReader().loadFromXML(new File("data/input/HydraMovie_target.xml"), "/movies/movie", data2);
		logger.info("*\tLoading datasets\t*");
		HashedDataSet<Movie, Attribute> data3 = new HashedDataSet<>();
		new MovieXMLReader().loadFromXML(new File("data/input/TMDB_target.xml"), "/movies/movie", data3);
	      // loading gold standard
        /**
         * CHOOSE YOUR DATA FOR EVALUATION
         */
        Scanner scanner = new Scanner(System.in);
        String evalDataText = "";
        int[] arr = {1,2,3};
        MatchingGoldStandard goldStandard = new MatchingGoldStandard();
        System.out.println("*\tSELECT YOUR DATA\t*\n"
            + "*\t1 = DBPEDIA <-> TMDB\t*\n"
            + "*\t2 = DBPEDIA <-> HYDRAMOVIE\t*\n"
            + "*\t3 = TMDB <-> HYDRAMOVIE\t*");
        String dataType = scanner.nextLine();
        HashedDataSet<Movie, Attribute> mainDataOne = null;
        HashedDataSet<Movie, Attribute> mainDataTwo = null;
        switch(dataType) {
          case "1": mainDataOne = data1; mainDataTwo = data3; evalDataText = "DBPEDIA <-> TMDB"; goldStandard.loadFromCSVFile(new File("data/goldstandard/GS_DBpedia_TMDB.csv"));  break;
          case "2": mainDataOne = data1; mainDataTwo = data2; evalDataText = "DBPEDIA <-> HYDRAMOVIE"; goldStandard.loadFromCSVFile(new File("data/goldstandard/GS_DBpedia_HM.csv")); break;
          case "3": mainDataOne = data2; mainDataTwo = data3; evalDataText = "TMDB <-> HYDRAMOVIE"; goldStandard.loadFromCSVFile(new File("data/goldstandard/GS_TMDB_HM.csv")); break;
          default: System.exit(0); break;
        } 
        logger.info("*\tLoading gold standard\t*");
        // create matching rule
        LinearCombinationMatchingRule<Movie, Attribute> matchingRule = new LinearCombinationMatchingRule<>(0.7);
       matchingRule.activateDebugReport("data/output/debugResultsMatchingRule.csv", 10000, goldStandard);
       
       // add comparators
       /**
        * CHOOSE YOUR COMPARATORS
        */
       matchingRule.addComparator(new MovieTitleMongeElkan(), 0.7);
       // matchingRule.addComparator(new MovieDateComparator1Year(), 0.3);
      //  matchingRule.addComparator(new MovieDateComparator2Years(), 0.3);
     //  matchingRule.addComparator(new MovieDateComparator5Years(), 0.3);
       matchingRule.addComparator(new MovieDateComparator10Years(), 0.3);
     //   matchingRule.addComparator(new MovieDateComparator2Years(), 0.2);  
    //   matchingRule.addComparator(new MovieDateComparator(), 0.3);
      // matchingRule.addComparator(new MovieDirectorMongeElkan(), 0.2);
      // matchingRule.addComparator(new MovieProducerMongeElkan(), 0.2);
       
       // selection of blocker
       System.out.println("*\tSELECT YOUR BLOCKING GENERATOR KEY\t*\n"
           + "*\t1 = By title and year\t*\n"
           + "*\t2 = By year and director\t*\n"
           + "*\t3 = By year only\t*\n"
           + "*\t4 = By director only\t*\n"
           + "*\t5 = By producer only\t*\n"
           + "*\telse = By title only\t*");
       String keyType = scanner.nextLine();
       RecordBlockingKeyGenerator<Movie, Attribute> blockingKeyGenerator;
       switch(keyType) {
         case "1": blockingKeyGenerator = new MovieBlockingKeyByTitleAndYearGenerator(); break;
         case "2": blockingKeyGenerator = new MovieBlockingKeyByYearAndDirector(); break;
         case "3": blockingKeyGenerator = new MovieBlockingKeyByYearGenerator(); break;
         case "4": blockingKeyGenerator = new MovieBlockingKeyByDirector(); break;
         case "5": blockingKeyGenerator = new MovieBlockingKeyByProducer(); break;
         default: blockingKeyGenerator = new MovieBlockingKeyByTitleGenerator(); break;
       } 
       System.out.println("*\tSELECT YOUR BLOCKING TYPE\t*\n"
           + "*\t1 = Standard blocker\t*\n"
           + "*\t2 = Sorted neighbourhood blocker\t*\n"
           + "*\telse = No blocking\t*");
       String blockID = scanner.nextLine();
       AbstractBlocker<Movie, Movie, Attribute> blocker;
       switch(blockID) {
         case "1": blocker = new StandardRecordBlocker<Movie, Attribute>(blockingKeyGenerator); break;
         case "2": blocker = new SortedNeighbourhoodBlocker<>(blockingKeyGenerator, 20); break;
         default: blocker = new NoBlocker<>(); break;
       }
       scanner.close();
       blocker.setMeasureBlockSizes(true);
       //Write debug results to file:
       blocker.collectBlockSizeData("data/output/debugResultsBlocking.csv", 100);
       
       
       // Initialize Matching Engine
       MatchingEngine<Movie, Attribute> engine = new MatchingEngine<>();

       // Execute the matching
       /**
        * CHOOSE YOUR MOVIE DATA
        * data1 = DBpedia
        * data2 = HydraMovie
        * data3 = TMDB
        */
       logger.info("*\tRunning identity resolution\t*");
       Processable<Correspondence<Movie, Attribute>> correspondences = engine.runIdentityResolution(
               mainDataOne, mainDataTwo, null, matchingRule,
               (Blocker<Movie, Attribute, Movie, Attribute>) blocker);
       
       // write the correspondences to the output file
       new CSVCorrespondenceFormatter().writeCSV(new File("data/output/outputCorrespondences.csv"), correspondences);        
       
       logger.info("*\tEvaluating result\t*");
       // evaluate your result
       MatchingEvaluator<Movie, Attribute> evaluator = new MatchingEvaluator<Movie, Attribute>();
       Performance perfTest = evaluator.evaluateMatching(correspondences,
               goldStandard);

       // print the evaluation result
       logger.info(evalDataText);
       logger.info(String.format(
               "Precision: %.4f",perfTest.getPrecision()));
       logger.info(String.format(
               "Recall: %.4f", perfTest.getRecall()));
       logger.info(String.format(
               "F1: %.4f",perfTest.getF1()));
	}
}

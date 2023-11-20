package ir;

import java.io.File;
import java.util.Scanner;
import org.slf4j.Logger;
import blocking.MovieBlockingKeyByTitleAndYearGenerator;
import blocking.MovieBlockingKeyByTitleGenerator;
import blocking.MovieBlockingKeyByYearAndDirector;
import blocking.MovieBlockingKeyByYearGenerator;
import comparators.MovieDateComparator10Years;
import comparators.MovieDateComparator1Year;
import comparators.MovieDateComparator2Years;
import comparators.MovieDateComparator5Years;
import comparators.MovieDirectorMongeElkan;
import comparators.MovieProducerMongeElkan;
import comparators.MovieTitleMongeElkan;
import de.uni_mannheim.informatik.dws.winter.matching.MatchingEngine;
import de.uni_mannheim.informatik.dws.winter.matching.MatchingEvaluator;
import de.uni_mannheim.informatik.dws.winter.matching.algorithms.RuleLearner;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.SortedNeighbourhoodBlocker;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.StandardRecordBlocker;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.generators.RecordBlockingKeyGenerator;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.AbstractBlocker;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.Blocker;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.NoBlocker;
import de.uni_mannheim.informatik.dws.winter.matching.rules.WekaMatchingRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.HashedDataSet;
import de.uni_mannheim.informatik.dws.winter.model.MatchingGoldStandard;
import de.uni_mannheim.informatik.dws.winter.model.Performance;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.CSVCorrespondenceFormatter;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import de.uni_mannheim.informatik.dws.winter.utils.WinterLogManager;
import model.Movie;
import model.MovieXMLReader;

public class MachineLearning {

  /*
   * Logging Options: default: level INFO - console trace: level TRACE - console infoFile: level
   * INFO - console/file traceFile: level TRACE - console/file
   * 
   * To set the log level to trace and write the log to winter.log and console, activate the
   * "traceFile" logger as follows: private static final Logger logger =
   * WinterLogManager.activateLogger("traceFile");
   *
   */
  
  static String gsTrain = "data/goldstandard/training/GS_DBpedia_HM_train.csv";
  static String goldTest = "data/goldstandard/testing/GS_DBpedia_HM_test.csv";

  private static final Logger logger = WinterLogManager.activateLogger("default");

  public static void main(String[] args) throws Exception {
    logger.info("*\tLoading datasets\t*");
    HashedDataSet<Movie, Attribute> data1 = new HashedDataSet<>();
    new MovieXMLReader().loadFromXML(new File("data/input/DBpedia_target.xml"), "/movies/movie",
        data1);
    logger.info("*\tLoading datasets\t*");
    HashedDataSet<Movie, Attribute> data2 = new HashedDataSet<>();
    new MovieXMLReader().loadFromXML(new File("data/input/HydraMovie_target.xml"), "/movies/movie",
        data2);
    logger.info("*\tLoading datasets\t*");
    HashedDataSet<Movie, Attribute> data3 = new HashedDataSet<>();
    new MovieXMLReader().loadFromXML(new File("data/input/TMDB_target.xml"), "/movies/movie",
        data3);
    // loading gold standard
    logger.info("*\tLoading gold standard\t*");
    MatchingGoldStandard goldStandard = new MatchingGoldStandard();
    goldStandard.loadFromCSVFile(new File(gsTrain));

    // create a matching rule
    String options[] = new String[] {"-S"};
    String modelType = "SimpleLogistic"; // use a logistic regression
    WekaMatchingRule<Movie, Attribute> matchingRule =
        new WekaMatchingRule<>(0.7, modelType, options);
    matchingRule.activateDebugReport("data/output/debugResultsMatchingRule.csv", 1000, goldStandard);

    // add comparators
    matchingRule.addComparator(new MovieTitleMongeElkan());
    matchingRule.addComparator(new MovieDateComparator1Year());
    matchingRule.addComparator(new MovieDateComparator2Years());
    matchingRule.addComparator(new MovieDateComparator5Years());
    matchingRule.addComparator(new MovieDateComparator10Years());
    matchingRule.addComparator(new MovieDirectorMongeElkan());
    matchingRule.addComparator(new MovieProducerMongeElkan());


    // train the matching rule's model
    logger.info("*\tLearning matching rule\t*");
    RuleLearner<Movie, Attribute> learner = new RuleLearner<>();
    learner.learnMatchingRule(data1, data2, null, matchingRule, goldStandard);
    logger.info(String.format("Matching rule is:\n%s", matchingRule.getModelDescription()));

    // create a blocker (blocking strategy)
 // selection of blocker
    Scanner scanner = new Scanner(System.in);
    System.out.println("*\tSELECT YOUR BLOCKING GENERATOR KEY\t*\n"
        + "*\t1 = By title and year\t*\n"
        + "*\t2 = By year and director\t*\n"
        + "*\t3 = By year only\t*\n"
        + "*\telse = By title only\t*");
    String keyType = scanner.nextLine();
    RecordBlockingKeyGenerator<Movie, Attribute> blockingKeyGenerator;
    switch(keyType) {
      case "1": blockingKeyGenerator = new MovieBlockingKeyByTitleAndYearGenerator(); break;
      case "2": blockingKeyGenerator = new MovieBlockingKeyByYearAndDirector(); break;
      case "3": blockingKeyGenerator = new MovieBlockingKeyByYearGenerator(); break;
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
    logger.info("*\tRunning identity resolution\t*");
    Processable<Correspondence<Movie, Attribute>> correspondences =
        engine.runIdentityResolution(data1, data2, null, matchingRule, (Blocker<Movie, Attribute, Movie, Attribute>) blocker);

    // write the correspondences to the output file
    new CSVCorrespondenceFormatter().writeCSV(
        new File("data/output/outputCorrespondences_ML.csv"), correspondences);

    // load the gold standard (test set)
    logger.info("*\tLoading gold standard\t*");
    MatchingGoldStandard gsTest = new MatchingGoldStandard();
    gsTest.loadFromCSVFile(new File(goldTest));

    // evaluate your result
    logger.info("*\tEvaluating result\t*");
    MatchingEvaluator<Movie, Attribute> evaluator = new MatchingEvaluator<Movie, Attribute>();
    Performance perfTest = evaluator.evaluateMatching(correspondences, gsTest);

    // print the evaluation result
    logger.info("Academy Awards <-> Actors");
    logger.info(String.format("Precision: %.4f", perfTest.getPrecision()));
    logger.info(String.format("Recall: %.4f", perfTest.getRecall()));
    logger.info(String.format("F1: %.4f", perfTest.getF1()));
  }
}

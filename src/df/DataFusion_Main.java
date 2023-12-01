package df;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Locale;

import fusers.*;
import model.Movie;
import model.MovieXMLFormatter;
import model.MovieXMLReader;

import evaluation.TitleEvaluationRule;
import evaluation.ActorsEvaluationRule;
import evaluation.DirectorEvaluationRule;
import evaluation.ProducerEvaluationRule;
import evaluation.YearEvaluationRule;
import evaluation.RatingEvaluationRule;

import de.uni_mannheim.informatik.dws.winter.datafusion.CorrespondenceSet;
import de.uni_mannheim.informatik.dws.winter.datafusion.DataFusionEngine;
import de.uni_mannheim.informatik.dws.winter.datafusion.DataFusionEvaluator;
import de.uni_mannheim.informatik.dws.winter.datafusion.DataFusionStrategy;
import de.uni_mannheim.informatik.dws.winter.model.DataSet;
import de.uni_mannheim.informatik.dws.winter.model.FusibleDataSet;
import de.uni_mannheim.informatik.dws.winter.model.FusibleHashedDataSet;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroupFactory;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.utils.WinterLogManager;
import org.slf4j.Logger;


public class DataFusion_Main 
{
	/*
	 * Logging Options:
	 * 		default: 	level INFO	- console
	 * 		trace:		level TRACE     - console
	 * 		infoFile:	level INFO	- console/file
	 * 		traceFile:	level TRACE	- console/file
	 *  
	 * To set the log level to trace and write the log to winter.log and console, 
	 * activate the "traceFile" logger as follows:
	 *     private static final Logger logger = WinterLogManager.activateLogger("traceFile");
	 *
	 */

	private static final Logger logger = WinterLogManager.activateLogger("trace");
	
	public static void main( String[] args ) throws Exception
    {
		// Load the Data into FusibleDataSet
		logger.info("*\tLoading datasets\t*");
//		FusibleDataSet<Movie, Attribute> ds1 = new FusibleHashedDataSet<>();
//		new MovieXMLReader().loadFromXML(new File("data/input/DBpedia_target.xml"), "/movies/movie", ds1);
//		ds1.printDataSetDensityReport();

		FusibleDataSet<Movie, Attribute> ds1 = new FusibleHashedDataSet<>();
		new MovieXMLReader().loadFromXML(new File("data/input/HydraMovie_target.xml"), "/movies/movie", ds1);
		ds1.printDataSetDensityReport();

		FusibleDataSet<Movie, Attribute> ds2 = new FusibleHashedDataSet<>();
		new MovieXMLReader().loadFromXML(new File("data/input/TMDB_target.xml"), "/movies/movie", ds2);
		ds2.printDataSetDensityReport();

		// Maintain Provenance
		// Scores (e.g. from rating)
		ds1.setScore(1.0);
		ds2.setScore(2.0);
//		ds3.setScore(3.0);

		/*// Year (e.g. last update)
		DateTimeFormatter formatter = new DateTimeFormatterBuilder()
		        .appendPattern("yyyy-MM-dd")
		        .parseDefaulting(ChronoField.CLOCK_HOUR_OF_DAY, 0)
		        .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
		        .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
		        .toFormatter(Locale.ENGLISH);

		ds1.setDate(LocalDateTime.parse("2012-01-01", formatter));
		ds2.setDate(LocalDateTime.parse("2010-01-01", formatter));
		ds3.setDate(LocalDateTime.parse("2008-01-01", formatter));
		*/

		// load correspondences
		logger.info("*\tLoading correspondences\t*");
		CorrespondenceSet<Movie, Attribute> correspondences = new CorrespondenceSet<>();

//		correspondences.loadCorrespondences(new File("data/correspondences/outputCorrespondences_DBPEDIA_TMDB.csv"),ds1, ds3);
//		correspondences.loadCorrespondences(new File("data/correspondences/outputCorrespondences_DBPEDIA_Hydra.csv"),ds1, ds2);
		correspondences.loadCorrespondences(new File("data/correspondences/outputCorrespondences_Hydra_TMDB.csv"),ds1, ds2);
		// write group size distribution
		correspondences.printGroupSizeDistribution();


		// load the gold standard
		logger.info("*\tEvaluating results\t*");
		DataSet<Movie, Attribute> gs = new FusibleHashedDataSet<>();
		new MovieXMLReader().loadFromXML(new File("data/goldstandard/gold_df.xml"), "/movies/movie", gs);

		for(Movie m : gs.get()) {
			logger.info(String.format("gs: %s", m.getIdentifier()));
		}

		// define the fusion strategy
		DataFusionStrategy<Movie, Attribute> strategy = new DataFusionStrategy<>(new MovieXMLReader());
		// write debug results to file
		//strategy.activateDebugReport("data/output/debugResultsDatafusion.csv", -1, gs);
		
		// add attribute fusers
		// TITLE
		 strategy.addAttributeFuser(Movie.TITLE, new TitleFuserShortestString(),new TitleEvaluationRule());
		// strategy.addAttributeFuser(Movie.TITLE, new TitleFuserLongestString(),new TitleEvaluationRule());
		// DIRECTORS
		 strategy.addAttributeFuser(Movie.DIRECTORS,new DirectorsFuserUnion(),new DirectorEvaluationRule());
		// strategy.addAttributeFuser(Movie.DIRECTORS,new DirectorsFuserIntersection(),new DirectorEvaluationRule());
		// PRODUCERS
		 strategy.addAttributeFuser(Movie.PRODUCERS,new ProducersFuserUnion(),new ProducerEvaluationRule());
		// strategy.addAttributeFuser(Movie.PRODUCERS,new ProducersFuserIntersection(),new ProducerEvaluationRule());
		// YEAR
		// strategy.addAttributeFuser(Movie.YEAR, new YearFuserFavourSource(),new YearEvaluationRule());
		 strategy.addAttributeFuser(Movie.YEAR, new YearFuserVoting(),new YearEvaluationRule());
		// strategy.addAttributeFuser(Movie.YEAR, new YearFuserClusteredVote(),new YearEvaluationRule());

		// ACTORS
		 strategy.addAttributeFuser(Movie.ACTORS,new ActorsFuserUnion(),new ActorsEvaluationRule());
		// strategy.addAttributeFuser(Movie.ACTORS,new ActorsFuserIntersection(),new ActorsEvaluationRule());
		 //RATING
		// strategy.addAttributeFuser(Movie.RATING,new RatingFuserFavourSource(),new RatingEvaluationRule());
		 strategy.addAttributeFuser(Movie.RATING,new RatingFuserMedian(),new RatingEvaluationRule());
		// strategy.addAttributeFuser(Movie.RATING,new RatingFuserAverage(),new RatingEvaluationRule());
		
		// create the fusion engine
		DataFusionEngine<Movie, Attribute> engine = new DataFusionEngine<>(strategy);

		// print consistency report
		engine.printClusterConsistencyReport(correspondences, null);
		
		// print record groups sorted by consistency
		engine.writeRecordGroupsByConsistency(new File("data/output/recordGroupConsistencies.csv"), correspondences, null);

		// run the fusion
		logger.info("*\tRunning data fusion\t*");
		FusibleDataSet<Movie, Attribute> fusedDataSet = engine.run(correspondences, null);

		// write the result
		new MovieXMLFormatter().writeXML(new File("data/output/fused.xml"), fusedDataSet);

		// evaluate
		DataFusionEvaluator<Movie, Attribute> evaluator = new DataFusionEvaluator<>(strategy, new RecordGroupFactory<Movie, Attribute>());
		
		double accuracy = evaluator.evaluate(fusedDataSet, gs, null);

		logger.info(String.format("*\tAccuracy: %.2f", accuracy));
    }
}

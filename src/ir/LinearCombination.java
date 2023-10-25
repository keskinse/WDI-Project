package ir;

import java.io.File;

import org.slf4j.Logger;

import model.DBpedia;
import model.DBpediaXMLReader;
import model.Movie;
import model.MovieXMLReader;
import de.uni_mannheim.informatik.dws.winter.model.HashedDataSet;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
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
		HashedDataSet<DBpedia, Attribute> data1 = new HashedDataSet<>();
		new DBpediaXMLReader().loadFromXML(new File("data/input/DBpedia_target.xml"), "/movies/movie", data1);
		logger.info("*\tLoading datasets\t*");
		HashedDataSet<DBpedia, Attribute> data2 = new HashedDataSet<>();
		new DBpediaXMLReader().loadFromXML(new File("data/input/HydraMovie_target.xml"), "/movies/movie", data2);
		logger.info("*\tLoading datasets\t*");
		HashedDataSet<DBpedia, Attribute> data3 = new HashedDataSet<>();
		new DBpediaXMLReader().loadFromXML(new File("data/input/TMDB_target.xml"), "/movies/movie", data3);
	}
}

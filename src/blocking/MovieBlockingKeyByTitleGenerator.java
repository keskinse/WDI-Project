package blocking;

import de.uni_mannheim.informatik.dws.winter.matching.blockers.generators.RecordBlockingKeyGenerator;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.Pair;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.processing.DataIterator;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import model.Movie;

public class MovieBlockingKeyByTitleGenerator extends
RecordBlockingKeyGenerator<Movie, Attribute> {
  
  private static final long serialVersionUID = 1L;

  @Override
  public void generateBlockingKeys(Movie record, Processable<Correspondence<Attribute, Matchable>> correspondences,
          DataIterator<Pair<String, Movie>> resultCollector) {
    String blockingKeyValue = ""; 
    try {
      String[] tokens  = record.getTitle().split(" ");
      blockingKeyValue = "";

      for(int i = 0; i <= 2 && i < tokens.length; i++) {
          blockingKeyValue += tokens[i].substring(0, Math.min(2,tokens[i].length())).toUpperCase();
      }
      } catch(Exception e) {
        blockingKeyValue = "";

      }

      resultCollector.next(new Pair<>(blockingKeyValue, record));
  }

}

package blocking;

import de.uni_mannheim.informatik.dws.winter.matching.blockers.generators.RecordBlockingKeyGenerator;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.Pair;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.processing.DataIterator;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import model.Movie;

public class MovieBlockingKeyByYearAndDirector
    extends RecordBlockingKeyGenerator<Movie, Attribute> {

  private static final long serialVersionUID = 1L;

  @Override
  public void generateBlockingKeys(Movie record,
      Processable<Correspondence<Attribute, Matchable>> correspondences,
      DataIterator<Pair<String, Movie>> resultCollector) {
    String blockingKey = "";
    try {
      int size = record.getDirectors().size();
      String director = null;
      if (size == 1) {
        director = record.getDirectors().get(0).getDirector();
         blockingKey = director.substring(0, Math.min(3, director.length()))
             + Integer.toString(record.getYear()).substring(2,3);
      } else {
        for (int i = 0; i < 2; i++) {
          director = record.getDirectors().get(i).getDirector();
          blockingKey += director.substring(0, Math.min(2, director.length()));
        }
        blockingKey += Integer.toString(record.getYear()).substring(2,3);
      }
    } catch (Exception e) {
      blockingKey = "";
    }
    resultCollector.next(new Pair<>(blockingKey.toUpperCase(), record));
  }

}

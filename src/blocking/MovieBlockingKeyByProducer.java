package blocking;

import de.uni_mannheim.informatik.dws.winter.matching.blockers.generators.RecordBlockingKeyGenerator;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.Pair;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.processing.DataIterator;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import model.Movie;
import model.Producer;

public class MovieBlockingKeyByProducer extends
RecordBlockingKeyGenerator<Movie, Attribute> {
  
  private static final long serialVersionUID = 1L;

  @Override
  public void generateBlockingKeys(Movie record, Processable<Correspondence<Attribute, Matchable>> correspondences,
          DataIterator<Pair<String, Movie>> resultCollector) {
    String blockingKeyValue = ""; 
    try {
      if(record.getProducers().size() >= 3) {
      for (int i = 0; i <= 2; i++) {
        Producer p = record.getProducers().get(i);        
        blockingKeyValue +=  p.getProducer().substring(0,Math.min(2, p.getProducer().length()));
      }
      } else {
        Producer p = record.getProducers().get(0);
        blockingKeyValue = p.getProducer().substring(0,Math.min(5, p.getProducer().length()));
      }
      
      } catch(Exception e) {
        blockingKeyValue = "";
      }

      resultCollector.next(new Pair<>(blockingKeyValue, record));
  }
}
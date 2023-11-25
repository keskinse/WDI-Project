package comparators;

import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.Comparator;
import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.ComparatorLogger;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.string.TokenizingJaccardSimilarity;
import model.Movie;
import model.Producer;

public class MovieProducerJaccard implements Comparator<Movie, Attribute> {

  private static final long serialVersionUID = 1L;
  private TokenizingJaccardSimilarity sim = new TokenizingJaccardSimilarity();
  
  private ComparatorLogger comparisonLog;

  @Override
  public double compare(
          Movie record1,
          Movie record2,
          Correspondence<Attribute, Matchable> schemaCorrespondences) {
      
      double similarity = 0.0;
      try {
        int idx_one = 0, idx_two = 0;
        double max = Double.MIN_VALUE;
        for(int i = 0; i < record1.getProducers().size(); i++) {
          for(int j = 0; j < record2.getProducers().size(); j++) {
            similarity = sim.calculate(record1.getProducers().get(i).getProducer(), record2.getProducers().get(j).getProducer());
            if(similarity > max) {
              max = similarity;
              idx_one = i;
              idx_two = j;
            }
          }
        }
        Producer p1 = record1.getProducers().get(idx_one);
        Producer p2 = record2.getProducers().get(idx_two);
        
        if(this.comparisonLog != null){
            this.comparisonLog.setComparatorName(getClass().getName());
        
            this.comparisonLog.setRecord1Value(p1.getProducer());
            this.comparisonLog.setRecord2Value(p2.getProducer());
        
            this.comparisonLog.setSimilarity(Double.toString(similarity));
        }
      } catch(Exception e) {
        similarity = 0.0;
      }
      
      return similarity;
  }

  @Override
  public ComparatorLogger getComparisonLog() {
      return this.comparisonLog;
  }

  @Override
  public void setComparisonLog(ComparatorLogger comparatorLog) {
      this.comparisonLog = comparatorLog;
  }

}

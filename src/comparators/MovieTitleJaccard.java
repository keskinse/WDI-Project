package comparators;

import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.Comparator;
import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.ComparatorLogger;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.string.TokenizingJaccardSimilarity;
import model.Movie;

public class MovieTitleJaccard implements Comparator<Movie, Attribute> {

  private static final long serialVersionUID = 1L;
  private TokenizingJaccardSimilarity sim = new TokenizingJaccardSimilarity();
  
  private ComparatorLogger comparisonLog;

  @Override
  public double compare(
          Movie record1,
          Movie record2,
          Correspondence<Attribute, Matchable> schemaCorrespondences) {
      
      String s1 = record1.getTitle();
      String s2 = record2.getTitle();
      
      double similarity = sim.calculate(s1, s2);
      
      if(this.comparisonLog != null){
          this.comparisonLog.setComparatorName(getClass().getName());
      
          this.comparisonLog.setRecord1Value(s1);
          this.comparisonLog.setRecord2Value(s2);
      
          this.comparisonLog.setSimilarity(Double.toString(similarity));
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

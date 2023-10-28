/*
 * Copyright (c) 2017 Data and Web Science Group, University of Mannheim, Germany (http://dws.informatik.uni-mannheim.de/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package comparators;

import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.Comparator;
import de.uni_mannheim.informatik.dws.winter.matching.rules.comparators.ComparatorLogger;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.date.YearSimilarity;
import de.uni_mannheim.informatik.dws.winter.similarity.numeric.AbsoluteDifferenceSimilarity;
import model.Movie;

public class MovieDateComparator1Year implements Comparator<Movie, Attribute> {

    private static final long serialVersionUID = 1L;
    private AbsoluteDifferenceSimilarity sim = new AbsoluteDifferenceSimilarity(1.0);
    
    private ComparatorLogger comparisonLog;

    @Override
    public double compare(
            Movie record1,
            Movie record2,
            Correspondence<Attribute, Matchable> schemaCorrespondences) {
        
        try {
        double similarity = sim.calculate((double)record1.getYear(), (double)record2.getYear());
        
        if(this.comparisonLog != null){
            this.comparisonLog.setComparatorName(getClass().getName());
        
            this.comparisonLog.setRecord1Value(Integer.toString(record1.getYear()));
            this.comparisonLog.setRecord2Value(Integer.toString(record2.getYear()));
        
            this.comparisonLog.setSimilarity(Double.toString(similarity));
        }
        return similarity;
        } catch (Exception e) {
          return 0.0;
        }

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

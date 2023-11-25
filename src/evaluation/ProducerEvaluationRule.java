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
package evaluation;

import java.util.HashSet;
import java.util.Set;
import de.uni_mannheim.informatik.dws.winter.datafusion.EvaluationRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import model.Movie;
import model.Producer;

public class ProducerEvaluationRule extends EvaluationRule<Movie, Attribute> {

    @Override
    public boolean isEqual(Movie record1, Movie record2, Attribute schemaElement) {
      Set<String> producers1 = new HashSet<>();

      for (Producer p : record1.getProducers()) {
          producers1.add(p.getProducer());
      }

      Set<String> producers2 = new HashSet<>();
      for (Producer p : record2.getProducers()) {
          producers2.add(p.getProducer());
      }

      return producers1.containsAll(producers2) && producers2.containsAll(producers1);
    }

    /* (non-Javadoc)
     * @see de.uni_mannheim.informatik.wdi.datafusion.EvaluationRule#isEqual(java.lang.Object, java.lang.Object, de.uni_mannheim.informatik.wdi.model.Correspondence)
     */
    @Override
    public boolean isEqual(Movie record1, Movie record2,
            Correspondence<Attribute, Matchable> schemaCorrespondence) {
        return isEqual(record1, record2, (Attribute)null);
    }
    
}

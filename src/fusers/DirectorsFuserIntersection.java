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
package fusers;

import java.util.List;
import de.uni_mannheim.informatik.dws.winter.datafusion.AttributeValueFuser;
import de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.list.Intersection;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.FusedValue;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroup;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import model.Director;
import model.Movie;

/**
 * {@link AttributeValueFuser} for the directors of {@link Movie}s.
 *
 * @author Robert Meusel (robert@dwslab.de)
 * @author Oliver Lehmberg (oli@dwslab.de)
 *
 */
public class DirectorsFuserIntersection extends
        AttributeValueFuser<List<Director>, Movie, Attribute> {

    public DirectorsFuserIntersection() {
        super(new Intersection<Director, Movie, Attribute>());
    }

    @Override
    public boolean hasValue(Movie record, Correspondence<Attribute, Matchable> correspondence) {
        return record.hasValue(Movie.DIRECTORS);
    }

    @Override
    public List<Director> getValue(Movie record, Correspondence<Attribute, Matchable> correspondence) {
        return record.getDirectors();
    }

    @Override
    public void fuse(RecordGroup<Movie, Attribute> group, Movie fusedRecord, Processable<Correspondence<Attribute, Matchable>> schemaCorrespondences, Attribute schemaElement) {
        FusedValue<List<Director>, Movie, Attribute> fused = getFusedValue(group, schemaCorrespondences, schemaElement);
        fusedRecord.setDirectors(fused.getValue());
        fusedRecord
                .setAttributeProvenance(Movie.DIRECTORS, fused.getOriginalIds());
    }

}

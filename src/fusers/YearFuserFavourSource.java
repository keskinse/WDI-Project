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

import de.uni_mannheim.informatik.dws.winter.datafusion.AttributeValueFuser;
import de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.meta.FavourSources;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.FusedValue;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroup;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import model.Movie;

/**
 * {@link AttributeValueFuser} for the year of {@link Movie}s.
 *
 * @author Robert Meusel (robert@dwslab.de)
 * @author Oliver Lehmberg (oli@dwslab.de)
 *
 */
public class YearFuserFavourSource extends AttributeValueFuser<Integer, Movie, Attribute> {

    public YearFuserFavourSource() {
        super(new FavourSources<Integer, Movie, Attribute>());
    }

    @Override
    public boolean hasValue(Movie record, Correspondence<Attribute, Matchable> correspondence) {
        return record.hasValue(Movie.YEAR);
    }

    @Override
    public Integer getValue(Movie record, Correspondence<Attribute, Matchable> correspondence) {
        return record.getYear();
    }

    @Override
    public void fuse(RecordGroup<Movie, Attribute> group, Movie fusedRecord, Processable<Correspondence<Attribute, Matchable>> schemaCorrespondences, Attribute schemaElement) {
        FusedValue<Integer, Movie, Attribute> fused = getFusedValue(group, schemaCorrespondences, schemaElement);
        try {
        fusedRecord.setYear(fused.getValue());
        } catch (Exception e) {
          fusedRecord.setYear(0);
        }
        fusedRecord.setAttributeProvenance(Movie.YEAR, fused.getOriginalIds());
    }

}

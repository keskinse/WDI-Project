package model.modelDF;

import java.io.Serializable;

import de.uni_mannheim.informatik.dws.winter.model.AbstractRecord;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;

public class Director extends AbstractRecord<Attribute> implements Serializable {

    private static final long serialVersionUID = 1L;
    private String director;

    public Director(String director) {
        this.director = director;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int hashCode() {
        int result = 31 + ((director == null) ? 0 : director.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Director other = (Director) obj;
        if (director == null) {
            if (other.director != null)
                return false;
        } else if (!director.equals(other.director))
            return false;
        return true;
    }

    public static final Attribute DIRECTOR = new Attribute("Director");

    public boolean hasValue(Attribute attribute) {
        if (attribute == DIRECTOR)
            return director != null;
        return false;
    }
}

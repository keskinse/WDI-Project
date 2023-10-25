package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import de.uni_mannheim.informatik.dws.winter.model.AbstractRecord;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;

public class Actor extends AbstractRecord<Attribute> implements Serializable {

private static final long serialVersionUID = 1L;
private String actor;

public Actor(String actor) {
this.actor = actor;
}

public String getActor() {
return actor;
}

public void setActor(String actor) {
this.actor = actor;
}

public int hashCode() {
int result = 31 + ((actor == null) ? 0 : actor.hashCode());
return result;
} 

public boolean equals(Object obj) {
if (this == obj)
return true;
if (obj == null)
return false;
if (getClass() != obj.getClass())
return false;
Actor other = (Actor) obj;
if (actor == null) {
if (other.actor != null)
return false;
} else if (!actor.equals(other.actor))
return false;
return true;
}

public static final Attribute ACTOR = new Attribute("Actor");

public boolean hasValue(Attribute attribute) {
if(attribute==ACTOR)
return actor!=null;
return false;
}
}


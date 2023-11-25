package model;

import java.io.Serializable;

import de.uni_mannheim.informatik.dws.winter.model.AbstractRecord;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;

public class Producer extends AbstractRecord<Attribute> implements Serializable {

    private static final long serialVersionUID = 1L;
    private String producer;

    public Producer(String producer) {
        this.producer = producer;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public int hashCode() {
        int result = 31 + ((producer == null) ? 0 : producer.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Producer other = (Producer) obj;
        if (producer == null) {
            if (other.producer != null)
                return false;
        } else if (!producer.equals(other.producer))
            return false;
        return true;
    }

    public static final Attribute PRODUCER = new Attribute("producer");

    public boolean hasValue(Attribute attribute) {
        if (attribute == PRODUCER)
            return producer != null;
        return false;
    }
}

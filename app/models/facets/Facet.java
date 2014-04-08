package models.facets;

/**
 * Created by Stephen Yingling on 4/7/14.
 */
public class Facet {

    public Facet(){

    }

    protected String value;
    protected String name;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

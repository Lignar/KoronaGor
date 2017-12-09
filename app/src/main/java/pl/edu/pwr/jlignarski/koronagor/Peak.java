package pl.edu.pwr.jlignarski.koronagor;

/**
 * @author janusz on 08.12.17.
 */

class Peak {

    private String name;
    private int height;
    private String range;

    public Peak(String name, int height, String range) {
        this.name = name;
        this.height = height;
        this.range = range;
    }

    public String getName() {
        return name;
    }

    public int getHeight() {
        return height;
    }

    public String getRange() {
        return range;
    }

    public String getId() {
        return name;
    }
}

package org.example;

import java.util.List;

/**
 * Pretty much just used for making GeoJSON Files readable for {@link Main} that I got from <a href="https://polygons.openstreetmap.fr"/>This OpenStreetMap link</a>
 */
public class GeoJSON {
    private String type;
    private List<List<Double>> coordinates;

    /**
     * @deprecated Unused Method
     * Pretty much just returns the type of shape the GeoJSON has, majority of the time it's MultiPolygon.
     * @return Returns the type of polygon used, this method is not used at all
     */
    public String getType() {
        return type;
    }

    /**
     * @deprecated This method is never used
     * @param type The type of polygon used
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Pretty much gives out a list of coordinates
     * @return the coordinates of points
     */
    public List<List<Double>> getCoordinates() {
        return coordinates;
    }
    public void setCoordinates(List<List<Double>> coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Pretty much converts the GeoJSON into a string the best it can.
     */
    @Override
    public String toString() {
        return "GeoJSON{" +
                "type='" + type + "'\'" +
                ", coordinates=" + coordinates +
                '}';

    }
}

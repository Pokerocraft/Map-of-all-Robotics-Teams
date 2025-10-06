package org.example;

import java.util.List;

/**
 * Pretty much a class used just for Event JSONs or EJSONs, technically yes that is a file type that doesn't exist, however, I would likely have to change my JSON class if I used the JSON format for Events
 */
public class EJSON {
    private String reasonForImportance;
    private List<Double> coordinates;

    public String getReasonForImportance() {return reasonForImportance;}
    public void setReasonForImportance(String reasonForImportance) {this.reasonForImportance = reasonForImportance;}
    public List<Double> getCoordinates() {return coordinates;}
    public void setCoordinates(List<Double> coordinates) {this.coordinates = coordinates;}

    @Override
    public String toString() {
        return "EJSON{" +
                "reasonForImportance="+reasonForImportance+"'\'" +
                ", coordinates="+coordinates +
                "'}'";
    }
}

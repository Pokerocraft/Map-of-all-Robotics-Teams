package org.example;

import java.util.List;

/**
 * Effectively a JSON file structure class for my FRCData
 */
public class JSON {
    private int teamNumber;
    private String teamName;
    private List<Double> coordinates;

    /**
     * Gets the team name from the JSON file
     * @return Returns the team name
     */
    public String getTeamName(){return teamName;}

    /**
     * Sets the team name, though this shouldn't be used at all
     * @param teamName The team name
     */
    public void  setTeamName(String teamName){this.teamName=teamName;}

    /**
     * Gets the team number
     * @return Returns the team number
     */
    public int getTeamNumber(){return teamNumber;}

    /**
     * Sets the team number, though this shouldn't be used at all
     * @param teamNumber The team number
     */
    public void  setTeamNumber(int teamNumber){this.teamNumber=teamNumber;}

    /**
     * Gets the location of the team
     * @return Coordinates, latitude and longitude
     */
    public List<Double> getCoordinates(){return coordinates;}

    /**
     * Sets the location of the team, may not be accurate to where their team actually works
     * @param coordinates Coordinates, latitude and longitude
     */
    public void setCoordinates(List<Double> coordinates){this.coordinates=coordinates;}


    @Override
    public String toString()
    {
        return "JSON{" +
                "team-number=" + teamNumber + "'\'" +
                ", team-name="  + teamName + "'\'" +
                ", coordinates=" + coordinates +
                '}';
    }
}

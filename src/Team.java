//--------------------------------------------------------------------
// Assignment 2
// Part: 2 3
// Written by: Asif Khan 40211000 , Ayesha Mahmood 40189093
//--------------------------------------------------------------------

import java.io.Serializable;
/**
 * team with various attributes.
 * to store information about a team.
 */
public class Team implements Serializable{
	private String name;
    private String sport;
    private int year;
    private String score;
    private boolean championship;

    // Constructor
    public Team(String name, String sport, int year, String score, boolean championship) {
        this.name = name;
        this.sport = sport;
        this.year = year;
        this.score = score;
        this.championship = championship;
    }
 // Getters and setters for instance variables
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSport() {
        return sport;
    }
    public void setSport(String sport) {
        this.sport = sport;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public String getScore() {
        return score;
    }
    public void setScore(String score) {
        this.score = score;
    }
    public boolean isChampionship() {
        return championship;
    }
    public void setChampionship(boolean championship) {
        this.championship = championship;
    }
    @Override
    public String toString() {
        return name + "," + sport + "," + year + "," + score + "," + (championship ? "Y" : "N");
    }

}

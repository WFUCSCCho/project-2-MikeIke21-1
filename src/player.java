/*
@file: player.java
@description: This class creates my player, the data I am sending into my BST. The method contains the
variables needed for the class, setters, getters a compare to and to string method.
@author: Michael Iaccarino
@date: September 26, 2024
 */



public class player implements Comparable<player> {
    //declare variables (not all E)
    int overallPick;
    int year;
    String team;
    String name;
    String college;
    int yearsPlayed;
    int games;
    double winShares;
    double winShares40;
    double minutes;
    double points;
    double rebounds;
    double assist;

    // default constructor
    player(){
        this.overallPick = 0;
        this.year = 0;
        this.team = null;
        this.name = null;
        this.college = null;
        this.yearsPlayed = 0;
        this.games = 0;
        this.winShares = 0.0;
        this.winShares40 = 0.0;
        this.minutes = 0.0;
        this.points = 0.0;
        this.rebounds = 0.0;
        this.assist = 0.0;

    }
    //parameter constructor
    player(int overallPick, int year, String team, String name, String college, int yearsPlayed, int games, double winShares, double winShares40, double minutes, double points, double rebounds, double assist){
        this.overallPick = overallPick;
        this.year = year;
        this.team = team;
        this.name = name;
        this.college = college;
        this.yearsPlayed = yearsPlayed;
        this.games = games;
        this.winShares = winShares;
        this.winShares40 = winShares40;
        this.minutes = minutes;
        this.points = points;
        this.rebounds = rebounds;
        this.assist = assist;

    }

    //setters
    public void setOverallPick(int overallPick) {
        this.overallPick = overallPick;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public void setTeam(String team) {
        this.team = team;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setCollege(String college) {
        this.college = college;
    }
    public void setYearsPlayed(int yearsPlayed) {
        this.yearsPlayed = yearsPlayed;
    }
    public void setGames(int games) {
        this.games = games;
    }
    public void setWinShares(double winShares) {
        this.winShares = winShares;
    }
    public void setWinShares40(double winShares40) {
        this.winShares40 = winShares40;
    }
    public void setMinutes(double minutes) {
        this.minutes = minutes;
    }
    public void setPoints(double points) {
        this.points = points;
    }
    public void setRebounds(double rebounds) {
        this.rebounds = rebounds;
    }
    public void setAssist(double assist) {
        this.assist = assist;
    }

    //getters
    public int getOverallPick() {
        return overallPick;
    }
    public int getYear() {
        return year;
    }
    public String getTeam() {
        return team;
    }
    public String getName() {
        return name;
    }
    public String getCollege() {
        return college;
    }
    public int getYearsPlayed() {
        return yearsPlayed;
    }
    public int getGames() {
        return games;
    }
    public double getWinShares() {
        return winShares;
    }
    public double getWinShares40() {
        return winShares40;
    }
    public double getMinutes() {
        return minutes;
    }
    public double getPoints() {
        return points;
    }
    public double getRebounds() {
        return rebounds;
    }
    public double getAssist() {
        return assist;
    }


    //to string method
    //This is what I output to the file, to keep it short I just am doing the name and the stat I am comparing them by
    public String toString() {
        //output their name and the winshare stat(my comparable)
        return name + "|" + winShares40 + "|";

    }

    @Override
    //this should compare the players based off their winShares40 stat, which I believe is the best tracker of skill
    public int compareTo(player o) {
        if(this.getWinShares40() > o.getWinShares40()){
            return 1;
        }
        else if(this.getWinShares40() < o.getWinShares40()){
            return -1;
        }
        return 0;
    }

    //this class takes in a whole object and returns true or false
    public boolean equals(player obj) {
        if (this == obj){
            return true;
        }
        else{
            return false;
        }
    }


}
import java.util.Hashtable;
import java.util.Vector;
import java.util.LinkedList;
import java.util.Scanner;
import java.io.*;
import java.util.Enumeration;

/**
 * Represents an object of type Movie.
 * A Movie object has a title, some Actors, and 
 * results for the twelve Bechdel tests.
 *
 * @author Natalie Ho, Nicole Sobski, and Nerine Uyanik
 * @version 12/08/2022
 */
public class Movie implements Comparable<Movie>
{
    //instance variables
    private String title;
    private Hashtable<Actor, String> movieActors;
    private Vector<String> movieTests;
    private float femaleCastPercentage;
    private int feministScore;

    /**
     * Constructor for objects of class Movie.
     * 
     * @param title The title of the movie
     */
    public Movie(String title) {
        this.title = title; 
        movieActors = new Hashtable<Actor, String>();
        movieTests = new Vector<String>();
    }

    /**
     * getTitle() Returns the movie's title
     * 
     * @return The title of this movie
     */
    public String getTitle() {
        return title;
    }

    /**
     * getAllActors() Returns the movie's actors in a Hashtable
     * 
     * @return A Hashtable with all the actors who played in this movie
     */
    public Hashtable getAllActors() {
        return movieActors;
    }

    /**
     * getActors() returns a Linked List with all the actor names 
     * who played in this movie.
     * 
     * @return A LinkedList with the names of all the actors who 
     * played in this movie
     */
    public LinkedList<String> getActors() {
        LinkedList<String> actorList = new LinkedList<String>();

        for (Actor a : movieActors.keySet()) {
            //get actors in hastable 
            actorList.add(a.getName());
        }

        return actorList;
    }

    /**
     * getAllTestResults() returns a Vector with all the Bechdel 
     * test results for this movie
     * 
     * @return A Vector with the Bechdel test results for this movie: 
     * A test result can be "1" or "0" indicating that this movie 
     * passed or did not pass the corresponding test.
     */
    public Vector getAllTestResults() {
        return movieTests;
    }
    
    /**
     * getFemaleCastPercentage() accesses the female cast percentage of the movie
     * 
     * @return float - the percentage of female cast members
     */
    public float getFemaleCastPercentage() {
        return femaleCastPercentage;
    }
    
    /**
     * getFeministScore() accesses the feminist score associated with the movie
     * 
     * @return int - the feminist score (0, 1, 2, 3, 4, 5)
     */
    public int getFeministScore() {
        return feministScore;
    }
    
    /**
     * setTestResults() populates the testResults vector with "0" 
     * and "1"s, each representing the result of the coresponding 
     * test on this movie. This information will be read from the 
     * file "nextBechdel_allTests.csv"
     * 
     * @param results   A string consisting of of 0's and 1's. 
     * Each one of these values denotes the result of the 
     * corresponding test on this movie
     */
    public void setTestResults​(String results) {
        //create array of results string separated by commas
        String[] arrayOfStrings = results.split(",");
        //loop through array and add each result into testResults vector
        for (int i=0; i<arrayOfStrings.length; i++) {
            movieTests.add(arrayOfStrings[i]);
        }
    }
    
    /**
     * Tests this movie object with the input one and determines 
     * whether they are equal.
     * 
     * @return true if both objects are movies and have the same title, 
     * false in any other case.
     */
    public boolean equals(Object other) {
        if (other instanceof Movie) {
            return this.title.equals(((Movie) other).title); // Need explicit (Movie) cast to use .title
        } else {
            return false;
        }
    }

    /**
     * addOneActor
     * Takes in a String, formatted as lines are in the input file 
     * ("nextBechdel_castGender.txt"), generates an Actor, and adds 
     * the object to the actors of this movie. Input String has the 
     * following formatting: 
     * "MOVIE","ACTOR","CHARACTER_NAME","TYPE","BILLING","GENDER" 
     * Example of input: 
     * "Trolls","Ricky Dillon","Aspen Heitz","Supporting","18","Male"
     * 
     * @param line  A String representing the information of each Actor
     * @return      The Actor that was just added to this movie
     */
    public Actor addOneActor​(String line){
        //split the line into array of Strings representing info about actor
        String[] arrayActorInfo = line.split("\",\"");

        //removes comma from movie and gender strings
        arrayActorInfo[0] = arrayActorInfo[0].substring(1);
        arrayActorInfo[5] = arrayActorInfo[5].substring(0, arrayActorInfo[5].length()-1);

        //use index to get name and gender to create Actor object
        String actorName = arrayActorInfo[1];
        String actorGender = arrayActorInfo[5];

        Actor actor = new Actor(actorName, actorGender);
        //use index to get role of actor
        String actorRole = arrayActorInfo[3];
        //add to movieActors Actor as key and role as value
        movieActors.put(actor, actorRole);
        //return Actor object
        return actor;
    }

    /**
     * addAllActors() Reads the input file ("nextBechdel_castGender.txt"), 
     * and adds all its Actors to this movie. Each line in the movie 
     * has the following formatting: 
     * Input String has the following formatting: 
     * "MOVIE TITLE","ACTOR","CHARACTER_NAME","TYPE","BILLING","GENDER" 
     * Example of input: 
     * "Trolls","Ricky Dillon","Aspen Heitz","Supporting","18","Male"
     * 
     * @param actorsFile - The file containing information on each 
     * actor who acted in the movie.
     */
    public void addAllActors(String actorsFile) {
        try {
            //create scanner to read in file
            Scanner fileScan = new Scanner(new File(actorsFile));
            //continue reading file while there are more lines
            while (fileScan.hasNext()) {
                //read each line in the data file 
                String line = fileScan.nextLine();
                //split each line by quotes
                String[] arrayActorInfo = line.split("\",\"");
                //remove beginning quotation mark before movie title
                arrayActorInfo[0] = arrayActorInfo[0].substring(1);
                //check if the line has same movie title
                if (arrayActorInfo[0].equals(title)) {
                    //call addOneActor to add Actor to movieActors hashtable
                    addOneActor(line);
                }
            }
            //close scanner after exiting while loop
            fileScan.close();
        } catch (IOException ex) { //handles exceptions
            System.out.println(ex);
        }
    }
    
    /**
     * feministScore() provides a point system based on movies that pass the
     * rees-davies, ko, pierce, and feldman tests + checks which movies have 
     * 40% or more actors that are female 
     * 
     * @return int the movie's feminist score
     */
    public int feministScore() {
        //intializing score counter
        int score = 0;
        Vector<String> movieTestResults = this.getAllTestResults();
        String starScore = "";
        // Rees-Davies [12]
        if (movieTestResults.get(12).equals("0")) {
            //if true, add point to score
            score += 1;
            starScore += "*";
            // System.out.println(this.getTitle() + " passed the Rees-Davies test");
        } else {
            // System.out.println(this.getTitle() + " failed the Rees-Davies test");
        }
        // Ko [6]
        if (movieTestResults.get(6).equals("0")) {
            //if true, add point to score
            score += 1;
            starScore += "*";
            // System.out.println(this.getTitle() + " passed the Ko Test");
        } else {
            // System.out.println(this.getTitle() + " failed the Ko Test");
        }
        // Peirce [1]
        if (movieTestResults.get(1).equals("0")) {
            //if true, add point to score
            score += 1;
            starScore += "*";
            // System.out.println(this.getTitle() + " passed the Peirce Test");
        } else {
            // System.out.println(this.getTitle() + " failed the Peirce Test");
        }
        // Feldman [3]
        if (movieTestResults.get(3).equals("0")) {
            //if true, add point to score
            score += 1;
            starScore += "*";
            // System.out.println(this.getTitle() + " passed the Feldman Test");
        } else {
            // System.out.println(this.getTitle() + " failed the Feldman Test");
        }
        // pass if 40%+ of cast is female
        int totalCastSize = movieActors.size();
        int femaleCastSize = 0;
        for (Actor a : movieActors.keySet()) {
            //get actors in hastable 
            if (a.getGender().equals("Female")) {
                femaleCastSize += 1;
            }
        }
        femaleCastPercentage = (float) femaleCastSize/totalCastSize;
        
        if (femaleCastPercentage >= .4) {
            //if true, add point to score
            score += 1;
            starScore += "*";
            // System.out.println(this.getTitle() + " has a cast that is 40% female or greater");
        } else {
            // System.out.println(this.getTitle() + " has a cast that is less than 40% female");
        }
        // System.out.println(this.getTitle() + " Feminist score star rating (out of 5): " + starScore);
        return score;
    }
    
    /**
     * compareTo() evluates the difference between two movies
     * 
     * @param Movie otherMovie - the other movie was are comparing this movie to
     * @return int - 0 if the objects have the same score, 1 if the invoking movie
     * score is greater, -1 if the invoking movie score is smaller
     */
    public int compareTo(Movie otherMovie) {
        int thisFeministScore = this.feministScore();
        int otherFeministScore = otherMovie.feministScore();
        if (thisFeministScore == otherFeministScore) {
            if (this.getFemaleCastPercentage() > otherMovie.getFemaleCastPercentage()) {
                return 1;           
            }
            if (this.getFemaleCastPercentage() < otherMovie.getFemaleCastPercentage()) {
                return -1;                
            }
            return 0;
        }
        if (thisFeministScore > otherFeministScore) {
            return 1;
        }
        return -1;
    }
    /**
     * toString() returns a string representation of this movie. 
     * 
     * @return a string representation of this movie: 
     * includes the title and the number of actors who played in it.
     */
    public String toString() {
        String s = getTitle() + 
        " has " + movieActors.size() + " actors." + 
        " Its feminist score is " + this.feministScore() + 
        " (" + Math.round(this.getFemaleCastPercentage() *100.0) + "% female cast)";
        return s;
    }

    /**
     * main method for testing
     */
    public static void main(String [] args) {
        System.out.println("------------TESTING MOVIE CLASS-------------");
        System.out.println("**Creating new Movie object m1**");
        Movie m1 = new Movie("Hidden Figures");
        
        //testing getActors() --> empty linkedList
        System.out.println("~~Test empty getActors()~~");
        System.out.println("Expect: []");
        System.out.println("Result: " + m1.getActors());
        
        //testing setTestResults()
        System.out.println("**Setting test results**");
        m1.setTestResults("0,0,0,1,0,0,0,1,0,0,1,1,1");
        //testing getTestResults()
        System.out.println("~~Get test results~~");
        System.out.println("Expect: [0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 1, 1]");
        System.out.println("Result: " + m1.getAllTestResults());
        
        //testing addOneActor()
        System.out.println("~~Test addOneActor()~~");
        System.out.println("Expect:"
        +"\nThe actor Natalie Ho is Female"
        +"\nThe actor John Doe is Male");
        System.out.println("Result:");
        System.out.println(m1.addOneActor("\"Hidden Figures\","
        +"\"Natalie Ho\",\"Fake Actor 1\",\"Supporting\",\"19\","
        +"\"Female\""));
        System.out.println(m1.addOneActor("\"Hidden Figures\","
        +"\"John Doe\",\"Fake Actor 2\",\"Supporting\",\"23\","
        +"\"Male\""));
        //testing getActors()
        System.out.println("~~Test getActors()~~");
        System.out.println("Expect: [Natalie Ho, John Doe]");
        System.out.println("Result: " + m1.getActors());
        
        //testing addAllActors()
        System.out.println("**Add all actors in this movie.**");
        m1.addAllActors("data/nextBechdel_castGender.txt");
        //testing getAllActors()
        System.out.println("~~Test getAllActors()~~");
        System.out.println(m1.getAllActors());
        System.out.println("~Checking size of hashtable~");
        System.out.println("Expect: 98");
        System.out.println("Result: " + m1.getAllActors().size());
        
        //testing feministScore()
        System.out.println("~~Test feministScore()");
        System.out.println(m1.feministScore());
        
        //testing compareTo()
        System.out.println("**Creating new Movie object m2**");
        Movie m2 = new Movie("La La Land");
        m2.addAllActors("data/nextBechdel_castGender.txt");
        m2.setTestResults("0,0,1,1,0,1,1,1,1,0,1,1,1");
        System.out.println("**Comparing m1 to m2**");
        System.out.println("Expect: -1");
        System.out.println("Result: " + m1.compareTo(m2));
        
        System.out.println("**Creating new Movie object m3**");
        Movie m3 = new Movie("Doctor Strange");
        m3.addAllActors("data/nextBechdel_castGender.txt");
        m3.setTestResults("1,1,1,1,1,1,1,1,1,1,1,1,0");
        System.out.println("**Comparing m2 to m3**");
        System.out.println("Expect: 1");
        System.out.println("Result: " + m2.compareTo(m3));
        System.out.println("Comparing m3 to m2");
        System.out.println("Expect: -1");
        System.out.println("Result: " + m3.compareTo(m2));
        
        // getFemaleCastPercentage()
        System.out.println("**Testing getFemaleCastPercentage()");
        System.out.println("m1 " + m1.getFemaleCastPercentage());
        System.out.println("m2 " + m2.getFemaleCastPercentage());
        System.out.println("m3 " + m3.getFemaleCastPercentage());
    }
}

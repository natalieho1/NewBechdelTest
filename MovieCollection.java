import java.util.LinkedList;
import java.util.Scanner;
import java.io.*;
import java.util.Vector;
import java.util.Hashtable;
import java.util.Enumeration;
// import java.util.*;
import javafoundations.*;

/**
 * Represents a collection of Movies. Uses a LinkedList to hold the movie objects. 
 *
 * @author Natalie Ho, Nerine Uyanik, Nicole Sobski
 * @version 12/6/22
 */
public class MovieCollection
{
    // instance variables
    private LinkedList<Movie> allMovies;
    private LinkedList<Actor> allActors;
    private String testsFileName;
    private String castsFileName;

    /**
     * Constructor for objects of class MovieCollection
     */
    public MovieCollection(String testsFileName, String castsFileName)
    {
        allMovies = new LinkedList<Movie>();
        allActors = new LinkedList<Actor>();
        this.testsFileName = testsFileName;
        this.castsFileName = castsFileName;
    }

    /**
     * Returns all the movies in a LinkedList
     * 
     * @return Linked List of Movie Objects
     */
    public LinkedList<Movie> getMovies() {
        return allMovies;
    }

    /**
     * Returns all the actors in a LinkedList
     * 
     * @return Linked List of Actor Objects
     */
    public LinkedList<Actor> getActors() {
        return allActors;
    }

    /**
     * Returns all the movie names in a LinkedList
     * 
     * @return Linked List of movie names
     */
    public LinkedList<String> getMovieTitles() {
        //create linked list of strings 
        LinkedList<String> movieTitles = new LinkedList<String>();

        //loop through allMovies and get each movie title 
        for (Movie m : allMovies) {
            //add movie title to new linked list 
            movieTitles.add(m.getTitle());
        }

        return movieTitles;
    }

    /**
     * Returns all the actor names in a LinkedList
     * 
     * @return Linked List of actor names
     */
    public LinkedList<String> getActorNames() {
        LinkedList<String> actors = new LinkedList<String>();

        for (Movie m : allMovies){
            //loop through linked list of actor names
            for (String actor : m.getActors()) {
                //add actor name to linked list 
                actors.add(actor);

            }
        }
        return actors;
    }

    /**
     * Reads the cast for each movie from input casts file
     */
    public void readCasts() {
        //loop through linked list of movies to get movie object
        for (Movie m : allMovies) {
            m.addAllActors(castsFileName);
            //get hashtable from each movie object
            Hashtable<Actor, String> actorHashtable = m.getAllActors();
            //get all keys (actors) from hashtable of movie objects 
            Enumeration<Actor> e = actorHashtable.keys();
            //loop through enumeration and put each actor into linked list 
            while(e.hasMoreElements()) {
                Actor a = e.nextElement();
                allActors.add(a);
            }

        }
    }

    /**
     * Reads the input file, and uses its first column (movie title) to create all movie objects.
     */
    public void readMovies()
    {
        try {
            Scanner fileScan = new Scanner(new File(testsFileName)); //creating scanner to read in testsFileName
            fileScan.nextLine(); //reading the first line which just designates the categories of data
            while (fileScan.hasNext()) { //continue reading filing while there are lines to read in
                //each line in the data file 
                String line = fileScan.nextLine();

                //split each line by commas because the file contains a title and results separated by commas
                String[] arrayMovieInfo = line.split(",", 2);

                //create movie objects that contain a title and all the associated test results
                Movie movie = new Movie(arrayMovieInfo[0]);
                movie.setTestResults(arrayMovieInfo[1]);
                allMovies.add(movie);
            }
            fileScan.close(); //closing scanner once we exit the while loop
        } catch (IOException ex) {
            System.out.println(ex); //handles any problems with exception
        }
    }

    /**
     * Returns a list of all Movies that pass the n-th Bechdel test 
     * @param n - integer identifying the n-th test in the list of 12 Bechdel alternatives, starting from zero
     * @return A list of all Movies which have passed the n-th test
     */
    public LinkedList<Movie> findAllMoviesPassedTestNum(int n) {
        //create new linked list of movies 
        LinkedList<Movie> passedTest = new LinkedList<Movie>();
        //loop through linked list of movie objects 
        for (Movie m : allMovies) {
            //get each vector of test results from getAllTestResults method 
            Vector<String> mResults = m.getAllTestResults();
            //check if number at index n is equal to 0 
            if (mResults.get(n).equals("0")) {
                //if true, add movie to linked list 
                passedTest.add(m);
            }
        } 
        return passedTest;
    }

    /**
     * findAllMoviesPassedOnFailedOne() takes in two input indices corresponding to 
     * a alternate Bechdel test, the index of a test that was passed and the index of a test
     * that was failed. The movies that meet this constraint are returned in a Linked List.
     * 
     * @param test1 index of movie test result
     * @param test2 index of movie test result
     * 
     * @return a Linked List of movies that have met the given condition
     */
    public LinkedList<Movie> findAllMoviesPassedAtLeastOne(int test1, int test2) {
        //create new linked list of movies 
        LinkedList<Movie> passed1Test = new LinkedList<Movie>();
        //loop through linked list of movie objects 
        for (Movie m : allMovies) {
            //get each vector of test results from getAllTestResults method 
            Vector<String> movieResults = m.getAllTestResults();
            //check if number at index passed is equal to 0 and if number at index failed is equal to 1
            if (movieResults.get(test1).equals("0") || movieResults.get(test2).equals("0")) {
                //if true, add movie to linked list 
                passed1Test.add(m);
            }
        } 
        return passed1Test;
    }

    /**
     * findAllMoviesPassedOnFailedOne() takes in two input indices corresponding to 
     * a alternate Bechdel test, the index of a test that was passed and the index of a test
     * that was failed. The movies that meet this constraint are returned in a Linked List.
     * 
     * @param passed index of movie test result
     * @param failed index of movie test result
     * 
     * @return a Linked List of movies that have met the given condition
     */
    public LinkedList<Movie> findAllMoviesPassedOneFailedOne(int passed, int failed) {
        //create new linked list of movies 
        LinkedList<Movie> passed1Test = new LinkedList<Movie>();
        //loop through linked list of movie objects 
        for (Movie m : allMovies) {
            //get each vector of test results from getAllTestResults method 
            Vector<String> movieResults = m.getAllTestResults();
            //check if number at index passed is equal to 0 and if number at index failed is equal to 1
            if (movieResults.get(passed).equals("0") && movieResults.get(failed).equals("1")) {
                //if true, add movie to linked list 
                passed1Test.add(m);
            }
        } 
        return passed1Test;
    }
    
    /**
     * rankMovies() utilizes our PriorityQueue implementation to create a sorted ordering of
     * movies based on which score the highest using our feministScore() method
     * 
     * @return PriorityQueue of Movies
     */
    public PriorityQueue<Movie> rankMovies() {
        //create a new priority queue
        PriorityQueue<Movie> queue = new PriorityQueue<Movie>();

        //add movies into queue
        for (int i = 0; i < allMovies.size(); i++) {
            queue.enqueue(allMovies.get(i));
        }
        
        //get the first element and its feminist score 
        System.out.println(queue.first().getTitle() + " has the highest feminist score of " + queue.first().feministScore());
        return queue;
    }
    
    /**
     * toString() an informative message printed about the movie collection, such as how many
     * movies the collection contains and information about these movies
     * 
     * @return String - informative message
     */
    public String toString() {
        //informative statement about collection size
        String s1 = "This movie collection contains " + allMovies.size() + " movies:";
        //loop through all movies in collection to print each one for each line
        for (int i = 0; i < allMovies.size(); i++) {
            s1 += "\n"+allMovies.get(i);
        }
        return s1;
    }

    /**
     * Main Method used for testing purposes
     * 
     * @param String args
     */
    public static void main (String [] args) {
        //MovieCollection mo1 = new MovieCollection("data/nextBechdel_allTests.txt", "data/nextBechdel_castGender.txt");

        //mo1.readMovies();
        //mo1.readCasts();

        // LinkedList<Movie> bechdelTest = mo1.findAllMoviesPassedTestNum(0);

        // System.out.println("---------Finding all movies that passed the Bechdel Test---------");
        // System.out.println("There are " + bechdelTest.size() + " movies that passed the Bechdel test: ");
        // for (int i = 0; i < bechdelTest.size(); i++) {
        // System.out.println(bechdelTest.get(i));
        // }

        // System.out.println(); 
        
        // System.out.println("---------Finding all movies that passed either the Pierce or Landau Test---------");

        // LinkedList<Movie> pierceOrLandau = mo1.findAllMoviesPassedAtLeastOne(1, 2);
        
        // System.out.println("There are " + pierceOrLandau.size() + " movies that passed either the Pierce or the Landau tests: ");
        // for (int i = 0; i < pierceOrLandau.size(); i++) {
        // System.out.println(pierceOrLandau.get(i));
        // }
        // System.out.println(); 
        
        // System.out.println("---------Finding all movies that passed the White Test but not the Rees-Davies Test---------");
        // LinkedList<Movie> whiteNotRees = mo1.findAllMoviesPassedOneFailedOne(11, 12);
        
        // System.out.println("There are " + whiteNotRees.size() + " movies that passed the White test but NOT the Rees-Davies test: ");
        // for (int i = 0; i < whiteNotRees.size(); i++) {
        // System.out.println(whiteNotRees.get(i));
        // }
        // System.out.println(); 
        // for (Movie m : mo1.getMovies()) {
            // System.out.println(m.feministScore());
        // }

        MovieCollection mo2 = new MovieCollection("data/nextBechdel_allTests.txt", "data/nextBechdel_castGender.txt");

        mo2.readMovies();
        mo2.readCasts();
        
        System.out.println(); 
        
        //create a new priority queue
        PriorityQueue<Movie> result2 = mo2.rankMovies();

        System.out.println("---------------- rankMovies() RESULTS ----------------");
        while (!result2.isEmpty())
        {
            System.out.println(result2.dequeue());
        }
    }
}

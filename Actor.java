/**
 * Represents an object of type Actor. 
 * An Actor has a name and a gender.
 *
 * @author Natalie Ho, Nicole Sobski, and Nerine Uyanik
 * @version 12/08/2022
 */
public class Actor
{
    //instance variables
    private String name;
    private String gender;

    /**
     * Constructor for objects of class Actor
     */
    public Actor(String name, String gender) {
        this.name = name;
        this.gender = gender;
    }

    /**
     * getName() Returns the name of this actor
     * 
     * @return The name of this actor (String)
     */
    public String getName() {
        return name;
    }

    /**
     * getGender() Returns the gender of this actor
     * 
     * @return The gender of this actor (String)
     */
    public String getGender() {
        return gender;
    }

    /**
     * setGender() Sets the gender of this actor
     * 
     * @param newGender The gender of this actor (String)
     */
    public void setGender(String newGender) {
        gender = newGender;
    }

    /**
     * setName() Sets the name of this actor
     * 
     * @param newName   The name of this actor (String)
     */
    public void setName(String newName) {
        name = newName;
    }

    /**
     * This method is defined here because Actor (mutable) is used 
     * as a key in a Hashtable. It makes sure that same Actors have 
     * always the same hash code. So, the hash code of any object 
     * that is used as key in a hash table,has to be produced on an 
     *immutable* quantity, like a String (such a string is the 
     *name of the actor in our case)
     * 
     * @return an integer, which is the has code for the name of the actor
     */
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * Tests this actor against the input one and determines whether 
     * they are equal. Two actors are considered equal if they have 
     * the same name and gender.
     * 
     * @return true if both objects are of type Actor, 
     * and have the same name and gender, false in any other case.
     */
    public boolean equals(Object other) {
        if (other instanceof Actor) {
            return this.name.equals(((Actor) other).name) && 
            this.gender.equals(((Actor) other).gender); // Need explicit (Actor) cast to use .name
        } else {
            return false;
        }
    }

    /**
     * Returns a string representation of this actor. 
     * 
     * @return a string representation of this actor,
     * containing their name and gender.
     */
    public String toString() {
        String s;
        s = "The actor " + getName() + " is " + getGender();
        return s;
    }

    /**
     * main method for testing
     * 
     * @param String args
     */
    public static void main (String[] args) {
        System.out.println("~~Testing Actor constructor~~");
        Actor a1 = new Actor("Meryl Streep", "Female");
        System.out.println("Expect: The actor Meryl Streep is Female");
        System.out.println("Result: " + a1);

        System.out.println("~~Testing setName() and setGender()~~");
        Actor a2 = new Actor("","");
        a2.setName("Timothee Chalamet");
        a2.setGender("Male");
        System.out.println("Expect: The actor Timothee Chalamet is Male");
        System.out.println("Result: " + a2);
        
        System.out.println("~~Testing getName()~~");
        System.out.println("Expect: Timothee Chalamet");
        System.out.println("Result: " + a2.getName());
        System.out.println("~~Testing getGender()~~");
        System.out.println("Expect: Male");
        System.out.println("Result: " + a2.getGender());
    }
}

package javafoundations;
import javafoundations.exceptions.*;
import java.util.*;

/**
 * Represents a priority implementation of a queue (prioritizing items based on comparable)
 * 
 * @author Natalie Ho, Nerine Uyanik, Nicole Sobski
 * @version 12/14/2022
 */
public class PriorityQueue<T extends Comparable<T>> implements Queue<T> 
{
    //instance variable
    private LinkedMaxHeap<T> heap;
    
    /**
     * Constructor
     */
    public PriorityQueue()
    {
        heap = new LinkedMaxHeap<T>();
    }
  
    /**
     * Returns a reference to the front element of the queue
     * 
     * @return T reference to the element
     */
    public T first()
    {
        return heap.getMax(); //max element will be at the front because of our compareTo() method
    }
    
    /**
     * isEmpty() checks if the priority queue is empty. It returns true if the queue does not 
     * have any elements and false otherwise.
     * 
     * @return boolean - true if no elements, false otherwise
     */
    public boolean isEmpty() {
        return heap.isEmpty();
    }
    
    /**
     * size() finds the number of elements in priority queue. It returns the number of elements 
     * stored in the queue.
     * 
     * @return int - number of elements
     */
    public int size() {
        return heap.size();
    }
    
    /**
     * toString() calls the toString representation of our priority queue.
     * 
     * @return String - informative statement about elements in queue
     */
    public String toString() {
        return heap.toString();
    }
    
    /**
     * enqueue() adds the given element to the back of the queue.
     * 
     * @param T - the element being added to the queue
     */
    public void enqueue(T element) {
        heap.add(element);
    }
    
    /**
     * Removes the element that is at the front of the queue (prioritized element) and returns this element
     * 
     * @return T - the element at the front of the queue
     */
    public T dequeue(){
        try {
            T temp = heap.removeMax(); //temporary variable to remove the priority element from the queue
            return temp;
        } catch(EmptyCollectionException ex) {
            System.out.println(ex); //in case the queue is empty
        }
        return null;
    }
}

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import data_structures.BalancedTree;
import data_structures.BinarySearchTree;
import data_structures.Hashtable;

/**
 * The PhoneBook application Class for the dictionary.
 *
 * @version     0.1.0 01 Dec 2015
 * @author      Kent Vo
 */
public class PhoneBook {
/* The application to allow the user to interact with the dictionary.
 * NOTE: the add/delete method does not change the datafile on disk.
 */

    Hashtable<PhoneNumber, String> table;
    //BinarySearchTree<PhoneNumber, String> table;
    //BalancedTree<PhoneNumber, String> table;
    private int currentSize = 0;
    private int maxSize;

    /**
     * Set the PhoneBook to a specific size and the data structure to use.
     *
     * @param maxSize the specified size
     */
    public PhoneBook(int size) {
        this.maxSize = size;
        table = 
                new Hashtable<PhoneNumber, String>(maxSize);
              //  new BinarySearchTree<PhoneNumber, String>(); 
              //  new BalancedTree<PhoneNumber, String>();
    }
    
    /**
     * Load data into the PhoneBook from a text file in the key/value format,
     * where key=PhoneNumber and value=name (Last, First).
     *
     * @param filename the name of the text file
     */
    public void load(String filename) {
       try {
            BufferedReader bufferReader
                    = new BufferedReader(new FileReader(filename));
            String txtLine;
            while ((txtLine = bufferReader.readLine()) != null) {
               String splitString[] = txtLine.split("=");
               PhoneNumber temp = new PhoneNumber(splitString[0]);
               addEntry(temp, splitString[1]);
            }
            System.out.println(currentSize);
            bufferReader.close();
        } catch(IOException ex) {
            System.out.println("IOException caught");
        }
    }
    
    /**
     * Return the name value identified by the provided PhoneNumber key.
     *
     * @param number the provided PhoneNumber key
     * @return the name value; otherwise null if not found
     */
    public String numberLookup(PhoneNumber number) {
        return table.getValue(number); 
    }
    
    /**
     * Return the PhoneNumber key associated with the provided name value.
     *
     * @param name the provided name value
     * @return the first occurence of the PhoneNumber key; otherwise null
     */
    public PhoneNumber nameLookup(String name) {
        return table.getKey(name);
    }
    
    /**
     * Add a new PhoneNumber and name pair into the PhoneBook.
     *
     * @param number the PhoneNumber to add
     * @param name the name to add; must be in the form "Last, First"
     * @return false if PhoneBook is full or duplicate found; otherwise true
     */
    public boolean addEntry(PhoneNumber number, String name) {
        if (table.add(number, name)) {
            currentSize++;
            return true;
        }
        return false;
    }
    
    /**
     * Delete a specific PhoneNumber from the PhoneBook.
     *
     * @param number the specified PhoneNumber to delete
     * @return true if the PhoneNumber is found and deleted; otherwise false
     */
    public boolean deleteEntry(PhoneNumber number) {
        if (table.delete(number)) {
            currentSize--;
            return true;
        }
        return false;
    }
    
    /**
     * Print all PhoneNumber key with the associating name value; order is
     * sorted by PhoneNumber.
     */
    public void printAll() {
        Iterator<String> iterValue = table.values();
        for (Iterator<PhoneNumber> iterKey = table.keys(); iterKey.hasNext();) {
            PhoneNumber key = (PhoneNumber)iterKey.next();
            String value = (String)iterValue.next();
            System.out.println(key.toString()+ ": " + value);
        }
    }
    
    /**
     * Print all PhoneNumber key with the associating name value in the
     * area code provided; order is sorted by PhoneNumber.
     *
     * @param code the provided area code
     */
    public void printByAreaCode(String code) {
        Iterator<String> iterValue = table.values();
        for (Iterator<PhoneNumber> iterKey = table.keys(); iterKey.hasNext();) {
            PhoneNumber key = (PhoneNumber)iterKey.next();
            String value = (String)iterValue.next();
            if (key.toString().startsWith(code)) {
                System.out.println(key.toString() + ": " + value);
            }
        }
    }
    
    /**
     * Print all name value; order is sorted by name.
     */
    public void printNames() {
        String[] nodes = new String[currentSize];
        int i = 0;
        System.out.println(currentSize);
        for (Iterator<String> it = table.values(); it.hasNext();) {
            nodes[i++] = (String) it.next();
        }
        quickSort(nodes, 0, currentSize - 1);

        for (i = 0; i < currentSize; i++) {
            System.out.println(nodes[i]);
        }
    }
    
    /**
     * Quick sort the name value by name.
     *
     * @param nodes the string node
     * @param low the start of the string array
     * @param high the end of the string array
     */
    private void quickSort(String[] nodes, int low, int high) {
        int left = low;
        int right = high;
        int mid = (right - left) / (2 + left);
        String pivot = nodes[mid];
        while (left <= right) {
            while ((nodes[left]).compareTo(pivot) < 0) {
                left++;
            }

            while ((nodes[right]).compareTo(pivot) > 0) {
                right--;
            }

            if (left <= right) {
                String temp = nodes[left];
                nodes[left] = nodes[right];
                nodes[right] = temp;
                left++;
                right--;
            }
        }

        if (low < right) {
            quickSort(nodes, low, right);
        }

        if (left < high) {
            quickSort(nodes, left, high);
        }
    }
}

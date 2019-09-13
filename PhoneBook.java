import data_structures.*;
import java.util.Iterator;
import java.io.*;

public class PhoneBook <K extends Comparable<K>, V>{
    private int maxSize;
	private DictionaryADT<K, V> yellowPages;

    // Constructor. There is no argument-less constructor, or default size
    public PhoneBook(int maxSize) {
		this.maxSize = maxSize;
		yellowPages =	// new Hashtable<K,V>(maxSize);
				 		 new BinarySearchTree<K,V>();
						// new BalancedTree<K,V>();
	}
    
    // Reads PhoneBook data from a text file and loads the data into
    // the PhoneBook. Data is in the form "key=value" where a phoneNumber
    // is the key and a name in the format "Last, First" is the value.
    public void load(String filename) {
		File file = new File(filename);
		
		try {
			InputStream ips = new FileInputStream(file);
			InputStreamReader ipsReader = new InputStreamReader(ips);
			BufferedReader buffReader = new BufferedReader(ipsReader);
			String txtLine;
			
			while((txtLine = buffReader.readLine()) != null) {
				String[] splitString = txtLine.split("=");
				PhoneNumber tmp = new PhoneNumber(splitString[0]);

				addEntry(tmp, splitString[1]);
			}
			
			buffReader.close();
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
    
    // Returns the name associated with the given PhoneNumber, if it is
    // in the PhoneBook, null if it is not.
    public String numberLookup(PhoneNumber number) {
		return yellowPages.getValue((K) number).toString();
	}
    
    // Returns the PhoneNumber associated with the given name value.
    // There may be duplicate values, return the first one found.
    // Return null if the name is not in the PhoneBook.
    public PhoneNumber nameLookup(String name) {
		return (PhoneNumber) yellowPages.getKey((V) name);
	}
    
    // Adds a new PhoneNumber = name pair to the PhoneBook. All
    // names should be in the form "Last, First".
    // Duplicate entries are *not* allowed. Return true if the
    // insertion succeeds otherwise false (PhoneBook is full or
    // the new record is a duplicate). Does not change the datafile on disk.
    public boolean addEntry(PhoneNumber number, String name) {
		if(yellowPages.add((K) number, (V) name))
			return true;
			
		return false;
	}
    
    // Deletes the record associated with the PhoneNumber if it is
    // in the PhoneBook. Returns true if the number was found and
    // its record deleted, otherwise false. Does not change the datafile on disk.
    public boolean deleteEntry(PhoneNumber number) {
		if(yellowPages.delete((K) number))
				return true;
		
		return false;
	}
    
    // Prints a directory of all PhoneNumbers with their associated
    // names, in sorted order (ordered by PhoneNumber).
    public void printAll() {
		Iterator<K> iter = yellowPages.keys();
		
		while(iter.hasNext()) {
			K tmpKey = iter.next();
			System.out.println(tmpKey.toString() + ": " + yellowPages.getValue(tmpKey));
		}
	}
    
    // Prints all records with the given Area Code in ordered
    // sorted by PhoneNumber.
    public void printByAreaCode(String code) {
		Iterator<K> iter = yellowPages.keys();
		
		while(iter.hasNext()) {
			PhoneNumber tmpKey = (PhoneNumber) iter.next();
			if(code.compareTo(tmpKey.areaCode) == 0) 
				System.out.println(tmpKey.toString() + ": " + yellowPages.getValue((K) tmpKey));
		}
			
	}
    
    // Prints all of the names in the directory, in sorted order (by name,
    // not by number). There may be duplicates as these are the values.
    public void printNames() {
		Iterator<V> iter = yellowPages.values();
		
		while(iter.hasNext()) {
			System.out.println(iter.next());
		}
	}
}

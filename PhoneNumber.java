import data_structures.*;
import java.util.Iterator;

public class PhoneNumber implements Comparable<PhoneNumber> {
    String areaCode, prefix, number;
    String phoneNumber;

    // Constructor. Creates a new PhoneNumber instance. The parameter
    // is a phone number in the form xxx-xxx-xxxx, which is area code -
    // prefix - number. The phone number must be validated, and an
    // IllegalArgumentException thrown if it is invalid.
    public PhoneNumber(String n) {
		verify(n);

		phoneNumber = n;
		
		String splitNum[] = n.split("-");
		areaCode = splitNum[0];
		prefix = splitNum[1];
		number = splitNum[2];
	}
    
    // Follows the specifications of the Comparable Interface.
    @Override
    public int compareTo(PhoneNumber n) {
		return phoneNumber.compareTo(n.phoneNumber);
	}

    
    // Returns an int representing the hashCode of the PhoneNumber.
    public int hashCode() {
		return (int) (areaCode + prefix.hashCode() + number.hashCode()).hashCode() ;
	}
    
    // Private method to validate the Phone Number. Should be called
    // from the constructor.
    private void verify(String n) {
		if(!n.matches("(\\d{3})-(\\d{3})-(\\d{4})"))
			throw new IllegalArgumentException();
	}
    
    // Returns the area code of the Phone Number.
    public String getAreaCode() {
		return areaCode;
	}
    
    // Returns the prefix of the Phone Number.
    public String getPrefix() {
		return prefix;
	}
    
    // Returns the the last four digits of the number.
    public String getNumber() {
		return number;
	}
    
    // Returns the Phone Number.
    public String toString() {
		return phoneNumber;
	}
}

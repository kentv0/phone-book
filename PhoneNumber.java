import java.util.ConcurrentModificationException;
import java.util.Iterator;

/**
 * The PhoneNumber instance Class for {@link PhoneBook.class}.
 *
 * @version     0.1.0 01 Dec 2015
 * @author      Kent Vo
 */
public class PhoneNumber implements Comparable<PhoneNumber> {
/* The telephone number instance Class for the PhoneBook application. */

    String areaCode;
    String number;
    String phoneNumber;
    String prefix;
    
    /**
     * Create a new PhoneNumber instance.
     *
     * @param n the telephone number string in the format xxx-xxx-xxx
     */
    public PhoneNumber(String n) {
        verify(n);
        phoneNumber = n;
        areaCode = getAreaCode();
        prefix = getPrefix();
        number = getNumber();
    }
    
    /**
     * Follows the specifications of the Comparable Interface.
     *
     * @param n the provided PhoneNumber string
     */
    public int compareTo(PhoneNumber n) {
        if (areaCode.compareTo(n.getAreaCode()) != 0) {
            return areaCode.compareTo(n.getAreaCode());
        } else if (prefix.compareTo(n.getPrefix()) != 0) {
            return prefix.compareTo(n.getPrefix());
        } else if (number.compareTo(n.getNumber()) != 0) {
            return number.compareTo(n.getNumber());
        } else {
            return 0;
        }
    }
    
    /**
     * Return the hash code of the PhoneNumber.
     *
     * @return the integer representing the hash code of the PhoneNumber
     */
    public int hashCode() {
        return (Integer.parseInt(areaCode) + Integer.parseInt(prefix)
                + Integer.parseInt(number));
    }
    
    /**
     * Validate the PhoneNumber provided is in the correct format.
     *
     * @param n the provided PhoneNumber string
     * @throws IllegalArgumentException if PhoneNumber is formatted incorrectly
     */
    private void verify(String n) {
        if (n.matches("\\d{3}-\\d{3}-\\d{4}")) {
        } else {
            throw new IllegalArgumentException("must be in the pattern of"
                    + "xxx-xxx-xxxx");
        }
    }

    /**
     * Return the area code of the PhoneNumber.
     *
     * @return the area code string
     */
    public String getAreaCode() {
        return phoneNumber.substring(0, 3);
    }
    
    /**
     * Return the prefix of the PhoneNumber.
     *
     * @return the prefix string
     */
    public String getPrefix() {
        return phoneNumber.substring(4,7);
    }
    
    /**
     * Return the last four digit of the PhoneNumber.
     *
     * @return the last four digit string
     */
    public String getNumber() {
        return phoneNumber.substring(8, 12);
    }

    /**
     * Return the PhoneNumber.
     *
     * @return the PhoneNumber string.
     */
    public String toString() {
        String s = areaCode + "-" + prefix + "-" + number;
        return s;
    }
}

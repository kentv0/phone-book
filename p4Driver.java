/**
 * The p4Driver test driver class for {@link BalancedTree.class},
 * {@link BinarySearchTree.class}, {@link Hashtable.class}, and
 * {@link PhoneBook.class}.
 *
 * @version     0.1.0 01 Dec 2015
 * @author      Alan Riggins
 */
public class p4Driver {
    public static void main(String [] args) {
        PhoneBook BP = new PhoneBook(new Integer(10000));
		
        BP.load("p4_data.txt");
        BP.addEntry(new PhoneNumber("869-123-7895"), "Vo, Kent");
        BP.printAll();
        System.out.println("Inserting new value");
        BP.addEntry(new PhoneNumber("434-132-5543"), "White, Drew");
        BP.printAll();
        System.out.println("Removing Vo, Kent");
        BP.deleteEntry(new PhoneNumber("869-123-7895"));
        BP.deleteEntry(BP.nameLookup("White, Drew"));
        BP.printAll();
    }
}

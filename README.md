Phone Book
======
Description
------
The project details can be viewed in the document [here](https://github.com/kentv0/phone-book/blob/master/prompt.pdf).

Get Started
------
### 1. Clone repository
```
    $ git clone https://github.com/kentv0/phone-book.git
```
### 2. Compile package
```
    $ cd phone-book
    $ javac ./*.java data_structures/* 
```
### 3. Run test driver 1
```
    $ java DictionaryTester
```
```
    Expected output:
    Adding elements to dictionary
    Time for insertion of 1000000 elements: 714
    Now doing lookups
    Time for getValue with 1000000 elements: 455
    Now Doing deletion
    Time for deletion with 1000000 elements: 692
    The iterators should print 1 .. 10
    1   1
    2   2
    3   3
    4   4
    5   5
    6   6
    7   7
    8   8
    9   9
    10   10
    OK, iterators are fail-fast
    Now calling iterator on EMPTY structure:
    NO output should follow this line

```
### 4. Run test driver 2
```
    $ java p4Driver
```
```
    Expected output:
    10000
    ...
    869-123-7895: Vo, Kent
    ...
    Inserting new value
    ...
    434-132-5543: White, Drew
    ...
    Removing Vo, Kent
    ...
    
```
### 5. Change implementations
* This driver tests the three data structures: ```HashTable.java```, ```BinarySearchTree.java```, and ```BalancedTree.java```
* Modify ```DictionaryTester.java``` file and uncomment the desired implementation to test in lines 10-12
    ```java
    ...
    //new Hashtable<Integer, Integer>(SIZE);
    new BinarySearchTree<Integer, Integer>();
    new BalancedTree<Integer, Integer>();
    ...
    ```
* Modify ```PhoneBook.java``` file and uncomment the desired implementation to test in lines 20-22 and lines 34-36
    ```java
    ...
        Hashtable<PhoneNumber, String> table;
        //BinarySearchTree<PhoneNumber, String> table;
        //BalancedTree<PhoneNumber, String> table;
    ...
                  new Hashtable<PhoneNumber, String>(maxSize);
                //  new BinarySearchTree<PhoneNumber, String>(); 
                //  new BalancedTree<PhoneNumber, String>();
    ...
    ```
* Then repeat step 3 and 4 to re-run test drivers
### 6. Clean
```
    $ rm -rf ./*.class data_structures/*.class
```
### 7. Debug
* Ignore the following output during compile
    ```
    Note: Some input files use unchecked or unsafe operations.
    Note: Recompile with -Xlint:unchecked for details.
    ```

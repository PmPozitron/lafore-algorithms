package chapter5.single;

import java.util.Random;

// linkList.java
// demonstrates linked list
// to run this program: C>java LinkListApp
////////////////////////////////////////////////////////////////
class Link
{
    public Integer iData;              // data item
    public Double dData;           // data item
    public Link next;              // next link in list
    // -------------------------------------------------------------
    public Link(int id, double dd) // constructor
    {
        iData = id;                 // initialize data
        dData = dd;                 // ('next' is automatically
    }                           //  set to null)
    // -------------------------------------------------------------
    public void displayLink()      // display ourself
    {
        System.out.print("{" + iData + ", " + dData + "} ");
    }
}  // end class Link
////////////////////////////////////////////////////////////////
class LinkList
{
    private Link first;            // ref to first link on list

    // -------------------------------------------------------------
    public LinkList()              // constructor
    {
        first = null;               // no links on list yet
    }
    // -------------------------------------------------------------
    public boolean isEmpty()       // true if list is empty
    {
        return (first==null);
    }
    // -------------------------------------------------------------
    // insert at start of list
    public void insertFirst(int id, double dd)
    {                           // make new link
        Link newLink = new Link(id, dd);
        newLink.next = first;       // newLink --> old first
        first = newLink;            // first --> newLink
    }
    // -------------------------------------------------------------
    public Link deleteFirst()      // delete first item
    {                           // (assumes list not empty)
        Link temp = first;          // save reference to link
        first = first.next;         // delete it: first-->old next
        return temp;                // return deleted link
    }
    // -------------------------------------------------------------
    public void displayList() {
        System.out.print("List (first-->last): ");
        Link current = first;       // start at beginning of list
        while(current != null)      // until end of list,
        {
            current.displayLink();   // print data
            current = current.next;  // move to next link
        }
        System.out.println("");
    }
// -------------------------------------------------------------
}  // end class LinkList
////////////////////////////////////////////////////////////////
public class LinkListApp {
    public static void main(String[] args) {
        new LinkListApp().testMemoryExhausting();

    }  // end main()

    private void testDrive() {
        LinkList theList = new LinkList();  // make new list

        theList.insertFirst(22, 2.99);      // insert four items
        theList.insertFirst(44, 4.99);
        theList.insertFirst(66, 6.99);
        theList.insertFirst(88, 8.99);

        theList.displayList();              // display list

        while( !theList.isEmpty() )         // until it's empty,
        {
            Link aLink = theList.deleteFirst();   // delete link
            System.out.print("Deleted ");         // display it
            aLink.displayLink();
            System.out.println("");
        }
        theList.displayList();              // display list
    }

    private void testMemoryExhausting() {
        LinkList list = new LinkList();
        Random rnd = new Random();
        Double value = Double.NaN;
        int count = 1000000000;
        for (int i = 0; i < count; i++) {
            value = rnd.nextDouble();
            list.insertFirst(value.intValue(), value);
            if (i % 1000 == 0) {
                System.out.println("inserted items count: " + i);
            }
        }
    }
}  // end class LinkListApp
////////////////////////////////////////////////////////////////

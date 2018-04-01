package chapter5.doubly;

// doublyLinked.java
// demonstrates doubly-linked list
// to run this program: C>java DoublyLinkedApp
////////////////////////////////////////////////////////////////
class Link
{
    private long dData;                 // data item
    private Link next;                  // next link in list
    private Link previous;              // previous link in list
    // -------------------------------------------------------------
    public Link(long d)                // constructor
    { setdData(d); }
    // -------------------------------------------------------------
    public void displayLink()          // display this link
    { System.out.print(getdData() + " "); }

    public long getdData() {
        return dData;
    }

    public void setdData(long dData) {
        this.dData = dData;
    }

    public Link getNext() {
        return next;
    }

    public void setNext(Link next) {
        this.next = next;
    }

    public Link getPrevious() {
        return previous;
    }

    public void setPrevious(Link previous) {
        this.previous = previous;
    }

// -------------------------------------------------------------
}  // end class Link
////////////////////////////////////////////////////////////////
class DoublyLinkedList
{
    private Link first;               // ref to first item
    private Link last;                // ref to last item

    public Link getFirst() {
        return first;
    }

    public Link getLast() {
        return last;
    }

    // -------------------------------------------------------------
    public DoublyLinkedList()         // constructor
    {
        first = null;                  // no items on list yet
        last = null;
    }
    // -------------------------------------------------------------
    public boolean isEmpty()          // true if no links
    { return first==null; }
    // -------------------------------------------------------------
    public void insertFirst(long dd)  // insert at front of list
    {
        Link newLink = new Link(dd);   // make new link

        if( isEmpty() )                // if empty list,
            last = newLink;             // newLink <-- last
        else
            first.setPrevious(newLink);   // newLink <-- old first
        newLink.setNext(first);          // newLink --> old first
        first = newLink;               // first --> newLink
    }
    // -------------------------------------------------------------
    public void insertLast(long dd)   // insert at end of list
    {
        Link newLink = new Link(dd);   // make new link
        if( isEmpty() )                // if empty list,
            first = newLink;            // first --> newLink
        else
        {
            last.setNext(newLink);        // old last --> newLink
            newLink.setPrevious(last);    // old last <-- newLink
        }
        last = newLink;                // newLink <-- last
    }
    // -------------------------------------------------------------
    public Link deleteFirst()         // delete first link
    {                              // (assumes non-empty list)
        Link temp = first;
        if(first.getNext() == null)         // if only one item
            last = null;                // null <-- last
        else
            first.getNext().setPrevious(null); // null <-- old next
        first = first.getNext();            // first --> old next
        return temp;
    }
    // -------------------------------------------------------------
    public Link deleteLast()          // delete last link
    {                              // (assumes non-empty list)
        Link temp = last;
        if(first.getNext() == null)         // if only one item
            first = null;               // first --> null
        else
            last.getPrevious().setNext(null);  // old previous --> null
        last = last.getPrevious();          // old previous <-- last
        return temp;
    }
    // -------------------------------------------------------------
    // insert dd just after key
    public boolean insertAfter(long key, long dd)
    {                              // (assumes non-empty list)
        Link current = first;          // start at beginning
        while(current.getdData() != key)    // until match is found,
        {
            current = current.getNext();     // move to next link
            if(current == null)
                return false;            // didn't find it
        }
        Link newLink = new Link(dd);   // make new link

        if(current==last)              // if last link,
        {
            newLink.setNext(null);        // newLink --> null
            last = newLink;             // newLink <-- last
        }
        else                           // not last link,
        {
            newLink.setNext(current.getNext()); // newLink --> old next
            // newLink <-- old next
            current.getNext().setPrevious(newLink);
        }
        newLink.setPrevious(current);    // old current <-- newLink
        current.setNext(newLink);        // old current --> newLink
        return true;                   // found it, did insertion
    }
    // -------------------------------------------------------------
    public Link deleteKey(long key)   // delete item w/ given key
    {                              // (assumes non-empty list)
        Link current = first;          // start at beginning
        while(current.getdData() != key)    // until match is found,
        {
            current = current.getNext();     // move to next link
            if(current == null)
                return null;             // didn't find it
        }
        if(current==first)             // found it; first item?
            first = current.getNext();       // first --> old next
        else                           // not first
            // old previous --> old next
            current.getPrevious().setNext(current.getNext());

        if(current==last)              // last item?
            last = current.getPrevious();    // old previous <-- last
        else                           // not last
            // old previous <-- old next
            current.getNext().setPrevious(current.getPrevious());
        return current;                // return value
    }
    // -------------------------------------------------------------
    public void displayForward()
    {
        System.out.print("List (first-->last): ");
        Link current = first;          // start at beginning
        while(current != null)         // until end of list,
        {
            current.displayLink();      // display data
            current = current.getNext();     // move to next link
        }
        System.out.println("");
    }
    // -------------------------------------------------------------
    public void displayBackward()
    {
        System.out.print("List (last-->first): ");
        Link current = last;           // start at end
        while(current != null)         // until start of list,
        {
            current.displayLink();      // display data
            current = current.getPrevious(); // move to previous link
        }
        System.out.println("");
    }
// -------------------------------------------------------------
}  // end class DoublyLinkedList
////////////////////////////////////////////////////////////////
class DoublyLinkedApp
{
    public static void main(String[] args)
    {                             // make a new list
        DoublyLinkedList theList = new DoublyLinkedList();

        theList.insertFirst(22);      // insert at front
        theList.insertFirst(44);
        theList.insertFirst(66);

        theList.insertLast(11);       // insert at rear
        theList.insertLast(33);
        theList.insertLast(55);

        theList.displayForward();     // display list forward
        theList.displayBackward();    // display list backward

        theList.deleteFirst();        // delete first item
        theList.deleteLast();         // delete last item
        theList.deleteKey(11);        // delete item with key 11

        theList.displayForward();     // display list forward

        theList.insertAfter(22, 77);  // insert 77 after 22
        theList.insertAfter(33, 88);  // insert 88 after 33

        theList.displayForward();     // display list forward
    }  // end main()
}  // end class DoublyLinkedApp
////////////////////////////////////////////////////////////////


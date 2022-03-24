import java.util.AbstractCollection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedCollection<E> extends AbstractCollection<E> {

    private static class Node<E>{
        private E data;
        Node<E> next;

        private Node(E data, Node<E> next) {
            this.data = data;
            this.next = next;
        }
    }

    private int count;
    private Node<E> tail;
    private int version;

    private LinkedCollection(boolean ignored) {} // DO NOT CHANGE THIS

    private static boolean doReport = true;

    private boolean report(String s) {
        if (doReport) System.out.println("invariant error: "+ s);
        return false;
    }

    /**
     * Check the invariant:
     * <ol>
     * <li> The "tail" is not null.
     * <li> We have a cycle from "tail" back around to "tail", in particular
     *   <ul>
     *   <li> There are no null "next" pointers (the list never ends)
     *   <li> We don't cycle back to a node <em>other</em> than the tail.
     *   </ul>
     *   This can be accomplished by a variant on Floyd's algorithm
     *   (tortoise and hare), where we stop if we hit null (an error)
     *   or the hare and tortoise are found on the same node (another error),
     *   or if the hare hits the tail again (everything is OK).
     * <li> We check that the node after the tail is a dummy node (data is null).
     * <li> We check that the number of nodes (including dummy) is one more than "count".
     * </ol>
     * @return whether the invariant is true
     */
    private boolean wellFormed() {
        // TODO: check for problems and report if found
        //checking if there isn't a cyclic element
        if (tail == null) return report("tail is null");
        if (tail.next == null) return report("tail points to null");
        Node <E> turtle = tail;
        Node <E> rabbit = tail.next;
        int nodeCount = 0;
        while(turtle.next != tail) {
            if (turtle== null || rabbit == null ) return report("found null element");
            if (turtle == rabbit) return report("Tortoise and hare point to same node");
            if (rabbit == tail) break;
            turtle = turtle.next;
            rabbit = rabbit.next.next;
        }
        if (tail.next.data != null) return report("Tail not pointing to dummy node");
        Node<E> j = null;
        for (Node<E> i = tail; i != j; i = i.next) {
            ++nodeCount;
            j = tail;
        }
        if (nodeCount != (this.count+1)) return report("Number of nodes does not match count");
        return true;
    }

    /**
     * Initialize an empty collection
     * @param - none
     * @postcondition
     *   This collection is empty
     **/
    public LinkedCollection() {
        count = 0;
        version = 0;
        tail = new Node<E>(null, null);
        tail.next = tail;
        assert wellFormed() : "invariant broken at constructor";
    }
    // You need a nested (not static) class to implement the iterator:
    private class MyIterator implements Iterator<E> {
        Node<E> precursor;
        Node<E> cursor;
        int myVersion;
        MyIterator(boolean ignored) {} // DO NOT CHANGE THIS

        private boolean wellFormed() {
            //**********************************************
            if (!LinkedCollection.this.wellFormed()) return false;
            //*********************************************
            if (myVersion != version) return true;
            //**********************************************
            boolean precursorFound = false;
            Node<E> j = null;
            for (Node<E> i = tail; i != j; i = i.next) {
                if (precursor == i) precursorFound = true;
                j = tail;
            }
            if (!precursorFound) return report("precursor not in list");
            //***********************************************
            if (cursor != precursor && cursor != precursor.next) return report("Wrong value for cursor");
            //***********************************************
            if (cursor == tail.next && cursor!= precursor) return report("cursor is dummy only if it is the same as the precursor");
            return true;
        }
        /**
         * constructor
         * @param - none
         * @postcondition - versions are equal, cursor and precursor point to dummy
         **/
        public MyIterator() {
            myVersion = version;
            precursor = tail.next;
            cursor = tail.next;
            assert wellFormed() : "invariant fails in iterator constructor";
        }

        @Override //required
        public boolean hasNext() {
            assert wellFormed() : "Invariant false at beginning of hasNext";
            if (version != myVersion) throw new ConcurrentModificationException("bruh");
            if (cursor != tail ) return true;
            return false;
        }

        @Override //required
        public E next() {
            assert wellFormed() : "Invariant false at beginning of next";
            if (!hasNext()) throw new NoSuchElementException("No such element");
            precursor = cursor;
            cursor = cursor.next;
            assert wellFormed() : "Invariant false at end of next";
            return cursor.data;
        }
        @Override //implementation
        public void remove() {
            assert wellFormed() : "Invariant false at beginning of remove";
            if (version != myVersion) throw new ConcurrentModificationException("version mismatch at remove");
            if ( cursor == precursor ) throw new IllegalStateException("precursor == cursor at remove");
            if (cursor != tail) {
                precursor.next = cursor.next;
                cursor = precursor;
                count--;
                ++myVersion;
                ++version;
            }else {
                precursor.next = cursor.next;
                tail = precursor;
                cursor = tail;
                count--;
                ++myVersion;
                ++version;
            }
            assert wellFormed() : "Invariant false at end of remove";
        }

    }
    @Override //required
    public Iterator<E> iterator() {
        return new MyIterator();
    }

    @Override //required
    public int size() {
        return this.count;
    }
    @Override //implementation
    public boolean add(E element) {
        assert wellFormed() : "invariant failed at start of add";
        Node<E> temp = tail.next;
        Node<E> newNode = new Node<E>(element,null);
        tail.next = newNode;
        newNode.next = temp;
        tail = newNode;
        this.count++;
        this.version++;
        assert wellFormed() : "invariant failed at end of add";
        return true;
    }
    @Override //implementation
    public void clear() {
        if( this.count == 0) return;
        Node<E> temp = tail.next;
        temp.next = temp;
        tail = temp;
        this.count = 0;
        ++version;
    }
}
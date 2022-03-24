public class PointSequence implements Cloneable {

    private static class Node {
        Point data;
        Node next;

        public Node(Point data, Node next) {
            this.data = data;
            this.next = next;
        }
    }


    private int manyNodes;
    private Node head;
    private Node tail;
    private Node cursor;
    private Node precursor;

    private static boolean doReport = true; // used only by invariant checker

    /**
     * Used to report an error found when checking the invariant.
     * By providing a string, this will help debugging the class if the invariant should fail.
     *
     * @param error string to print to report the exact error found
     * @return false always
     */
    private boolean report(String error) {
        if (doReport) System.out.println("Invariant error found: " + error);
        return false;
    }

    /**
     * Check the invariant.  For information on what a class invariant is,
     * please read page 123 in the textbook.
     * Return false if any problem is found.  Returning the result
     * of {@link #report(String)} will make it easier to debug invariant problems.
     *
     * @return whether invariant is currently true
     */
    private boolean wellFormed() {
        // Invariant:
        // 1. The list must not include a cycle.
        // 2. "tail" must point to the last node in the list starting at "head".
        //    In particular, if the list is empty, "tail" must be null.
        // 3. "manyNodes" is number of nodes in list
        // 4. "precursor" is either null or points to a node in the list, other than the tail.
        // 5. "cursor" is the node after "precursor" (if "precursor" is not null),
        //    or is either null or the head node (otherwise).
        // This is the invariant of the data structure according to the
        // design in the textbook on pages 233-4 (3rd ed. 226-7)

        // Implementation:
        // Do multiple checks: each time returning false if a problem is found.
        // (Use "report" to give a descriptive report while returning false.)

        // We do the first one for you:
        // check that list is not cyclic
        if (head != null) {
            // This check uses the "tortoise and hare" algorithm attributed to Floyd.
            Node fast = head.next;
            for (Node p = head; fast != null && fast.next != null; p = p.next) {
                if (p == fast) return report("list is cyclic!");
                fast = fast.next.next;
            }
        }
        // Implement remaining conditions.
        //checking that tail points to last node:
        if (head != null) {
            Node fast;
            for (fast = head; fast.next != null; fast = fast.next) {
            }
            if (fast != tail) {
                return report("Tail is not last element");
            }
        } else {
            if (tail != null) return report("tail should be null because list is empty");
        }

        // checking that manyNodes is number of nodes in list
        if (head == null && manyNodes != 0) return report("manyNodes should be 0");
        else {
            Node fast;
            int n = 0;
            for (fast = head; fast != null; fast = fast.next) {
                n++;
            }
            if (manyNodes != n) return report("manyNodes does not equal number of nodes");
        }
        //  "precursor" is either null or points to a node in the list, other than the tail.
        if (precursor != null) {
            if (precursor == tail) return report("precursor = tail");
            else {
                Node fast;
                boolean inList = false;
                for (fast = head; fast != null; fast = fast.next) {
                    if (precursor == fast) inList = true;
                }
                if (!inList) return report("precursor not in list");
            }
            if (cursor != null) {
                if (cursor != precursor.next) return report("precursor is not before cursor");
            }
        } else {
            if (cursor != null && cursor != head) {
                return report("List not connected to head");
            }
        }
        if (cursor == null && precursor != null) {
            return report("precursor should be null if cursor is null");
        }

        // If no problems found, then return true:
        return true;
    }

    private PointSequence(boolean doNotUse) {
    } // only for purposes of testing, do not change

    /**
     * Create an empty sequence
     *
     * @param - none
     * @postcondition This sequence is empty
     **/
    public PointSequence() {
        // TODO: initialize fields (if necessary)
        this.manyNodes = 0;
        this.head = null;
        this.tail = null;
        this.cursor = null;
        this.precursor = null;
        assert wellFormed() : "invariant failed in constructor";
    }

    /// Simple sequence methods

    /**
     * Determine the number of elements in this sequence.
     *
     * @param - none
     * @return the number of elements in this sequence
     **/
    public int size() {
        assert wellFormed() : "invariant wrong at start of size()";
        return this.manyNodes;
        // TODO: Implemented by student.
        // This method shouldn't modify any fields, hence no assertion at end
    }

    /**
     * Set the current element at the front of this sequence.
     *
     * @param - none
     * @postcondition The front element of this sequence is now the current element (but
     * if this sequence has no elements at all, then there is no current
     * element).
     **/
    public void start() {
        assert wellFormed() : "invariant wrong at start of start()";

        this.cursor = this.head;
        this.precursor = null;
        assert wellFormed() : "invariant wrong at end of start()";
    }

    /**
     * Accessor method to determine whether this sequence has a specified
     * current element that can be retrieved with the
     * getCurrent method.
     *
     * @param - none
     * @return true (there is a current element) or false (there is no current element at the moment)
     **/
    public boolean isCurrent() {
        assert wellFormed() : "invariant wrong at start of getCurrent()";
        // TODO: Implemented by student.
        // This method shouldn't modify any fields, hence no assertion at end
        if (this.cursor != null) {
            return true;
        }
        return false;
    }

    /**
     * Accessor method to get the current element of this sequence.
     *
     * @param - none
     * @return the current element of this sequence
     * @throws IllegalStateException Indicates that there is no current element, so
     *                               getCurrent may not be called.
     * @precondition isCurrent() returns true.
     **/
    public Point getCurrent() {
        assert wellFormed() : "invariant wrong at start of getCurrent()";
        // TODO: Implemented by student.
        if (isCurrent()) {
            return this.cursor.data;
        } else throw new IllegalStateException("no current element in getCurrent");
        // This method shouldn't modify any fields, hence no assertion at end
    }

    /**
     * Move forward, so that the current element is now the next element in
     * this sequence.
     *
     * @param - none
     * @throws IllegalStateException Indicates that there is no current element, so
     *                               advance may not be called.
     * @precondition isCurrent() returns true.
     * @postcondition If the current element was already the end element of this sequence
     * (with nothing after it), then there is no longer any current element.
     * Otherwise, the new element is the element immediately after the
     * original current element.
     **/
    public void advance() {
        assert wellFormed() : "invariant wrong at start of advance()";
        // TODO: Implemented by student.
        if (isCurrent()) {
            if (cursor == tail) {
                precursor = null;
                cursor = null;
            } else {
                precursor = cursor;
                this.cursor = cursor.next;
            }
        } else throw new IllegalStateException("no current element in advance.");
        assert wellFormed() : "invariant wrong at end of advance()";

    }

    /**
     * Add a new element to this sequence, after the current element.
     * If the new element would take this sequence beyond its current capacity,
     * then the capacity is increased before adding the new element.
     *
     * @param element the new element that is being added, may be null
     * @throws OutOfMemoryError Indicates insufficient memory for increasing the sequence's capacity.
     * @postcondition A new copy of the element has been added to this sequence. If there was
     * a current element, then the new element is placed after the current
     * element. If there was no current element, then the new element is placed
     * at the beginning of the sequence. In all cases, the new element becomes the
     * new current element of this sequence.
     **/
    public void append(Point element) {
        assert wellFormed() : "invariant wrong at start of append";
        if (isCurrent()) {
            Node n = new Node(element, cursor.next);
            cursor.next = n;
            if (cursor == tail) {
                tail = n;
            }
            precursor = cursor;
            cursor = n;
            manyNodes++;
        } else {
            Node n = new Node(element, head);
            if (manyNodes == 0) {
                cursor = head = tail = n;
            } else cursor = head = n;
            manyNodes++;
        }
        assert wellFormed() : "invariant wrong at end of append";
    }

    /**
     * Remove the current element from this sequence.
     *
     * @param - none
     * @throws IllegalStateException Indicates that there is no current element, so
     *                               removeCurrent may not be called.
     * @precondition isCurrent() returns true.
     * @postcondition The current element has been removed from this sequence, and the
     * following element (if there is one) is now the new current element.
     * If there was no following element, then there is now no current
     * element.
     **/
    public void removeCurrent() {
        assert wellFormed() : "invariant wrong at start of removeCurrent()";
        // TODO: Implemented by student.
        // See textbook pp.176-78, 181-184
        if (isCurrent()) {
            if (manyNodes == 1) {
                head = tail = precursor = cursor = null;
                manyNodes--;
            } else {
                if (cursor == head) {
                    head = head.next;
                    cursor = cursor.next;
                    manyNodes--;
                } else if (cursor == tail) {
                    tail = precursor;
                    tail.next = null;
                    precursor = cursor = null;
                    manyNodes--;
                } else {
                    precursor.next = cursor.next;
                    cursor = cursor.next;
                    manyNodes--;
                }
            }

        } else throw new IllegalStateException("no current element");
        assert wellFormed() : "invariant wrong at end of removeCurrent()";
    }

    /**
     * Place the contents of another sequence at the end of this sequence.
     *
     * @param addend a sequence whose contents will be placed at the end of this sequence
     * @throws NullPointerException Indicates that addend is null.
     * @throws OutOfMemoryError     Indicates insufficient memory to increase the size of this sequence.
     * @precondition The parameter, addend, is not null.
     * @postcondition The elements from addend have been placed at the end of
     * this sequence. The current element of this sequence if any,
     * remains unchanged.   The addend is unchanged.
     **/
    public void addAll(PointSequence addend) {
        assert wellFormed() : "invariant wrong at start of addAll";
        assert addend.wellFormed() : "invariant of parameter wrong at start of addAll";
        // TODO: Implemented by student.
        // (Using clone() will reduce the work.)
        if (addend == null) throw new NullPointerException("addend is null.");
        PointSequence AddendClone = addend.clone();

        if (head != null) {
            this.tail.next = AddendClone.head;
            if (AddendClone.tail != null) {
                this.tail = AddendClone.tail;
            }
        } else {
            this.head = AddendClone.head;
            this.tail = AddendClone.tail;
        }
        manyNodes += AddendClone.manyNodes;
        assert wellFormed() : "invariant wrong at end of AddAll";
        assert addend.wellFormed() : "invariant of parameter wrong at end of insertAll";
    }


    /**
     * Generate a copy of this sequence.
     *
     * @param - none
     * @return The return value is a copy of this sequence. Subsequent changes to the
     * copy will not affect the original, nor vice versa.
     * Whatever was current in the original object is now current in the clone.
     * @throws OutOfMemoryError Indicates insufficient memory for creating the clone.
     **/
    public PointSequence clone() {
        assert wellFormed() : "invariant wrong at start of clone()";

        PointSequence result;

        try {
            result = (PointSequence) super.clone();
        } catch (CloneNotSupportedException e) {
            // This exception should not occur. But if it does, it would probably
            // indicate a programming error that made super.clone unavailable.
            // The most common error would be forgetting the "Implements Cloneable"
            // clause at the start of this class.
            throw new RuntimeException
                    ("This class does not implement Cloneable");
        }

        // TODO: Implemented by student.
        // Now do the hard work of cloning the list.
        // See pp. 193-197, 228
        // Setting precursor correctly is tricky.

        if (head != null) {
            Node resultHead = new Node(head.data, null);


            result.head = resultHead;
            result.tail = resultHead;

            Node IteratingNode = head;
            if (this.cursor == head) {
                result.cursor = resultHead;
            }
            if (this.precursor == head) {
                result.precursor = resultHead;
            }


            while (IteratingNode.next != null) {
                IteratingNode = IteratingNode.next;
                Node IteratingNodeCopy = new Node(IteratingNode.data, null);

                if (this.precursor == IteratingNode) {
                    result.precursor = IteratingNodeCopy;
                }
                if (this.cursor == IteratingNode) {
                    result.cursor = IteratingNodeCopy;
                }

                result.tail.next = IteratingNodeCopy;
                result.tail = result.tail.next;
            }

        }

        assert wellFormed() : "invariant wrong at end of clone()";
        assert result.wellFormed() : "invariant wrong for result of clone()";
        return result;
    }
}
// AvlTree class
//
// CONSTRUCTION: with no initializer
//
// ******************PUBLIC OPERATIONS*********************
// void insert( x )       --> Insert x
// void remove( x )       --> Remove x (unimplemented)
// boolean contains( x )  --> Return true if x is present
// boolean remove( x )    --> Return true if x was present
// Comparable findMin( )  --> Return smallest item
// Comparable findMax( )  --> Return largest item
// boolean isEmpty( )     --> Return true if empty; else false
// void makeEmpty( )      --> Remove all items
// void printTree( )      --> Print tree in sorted order
// ******************ERRORS********************************
// Throws UnderflowException as appropriate

/**
@file: AvlTree.java
@description: This is my AVL tree class. I finished the functions that were not provided in the public operations listed above.
 I added find help and remove help methods. I build the single and double rotations, I picked up the left rotations from class and inverted it for the right side
 The class has all the basic functions of an AVL tree and with the insertion method can build a working and balanced AVL
@author: Michael Iaccarino
@date: October 23, 2024
 **/

/**
 * Implements an AVL tree.
 * Note that all "matching" is based on the compareTo method.
 */
public class AvlTree<AnyType extends Comparable<? super AnyType>> {
    /**
     * Construct the tree.
     */
    public AvlTree( ) {
        root = null;
    }

    /**
     * Insert into the tree; duplicates are ignored.
     * @param x the item to insert.
     */
    public void insert( AnyType x ) {
        root = insert( x, root );
    }

    /**
     * Remove from the tree. Nothing is done if x is not found.
     * @param x the item to remove.
     */
    public void remove( AnyType x ) {
        root = remove( x, root );
    }


    /**
     * Internal method to remove from a subtree.
     * @param x the item to remove.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */

    //find help method for remove to search tree and get the node we are looking for
    private AvlNode<AnyType> findHelp(AnyType x,  AvlNode<AnyType> t) {
        if (t == null){
            return null;
        }
        if (t.element.compareTo( x ) > 0) {
            return findHelp( x, t.left );
        }
        else if (t.element.compareTo( x ) == 0) {
            return t;
        }
        else{
            return findHelp( x, t.right );
        }



    }

    //remove help

    private AvlNode<AnyType> removeHelp(AnyType x, AvlNode<AnyType> t) {
        if (t == null) {
            return null; // If node is null, nothing to remove.
        }
        if (t.element == null){
            return null;
        }
        if (t.element.compareTo(x) > 0) {
            t.left = (removeHelp(x, t.left));
        }
        else if (t.element.compareTo(x) < 0) {
            t.right = (removeHelp(x, t.right));
        }
        else {
            if (t.left == null) {
                return t.right;
            }
            else if (t.right == null) {
                return t.left;
            }
            else{
                AvlNode<AnyType> temp = findMax(t.left);
                t.element = temp.element;
                t.left = remove(temp.element,t.left);
            }
        }
        return balance(t);
    }



    private AvlNode<AnyType> remove( AnyType x, AvlNode<AnyType> t ) {
        //first we need to find the node we are looking for
        AvlNode<AnyType> temp = findHelp(x, t);
        if (temp != null) {
            t = removeHelp(x, t);
        }
        return t;
    }

    /**
     * Find the smallest item in the tree.
     * @return smallest item or null if empty.
     */
    public AnyType findMin( ) {
        if( isEmpty( ) ) {
            throw new UnderflowException( );
        }
        return findMin( root ).element;
    }

    /**
     * Find the largest item in the tree.
     * @return the largest item of null if empty.
     */
    public AnyType findMax( ) {
        if( isEmpty( ) )
            throw new UnderflowException( );
        return findMax( root ).element;
    }

    /**
     * Find an item in the tree.
     * @param x the item to search for.
     * @return true if x is found.
     */
    public boolean contains( AnyType x ) {
        return contains( x, root );
    }

    /**
     * Make the tree logically empty.
     */
    public void makeEmpty( ) {
        root = null;
    }

    /**
     * Test if the tree is logically empty.
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty( ) {
        return root == null;
    }

    /**
     * Print the tree contents in sorted order.
     */
    public void printTree( ) {
        if( isEmpty( ) )
            System.out.println( "Empty tree" );
        else
            printTree( root );
    }

    private static final int ALLOWED_IMBALANCE = 1;

    // Assume t is either balanced or within one of being balanced
    private AvlNode<AnyType> balance( AvlNode<AnyType> t ) {
	    if (t == null){
            return t;
        }

        if (height(t.left)-height(t.right)>ALLOWED_IMBALANCE){
            if (height(t.left.left) >= height(t.left.right)){
                t = rotateWithLeftChild(t);
            }
            else {
                t = doubleWithLeftChild(t);
            }
        }
        else {
            if (height(t.right)-height(t.left)>ALLOWED_IMBALANCE){
                if (height(t.right.right) >= height(t.right.left)){
                    t = rotateWithRightChild(t);
                }
                else {
                    t = doubleWithRightChild(t);
                }
            }
        }

        t.height = Math.max(height(t.left), height(t.right))+1;
        return t;
    }

    public void checkBalance( ) {
        checkBalance( root );
    }

    private int checkBalance( AvlNode<AnyType> t ) {
        if( t == null )
            return -1;

        if( t != null ) {
            int hl = checkBalance( t.left );
            int hr = checkBalance( t.right );
            if( Math.abs( height( t.left ) - height( t.right ) ) > 1 ||
                    height( t.left ) != hl || height( t.right ) != hr )
                System.out.println( "OOPS!!" );
        }

        return height( t );
    }


    /**
     * Internal method to insert into a subtree.
     * @param x the item to insert.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private AvlNode<AnyType> insert( AnyType x, AvlNode<AnyType> t ) {
	    if(t == null){
            return new AvlNode<>(x, null, null);
        }

        int compare = x.compareTo( t.element );

        if (compare< 0){
            t.left = insert( x, t.left );
        }
        else if (compare > 0){
            t.right = insert( x, t.right );
        }
        else {}

        return balance(t);

    }

    /**
     * Internal method to find the smallest item in a subtree.
     * @param t the node that roots the tree.
     * @return node containing the smallest item.
     */
    private AvlNode<AnyType> findMin( AvlNode<AnyType> t ) {
	    // returns furthest number left
        while (t.left != null) {
            t = t.left;
        }
        return t;
    }

    /**
     * Internal method to find the largest item in a subtree.
     * @param t the node that roots the tree.
     * @return node containing the largest item.
     */
    private AvlNode<AnyType> findMax( AvlNode<AnyType> t ) {
        //returns furthest number right
        while (t.right != null) {
            t = t.right;
        }
        return t;
    }

    /**
     * Internal method to find an item in a subtree.
     * @param x is item to search for.
     * @param t the node that roots the tree.
     * @return true if x is found in subtree.
     */
    private boolean contains( AnyType x, AvlNode<AnyType> t ) {
	// FINISH ME
        //if tree is empty return false
        if (t == null){
            return false;
        }
        //compare our key to the element of the node we are on
        int compare = x.compareTo( t.element );
        //if x is smaller than the node we are on
        //We recursively run the program with the same key and the starting node to our left
        if (compare < 0){
            return contains( x, t.left );
        }
        //if x is greater than the node we are on
        //Recursivly runt he program with the same key and starting node on the right
        else if (compare > 0){
            return contains( x, t.right );
        }
        else {
            return true;
        }
    }

    /**
     * Internal method to print a subtree in (sorted) order.
     * @param t the node that roots the tree.
     */
    private void printTree( AvlNode<AnyType> t ) {
        if (t == null){
            return;
        }

        //in order iteration for the print should have it printed in sorted order
        printTree(t.left);
        System.out.println(t.element + " ");
        printTree(t.right);

    }

    /**
     * Return the height of node t, or -1, if null.
     */
    private int height( AvlNode<AnyType> t ) {
        return t == null ? -1 : t.height;
    }

    /**
     * Rotate binary tree node with left child.
     * For AVL trees, this is a single rotation for case 1.
     * Update heights, then return new root.
     */
    private AvlNode<AnyType> rotateWithLeftChild( AvlNode<AnyType> k2 ) {
        AvlNode<AnyType> k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;

        k2.height = Math.max( height( k2.left ), height( k2.right ) )+1;
        k1.height = Math.max( height( k1.left ), k2.height )+1;

        return k1;
    }

    /**
     * Rotate binary tree node with right child.
     * For AVL trees, this is a single rotation for case 4.
     * Update heights, then return new root.
     */
    private AvlNode<AnyType> rotateWithRightChild( AvlNode<AnyType> k1 ) {
	    //inverted the rotate with left child formula, I think this should work after looking at insertions on paper
        AvlNode<AnyType> k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;

        k2.height = Math.max( height( k2.left ), height( k2.right ) )+1;
        k1.height = Math.max( height( k1.left ), k2.height )+1;

        return k2;
    }

    /**
     * Double rotate binary tree node: first left child
     * with its right child; then node k3 with new left child.
     * For AVL trees, this is a double rotation for case 2.
     * Update heights, then return new root.
     */
    private AvlNode<AnyType> doubleWithLeftChild( AvlNode<AnyType> k3 ) {
	  k3.left = rotateWithRightChild( k3.left );
      return rotateWithLeftChild(k3);
    }

    /**
     * Double rotate binary tree node: first right child
     * with its left child; then node k1 with new right child.
     * For AVL trees, this is a double rotation for case 3.
     * Update heights, then return new root.
     */
    private AvlNode<AnyType> doubleWithRightChild( AvlNode<AnyType> k1 ) {
	    k1.right = rotateWithLeftChild(k1.right);
        return rotateWithRightChild(k1);
    }

    private static class AvlNode<AnyType> {
        // Constructors
        AvlNode( AnyType theElement ) {
            this( theElement, null, null );
        }

        AvlNode( AnyType theElement, AvlNode<AnyType> lt, AvlNode<AnyType> rt ) {
            element  = theElement;
            left     = lt;
            right    = rt;
            height   = 0;
        }

        AnyType           element;      // The data in the node
        AvlNode<AnyType>  left;         // Left child
        AvlNode<AnyType>  right;        // Right child
        int               height;       // Height
    }

    /** The tree root. */
    private AvlNode<AnyType> root;
}

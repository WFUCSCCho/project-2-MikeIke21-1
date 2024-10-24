/*
@file: BST.java
@description: This creates a generic Binary search tree class that uses a node to fill, delete and print our BST
@author: Michael Iaccarino
@date: September 26, 2024
 */


import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

class BST <E extends Comparable<E>> {
    private Node<E> root;
    private player playerRoot;
    private int nodeCount; //number of nodes in nodecount

    // Implement the constructor

    public BST() {
        root = null;
        nodeCount = 0;
    }


    // Implement the clear method
    public void clear() {
        root = null;
        nodeCount = 0;
    }

    public boolean isEmpty() {return root == null;}

    // Implement the size method
    public int size() {
        return nodeCount;
    }

    //findHelp
    public E findhelp(Node<E> root, E key) {
        if (root == null) {
            return null;
        }
        if (root.value().compareTo(key) > 0) {
            return findhelp(root.left(), key);
        } else if (root.value().compareTo(key) == 0) {
            return root.value();
        } else {
            return findhelp(root.right(), key);
        }
    }


    //insert
    //add iterator
    private Node<E> insertHelp(Node<E> root, E key){
        if (root == null) {
            nodeCount++;
            return new Node<E>(key, null, null);
        }
        else if (root.value().compareTo(key) > 0) {
            root.setLeft(insertHelp(root.left(), key));
        } else {
            root.setRight(insertHelp(root.right(), key));
        }
        return root;

    }

    public void insert(E key) {
        root = insertHelp(root, key);
        nodeCount++;
    }


    // Implement the remove method
    private Node<E> removeHelp(Node<E> root, E key) {
        if (root == null) {
            return null;
        }
        if (root.value().compareTo(key) > 0) {
            root.setLeft(removeHelp(root.left(), key));
        } else if (root.value().compareTo(key) < 0) {
            root.setRight(removeHelp(root.right(), key));
        } else {
            if (root.left() == null) {
                return root.right();
            } else if (root.right() == null) {
                return root.left();
            } else {
                Node<E> temp = getMax(root.left());
                root.setValue(temp.value());
                root.setLeft(deleteMax(root.left()));

            }
        }
        return root;


    }


    public E remove(E key){
        E temp = findhelp(root, key);
        if (temp != null) {
            root = removeHelp(root, key);
            nodeCount --;
        }
        return temp;
    }



    // Helper method to find the rightmost node (max)
    public Node<E> getMax(Node<E> node) {
        while (node.right() != null) {
            node = node.right();
        }
        return node;
    }

    // Delete the maximum value in the tree

    public Node<E> deleteMax(Node<E> node) {
        // If there's no right child, this is the max node
        if (node.right() == null) {
            return node.left();  // Replace the node with its left child (can be null)
        }

        // Otherwise, go right to find the max
        node.setRight(deleteMax(node.right()));
        return node;
    }


    // Implement the search method
    public E find(E key) {
        return findhelp(root, key);
    }

    public String inOrderToString(Node<E> node) {
        if (node == null) {
            return "";  // Base case: return empty string for null nodes
        }

        // Recursively traverse the left subtree, the root, and then the right subtree
        String left = inOrderToString(node.left());
        String right = inOrderToString(node.right());

        // Concatenate the results (add a space after each value)
        return (left + (left.isEmpty() ? "" : " ") + node.value() + (right.isEmpty() ? "" : " ") + right).trim();
    }

    public String print() {
        return inOrderToString(root);
    }


// Implement the iterator method


    // Implement the BSTIterator class
    public class BSTIterator implements Iterator<E> {

        private Stack<Node<E>> stack = new Stack<Node<E>>();

        public BSTIterator(Node<E> root) {
            pushLeft(root);
        }

        public void pushLeft(Node<E> node) {
            while (node != null) {
                stack.push(node);
                node = node.left();
            }
        }

        public void inorder(){
            inOrderHelper(root);
        }

        public void inOrderHelper(Node<E> x){
            if (x.value() == null) {
                return;
            }
            inOrderHelper(x.left());
            System.out.print(x.value() + " ");
            inOrderHelper(x.right());
        }

        public void inOrderIterator(){
            if (root != null) {
                stack = new Stack<Node<E>>();
                pushLeft(root);
            }
        }


        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public E next() {
            if (!stack.isEmpty()) {
                Node<E> x = stack.peek();
                stack.pop();

                if (x.right() != null) {
                    pushLeft(x.right());
                }

                return x.value();
            }
            throw new NoSuchElementException();
        }
    }


}
/**
 * Represents a Red-Black Tree.
 * @author Dimitar Dimitrov and Viola Yasuda
 * @version 12/2/2021
 */
public class RedBlackTree {
    private Node root;

    /**
     * Represents a node in the red-black tree.
     */
    public class Node {
        String key;
        Node parent;
        Node leftChild;
        Node rightChild;
        boolean isRed;
        int color;

        /**
         * Constructs a node.
         * @param data the data value the node holds.
         */
        public Node(String data){
            this.key = data;
            leftChild = null;
            rightChild = null;
        }

        /**
         * Compares this node to another node for ordering.
         * @param n the other node
         * @return a negative value if this < that, a positive value if this > that, and 0 if they are the same node
         */
        public int compareTo(Node n){ 	//this < that  <0
            return key.compareTo(n.key);  	//this > that  >0
        }

        /**
         * Determines if the node is a leaf.
         * @return true if the node is a leaf, false otherwise
         */
        public boolean isLeaf(){
            if (this.equals(root) && this.leftChild == null && this.rightChild == null) return true;
            if (this.equals(root)) return false;
            if (this.leftChild == null && this.rightChild == null){
                return true;
            }
            return false;
        }
    }

    /**
     * Determines if a node in the tree is a leaf.
     * @param n the node in question
     * @return true if the node is a leaf, false otherwise
     */
    public boolean isLeaf(Node n){
        if (n.equals(root) && n.leftChild == null && n.rightChild == null) return true;
        if (n.equals(root)) return false;
        if (n.leftChild == null && n.rightChild == null){
            return true;
        }
        return false;
    }

    /**
     * Represents a visitor tat will visit each node in the tree.
     */
    public interface Visitor{
        /**
         * This method is called at each node.
         * @param n the visited node
         */
        void visit(Node n);
    }

    /**
     * Visits a node.
     * @param n the node to visit
     */
    public void visit(Node n){
        System.out.println(n.key);
    }

    /**
     * Prints the tree in preorder.
     */
    public void printTree(){  //preorder: visit, go left, go right
        Node currentNode = root;
        printTree(currentNode);
    }

    /**
     * Prints the subtree under a node in preorder.
     * @param node the node that is at the root of the subtree to print
     */
    public void printTree(Node node){
        System.out.print(node.key);
        if (node.isLeaf()){
            return;
        }
        printTree(node.leftChild);
        printTree(node.rightChild);
    }

    // place a new node in the RB tree with data the parameter and color it red.
    /**
     *
     * @param data
     */
    public void addNode(String data) {  	//this < that  <0.  this > that  >0
        RedBlackTree.Node nodeToAdd = new RedBlackTree.Node(data);
        if (root == null) {
            root = nodeToAdd;
        }
        else {
            RedBlackTree.Node currentNode = root;
            while (currentNode.compareTo(nodeToAdd) != 0) {
                if (currentNode.compareTo(nodeToAdd) < 0) {
                    if (currentNode.rightChild == null) {
                        currentNode.rightChild = nodeToAdd;
                    }
                    currentNode = currentNode.rightChild;
                }
                else if (currentNode.compareTo(nodeToAdd) > 0) {
                    if (currentNode.leftChild == null) {
                        currentNode.leftChild = nodeToAdd;
                    }
                    currentNode = currentNode.leftChild;
                }
            }
        }
    }

    /**
     * Inserts a data value into the tree.
     * @param data the value to be inserted
     */
    public void insert(String data){
        addNode(data);
    }

    /**
     * Gets the node in the tree with a specified string.
     * @param k the string to be searched for
     * @return the node with the string
     */
    public Node lookup(String k){
        RedBlackTree.Node currentNode = root;
        while (!(currentNode == null || currentNode.key.equals(k))) {
            if (currentNode.key.compareTo(k) < 0) {
                currentNode = currentNode.rightChild;
            }
            else if (currentNode.key.compareTo(k) > 0) {
                currentNode = currentNode.rightChild;
            }
        }
        return currentNode;
    }

    /**
     * Gets the sibling of a node.
     * @param n the node whose sibling should be retrieved
     * @return the sibling of the node
     */
    public Node getSibling(Node n){
        if (isLeftChild(n.parent, n)) {
            return n.parent.rightChild;
        }
        else {
            return n.parent.leftChild;
        }
    }

    /**
     * Gets the aunt of a node.
     * @param n the node whose aunt should be retrieved.
     * @return the aunt of the node
     */
    public Node getAunt(Node n){
        return getSibling(n.parent);
    }

    /**
     * Gets the grandparent of a node.
     * @param n the node whose grandparent should be retrieved
     * @return the grandparent of the node
     */
    public Node getGrandparent(Node n){
        return n.parent.parent;
    }

    /**
     *
     * @param n
     */
    public void rotateLeft(Node n){
        Node parent = n.parent;
        //     5  (n)
        //       \
        //         6 (rightChild)
        //        /
        //       7  (rLeftChild)
        //right child node of the head
        Node rightChild = n.rightChild;
        //left node of the right child
        Node rLeftChild = rightChild.leftChild;

        //         6 (rightChild)
        //        /
        //       5  (n)
        //set the right child's left node to the head (n)
        rightChild.leftChild = n;

        //     6  (rightChild)
        //    / \
        //  5     7  (rLeftChild)
        //set the head's right child to the left child
        rightChild.rightChild = rLeftChild;

        //set the head to the right child
        rightChild.parent = parent;

        //if the rLeftChild is not null, set the parent to n
        if(rLeftChild != null) {
            rLeftChild.parent = rightChild;
        }
    }

    /**
     *
     * @param n
     */
    public void rotateRight(Node n){
        //
    }

    /**
     *
     * @param current
     */
    public void fixTree(Node current) {
        //
    }

    /**
     * Determines if a node is empty.
     * @param n the node in question.
     * @return true if the node does not contain a key, false otherwise.
     */
    public boolean isEmpty(Node n){
        if (n.key == null){
            return true;
        }
        return false;
    }

    /**
     * Determines if the node is the left child of its parent.
     * @param parent the parent node
     * @param child the child node
     * @return true if the child is the left child, false if the child is the right child
     */
    public boolean isLeftChild(Node parent, Node child) {
        if (child.compareTo(parent) < 0 ) {//child is less than parent
            return true;
        }
        return false;
    }

    /**
     * Starts the process of visiting all the nodes in the tree in pre-order.
     * @param v the visitor to visit the nodes
     */
    public void preOrderVisit(Visitor v) {
        preOrderVisit(root, v);
    }

    /**
     * Visits all the nodes in the subtree in pre-order.
     * @param n the node at the root of the subtree
     * @param v the visitor of the subtree
     */
    private static void preOrderVisit(Node n, Visitor v) {
        if (n == null) {
            return;
        }
        v.visit(n);
        preOrderVisit(n.leftChild, v);
        preOrderVisit(n.rightChild, v);
    }
}


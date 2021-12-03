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
            return this.leftChild == null && this.rightChild == null;
        }

        /**
         * Returns a string with the node's information (for debugging purposes).
         * @return a string with the node's state
         */
        @Override
        public String toString() {
            return "Node{" +
                    "key='" + key + '\'' +
                    ", parent=" + parent +
                    ", leftChild=" + leftChild +
                    ", rightChild=" + rightChild +
                    ", isRed=" + isRed +
                    ", color=" + color +
                    '}';
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
        return n.leftChild == null && n.rightChild == null;
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

    /**
     * Places a new node in the RB tree with data the parameter and color it red.
     * @param data the data in the node to add to the tree
     */
    public void addNode(String data) {  	//this < that  <0.  this > that  >0
        // TODO: check for correctness
        RedBlackTree.Node nodeToAdd = new RedBlackTree.Node(data);
        if (root == null) {
            root = nodeToAdd;
            root.parent = new Node("");
        }
        else {
            RedBlackTree.Node currentNode = root;
            while (currentNode.compareTo(nodeToAdd) != 0) {
                if (currentNode.compareTo(nodeToAdd) < 0) {
                    if (currentNode.rightChild == null) {
                        currentNode.rightChild = nodeToAdd;
                        nodeToAdd.parent = currentNode;
                    }
                    currentNode = currentNode.rightChild;
                }
                else if (currentNode.compareTo(nodeToAdd) > 0) {
                    if (currentNode.leftChild == null) {
                        currentNode.leftChild = nodeToAdd;
                        nodeToAdd.parent = currentNode;
                    }
                    currentNode = currentNode.leftChild;
                }
            }
        }
        fixTree(nodeToAdd);
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
        // TODO: check for correctness
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
        // TODO: check for correctness
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
        // TODO: check for correctness
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
     * Rotates a subtree to the left.
     * @param n the node whose subtree will be rotated to the left
     */
    public void rotateLeft(Node n){
        // TODO: check for correctness
        Node parent = n.parent;
        //     5  (n) t
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
     * Rotates a subtree to the right
     * @param n the node whose subtree will be rotated to the right
     */
    public void rotateRight(Node n){
        // TODO: write method definition
    }

    /**
     * Recursively traverses the tree to make it a Red Black tree.
     * @param current the node to start traversing at
     */
    public void fixTree(Node current) {
        // TODO: write method definition
        //current is root node, make black and quit
        if(current.compareTo(root) == 0) {
            current.color = 1;
            current.isRed = false;
        }
        //if the current node is red and parent node is red, unbalanced tree
        else if(current.isRed && current.parent.isRed) {
            // If the aunt node is empty or black, then there are four sub cases that you have to process.
            Node auntNode = getAunt(current);
            Node grandParentNode = getGrandparent(current);
            if (auntNode == null || !auntNode.isRed) {
                //A) grandparent –parent(is left child)— current (is right child) case.
                //Solution: rotate the parent left and then continue recursively fixing the tree starting with the original parent.
                if(isLeftChild(grandParentNode, current.parent)) {
                    rotateLeft(current.parent);
                    fixTree(current.parent);
                }
                //B) grandparent –parent (is right child)— current (is left child) case.
                //Solution: rotate the parent right and then continue recursively fixing the tree starting with the original parent.
                else if(!isLeftChild(grandParentNode, current.parent)) {
                    rotateRight(current.parent);
                    fixTree(current.parent);
                }
                //C) grandparent –parent (is left child)— current (is left child) case.
                else if (isLeftChild(grandParentNode, current.parent) && isLeftChild(current.parent, current)) {
                    //Solution: make the parent black, make the grandparent red, rotate the grandparent to the right and quit, tree is balanced.
                    current.parent.color = 1;
                    current.parent.isRed = false;
                    grandParentNode.color = 0;
                    grandParentNode.isRed = true;
                    rotateRight(grandParentNode);
                }
                //D) grandparent –parent (is right child)— current (is right child) case.
                else if (!isLeftChild(grandParentNode, current.parent) && !isLeftChild(current.parent, current)) {
                    //Solution: make the parent black, make the grandparent red, rotate the grandparent to the left, quit tree is balanced.
                    current.parent.color = 1;
                    current.parent.isRed = false;
                    grandParentNode.color = 0;
                    grandParentNode.isRed = true;
                    rotateLeft(grandParentNode);
                }
            }
            // II. Else if the aunt is red,
            else {
                // then make the parent black,
                current.parent.color = 1;
                current.parent.isRed = false;
                // make the aunt black,
                auntNode.color = 1;
                auntNode.isRed = false;
                // make the grandparent red
                grandParentNode.color = 0;
                grandParentNode.isRed = true;
                // and continue recursively fix up the tree starting with the grandparent.
                fixTree(grandParentNode);
            }
        }
    }

    /**
     * Determines if a node is empty.
     * @param n the node in question.
     * @return true if the node does not contain a key, false otherwise.
     */
    public boolean isEmpty(Node n){
        return n.key == null;
    }

    /**
     * Determines if the node is the left child of its parent.
     * @param parent the parent node
     * @param child the child node
     * @return true if the child is the left child, false if the child is the right child
     */
    public boolean isLeftChild(Node parent, Node child) {
        //child is less than parent
        return child.compareTo(parent) < 0;
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


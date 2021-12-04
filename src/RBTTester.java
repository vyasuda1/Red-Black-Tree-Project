import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This class tests our red-black tree.
 * @author Dimitar Dimitrov and Viola Yasuda
 * @version 12/3/2021
 */
public class RBTTester {
    RedBlackTree redBlackTree;

    @Before
    public void initializeRedBlackTree() {
        redBlackTree = new RedBlackTree();
        redBlackTree.insert("A");
        redBlackTree.insert("Z");
        redBlackTree.insert("M");
        redBlackTree.insert("F");
        redBlackTree.insert("S");
    }

    /**
     * Creates a dictionary using a red-black tree and looks up words from a poem in the dictionary to check for
     * spelling. Tests if the words in the dictionary are found and if the words not in the dictionary return null.
     */
    @Test
    public void testDictionary() {
        //count the time to create the dictionary
        long startTime = 0, endTime = 0, duration;
        RedBlackTree rbt = new RedBlackTree();
        File file = new File("dictionary.txt");
        try {
            Scanner in = new Scanner(file);
            startTime = System.currentTimeMillis();
            while (in.hasNextLine()) {
                rbt.insert(in.nextLine());
            }
            endTime = System.currentTimeMillis();
            in.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        duration = endTime - startTime;

        System.out.println("Dictionary creation time: " + duration + " ms\n");

        //count the time for spell checking
        File poemFile = new File("poem.txt");
        String[] expectedKeys = new String[]{"there", "once", "NOT FOUND", "NOT FOUND", "NOT FOUND", "from",
                "NOT FOUND", "NOT FOUND", "kept", "NOT FOUND", "NOT FOUND", "cash", "NOT FOUND", "NOT FOUND", "bucket",
                "NOT FOUND", "NOT FOUND", "daughter", "named", "NOT FOUND", "NOT FOUND", "away", "with", "NOT FOUND",
                "NOT FOUND", "NOT FOUND", "NOT FOUND", "NOT FOUND", "NOT FOUND", "bucket", "NOT FOUND"};
        String[] actualKeys = new String[expectedKeys.length];
        try {
            Scanner in = new Scanner(poemFile);
            int i = 0;
            while(in.hasNext()) {
                String word = in.next().toLowerCase().trim();
                if (!word.substring(word.length() - 1).matches("[a-zA-Z]+")) {
                    word = word.substring(0, word.length() - 1);
                }
                startTime = System.currentTimeMillis();
                RedBlackTree.Node node = rbt.lookup(word);
                endTime = System.currentTimeMillis();
                duration = endTime - startTime;
                System.out.print("Lookup time for \"" + word + "\": " + duration + " ms");
                if (node == null) {
                    System.out.println(" (NOT FOUND)");
                    actualKeys[i] = "NOT FOUND";
                }
                else {
                    System.out.println();
                    actualKeys[i] = node.key;
                }
                i++;
            }
            assertArrayEquals(expectedKeys, actualKeys);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tests the addNode method of the RedBlackTree class.
     */
    @Test
    public void testAddNode() {
        redBlackTree.addNode("1");
        redBlackTree.addNode("a");
        RedBlackTree.Node node1 = redBlackTree.lookup("1");
        RedBlackTree.Node nodea = redBlackTree.lookup("a");
        assertEquals("1", node1.key);
        assertEquals("A", node1.parent.key);
        assertNull(node1.leftChild);
        assertNull(node1.rightChild);
        assertTrue(node1.isRed);
        assertEquals(0, node1.color);
        assertEquals("a", nodea.key);
        assertEquals("Z", nodea.parent.key);
        assertNull(nodea.leftChild);
        assertNull(nodea.rightChild);
        assertTrue(nodea.isRed);
        assertEquals(0, nodea.color);
    }

    /**
     * Tests the lookup method of the RedBlackTree class.
     */
    @Test
    public void testLookup() {
        RedBlackTree.Node mNode = redBlackTree.lookup("M");
        RedBlackTree.Node aNode = redBlackTree.lookup("A");
        RedBlackTree.Node sNode = redBlackTree.lookup("S");
        assertEquals("M", mNode.key);
        assertEquals("", mNode.parent.key);
        assertEquals("A", mNode.leftChild.key);
        assertEquals("Z", mNode.rightChild.key);
        assertFalse(mNode.isRed);
        assertEquals(1, mNode.color);

        assertEquals("A", aNode.key);
        assertEquals("M", aNode.parent.key);
        assertNull(aNode.leftChild);
        assertEquals("F", aNode.rightChild.key);
        assertFalse(aNode.isRed);
        assertEquals(1, aNode.color);

        assertEquals("S", sNode.key);
        assertEquals("Z", sNode.parent.key);
        assertNull(sNode.leftChild);
        assertNull(sNode.rightChild);
        assertTrue(sNode.isRed);
        assertEquals(0, sNode.color);
    }

    /**
     * Tests the getSibling method of the RedBlackTree class.
     */
    @Test
    public void testGetSibling() {
        RedBlackTree.Node siblingToA = redBlackTree.getSibling(redBlackTree.lookup("A"));
        RedBlackTree.Node siblingToZ = redBlackTree.getSibling(redBlackTree.lookup("Z"));
        RedBlackTree.Node siblingToF = redBlackTree.getSibling(redBlackTree.lookup("F"));
        RedBlackTree.Node siblingToS = redBlackTree.getSibling(redBlackTree.lookup("S"));
        assertEquals("Z", siblingToA.key);
        assertEquals("A", siblingToZ.key);
        assertNull(siblingToF);
        assertNull(siblingToS);
    }

    /**
     * Tests the getAunt method of the RedBlackTree class.
     */
    @Test
    public void testGetAunt() {
        RedBlackTree.Node auntToF = redBlackTree.getAunt(redBlackTree.lookup("F"));
        RedBlackTree.Node auntToS = redBlackTree.getAunt(redBlackTree.lookup("S"));
        assertEquals("Z", auntToF.key);
        assertEquals("A", auntToS.key);
    }

    /**
     * Tests the RedBlackTree class. Provided by Professor Potika.
     */
	@Test
	public void test() {
		RedBlackTree rbt = new RedBlackTree();
        rbt.insert("D");
        rbt.insert("B");
        rbt.insert("A");
        rbt.insert("C");
        rbt.insert("F");
        rbt.insert("E");
        rbt.insert("H");
        rbt.insert("G");
        rbt.insert("I");
        rbt.insert("J");
        assertEquals("DBACFEHGIJ", makeString(rbt));
        String str= """
                Color: 1, Key:D Parent:\s
                Color: 1, Key:B Parent: D
                Color: 1, Key:A Parent: B
                Color: 1, Key:C Parent: B
                Color: 1, Key:F Parent: D
                Color: 1, Key:E Parent: F
                Color: 0, Key:H Parent: F
                Color: 1, Key:G Parent: H
                Color: 1, Key:I Parent: H
                Color: 0, Key:J Parent: I
                """;
		assertEquals(str, makeStringDetails(rbt));
    }

    /**
     * Makes a string with the keys in the red-black tree.
     * @param t the red-black tree
     * @return a string with the keys in the tree
     */
    public static String makeString(RedBlackTree t) {
       class MyVisitor implements RedBlackTree.Visitor {
          String result = "";
          public void visit(RedBlackTree.Node n)
          {
             result = result + n.key;
          }
       }
        MyVisitor v = new MyVisitor();
       t.preOrderVisit(v);
       return v.result;
    }

    /**
     * Makes string details of the red-black tree.
     * @param t the red-black tree
     * @return a string with information about the nodes in the tree
     */
    public static String makeStringDetails(RedBlackTree t) {
	    class MyVisitor implements RedBlackTree.Visitor {
	        String result = "";
	        public void visit(RedBlackTree.Node n) {
	            if(!(n.key).equals(""))
	                result = result +"Color: "+n.color+", Key:"+n.key+" Parent: "+n.parent.key+"\n";
	        }
	    }
        MyVisitor v = new MyVisitor();
	    t.preOrderVisit(v);
	    return v.result;
    }
 }
  

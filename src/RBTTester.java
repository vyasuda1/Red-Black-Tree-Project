
import static org.junit.Assert.*;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class RBTTester {

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
                rbt.insert(in.nextLine().trim());
            }
            endTime = System.currentTimeMillis();
            in.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        duration = endTime - startTime;

        System.out.println("Dictionary creation time: " + duration + " ms");

        //count the time for spell checking
        File poemFile = new File("poem.txt");
        try {
            Scanner in = new Scanner(poemFile);
            startTime = System.currentTimeMillis();
            while(in.hasNext()) {
                String word = in.next().toLowerCase().trim();
                if (!word.substring(word.length() - 1, word.length()).matches("[a-zA-Z]+")) {
                    word = word.substring(0, word.length() - 1);
                }
                RedBlackTree.Node node = rbt.lookup(word);
                if (node == null) {
                    System.out.println("Word not found: " + word);
                }
            }
            endTime = System.currentTimeMillis();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        duration = endTime - startTime;
        System.out.println("Spell check time: " + duration + " ms");
    }

	@Test
    //Test the Red Black Tree
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
        String str=     "Color: 1, Key:D Parent: \n"+
                        "Color: 1, Key:B Parent: D\n"+
                        "Color: 1, Key:A Parent: B\n"+
                        "Color: 1, Key:C Parent: B\n"+
                        "Color: 1, Key:F Parent: D\n"+
                        "Color: 1, Key:E Parent: F\n"+
                        "Color: 0, Key:H Parent: F\n"+
                        "Color: 1, Key:G Parent: H\n"+
                        "Color: 1, Key:I Parent: H\n"+
                        "Color: 0, Key:J Parent: I\n";
		assertEquals(str, makeStringDetails(rbt));
    }
    
    //add tester for spell checker
    
    public static String makeString(RedBlackTree t)
    {
       class MyVisitor implements RedBlackTree.Visitor {
          String result = "";
          public void visit(RedBlackTree.Node n)
          {
             result = result + n.key;
          }
       };
       MyVisitor v = new MyVisitor();
       t.preOrderVisit(v);
       return v.result;
    }

    public static String makeStringDetails(RedBlackTree t) {
    	{
    	       class MyVisitor implements RedBlackTree.Visitor {
    	          String result = "";
    	          public void visit(RedBlackTree.Node n)
    	          {
    	        	  if(!(n.key).equals(""))
    	        		  result = result +"Color: "+n.color+", Key:"+n.key+" Parent: "+n.parent.key+"\n";
    	             
    	          }
    	       };
    	       MyVisitor v = new MyVisitor();
    	       t.preOrderVisit(v);
    	       return v.result;
    	 }
    }
 }
  

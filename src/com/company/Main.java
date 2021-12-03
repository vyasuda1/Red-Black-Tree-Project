package com.company;
public class Main {

    public static void main(String[] args) {
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
        //Assert.assertEquals("DBACFEHGIJ", makeString(rbt));
        rbt.printTree();
        System.out.print("\nExpected: ");
        System.out.println("DBACFEHGIJ");
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
        //assertEquals(str, makeStringDetails(rbt));
    }
}

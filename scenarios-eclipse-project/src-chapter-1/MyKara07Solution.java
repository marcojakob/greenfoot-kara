
/**
 * MyKara is a subclass of Kara. Therefore, it inherits all methods of Kara: <p>
 * 
 * <i>MyKara ist eine Unterklasse von Kara. Sie erbt damit alle Methoden der Klasse Kara:</i> <p>
 * 
 * Actions:     move(), turnLeft(), turnRight(), putLeaf(), removeLeaf() <b>
 * Sensors:     onLeaf(), treeFront(), treeLeft(), treeRight(), mushroomFront()
 */
public class MyKara07Solution extends Kara {
	
    /**
     * In the 'act()' method you can write your program for Kara <br>
     * <i>In der Methode 'act()' koennen die Befehle fuer Kara programmiert werden</i>
     */
	public void act() {
        move();
        
        turnLeft(); 
        move();
        turnRight(); 
        move();
        move();
        turnRight();
        move();
        turnLeft();

        turnLeft(); 
        move();
        turnRight();
        move();
        move();
        turnRight();
        move();
        turnLeft();
        
        move();

        turnLeft(); 
        move();
        turnRight();
        move();
        move();
        turnRight();
        move();
        turnLeft();
        
        removeLeaf();
        
        stop();
	}
}

/**
 * MyKara is a subclass of Kara. Therefore, it inherits all methods of Kara: <p>
 * 
 * <i>MyKara ist eine Unterklasse von Kara. Sie erbt damit alle Methoden der Klasse Kara:</i> <p>
 * 
 * Actions:     move(), turnLeft(), turnRight(), putLeaf(), removeLeaf() <b>
 * Sensors:     onLeaf(), treeFront(), treeLeft(), treeRight(), mushroomFront()
 */
public class MyKara20Solution extends Kara {
	
    /**
     * In the 'act()' method you can write your program for Kara <br>
     * <i>In der Methode 'act()' koennen die Befehle fuer Kara programmiert werden</i>
     */
	public void act() {
		makeOneStep();
	}
    
	public void makeOneStep() {
		if (!treeRight()) {
			// no tree right --> go right
			turnRight();
			move();
		} else {
			// there is a tree right
			if (!treeFront()) {
				// no tree in front --> move
				move();
			} else {
				// trees right and front
				if (!treeLeft()) {
					// no tree left --> go left
					turnLeft();
					move();
				} else {
					// trees right, front and left: dead end
					turnLeft();
					turnLeft();
					move();
				}
			}
		}
	}
}


/**
 * MyKara is a subclass of Kara. Therefore, it inherits all methods of Kara: <p>
 * 
 * <i>MyKara ist eine Unterklasse von Kara. Sie erbt damit alle Methoden der Klasse Kara:</i> <p>
 * 
 * Actions:     move(), turnLeft(), turnRight(), putLeaf(), removeLeaf() <b>
 * Sensors:     onLeaf(), treeFront(), treeLeft(), treeRight(), mushroomFront()
 */
public class MyKara23Solution extends Kara {
	
    boolean goingRight = true;
    boolean finished = false;
    boolean havePutLeaf = false;

    /**
     * In the 'act()' method you can write your program for Kara <br>
     * <i>In der Methode 'act()' koennen die Befehle fuer Kara programmiert werden</i>
     */
	public void act() {
		// process first row
		processRow();

		while (!finished) {
			if (goingRight) {
				if (!treeRight()) {
					turnRight();
					move();
					turnRight();
					// we have turned and now go left
					goingRight = false;

					processRow();
				} else {
					// we are in the bottom right corner
					finished = true;
				}
			} else {
				if (!treeLeft()) {
					turnLeft();
					move();
					turnLeft();
					// we have turned and now go right
					goingRight = true;

					processRow();
				} else {
					// we are in the bottom left corner
					finished = true;
				}
			}
		}
		
		stop();
	}

	public void processRow() {
		while (!treeFront()) {
			processCell();
			move();
		}

		// process the last cell
		processCell();
	}

	public void processCell() {
		if (!havePutLeaf) {
			putLeaf();
		}

		havePutLeaf = !havePutLeaf;
	}
}
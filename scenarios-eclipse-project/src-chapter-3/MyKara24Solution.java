
/**
 * MyKara is a subclass of Kara. Therefore, it inherits all methods of Kara: <p>
 * 
 * <i>MyKara ist eine Unterklasse von Kara. Sie erbt damit alle Methoden der Klasse Kara:</i> <p>
 * 
 * Actions:     move(), turnLeft(), turnRight(), putLeaf(), removeLeaf() <b>
 * Sensors:     onLeaf(), treeFront(), treeLeft(), treeRight(), mushroomFront()
 */
public class MyKara24Solution extends Kara {
	
	int longestRow = 0;

    /**
     * In the 'act()' method you can write your program for Kara <br>
     * <i>In der Methode 'act()' koennen die Befehle fuer Kara programmiert werden</i>
     */
	public void act() {
		while (!onLeaf()) {
			if (treeFront()) {
				countRow();
			} else {
				move();
			}
		}

		System.out.println("The longest tree line is " + longestRow
				+ " trees long");
		
		stop();
	}

	public void countRow() {
		int currentRow = 0;
		turnLeft();

		while (treeRight()) {
			currentRow = currentRow + 1;
			move();
		}

		// go around tree line
		turnRight();
		move();
		move();
		turnRight();

		// go back down
		int i = 0;
		while (i < currentRow) {
			move();
			i = i + 1;
		}

		turnLeft();

		// test whether the current row is longer
		if (currentRow > longestRow) {
			longestRow = currentRow;
		}
	}
}

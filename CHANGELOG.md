# Changelog

## GreenfootKara 2.1.1, 2014-10-04

* Ensure all popup dialogs (messages, file chooser, etc.) open in the Event Dispatcher Thread so it doesn't freeze the UI (see Issue #1).
* Update to Greenfoot 2.4.0.
* Specify last instantiated world in `greenfot.project` for scenarios using `GameScreen` class as world.


## GreenfootKara 2.1.0, 2013-03-24

* The master for all tutorials/worksheets is now in HTML instead of Word. The HTML is on the code.makery.ch website. The converted worksheets can still be downloaded as Word files from that website. 
* Remove Word and PowerPoint files from GitHub repository.
* Change numbering of scenarios/tasks. Now we have 1.01 for the first scenario of chapter one, 1.02 for the second scenario, etc.


## GreenfootKara 2.0.2, 2012-10-25

* Simpler solutions to scenarios 22 and 23.


## GreenfootKara 2.0.1, 2012-10-22

* Improved error handling if world setup file could not be loaded.


## GreenfootKara 2.0, 2012-10-01

* Major changes to be able to work with an eclipse project to generate
	Greenfoot scenarios.
* Created a new class for every scenario called WorldSetup that reades the world setup
	(all character positions) from a world setup text file.
* Added method to save the world setup to either the console or a text file (in ASCII).
* Kara now has a stop() method to stop the simulation cycle. Now we can always use the
	run button. This gives us the ability to always use the run-Button and still 
	have a program that terminates. Greenfoot 2.1.2 has a problem with the act-button
	so that the first act() is executed after a compile, the delay() method is ignored
	and thus Kara moves very fast to the end.
* Kara Sokoban contains now an example of alternative images (skin). See Kara Sokoban
	Solution.
* Corrected little bug in Exercise 12.


## GreenfootKara 1.3, 2012-07-04

* English and German:
 * Most code comments that usually only teachers read are in English
 * Javadoc for Kara and MyKara are in English and German
 * Handouts in English and German
	
* Changed the name of 'Wiese' to 'KaraWorld'	

* Improved handling of adding and dragging Actors with the mouse.
  Less error messages when Actors are placed where they should not.
  Instead they are just removed again.
  
* Use JOptionPane instead of System.out: The console can be in the background
  and so students might not notice a message written to System.out. As was 
  already used in some scenarios, JOptionPane is now used as the default method
  to show warnings to the user.
  Also added an option for the user to 'Exit Program'. This will immediately exit
  the program and restart greenfoot.

* Improvements in Kara Sokoban:
 * Possibility to use Highscore when Scenario is uploaded to Greenfoot.org (via UserInfo).
 * Mechanism to detect whether we can use a file highscore or the server highscore via UserInfo.
 * Simplified adding of a new Highscore for students.
 * Fixed some minor bugs.
 * Some minor refactorings.

* Finally implemented a solution for the Scenario 'Kara 25 Push Mushroom trough Tunnel'

* Some refactorings:
 * Deleted the privat Kara method 'canMove()'. The testing if Kara can move is now done
	  inside the 'move()' method.
	
* Updated the Sheet about Teacher Information.
	

## GreenfootKara 1.2, 2012-01-04

* Reordering of scenarios. Now they are ordered by chapter instead of day.
* Some minor spelling corrections

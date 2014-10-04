package kara.greenfoot.sokoban;

import static org.junit.Assert.*;
//import static org.hamcrest.CoreMatchers.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HighscoreTest {

	Highscore highscore;
	
	@Before
	public void setUp() throws Exception {
		highscore = new Highscore(1);
	}

	@After
	public void tearDown() throws Exception {
		highscore = null;
	}

	@Test
	public void testAddHighscoreEntry_BestFirst() {
		// given
		Highscore.Entry e1 = new Highscore.Entry("aaa", 20);
		Highscore.Entry e2 = new Highscore.Entry("bbb", 30);
		Highscore.Entry e3 = new Highscore.Entry("ccc", 40);
		
		// when
		highscore.addHighscoreEntry(e1);
		highscore.addHighscoreEntry(e2);
		highscore.addHighscoreEntry(e3);
		
		// then
		assertTrue(entryEquals(highscore.getFirstEntry(), e1));
		assertTrue(entryEquals(highscore.getSecondEntry(), e2));
		assertTrue(entryEquals(highscore.getThirdEntry(), e3));
	}
	
	@Test
	public void testAddHighscoreEntry_BestLast() {
		// given
		Highscore.Entry e1 = new Highscore.Entry("aaa", 50);
		Highscore.Entry e2 = new Highscore.Entry("bbb", 40);
		Highscore.Entry e3 = new Highscore.Entry("ccc", 30);
		
		// when
		highscore.addHighscoreEntry(e1);
		highscore.addHighscoreEntry(e2);
		highscore.addHighscoreEntry(e3);
		
		// then
		assertTrue(entryEquals(highscore.getFirstEntry(), e3));
		assertTrue(entryEquals(highscore.getSecondEntry(), e2));
		assertTrue(entryEquals(highscore.getThirdEntry(), e1));
	}
	
	@Test
	public void testAddHighscoreEntry_NoEntries() {
		// given
		
		// when
		
		// then
		assertTrue(entryEquals(highscore.getFirstEntry(), Highscore.EMPTY_ENTRY));
		assertTrue(entryEquals(highscore.getSecondEntry(), Highscore.EMPTY_ENTRY));
		assertTrue(entryEquals(highscore.getThirdEntry(), Highscore.EMPTY_ENTRY));
	}
	
	@Test
	public void testAddHighscoreEntry_MoreThanThreeEntries1() {
		// given
		Highscore.Entry e1 = new Highscore.Entry("aaa", 50);
		Highscore.Entry e2 = new Highscore.Entry("bbb", 60);
		Highscore.Entry e3 = new Highscore.Entry("ccc", 70);
		Highscore.Entry e4 = new Highscore.Entry("ddd", 80);
		
		// when
		highscore.addHighscoreEntry(e1);
		highscore.addHighscoreEntry(e2);
		highscore.addHighscoreEntry(e3);
		highscore.addHighscoreEntry(e4);
		
		// then
		assertTrue(entryEquals(highscore.getFirstEntry(), e1));
		assertTrue(entryEquals(highscore.getSecondEntry(), e2));
		assertTrue(entryEquals(highscore.getThirdEntry(), e3));
	}
	
	@Test
	public void testAddHighscoreEntry_MoreThanThreeEntries2() {
		// given
		Highscore.Entry e1 = new Highscore.Entry("aaa", 50);
		Highscore.Entry e2 = new Highscore.Entry("bbb", 60);
		Highscore.Entry e3 = new Highscore.Entry("ccc", 70);
		Highscore.Entry e4 = new Highscore.Entry("ddd", 70);
		
		// when
		highscore.addHighscoreEntry(e1);
		highscore.addHighscoreEntry(e2);
		highscore.addHighscoreEntry(e3);
		highscore.addHighscoreEntry(e4);
		
		// then
		assertTrue(entryEquals(highscore.getFirstEntry(), e1));
		assertTrue(entryEquals(highscore.getSecondEntry(), e2));
		assertTrue(entryEquals(highscore.getThirdEntry(), e3));
	}
	
	@Test
	public void testAddHighscoreEntry_MoreThanThreeEntries3() {
		// given
		Highscore.Entry e1 = new Highscore.Entry("aaa", 50);
		Highscore.Entry e2 = new Highscore.Entry("bbb", 60);
		Highscore.Entry e3 = new Highscore.Entry("ccc", 70);
		Highscore.Entry e4 = new Highscore.Entry("ddd", 69);
		
		// when
		highscore.addHighscoreEntry(e1);
		highscore.addHighscoreEntry(e2);
		highscore.addHighscoreEntry(e3);
		highscore.addHighscoreEntry(e4);
		
		// then
		assertTrue(entryEquals(highscore.getFirstEntry(), e1));
		assertTrue(entryEquals(highscore.getSecondEntry(), e2));
		assertTrue(entryEquals(highscore.getThirdEntry(), e4));
	}
	
	@Test
	public void testAddHighscoreEntry_MoreThanThreeEntries4() {
		// given
		Highscore.Entry e1 = new Highscore.Entry("aaa", 50);
		Highscore.Entry e2 = new Highscore.Entry("bbb", 60);
		Highscore.Entry e3 = new Highscore.Entry("ccc", 70);
		Highscore.Entry e4 = new Highscore.Entry("ddd", 55);
		
		// when
		highscore.addHighscoreEntry(e1);
		highscore.addHighscoreEntry(e2);
		highscore.addHighscoreEntry(e3);
		highscore.addHighscoreEntry(e4);
		
		// then
		assertTrue(entryEquals(highscore.getFirstEntry(), e1));
		assertTrue(entryEquals(highscore.getSecondEntry(), e4));
		assertTrue(entryEquals(highscore.getThirdEntry(), e2));
	}
	
	@Test
	public void testAddHighscoreEntry_MoreThanThreeEntries5() {
		// given
		Highscore.Entry e1 = new Highscore.Entry("aaa", 50);
		Highscore.Entry e2 = new Highscore.Entry("bbb", 60);
		Highscore.Entry e3 = new Highscore.Entry("ccc", 70);
		Highscore.Entry e4 = new Highscore.Entry("ddd", 59);
		
		// when
		highscore.addHighscoreEntry(e1);
		highscore.addHighscoreEntry(e2);
		highscore.addHighscoreEntry(e3);
		highscore.addHighscoreEntry(e4);
		
		// then
		assertTrue(entryEquals(highscore.getFirstEntry(), e1));
		assertTrue(entryEquals(highscore.getSecondEntry(), e4));
		assertTrue(entryEquals(highscore.getThirdEntry(), e2));
	}
	
	@Test
	public void testAddHighscoreEntry_MoreThanThreeEntries6() {
		// given
		Highscore.Entry e1 = new Highscore.Entry("aaa", 50);
		Highscore.Entry e2 = new Highscore.Entry("bbb", 60);
		Highscore.Entry e3 = new Highscore.Entry("ccc", 70);
		Highscore.Entry e4 = new Highscore.Entry("ddd", 49);
		
		// when
		highscore.addHighscoreEntry(e1);
		highscore.addHighscoreEntry(e2);
		highscore.addHighscoreEntry(e3);
		highscore.addHighscoreEntry(e4);
		
		// then
		assertTrue(entryEquals(highscore.getFirstEntry(), e4));
		assertTrue(entryEquals(highscore.getSecondEntry(), e1));
		assertTrue(entryEquals(highscore.getThirdEntry(), e2));
	}
	
	@Test
	public void testAddHighscoreEntry_SamePlayerAgainWorse() {
		// given
		Highscore.Entry e1 = new Highscore.Entry("aaa", 50);
		Highscore.Entry e2 = new Highscore.Entry("bbb", 60);
		Highscore.Entry e3 = new Highscore.Entry("ccc", 70);
		Highscore.Entry e4 = new Highscore.Entry("aaa", 55);
		
		// when
		highscore.addHighscoreEntry(e1);
		highscore.addHighscoreEntry(e2);
		highscore.addHighscoreEntry(e3);
		highscore.addHighscoreEntry(e4);
		
		// then
		assertTrue(entryEquals(highscore.getFirstEntry(), e1));
		assertTrue(entryEquals(highscore.getSecondEntry(), e2));
		assertTrue(entryEquals(highscore.getThirdEntry(), e3));
	}
	
	@Test
	public void testAddHighscoreEntry_SamePlayerAgainBetter() {
		// given
		Highscore.Entry e1 = new Highscore.Entry("aaa", 50);
		Highscore.Entry e2 = new Highscore.Entry("bbb", 60);
		Highscore.Entry e3 = new Highscore.Entry("ccc", 70);
		Highscore.Entry e4 = new Highscore.Entry("aaa", 45);
		
		// when
		highscore.addHighscoreEntry(e1);
		highscore.addHighscoreEntry(e2);
		highscore.addHighscoreEntry(e3);
		highscore.addHighscoreEntry(e4);
		
		// then
		assertTrue(entryEquals(highscore.getFirstEntry(), e4));
		assertTrue(entryEquals(highscore.getSecondEntry(), e2));
		assertTrue(entryEquals(highscore.getThirdEntry(), e3));
	}
	
	@Test
	public void testAddHighscoreEntry_SamePlayerAgainBetter2() {
		// given
		Highscore.Entry e1 = new Highscore.Entry("aaa", 1356);
		Highscore.Entry e2 = new Highscore.Entry("aaa", 1258);
		
		// when
		highscore.addHighscoreEntry(e1);
		highscore.addHighscoreEntry(e2);
		
		// then
		assertTrue(entryEquals(highscore.getFirstEntry(), e2));
	}
	
	private boolean entryEquals(Highscore.Entry e1, Highscore.Entry e2) {
		return e1.getMoves() == e2.getMoves() && e1.getName().equals(e2.getName());
	}
}

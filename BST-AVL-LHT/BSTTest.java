import static org.junit.Assert.*;

import java.util.HashSet;

import org.junit.Test;


public class BSTTest {

	@Test
	public void test() {
		BST test = new BST();
		assertEquals(test.size(), 0);
		test.insert("1");
		assertEquals(test.size(), 1);
		
		for(int i = 0; i < 100; i++)
			test.insert("" + i);
		assertEquals(test.size(), 100);
		
		assertTrue(test.find("50"));
		assertFalse(test.find("-1"));
		assertEquals(test.sumFreq(), 102);
		test.resetCounters();
		assertEquals(test.sumFreq(), 100);
		assertEquals(test.sumProbes(), 0);
		test.print();
		
		BST test2 = new BST();
		test2.insert("1");
		test2.find("1");
		assertEquals(test2.sumProbes(), 1);
		test2.insert("2");
		test2.resetCounters();
		test2.find("2");
		assertEquals(test2.sumProbes(), 2);
		test2.insert("3");
		test2.insert("4");
		test2.insert("5");
		test2.resetCounters();
		test2.find("3");
		assertEquals(test2.sumProbes(), 3);
	}
	
	@Test
	public void test1000Words() {
		BST bst = new BST();
		buildAndProbe(bst, "src/1000words.txt", "src/1000words.txt");
		System.out.println("Number of words in the BST: " + bst.size()
				+ " (number of insertions: " + bst.sumFreq() + ")");
	}
	
	@Test
	public void testDarwin40k() {
		BST bst = new BST();
		buildAndProbe(bst, "src/darwin.txt", "src/40kwords.txt");
		System.out.println("Number of words in the BST: " + bst.size()
				+ " (number of insertions: " + bst.sumFreq() + ")");
	}
	
	public void buildAndProbe(BST bst, String input, String keys) {
		TextInputStream sfs = new TextInputStream(input);

		while (sfs.ready())
			bst.insert(sfs.readWord());

		//bst.print();
		
		TextInputStream qfs = new TextInputStream(keys);
		int notfound = 0;

		while (qfs.ready()) {
			String queryWord = qfs.readWord();
			if (bst.find(queryWord) == false) {
				// Uncomment the next line to make it verbose.
				// System.out.println("The word `"+queryWord+"' not found.");
				notfound++;
			}
		}

		//bst.print();
		String bstType = (bst instanceof AVL) ? "AVL" : "BST";
		System.out.println("Total number of node accesses (" + bstType + "): "
				+ bst.sumProbes() + " (failed searches: " + notfound + ")");
	}
	
	@Test
	public void testInput() {
		TextInputStream input = new TextInputStream("src/1000words.txt");
		BST bst = new BST();
		int num = 0;
		String word;
		while(input.ready()) {
			word = input.readWord();
			//System.out.println(word);
			bst.insert(word);
			num++;
		}
		System.out.println(num);
		System.out.println(bst.size());
		System.out.println(bst.sumFreq());
	}
	
	@Test
	public void test40k() {
		TextInputStream input = new TextInputStream("src/40kwords.txt");
		BST bst = new BST();
		int num = 0;
		while(input.ready()) {
			bst.insert(input.readWord());
			num++;
		}
		System.out.println(num);
		System.out.println(bst.size());
		System.out.println(bst.sumFreq());
	}
	
	@Test
	public void testDarwin() {
		TextInputStream input = new TextInputStream("src/Darwin.txt");
		BST bst = new BST();
		int num = 0;
		while(input.ready()) {
			bst.insert(input.readWord());
			num++;
		}
		System.out.println(num);
		System.out.println(bst.size());
		System.out.println(bst.sumFreq());
	}
	
	@Test
	public void testDarwinUniques() {
		HashSet<String> test = new HashSet<String>();
		TextInputStream input = new TextInputStream("src/Darwin.txt");
		while(input.ready()) {
			test.add(input.readWord());
		}
		System.out.println(test.size());
	}
	
	@Test
	public void testRepetition() {
		BST bst = new BST();
		for(int i = 0; i < 100; i++) {
			bst.insert("" + i%10);
		}
		System.out.println(bst.size());
		System.out.println(bst.sumFreq());
	}

}

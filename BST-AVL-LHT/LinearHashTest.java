import static org.junit.Assert.*;
import java.util.LinkedList;

import org.junit.Test;

public class LinearHashTest {
	
	@Test
	public void testLHash() {
		LinearHash myHash = new LinearHash(8);
		myHash.insertUnique("1");
		myHash.print();
		try {
		for(int i = 0; i < 1000; i++) {
			myHash.insertUnique("" + i);
		} }
		catch(Exception e) {
		myHash.print();
		}
		System.out.println("Size:" + myHash.size() + "\nWords:" + myHash.wordCount());
	}
	
	@Test
	public void test() {
		LinearHash l = new LinearHash(2);
		System.out.print(l.insertUnique("8"));
		System.out.print(l.insertUnique("8"));
		l.insertUnique("1");
		l.print();
		l.insertUnique("2");
		l.print();
		assertEquals(l.insertUnique("8"), -1);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testLinkedLists() {
		LinkedList<String> test[] = (LinkedList<String>[]) new LinkedList[10];
		for(int i = 0; i < 10; i++) {
			test[i] = new LinkedList<String>();
			test[i].add("" + i);
			
		}
		LinkedList<String> test2[] = (LinkedList<String>[]) new LinkedList[10];
		
		for(int i = 0; i < test.length; i++) {
			test2[i] = test[i];
		}
		for(int i = 0; i < test2.length; i++)
			for(String s : test2[i])
				System.out.print(s);
	}
	
	@Test
	public void test1000words() {
		buildAndProbe("src/1000words.txt","src/1000words.txt");
	}
	
	@Test
	public void testDarwin() {
		buildAndProbe("src/darwin.txt","src/darwin.txt");
	}
	
	@Test
	public void testDarwin40k() {
		buildAndProbe("src/darwin.txt", "src/40kwords.txt");
	}
		
	public void buildAndProbe(String trainFile, String queryFile) {
		
		LinearHash lhash = new LinearHash(128);
		TextInputStream dfs = new TextInputStream(trainFile);
		int failedInsert = 0;

		while (dfs.ready()) {
			String trainWord = dfs.readWord();
			if (lhash.insertUnique(trainWord) < 0) {
				// Uncomment the next line to make it verbose.
				// System.out.println("Failed to insert `"+trainWord+"' into HT.");
				failedInsert++;
			}
		}
		
		float storageUtil = (float) lhash.wordCount()
				/ (lhash.wordCount() + lhash.emptyCount());

		lhash.print();
		System.out.print("Words inserted: " + lhash.wordCount());
		System.out.print(" (insertions failed: " + failedInsert + "); ");
		System.out.print("Storage util: " + storageUtil);
		System.out.println(" (hash entries: " + lhash.size() + ")");

		// (4) Probe the Linear Hash table to find words from the query set.
		TextInputStream qfs = new TextInputStream(queryFile);
		int hdepth, nfound = 0, notfound = 0;
		float accesses = 0;

		while (qfs.ready()) {
			String queryWord = qfs.readWord();
			if ((hdepth = lhash.lookup(queryWord)) > 0) {
				// Uncomment the next line to make it verbose.
				// System.out.println("The word `"+queryWord+"' found: "+hdepth);
				nfound++;
				accesses += (float) hdepth / 2. + .5;
			} else {
				// Uncomment the next line to make it verbose.
				// System.out.println("The word `"+queryWord+"' not found.");
				notfound++;
				accesses += (float) (-hdepth);
			}
		}
		System.out.print("Words found: " + nfound);
		System.out.print(" (searches failed: " + notfound + ")\n");
		System.out.println("Total number of Hash Table accesses: " + accesses);
		// (5) Report the memory usage.
		Runtime runtime = Runtime.getRuntime();
		System.out.println("\nMemory consumption: "
				+ (runtime.totalMemory() - runtime.freeMemory()) + " bytes\n");
	}
	
	@Test
	public void testAccesses() {
		LinearHash l = new LinearHash(128);
		for(int i = 0; i < 100; i++) {
			l.insertUnique("" + i);
		}
		l.print();
		assertEquals(1, l.lookup("" + 64));
		assertEquals(-1, l.lookup(""));
	}

}

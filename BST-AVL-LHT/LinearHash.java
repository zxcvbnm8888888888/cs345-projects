// Author: Tanner Prynn
// 08/16/2012
// CSC345 Program 2

import java.util.Collections;
import java.util.LinkedList;

public class LinearHash {

	private LinkedList<String> hashTable[];
	private int splitIndex;
	private int modulo;
	private int size;
	
	@SuppressWarnings("unchecked")
	public LinearHash(int HTinitSize) {
		hashTable = (LinkedList<String>[]) new LinkedList[HTinitSize];
		splitIndex = 0;
		size = HTinitSize;
		modulo = HTinitSize;
	}// constructor for the Hash table

	public int insertUnique(String word) {
		int hash = hash(word);
	
		//If the hash is before the splitIndex, use the extended function
		if(hash < splitIndex)
			hash = extendedHash(word);
		
		//Create a new LinkedList at the hashTable entry, and add the word
		if(hashTable[hash] == null) {
			hashTable[hash] = new LinkedList<String>();
			hashTable[hash].add(word);
		}
		
		//Just add the word
		else if(hashTable[hash].size() == 0) {
			hashTable[hash].add(word);
		}
		
		//Collision (or duplicate)
		else {
			//Check if the word already exists in the hashTable
			if(hashTable[hash].contains(word))
				return -1;
			
			//Insert into the table
			hashTable[hash].add(word);
			
			//The size of the table is now 1 larger
			size++;
			
			//Increase size of hashTable
			if(size > hashTable.length)
				hashTable = increaseSize(hashTable);
			
			//Rehash values at splitIndex
			if(hashTable[splitIndex] != null) {
				LinkedList<String> temp = hashTable[splitIndex];
				hashTable[splitIndex] = new LinkedList<String>();
				hashTable[splitIndex+modulo] = new LinkedList<String>();
				for(String s : temp) {
					hash = extendedHash(s);
					if(hash != splitIndex && hash != modulo+splitIndex) {
						System.out.println("ERROR: key:" + s + " ExtHash:"
								+ hash + " Hash:" + hash(s) + " split:"
								+ splitIndex + " mod:" + modulo);
					}
					hashTable[hash].add(s);	
				}
			}
			
			splitIndex++;
			//If splitIndex has reached the end of the table's current hash
			if(splitIndex == modulo) {
				modulo = 2*modulo;
				splitIndex = 0;
			}
		}
		
		return hash;
	} // insert `word' to the Hash table.
	
	@SuppressWarnings("unchecked")
	private LinkedList<String>[] increaseSize(LinkedList<String>[] array) {
		LinkedList<String>[] newArray = (LinkedList<String>[]) new LinkedList[array.length*2];
		for(int i = 0; i < array.length; i++) {
			newArray[i] = array[i];
		}
		return newArray;
	}

	private int hash(String word) {
		return (int) MyUtil.ELFhash(word, modulo);
	}
	
	private int extendedHash(String word) {
		return (int) MyUtil.ELFhash(word, 2*modulo);
	}

	public int lookup(String word) {
		int hash = hash(word);
		//Check if the entry has been split
		if(hash < splitIndex)
			hash = extendedHash(word);
		if(hashTable[hash] == null)
			return 0;
		else if(hashTable[hash].contains(word))
			return hashTable[hash].size();
		return -hashTable[hash].size();
	} // look up `word' in the Hash table.

	public int wordCount() {
		int result = 0;
		for(int i = 0; i < size; i++)
			if(hashTable[i] != null)
				result += hashTable[i].size();
		
		return result;
	}

	public int emptyCount() {
		int result = 0;
		for(int i = 0; i < size; i++) {
			if(hashTable[i] == null)
				result++;
			else if(hashTable[i].size() == 0)
				result++;
		}
		return result;
	}

	public int size() {
		return (int) size;
	} // 2^k + collisions in the current round

	public void print() {
		for(int i = 0; i < size; i++) {
			LinkedList<String> current = hashTable[i];
			if(current == null) {
				System.out.print("\n[" + i + ": ]");
				continue;
			}
			if(current.size() == 0) {
				System.out.print("\n[" + i + ": ]");
				continue;
			}
			Collections.sort(current);
			System.out.print("\n[" + i + ":");
			for(String s : current)
				System.out.print(" " + s);
			System.out.print("]");
		}
		System.out.println();
	}

}

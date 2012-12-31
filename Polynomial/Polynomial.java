// Skeleton of the Polynomial ADT

import java.util.HashMap;
import java.util.Iterator;

public class Polynomial {
	public HashMap<Integer, Integer> poly;
	
	// Create an empty polynomial
	public Polynomial() {
		poly = new HashMap<Integer, Integer>();
	}

	// Create a single-term polynomial
	public Polynomial(int coef, int exp) {
		poly = new HashMap<Integer, Integer>();
		if(coef != 0)
			poly.put(exp, coef);
	}

	// Add opnd to 'this' polynomial; 'this' is returned
	public Polynomial add(Polynomial opnd) {
		Iterator<Integer> i = opnd.poly.keySet().iterator();
		int exp;
		int coef;
		while(i.hasNext()) {
			exp = i.next();
			coef = opnd.poly.get(exp);
			if(poly.containsKey(exp)) {
				poly.put(exp, coef+poly.get(exp));
				if(poly.get(exp) == 0)
					poly.remove(exp);
			} 
			else if(coef != 0)
				poly.put(exp, coef);
		}
			
		return this;
	}

	// Subtract opnd from 'this' polynomial; 'this' is returned
	public Polynomial sub(Polynomial opnd) {
		Iterator<Integer> i = opnd.poly.keySet().iterator();
		int exp;
		int coef;
		while(i.hasNext()) {
			exp = i.next();
			coef = opnd.poly.get(exp);
			if(poly.containsKey(exp)) {
				poly.put(exp, poly.get(exp)-coef);
				if(poly.get(exp) == 0)
					poly.remove(exp);
			} 
			else if(coef != 0)
				poly.put(exp, -coef);
		}
			
		return this;
	}

	// Print the terms of 'this' polynomial in decreasing order of exponents.
	// No pair of terms can share the same exponent in the printout.
	public void print() {
		Iterator<Integer> i = poly.keySet().iterator();
		int[] sortableArray = new int[poly.size()];
		int index = 0;
		while(i.hasNext()) {
			sortableArray[index] = i.next();
			index++;
		}
		java.util.Arrays.sort(sortableArray);
		int exp;
		for(index = sortableArray.length-1; index >= 0; index--) {
			exp = sortableArray[index];
			System.out.print("" + poly.get(exp) + " "+ exp + " ");
		}
	}
	
	public String toString() {
		String result = "";
		Iterator<Integer> i = poly.keySet().iterator();
		int[] sortableArray = new int[poly.size()];
		int index = 0;
		while(i.hasNext()) {
			sortableArray[index] = i.next();
			index++;
		}
		java.util.Arrays.sort(sortableArray);
		int exp;
		for(index = sortableArray.length-1; index >= 0; index--) {
			exp = sortableArray[index];
			result += "" + poly.get(exp) + "x^" + exp;
			if(index != 0)
				result += "+";
		}
		return result;
	}
}

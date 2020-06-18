/*
 * Copyright (c) 2020 Mark A. Hunter (ACT Health)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package net.fhirfactory.pegacorn.common.model;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public class FDNSet {
	private LinkedHashSet<FDN> elements;
	private String fdnSetAsString;

	public FDNSet() {
		elements = new LinkedHashSet<>();
		fdnSetAsString = new String();
	}

	public FDNSet(FDNSet originalSet) {
		elements = new LinkedHashSet<>();
		if (originalSet != null) {
			Iterator<FDN> originalSetIterator = originalSet.getElements().iterator();
			while (originalSetIterator.hasNext()) {
				FDN newFDN = new FDN(originalSetIterator.next());
				elements.add(newFDN);
			}
		}
		generateString();
	}

	public Set<FDN> getElements() {
		return (elements);
	}

	public void setElements(Set<FDN> newElementSet) {
		elements.clear();
		if (newElementSet == null) {
			return;
		}
		Iterator<FDN> fdnIterator = newElementSet.iterator();
		while (fdnIterator.hasNext()) {
			FDN fdnCopy = new FDN(fdnIterator.next());
			elements.add(fdnCopy);
		}
		generateString();
	}

	public void addElement(FDN newFDN) {
		Iterator<FDN> setIterator = elements.iterator();
		boolean isAlreadyPresent = false;
		while (setIterator.hasNext()) {
			FDN currentFDN = setIterator.next();
			if (currentFDN.equals(newFDN)) {
				isAlreadyPresent = true;
				break;
			}
		}
		if (!isAlreadyPresent) {
			FDN toBeAddedFDN = new FDN(newFDN);
			elements.add(toBeAddedFDN);
			generateString();
		}
	}

	public void removeElement(FDN theFDN) {
		Iterator<FDN> setIterator = elements.iterator();
		boolean isAlreadyPresent = false;
		while (setIterator.hasNext()) {
			FDN currentFDN = setIterator.next();
			if (currentFDN.equals(theFDN)) {
				elements.remove(currentFDN);
				generateString();
				break;
			}
		}
	}

	public boolean isEmpty() {
		if (elements.isEmpty()) {
			return (true);
		} else {
			return (false);
		}
	}

	private void generateString() {
		if(elements.isEmpty()) {
			this.fdnSetAsString = new String();
		}
		String newString = new String("{FDNSet=(");
		int counter = 0;
		Iterator<FDN> setIterator = elements.iterator();	
		while (setIterator.hasNext()) {
			FDN currentFDN = setIterator.next();
			newString = newString + "[" + Integer.toString(counter) + "][" + currentFDN.toString() + "]";
			counter += 1;
		}
		newString = newString + ")}";
		this.fdnSetAsString = newString;
	}

	@Override
	public String toString() {
		return (this.fdnSetAsString);
	}

}

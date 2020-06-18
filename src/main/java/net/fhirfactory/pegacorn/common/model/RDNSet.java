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

import java.util.ArrayList;

/**
 * @author Mark A. Hunter
 *
 */
public class RDNSet {
	private ArrayList<RDN> rdnElements;
	private String rdnToString;
	
	public RDNSet() {
		rdnElements = new ArrayList<RDN>();
		rdnToString = new String();
	}
	
	public RDNSet(RDNSet originalRDNSet) {
		if(!(originalRDNSet==null)) {
			int listSize = originalRDNSet.rdnElements.size();
			for( int listCounter = 0; listCounter < listSize; listCounter++) {
				RDN newRDN = new RDN(originalRDNSet.rdnElements.get(listCounter));
				this.rdnElements.add(listCounter, newRDN);
			}
			generateToString();
		}
	}
	
	public void appendRDN(RDN rdnElement) {
		if(rdnElement != null) {
			RDN newRDN = new RDN(rdnElement);
			int rdnSetExistingSize = this.rdnElements.size();
			this.rdnElements.add(rdnSetExistingSize, newRDN);
			generateToString();
		}
	}
	
	@Override
	public String toString() {
		return(rdnToString);
	}
	
	private void generateToString() {
		String newString = new String();
		int listSize = this.rdnElements.size();
		for( int listCounter = 0; listCounter < listSize; listCounter++) {
			newString = newString + this.rdnElements.get(listCounter).toString();
			if(listCounter < (listSize-1)) {
				newString = newString + ".";
			}
		}
		this.rdnToString = newString;
	}
	
}

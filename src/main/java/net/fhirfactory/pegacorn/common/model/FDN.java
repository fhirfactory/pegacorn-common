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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Mark A. Hunter (ACT Health)
 * @since 01-Jun-2020
 *
 */
public class FDN {
	private static final Logger LOG = LoggerFactory.getLogger(FDN.class);
	private RDNSet rdnElementSet;
	private Integer numberOfRDNs;
	private String fdnAsString;

	public static String RDN_STRING_ENTRY_SEPERATOR = "::";
	public static String FDN_STRING_PREFIX = "[FDN:";
	public static String FDN_STRING_SUFFIX = "]";

	/**
	 * Default Constructor
	 */
	public FDN() {
		LOG.trace(".FDN(): Default constructor invoked.");
		this.rdnElementSet = new RDNSet();
		this.numberOfRDNs = 0;
		this.fdnAsString = new String();
	}

	/**
	 * The Copy Constructor: It creates a duplicate of the original FDN
	 * (instantiating new containing elements).
	 *
	 * @param originalFDN The original FDN
	 */
	public FDN(FDN originalFDN) {
		LOG.trace(".FDN( FDN originalFDN ): Constructor invoked, originalFDN --> {}", originalFDN);
		this.rdnElementSet = new HashMap<Integer, RDN>();
		if (originalFDN == null) {
			return;
		}
		this.numberOfRDNs = originalFDN.getNumberOfRDNs();
		Map<Integer, RDN> originalRDNSet = originalFDN.getRDNSet();
		int fdnSize = originalFDN.getNumberOfRDNs();
		LOG.trace(".FDN( FDN originalFDN ): originalFDN Size = {}", fdnSize);
		for (int counter = 0; counter < fdnSize; counter += 1) {
			LOG.trace(".FDN( FDN originalFDN ): originalRDNSet.get({}) --> {}", counter, originalRDNSet.get(counter));
			rdnElementSet.put(counter, originalRDNSet.get(counter));
		}
		if (LOG.isTraceEnabled()) {
			int newFDNSize = rdnElementSet.size();
			LOG.trace(".FDN( FDN originalFDN ): rdnElementSet Size = {}", newFDNSize);
			for (int newCounter = 0; newCounter < fdnSize; newCounter += 1) {
				LOG.trace(".FDN( FDN originalFDN ): rdnElementSet.get({}) --> {}", newCounter,
						rdnElementSet.get(newCounter));
			}
		}
		generateString();
	}

	public FDN(String stringFDN) {
		LOG.trace(".FDN( String qualifiedFDN ): Constructor invoked, qualifiedFDN --> {}", stringFDN);
		rdnElementSet = new HashMap<Integer, RDN>();
		this.numberOfRDNs = 0;
		populateFDN(stringFDN);
		generateString();
	}

	public void appendRDN(RDN pRDN) {
		rdnElementSet.put(this.getNumberOfRDNs(), pRDN);
		this.numberOfRDNs += 1;
		generateString();
	}

	public void populateFDN(String stringFDN) {
		LOG.trace(".populateFDN(): Constructor invoked, stringFDN --> {}", stringFDN);
		if (stringFDN == null) {
			return;
		}
		if (stringFDN.isEmpty()) {
			return;
		}
		
	}

	@Override
	public String toString(){
		return(this.fdnAsString);
	}

	public void generateString() {
		fdnAsString = new String();
		if (!rdnElementSet.isEmpty()) {
			fdnAsString = fdnAsString.concat(FDN_STRING_PREFIX);
			int rdnCount = this.getNumberOfRDNs();
			for (int counter = 0; counter < rdnCount; counter += 1) {
				fdnAsString = fdnAsString.concat(this.rdnElementSet.get(counter).toString());
				if (counter != (this.getNumberOfRDNs() - 1)) {
					fdnAsString = fdnAsString.concat(RDN_STRING_ENTRY_SEPERATOR);
				}
			}
			fdnAsString = fdnAsString.concat(FDN_STRING_SUFFIX);
		}
	}

	public FDN getParentFDN() {
		if (this.getNumberOfRDNs() <= 1) {
			return null;
		}
		FDN parentFDN = new FDN();
		for (int counter = 0; counter < (this.getNumberOfRDNs() - 1); counter += 1) {
			parentFDN.appendRDN(this.rdnElementSet.get(counter));
		}
		return (parentFDN);
	}

	public RDN getUnqualifiedRDN() {
		if (rdnElementSet.isEmpty()) {
			return (null);
		}
		if (this.numberOfRDNs < 1) {
			return (null);
		}
		RDN theRDN = this.rdnElementSet.get((this.getNumberOfRDNs() - 1));
		return (theRDN);
	}

	public boolean isEmpty() {
		return (this.rdnElementSet.isEmpty());
	}

	public Map<Integer, RDN> getRDNSet() {
		return (this.rdnElementSet);
	}

	public Integer getNumberOfRDNs() {
		return (this.numberOfRDNs);
	}

	public boolean equals(FDN otherFDN) {
		if (otherFDN == null) {
			return (false);
		}
		if (getNumberOfRDNs() != otherFDN.getNumberOfRDNs()) {
			return (false);
		}
		String thisFDNAsString = toString();
		String otherFDNAsString = otherFDN.toString();
		if (thisFDNAsString.contentEquals(otherFDNAsString)) {
			return (true);
		} else {
			return (false);
		}
	}
}

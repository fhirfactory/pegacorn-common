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

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * @author Mark A. Hunter (ACT Health)
 * @since 01-June-2020
 *
 */
public class FDN {
	private static final Logger LOG = LoggerFactory.getLogger(FDN.class);
	private HashMap<Integer, RDN> rdnSet;
	private String FDNType;
	private Integer rdnCount;
	private FDNToken token;
	private String fdnToString;
	private String unqualifiedToken;

	private static final String RDN_TO_STRING_ENTRY_SEPERATOR = ".";
	private static final String FDN_TO_STRING_PREFIX = "[FDN:";
	private static final String FDN_TO_STRING_SUFFIX = "]";
	
	private static final String FDN_TOKEN_PREFIX = "{FDNToken:";
	private static final String FDN_TOKEN_SUFFIX = "}";

	/**
	 * Default Constructor
	 */
	public FDN() {
		LOG.trace(".FDN(): Default constructor invoked.");
		this.rdnSet = new HashMap<Integer, RDN>();
		this.rdnCount = 0;
		LOG.trace(".FDN(): this.rdnElementSet intialised.");
		this.token = new FDNToken();
		this.fdnToString = new String();
		this.unqualifiedToken = new String();
	}

	/**
	 * The Copy Constructor: It creates a duplicate of the original FDN
	 * (instantiating new containing elements).
	 *
	 * @param originalFDN The original FDN
	 */
	public FDN(FDN originalFDN) {
		LOG.trace(".FDN( FDN originalFDN ): Constructor invoked, originalFDN --> {}", originalFDN);
		if (originalFDN == null) {
			throw (new IllegalArgumentException("Empty FDN passed to copy Constructor"));
		}
		// Essentially, we iterate through the HashMap from the OriginalFDN, 
		// create new RDN's from the content, and append these RDN's (in the 
		// appropriate order) into the new FDN.
		this.rdnSet = new HashMap<Integer, RDN>();
		this.rdnCount = originalFDN.getRDNCount();
		Map<Integer, RDN> otherRDNSet = originalFDN.getRDNSet();
		if(otherRDNSet.keySet().size() != originalFDN.getRDNCount()) {
			throw (new IllegalArgumentException("Malformed FDN passed to copy Constructor"));
		}
		for(int counter = 0; counter < this.getRDNCount(); counter++ ) {
			this.rdnSet.put(counter, otherRDNSet.get(counter));
		}
		// We need to pre-build the toString() and getToken() content so we don't re-do it 
		// every time we do some comparison etc.
		generateToString();
		generateToken();
		generateUnqualifiedToken();
		LOG.trace(".FDN( FDN originalFDN ): generatedFDN = {}", this.fdnToString);
	}
	
	/**
	 * This constructor uses an FDNToken to construct a new FDN.
	 * 
	 * @param token An FDNToken from which the FDN may be instantiated.
	 */

	public FDN(FDNToken token) {
		LOG.trace(".FDN( FDNToken token ): Constructor invoked, token --> {}", token);
		if (token == null) {
			throw (new IllegalArgumentException("Empty parameter passed to Constructor"));
		}
		String tokenContent = token.getContent();
		// The FDNToken is really just a JSONObject.toString() with an FDN_TOKEN_PREFIX and FDN_TOKEN_SUFFIX
		// added to it. So, to be useful, we need to strip those two strings from the FDNToken.content.
		if((!tokenContent.startsWith(FDN_TOKEN_PREFIX)) || (!tokenContent.endsWith(FDN_TOKEN_SUFFIX))) {
			throw (new IllegalArgumentException("Badly formed FDNToken passed to Constructor --> " + token.getContent()));
		}
		String fdnTokenPrefixRemoved = tokenContent.substring(FDN_TOKEN_PREFIX.length(), (tokenContent.length())); 
		String actualToken = fdnTokenPrefixRemoved.substring(0,(fdnTokenPrefixRemoved.length()-FDN_TOKEN_SUFFIX.length()));
		// Now we have a nice string that should allow for parsing/loading into a JSONObject. This will allow us to 
		// work with the individual elements (which are, in fact, just <counter, RDN> values).
		LOG.trace(".FDN( FDNToken token ): After removing the FDN prefix/suffix details, the actual Token content is --> {}", actualToken);
		try {
			JSONObject tokenAsRDNSet = new JSONObject(actualToken);
			int setSize = tokenAsRDNSet.length();
			LOG.trace(".FDN( FDNToken token ): The number of RDN entries in the Token is --> {}", setSize);
			this.rdnSet = new HashMap<Integer,RDN>();
			// We are going to take the content from the FDNToken, convert it to a JSONObject, and then
			// iterate through the elements (the JSONObject will have values <counter, string> in it.
			// We then take the string from the JSONObject, which is, in fact, an RDNToken and then
			// instantiate an RDN(RDNToken). The resulting RDN is then added to the rdnSet map.
			for(int counter = 0; counter < setSize; counter++ ) {
				LOG.trace(".FDN( FDNToken token ): Iterating through the extracted Token JSONObject, attempting to extract RDN[{}]", counter);
				String currentRDNTokenContent = tokenAsRDNSet.getString(Integer.toString(counter));
				LOG.trace(".FDN( FDNToken token ): Iterating through the extracted Token JSONObject, extracted RDN Token Content --> {}", currentRDNTokenContent);
				RDNToken currentRDNToken = new RDNToken(currentRDNTokenContent);
				RDN currentRDN = new RDN(currentRDNToken);
				LOG.trace(".FDN( FDNToken token ): Iterating through the extracted RDNs, current RDN --> {}", currentRDN);
				this.rdnSet.put(counter, currentRDN);
			}
			this.rdnCount = setSize;
		} catch (Exception jsonEx) {
			throw (new IllegalArgumentException(jsonEx.getMessage()));
			
		}
		// We need to pre-build the toString() and getToken() content so we don't re-do it 
		// every time we do some comparison etc.
		generateToString();
		generateToken();
		generateUnqualifiedToken();
	}

	/**
	 * This method appends an RDN (Relative Distinguished Name) to an existing FDN. This makes the
	 * RDN the "Least Significant" member.
	 * 
	 * @param toBeAddedRDN An RDN that should be appended (injected as the "Least Significant" member
	 * of the FDN.
	 */
	public void appendRDN(RDN toBeAddedRDN) {
		LOG.trace(".appendRDN(): Entry, toBeAddedRDN --> {}", toBeAddedRDN);
		if (toBeAddedRDN == null) {
			throw (new IllegalArgumentException("Empty RDN passed to appendRDN"));
		}
		RDN newRDN = new RDN(toBeAddedRDN);
		int existingSetSize = this.getRDNCount();
		this.rdnSet.put(existingSetSize,newRDN);
		this.rdnCount = existingSetSize + 1;
		// We need to pre-build the toString() and getToken() content so we don't re-do it 
		// every time we do some comparison etc.
		generateToString();
		generateToken();
		generateUnqualifiedToken();
	}

	@Override
	public String toString() {
		return (this.fdnToString);
	}

	/**
	 * This method pre-builds the FDN::toString() value. This value (this.fdnToString) cannot
	 * be used as input into an FDN constructor and is made available only for the purposes of 
	 * documentation and/or reporting.
	 */
	private void generateToString() {
		fdnToString = new String();
		fdnToString = fdnToString.concat(FDN_TO_STRING_PREFIX);
		for(int counter = 0; counter < this.getRDNCount(); counter++) {
			RDN currentRDN = this.rdnSet.get(counter);
			String currentRDNString = currentRDN.getConciseString();
			fdnToString = fdnToString.concat(currentRDNString);
			if(counter != (this.getRDNCount()-1)) {
				fdnToString = fdnToString.concat(RDN_TO_STRING_ENTRY_SEPERATOR);
			}
		}
		fdnToString = fdnToString.concat(FDN_TO_STRING_SUFFIX);
	}

	/**
	 * FDNs are used to support hierarchical/containment models - where a Parent FDN
	 * it total contained within the child FDN. For exmaple, for the following 
	 * FDN:
	 * 		FDN = (Campus=CHS).(Building=Bulding10),(Floor=3)
	 * 
	 * then the Campus element is the Parent of the Building element. Note that the
	 * Floor=? element is the "Least Significant" element.
	 * 
	 * @return Returns the "Parent" FDN of this FDN. The "Parent" FDN is one that has
	 * the current "Least Significant" member removed from it.
	 */
	public FDN getParentFDN() {
		if (this.getRDNCount() <= 1) {
			return null;
		}
		FDN newParentFDN = new FDN();
		for(int counter =0; counter < (this.getRDNCount()-2); counter++) {
			RDN currentRDN = this.rdnSet.get(counter);
			newParentFDN.appendRDN(currentRDN);
		}
		return (newParentFDN);
	}

	public RDN getUnqualifiedRDN() {
		if (this.getRDNCount() <= 0){
			return (null);
		}
		RDN leastSignificantRDN = this.rdnSet.get((this.getRDNCount()-1));
		return (leastSignificantRDN);
	}

	public boolean isEmpty() {
		if( this.getRDNCount() <= 0) {
			return(true);
		} else {
			return(false);
		}
	}
	
	public Map<Integer, RDN> getRDNSet() {
		return(this.rdnSet);
	}

	public int getRDNCount() {
		return (this.rdnCount);
	}

	public boolean equals(FDN otherFDN) {
		if (otherFDN == null) {
			return (false);
		}
		if (getRDNCount() != otherFDN.getRDNCount()) {
			return (false);
		}
		String thisFDNToken = getToken().getContent();
		String otherFDNToken = otherFDN.getToken().getContent();
		if (thisFDNToken.contentEquals(otherFDNToken)) {
			return (true);
		} else {
			return (false);
		}
	}

	public FDNToken getToken() {
		return (this.token);
	}

	private void generateToken() {
		JSONObject newToken = new JSONObject();
		for(int counter = 0; counter < this.getRDNCount(); counter++) {
			RDN currentRDN = this.rdnSet.get(counter);
			newToken.put(Integer.toString(counter), currentRDN.getToken().getContent());
		}
		String tokenString = FDN_TOKEN_PREFIX + newToken.toString() + FDN_TOKEN_SUFFIX;
		this.token = new FDNToken(tokenString);
	}

	private void generateUnqualifiedToken(){
		String newUnqualifiedToken = new String();
		for(int counter = 0; counter < this.getRDNCount(); counter++) {
			RDN currentRDN = this.rdnSet.get(counter);
			newUnqualifiedToken = newUnqualifiedToken + "<" + currentRDN.getUnqualifiedName() + ">";
		}
		this.unqualifiedToken = newUnqualifiedToken;
	}

	public String getUnqualifiedToken() {
		return unqualifiedToken;
	}

	public String getFDNType() {
		return FDNType;
	}

	public void setFDNType(String FDNType) {
		this.FDNType = FDNType;
	}

	public void appendFDN(FDN additionalFDN){
		if( additionalFDN==null){
			return;
		}
		int additionalFDNSize = additionalFDN.getRDNCount();
		Map<Integer, RDN> additionalRDNSet = additionalFDN.getRDNSet();
		for(int counter = 0; counter < additionalFDNSize; counter++ ) {
			this.appendRDN(additionalRDNSet.get(counter));
		}
		generateToString();
		generateToken();
		generateUnqualifiedToken();
	}
}

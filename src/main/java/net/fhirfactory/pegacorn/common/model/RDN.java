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

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author Mark A. Hunter
 */
public class RDN {
	private static final Logger LOG = LoggerFactory.getLogger(RDN.class);

	private String nameValue;
	private String nameQualifier;

	@JsonIgnore
	public static String RDN_SEPARATOR = "=";

	private static final String tokenEntryQualifierForQualifier = "Qualifier";
	private static final String tokenEntryQualifierForValue = "Value";

	@JsonIgnore
	public RDNToken token;

	@JsonIgnore
	private String rdnToString;
	
	private String rdnAsConciseString;

	@JsonIgnore
	public RDN(String nameQualifier, String nameValue) {
		LOG.debug(".RND(String, String): Entry, nameQualifier --> {}, nameValue --> {}", nameQualifier, nameValue);
		if ((nameQualifier == null) || (nameValue == null)) {
			throw (new IllegalArgumentException("null nameQualifier or nameValue passed to Constructor"));
		}
		if ((nameQualifier.isEmpty()) || (nameValue.isEmpty())) {
			throw (new IllegalArgumentException("Empty nameQualifier or nameValue passed to Constructor"));
		}
		this.nameValue = new String(nameValue);
		this.nameQualifier = new String(nameQualifier);
		convertToString();
		createToken();
		convertToConciseString();
	}

	@JsonIgnore
	public RDN(RDN otherRDN) {
		if (otherRDN == null) {
			throw (new IllegalArgumentException("null otherRDN passed to copy Constructor"));
		}
		this.nameQualifier = new String(otherRDN.getNameQualifier());
		this.nameValue = new String(otherRDN.getNameValue());
		convertToString();
		createToken();
		convertToConciseString();
	}

	@JsonIgnore
	public RDN(RDNToken token) {
		LOG.debug(".RND(RDNToken): Entry, token --> {}", token);
		if (token == null) {
			throw (new IllegalArgumentException("null RDNToken passed to Constructor"));
		}

		try {
			JSONObject jsonToken = new JSONObject(token.getContent());
			LOG.trace(".RND(RDNToken): Converted Token into JSONObject --> {}", jsonToken);
			if((!jsonToken.has(tokenEntryQualifierForQualifier)) || (!jsonToken.has(tokenEntryQualifierForValue)) ) {
				throw (new IllegalArgumentException("invalid RDNToken passed to Constructor"));
			}
			LOG.trace(".RND(RDNToken): JSONObject has both Type and Value entries!");
			this.nameQualifier = new String(jsonToken.getString(tokenEntryQualifierForQualifier));
			this.nameValue = new String(jsonToken.getString(tokenEntryQualifierForValue));
		} catch (Exception jsonEx) {
			throw (new IllegalArgumentException(jsonEx.getMessage()));
		}
		LOG.trace(".RND(RDNToken): new RDN created, now building different String values!");
		convertToString();
		createToken();
		convertToConciseString();
		LOG.trace(".RND(RDNToken): new RDN --> {}", this.rdnToString);
	}

	public String getNameQualifier() {
		return (this.nameQualifier);
	}

	@JsonIgnore
	public void setNameQualifier(String newNameType) {
		this.nameQualifier = new String(newNameType);
		convertToString();
		createToken();
	}

	public String getNameValue() {
		return (this.nameValue);
	}

	@JsonIgnore
	public void setNameValue(String newNameValue) {
		this.nameValue = new String(newNameValue);
		convertToString();
		createToken();
	}

	@Override
	public String toString() {
		return (this.rdnToString);
	}

	@JsonIgnore
	private void convertToString() {
		this.rdnToString = "[RDN=(" + this.nameQualifier + RDN_SEPARATOR + this.nameValue + ")]";
	}

	@JsonIgnore
	private void createToken() {
		JSONObject newToken = new JSONObject();
		newToken.put(tokenEntryQualifierForQualifier, this.nameQualifier);
		newToken.put(tokenEntryQualifierForValue, this.nameValue);
		this.token = new RDNToken(newToken.toString());
	}

	@JsonIgnore
	public RDNToken getToken() {
		return (this.token);
	}
	
	public String getConciseString() {
		return(this.rdnAsConciseString);
	}
	
	private void convertToConciseString() {
		this.rdnAsConciseString = "("+ this.nameQualifier + RDN_SEPARATOR + this.nameValue + ")";
	}

	public String getUnqualifiedName(){
		return(getNameValue());
	}
}

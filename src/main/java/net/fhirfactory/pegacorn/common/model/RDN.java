/*
 * Copyright (c) 2020 mhunter
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

/**
 *
 * @author Mark A. Hunter
 */
public class RDN {

	private JSONObject rdnValue;

	public static String RDN_SEPARATOR = "=";
	
	public static final String rdnQualifierType = "type";
	public static final String rdnQualifierValue = "value";

	public String BAD_NAME_VALUE = "ERROR_BAD_NAME_VALUE";
	public String BAD_NAME_TYPE = "ERROR_BAD_NAME_TYPE";

	public String rdnAsQualifiedString;
	public String rdnToString;

	public RDN(String nameType, String nameValue) {
		if (nameType == null) {
			nameType = BAD_NAME_TYPE;
		}
		if (nameType.isEmpty()) {
			nameType = BAD_NAME_TYPE;
		}
		if (nameValue == null) {
			nameValue = BAD_NAME_VALUE;
		}
		if (nameValue.isEmpty()) {
			nameValue = BAD_NAME_VALUE;
		}
		this.rdnValue.put(rdnQualifierValue, nameValue);
		this.rdnValue.put(rdnQualifierType, nameType);
		convertToString();
		toJSONString();
	}

	public RDN(RDN otherRDN) {
		rdnValue = new JSONObject();
		if (otherRDN != null) {
			rdnValue.put(rdnQualifierType, otherRDN.rdnValue.getString(rdnQualifierType));
			rdnValue.put(rdnQualifierValue, otherRDN.rdnValue.getString(rdnQualifierValue));
			convertToString();
			toJSONString();
		}
	}
	
	public RDN(String qualifiedString) {
		if(qualifiedString != null) {
			try {
				this.rdnValue = new JSONObject(qualifiedString);
			} catch(Exception jsonEx) {
				// do nothing.
			}
		}
	}

	public String getNameType() {
		return (this.rdnValue.getString(rdnQualifierType));
	}

	public void setNameType(String newNameType) {
		this.rdnValue.put(rdnQualifierType, newNameType);
		toJSONString();
	}

	public String getNameValue() {
		return(this.rdnValue.getString(rdnQualifierValue));
	}

	public void setNameValue(String newNameValue) {
		this.rdnValue.put(rdnQualifierValue, newNameValue);
		toJSONString();
	}

	@Override
	public String toString() {
		return (this.rdnToString);
	}
	
	public void convertToString() {
		this.rdnToString = "(" + this.rdnValue.getString(rdnQualifierType) + RDN_SEPARATOR + this.rdnValue.getString(rdnQualifierValue) + ")";
	}

	public void toJSONString() {
		this.rdnAsQualifiedString = rdnValue.toString();
	}
	
	public String asQaulifiedString() {
		return(this.rdnAsQualifiedString);
	}
}

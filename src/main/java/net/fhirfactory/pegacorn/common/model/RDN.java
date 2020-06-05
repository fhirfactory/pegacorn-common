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

import java.util.Iterator;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author markh
 */
public class RDN {

    private JSONObject value;
    public static String RDN_SEPARATOR = "|=|";

    public String BAD_NAME_VALUE = "ERROR_BAD_NAME_VALUE";
    public String BAD_NAME_TYPE = "ERROR_BAD_NAME_TYPE";

    public RDN(String nameType, String nameValue) {
        value = new JSONObject();
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
        value.append(nameType, nameValue);
    }

    public RDN(String qualifiedRDNName) { // String of type "type=value"
        value = new JSONObject();
        if (qualifiedRDNName == null) {
            value.append(this.BAD_NAME_TYPE, this.BAD_NAME_VALUE);
            return;
        }
        if (qualifiedRDNName.isEmpty()) {
            value.append(this.BAD_NAME_TYPE, this.BAD_NAME_VALUE);
            return;
        }
        JSONObject tempRDN;
        try {
            tempRDN = new JSONObject(qualifiedRDNName);
        } catch (JSONException Ex) {
            value.append(this.BAD_NAME_TYPE, this.BAD_NAME_VALUE);
            return;
        }
        if (tempRDN.length() != 1) {
            value.append(this.BAD_NAME_TYPE, this.BAD_NAME_VALUE);
            return;
        }
        Iterator<String> nameTypeIterator = tempRDN.keys();
        // we are only interested in the first :)
        String nameTypeOfInterest = nameTypeIterator.next();
        value.append(nameTypeOfInterest, tempRDN.get(nameTypeOfInterest));
    }

    public String getNameType() {
        Iterator<String> nameTypeIterator = value.keys();
        // we are only interested in the first :)
        String nameTypeOfInterest = nameTypeIterator.next();
        return nameTypeOfInterest;
    }

    public void setNameType(String newNameType) {
        Iterator<String> nameTypeIterator = value.keys();
        // we are only interested in the first :)
        String oldNameType = nameTypeIterator.next();
        String oldNameValue = value.getString(oldNameType);
        value.remove(oldNameType);
        value.append(newNameType, oldNameValue);
    }

    public String getNameValue() {
        Iterator<String> nameTypeIterator = value.keys();
        // we are only interested in the first :)
        String nameTypeOfInterest = nameTypeIterator.next();
        return (value.getString(nameTypeOfInterest));
    }

    public void setNameValue(String newNameValue) {
        Iterator<String> nameTypeIterator = value.keys();
        // we are only interested in the first :)
        String oldNameType = nameTypeIterator.next();
        value.remove(oldNameType);
        value.append(oldNameType, newNameValue);
    }

    public String toString() {
        Iterator<String> nameTypeIterator = value.keys();
        // we are only interested in the first :)
        String nameType = nameTypeIterator.next();
        String nameValue = value.getString(nameType);
        return (nameType + RDN_SEPARATOR + nameValue);
    }

    public String toJSONString() {
        return (value.toString());
    }
}

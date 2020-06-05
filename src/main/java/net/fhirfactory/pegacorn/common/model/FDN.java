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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;
import org.json.JSONObject;

/**
 *
 * @author Mark Hunter
 */
public class FDN {

    private JSONObject rdnElementSet;

    public static String RDN_STRING_ENTRY_SEPERATOR = "|+|";

    public FDN() {
        rdnElementSet = new JSONObject();
    }

    public FDN(FDN originalFDN) {
        this.rdnElementSet = new JSONObject();
        Iterator<String> nameTypeIterator = rdnElementSet.keys();
        while (nameTypeIterator.hasNext()) {
            String sequenceNumber = nameTypeIterator.next();
            RDN rdnValue = (RDN) (rdnElementSet.get(sequenceNumber));
            this.rdnElementSet.append(sequenceNumber, rdnValue);
        }
    }

    public FDN(String qualifiedFDN) {
        rdnElementSet = new JSONObject();
        populateFDN(qualifiedFDN);
    }

    public void appendRDN(RDN pRDN) {
        int setSize = rdnElementSet.length();
        rdnElementSet.append(Integer.toString(setSize), pRDN);
    }

    public void populateFDN(String qualifiedFDN) {
        if (qualifiedFDN == null) {
            return;
        }
        if (qualifiedFDN.isEmpty()) {
            return;
        }
        String[] qualifiedElements = qualifiedFDN.split(FDN.RDN_STRING_ENTRY_SEPERATOR);
        if (qualifiedElements.length < 1) {
            return;
        }
        rdnElementSet = new JSONObject();
        for (int counter = 0; counter < qualifiedElements.length; counter += 1) {
            RDN newRDNElement = new RDN(qualifiedElements[counter]);
            rdnElementSet.append(Integer.toString(counter), newRDNElement);
        }
    }

    public String toString() {
        String fdnAsString = new String();
        if (!rdnElementSet.isEmpty()) {
            Iterator<String> rdnIterator = rdnElementSet.keys();
            ArrayList<RDN> rdnArray = new ArrayList<RDN>();
            while (rdnIterator.hasNext()) {
                String rdnLocationAsString = rdnIterator.next();
                int rdnLocation = Integer.valueOf(rdnLocationAsString);
                rdnArray.add(rdnLocation, (RDN) (rdnElementSet.get(rdnLocationAsString)));
            }
            Iterator rdnOrderedListIterator = rdnArray.listIterator();
            while (rdnOrderedListIterator.hasNext()) {
                RDN rdnValue = (RDN) (rdnOrderedListIterator.next());
                fdnAsString = fdnAsString.concat(rdnValue.toString());
                if (rdnOrderedListIterator.hasNext()) {
                    fdnAsString = fdnAsString.concat(RDN_STRING_ENTRY_SEPERATOR);
                }
            }
        }
        return (fdnAsString);
    }

    public String toJSONString() {
        return (rdnElementSet.toString());
    }

    public FDN getParentFDN() {
        if (!rdnElementSet.isEmpty()) {
            Iterator<String> rdnIterator = rdnElementSet.keys();
            ArrayList<RDN> rdnArray = new ArrayList<RDN>();
            while (rdnIterator.hasNext()) {
                String rdnLocationAsString = rdnIterator.next();
                int rdnLocation = Integer.valueOf(rdnLocationAsString);
                rdnArray.add(rdnLocation, (RDN) (rdnElementSet.get(rdnLocationAsString)));
            }
            FDN newFDN = new FDN();
            int sizeOfExistingFDN = rdnArray.size();
            if (sizeOfExistingFDN < 2) {
                return (null);
            }
            for (int counter = 0; counter < (sizeOfExistingFDN - 1); counter += 1) {
                newFDN.appendRDN((RDN) (rdnArray.get(counter)));
            }
            return (newFDN);
        } else {
            return (null);
        }
    }

    public RDN getRDNValue(String name) {
        if (rdnElementSet.isEmpty()) {
            return (null);
        }
        Iterator<String> rdnIterator = rdnElementSet.keys();
        ArrayList<RDN> rdnArray = new ArrayList<RDN>();
        while (rdnIterator.hasNext()) {
            String rdnLocationAsString = rdnIterator.next();
            int rdnLocation = Integer.valueOf(rdnLocationAsString);
            rdnArray.add(rdnLocation, (RDN) (rdnElementSet.get(rdnLocationAsString)));
        }
        rdnArray.trimToSize();
        int numberOfRDNs = rdnArray.size();
        return((RDN)(rdnArray.get(numberOfRDNs-1)));
    }
    
    public boolean isEmpty(){
        return(this.rdnElementSet.isEmpty());
    }
}

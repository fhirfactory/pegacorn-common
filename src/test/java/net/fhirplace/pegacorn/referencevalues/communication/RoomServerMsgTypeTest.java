/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package net.fhirplace.pegacorn.referencevalues.communication;

import org.junit.jupiter.api.Test;

import net.fhirfactory.pegacorn.fhir.r4.referencevalues.Communication.RoomServerMsgType;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author ACT Health
 */
public class RoomServerMsgTypeTest {
    
    public RoomServerMsgTypeTest() {
    }

    /**
     * Test of isPatentCentric method, of class RoomServerMsgType.
     */
    @org.junit.jupiter.api.Test
    public void testIsPatentCentric() {
        System.out.println("isPatentCentric()");
        String pStringToCompare = "net.fhirbox.pegacorn.communicate.roomserver.PatientCentric";
        RoomServerMsgType instance = new RoomServerMsgType();
        boolean expResult = true;
        boolean result = instance.isPatentCentric(pStringToCompare);
        assertEquals(expResult, result);
    }
    
    @org.junit.jupiter.api.Test
    public void testIsNotPatentCentric() {
        System.out.println("isNotPatentCentric()");
        String pStringToCompare = "random garbage";
        RoomServerMsgType instance = new RoomServerMsgType();
        boolean expResult = false;
        boolean result = instance.isPatentCentric(pStringToCompare);
        assertEquals(expResult, result);
    }    
    
    @org.junit.jupiter.api.Test
    public void testIsCareTeamCentric() {
        System.out.println("testIsCareTeamCentric()");
        String pStringToCompare = "net.fhirbox.pegacorn.communicate.roomserver.CareTeamCentric";
        RoomServerMsgType instance = new RoomServerMsgType();
        boolean expResult = true;
        boolean result = instance.isCareTeamCentric(pStringToCompare);
        assertEquals(expResult, result);
    }    

    @org.junit.jupiter.api.Test
    public void testIsNotCareTeamCentric() {
        System.out.println("testIsNotCareTeamCentric()");
        String pStringToCompare = "net.fhirbox.pegacorn.communicate.roomserver.PatientCentric";
        RoomServerMsgType instance = new RoomServerMsgType();
        boolean expResult = false;
        boolean result = instance.isCareTeamCentric(pStringToCompare);
        assertEquals(expResult, result);
    }     
    
    @org.junit.jupiter.api.Test
    public void testIsOrganizationCentric() {
        System.out.println("testIsOrganizationCentric()");
        String pStringToCompare = "net.fhirbox.pegacorn.communicate.roomserver.OrganizationCentric";
        RoomServerMsgType instance = new RoomServerMsgType();
        boolean expResult = true;
        boolean result = instance.isOrganizationCentric(pStringToCompare);
        assertEquals(expResult, result);
    } 

    @org.junit.jupiter.api.Test
    public void testIsNotOrganizationCentric() {
        System.out.println("testIsNotOrganizationCentric()");
        String pStringToCompare = "net.fhirbox.pegacorn.communicate.roomserver.CareTeamCentric";
        RoomServerMsgType instance = new RoomServerMsgType();
        boolean expResult = false;
        boolean result = instance.isOrganizationCentric(pStringToCompare);
        assertEquals(expResult, result);
    }     

    @org.junit.jupiter.api.Test
    public void testIsGeneralGroup() {
        System.out.println("testIsGeneralGroup()");
        String pStringToCompare = "net.fhirbox.pegacorn.communicate.roomserver.GeneralGroup";
        RoomServerMsgType instance = new RoomServerMsgType();
        boolean expResult = true;
        boolean result = instance.isGeneralGroup(pStringToCompare);
        assertEquals(expResult, result);
    }     
    
    @org.junit.jupiter.api.Test
    public void testIsNotGeneralGroup() {
        System.out.println("testIsGeneralGroup()");
        String pStringToCompare = "net.fhirbox.pegacorn.communicate.roomserver.NotGeneralGroup";
        RoomServerMsgType instance = new RoomServerMsgType();
        boolean expResult = false;
        boolean result = instance.isGeneralGroup(pStringToCompare);
        assertEquals(expResult, result);
    }   
    
    @org.junit.jupiter.api.Test
    public void testIsPeer2Peer() {
        System.out.println("testIsPeer2Peer()");
        String pStringToCompare = "net.fhirbox.pegacorn.communicate.roomserver.Peer2Peer";
        RoomServerMsgType instance = new RoomServerMsgType();
        boolean expResult = true;
        boolean result = instance.isPeer2Peer(pStringToCompare);
        assertEquals(expResult, result);
    }   
    
    @org.junit.jupiter.api.Test
    public void testIsNotPeer2Peer() {
        System.out.println("testIsNotPeer2Peer()");
        String pStringToCompare = "net.fhirbox.pegacorn.communicate.roomserver.NotPeer2Peer";
        RoomServerMsgType instance = new RoomServerMsgType();
        boolean expResult = false;
        boolean result = instance.isPeer2Peer(pStringToCompare);
        assertEquals(expResult, result);
    }       
    
}

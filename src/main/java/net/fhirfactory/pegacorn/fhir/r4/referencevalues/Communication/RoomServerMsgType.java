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
package net.fhirfactory.pegacorn.fhir.r4.referencevalues.Communication;

/**
 *
 * @author Mark A. Hunter
 */
public class RoomServerMsgType {
    public final static String ROOMSERVER_MSGTYPE_PATIENTCENTRIC = "net.fhirbox.pegacorn.communicate.roomserver.PatientCentric";
    public final static String ROOMSERVER_MSGTYPE_CARETEAMCENTRIC = "net.fhirbox.pegacorn.communicate.roomserver.CareTeamCentric";
    public final static String ROOMSERVER_MSGTYPE_ORGANIZATIONCENTRIC = "net.fhirbox.pegacorn.communicate.roomserver.OrganizationCentric";
    public final static String ROOMSERVER_MSGTYPE_GENERALGROUP = "net.fhirbox.pegacorn.communicate.roomserver.GeneralGroup";
    public final static String ROOMSERVER_MSGTYPE_PEER2PEER = "net.fhirbox.pegacorn.communicate.roomserver.Peer2Peer";
    
    public boolean isPatentCentric(String pStringToCompare )
    {
        return( pStringToCompare.equals(ROOMSERVER_MSGTYPE_PATIENTCENTRIC));
    }
    
    public boolean isCareTeamCentric(String pStringToCompare )
    {
        return( pStringToCompare.equals(ROOMSERVER_MSGTYPE_CARETEAMCENTRIC));
    }
    
    public boolean isOrganizationCentric(String pStringToCompare )
    {
        return( pStringToCompare.equals(ROOMSERVER_MSGTYPE_ORGANIZATIONCENTRIC));
    }
    
    public boolean isGeneralGroup(String pStringToCompare)
    {
        return( pStringToCompare.equals(ROOMSERVER_MSGTYPE_GENERALGROUP));
    }
    
    public boolean isPeer2Peer(String pStringToCompare )
    {
        return( pStringToCompare.equals(ROOMSERVER_MSGTYPE_PEER2PEER));
    }
}

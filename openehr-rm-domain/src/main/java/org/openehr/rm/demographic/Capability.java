/*
 * component:   "openEHR Reference Implementation"
 * description: "Class Attestation"
 * keywords:    "demographic"
 *
 * author:      "Goran Pestana <goran@acode.se>"
 * support:     "Acode HB <support@acode.se>"
 * copyright:   "Copyright (c) 2005 Acode HB, Sweden"
 * license:     "See notice at bottom of class"
 *
 * file:        "$URL: http://svn.openehr.org/ref_impl_java/TRUNK/libraries/src/java/org/openehr/rm/demographic/Capability.java $"
 * revision:    "$LastChangedRevision: 2 $"
 * last_change: "$LastChangedDate: 2005-10-12 22:20:08 +0100 (Wed, 12 Oct 2005) $"
 */
package org.openehr.rm.demographic;

import org.openehr.rm.common.archetyped.Locatable;
import org.openehr.rm.common.archetyped.Archetyped;
import org.openehr.rm.common.archetyped.FeederAudit;
import org.openehr.rm.common.archetyped.Link;
import org.openehr.rm.common.archetyped.Pathable;
import org.openehr.rm.support.identification.UIDBasedID;
import org.openehr.rm.datatypes.quantity.datetime.DvDate;
import org.openehr.rm.datatypes.quantity.DvInterval;
import org.openehr.rm.datatypes.text.DvText;
import org.openehr.rm.datastructure.itemstructure.ItemStructure;
import org.openehr.rm.FullConstructor;
import org.openehr.rm.Attribute;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.List;
import java.util.Set;

/**
 * Capability of a role, such as ehr modifier, health care provider .
 * Capability should be backed up by credentials.
 *
 * @author Goran Pestana
 * @version 1.0
 */
public class Capability extends Locatable {

    protected Capability() {
    }

    /**
     * Constructs a Capability
     *
     * @param uid              null if not specified
     * @param archetypeNodeId
     * @param name
     * @param archetypeDetails null if not specified
     * @param feederAudit      null if not specified
     * @param links            null if not specified
     * @param timeValidity     null if not specified
     * @param credentials      not null
     * @throws IllegalArgumentException if name null or meaning null
     *                                  or links not null and empty
     *                                  or credentials null
     */
    @FullConstructor
            public Capability(
            @Attribute(name = "uid") UIDBasedID uid,
            @Attribute(name = "archetypeNodeId", required = true) String archetypeNodeId,
            @Attribute(name = "name", required = true) DvText name,
            @Attribute(name = "archetypeDetails") Archetyped archetypeDetails,
            @Attribute(name = "feederAudit") FeederAudit feederAudit,
            @Attribute(name = "links") Set<Link> links,
            @Attribute(name = "parent") Pathable parent,
            @Attribute(name = "timeValidity") DvInterval<DvDate> timeValidity,
            @Attribute(name = "credentials", required=true) ItemStructure credentials) {

        super(uid, archetypeNodeId, name, archetypeDetails, feederAudit, links, parent);

        if (credentials == null) {
            throw new IllegalArgumentException("null credentials");
        }
        this.timeValidity = timeValidity;
        this.credentials = credentials;
    }

    public String pathOfItem(Locatable item) {
        //todo: to be implemented
        return null;
    }

    public boolean hasLegalIdentity() {
        //todo: to be implemented
        return false;
    }

    /**
     * Valid time interval for this capability
     *
     * @return time validity
     */
    public DvInterval<DvDate> getTimeValidity() {
        return timeValidity;
    }

    protected void setTimeValidity(DvInterval<DvDate> timeValidity) {
        this.timeValidity = timeValidity;
    }

    /**
     * The qualifications of the performer of the role for this capability.
     * This might include professional qualifications and official
     * identifications such as provider numbers etc.
     *
     * @return credentials
     */
    public ItemStructure getCredentials() {
        return credentials;
    }

    protected void setCredentials(ItemStructure credentials) {
        this.credentials = credentials;
    }

    /**
     * Equals if two capabilities has same values
     *
     * @param o
     * @return equals if true
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!( o instanceof Capability )) return false;
        if (!super.equals(o)) return false;

        final Capability capability = (Capability) o;
        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(credentials, capability.credentials)
            .append(timeValidity, capability.timeValidity)
            .isEquals();
    }

    /**
     * Return a hash code of this object
     *
     * @return hash code
     */
    public int hashCode() {
        return new HashCodeBuilder(13, 29)
            .appendSuper(super.hashCode())
            .append(timeValidity)
            .append(credentials)
            .toHashCode();
    }

	@Override
	public String pathOfItem(Pathable arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object> itemsAtPath(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean pathExists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pathUnique(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/* fields */
    private DvInterval<DvDate> timeValidity;
    private ItemStructure credentials;
}

/*
 *  ***** BEGIN LICENSE BLOCK *****
 *  Version: MPL 1.1/GPL 2.0/LGPL 2.1
 *
 *  The contents of this file are subject to the Mozilla Public License Version
 *  1.1 (the 'License'); you may not use this file except in compliance with
 *  the License. You may obtain a copy of the License at
 *  http://www.mozilla.org/MPL/
 *
 *  Software distributed under the License is distributed on an 'AS IS' basis,
 *  WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 *  for the specific language governing rights and limitations under the
 *  License.
 *
 *  The Original Code is Capability.java
 *
 *  The Initial Developer of the Original Code is Goran Pestana.
 *  Portions created by the Initial Developer are Copyright (C) 2003-2005
 *  the Initial Developer. All Rights Reserved.
 *
 *  Contributor(s):
 *
 * Software distributed under the License is distributed on an 'AS IS' basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 *  ***** END LICENSE BLOCK *****
 */
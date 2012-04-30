/*
 * PackJacket - GUI frontend to IzPack to make Java-based installers
 * Copyright (C) 2008 - 2009  Amandeep Grewal, Manodasan Wignarajah
 *
 * PackJacket is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * PackJacket is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with PackJacket.  If not, see <http://www.gnu.org/licenses/>.
 */
package packjacket.xml;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Contains fields for the RadioComboCheck Database
 * @author Manodasan Wignarajah
 */
public class RadioComboCheck extends UserInput {

    /**
     * Used for PJC file backwards compability
     */
    public static final long serialVersionUID = 4L;
    /**
     * Stores the name of the variable
     */
    public String variable;//radio/combo - variable
    /**
     * Stores a boolean representation of whether description is to be shown
     */
    public boolean descriptionOption;//option for description
    /**
     * Stores the description
     */
    public String description;//for all
    /**
     * Stores how to align the description, used in all 3
     */
    public String description_align;
    /**
     * Stores the text for the combo, used only in combo
     */
    public String spec_txt;
    /**
     * Stores whether it is "radio", "combo" or "check"
     */
    public String real_type;
    /**
     * Stores all the choices
     */
    public Collection<Choice> choices = new ArrayList<Choice>();//all the choices, for check box, there are multiple fields
    /**
     * Stores the value of set attribute
     */
    public String set;//radio.combo, which one is selected first

    /**
     * Sets the type in UserInput by calling super
     */
    public RadioComboCheck() {
        super("radiocombocheck");
    }

    @Override
    public String toString() {
        return real_type.equals("check") ? "Check Boxes" : variable;
    }

    @Override
    public RadioComboCheck clone() {
        RadioComboCheck r = new RadioComboCheck();
        r.variable = variable;
        r.descriptionOption = descriptionOption;
        r.description = description;
        r.description_align = description_align;
        r.spec_txt = spec_txt;
        r.real_type = real_type;
        r.choices = new ArrayList<Choice>(choices);
        r.set = set;
        return r;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final RadioComboCheck other = (RadioComboCheck) obj;
        if ((this.variable == null) ? (other.variable != null) : !this.variable.equals(other.variable))
            return false;
        if (this.descriptionOption != other.descriptionOption)
            return false;
        if ((this.description == null) ? (other.description != null) : !this.description.equals(other.description))
            return false;
        if ((this.description_align == null) ? (other.description_align != null) : !this.description_align.equals(other.description_align))
            return false;
        if ((this.spec_txt == null) ? (other.spec_txt != null) : !this.spec_txt.equals(other.spec_txt))
            return false;
        if ((this.real_type == null) ? (other.real_type != null) : !this.real_type.equals(other.real_type))
            return false;
        if (this.choices != other.choices && (this.choices == null || !this.choices.equals(other.choices)))
            return false;
        if ((this.set == null) ? (other.set != null) : !this.set.equals(other.set))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + (this.variable != null ? this.variable.hashCode() : 0);
        hash = 47 * hash + (this.descriptionOption ? 1 : 0);
        hash = 47 * hash + (this.description != null ? this.description.hashCode() : 0);
        hash = 47 * hash + (this.description_align != null ? this.description_align.hashCode() : 0);
        hash = 47 * hash + (this.spec_txt != null ? this.spec_txt.hashCode() : 0);
        hash = 47 * hash + (this.real_type != null ? this.real_type.hashCode() : 0);
        hash = 47 * hash + (this.choices != null ? this.choices.hashCode() : 0);
        hash = 47 * hash + (this.set != null ? this.set.hashCode() : 0);
        return hash;
    }
}

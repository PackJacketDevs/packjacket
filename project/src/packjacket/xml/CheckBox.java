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

/**
 * Contains fields for the CheckBox Database
 * @author Manodasan Wignarajah
 */
public class CheckBox extends UserInput {

    /**
     * Used for PJC file backwards compability
     */
    public static final long serialVersionUID = 4L;
    /**
     * Stores the name of the variable specified by the user
     */
    public String variable;
    /**
     * Stores whether this checkbox is to have a description
     */
    public boolean descriptionOption; //if the following two fields are used
    /**
     * Stores the specified description
     */
    public String description;
    /**
     * Stores how the description is to be aligned
     */
    public String description_align;
    /**
     * Stores the text to display in the checkbox, used in the attribute for text in spec in UserInput
     */
    public String text;
    /**
     * Stores the value to use if the checkbox is selected, used in the spec as the true value in UserInput
     */
    public String trueIf;
    /**
     * Stores the value to use if the checkbox is not selected, used in spec as the false value in UserInput
     */
    public String falseIf;
    /**
     * Stores whether the CheckBox is selected or not.
     */
    public boolean set;

    /**
     * Assigns the value for type in UserInput by calling super
     */
    public CheckBox() {
        super("check");
    }

    @Override
    public String toString() {
        return variable;
    }

    @Override
    public CheckBox clone() {
        CheckBox u = new CheckBox();
        u.description = description;
        u.descriptionOption = descriptionOption;
        u.variable = variable;
        u.description_align = description_align;
        u.text = text;
        u.trueIf = trueIf;
        u.falseIf = falseIf;
        u.set = set;
        return u;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final CheckBox other = (CheckBox) obj;
        if ((this.variable == null) ? (other.variable != null) : !this.variable.equals(other.variable))
            return false;
        if (this.descriptionOption != other.descriptionOption)
            return false;
        if ((this.description == null) ? (other.description != null) : !this.description.equals(other.description))
            return false;
        if ((this.description_align == null) ? (other.description_align != null) : !this.description_align.equals(other.description_align))
            return false;
        if ((this.text == null) ? (other.text != null) : !this.text.equals(other.text))
            return false;
        if ((this.trueIf == null) ? (other.trueIf != null) : !this.trueIf.equals(other.trueIf))
            return false;
        if ((this.falseIf == null) ? (other.falseIf != null) : !this.falseIf.equals(other.falseIf))
            return false;
        if (this.set != other.set)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + (this.variable != null ? this.variable.hashCode() : 0);
        hash = 79 * hash + (this.descriptionOption ? 1 : 0);
        hash = 79 * hash + (this.description != null ? this.description.hashCode() : 0);
        hash = 79 * hash + (this.description_align != null ? this.description_align.hashCode() : 0);
        hash = 79 * hash + (this.text != null ? this.text.hashCode() : 0);
        hash = 79 * hash + (this.trueIf != null ? this.trueIf.hashCode() : 0);
        hash = 79 * hash + (this.falseIf != null ? this.falseIf.hashCode() : 0);
        hash = 79 * hash + (this.set ? 1 : 0);
        return hash;
    }
}


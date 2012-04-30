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
 * Contains fields for the Choice Database
 * @author Manodasan Wignarajah
 */
public class Choice extends UserInput {

    /**
     * Used for PJC file backwards compability
     */
    public static final long serialVersionUID = 4L;
    /**
     * Stores the text for the choice
     */
    public String text;
    /**
     * Stores the value to use if this choice is selected in the checkbox, used as the value for trueif attriubte
     */
    public String trueIf;
    /**
     * Stores the value to use if it isn't selected in the checkbox, the falseif value
     */
    public String falseIf;
    /**
     * Stores the name of the variable, used primarily in checkbox
     */
    public String variable;
    /**
     * Stores the value which is to be used in combo/radio
     */
    public String value;
    /**
     * Sets whehter it is a set and is used in checkbox
     */
    public boolean set; //checkbox - set

    /**
     * Sets the type in UserInput by calling super
     */
    public Choice() {
        super("choice");
    }

    @Override
    public String toString() {
        return text;
    }

    @Override
    public Choice clone() {
        Choice c = new Choice();
        c.text = text;
        c.trueIf = trueIf;
        c.falseIf = falseIf;
        c.variable = variable;
        c.value = value;
        c.set = set;
        return c;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Choice other = (Choice) obj;
        if ((this.text == null) ? (other.text != null) : !this.text.equals(other.text))
            return false;
        if ((this.trueIf == null) ? (other.trueIf != null) : !this.trueIf.equals(other.trueIf))
            return false;
        if ((this.falseIf == null) ? (other.falseIf != null) : !this.falseIf.equals(other.falseIf))
            return false;
        if ((this.variable == null) ? (other.variable != null) : !this.variable.equals(other.variable))
            return false;
        if ((this.value == null) ? (other.value != null) : !this.value.equals(other.value))
            return false;
        if (this.set != other.set)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + (this.text != null ? this.text.hashCode() : 0);
        hash = 37 * hash + (this.trueIf != null ? this.trueIf.hashCode() : 0);
        hash = 37 * hash + (this.falseIf != null ? this.falseIf.hashCode() : 0);
        hash = 37 * hash + (this.variable != null ? this.variable.hashCode() : 0);
        hash = 37 * hash + (this.value != null ? this.value.hashCode() : 0);
        hash = 37 * hash + (this.set ? 1 : 0);
        return hash;
    }
}

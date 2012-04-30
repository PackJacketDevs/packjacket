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
 * Contains fields for the Text Input Database
 * @author Manodasan Wignarajah
 */
public class TextInput extends UserInput {

    /**
     * Used for PJC file backwards compability
     */
    public static final long serialVersionUID = 4L;
    /**
     * Stores the name of the variable
     */
    public String variable;
    /**
     * Stores the text to display
     */
    public String text;
    /**
     * Stores the value of the set attribute, used as a default value to display on the text input field
     */
    public String set;
    /**
     * Stores the size of the field
     */
    public int size;
    /**
     * Stores the text of the description
     */
    public String description_text;
    /**
     * Stores the alignment of the description
     */
    public String description_align;
    /**
     * Stores whether the description option is chosen
     */
    public boolean descriptionOption;
    /**
     * Stores whether a value is specified for set
     */
    public boolean setOption;

    /**
     * Sets the value of type in UserInput by calling super
     */
    public TextInput() {
        super("text");
    }

    @Override
    public String toString() {
        return variable;
    }

    @Override
    public UserInput clone() {
        TextInput tt = new TextInput();
        tt.variable = variable;
        tt.text = text;
        tt.set = set;
        tt.size = size;
        tt.description_text = description_text;
        tt.description_align = description_align;
        tt.descriptionOption = descriptionOption;
        tt.setOption = setOption;
        return tt;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final TextInput other = (TextInput) obj;
        if ((this.variable == null) ? (other.variable != null) : !this.variable.equals(other.variable))
            return false;
        if ((this.text == null) ? (other.text != null) : !this.text.equals(other.text))
            return false;
        if ((this.set == null) ? (other.set != null) : !this.set.equals(other.set))
            return false;
        if (this.size != other.size)
            return false;
        if ((this.description_text == null) ? (other.description_text != null) : !this.description_text.equals(other.description_text))
            return false;
        if ((this.description_align == null) ? (other.description_align != null) : !this.description_align.equals(other.description_align))
            return false;
        if (this.descriptionOption != other.descriptionOption)
            return false;
        if (this.setOption != other.setOption)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.variable != null ? this.variable.hashCode() : 0);
        hash = 37 * hash + (this.text != null ? this.text.hashCode() : 0);
        hash = 37 * hash + (this.set != null ? this.set.hashCode() : 0);
        hash = 37 * hash + this.size;
        hash = 37 * hash + (this.description_text != null ? this.description_text.hashCode() : 0);
        hash = 37 * hash + (this.description_align != null ? this.description_align.hashCode() : 0);
        hash = 37 * hash + (this.descriptionOption ? 1 : 0);
        hash = 37 * hash + (this.setOption ? 1 : 0);
        return hash;
    }
}

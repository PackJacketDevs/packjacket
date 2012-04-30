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
 * Contains fields for the password field Database
 * @author Manodasan Wignarajah
 */
public class PasswordField extends UserInput {

    /**
     * Used for PJC file backwards compability
     */
    public static final long serialVersionUID = 4L;
    /**
     * Stores the name of the variable
     */
    public String variable;
    /**
     * Stores how to align the password field
     */
    public String align;
    /**
     * Stores the size for both password fields
     */
    public int size;
    /**
     * Stores the text for the first password field
     */
    public String text1;
    /**
     * Stores the text for the second password field
     */
    public String text2;//pwd txt #2

    /**Adds the following code automatically
    <code>
    <validator class="com.izforge.izpack.util.PasswordEqualityValidator"
    txt="Both passwords must match." id="lang pack key for the error text"/>
    </code>    
     */
    /**
     * Sets the type in UserInput by calling super
     */
    public PasswordField() {
        super("password");
    }

    @Override
    public String toString() {
        return variable;
    }

    @Override
    public PasswordField clone() {
        PasswordField pw = new PasswordField();
        pw.variable = variable;
        pw.align = align;
        pw.size = size;
        pw.text1 = text1;
        pw.text2 = text2;
        return pw;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final PasswordField other = (PasswordField) obj;
        if ((this.variable == null) ? (other.variable != null) : !this.variable.equals(other.variable))
            return false;
        if ((this.align == null) ? (other.align != null) : !this.align.equals(other.align))
            return false;
        if (this.size != other.size)
            return false;
        if ((this.text1 == null) ? (other.text1 != null) : !this.text1.equals(other.text1))
            return false;
        if ((this.text2 == null) ? (other.text2 != null) : !this.text2.equals(other.text2))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 43 * hash + (this.variable != null ? this.variable.hashCode() : 0);
        hash = 43 * hash + (this.align != null ? this.align.hashCode() : 0);
        hash = 43 * hash + this.size;
        hash = 43 * hash + (this.text1 != null ? this.text1.hashCode() : 0);
        hash = 43 * hash + (this.text2 != null ? this.text2.hashCode() : 0);
        return hash;
    }
}

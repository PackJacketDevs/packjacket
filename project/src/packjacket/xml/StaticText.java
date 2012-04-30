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
 * Contains fields for the Static Text Database
 * @author Manodasan Wignarajah
 */
public class StaticText extends UserInput {

    /**
     * Used for PJC file backwards compability
     */
    public static final long serialVersionUID = 4L;
    /**
     * Stores how to align the text
     */
    public String align;
    /**
     * Store the text to display
     */
    public String text;

    /**
     * Sets the type in UserInput by calling super
     */
    public StaticText() {
        super("staticText");
    }

    @Override
    public String toString() {
        return text;
    }

    @Override
    public UserInput clone() {
        StaticText st = new StaticText();
        st.align = align;
        st.text = text;
        return st;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final StaticText other = (StaticText) obj;
        if ((this.align == null) ? (other.align != null) : !this.align.equals(other.align))
            return false;
        if ((this.text == null) ? (other.text != null) : !this.text.equals(other.text))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.align != null ? this.align.hashCode() : 0);
        hash = 59 * hash + (this.text != null ? this.text.hashCode() : 0);
        return hash;
    }
}

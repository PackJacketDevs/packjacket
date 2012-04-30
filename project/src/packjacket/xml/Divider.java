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
 * Contains fields for the Divider Database
 * @author Manodasan Wignarajah
 */
public class Divider extends UserInput {

    /**
     * Used for PJC file backwards compability
     */
    public static final long serialVersionUID = 4L;
    /**
     * Sets how to align the divider
     */
    public String align;

    /**
     * Sets the value of type by calling super
     */
    public Divider() {
        super("divider");
    }

    @Override
    public String toString() {
        return "Divider";
    }

    @Override
    public UserInput clone() {
        Divider d = new Divider();
        d.align = align;
        return d;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Divider other = (Divider) obj;
        if ((this.align == null) ? (other.align != null) : !this.align.equals(other.align))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + (this.align != null ? this.align.hashCode() : 0);
        return hash;
    }
}

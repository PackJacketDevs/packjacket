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
 * Contains fields for the Locale Database
 * @author Manodasan Wignarajah
 */
public class Locale implements XMLInterface {

    /**
     * Used for PJC file backwards compability
     */
    public static final long serialVersionUID = 4L;
    /**
     * Stores the name of the language, preferred if the value is something like "English"
     */
    public String locale_langpack_default;

    @Override
    public Locale clone() {
        Locale l = new Locale();
        l.locale_langpack_default = locale_langpack_default;
        return l;
    }

    @Override
    public String toString() {
        return locale_langpack_default;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Locale other = (Locale) obj;
        if ((this.locale_langpack_default == null) ? (other.locale_langpack_default != null) : !this.locale_langpack_default.equals(other.locale_langpack_default))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + (this.locale_langpack_default != null ? this.locale_langpack_default.hashCode() : 0);
        return hash;
    }
}

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
 * Contains fields for the Operating System Database
 * @author Manodasan Wignarajah
 */
public class OperatingSystem implements XMLInterface {

    /**
     * Used for PJC file backwards compability
     */
    public static final long serialVersionUID = 4L;
    /**
     * Stores the Operating System family
     */
    public String family;
    /**
     * Stores the name of the Operating System
     */
    public String name;
    /**
     * Stores the version of the Operating System
     */
    public String vers;
    /**
     * Stores the arch of the Operating System
     */
    public String arch;

    @Override
    public OperatingSystem clone() {
        OperatingSystem os = new OperatingSystem();
        os.family = family;
        os.name = name;
        os.vers = vers;
        os.arch = arch;
        return os;
    }

    @Override
    public String toString() {
        return family + " " + name + " " + vers + " " + arch;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final OperatingSystem other = (OperatingSystem) obj;
        if ((this.family == null) ? (other.family != null) : !this.family.equals(other.family))
            return false;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name))
            return false;
        if ((this.vers == null) ? (other.vers != null) : !this.vers.equals(other.vers))
            return false;
        if ((this.arch == null) ? (other.arch != null) : !this.arch.equals(other.arch))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (this.family != null ? this.family.hashCode() : 0);
        hash = 97 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 97 * hash + (this.vers != null ? this.vers.hashCode() : 0);
        hash = 97 * hash + (this.arch != null ? this.arch.hashCode() : 0);
        return hash;
    }
}

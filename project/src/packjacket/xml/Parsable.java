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
 * Contains fields for the Parsable Database
 * @author Manodasan Wignarajah
 */
public class Parsable implements XMLInterface {

    /**
     * Used for PJC file backwards compability
     */
    public static final long serialVersionUID = 4L;
    /**
     * Stores the file to parse
     */
    public String targetfile;
    /**
     * Stores the type of file it is, and how to parse it, An example is "Java Properties File"
     */
    public String type;
    /**
     * Stores the encoding of the file
     */
    public String encoding;
    /**
     * Stores the Operating System on which to parse the specified file
     */
    public OperatingSystem os;

    @Override
    public String toString() {
        return targetfile;
    }

    @Override
    public Parsable clone() {
        Parsable p = new Parsable();
        p.targetfile = targetfile;
        p.type = type;
        p.encoding = encoding;
        p.os = os.clone();
        return p;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Parsable other = (Parsable) obj;
        if ((this.targetfile == null) ? (other.targetfile != null) : !this.targetfile.equals(other.targetfile))
            return false;
        if ((this.type == null) ? (other.type != null) : !this.type.equals(other.type))
            return false;
        if ((this.encoding == null) ? (other.encoding != null) : !this.encoding.equals(other.encoding))
            return false;
        if (this.os != other.os && (this.os == null || !this.os.equals(other.os)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (this.targetfile != null ? this.targetfile.hashCode() : 0);
        hash = 97 * hash + (this.type != null ? this.type.hashCode() : 0);
        hash = 97 * hash + (this.encoding != null ? this.encoding.hashCode() : 0);
        hash = 97 * hash + (this.os != null ? this.os.hashCode() : 0);
        return hash;
    }
}

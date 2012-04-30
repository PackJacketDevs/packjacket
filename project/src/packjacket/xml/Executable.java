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

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Contains fields for the Executable Database
 * @author Manodasan Wignarajah
 */
public class Executable implements XMLInterface {

    /**
     * Used for PJC file backwards compability
     */
    public static final long serialVersionUID = 4L;
    /**
     * Stores the value of the target file specified
     */
    public String targetfile;
    /**
     * Stores the main class if a jar is used as the target file
     */
    public String clas;
    /**
     * Stores the type of file
     */
    public String type;
    /**
     * Stores when to launch the executable
     */
    public String stage;
    /**
     * Stores what to do if the executable failed
     */
    public String failure;
    /**
     * Stores on what operating systems to run the executable
     */
    public OperatingSystem os;
    /**
     * Stores whether to keep the executable file after running it
     */
    public boolean keep;
    /**
     * Stores the arguments to parameter pass to the the file being executed
     */
    public Collection<String> args = new ArrayList<String>();

    @Override
    public String toString() {
        return new File(targetfile).getName();
    }

    @Override
    public Executable clone() {
        Executable e = new Executable();
        e.targetfile = targetfile;
        e.clas = clas;
        e.type = type;
        e.stage = stage;
        e.failure = failure;
        e.os = os.clone();
        e.keep = keep;
        e.args = new ArrayList<String>(args);
        return e;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Executable other = (Executable) obj;
        if ((this.targetfile == null) ? (other.targetfile != null) : !this.targetfile.equals(other.targetfile))
            return false;
        if ((this.clas == null) ? (other.clas != null) : !this.clas.equals(other.clas))
            return false;
        if ((this.type == null) ? (other.type != null) : !this.type.equals(other.type))
            return false;
        if ((this.stage == null) ? (other.stage != null) : !this.stage.equals(other.stage))
            return false;
        if ((this.failure == null) ? (other.failure != null) : !this.failure.equals(other.failure))
            return false;
        if (this.os != other.os && (this.os == null || !this.os.equals(other.os)))
            return false;
        if (this.keep != other.keep)
            return false;
        if (this.args != other.args && (this.args == null || !this.args.equals(other.args)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (this.targetfile != null ? this.targetfile.hashCode() : 0);
        hash = 67 * hash + (this.clas != null ? this.clas.hashCode() : 0);
        hash = 67 * hash + (this.type != null ? this.type.hashCode() : 0);
        hash = 67 * hash + (this.stage != null ? this.stage.hashCode() : 0);
        hash = 67 * hash + (this.failure != null ? this.failure.hashCode() : 0);
        hash = 67 * hash + (this.os != null ? this.os.hashCode() : 0);
        hash = 67 * hash + (this.keep ? 1 : 0);
        hash = 67 * hash + (this.args != null ? this.args.hashCode() : 0);
        return hash;
    }
}

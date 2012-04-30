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

import java.util.ArrayList;
import java.util.Collection;

/**
 * Contains fields for the XProcess Database
 * @author Manodasan Wignarajah
 */
public class XProcess implements XMLInterface {

    /**
     * Used for PJC file backwards compability
     */
    public static final long serialVersionUID = 4L;
    /**
     * Stores the job name of the process
     */
    public String job_name;
    /**
     * Stores the file to execute
     */
    public String executefile;
    /**
     * Stores the Operating System to do this task on
     */
    public OperatingSystem os;
    /**
     * Stores the arguments to parameter pass to the executeFile
     */
    public Collection<String> args = new ArrayList<String>();

    @Override
    public String toString() {
        return job_name;
    }

    @Override
    public XProcess clone() {
        XProcess p = new XProcess();
        p.job_name = job_name;
        p.executefile = executefile;
        p.os = os.clone();
        p.args = new ArrayList<String>(args);
        return p;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final XProcess other = (XProcess) obj;
        if ((this.job_name == null) ? (other.job_name != null) : !this.job_name.equals(other.job_name))
            return false;
        if ((this.executefile == null) ? (other.executefile != null) : !this.executefile.equals(other.executefile))
            return false;
        if (this.os != other.os && (this.os == null || !this.os.equals(other.os)))
            return false;
        if (this.args != other.args && (this.args == null || !this.args.equals(other.args)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + (this.job_name != null ? this.job_name.hashCode() : 0);
        hash = 11 * hash + (this.executefile != null ? this.executefile.hashCode() : 0);
        hash = 11 * hash + (this.os != null ? this.os.hashCode() : 0);
        hash = 11 * hash + (this.args != null ? this.args.hashCode() : 0);
        return hash;
    }
}

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
 * Contains fields for the Pack Database
 * @author Manodasan Wignarajah
 */
public class Pack implements XMLInterface {

    /**
     * Used for PJC file backwards compatibility
     */
    public static final long serialVersionUID = 4L;
    /**
     * Stores the name of the pack
     */
    public String pack_name;
    /**
     * Stores the depends for this pack
     */
    public Collection<String> pack_depends = new ArrayList<String>();
    /**
     * Stores the description for this pack
     */
    public String pack_description;
    /**
     * Stores whether this pack is a mandatory pack
     */
    public boolean pack_required;
    /**
     * Stores whether the pack is preselected
     */
    public boolean pack_preselected;
    /**
     * Stores the image path of the pack snapshot
     */
    public String pack_packImgId;
    /**
     * Stores the files in this pack
     */
    public Collection<XFile> file = new ArrayList<XFile>();
    /**
     * Stores the os to install this pack on
     */
    public OperatingSystem pack_os;
    /**
     * Stores the parent pack
     */
    public String pack_parent;
    /**
     * Stores the executables to run if this pack is installed
     */
    public Collection<Executable> executables = new ArrayList<Executable>();
    /**
     * Stores the files to parse
     */
    public Collection<Parsable> parsables = new ArrayList<Parsable>();
    /**
     * Whether this pack is hidden in the installer
     */
    public boolean pack_hidden;
    /**
     * Whether the files for this pack are included with the installer or not
     */
    public boolean pack_loose;
    /**
     * Stores the name of the the xml file which stores the packs
     */
    public String xmlFile;

    @Override
    public String toString() {
        return (pack_name == null || pack_name.equals("")) ? xmlFile : pack_name;
    }

    @Override
    public Pack clone() {
        Pack p = new Pack();
        p.pack_name = pack_name;
        p.pack_depends = new ArrayList<String>(pack_depends);
        p.pack_description = pack_description;
        p.pack_required = pack_required;
        p.pack_preselected = pack_preselected;
        p.pack_packImgId = pack_packImgId;
        p.file = new ArrayList<XFile>(file);
        p.pack_os = pack_os == null ? null : pack_os.clone();
        p.pack_parent = pack_parent;
        p.executables = new ArrayList<Executable>(executables);
        p.parsables = new ArrayList<Parsable>(parsables);
        p.pack_hidden = pack_hidden;
        p.pack_loose = pack_loose;
        p.xmlFile = xmlFile;
        return p;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Pack other = (Pack) obj;
        if ((this.pack_name == null) ? (other.pack_name != null) : !this.pack_name.equals(other.pack_name))
            return false;
        if ((this.xmlFile == null) ? (other.xmlFile != null) : !this.xmlFile.equals(other.xmlFile))
            return false;
        if (this.pack_depends != other.pack_depends && (this.pack_depends == null || !this.pack_depends.equals(other.pack_depends)))
            return false;
        if ((this.pack_description == null) ? (other.pack_description != null) : !this.pack_description.equals(other.pack_description))
            return false;
        if (this.pack_required != other.pack_required)
            return false;
        if (this.pack_hidden != other.pack_hidden)
            return false;
        if (this.pack_loose != other.pack_loose)
            return false;
        if (this.pack_preselected != other.pack_preselected)
            return false;
        if ((this.pack_packImgId == null) ? (other.pack_packImgId != null) : !this.pack_packImgId.equals(other.pack_packImgId))
            return false;
        if (this.file != other.file && (this.file == null || !this.file.equals(other.file)))
            return false;
        if (this.pack_os != other.pack_os && (this.pack_os == null || !this.pack_os.equals(other.pack_os)))
            return false;
        if ((this.pack_parent == null) ? (other.pack_parent != null) : !this.pack_parent.equals(other.pack_parent))
            return false;
        if (this.executables != other.executables && (this.executables == null || !this.executables.equals(other.executables)))
            return false;
        if (this.parsables != other.parsables && (this.parsables == null || !this.parsables.equals(other.parsables)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + (this.pack_name != null ? this.pack_name.hashCode() : 0);
        hash = 37 * hash + (this.xmlFile != null ? this.xmlFile.hashCode() : 0);
        hash = 37 * hash + (this.pack_depends != null ? this.pack_depends.hashCode() : 0);
        hash = 37 * hash + (this.pack_description != null ? this.pack_description.hashCode() : 0);
        hash = 37 * hash + (this.pack_required ? 1 : 0);
        hash = 37 * hash + (this.pack_hidden ? 1 : 0);
        hash = 37 * hash + (this.pack_loose ? 1 : 0);
        hash = 37 * hash + (this.pack_preselected ? 1 : 0);
        hash = 37 * hash + (this.pack_packImgId != null ? this.pack_packImgId.hashCode() : 0);
        hash = 37 * hash + (this.file != null ? this.file.hashCode() : 0);
        hash = 37 * hash + (this.pack_os != null ? this.pack_os.hashCode() : 0);
        hash = 37 * hash + (this.pack_parent != null ? this.pack_parent.hashCode() : 0);
        hash = 37 * hash + (this.executables != null ? this.executables.hashCode() : 0);
        hash = 37 * hash + (this.parsables != null ? this.parsables.hashCode() : 0);
        return hash;
    }
}

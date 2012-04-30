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

/**
 * Contains fields for the XFile Database
 * Called XFile to make sure not confused over java.io.File
 * @author Manodasan Wignarajah
 */
public class XFile implements XMLInterface {

    /**
     * Used for PJC file backwards compatibility
     */
    public static final long serialVersionUID = 4L;
    /**
     * Stores the file path specified for source file
     */
    public String packs_file_sourceFile;
    /**
     * Stores the dir path to put this file into(Target directory)
     */
    public String packs_file_targetdir;
    /**
     * Stores what to do if the file already exists in the dir
     */
    public String packs_file_overwrite;
    /**
     * Stores what to rename the target file to
     */
    public String packs_file_renameTarget;
    /**
     * Stores the file path for the source directory if not src file isn't specified
     */
    public String packs_file_sourceDir;
    /**
     * Stores whether the user is specifying a source file
     */
    public boolean sourceFileOption;
    /**
     * Stores whether the user is specifying a source directory
     */
    public boolean sourceDirOption;
    /**
     * Stores whether the rename target file option is chosen
     */
    public boolean renameTargetFileOption;
    /**
     * Stores the os to install this file on
     */
    public OperatingSystem packs_file_os;
    /**
     * If this file is an archive and if the value is true, then the content of the archive will be unpacked and added as individual files
     */
    public boolean packs_file_unpack;

    @Override
    public String toString() {
        if (sourceFileOption)
            if (renameTargetFileOption)
                return new File(packs_file_sourceFile).getName() + " / " + packs_file_renameTarget;
            else
                return new File(packs_file_sourceFile).getName();
        else
            return new File(packs_file_sourceDir).getName();
    }

    @Override
    public XFile clone() {
        XFile f = new XFile();
        f.packs_file_sourceFile = packs_file_sourceFile;
        f.packs_file_targetdir = packs_file_targetdir;
        f.packs_file_overwrite = packs_file_overwrite;
        f.packs_file_renameTarget = packs_file_renameTarget;
        f.packs_file_sourceDir = packs_file_sourceDir;
        f.sourceFileOption = sourceFileOption;
        f.sourceDirOption = sourceDirOption;
        f.renameTargetFileOption = renameTargetFileOption;
        f.packs_file_os = packs_file_os.clone();
        f.packs_file_unpack = packs_file_unpack;
        return f;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final XFile other = (XFile) obj;
        if ((this.packs_file_sourceFile == null) ? (other.packs_file_sourceFile != null) : !this.packs_file_sourceFile.equals(other.packs_file_sourceFile))
            return false;
        if ((this.packs_file_targetdir == null) ? (other.packs_file_targetdir != null) : !this.packs_file_targetdir.equals(other.packs_file_targetdir))
            return false;
        if ((this.packs_file_overwrite == null) ? (other.packs_file_overwrite != null) : !this.packs_file_overwrite.equals(other.packs_file_overwrite))
            return false;
        if ((this.packs_file_renameTarget == null) ? (other.packs_file_renameTarget != null) : !this.packs_file_renameTarget.equals(other.packs_file_renameTarget))
            return false;
        if ((this.packs_file_sourceDir == null) ? (other.packs_file_sourceDir != null) : !this.packs_file_sourceDir.equals(other.packs_file_sourceDir))
            return false;
        if (this.sourceFileOption != other.sourceFileOption)
            return false;
        if (this.packs_file_unpack != other.packs_file_unpack)
            return false;
        if (this.sourceDirOption != other.sourceDirOption)
            return false;
        if (this.renameTargetFileOption != other.renameTargetFileOption)
            return false;
        if (this.packs_file_os != other.packs_file_os && (this.packs_file_os == null || !this.packs_file_os.equals(other.packs_file_os)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.packs_file_sourceFile != null ? this.packs_file_sourceFile.hashCode() : 0);
        hash = 53 * hash + (this.packs_file_targetdir != null ? this.packs_file_targetdir.hashCode() : 0);
        hash = 53 * hash + (this.packs_file_overwrite != null ? this.packs_file_overwrite.hashCode() : 0);
        hash = 53 * hash + (this.packs_file_renameTarget != null ? this.packs_file_renameTarget.hashCode() : 0);
        hash = 53 * hash + (this.packs_file_sourceDir != null ? this.packs_file_sourceDir.hashCode() : 0);
        hash = 53 * hash + (this.sourceFileOption ? 1 : 0);
        hash = 53 * hash + (this.packs_file_unpack ? 1 : 0);
        hash = 53 * hash + (this.sourceDirOption ? 1 : 0);
        hash = 53 * hash + (this.renameTargetFileOption ? 1 : 0);
        hash = 53 * hash + (this.packs_file_os != null ? this.packs_file_os.hashCode() : 0);
        return hash;
    }
}

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
 * Contains fields for the FileField Database
 * @author Manodasan Wignarajah
 */
public class FileField extends UserInput {

    /**
     * Used for PJC file backwards compability
     */
    public static final long serialVersionUID = 4L;
    /**
     * Stores whether the file chooser is for choosing a file or directory, either "file" or "dir"
     */
    public String type_real;
    /**
     * Stores the name of the variable which will hold the file or dir chosen
     */
    public String variable;
    /**
     * Store the text of the static text
     */
    public String label;
    /**
     * Stores the alignment of the statc text and file chooser
     */
    public String align;
    /**
     * Stores the size, used in spec size attribute
     */
    public int size;
    /**
     * Stores the value of set, set the value to ""
     */
    public String set;

    /**
     * Sets the value of type by calling super
     */
    public FileField() {
        super("filedir");
    }

    @Override
    public String toString() {
        return variable;
    }

    @Override
    public FileField clone() {
        FileField ff = new FileField();
        ff.type_real = type_real;
        ff.variable = variable;
        ff.label = label;
        ff.align = align;
        ff.size = size;
        ff.set = set;
        return ff;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final FileField other = (FileField) obj;
        if ((this.type_real == null) ? (other.type_real != null) : !this.type_real.equals(other.type_real))
            return false;
        if ((this.variable == null) ? (other.variable != null) : !this.variable.equals(other.variable))
            return false;
        if ((this.label == null) ? (other.label != null) : !this.label.equals(other.label))
            return false;
        if ((this.align == null) ? (other.align != null) : !this.align.equals(other.align))
            return false;
        if (this.size != other.size)
            return false;
        if ((this.set == null) ? (other.set != null) : !this.set.equals(other.set))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + (this.type_real != null ? this.type_real.hashCode() : 0);
        hash = 89 * hash + (this.variable != null ? this.variable.hashCode() : 0);
        hash = 89 * hash + (this.label != null ? this.label.hashCode() : 0);
        hash = 89 * hash + (this.align != null ? this.align.hashCode() : 0);
        hash = 89 * hash + this.size;
        hash = 89 * hash + (this.set != null ? this.set.hashCode() : 0);
        return hash;
    }
}

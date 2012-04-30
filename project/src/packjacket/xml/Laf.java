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
 * Contains fields for the LAF Database
 * @author Manodasan Wignarajah
 */
public class Laf implements XMLInterface {

    /**
     * Used for PJC file backwards compability
     */
    public static final long serialVersionUID = 4L;
    /**
     * Stores the name of the laf
     */
    public String guiprefs_laf_name;
    /**
     * Stores whether the liquid decorate frames was chosen
     */
    public boolean guiprefs_laf_liquid_decorate_frames;
    /**
     * Stores whether the liquid decorate dialogs was chosen
     */
    public boolean guiprefs_laf_liquid_decorate_dialogs;
    /**
     * Stores the chosen looks variant for laf
     */
    public String guiprefs_laf_looks_variant;
    /**
     * Stores the chosen substance variant for laf
     */
    public String guiprefs_laf_substance_variant;
    /**
     * Stores the os this is applied to
     */
    public OperatingSystem guiprefs_laf_os;

    @Override
    public Laf clone() {
        Laf l = new Laf();
        l.guiprefs_laf_name = guiprefs_laf_name;
        l.guiprefs_laf_liquid_decorate_frames = guiprefs_laf_liquid_decorate_frames;
        l.guiprefs_laf_liquid_decorate_dialogs = guiprefs_laf_liquid_decorate_dialogs;
        l.guiprefs_laf_looks_variant = guiprefs_laf_looks_variant;
        l.guiprefs_laf_substance_variant = guiprefs_laf_substance_variant;
        l.guiprefs_laf_os = guiprefs_laf_os.clone();
        return l;
    }

    @Override
    public String toString() {
        return guiprefs_laf_name + " on " + guiprefs_laf_os;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Laf other = (Laf) obj;
        if ((this.guiprefs_laf_name == null) ? (other.guiprefs_laf_name != null) : !this.guiprefs_laf_name.equals(other.guiprefs_laf_name))
            return false;
        if (this.guiprefs_laf_liquid_decorate_frames != other.guiprefs_laf_liquid_decorate_frames)
            return false;
        if (this.guiprefs_laf_liquid_decorate_dialogs != other.guiprefs_laf_liquid_decorate_dialogs)
            return false;
        if ((this.guiprefs_laf_looks_variant == null) ? (other.guiprefs_laf_looks_variant != null) : !this.guiprefs_laf_looks_variant.equals(other.guiprefs_laf_looks_variant))
            return false;
        if ((this.guiprefs_laf_substance_variant == null) ? (other.guiprefs_laf_substance_variant != null) : !this.guiprefs_laf_substance_variant.equals(other.guiprefs_laf_substance_variant))
            return false;
        if (this.guiprefs_laf_os != other.guiprefs_laf_os && (this.guiprefs_laf_os == null || !this.guiprefs_laf_os.equals(other.guiprefs_laf_os)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + (this.guiprefs_laf_name != null ? this.guiprefs_laf_name.hashCode() : 0);
        hash = 29 * hash + (this.guiprefs_laf_liquid_decorate_frames ? 1 : 0);
        hash = 29 * hash + (this.guiprefs_laf_liquid_decorate_dialogs ? 1 : 0);
        hash = 29 * hash + (this.guiprefs_laf_looks_variant != null ? this.guiprefs_laf_looks_variant.hashCode() : 0);
        hash = 29 * hash + (this.guiprefs_laf_substance_variant != null ? this.guiprefs_laf_substance_variant.hashCode() : 0);
        hash = 29 * hash + (this.guiprefs_laf_os != null ? this.guiprefs_laf_os.hashCode() : 0);
        return hash;
    }
}

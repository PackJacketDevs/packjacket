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
package packjacket.xmlcreation;

import java.util.ArrayList;
import java.util.Collection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import packjacket.xml.OperatingSystem;
import packjacket.xml.Pack;

/**
 * Contains static methods which can be useful for all xml creation classes
 * @author Manodasan Wignarajah
 */
public class XMLUtils {

    /**
     * Returns a collection of OS Elements.  It it used when the OS family is all
     * @param operatingSystem the OperatingSystem to create the OS Elements for
     * @param xml the Document to use to create elements
     * @return Collection of OS Elements
     */
    public static Collection<Element> getOSElements(OperatingSystem operatingSystem, Document xml) {
        Collection<Element> osElements = new ArrayList<Element>();
        //Checks whether the family for the specified OperatingSystem is all, if it is, OS Element is created for all 3 Operating System
        if (operatingSystem.family.equals("All")) {
            osElements.add(getOSElement(operatingSystem, "windows", xml));
            osElements.add(getOSElement(operatingSystem, "unix", xml));
            osElements.add(getOSElement(operatingSystem, "mac", xml));
        } else
            osElements.add(getOSElement(operatingSystem, operatingSystem.family.toLowerCase(), xml));
        return osElements;
    }

    /**
     * Gets the OS Element for the given parameter pass
     * @param operatingSystem the OperatingSystem for which to create the OS element for
     * @param family the family of the operating system
     * @param xml the Document to use to create element
     * @return The OS Element with the values of attributes as the ones parameter passed, or null if all attributes had value of "All"
     */
    public static Element getOSElement(OperatingSystem operatingSystem, String family, Document xml) {
        Element os = xml.createElement("os");
        //Checks if family is not "All", to set as attribute
        if (!family.equals("All"))
            os.setAttribute("family", family);
        //Checks if name is not "All" to set as attribute
        if (!operatingSystem.name.equals("All"))
            os.setAttribute("name", operatingSystem.name.toLowerCase());
        //Checks if vers is not "All" to set as attribute
        if (!operatingSystem.vers.equals("All"))
            os.setAttribute("vers", operatingSystem.vers.toLowerCase());
        //Checks if arch is not "All" to set as attribute
        if (!operatingSystem.arch.equals("All"))
            os.setAttribute("arch", operatingSystem.arch.toLowerCase());
        //Checks if there is attributes, if there is, element is returned, otherwise null is returned
        return os.hasAttributes() ? os : null;
    }

    /**
     * Creates and returns an element with the specified tag and text content
     * @param tag the tag of the element
     * @param textContent the text content of the element
     * @param xml the Document to use to create the element
     * @return The element with the specified tag and text content
     */
    public static Element createElement(String tag, String textContent, Document xml) {
        Element element = xml.createElement(tag);
        //Sets the text content of the element
        element.setTextContent(textContent);
        return element;
    }

    /**
     * Gets the arg element
     * @param text the text content
     * @param xml the Document to use to create the element
     * @return the arg element
     */
    public static Element getArg(String text, Document xml, boolean value) {
        Element arg = xml.createElement("arg");
        if(value)
            arg.setAttribute("value", text);
        else
            arg.setTextContent(text);
        return arg;
    }

    /**
     * Returns a boolean which represents whether the pack is a pack or a refpack
     * @param p the pack to check
     * @return whether it is a pack (true) or if it is a refpack (false)
     */
    public static boolean isPack(Pack p) {
        if (p == null)
            return true;
        if (p.pack_name != null && !p.pack_name.equals(""))
            return true;
        return false;
    }
}

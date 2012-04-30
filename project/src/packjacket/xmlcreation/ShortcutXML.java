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

import com.sun.org.apache.xerces.internal.dom.DocumentImpl;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import java.io.FileOutputStream;
import java.io.IOException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import packjacket.xml.Shortcut;
import packjacket.xml.XML;

/**
 * Generates the xml file required for shortcuts
 * @author Manodasan Wignarajah
 */
public class ShortcutXML {

    Document xml;
    XML xmlObject;
    boolean unix;

    /**
     * Constructs the ShortcutXML object
     * @param xmlObject the XML data class
     * @param unix whether it is for unix or not
     */
    public ShortcutXML(XML xmlObject, boolean unix) {
        xml = new DocumentImpl();
        this.xmlObject = xmlObject;
        this.unix = unix;
    }

    /**
     * Gets the shortcuts element
     * @return the shortcuts element
     */
    private Element getShortcuts() {
        Element shortcuts = xml.createElement("shortcuts");
        shortcuts.appendChild(xml.createElement("skipIfNotSupported"));
        if (xmlObject.shortcuts_defaultCurrentUser)
            shortcuts.appendChild(xml.createElement("defaultCurrentUser"));
        shortcuts.appendChild(getProgramGroup());
        for (Shortcut s : xmlObject.shortcuts)
            shortcuts.appendChild(getShortcut(s));
        return shortcuts;
    }

    /**
     * Gets the program group element
     * @return the program group element
     */
    private Element getProgramGroup() {
        Element programGroup = xml.createElement("programGroup");
        programGroup.setAttribute("defaultName", xmlObject.shortcuts_programGroup_defaultName);
        programGroup.setAttribute("location", xmlObject.shortcuts_programGroup_location.replace("Applications (recommended)", "applications").replace("Start Menu", "startMenu"));
        return programGroup;
    }

    /**
     * Gets the shortcut element for shortcut s
     * @param s the shortcut to generate the element for
     * @return the shortcut element
     */
    private Element getShortcut(Shortcut s) {
        Element shortcut = xml.createElement("shortcut");
        //Adds the appropriate attributes with their respective values
        shortcut.setAttribute("name", s.shortcuts_shortcut_name);
        shortcut.setAttribute("programGroup", getTextValue(s.shortcuts_shortcut_programGroup));
        shortcut.setAttribute("desktop", getTextValue(s.shortcuts_shortcut_desktop));
        shortcut.setAttribute("applications", getTextValue(s.shortcuts_shortcut_application));
        shortcut.setAttribute("startMenu", getTextValue(s.shortcuts_shortcut_startMenu));
        shortcut.setAttribute("startup", getTextValue(s.shortcuts_shortcut_startup));
        if (s.targetOption) {
            if (unix)
                shortcut.setAttribute("type", "Application");
            shortcut.setAttribute("target", s.shortcuts_shortcut_target);
        } else if (s.urlOption && unix) {
            shortcut.setAttribute("type", "Link");
            shortcut.setAttribute("url", s.url);
        }
        if (unix) {
            shortcut.setAttribute("encoding", "UTF-8");
            if (s.shortcuts_shortcut_terminal)
                shortcut.setAttribute("terminal", s.shortcuts_shortcut_terminal + "");
            shortcut.setAttribute("KdeUsername", s.rootUser);
            if (s.requiresSudo)
                shortcut.setAttribute("KdeSubstUID", "true");
            if (s.allUsers)
                shortcut.setAttribute("createForAll", "true");
        }
        if (s.shortcuts_shortcut_commandLineOption)
            shortcut.setAttribute("commandLine", s.shortcuts_shortcut_commandLine);
        if (s.shortcuts_shortcut_workingDirectoryOption)
            shortcut.setAttribute("workingDirectory", s.shortcuts_shortcut_workingDirectory);
        if (!s.shortcuts_shortcut_description.equals(""))
            shortcut.setAttribute("description", s.shortcuts_shortcut_description);
        if (s.shortcuts_shortcut_iconFileOption) {
            shortcut.setAttribute("iconFile", s.shortcuts_shortcut_iconFile);
            shortcut.setAttribute("iconIndex", s.shortcuts_shortcut_iconIndex + "");
        }
        shortcut.setAttribute("initialState", !unix ? s.shortcuts_shortcut_initialState.toLowerCase().replace("no show", "noShow") : "normal");
        //Create shortcuts for certain pack
        for (String t : s.createForPacks) {
            Element createForPack = xml.createElement("createForPack");
            createForPack.setAttribute("name", t);
            shortcut.appendChild(createForPack);
        }
        return shortcut;
    }

    /**
     * Returns text value of boolean
     * @param b the boolean value
     * @return text representation of boolean
     */
    private String getTextValue(boolean b) {
        return b ? "yes" : "no";
    }

    /**
     * Generates the xml file
     * @param xmlObject the XML data class
     * @param fileName the file name to write as
     * @param unix whether on unix or not
     * @throws IOException any IO error caused while running
     */
    public static void generateXML(XML xmlObject, String fileName, boolean unix) throws IOException {
        //Creates the file, serializes and writes the xml file
        ShortcutXML creation = new ShortcutXML(xmlObject, unix);
        creation.xml.appendChild(creation.getShortcuts());
        System.out.println(fileName);
        FileOutputStream file = new FileOutputStream(fileName);
        OutputFormat f = new OutputFormat("XML", "UTF-8", true);
        f.setIndenting(true);
        f.setStandalone(true);
        f.setVersion("1.0");
        XMLSerializer s = new XMLSerializer(file, f);
        s.asDOMSerializer();
        s.serialize(creation.xml.getDocumentElement());
        file.close();
    }
}

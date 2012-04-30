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
import java.util.Collection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import packjacket.xml.XML;
import packjacket.xml.XProcess;

/**
 * Creates the XML file for the Process panel
 * @author Manodasan Wignarajah
 */
public class ProcessXML {

    Document xml;
    XML xmlObject;
    Collection<String> var;

    /**
     * Constructs the ProcessXML object
     * @param xmlObject the XML data class
     * @param var the vars being used in QuickXPort
     */
    public ProcessXML(XML xmlObject, Collection<String> var) {
        xml = new DocumentImpl();
        this.xmlObject = xmlObject;
        this.var = var;
    }

    /**
     * Gets the processing element
     * @return the processing element
     */
    private Element getProcessing() {
        Element processing = xml.createElement("processing");
        if (xmlObject.process_logFileOption) {
            Element element = xml.createElement("logfiledir");
            element.setTextContent(xmlObject.process_logFilePath);
            processing.appendChild(element);
        }
        if (xmlObject.userInputOption)
            if (xmlObject.userInput_ea.contains("List Variable")) {
                Element job = xml.createElement("job");
                job.setAttribute("name", "QuickXport");
                Element executefile = xml.createElement("executefile");
                executefile.setAttribute("name", "java");
                executefile.appendChild(XMLUtils.getArg("-jar", xml, false));
                executefile.appendChild(XMLUtils.getArg("$INSTALL_PATH/a.jar", xml, false));
                executefile.appendChild(XMLUtils.getArg("$INSTALL_PATH/" + xmlObject.userInput_ea_file, xml, false));
                executefile.appendChild(XMLUtils.getArg(xmlObject.userInput_ea.contains("&") ? "a" : "a", xml, false));
                //Traverses through the collection of vars, and appends the arg element
                for (String v : var)
                    executefile.appendChild(XMLUtils.getArg(v, xml, false));
                job.appendChild(executefile);
                processing.appendChild(job);
            }
        //Traverses through all the XProcesses added
        for (XProcess p : xmlObject.processes) {
            Element job = xml.createElement("job");
            job.setAttribute("name", p.job_name);
            Element os = XMLUtils.getOSElement(p.os, p.os.family, xml);
            if (os != null)
                job.appendChild(os);
            Element executeFile = xml.createElement("executefile");
            executeFile.setAttribute("name", p.executefile);
            for (String a : p.args)
                executeFile.appendChild(XMLUtils.getArg(a, xml,false));
            job.appendChild(executeFile);
            processing.appendChild(job);
        }
        return processing;
    }

    /**
     * Generates the xml file
     * @param xmlObject the XML data class
     * @param fileName the file name to write the xml file as
     * @param var the vars in QuickXPort
     * @throws IOException Catches any IO errors caused while writing file
     */
    public static void generateXML(XML xmlObject, String fileName, Collection<String> var) throws IOException {
        //Creates the file, serializes and writes the xml file
        ProcessXML creation = new ProcessXML(xmlObject, var);
        creation.xml.appendChild(creation.getProcessing());
        FileOutputStream file = new FileOutputStream(fileName);
        OutputFormat f = new OutputFormat();
        f.setOmitXMLDeclaration(true);
        f.setIndenting(true);
        f.setStandalone(true);
        f.setVersion("1.0");
        XMLSerializer s = new XMLSerializer(file, f);
        s.asDOMSerializer();
        s.serialize(creation.xml.getDocumentElement());
        file.close();
    }
}

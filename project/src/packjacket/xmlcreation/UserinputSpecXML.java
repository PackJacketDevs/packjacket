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
import java.util.ArrayList;
import java.util.Collection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import packjacket.xml.CheckBox;
import packjacket.xml.Choice;
import packjacket.xml.Divider;
import packjacket.xml.FileField;
import packjacket.xml.PasswordField;
import packjacket.xml.RadioComboCheck;
import packjacket.xml.StaticText;
import packjacket.xml.TextInput;
import packjacket.xml.UserInput;
import packjacket.xml.XML;

/**
 * Creates the XML file for the UserInput Panel
 * @author Manodasan Wignarajah
 */
public class UserinputSpecXML {

    Document xml;
    XML xmlObject;
    public static Collection<String> var;

    /**
     * Construtcts the UserInputSpecXML object
     * @param xmlObject the XML data class
     */
    public UserinputSpecXML(XML xmlObject) {
        xml = new DocumentImpl();
        this.xmlObject = xmlObject;
        var = new ArrayList<String>();
    }

    /**
     * Gets the UserInput element
     * @return the UserInput elemnet
     */
    private Element getUserInput() {
        Element userInput = xml.createElement("userInput");
        userInput.appendChild(getPanel());
        return userInput;
    }

    /**
     * Gets the panel element
     * @return the panel element
     */
    private Element getPanel() {
        Element panel = xml.createElement("panel");
        panel.setAttribute("order", "0");
        Element field = xml.createElement("field");
        field.setAttribute("type", "title");
        field.setAttribute("txt", xmlObject.userInput_title);
        if (xmlObject.userInput_italics)
            field.setAttribute("italic", "true");
        if (xmlObject.userInput_bold)
            field.setAttribute("bold", "true");
        field.setAttribute("size", xmlObject.userInput_font + "");
        panel.appendChild(field);
        for (UserInput u : xmlObject.userInputs) {
            if (u.type.equals("filedir"))
                panel.appendChild(getStaticText(((FileField) u).align, ((FileField) u).label));
            Collection<Element> fields = getFields(u);
            for (Element f : fields)
                panel.appendChild(f);
        }
        return panel;
    }

    /**
     * Gets the field element for u
     * @param u the UserInput to get field for
     * @return the field element
     */
    private Element getField(UserInput u) {
        Element field = xml.createElement("field");
        //Depending on the type, the appropriate attributes are set
        if (u.type.equals("staticText"))
            field = getStaticText(((StaticText) u).align, ((StaticText) u).text);
        else if (u.type.equals("check")) {
            addVariable(((CheckBox) u).variable);
            field.setAttribute("type", "check");
            field.setAttribute("variable", ((CheckBox) u).variable);
            if (((CheckBox) u).descriptionOption)
                field.appendChild(getDescription(((CheckBox) u).description_align, ((CheckBox) u).description));
            Element spec = xml.createElement("spec");
            spec.setAttribute("txt", ((CheckBox) u).text);
            spec.setAttribute("true", ((CheckBox) u).trueIf);
            spec.setAttribute("false", ((CheckBox) u).falseIf);
            if (((CheckBox) u).set)
                spec.setAttribute("set", "true");
            field.appendChild(spec);
        } else if (u.type.equals("Space"))
            field.setAttribute("type", "space");
        else if (u.type.equals("filedir")) {
            addVariable(((FileField) u).variable);
            field.setAttribute("type", ((FileField) u).type_real);
            field.setAttribute("align", ((FileField) u).align.toLowerCase().replace("centre", "center"));
            field.setAttribute("variable", ((FileField) u).variable);
            Element spec = xml.createElement("spec");
            spec.setAttribute("txt", "");
            spec.setAttribute("size", ((FileField) u).size + "");
            spec.setAttribute("set", ((FileField) u).set + "");
            field.appendChild(spec);
        } else if (u.type.equals("divider")) {
            field.setAttribute("type", "divider");
            field.setAttribute("align", ((Divider) u).align.toLowerCase());
        } else if (u.type.equals("password")) {
            addVariable(((PasswordField) u).variable);
            field.setAttribute("type", "password");
            field.setAttribute("align", ((PasswordField) u).align.toLowerCase().replace("centre", "center"));
            field.setAttribute("variable", ((PasswordField) u).variable);
            Element spec = xml.createElement("spec");
            spec.appendChild(getPwd(((PasswordField) u).text1, ((PasswordField) u).size + "", ""));
            spec.appendChild(getPwd(((PasswordField) u).text2, ((PasswordField) u).size + "", ""));
            field.appendChild(spec);
            Element validator = xml.createElement("validator");
            validator.setAttribute("class", "com.izforge.izpack.util.PasswordEqualityValidator");
            validator.setAttribute("txt", "Both passwords must match.");
            field.appendChild(validator);
        } else if (u.type.equals("radiocombocheck")) {
            if (((RadioComboCheck) u).real_type.equals("radio") || ((RadioComboCheck) u).real_type.equals("combo")) {
                addVariable(((RadioComboCheck) u).variable);
                field.setAttribute("type", ((RadioComboCheck) u).real_type);
                field.setAttribute("variable", ((RadioComboCheck) u).variable);
                if (((RadioComboCheck) u).descriptionOption)
                    field.appendChild(getDescription(((RadioComboCheck) u).description_align, ((RadioComboCheck) u).description));
                Element spec = xml.createElement("spec");
                if (((RadioComboCheck) u).real_type.equals("combo"))
                    spec.setAttribute("txt", ((RadioComboCheck) u).spec_txt);
                for (Choice c : ((RadioComboCheck) u).choices) {
                    Element choice = xml.createElement("choice");
                    choice.setAttribute("txt", c.text);
                    choice.setAttribute("value", c.value);
                    if (((RadioComboCheck) u).set.equals(c.text))
                        choice.setAttribute("set", "true");
                    spec.appendChild(choice);
                }
                field.appendChild(spec);
            }
        } else if (u.type.equals("text")) {
            addVariable(((TextInput) u).variable);
            field.setAttribute("type", "text");
            field.setAttribute("variable", ((TextInput) u).variable);
            if (((TextInput) u).descriptionOption)
                field.appendChild(getDescription(((TextInput) u).description_align, ((TextInput) u).description_text));
            Element spec = xml.createElement("spec");
            spec.setAttribute("txt", ((TextInput) u).text);
            spec.setAttribute("size", ((TextInput) u).size + "");
            if (((TextInput) u).setOption)
                spec.setAttribute("set", ((TextInput) u).set + "");
            field.appendChild(spec);
        }
        return field;
    }

    /**
     * Gets field element and stores as collection
     * @param u the user input to get the fields for
     * @return Collection<Element> of fields
     */
    private Collection<Element> getFields(UserInput u) {
        Collection<Element> fields = new ArrayList<Element>();
        //Depending on the type th appropriate action is done
        if (u.type.equals("radiocombocheck") && ((RadioComboCheck) u).real_type.equals("check"))
            for (Choice c : ((RadioComboCheck) u).choices) {
                addVariable(c.variable);
                Element field = xml.createElement("field");
                field.setAttribute("type", "check");
                field.setAttribute("variable", c.variable);
                if (((RadioComboCheck) u).descriptionOption)
                    field.appendChild(getDescription(((RadioComboCheck) u).description_align, ((RadioComboCheck) u).description));
                Element spec = xml.createElement("spec");
                spec.setAttribute("txt", c.text);
                spec.setAttribute("true", c.trueIf);
                spec.setAttribute("false", c.falseIf);
                if (c.set)
                    spec.setAttribute("set", c.set + "");
                field.appendChild(spec);
                fields.add(field);
            }
        else
            fields.add(getField(u));
        return fields;
    }

    /**
     * Gets the description element
     * @param align the alignment
     * @param txt the txt to display
     * @return the description element
     */
    private Element getDescription(String align, String txt) {
        Element description = xml.createElement("description");
        description.setAttribute("align", align.toLowerCase().replace("centre", "center"));
        description.setAttribute("txt", txt);
        return description;
    }

    /**
     * Gets the static text element
     * @param align the alignment
     * @param text the text to display
     * @return the static text element
     */
    private Element getStaticText(String align, String text) {
        Element field = xml.createElement("field");
        field.setAttribute("type", "staticText");
        field.setAttribute("align", align.toLowerCase().replace("centre", "center"));
        field.setAttribute("txt", text);
        return field;
    }

    /**
     * Gets the pwd elemnet
     * @param text the text to display
     * @param size the size of field
     * @param set the initial value
     * @return the pwd element
     */
    private Element getPwd(String text, String size, String set) {
        Element pwd = xml.createElement("pwd");
        pwd.setAttribute("txt", text);
        pwd.setAttribute("size", size);
        pwd.setAttribute("set", set);
        return pwd;
    }

    /**
     * Adds variable to list
     * @param variable variable to add
     */
    private void addVariable(String variable) {
        //Checks which type of QuickXport and does appropriate action
        if (xmlObject.userInput_ea.equals("List Variable Values"))
            var.add("${" + variable + "}");
        else if (xmlObject.userInput_ea.equals("List Variable Names & Values"))
            var.add(variable + ": ${" + variable + "}");
    }

    /**
     * Generates the UserInputSpec xml file
     * @param xmlObject the XML dataclass
     * @param fileName the file name to write as
     * @throws IOException Exception catches any error caused while writing file
     */
    public static void generateXML(XML xmlObject, String fileName) throws IOException {
        //Creates the file, serializes and writes the xml file
        UserinputSpecXML creation = new UserinputSpecXML(xmlObject);
        creation.xml.appendChild(creation.getUserInput());
        FileOutputStream file = new FileOutputStream(fileName);
        OutputFormat f = new OutputFormat("XML", "UTF-8", true);
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

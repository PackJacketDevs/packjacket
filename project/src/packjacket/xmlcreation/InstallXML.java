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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import packjacket.RunnerClass;
import packjacket.xml.Author;
import packjacket.xml.Executable;
import packjacket.xml.Laf;
import packjacket.xml.Locale;
import packjacket.xml.OperatingSystem;
import packjacket.xml.Pack;
import packjacket.xml.Parsable;
import packjacket.xml.XFile;
import packjacket.xml.XML;

/**
 * Generates the main XML file
 * @author Manodasan Wignarajah
 */
public class InstallXML {

    Document xml;
    XML xmlObject;
    static String mainXMLFileName;
    static String unixShortcuts;
    static String shortcuts;
    static String processXML;
    static String userInputSpecXML;
    static String dir;

    /**
     * Constructs the InstallXML object
     * @param xmlObject the XML dataclass
     * @param fileName the file name to write to
     * @throws IOException Any exception caused while writing file
     */
    public InstallXML(XML xmlObject, String fileName) throws IOException {
        xml = new DocumentImpl();
        this.xmlObject = xmlObject;
        mainXMLFileName = fileName;
        File xmlFile = new File(fileName);
        //Determines the dir to write the xml file in
        dir = xmlFile.getParent() + System.getProperty("file.separator");
        //Determines the names of the other xml files
        unixShortcuts = dir + xmlFile.getName().substring(0, xmlFile.getName().length() - 4) + "_Unix_shortcutSpec.xml";
        shortcuts = dir + xmlFile.getName().substring(0, xmlFile.getName().length() - 4) + "_shortcutSpec.xml";
        processXML = dir + xmlFile.getName().substring(0, xmlFile.getName().length() - 4) + "_processPanelSpec.xml";
        userInputSpecXML = dir + xmlFile.getName().substring(0, xmlFile.getName().length() - 4) + "_userInputSpec.xml";
    }

    /**
     * Gets the installation element
     * @return the installation element
     */
    private Element getInstallation() {
        //Creates the installation element and adds atributes and appends the appropriate element if their respective features were used
        Element installation = xml.createElement("installation");
        installation.setAttribute("version", "1.0");
        installation.appendChild(getInfo());
        if (xmlObject.packagingOption)
            installation.appendChild(getPackaging());
        installation.appendChild(getGuiPrefs());
        installation.appendChild(getLocale());
        Element resources = getResources();
        if (resources != null)
            installation.appendChild(resources);
        installation.appendChild(getPanels());
        Element variables = getVariables();
        if (variables != null)
            installation.appendChild(variables);
        installation.appendChild(getPacks());
        Element nativeLibrary32 = getNativeLibrary32();
        if (nativeLibrary32 != null)
            installation.appendChild(nativeLibrary32);
        Element nativeLibrary64 = getNativeLibrary64();
        if (nativeLibrary64 != null)
            installation.appendChild(nativeLibrary64);
        return installation;
    }

    /**
     * Gets the info element
     * @return the info element
     */
    private Element getInfo() {
        Element info = xml.createElement("info");
        //Appends the respective elements to the info element
        info.appendChild(XMLUtils.createElement("appname", xmlObject.info_appname, xml));
        info.appendChild(XMLUtils.createElement("appversion", xmlObject.info_appversion, xml));
        if (!xmlObject.info_appsubpath.equals(""))
            info.appendChild(XMLUtils.createElement("appsubpath", xmlObject.info_appsubpath, xml));
        //Checks whether the user added any authors
        if (xmlObject.authors.size() != 0) {
            Element authors = xml.createElement("authors");
            //Traverses through all the authors
            for (Author a : xmlObject.authors) {
                Element author = xml.createElement("author");
                author.setAttribute("email", a.email);
                author.setAttribute("name", a.name);
                authors.appendChild(author);
            }
            info.appendChild(authors);
        }
        if (!xmlObject.info_url.equals(""))
            info.appendChild(XMLUtils.createElement("url", (!(xmlObject.info_url.toLowerCase().startsWith("http://") || xmlObject.info_url.toLowerCase().startsWith("https://")) ? "http://" : "") + xmlObject.info_url, xml));
        Element uninstaller = xml.createElement("uninstaller");
        uninstaller.setAttribute("write", xmlObject.info_uninstaller_write ? "yes" : "no");
        if (xmlObject.info_uninstaller_write)
            uninstaller.setAttribute("name", !xmlObject.info_uninstaller_name.equals("") ? xmlObject.info_uninstaller_name : "uninstall.jar");
        info.appendChild(uninstaller);
        if (!xmlObject.info_javaversion.equals(""))
            info.appendChild(XMLUtils.createElement("javaversion", xmlObject.info_javaversion, xml));
        info.appendChild(XMLUtils.createElement("requiresjdk", xmlObject.info_requiresjdk ? "yes" : "no", xml));
        if (!xmlObject.info_webdir.equals(""))
            info.appendChild(XMLUtils.createElement("webdir", (!(xmlObject.info_webdir.toLowerCase().startsWith("http://") || xmlObject.info_webdir.toLowerCase().startsWith("https://")) ? "http://" : "") + xmlObject.info_webdir, xml));
        if (!xmlObject.info_summarylogfilepath.equals(""))
            info.appendChild(XMLUtils.createElement("summarylogfilepath", xmlObject.info_summarylogfilepath, xml));
        info.appendChild(XMLUtils.createElement("writeinstallationinformation", xmlObject.info_writeinstallationinformation ? "yes" : "no", xml));
        if (xmlObject.info_pack200)
            info.appendChild(xml.createElement("pack200"));
        if (xmlObject.info_privellegedAccess) {
            Element runPrivelleged = xml.createElement("run-privileged");
            runPrivelleged.setAttribute("condition", "izpack.windowsinstall.vista|izpack.windowsinstall.7|izpack.macinstall");
            info.appendChild(runPrivelleged);
        }
        return info;
    }

    /**
     * Gets the packaging element
     * @return the packaging element
     */
    private Element getPackaging() {
        Element packaging = xml.createElement("packaging");
        //Adds the appropriate attributes and elements
        Element packager = xml.createElement("packager");
        packager.setAttribute("class", "com.izforge.izpack.compiler.MultiVolumePackager");
        Element options = xml.createElement("options");
        options.setAttribute("volumesize", xmlObject.packaging_volumeSize);
        options.setAttribute("firstvolumefreespace", xmlObject.packaging_firstvolumefreespace);
        packager.appendChild(options);
        packaging.appendChild(packager);
        Element unpacker = xml.createElement("unpacker");
        unpacker.setAttribute("class", "com.izforge.izpack.installer.MultiVolumeUnpacker");
        packaging.appendChild(unpacker);
        return packaging;
    }

    /**
     * Gets the GUIPrefs element
     * @return the GUIPrefs element
     */
    private Element getGuiPrefs() {
        Element guiPrefs = xml.createElement("guiprefs");
        //Adds the appropriate attributes
        guiPrefs.setAttribute("width", xmlObject.guiprefs_width + "");
        guiPrefs.setAttribute("height", xmlObject.guiprefs_height + "");
        guiPrefs.setAttribute("resizable", xmlObject.guiprefs_resizable ? "yes" : "no");
        //Traverses through all the lafs
        for (Laf laf : xmlObject.lafs) {
            Element lafElement = xml.createElement("laf");
            lafElement.setAttribute("name", laf.guiprefs_laf_name.replace("JGoodies Looks", "looks").toLowerCase());
            if (lafElement.getAttribute("name").equals("liquid")) {
                lafElement.appendChild(getElement("param", "decorate.frames", laf.guiprefs_laf_liquid_decorate_frames ? "yes" : "no"));
                lafElement.appendChild(getElement("param", "decorate.dialogs", laf.guiprefs_laf_liquid_decorate_dialogs ? "yes" : "no"));
            } else if (lafElement.getAttribute("name").equals("looks"))
                lafElement.appendChild(getElement("param", "variant", laf.guiprefs_laf_looks_variant.toLowerCase().replace("xp", "XP").replace("3d", "3D")));
            else if (lafElement.getAttribute("name").equals("substance"))
                lafElement.appendChild(getElement("param", "variant", laf.guiprefs_laf_substance_variant.toLowerCase()));
            for (Element a : XMLUtils.getOSElements(laf.guiprefs_laf_os, xml))
                lafElement.appendChild(a);
            guiPrefs.appendChild(lafElement);
        }
        guiPrefs.appendChild(getElement("modifier", "useFlags", xmlObject.modifier_useFlags ? "yes" : "no"));
        guiPrefs.appendChild(getElement("modifier", "langDisplayType", xmlObject.modifier_langDisplayType.toLowerCase()));
        //Adds the elements if the repsective feature was selected to be used
        if (xmlObject.guiprefs_useHeadingPanel) {
            guiPrefs.appendChild(getElement("modifier", "useHeadingPanel", "yes"));
            guiPrefs.appendChild(getElement("modifier", "headingImageOnLeft", !xmlObject.guiprefs_headingImageOnLeftOrRight.toLowerCase().equals("right") ? "yes" : "no"));
            if (!xmlObject.guiprefs_headingFontSize.equals(""))
                guiPrefs.appendChild(getElement("modifier", "headingFontSize", xmlObject.guiprefs_headingFontSize));
            guiPrefs.appendChild(getElement("modifier", "headingBackgroundColor", xmlObject.guiprefs_headingBackgroundColor));
            guiPrefs.appendChild(getElement("modifier", "headingForegroundColor", xmlObject.guiprefs_headingForegroundColor));
        }
        if (xmlObject.guiprefs_counterOption) {
            guiPrefs.appendChild(getElement("modifier", "headingPanelCounter", xmlObject.guiprefs_headingPanelCounter.replace(" ", "").toLowerCase()));
            guiPrefs.appendChild(getElement("modifier", "headingPanelCounterPos", xmlObject.guiprefs_headingPanelCounterPos.replace("In Navigation Bar", "inNavigationPanel").replace("In Header", "inHeading")));
        }
        return guiPrefs;
    }

    /**
     * Gets the locale element
     * @return the locale element
     */
    private Element getLocale() {
        Element locale = xml.createElement("locale");
        //Traverses through all the added languages
        for (Locale language : xmlObject.langs) {
            String lang = "";
            //Checks which language it is
            if (language.locale_langpack_default.equals("Catalunyan"))
                lang = "cat";
            else if (language.locale_langpack_default.equals("Chinese (Simplified)"))
                lang = "chn";
            else if (language.locale_langpack_default.equals("Chinese (Traditional)"))
                lang = "twn";
            else if (language.locale_langpack_default.equals("Czech"))
                lang = "cze";
            else if (language.locale_langpack_default.equals("Danish"))
                lang = "dan";
            else if (language.locale_langpack_default.equals("Dutch"))
                lang = "ned";
            else if (language.locale_langpack_default.equals("Galician"))
                lang = "glg";
            else if (language.locale_langpack_default.equals("German"))
                lang = "deu";
            else if (language.locale_langpack_default.equals("English"))
                lang = "eng";
            else if (language.locale_langpack_default.equals("Finnish"))
                lang = "fin";
            else if (language.locale_langpack_default.equals("French"))
                lang = "fra";
            else if (language.locale_langpack_default.equals("Greek"))
                lang = "ell";
            else if (language.locale_langpack_default.equals("Hungarian"))
                lang = "hun";
            else if (language.locale_langpack_default.equals("Indonesian"))
                lang = "ind";
            else if (language.locale_langpack_default.equals("Italian"))
                lang = "ita";
            else if (language.locale_langpack_default.equals("Japanese"))
                lang = "jpn";
            else if (language.locale_langpack_default.equals("Korean"))
                lang = "kor";
            else if (language.locale_langpack_default.equals("Malaysian"))
                lang = "mys";
            else if (language.locale_langpack_default.equals("Nederlands"))
                lang = "ned";
            else if (language.locale_langpack_default.equals("Norwegian"))
                lang = "nor";
            else if (language.locale_langpack_default.equals("Persian"))
                lang = "fa";
            else if (language.locale_langpack_default.equals("Polish"))
                lang = "pol";
            else if (language.locale_langpack_default.equals("Portuguese (Brazilian)"))
                lang = "por";
            else if (language.locale_langpack_default.equals("Romanian"))
                lang = "rom";
            else if (language.locale_langpack_default.equals("Russian"))
                lang = "rus";
            else if (language.locale_langpack_default.equals("Serbian"))
                lang = "scg";
            else if (language.locale_langpack_default.equals("Spanish"))
                lang = "spa";
            else if (language.locale_langpack_default.equals("Slovakian"))
                lang = "svk";
            else if (language.locale_langpack_default.equals("Swedish"))
                lang = "swe";
            else if (language.locale_langpack_default.equals("Turkish"))
                lang = "tur";
            else if (language.locale_langpack_default.equals("Ukrainian"))
                lang = "ukr";
            Element langPack = xml.createElement("langpack");
            langPack.setAttribute("iso3", lang);
            locale.appendChild(langPack);
        }
        return locale;
    }

    /**
     * Gets the resources element
     * @return the resources element
     */
    private Element getResources() {
        Element resources = xml.createElement("resources");
        //Adds the appropriate resources depending on what is selected by the user
        if (xmlObject.panels.contains("InfoPanel"))
            resources.appendChild(getResource(xmlObject.resources_LicencePanel_license_src_info.endsWith(".html") ? "HTMLInfoPanel.info" : "InfoPanel.info", xmlObject.resources_LicencePanel_license_src_info, true));
        if (xmlObject.panels.contains("LicencePanel"))
            resources.appendChild(getResource(xmlObject.resources_LicencePanel_license_src_license.endsWith(".html") ? "HTMLLicencePanel.licence" : "LicencePanel.licence", xmlObject.resources_LicencePanel_license_src_license, true));
        if (xmlObject.panels.contains("HTMLHelloPanel"))
            resources.appendChild(getResource("HTMLHelloPanel.info", xmlObject.resources_HTMLHelloPanel_HTML, true));
        if (!xmlObject.shortcuts_programGroup_defaultName.equals("")) {
            resources.appendChild(getResource("shortcutSpec.xml", shortcuts.replace(dir, ""), false));
            resources.appendChild(getResource("Unix_shortcutSpec.xml", unixShortcuts.replace(dir, ""), false));
        }
        //Traverses through all the packs and checks whether to add the packImgId resource
        for (Pack pack : xmlObject.packs)
            if (XMLUtils.isPack(pack) && !pack.pack_packImgId.equals(""))
                resources.appendChild(getResource(pack.pack_name, pack.pack_packImgId, false));
        if (!xmlObject.langselimg.equals(""))
            resources.appendChild(getResource("installer.langsel.img", xmlObject.langselimg, false));
        if (!xmlObject.installerImage.equals(""))
            resources.appendChild(getResource("Installer.image", xmlObject.installerImage, false));
        if (!xmlObject.guiprefs_headingImg.equals(""))
            resources.appendChild(getResource("Heading.image", xmlObject.guiprefs_headingImg, false));
        if (xmlObject.userInputOption)
            resources.appendChild(getResource("userInputSpec.xml", userInputSpecXML.replace(dir, ""), false));
        if (xmlObject.processes.size() != 0 || xmlObject.userInput_ea.contains("List Variable"))
            resources.appendChild(getResource("ProcessPanel.Spec.xml", processXML.replace(dir, ""), false));
        return resources.hasChildNodes() ? resources : null;
    }

    /**
     * Gets the resource element for the specified fields
     * @param id the id of resource
     * @param src the source of resource
     * @param parse whether to parse resource
     * @return the resource element
     */
    private Element getResource(String id, String src, boolean parse) {
        Element res = xml.createElement("res");
        res.setAttribute("id", id);
        res.setAttribute("src", src);
        if (parse)
            res.setAttribute("parse", "yes");
        return res;
    }

    /**
     * Gets the panels element
     * @return the panels element
     */
    private Element getPanels() {
        Element panels = xml.createElement("panels");
        //Traverses through all the panels
        for (String s : xmlObject.panels) {
            if (s.contains("FinishPanel")) {
                if (xmlObject.userInput_ea.contains("List Variable") && !xmlObject.panels.contains("ProcessPanel"))
                    panels.appendChild(getPanel("ProcessPanel"));
                if (!xmlObject.shortcuts_programGroup_defaultName.equals(""))
                    panels.appendChild(getPanel("ShortcutPanel"));
            }
            panels.appendChild(getPanel((isHTMLPanel(s) ? "HTML" : "") + s));
        }
        return panels;
    }

    /**
     * Gets the panel element for the specified attribute
     * @param attribute the class name
     * @return the panel element
     */
    private Element getPanel(String attribute) {
        Element panel = xml.createElement("panel");
        panel.setAttribute("classname", attribute);
        return panel;
    }

    /**
     * Checks whether it is a HTML panel to insert resource
     * @param panel the panel to check
     * @return boolean representing whether it is or not
     */
    private boolean isHTMLPanel(String panel) {
        return (panel.equals("InfoPanel") && xmlObject.resources_LicencePanel_license_src_info.endsWith(".html")) || (panel.equals("LicencePanel") && xmlObject.resources_LicencePanel_license_src_license.endsWith(".html"));
    }

    /**
     * Gets the variables element
     * @return the variables element
     */
    private Element getVariables() {
        if (xmlObject.DesktopShortcutCheckboxEnabled) {
            Element variables = xml.createElement("variables");
            variables.appendChild(getElement("variable", "DesktopShortcutCheckboxEnabled", xmlObject.DesktopShortcutCheckboxEnabled + ""));
            return variables;
        }
        return null;
    }

    /**
     * Gets the packs element
     * @return the packs element
     */
    private Element getPacks() {
        Element packs = xml.createElement("packs");
        //Adds the QuickXPort pack if QuickXPort feature is used
        if (xmlObject.userInputOption && !xmlObject.userInput_ea.equals("Disabled")) {
            Element pack = xml.createElement("pack");
            pack.setAttribute("name", "QuickXport");
            pack.setAttribute("required", "yes");
            pack.setAttribute("preselected", "yes");
            pack.setAttribute("hidden", "true");
            pack.appendChild(XMLUtils.createElement("description", "This pack contains the required file for the QuickXport feature.", xml));
            try {
                pack.appendChild(getFile(new File(RunnerClass.homedir + "a.jar").getCanonicalPath(), "$INSTALL_PATH/", "true", false, null, false));
            } catch (IOException ex) {
                RunnerClass.logger.log(Level.SEVERE, null, ex);
            }
            packs.appendChild(pack);
        }
        //Traverses through all the packs added
        for (Pack p : xmlObject.packs)
            if (XMLUtils.isPack(p)) {
                Element pack = xml.createElement("pack");
                //Adds the appropriate attributes their their values
                pack.setAttribute("name", p.pack_name);
                pack.setAttribute("required", p.pack_required ? "yes" : "no");
                if (!p.pack_parent.equals(""))
                    pack.setAttribute("parent", p.pack_parent);
                pack.setAttribute("preselected", p.pack_preselected ? "yes" : "no");
                if (p.pack_hidden)
                    pack.setAttribute("hidden", "true");
                if (p.pack_loose)
                    pack.setAttribute("loose", "true");
                if (!p.pack_packImgId.equals(""))
                    pack.setAttribute("packImgId", p.pack_name);
                Element os = XMLUtils.getOSElement(p.pack_os, p.pack_os.family, xml);
                if (os != null)
                    pack.appendChild(os);
                pack.appendChild(XMLUtils.createElement("description", p.pack_description, xml));
                //Traverses through all the depends
                for (String d : p.pack_depends) {
                    Element depends = xml.createElement("depends");
                    depends.setAttribute("packname", d);
                    pack.appendChild(depends);
                }
                //Traverses through all the XFiles in the pack
                for (XFile f : p.file)
                    if (f.sourceFileOption)
                        if (f.renameTargetFileOption) {
                            String targetDir = f.packs_file_targetdir;
                            targetDir += targetDir.lastIndexOf("/") == targetDir.length() - 1 ? "" : "/";
                            pack.appendChild(getFile(f.packs_file_sourceFile, targetDir + f.packs_file_renameTarget, f.packs_file_overwrite, true, f.packs_file_os, f.packs_file_unpack));
                        } else
                            pack.appendChild(getFile(f.packs_file_sourceFile, f.packs_file_targetdir, f.packs_file_overwrite, false, f.packs_file_os, f.packs_file_unpack));
                    else if (f.sourceDirOption)
                        pack.appendChild(getFile(f.packs_file_sourceDir, f.packs_file_targetdir, f.packs_file_overwrite, false, f.packs_file_os, f.packs_file_unpack));
                //Traverses through all the executables in the pack
                for (Executable e : p.executables) {
                    Element executable = xml.createElement("executable");
                    executable.setAttribute("targetfile", e.targetfile);
                    if (e.targetfile.endsWith(".jar")) {
                        executable.setAttribute("class", e.clas.endsWith(".class") ? e.clas.substring(0, e.clas.length() - 6) : e.clas);
                        executable.setAttribute("type", "jar");
                    }
                    executable.setAttribute("stage", e.stage.toLowerCase().replace("-", ""));
                    executable.setAttribute("failure", e.failure.toLowerCase().replace(" installation", "").replace(" user and continue", "").replace(" user", ""));
                    executable.setAttribute("keep", e.keep + "");
                    Element operatingSystem = XMLUtils.getOSElement(e.os, e.os.family, xml);
                    if (operatingSystem != null)
                        executable.appendChild(operatingSystem);
                    Element args = xml.createElement("args");
                    //Traverses through all the arguments in the executable, and adds them
                    for (String s : e.args)
                        if (!s.equals(""))
                            args.appendChild(XMLUtils.getArg(s, xml, true));
                    if (args.hasChildNodes())
                        executable.appendChild(args);
                    pack.appendChild(executable);
                }
                //Traverses through all the parsables in the pack
                for (Parsable parse : p.parsables) {
                    Element parsable = xml.createElement("parsable");
                    parsable.setAttribute("targetfile", parse.targetfile);
                    parsable.setAttribute("type", parse.type.toLowerCase().replace("java properties file", "javaprop").replace("java file", "java"));
                    if (!parse.encoding.equals(""))
                        parsable.setAttribute("encoding", parse.encoding);
                    Element operatingSystem = XMLUtils.getOSElement(parse.os, parse.os.family, xml);
                    if (operatingSystem != null)
                        parsable.appendChild(operatingSystem);
                    pack.appendChild(parsable);
                }
                packs.appendChild(pack);
            } else {
                Element refPack = xml.createElement("refpack");
                refPack.setAttribute("file", p.xmlFile);
                packs.appendChild(refPack);
            }
        return packs;
    }

    /**
     * Gets the file element
     * @param src the source file
     * @param targetDir the target dir
     * @param override whether to override
     * @param singleFile whether it is a single file
     * @param operatingSystem the operating system to run on
     * @return
     */
    private Element getFile(String src, String targetDir, String override, boolean singleFile, OperatingSystem operatingSystem, boolean unpack) {
        Element file = xml.createElement(singleFile ? "singlefile" : "file");
        file.setAttribute("src", src);
        file.setAttribute(singleFile ? "target" : "targetdir", targetDir);
        file.setAttribute("override", override.toLowerCase().replace(" ", "").replace("overwrite", "true"));
        if (unpack)
            file.setAttribute("unpack", "true");
        if (operatingSystem != null) {
            Element os = XMLUtils.getOSElement(operatingSystem, operatingSystem.family, xml);
            if (os != null)
                file.appendChild(os);
        }
        return file;
    }

    /**
     * Gets the native 32 bit library element
     * @return the native library
     */
    private Element getNativeLibrary32() {
        if (!xmlObject.shortcuts_programGroup_defaultName.equals("")) {
            String stage = "";
            Element nativeElement = xml.createElement("native");
            nativeElement.setAttribute("type", "izpack");
            nativeElement.setAttribute("name", "ShellLink.dll");
            if (!stage.equals(""))
                nativeElement.setAttribute("stage", stage);
            return nativeElement;
        }
        return null;
    }

    /**
     * Gets the native 64 bit library element
     * @return the native library
     */
    private Element getNativeLibrary64() {
        if (!xmlObject.shortcuts_programGroup_defaultName.equals("")) {
            String stage = "";
            Element nativeElement = xml.createElement("native");
            nativeElement.setAttribute("type", "izpack");
            nativeElement.setAttribute("name", "ShellLink_x64.dll");
            if (!stage.equals(""))
                nativeElement.setAttribute("stage", stage);
            return nativeElement;
        }
        return null;
    }

    /**
     * Gets the element
     * @param type the type of element
     * @param key the key to the element
     * @param value the value of the element
     * @return the element
     */
    private Element getElement(String type, String key, String value) {
        Element element = xml.createElement(type);
        element.setAttribute(type.equals("modifier") ? "key" : "name", key);
        element.setAttribute("value", value);
        return element;
    }

    /**
     * Generates an xml file
     * @param xmlObject the XML data class
     * @param fileName the file name to write as
     * @throws IOException Exception thrown if problem with writing file
     */
    public static void generateXML(XML xmlObject, String fileName) throws IOException {
        //Creates the file, serializes and writes the xml file
        InstallXML creation = new InstallXML(xmlObject, fileName);
        creation.xml.appendChild(creation.getInstallation());
        FileOutputStream file = new FileOutputStream(mainXMLFileName);
        OutputFormat f = new OutputFormat("XML", "iso-8859-1", true);
        f.setIndenting(true);
        f.setStandalone(true);
        f.setVersion("1.0");
        XMLSerializer s = new XMLSerializer(file, f);
        s.asDOMSerializer();
        s.serialize(creation.xml.getDocumentElement());
        file.close();
        //Calls the appropriate class and method to generate the other xml files if required
        if (!xmlObject.shortcuts_programGroup_defaultName.equals("")) {
            ShortcutXML.generateXML(xmlObject, shortcuts, false);
            ShortcutXML.generateXML(xmlObject, unixShortcuts, true);
        }
        if (xmlObject.userInputOption)
            UserinputSpecXML.generateXML(xmlObject, userInputSpecXML);
        if (xmlObject.processes.size() != 0 || xmlObject.userInput_ea.contains("List Variable"))
            ProcessXML.generateXML(xmlObject, processXML, UserinputSpecXML.var);
    }
}

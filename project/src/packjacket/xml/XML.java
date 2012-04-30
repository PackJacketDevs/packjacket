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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.DefaultListModel;
import packjacket.RunnerClass;

/**
 * Contains fields for the XML Database
 * @author Manodasan Wignarajah
 */
public class XML implements Serializable {

    /**
     * Used for PJC file backwards compability
     */
    public static final long serialVersionUID = 4L;
    /**
     * Stores the version of PackJacket
     */
    public String version;
    /**
     * Stores the name of the application being installed
     */
    public String info_appname;
    /**
     * Stores the subpath
     */
    public String info_appsubpath;
    /**
     * Stores the version of the apllication being installed
     */
    public String info_appversion;
    /**
     * Stores the url
     */
    public String info_url;
    /**
     * Stores the name of the uninstaller
     */
    public String info_uninstaller_name;
    /**
     * Stores whether to write the uninstaller
     */
    public boolean info_uninstaller_write;
    /**
     * Stores the versin of java required
     */
    public String info_javaversion;
    /**
     * Stores whether the jdk is required
     */
    public boolean info_requiresjdk;
    /**
     * Stores the web directory
     */
    public String info_webdir;
    /**
     * Stores the path to the summary log file
     */
    public String info_summarylogfilepath;
    /**
     * Stores whether to write the installation information
     */
    public boolean info_writeinstallationinformation;
    /**
     * Stores whether to use pack200 compression
     */
    public boolean info_pack200;
    /**
     * Stores whether to run the setup file with adminitrator permissions
     */
    public boolean info_privellegedAccess;
    /**
     * Stores the width of the installer
     */
    public int guiprefs_width;
    /**
     * Stores the height of the installer
     */
    public int guiprefs_height;
    /**
     * Stores whether the installer gui is resizable
     */
    public boolean guiprefs_resizable;
    /**
     * Stores whether to show flags
     */
    public boolean modifier_useFlags;
    /**
     * Specified how to display the language to the installer user
     */
    public String modifier_langDisplayType;
    /**
     * Stores the authors
     */
    public Collection<Author> authors = new ArrayList<Author>();
    /**
     * Stores the languages
     */
    public Collection<Locale> langs = new ArrayList<Locale>();
    /**
     * Stores all the panels which is wanted in the installer in order
     */
    public Collection<String> panels = new ArrayList<String>();
    /**
     * Stores all the panels, used for GUI purposes
     */
    public DefaultListModel panelsModel;
    /**
     * Stores the location of the info file
     */
    public String resources_LicencePanel_license_src_info;
    /**
     * Stores the location of the license file
     */
    public String resources_LicencePanel_license_src_license;
    /**
     * Stores the location of the HTML file
     */
    public String resources_HTMLHelloPanel_HTML;
    /**
     * Stores the packs
     */
    public Collection<Pack> packs = new ArrayList<Pack>();
    /**
     * Stores the program group default value for name in shortcuts
     */
    public String shortcuts_programGroup_defaultName;
    /**
     * Stores the location of the program group
     */
    public String shortcuts_programGroup_location;
    /**
     * Stores the shortcuts
     */
    public Collection<Shortcut> shortcuts = new ArrayList<Shortcut>();
    /**
     * Stores the volume size for packaging
     */
    public String packaging_volumeSize;
    /**
     * Stores the volume size type for packaging
     */
    public String packaging_volumeSize_type;
    /**
     * Stores the amount of free space on the first volume
     */
    public String packaging_firstvolumefreespace;
    /**
     * Stores the amount of free space type on the first volume
     */
    public String packaging_firstvolumefreespace_type;
    /**
     * Store whether packaging is used
     */
    public boolean packagingOption;
    /**
     * Stores whether the option to put desktop shortcuts is chosen
     */
    public boolean DesktopShortcutCheckboxEnabled;
    /**
     * Stores the lafs
     */
    public Collection<Laf> lafs = new ArrayList<Laf>();
    /**
     * Stores the image on language selection
     */
    public String langselimg;
    /**
     * Stores the installer image
     */
    public String installerImage;
    /**
     * Stores whether to have a heading panel
     */
    public boolean guiprefs_useHeadingPanel;
    /**
     * Stores the heading image
     */
    public String guiprefs_headingImg;
    /**
     * Stores where to put the heading image
     */
    public String guiprefs_headingImageOnLeftOrRight;
    /**
     * Store the font size of the heading
     */
    public String guiprefs_headingFontSize;
    /**
     * Stores the background color
     */
    public String guiprefs_headingBackgroundColor;
    /**
     * Stores the foreground color
     */
    public String guiprefs_headingForegroundColor;
    /**
     * Stores whether to have a counter
     */
    public boolean guiprefs_counterOption;
    /**
     * Stores the type of counter
     */
    public String guiprefs_headingPanelCounter;
    /**
     * Stores the position of the counter
     */
    public String guiprefs_headingPanelCounterPos;
    /**
     * Stores whether to have a UserInput panel
     */
    public boolean userInputOption;
    /**
     * Stores the type of user inputs
     */
    public Collection<UserInput> userInputs = new ArrayList<UserInput>();
    /**
     * Stores the title on the UserInput panel
     */
    public String userInput_title;
    /**
     * Stores the font on the UserInput panel
     */
    public int userInput_font;
    /**
     * Stores whether the title is bold
     */
    public boolean userInput_bold;
    /**
     * Stores whether the title is in italics
     */
    public boolean userInput_italics;
    /**
     * Stores the processes
     */
    public Collection<XProcess> processes = new ArrayList<XProcess>();
    /**
     * Whether the user specified a log file for processes
     */
    public boolean process_logFileOption;
    /**
     * Stores the elements in the log file combo box
     */
    public Collection<String> process_logFileModel;
    /**
     * The log file specified by the user for processes
     */
    public String process_logFilePath;
    /**
     * Stores the type of UserInput QuickXport to use
     */
    public String userInput_ea;
    /**
     * Stores the file to create for ea
     */
    public String userInput_ea_file;
    /**
     * Stores whether the current user is the default option to install the shorcuts on
     */
    public boolean shortcuts_defaultCurrentUser;

    public XML() {
        version = RunnerClass.VERSION;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final XML other = (XML) obj;
        if ((this.version == null) ? (other.version != null) : !this.version.equals(other.version))
            return false;
        if ((this.info_appname == null) ? (other.info_appname != null) : !this.info_appname.equals(other.info_appname))
            return false;
        if ((this.info_appsubpath == null) ? (other.info_appsubpath != null) : !this.info_appsubpath.equals(other.info_appsubpath))
            return false;
        if ((this.info_appversion == null) ? (other.info_appversion != null) : !this.info_appversion.equals(other.info_appversion))
            return false;
        if ((this.info_url == null) ? (other.info_url != null) : !this.info_url.equals(other.info_url))
            return false;
        if ((this.info_uninstaller_name == null) ? (other.info_uninstaller_name != null) : !this.info_uninstaller_name.equals(other.info_uninstaller_name))
            return false;
        if (this.info_uninstaller_write != other.info_uninstaller_write)
            return false;
        if ((this.info_javaversion == null) ? (other.info_javaversion != null) : !this.info_javaversion.equals(other.info_javaversion))
            return false;
        if (this.info_requiresjdk != other.info_requiresjdk)
            return false;
        if ((this.info_webdir == null) ? (other.info_webdir != null) : !this.info_webdir.equals(other.info_webdir))
            return false;
        if ((this.info_summarylogfilepath == null) ? (other.info_summarylogfilepath != null) : !this.info_summarylogfilepath.equals(other.info_summarylogfilepath))
            return false;
        if (this.info_writeinstallationinformation != other.info_writeinstallationinformation)
            return false;
        if (this.info_pack200 != other.info_pack200)
            return false;
        if (this.info_privellegedAccess != other.info_privellegedAccess)
            return false;
        if (this.guiprefs_width != other.guiprefs_width)
            return false;
        if (this.guiprefs_height != other.guiprefs_height)
            return false;
        if (this.guiprefs_resizable != other.guiprefs_resizable)
            return false;
        if (this.modifier_useFlags != other.modifier_useFlags)
            return false;
        if ((this.modifier_langDisplayType == null) ? (other.modifier_langDisplayType != null) : !this.modifier_langDisplayType.equals(other.modifier_langDisplayType))
            return false;
        if (this.authors != other.authors && (this.authors == null || !this.authors.equals(other.authors)))
            return false;
        if (this.langs != other.langs && (this.langs == null || !this.langs.equals(other.langs)))
            return false;
        if (this.panels != other.panels && (this.panels == null || !this.panels.equals(other.panels)))
            return false;
        if (this.panelsModel != other.panelsModel && (this.panelsModel == null))
            return false;
        if (this.panelsModel.size() != other.panelsModel.size())
            return false;
        for (int x = 0; x < this.panelsModel.size(); x++)
            if (!this.panelsModel.get(x).equals(other.panelsModel.get(x)))
                return false;
        if ((this.resources_LicencePanel_license_src_info == null) ? (other.resources_LicencePanel_license_src_info != null) : !this.resources_LicencePanel_license_src_info.equals(other.resources_LicencePanel_license_src_info))
            return false;
        if ((this.resources_LicencePanel_license_src_license == null) ? (other.resources_LicencePanel_license_src_license != null) : !this.resources_LicencePanel_license_src_license.equals(other.resources_LicencePanel_license_src_license))
            return false;
        if ((this.resources_HTMLHelloPanel_HTML == null) ? (other.resources_HTMLHelloPanel_HTML != null) : !this.resources_HTMLHelloPanel_HTML.equals(other.resources_HTMLHelloPanel_HTML))
            return false;
        if (this.packs != other.packs && (this.packs == null || !this.packs.equals(other.packs)))
            return false;
        if ((this.shortcuts_programGroup_defaultName == null) ? (other.shortcuts_programGroup_defaultName != null) : !this.shortcuts_programGroup_defaultName.equals(other.shortcuts_programGroup_defaultName))
            return false;
        if ((this.shortcuts_programGroup_location == null) ? (other.shortcuts_programGroup_location != null) : !this.shortcuts_programGroup_location.equals(other.shortcuts_programGroup_location))
            return false;
        if (this.shortcuts != other.shortcuts && (this.shortcuts == null || !this.shortcuts.equals(other.shortcuts)))
            return false;
        if ((this.packaging_volumeSize == null) ? (other.packaging_volumeSize != null) : !this.packaging_volumeSize.equals(other.packaging_volumeSize))
            return false;
        if ((this.packaging_volumeSize_type == null) ? (other.packaging_volumeSize_type != null) : !this.packaging_volumeSize_type.equals(other.packaging_volumeSize_type))
            return false;
        if ((this.packaging_firstvolumefreespace == null) ? (other.packaging_firstvolumefreespace != null) : !this.packaging_firstvolumefreespace.equals(other.packaging_firstvolumefreespace))
            return false;
        if ((this.packaging_firstvolumefreespace_type == null) ? (other.packaging_firstvolumefreespace_type != null) : !this.packaging_firstvolumefreespace_type.equals(other.packaging_firstvolumefreespace_type))
            return false;
        if (this.packagingOption != other.packagingOption)
            return false;
        if (this.DesktopShortcutCheckboxEnabled != other.DesktopShortcutCheckboxEnabled)
            return false;
        if (this.lafs != other.lafs && (this.lafs == null || !this.lafs.equals(other.lafs)))
            return false;
        if ((this.langselimg == null) ? (other.langselimg != null) : !this.langselimg.equals(other.langselimg))
            return false;
        if ((this.installerImage == null) ? (other.installerImage != null) : !this.installerImage.equals(other.installerImage))
            return false;
        if (this.guiprefs_useHeadingPanel != other.guiprefs_useHeadingPanel)
            return false;
        if ((this.guiprefs_headingImg == null) ? (other.guiprefs_headingImg != null) : !this.guiprefs_headingImg.equals(other.guiprefs_headingImg))
            return false;
        if ((this.guiprefs_headingImageOnLeftOrRight == null) ? (other.guiprefs_headingImageOnLeftOrRight != null) : !this.guiprefs_headingImageOnLeftOrRight.equals(other.guiprefs_headingImageOnLeftOrRight))
            return false;
        if ((this.guiprefs_headingFontSize == null) ? (other.guiprefs_headingFontSize != null) : !this.guiprefs_headingFontSize.equals(other.guiprefs_headingFontSize))
            return false;
        if ((this.guiprefs_headingBackgroundColor == null) ? (other.guiprefs_headingBackgroundColor != null) : !this.guiprefs_headingBackgroundColor.equals(other.guiprefs_headingBackgroundColor))
            return false;
        if ((this.guiprefs_headingForegroundColor == null) ? (other.guiprefs_headingForegroundColor != null) : !this.guiprefs_headingForegroundColor.equals(other.guiprefs_headingForegroundColor))
            return false;
        if (this.guiprefs_counterOption != other.guiprefs_counterOption)
            return false;
        if ((this.guiprefs_headingPanelCounter == null) ? (other.guiprefs_headingPanelCounter != null) : !this.guiprefs_headingPanelCounter.equals(other.guiprefs_headingPanelCounter))
            return false;
        if ((this.guiprefs_headingPanelCounterPos == null) ? (other.guiprefs_headingPanelCounterPos != null) : !this.guiprefs_headingPanelCounterPos.equals(other.guiprefs_headingPanelCounterPos))
            return false;
        if (this.userInputOption != other.userInputOption)
            return false;
        if (this.userInputs != other.userInputs && (this.userInputs == null || !this.userInputs.equals(other.userInputs)))
            return false;
        if ((this.userInput_title == null) ? (other.userInput_title != null) : !this.userInput_title.equals(other.userInput_title))
            return false;
        if (this.userInput_font != other.userInput_font)
            return false;
        if (this.userInput_bold != other.userInput_bold)
            return false;
        if (this.userInput_italics != other.userInput_italics)
            return false;
        if (this.processes != other.processes && (this.processes == null || !this.processes.equals(other.processes)))
            return false;
        if (this.process_logFileOption != other.process_logFileOption)
            return false;
        if (this.process_logFileModel != other.process_logFileModel && (this.process_logFileModel == null || !this.process_logFileModel.equals(other.process_logFileModel)))
            return false;
        if ((this.process_logFilePath == null) ? (other.process_logFilePath != null) : !this.process_logFilePath.equals(other.process_logFilePath))
            return false;
        if ((this.userInput_ea == null) ? (other.userInput_ea != null) : !this.userInput_ea.equals(other.userInput_ea))
            return false;
        if ((this.userInput_ea_file == null) ? (other.userInput_ea_file != null) : !this.userInput_ea_file.equals(other.userInput_ea_file))
            return false;
        if (this.shortcuts_defaultCurrentUser != other.shortcuts_defaultCurrentUser)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (this.version != null ? this.version.hashCode() : 0);
        hash = 29 * hash + (this.info_appname != null ? this.info_appname.hashCode() : 0);
        hash = 29 * hash + (this.info_appsubpath != null ? this.info_appsubpath.hashCode() : 0);
        hash = 29 * hash + (this.info_appversion != null ? this.info_appversion.hashCode() : 0);
        hash = 29 * hash + (this.info_url != null ? this.info_url.hashCode() : 0);
        hash = 29 * hash + (this.info_uninstaller_name != null ? this.info_uninstaller_name.hashCode() : 0);
        hash = 29 * hash + (this.info_uninstaller_write ? 1 : 0);
        hash = 29 * hash + (this.info_javaversion != null ? this.info_javaversion.hashCode() : 0);
        hash = 29 * hash + (this.info_requiresjdk ? 1 : 0);
        hash = 29 * hash + (this.info_webdir != null ? this.info_webdir.hashCode() : 0);
        hash = 29 * hash + (this.info_summarylogfilepath != null ? this.info_summarylogfilepath.hashCode() : 0);
        hash = 29 * hash + (this.info_writeinstallationinformation ? 1 : 0);
        hash = 29 * hash + (this.info_pack200 ? 1 : 0);
        hash = 29 * hash + (this.info_privellegedAccess ? 1 : 0);
        hash = 29 * hash + this.guiprefs_width;
        hash = 29 * hash + this.guiprefs_height;
        hash = 29 * hash + (this.guiprefs_resizable ? 1 : 0);
        hash = 29 * hash + (this.modifier_useFlags ? 1 : 0);
        hash = 29 * hash + (this.modifier_langDisplayType != null ? this.modifier_langDisplayType.hashCode() : 0);
        hash = 29 * hash + (this.authors != null ? this.authors.hashCode() : 0);
        hash = 29 * hash + (this.langs != null ? this.langs.hashCode() : 0);
        hash = 29 * hash + (this.panels != null ? this.panels.hashCode() : 0);
        hash = 29 * hash + (this.panelsModel != null ? this.panelsModel.hashCode() : 0);
        hash = 29 * hash + (this.resources_LicencePanel_license_src_info != null ? this.resources_LicencePanel_license_src_info.hashCode() : 0);
        hash = 29 * hash + (this.resources_LicencePanel_license_src_license != null ? this.resources_LicencePanel_license_src_license.hashCode() : 0);
        hash = 29 * hash + (this.resources_HTMLHelloPanel_HTML != null ? this.resources_HTMLHelloPanel_HTML.hashCode() : 0);
        hash = 29 * hash + (this.packs != null ? this.packs.hashCode() : 0);
        hash = 29 * hash + (this.shortcuts_programGroup_defaultName != null ? this.shortcuts_programGroup_defaultName.hashCode() : 0);
        hash = 29 * hash + (this.shortcuts_programGroup_location != null ? this.shortcuts_programGroup_location.hashCode() : 0);
        hash = 29 * hash + (this.shortcuts != null ? this.shortcuts.hashCode() : 0);
        hash = 29 * hash + (this.packaging_volumeSize != null ? this.packaging_volumeSize.hashCode() : 0);
        hash = 29 * hash + (this.packaging_volumeSize_type != null ? this.packaging_volumeSize_type.hashCode() : 0);
        hash = 29 * hash + (this.packaging_firstvolumefreespace != null ? this.packaging_firstvolumefreespace.hashCode() : 0);
        hash = 29 * hash + (this.packaging_firstvolumefreespace_type != null ? this.packaging_firstvolumefreespace_type.hashCode() : 0);
        hash = 29 * hash + (this.packagingOption ? 1 : 0);
        hash = 29 * hash + (this.DesktopShortcutCheckboxEnabled ? 1 : 0);
        hash = 29 * hash + (this.lafs != null ? this.lafs.hashCode() : 0);
        hash = 29 * hash + (this.langselimg != null ? this.langselimg.hashCode() : 0);
        hash = 29 * hash + (this.installerImage != null ? this.installerImage.hashCode() : 0);
        hash = 29 * hash + (this.guiprefs_useHeadingPanel ? 1 : 0);
        hash = 29 * hash + (this.guiprefs_headingImg != null ? this.guiprefs_headingImg.hashCode() : 0);
        hash = 29 * hash + (this.guiprefs_headingImageOnLeftOrRight != null ? this.guiprefs_headingImageOnLeftOrRight.hashCode() : 0);
        hash = 29 * hash + (this.guiprefs_headingFontSize != null ? this.guiprefs_headingFontSize.hashCode() : 0);
        hash = 29 * hash + (this.guiprefs_headingBackgroundColor != null ? this.guiprefs_headingBackgroundColor.hashCode() : 0);
        hash = 29 * hash + (this.guiprefs_headingForegroundColor != null ? this.guiprefs_headingForegroundColor.hashCode() : 0);
        hash = 29 * hash + (this.guiprefs_counterOption ? 1 : 0);
        hash = 29 * hash + (this.guiprefs_headingPanelCounter != null ? this.guiprefs_headingPanelCounter.hashCode() : 0);
        hash = 29 * hash + (this.guiprefs_headingPanelCounterPos != null ? this.guiprefs_headingPanelCounterPos.hashCode() : 0);
        hash = 29 * hash + (this.userInputOption ? 1 : 0);
        hash = 29 * hash + (this.userInputs != null ? this.userInputs.hashCode() : 0);
        hash = 29 * hash + (this.userInput_title != null ? this.userInput_title.hashCode() : 0);
        hash = 29 * hash + this.userInput_font;
        hash = 29 * hash + (this.userInput_bold ? 1 : 0);
        hash = 29 * hash + (this.userInput_italics ? 1 : 0);
        hash = 29 * hash + (this.processes != null ? this.processes.hashCode() : 0);
        hash = 29 * hash + (this.process_logFileOption ? 1 : 0);
        hash = 29 * hash + (this.process_logFileModel != null ? this.process_logFileModel.hashCode() : 0);
        hash = 29 * hash + (this.process_logFilePath != null ? this.process_logFilePath.hashCode() : 0);
        hash = 29 * hash + (this.userInput_ea != null ? this.userInput_ea.hashCode() : 0);
        hash = 29 * hash + (this.userInput_ea_file != null ? this.userInput_ea_file.hashCode() : 0);
        hash = 29 * hash + (this.shortcuts_defaultCurrentUser ? 1 : 0);
        return hash;
    }
}


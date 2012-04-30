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
package packjacket.gui;

import javax.swing.JComponent;

/**
 * Class allows to store the panel, and the size of the JFrame, when another panel is being opened due to a button pressed on this, to later restore to this state when the other panel is closed(removed)
 * @author Manodasan Wignarajah
 */
public class ElementPanel {

    /**
     * Stores the panel
     */
    private JComponent panel;
    /**
     * Stores the width when this panel was removed to add another panel which isn't already in the panel queue
     */
    private int width;
    /**
     * Stores the height when this panel was removed to add another panel which isn't already in the panel queue
     */
    private int height;

    /**
     * Constructs and initalizes the ElementPanel object
     * @param panel the panel for the element
     */
    public ElementPanel(JComponent panel) {
        this.panel = panel;
    }

    /**
     * Sets the height of the JFrame, when panel was being removed, and another is being added
     *
     * @param height the height of the JFrame
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Sets the width of the Jframe, when panel was being removed and another is being added
     *
     * @param width the width of the JFrame
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Get the value of height
     *
     * @return the value of height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Get the value of width
     *
     * @return the value of width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get the value of panel
     *
     * @return the value of panel
     */
    public JComponent getPanel() {
        return panel;
    }
}

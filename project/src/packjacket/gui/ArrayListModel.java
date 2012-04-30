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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.swing.AbstractListModel;
import javax.swing.JList;
import packjacket.xml.XMLInterface;

/**
 * A list model for JLists, that works on an ArrayList backend.
 * @author Amandeep Grewal
 * @author Manodasan Wignarajah
 * @param <T> type of model
 */
public class ArrayListModel<T> extends AbstractListModel {

    public ArrayList<T> list;
    private JList jlist;

    /**
     * Create new ArrayListModel
     * @param list the arraylist to use
     * @param jlist the JList that this model is used on
     */
    public ArrayListModel(ArrayList<T> list, JList jlist) {
        this.list = list;
        this.jlist = jlist;
        jlist.setModel(this);
    }

    /**
     * Creates new ArrayListModel, with a new ArrayList as its backend
     * @param jlist the JList that this model is used on
     */
    public ArrayListModel(JList jlist) {
        this(new ArrayList<T>(), jlist);
    }

    @Override
    public int getSize() {
        return list.size();
    }

    @Override
    public T getElementAt(int index) {
        return list.get(index);
    }

    /**
     * Gets selected element
     * @return selected element
     */
    public T getElement() {
        return getElementAt(jlist.getSelectedIndex());
    }

    /**
     * Removes selected element
     */
    public void remove() {
        try {
            list.remove(jlist.getSelectedIndex());
            jlist.updateUI();
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
        }
    }

    /**
     * Removes element as specified index
     * @param index the index at which the element being removed is at
     */
    public void removeElementAt(int index) {
        try {
            list.remove(index);
            jlist.updateUI();
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
        }
    }

    /**
     * Adds element to end of list
     * @param e element to add
     */
    public void add(T e) {
        list.add(e);
        jlist.updateUI();
    }

    /**
     * Moves currently selected element up
     */
    public void moveDown() {
        try {
            //Swaps currently selected, and one below
            int i = jlist.getSelectedIndex();
            Collections.swap(list, i, ++i);
            jlist.setSelectedIndex(i);
        } catch (java.lang.IndexOutOfBoundsException e) {
        }
    }

    /**
     * Moves currently selected element down
     */
    public void moveUp() {
        try {
            //Swaps currently selected, and one above
            int i = jlist.getSelectedIndex();
            Collections.swap(list, i, --i);
            jlist.setSelectedIndex(i);
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
        }
    }

    /**
     * Sets the list to a new list. Not just assigned, but deep copied for XMLInterface.
     * @param list ArrayList to set to
     */
    public void setList(ArrayList<T> list) {
        this.list = new ArrayList<T>(list.size());
        for (T e : list)
            if (e instanceof XMLInterface)
                this.list.add((T) ((XMLInterface) e).clone());
            else
                this.list.add(e);
        jlist.updateUI();
    }

    public String toString() {
        String s = "";
        for (int a = 0; a < getSize(); a++)
            s += getElementAt(a).toString() + ", ";
        return s.substring(0, Math.max(0, s.length() - 2));
    }
}

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
 * Contains fields for the UserInput Database
 * @author Manodasan Wignarajah
 */
public abstract class UserInput implements XMLInterface {

    /**
     * Used for PJC file backwards compability
     */
    public static final long serialVersionUID = 4L;
    /**
     * Stores the type of UserInput it is
     */
    public final String type;

    /**
     * Used to set the value of type
     * @param type the type of user input it is
     */
    public UserInput(String type) {
        this.type = type;
    }

    @Override
    public abstract UserInput clone();
}

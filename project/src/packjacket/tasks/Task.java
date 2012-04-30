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
package packjacket.tasks;

/**
 * Any tasks that is able to run, and stop
 * @author Amandeep Grewal
 */
public interface Task {

    /**
     * Start the task
     * @throws java.io.IOException if any i/O error occurs
     */
    void go() throws java.io.IOException;

    /**
     * Abruptly stop the task (in case of user cancelling or crashing)
     */
    void stop();
}

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

import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.AbstractButton;

/**
 * Shows a busy cursor on a component
 * @author Amandeep Grewal
 */
public class CursorController {

    public static final Cursor busyCursor = new Cursor(Cursor.WAIT_CURSOR);
    public static final Cursor defaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);
    public static final int delay = 25;

    /**
     * Shows a busy cursor on click
     * @param component component whre busy cursor shown
     * @param btn the button to be clicked
     */
    public static void createListener(final Component component, AbstractButton btn) {
        final ActionListener l = btn.getActionListeners()[0];
        btn.removeActionListener(l);
        btn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent ae) {

                TimerTask timerTask = new TimerTask() {

                    @Override
                    public void run() {
                        component.setCursor(busyCursor);
                    }
                };
                Timer timer = new Timer();

                try {
                    timer.schedule(timerTask, delay);
                    l.actionPerformed(ae);
                } finally {
                    timer.cancel();
                    component.setCursor(defaultCursor);
                }
            }
        });
    }
}

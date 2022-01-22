/* 
 * Copyright (C) 2022 Atrament.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package org.atrament;

import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Atrament
 */
public class TestAction extends ManagedAction<ExtendedJFrame> {

    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    public TestAction(ExtendedJFrame mainWindow) {
        super(mainWindow);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        log.debug("Test action performed");
        log.debug(e.getActionCommand());
        controller.extendedMethod();
    }

}

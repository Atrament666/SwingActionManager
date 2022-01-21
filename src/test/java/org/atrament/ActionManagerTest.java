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
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Atrament
 */
public class ActionManagerTest {
    
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    enum MyState implements State {
        STOPPED, RUNNING
    };

    public ActionManagerTest() {
    }

    @Test
    public void testItWorks() {

        ActionManager<ManagedAction, ExtendedJFrame> am = new ActionManager<>(new ExtendedJFrame());
        ManagedAction action = am.getAction(TestAction.class);
        log.debug("Main window type" + action.mainWindow.getClass());
        action.actionPerformed(new ActionEvent(this, 0, "Hello test action 1"));
        ManagedAction anotherAction = am.getAction(TestAction.class);
        anotherAction.actionPerformed(new ActionEvent(this, 0, "Hello test action 2"));
        am.setCurrentState(MyState.RUNNING);
        log.debug("Current state is: " + am.getCurrentState());
        am.registerActionEnabledForState(action, MyState.RUNNING);
        am.updateActions();
    }

}

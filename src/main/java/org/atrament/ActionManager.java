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

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Atrament
 * @param <T> type that extends ManagedAction
 * @param <U> type that extends JFrame
 */
public class ActionManager<T extends ManagedAction, U extends JFrame> {

    private U controller;
    private final Map<Class<? extends T>, T> cache;
    private State currentState;
    private final Map<State, List<T>> register;

    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    public ActionManager(U frame) {
        cache = new HashMap<>();
        register = new HashMap<>();
        controller = frame;
    }

    public T getAction(Class<? extends T> clazz) {
        if (cache.containsKey(clazz)) {
            log.debug("Returning existing instance of " + clazz.getSimpleName());
            return clazz.cast(cache.get(clazz));
        } else {
            log.debug("Creating new instance of " + clazz.getSimpleName());
            try {
                T instance = clazz.getDeclaredConstructor(controller.getClass()).newInstance(controller);
                cache.put(clazz, instance);
                return instance;
            } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException ex) {
                log.error("Error creating instance: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
        return null;
    }

    public T getAction(Class<? extends T> clazz, State state) {
        T action = getAction(clazz);
        registerActionEnabledForState(action, state);
        return action;
    }

    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State currentState) {
        log.debug("Changing state to: " + currentState);
        this.currentState = currentState;
        updateActions();
    }

    public void updateActions() {
        log.debug("Updating actions");
        for (var state : register.keySet()) {
            List<T> actions = register.get(state);
            for (var action : actions) {
                log.debug("Current state is " + currentState + ". Setting enabled of " + action.toString() + " to " + (state == currentState));
                action.setEnabled(state == currentState);
            }
        }
    }

    /**
     * Registers action to be enabled in given state
     *
     * @param action action to be registered
     * @param state the state in which the action will be enabled
     */
    public void registerActionEnabledForState(T action, State state) {
        log.debug("Registering action " + action + " to be enable in " + state);
        if (register.containsKey(state)) {
            register.get(state).add(action);
        } else {
            register.put(state, new ArrayList());
            register.get(state).add(action);
        }

    }

    public U getController() {
        return controller;
    }

    public void setController(U controller) {
        this.controller = controller;
    }

}

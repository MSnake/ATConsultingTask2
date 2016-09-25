// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.atc.parser.notation.state;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class StateFactory {

    private static final Map<Class<? extends IState>, IState> STATE_MAP;

    static {
        Set<IState> allStates = new HashSet<>();
        allStates.add(new State00());
        allStates.add(new State01());
        allStates.add(new State02());
        allStates.add(new State03());
        allStates.add(new State04());
        allStates.add(new State05());
        allStates.add(new State06());
        allStates.add(new State07());
        allStates.add(new State08());
        allStates.add(new State09());
        allStates.add(new State10());
        allStates.add(new State11());
        allStates.add(new State12());
        allStates.add(new State13());
        allStates.add(new State14());
        allStates.add(new State15());

        Map<Class<? extends IState>, IState> result = new HashMap<>();
        for (IState state : allStates) {
            result.put(state.getClass(), state);
        }

        STATE_MAP = Collections.unmodifiableMap(result);
    }

    private StateFactory() {
        super();
    }

    public static IState getFirstState() {
        return getState(State00.class);
    }

    static IState getState(final Class<? extends IState> clazz) {
        return STATE_MAP.get(clazz);
    }

}

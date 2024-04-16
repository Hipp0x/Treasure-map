package pauline.adam.explorer;

import java.util.ArrayList;

public enum Action {

    D, G, A;

    public static ArrayList<Action> parseActions(String action) {
        String[] actionArray = action.split("");
        ArrayList<Action> actions = new ArrayList<>();
        for(String actionString : actionArray) {
            actions.add(Action.valueOf(actionString));
        }
        return actions;
    }
}

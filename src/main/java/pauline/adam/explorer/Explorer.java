package pauline.adam.explorer;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
public class Explorer {

    private final String name;
    private int positionX;
    private int positionY;
    @Setter private int nbTresorsTrouves = 0;
    @Setter private Orientation orientation;
    @Setter private ArrayList<Action> actions;

    public Explorer(String name, int positionX, int positionY) {
        this.name = name;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public void findNewTresor() {
        nbTresorsTrouves++;
    }

    public void move(int newPositionX, int newPositionY) {
        positionX = newPositionX;
        positionY = newPositionY;
    }
}

package pauline.adam.adventurer;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
public class Adventurer {

    private final String name;
    private int positionX;
    private int positionY;
    @Setter private int nbTreasuresFound = 0;
    @Setter private Orientation orientation;
    @Setter private ArrayList<Action> actions;

    public Adventurer(String name, int positionX, int positionY) {
        this.name = name;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public void findNewTreasure() {
        nbTreasuresFound++;
    }

    public void move(int newPositionX, int newPositionY) {
        positionX = newPositionX;
        positionY = newPositionY;
    }
}

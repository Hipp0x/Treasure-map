package pauline.adam.map;

import lombok.Getter;
import pauline.adam.adventurer.Adventurer;

public class Tile {

    @Getter private final int positionX;
    @Getter private final int positionY;
    private boolean isMountain = false;
    private boolean hasTreasure = false;
    private Adventurer adventurer = null;
    private Treasure treasure = null;

    public Tile(int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public void gotANewTreasure(Treasure treasure) {
        hasTreasure = true;
        this.treasure = treasure;
    }

    public boolean hasTreasure() { return hasTreasure; }

    public Treasure getTreasure() { return treasure; }

    public void treasureHasBeenFound() {
        hasTreasure = false;
        treasure = null;
    }

    public boolean isMountain() { return isMountain; }

    public void isNowAMountain() {
        isMountain = true;
    }

    public boolean isOccupied() { return adventurer != null; }

    public void isNowOccupiedBy(Adventurer adventurer) {
        this.adventurer = adventurer;
    }

    public void isNoLongerOccupied() {
        this.adventurer = null;
    }

    public boolean canMoveInto() {
        return !isMountain && !isOccupied();
    }

    public Adventurer getOccupant() { return adventurer; }


}


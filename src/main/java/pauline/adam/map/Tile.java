package pauline.adam.map;

import lombok.Getter;
import pauline.adam.explorer.Explorer;

public class Tile {

    @Getter private final int positionX;
    @Getter private final int positionY;
    private boolean isMountain = false;
    private boolean hasTreasure = false;
    private Explorer explorer = null;
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

    public boolean isOccupied() { return explorer != null; }

    public void isNowOccupiedBy(Explorer explorer) {
        this.explorer = explorer;
    }

    public void isNoLongerOccupied() {
        this.explorer = null;
    }

    public boolean canMoveInto() {
        return !isMountain && !isOccupied();
    }

    public Explorer getOccupant() { return explorer; }


}


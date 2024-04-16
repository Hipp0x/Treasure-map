package pauline.adam.map;

import lombok.Getter;
import pauline.adam.explorer.Action;
import pauline.adam.explorer.Explorer;
import pauline.adam.explorer.Orientation;
import pauline.adam.exceptions.Exceptions;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Map {

    private final int width;
    private final int height;
    private List<Explorer> explorers = new ArrayList<>();
    private Tile[][] tiles;

    public Map(String width, String height) {
        this.width = Integer.parseInt(width);
        this.height = Integer.parseInt(height);
        initTiles();
    }

    private void initTiles() {
        tiles = new Tile[height][width];
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                tiles[y][x] = new Tile(x, y);
            }
        }
    }

    public boolean isOutOfBounds(int positionX, int positionY) {
        return positionX < 0 && positionY < 0 &&
                positionX >= this.width && positionY >= this.height;
    }

    public void addMountain(String width, String height) {
        int positionX = Integer.parseInt(width);
        int positionY = Integer.parseInt(height);
        if (isOutOfBounds(positionX, positionY)) {
            throw new RuntimeException(Exceptions.MOUNTAIN_OUT_OF_BOUNDS.name());
        }
        tiles[positionY][positionX].isNowAMountain();
    }

    public void addTreasure(String width, String height, String nbTreasures) {
        int positionX = Integer.parseInt(width);
        int positionY = Integer.parseInt(height);
        if (isOutOfBounds(positionX, positionY)) {
            throw new RuntimeException(Exceptions.TREASURE_OUT_OF_BOUNDS.name());
        }
        if (Integer.parseInt(nbTreasures) < 0) {
            throw new RuntimeException(Exceptions.NEGATIVE_TREASURE.name());
        }
        Treasure treasure = new Treasure(positionX, positionY, Integer.parseInt(nbTreasures));
        tiles[positionY][positionX].gotANewTreasure(treasure);
    }

    public void addExplorer(String name, String width, String height, String orientation, String actions) {
        int positionX = Integer.parseInt(width);
        int positionY = Integer.parseInt(height);
        if (isOutOfBounds(positionX, positionY)) {
            throw new RuntimeException(Exceptions.EXPLORER_OUT_OF_BOUNDS.name());
        }
        Explorer explorer = new Explorer(name, positionX, positionY);
        explorer.setOrientation(Orientation.valueOf(orientation));
        explorer.setActions(Action.parseActions(actions));
        explorers.add(explorer);
        tiles[positionY][positionX].isNowOccupiedBy(explorer);

    }
}

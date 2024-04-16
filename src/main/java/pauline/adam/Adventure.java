package pauline.adam;

import pauline.adam.explorer.Action;
import pauline.adam.explorer.Explorer;
import pauline.adam.map.Map;
import pauline.adam.map.Treasure;
import pauline.adam.map.Tile;

import java.util.ArrayList;
import java.util.List;

import static pauline.adam.explorer.Orientation.*;

public class Adventure {

    private Map map;

    public Adventure(Map map) {
        this.map = map;
    }

    /**
     * Start the treasure hunt until no one can move anymore
     */
    public void treasureHunt() {
        List<Explorer> explorers = this.map.getExplorers();
        while(!explorers.isEmpty()) {
            ArrayList<Explorer> explorersToRemove = new ArrayList<>();
            for (Explorer explorer : explorers) {
                Action actions = explorer.getActions().get(0);
                switch (actions) {
                    case A:
                        explorerWantToMove(explorer, this.map);
                        break;
                    case D:
                        explorerWantToTurnRight(explorer);
                        break;
                    case G:
                        explorerWantToTurnLeft(explorer);
                        break;
                }
                explorer.getActions().remove(0);
                if (explorer.getActions().isEmpty()) {
                    explorersToRemove.add(explorer);
                }
            }
            for (Explorer explorer : explorersToRemove) {
                explorers.remove(explorer);
            }
        }

    }

    /**
     * Turn the explorer to the left
     * @param explorer the explorer who wants to turn left
     */
    private void explorerWantToTurnLeft(Explorer explorer) {
        switch (explorer.getOrientation()) {
            case N:
                explorer.setOrientation(O);
                break;
            case S:
                explorer.setOrientation(E);
                break;
            case E:
                explorer.setOrientation(N);
                break;
            case O:
                explorer.setOrientation(S);
                break;
        }
    }

    /**
     * Turn the explorer to the right
     * @param explorer the explorer who wants to turn right
     */
    private void explorerWantToTurnRight(Explorer explorer) {
        switch (explorer.getOrientation()) {
            case N:
                explorer.setOrientation(E);
                break;
            case S:
                explorer.setOrientation(O);
                break;
            case E:
                explorer.setOrientation(S);
                break;
            case O:
                explorer.setOrientation(N);
                break;
        }
    }

    /**
     * Move the explorer to the new position if he can move
     * @param explorer the explorer who wants to move
     * @param map the map
     */
    private void explorerWantToMove(Explorer explorer, Map map) {
        int positionX = explorer.getPositionX();
        int positionY = explorer.getPositionY();
        int newPositionX = positionX;
        int newPositionY = positionY;
        switch(explorer.getOrientation()) {
            case N:
                newPositionY = positionY - 1;
                break;
            case S:
                newPositionY = positionY + 1;
                break;
            case E:
                newPositionX = positionX + 1;
                break;
            case O:
                newPositionX = positionX - 1;
                break;
        }

        if (explorerCanMove(newPositionX, newPositionY, map)) {
            explorerLeaveHisTuile(positionY, positionX, map);
            explorer.move(newPositionX, newPositionY);
            searchTreasure(explorer, map.getTiles()[newPositionY][newPositionX]);
            map.getTiles()[newPositionY][newPositionX].isNowOccupiedBy(explorer);
        }
    }

    /**
     * Check if the explorer can move to the new position
     * (so if the new position is not out of bound and if the tile is not a mountain and not already occupied)
     * @param positionX the new position on the x-axis
     * @param positionY the new position on the y-axis
     * @param map the map
     * @return true if the explorer can move to the new position, false otherwise
     */
    private boolean explorerCanMove(int positionX, int positionY, Map map) {
        return positionX >= 0 && positionX < map.getWidth() &&
                positionY >= 0 && positionY < map.getHeight() &&
                map.getTiles()[positionY][positionX].canMoveInto();
    }

    private void explorerLeaveHisTuile(int posLongueur, int posLargeur, Map map) {
        map.getTiles()[posLongueur][posLargeur].isNoLongerOccupied();
    }

    /**
     * Search for a treasure on the tile and claim it if there is one
     * @param explorer the explorer who is searching for the treasure
     * @param tile the tile where the explorer is and seaching for the treasure
     */
    private void searchTreasure(Explorer explorer, Tile tile) {
        if (tile.hasTreasure()) {
            Treasure treasure = tile.getTreasure();
            int nbTresors = treasure.getNbTreasures();
            treasure.setNbTreasures(nbTresors - 1);
            explorer.findNewTresor();
            if (treasure.getNbTreasures() == 0) {
                tile.treasureHasBeenFound();
            }
        }
    }

}

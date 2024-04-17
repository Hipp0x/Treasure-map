package pauline.adam;

import pauline.adam.adventurer.Action;
import pauline.adam.adventurer.Adventurer;
import pauline.adam.map.Map;
import pauline.adam.map.Treasure;
import pauline.adam.map.Tile;

import java.util.ArrayList;
import java.util.List;

import static pauline.adam.adventurer.Orientation.*;

public class Adventure {

    private Map map;

    public Adventure(Map map) {
        this.map = map;
    }

    /**
     * Start the treasure hunt until no one can move anymore
     */
    public void treasureHunt() {
        List<Adventurer> adventurers = this.map.getAdventurers();
        while(!adventurers.isEmpty()) {
            ArrayList<Adventurer> adventurersToRemove = new ArrayList<>();
            for (Adventurer adventurer : adventurers) {
                Action actions = adventurer.getActions().get(0);
                switch (actions) {
                    case A:
                        adventurerWantToMove(adventurer, this.map);
                        break;
                    case D:
                        adventurerWantToTurnRight(adventurer);
                        break;
                    case G:
                        adventurerWantToTurnLeft(adventurer);
                        break;
                }
                adventurer.getActions().remove(0);
                if (adventurer.getActions().isEmpty()) {
                    adventurersToRemove.add(adventurer);
                }
            }
            for (Adventurer adventurer : adventurersToRemove) {
                adventurers.remove(adventurer);
            }
        }

    }

    /**
     * Turn the adventurer to the left
     * @param adventurer the adventurer who wants to turn left
     */
    private void adventurerWantToTurnLeft(Adventurer adventurer) {
        switch (adventurer.getOrientation()) {
            case N:
                adventurer.setOrientation(O);
                break;
            case S:
                adventurer.setOrientation(E);
                break;
            case E:
                adventurer.setOrientation(N);
                break;
            case O:
                adventurer.setOrientation(S);
                break;
        }
    }

    /**
     * Turn the adventurer to the right
     * @param adventurer the adventurer who wants to turn right
     */
    private void adventurerWantToTurnRight(Adventurer adventurer) {
        switch (adventurer.getOrientation()) {
            case N:
                adventurer.setOrientation(E);
                break;
            case S:
                adventurer.setOrientation(O);
                break;
            case E:
                adventurer.setOrientation(S);
                break;
            case O:
                adventurer.setOrientation(N);
                break;
        }
    }

    /**
     * Move the adventurer to the new position if he can move
     * @param adventurer the adventurer who wants to move
     * @param map the map
     */
    private void adventurerWantToMove(Adventurer adventurer, Map map) {
        int positionX = adventurer.getPositionX();
        int positionY = adventurer.getPositionY();
        int newPositionX = positionX;
        int newPositionY = positionY;
        switch(adventurer.getOrientation()) {
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

        if (adventurerCanMove(newPositionX, newPositionY, map)) {
            adventurerLeaveHisTuile(positionY, positionX, map);
            adventurer.move(newPositionX, newPositionY);
            searchTreasure(adventurer, map.getTiles()[newPositionY][newPositionX]);
            map.getTiles()[newPositionY][newPositionX].isNowOccupiedBy(adventurer);
        }
    }

    /**
     * Check if the adventurer can move to the new position
     * (so if the new position is not out of bound and if the tile is not a mountain and not already occupied)
     * @param positionX the new position on the x-axis
     * @param positionY the new position on the y-axis
     * @param map the map
     * @return true if the adventurer can move to the new position, false otherwise
     */
    private boolean adventurerCanMove(int positionX, int positionY, Map map) {
        return positionX >= 0 && positionX < map.getWidth() &&
                positionY >= 0 && positionY < map.getHeight() &&
                map.getTiles()[positionY][positionX].canMoveInto();
    }

    private void adventurerLeaveHisTuile(int posLongueur, int posLargeur, Map map) {
        map.getTiles()[posLongueur][posLargeur].isNoLongerOccupied();
    }

    /**
     * Search for a treasure on the tile and claim it if there is one
     * @param adventurer the adventurer who is searching for the treasure
     * @param tile the tile where the adventurer is and seaching for the treasure
     */
    private void searchTreasure(Adventurer adventurer, Tile tile) {
        if (tile.hasTreasure()) {
            Treasure treasure = tile.getTreasure();
            int nbTresors = treasure.getNbTreasures();
            treasure.setNbTreasures(nbTresors - 1);
            adventurer.findNewTreasure();
            if (treasure.getNbTreasures() == 0) {
                tile.treasureHasBeenFound();
            }
        }
    }

}

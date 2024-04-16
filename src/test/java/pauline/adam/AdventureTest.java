package pauline.adam;

import org.junit.jupiter.api.Test;
import pauline.adam.explorer.*;
import pauline.adam.map.Map;
import pauline.adam.map.Treasure;
import pauline.adam.utils.ReadWriteMap;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AdventureTest {

    ReadWriteMap readWriteMap = new ReadWriteMap();

    @Test
    void explorer_should_turn_left() {
        Map map = readWriteMap.readMap("src/test/java/resources/map_with_pauline.txt");
        Explorer explorer = map.getExplorers().get(0);
        map.getExplorers().get(0).setActions(new ArrayList<>(List.of(Action.G)));
        Adventure adventure = new Adventure(map);

        adventure.treasureHunt();

        assertEquals(Orientation.O, explorer.getOrientation());
    }

    @Test
    void explorer_should_turn_right() {
        Map map = readWriteMap.readMap("src/test/java/resources/map_with_pauline.txt");
        Explorer explorer = map.getExplorers().get(0);
        map.getExplorers().get(0).setActions(new ArrayList<>(List.of(Action.D)));
        Adventure adventure = new Adventure(map);

        adventure.treasureHunt();

        assertEquals(Orientation.E, explorer.getOrientation());
    }

    @Test
    void explorer_should_move() {
        Map map = readWriteMap.readMap("src/test/java/resources/map_with_pauline.txt");
        Explorer explorer = map.getExplorers().get(0);
        int positionX = explorer.getPositionX();
        int positionY = explorer.getPositionY();
        Adventure adventure = new Adventure(map);

        adventure.treasureHunt();

        assertEquals(positionX, explorer.getPositionX());
        assertEquals(positionY - 1, explorer.getPositionY());
    }

    @Test
    void explorer_should_not_move_when_moutain_in_front_of_him() {
        Map map = readWriteMap.readMap("src/test/java/resources/map_with_pauline.txt");
        Explorer explorer = map.getExplorers().get(0);
        int positionX = explorer.getPositionX();
        int positionY = explorer.getPositionY();
        map.getExplorers().get(0).setActions(new ArrayList<>(List.of(Action.D, Action.D, Action.A, Action.A)));
        Adventure adventure = new Adventure(map);

        adventure.treasureHunt();

        assertEquals(Orientation.S, explorer.getOrientation());
        assertEquals(positionX, explorer.getPositionX());
        assertEquals(positionY, explorer.getPositionY());
        assertEquals(0, explorer.getActions().size());
    }

    @Test
    void explorer_should_find_treasure() {
        Map map = readWriteMap.readMap("src/test/java/resources/map_with_pauline.txt");
        Treasure treasure = getTreasureFromMap(map);
        int nbTresorsInitial = treasure.getNbTreasures();
        Explorer explorer = map.getExplorers().get(0);
        int positionX = explorer.getPositionX();
        int positionY = explorer.getPositionY();
        map.getExplorers().get(0).setActions(new ArrayList<>(List.of(Action.D, Action.A, Action.A)));
        Adventure adventure = new Adventure(map);

        adventure.treasureHunt();

        assertEquals(Orientation.E, explorer.getOrientation());
        assertEquals(positionX + 2, explorer.getPositionX());
        assertEquals(positionY, explorer.getPositionY());
        assertEquals(1, explorer.getNbTresorsTrouves());
        assertEquals(nbTresorsInitial - 1, treasure.getNbTreasures());
    }

    private Treasure getTreasureFromMap(Map map) {
        for (int i = 0; i < map.getWidth(); i++) {
            for (int j = 0; j < map.getHeight(); j++) {
                if (map.getTiles()[i][j].hasTreasure()) {
                    return map.getTiles()[i][j].getTreasure();
                }
            }
        }
        return null;
    }

}
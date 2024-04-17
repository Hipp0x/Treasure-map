package pauline.adam;

import org.junit.jupiter.api.Test;
import pauline.adam.adventurer.*;
import pauline.adam.map.Map;
import pauline.adam.map.Treasure;
import pauline.adam.utils.ReadWriteMap;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AdventureTest {

    ReadWriteMap readWriteMap = new ReadWriteMap();

    @Test
    void adventurer_should_turn_left() {
        Map map = readWriteMap.readMap("src/test/java/resources/map_with_pauline.txt");
        Adventurer adventurer = map.getAdventurers().get(0);
        map.getAdventurers().get(0).setActions(new ArrayList<>(List.of(Action.G)));
        Adventure adventure = new Adventure(map);

        adventure.treasureHunt();

        assertEquals(Orientation.O, adventurer.getOrientation());
    }

    @Test
    void adventurer_should_turn_right() {
        Map map = readWriteMap.readMap("src/test/java/resources/map_with_pauline.txt");
        Adventurer adventurer = map.getAdventurers().get(0);
        map.getAdventurers().get(0).setActions(new ArrayList<>(List.of(Action.D)));
        Adventure adventure = new Adventure(map);

        adventure.treasureHunt();

        assertEquals(Orientation.E, adventurer.getOrientation());
    }

    @Test
    void adventurer_should_move() {
        Map map = readWriteMap.readMap("src/test/java/resources/map_with_pauline.txt");
        Adventurer adventurer = map.getAdventurers().get(0);
        int positionX = adventurer.getPositionX();
        int positionY = adventurer.getPositionY();
        Adventure adventure = new Adventure(map);

        adventure.treasureHunt();

        assertEquals(positionX, adventurer.getPositionX());
        assertEquals(positionY - 1, adventurer.getPositionY());
    }

    @Test
    void adventurer_should_not_move_when_moutain_in_front_of_him() {
        Map map = readWriteMap.readMap("src/test/java/resources/map_with_pauline.txt");
        Adventurer adventurer = map.getAdventurers().get(0);
        int positionX = adventurer.getPositionX();
        int positionY = adventurer.getPositionY();
        map.getAdventurers().get(0).setActions(new ArrayList<>(List.of(Action.D, Action.D, Action.A, Action.A)));
        Adventure adventure = new Adventure(map);

        adventure.treasureHunt();

        assertEquals(Orientation.S, adventurer.getOrientation());
        assertEquals(positionX, adventurer.getPositionX());
        assertEquals(positionY, adventurer.getPositionY());
        assertEquals(0, adventurer.getActions().size());
    }

    @Test
    void adventurer_should_find_treasure() {
        Map map = readWriteMap.readMap("src/test/java/resources/map_with_pauline.txt");
        Treasure treasure = getTreasureFromMap(map);
        int nbTresorsInitial = treasure.getNbTreasures();
        Adventurer adventurer = map.getAdventurers().get(0);
        int positionX = adventurer.getPositionX();
        int positionY = adventurer.getPositionY();
        map.getAdventurers().get(0).setActions(new ArrayList<>(List.of(Action.D, Action.A, Action.A)));
        Adventure adventure = new Adventure(map);

        adventure.treasureHunt();

        assertEquals(Orientation.E, adventurer.getOrientation());
        assertEquals(positionX + 2, adventurer.getPositionX());
        assertEquals(positionY, adventurer.getPositionY());
        assertEquals(1, adventurer.getNbTreasuresFound());
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
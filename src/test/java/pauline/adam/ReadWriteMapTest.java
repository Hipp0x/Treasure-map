package pauline.adam;

import org.junit.jupiter.api.Test;
import pauline.adam.map.Map;
import pauline.adam.map.Tile;
import pauline.adam.exceptions.Exceptions;
import pauline.adam.utils.ReadWriteMap;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReadWriteMapTest {

    ReadWriteMap readWriteMap = new ReadWriteMap();

    @Test
    void should_fail_to_init_map_when_bad_mapPath() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> readWriteMap.readMap("src/test/java/resources/badMapPath.txt"));

        assertEquals(Exceptions.FILE_NOT_FOUND_EXCEPTION.name(), exception.getMessage());
    }

    @Test
    void when_empty_map_should_throw_empty_map_error() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> readWriteMap.readMap("src/test/java/resources/empty_map.txt"));

        assertEquals(Exceptions.EMPTY_MAP.name(), exception.getMessage());
    }

    @Test
    void when_map_only_commentaire_should_throw_no_map_def_error() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> readWriteMap.readMap("src/test/java/resources/map_with_only_commentaires.txt"));

        assertEquals(Exceptions.NO_MAP_DEFINITION.name(), exception.getMessage());
    }

    @Test
    void should_init_map() {
        Map map = readWriteMap.readMap("src/test/java/resources/map.txt");

        assertNotNull(map);
        assertEquals(3, map.getWidth());
        assertEquals(4, map.getHeight());
        assertEquals(2, countNbMountains(map));
        assertEquals(2, countNbTresors(map));
        assertEquals(1, map.getExplorers().size());
    }

    private int countNbTresors(Map map) {
        int nbTresors = 0;
        for (Tile[] tiles : map.getTiles()) {
            for (Tile tile : tiles) {
                if (tile.hasTreasure()) {
                    nbTresors++;
                }
            }
        }
        return nbTresors;
    }

    private int countNbMountains(Map map) {
        int nbMountains = 0;
        for (Tile[] tiles : map.getTiles()) {
            for (Tile tile : tiles) {
                if (tile.isMountain()) {
                    nbMountains++;
                }
            }
        }
        return nbMountains;
    }

    @Test
    void should_write_map() throws IOException {

        Map expectedMap = readWriteMap.readMap("src/test/java/resources/map.txt");
        readWriteMap.writeMap(expectedMap, "src/test/java/resources/map_written.txt");
        List<String> allLinesForResultCarte = Files.readAllLines(Paths.get("src/test/java/resources/map_written.txt"));

        assertEquals(8, allLinesForResultCarte.size());
        assertEquals("C - 3 - 4", allLinesForResultCarte.get(0));
        assertEquals("M - 1 - 0", allLinesForResultCarte.get(1));
        assertEquals("M - 2 - 1", allLinesForResultCarte.get(2));
        assertEquals("T - 0 - 3 - 2", allLinesForResultCarte.get(4));
        assertEquals("T - 1 - 3 - 3", allLinesForResultCarte.get(5));
        assertEquals("A - Lara - 1 - 1 - S - 0", allLinesForResultCarte.get(7));
    }

}
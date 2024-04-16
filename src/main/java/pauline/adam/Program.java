package pauline.adam;

import pauline.adam.map.Map;
import pauline.adam.utils.ReadWriteMap;

public class Program {

    public static void chasseAuTresor(String path) {

        ReadWriteMap readWriteMap = new ReadWriteMap();
        Map map = readWriteMap.readMap(path);
        Adventure adventure = new Adventure(map);

        adventure.treasureHunt();

        readWriteMap.writeMap(map, path + "_result");

    }
}

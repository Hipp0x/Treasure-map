package pauline.adam.utils;

import pauline.adam.explorer.Explorer;
import pauline.adam.map.Map;
import pauline.adam.map.Tile;
import pauline.adam.exceptions.Exceptions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadWriteMap {

    /**
     * Initialise la carte selon les données d'un fichier
     * @param mapPath le chemin du fichier
     */
    public Map readMap(String mapPath) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader(mapPath));
            String line = reader.readLine();

            if (line == null) {
                throw new RuntimeException(Exceptions.EMPTY_MAP.name());
            }

            Map map = createMap(line);
            line = reader.readLine();
            while (line != null) {
                String[] lineArray = line.split(" - ");
                switch (lineArray[0]) {
                    case "C":
                        throw new RuntimeException(Exceptions.DOUBLE_MAP_DEFINITION.name());
                    case "M":
                        map.addMountain(lineArray[1], lineArray[2]);
                        break;
                    case "T":
                        map.addTreasure(lineArray[1], lineArray[2], lineArray[3]);
                        break;
                    case "A":
                        map.addExplorer(lineArray[1], lineArray[2], lineArray[3], lineArray[4], lineArray[5]);
                        break;
                    default:
                        break;
                }
                line = reader.readLine();
            }
            return map;

        } catch (FileNotFoundException e) {
            throw new RuntimeException(Exceptions.FILE_NOT_FOUND_EXCEPTION.name());
        } catch (IOException e) {
            throw new RuntimeException(Exceptions.IOEXCEPTION.name());
        }
    }

    private Map createMap(String line) {
        String[] lineArray = line.split(" - ");
        if (!lineArray[0].equals("C")) {
            throw new RuntimeException(Exceptions.NO_MAP_DEFINITION.name());
        }
        return new Map(lineArray[1], lineArray[2]);
    }

    /**
     * Ecrit la carte dans un fichier au chemin souhaité
     * @param map la carte à écrire
     * @param mapPath le chemin du fichier
     */
    public void writeMap(Map map, String mapPath) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(mapPath));
            // Ecriture de la taille de la carte
            writer.write("C - " + map.getWidth() + " - " + map.getHeight());
            writer.newLine();
            // Récupération des mountains, treasures et explorers
            List<Tile> mountains = new ArrayList<>();
            List<Tile> treasures = new ArrayList<>();
            List<Explorer> explorers = new ArrayList<>();
            for (Tile[] tiles : map.getTiles()) {
                for (Tile tile : tiles) {
                    if (tile.hasTreasure()) {
                        treasures.add(tile);
                    }
                    if (tile.isMountain()) {
                        mountains.add(tile);
                    }
                    if (tile.isOccupied()) {
                        explorers.add(tile.getOccupant());
                    }
                }
            }
            // Ecritures des mountains, treasures et explorers
            writeMountains(writer, mountains);
            writeTresors(writer, treasures);
            writeAventuriers(writer, explorers);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(Exceptions.IOEXCEPTION.name());
        }
    }

    private void writeMountains(BufferedWriter writer, List<Tile> mountains) {
        for (Tile mountain : mountains) {
            try {
                writer.write("M - " + mountain.getPositionX() + " - " + mountain.getPositionY());
                writer.newLine();
            } catch (IOException e) {
                throw new RuntimeException(Exceptions.IOEXCEPTION.name());
            }
        }
    }

    private void writeTresors(BufferedWriter writer, List<Tile> treasures) {
        try {
            writer.write("# {T comme Trésor} - {Axe horizontal} - {Axe vertical} - {Nb. de trésors restants}\n");
            for (Tile treasure : treasures) {
                writer.write("T - " + treasure.getPositionX() + " - " + treasure.getPositionY() + " - " + treasure.getTreasure().getNbTreasures());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(Exceptions.IOEXCEPTION.name());
        }
    }

    private void writeAventuriers(BufferedWriter writer, List<Explorer> explorers) {
        try {
            writer.write("#  {A comme Aventurier} - {Nom de l’aventurier} - {Axe horizontal} - {Axe vertical} - {Orientation} - {Nb. trésors ramassés}\n");
            for (Explorer explorer : explorers) {
                writer.write("A - " + explorer.getName() + " - " + explorer.getPositionX() + " - " + explorer.getPositionY() + " - " + explorer.getOrientation() + " - " + explorer.getNbTresorsTrouves());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(Exceptions.IOEXCEPTION.name());
        }
    }
}

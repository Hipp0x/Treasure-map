package pauline.adam.exceptions;

public enum Exceptions {

    FILE_NOT_FOUND_EXCEPTION("File not found"),
    IOEXCEPTION("IO Exception while reading file"),
    EMPTY_MAP("Empty map"),
    NO_MAP_DEFINITION("No map definition at top of the file"),
    DOUBLE_MAP_DEFINITION("Map already defined at top of the file"),
    MOUNTAIN_OUT_OF_BOUNDS("Mountain out of bounds"),
    TREASURE_OUT_OF_BOUNDS("Treasure out of bounds"),
    ADVENTURER_OUT_OF_BOUNDS("AdventurerAdventurer out of bounds"),
    NEGATIVE_TREASURE("Negative treasure")
    ;

    Exceptions(String error) {}
}

package pauline.adam.map;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Treasure {

    private final int positionX;
    private final int positionY;
    @Setter private int nbTreasures;

    public Treasure(int positionX, int positionY, int nbTreasures) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.nbTreasures = nbTreasures;
    }
}

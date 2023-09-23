package td.tower;

import java.util.List;
import td.monster.Monster;
import td.Block;

/**
 * Archery 
 * 
 * The archery tower will aim only one monster that has positive, non-zero 
 * health point. If there are multiple monster that are in range,
 * pick the one that is nearest to "home".
 * 
 * Propoerty of Archery tower:
 * Symbol : 'A'
 * Inital power: 5
 * Range : 3
 * cost : 5
 * upgrade power: 1 
 * upgrade cost: 2
 */
public class ArcheryTower extends Tower {

    private char symbol;
    private int range = 3;

    public ArcheryTower(int row, int col) {
        super(row, col, 5, 5, 2, 1, 3);
        symbol = 'A';
    }

    public void action(List<Block> blocks) {

        // array size
        int monsterCount = 0;

        // now search the blocks again, but this time search for every monster within the range
        for (int j = 0 ; j < blocks.size() ; j++) {
            if (blocks.get(j) instanceof Monster && isInRange(blocks.get(j))) {
                monsterCount++;
            }
        }

        // new array for monster index
        int[] monstersIndex = new int[monsterCount];

        // store the monsters in this index
        Monster[] toBeAttacked = new Monster[monsterCount];

        // to search through the index in the array
        int toBeAttackedIndex = 0;

        // now loop through the blocks again, but this time search for every monster within the range
        for (int j = 0 ; j < blocks.size() ; j++) {
            if (blocks.get(j) instanceof Monster && isInRange(blocks.get(j))) {

                toBeAttacked[toBeAttackedIndex] = ((Monster) blocks.get(j));

                monstersIndex[toBeAttackedIndex] = j;

                toBeAttackedIndex++;


            }
        }

        // variable to store index of monster closest to home
        int monster_index_closest_home = 0;
        int indexToDamage = 0;
        for (int k = 0; k < toBeAttacked.length; k++)
            if (toBeAttacked[k].getCol() > monster_index_closest_home) {
                monster_index_closest_home = toBeAttacked[k].getCol();
                indexToDamage = monstersIndex[k];
            }

        if (monsterCount > 0) {
            if (blocks.get(indexToDamage) instanceof Monster) {
                // decrease health of monster
                ((Monster) blocks.get(indexToDamage)).deductHealth(getPower());
            }
        }


    }

    public int getRange() {

        return range;

    }

    public char getSymbol() {

        return symbol;

    }

}
package td.tower;

import td.Block;
import td.Game;
import td.monster.Monster;

import java.util.List;

/**
 * Catapult
 * 
 * A catapult works in the following way. It will target on
 * one monster among all monsters that are in range. When there
 * are more than one monsters in range, pick ANY monster with 
 * highest remaining health point.
 * 
 * Then, it hits the target monster and other monsters located in
 * its 8 neighthor adjacent cells. For example,
 * ----------------------
 * | a | b | c | e |
 * | d | f | g | h |
 * | i | j | k | l |  ...
 * | m | n | o | p |
 * ----------------------
 * * If g is the target monster, monsters <b, c, e, f, g, h, j, k, l>
 * will receive damage.
 * * If m is the target monster, monsters <i, j, m, n>
 * will receive damage.
 * 
 * Note: In the first example, even if monster b is out of the range 
 * of the Tower, as long as Tower can hit g, b will also receive damage.
 * 
 * Propoerty of Catapult:
 * Symbol : 'C'
 * Inital power: 4
 * Range : 6
 * cost : 7
 * upgrade power: 2 
 * upgrade cost: 3
 * 
 */
public class CatapultTower extends Tower {
    private char symbol;
    private int range = 6;

    public CatapultTower(int row, int col){

        super(row, col, 4,7,3,2,6);
        symbol = 'C';

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


        // monster with most health
        int monster_max = 0;
        // variable to store index of monster with the greatest hp
        int monster_index_highest_hp = 0;

        for (int k = 0; k < toBeAttacked.length; k++) {
            if (toBeAttacked[k].getHp() > monster_max) {
                monster_max = toBeAttacked[k].getHp();
                monster_index_highest_hp = monstersIndex[k];
            }
        }

        // get the index of the monster with the greatest column number i.e. closest to home
        for (int k = 0 ; k < toBeAttacked.length ; k++) {
            if (toBeAttacked[k].getCol() > monster_index_highest_hp) {
                monster_index_highest_hp = monstersIndex[k];
            }
        }

        if (monsterCount > 0) {
            if (blocks.get(monster_index_highest_hp) instanceof Monster) {
                // decrease health of monster
                ((Monster) blocks.get(monster_index_highest_hp)).deductHealth(getPower());

                // store values of the monster being attached
                int xCoordinate = blocks.get(monster_index_highest_hp).getRow();
                int yCoordinate = blocks.get(monster_index_highest_hp).getCol();

                // loop through all the monsters
                for (int i = 0; i < blocks.size(); i++) {
                    if (blocks.get(i) instanceof Monster)
                        if (Math.abs(blocks.get(i).getRow() - xCoordinate) == 1 && Math.abs(blocks.get(i).getCol() - yCoordinate) == 1)
                            ((Monster) blocks.get(i)).deductHealth(getPower());
                }

                for (int i = 0; i < blocks.size(); i++) {
                    if (blocks.get(i) instanceof Monster)
                        if (Math.abs(blocks.get(i).getRow() - xCoordinate) == 1 && Math.abs(blocks.get(i).getCol() - yCoordinate) == 0)
                            ((Monster) blocks.get(i)).deductHealth(getPower());
                }

                for (int i = 0; i < blocks.size(); i++) {
                    if (blocks.get(i) instanceof Monster)
                        if (Math.abs(blocks.get(i).getRow() - xCoordinate) == 0 && Math.abs(blocks.get(i).getCol() - yCoordinate) == 1)
                            ((Monster) blocks.get(i)).deductHealth(getPower());

                }
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

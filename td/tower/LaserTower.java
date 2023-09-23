package td.tower;

import td.Block;
import td.Game;
import td.monster.Monster;

import java.util.List;

/**
 * Laser Tower
 * 
 * A Laser tower can shoot all monsters on its left (whose has a
 * smaller col than the tower) on the same row. 
 * 
 * All monster will receive the same number of damage.
 * 
 * Propoerty of laser tower:
 * Symbol : 'L'
 * Inital power: 4
 * Range : N/A (you can place any value here)
 * cost : 7
 * upgrade power: 2 
 * upgrade cost: 3
 */
public class LaserTower extends Tower{

    private char symbol;

    public LaserTower(int row, int col) {

        super(row, col, 4, 7, 3, 2, 1000);
        symbol = 'L';

    }
    public void action(List<Block> blocks) {

        // for all of the blocks
        for (int i = 0; i < blocks.size(); i++)

            // if the monster is in the row
            if (blocks.get(i) instanceof Monster && blocks.get(i).getRow() == this.row) {

                //then damage them
                ((Monster) blocks.get(i)).deductHealth(getPower());

            }

    }
    public char getSymbol() {

        return symbol;

    }

}


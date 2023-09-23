package td;

import java.util.Scanner;

import td.tower.*;

/**
 * A View class. To display the information on screen and also 
 * to take user's control.
 */
public class ConsoleDisplay implements Displayable {
    /**
     * The controller object game.
     */
    protected Game game;

    /**
     * Entry point. Don't touch
     */
    public static void main(String[] args) {
        new ConsoleDisplay();
    }

    /**
     * Constructor. To construct the game object and call game.run();
     */
    public ConsoleDisplay() {
        this.game = new Game(this);
        game.run();
    }

    /**
     * To display the score, money, map and character on screen.
     */
    @Override
    public void display() {

        System.out.println("Score: " + game.getScore() + " | Money: " + game.getMoney());
        System.out.println("----------------");

        for (int i = 0; i < Game.HEIGHT; i++) {

            for (int j = 0; j < Game.WIDTH; j++) {

                if (game.getBlockByLocation(i, j) != null) {

                    System.out.print(game.getBlockByLocation(i, j).getSymbol());

                } else System.out.print(" ");

            }

            System.out.print("oooo");
            System.out.println();

        }

    }

    /**
     * To accept user input (build tower, upgrade tower, view blocks).
     * <p>
     * This method has been done for you.
     * You should not modify it.
     * You are not allowed to modify it.
     */
    @Override
    public void userInput() {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            printInstruction();
            try {
                switch (scanner.nextInt()) {
                    case 1:
                        option1();
                        break;
                    case 2:
                        option2();
                        break;
                    case 3:
                        option3();
                        break;
                    case 4:
                        return;
                    default:
                        throw new InvalidInputException("Invalid option! Pick only 1-4");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            display();
        }
    }

    /**
     * Given method.
     * <p>
     * You are not supposed to change this method.
     * But you can change if you wish.
     */
    private void printInstruction() {
        System.out.println("Please pick one of the following: ");
        System.out.println("1. View a tower/monster");
        System.out.println("2. Build a new Tower");
        System.out.println("3. Upgrade a Tower");
        System.out.println("4. End a turn");
    }


    public void option1() {

        // create scanner and text
        Scanner input = new Scanner(System.in);

        System.out.println("Enter the coordinate of the tower/monster row followed by column:");

        try {
            int rowSelected = input.nextInt();
            int colSelected = input.nextInt();

            // if there is a block
            if (game.getBlockByLocation(rowSelected, colSelected) != null) {

                // If there is a tower
                if (game.getBlockByLocation(rowSelected, colSelected) instanceof Tower) {

                    // if there is a laser tower
                    if (game.getBlockByLocation(rowSelected, colSelected) instanceof LaserTower) {

                        for (int i = 0; i < Game.WIDTH; i++) {
                            System.out.print('-');
                        }
                        for (int i = 0; i < 4; i++) {
                            System.out.print('-');
                        }
                        System.out.println();

                        for (int i = 0; i < Game.HEIGHT; i++) {

                            for (int j = 0; j < Game.WIDTH; j++) {

                                if (game.getBlockByLocation(i, j) != null) {
                                    System.out.print(game.getBlockByLocation(i, j).getSymbol());
                                } else if (i < rowSelected && j < colSelected) {
                                    System.out.print('#');
                                } else {
                                    System.out.print(' ');
                                }

                            }
                            System.out.println("oooo");

                        }

                        System.out.println(game.getBlockByLocation(rowSelected, colSelected));

                    }

                    // If it is an archery tower
                    else if (game.getBlockByLocation(rowSelected, colSelected) instanceof ArcheryTower) {

                        for (int i = 0; i < Game.WIDTH; i++) {
                            System.out.print('-');
                        }
                        for (int i = 0; i < 4; i++) {
                            System.out.print('-');
                        }
                        System.out.println();

                        for (int i = 0; i < Game.HEIGHT; i++) {

                            for (int j = 0; j < Game.WIDTH; j++) {

                                if (game.getBlockByLocation(i, j) != null) {
                                    System.out.print(game.getBlockByLocation(i, j).getSymbol());
                                } else if (Math.abs(i - rowSelected) + Math.abs(j - colSelected) <= ((ArcheryTower) game.getBlockByLocation(rowSelected, colSelected)).getRange()) {
                                    System.out.print('#');
                                } else {
                                    System.out.print(' ');
                                }

                            }

                            System.out.println("oooo");

                        }

                        System.out.println(game.getBlockByLocation(rowSelected, colSelected));

                    }

                    // Otherwise it is a catapult tower
                    else {

                        for (int i = 0; i < Game.WIDTH; i++) {
                            System.out.print('-');
                        }
                        for (int i = 0; i < 4; i++) {
                            System.out.print('-');
                        }
                        System.out.println();

                        for (int i = 0; i < Game.HEIGHT; i++) {

                            for (int j = 0; j < Game.WIDTH; j++) {

                                if (game.getBlockByLocation(i, j) != null) {
                                    System.out.print(game.getBlockByLocation(i, j).getSymbol());
                                } else if (Math.abs(i - rowSelected) + Math.abs(j - colSelected) <= ((CatapultTower) game.getBlockByLocation(rowSelected, colSelected)).getRange()) {
                                    System.out.print('#');
                                } else {
                                    System.out.print(' ');
                                }

                            }

                            System.out.println("oooo");

                        }

                        System.out.println(game.getBlockByLocation(rowSelected, colSelected));
                    }

                }

                // If there is a monster
                else {
                    System.out.println(game.getBlockByLocation(rowSelected, colSelected));
                }

            }
            // If not a block
            else {
                throw new InvalidInputException("Null!");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void option2() {

        Scanner in = new Scanner(System.in);

        System.out.println("1. ArcheryTower ($5); 2. LaserTower ($7); 3 CatapultTower ($7)");

        try {

            int towerType = in.nextInt();

            System.out.println("Which row?");

            int rowSelected = in.nextInt();

            System.out.println("Which column?");

            int colSelected = in.nextInt();

            if (!game.build(towerType, rowSelected, colSelected)) {
                throw new InvalidInputException("The option is invalid! Please make sure there is no existing tower or monster on the block and the column number is not 0.");
            }

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

    }

    public void option3() {

        Scanner updateScanner = new Scanner(System.in);

        try {

            System.out.println("Which row?");

            int rowSelected = updateScanner.nextInt();

            System.out.println("Which column?");

            int colSelected = updateScanner.nextInt();

            if (!game.upgrade(rowSelected, colSelected)) {
                throw new InvalidInputException("The option is invalid! Please make sure there is an existing tower on the block and you have sufficient funds.");
            }

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

    }

    public void gameOver() {
        System.out.println("Bye!");
    }
}

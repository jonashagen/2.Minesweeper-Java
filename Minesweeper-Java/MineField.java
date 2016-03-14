
import java.util.Random;

class MineField {

    private boolean[][] mines, visible;
    private boolean boom;
    private final int rowMax = 5;
    private final int colMax = 10;

    /**
     * Constructs minefield.
     */
    MineField() {

        mines = new boolean[rowMax][colMax];
        visible = new boolean[rowMax][colMax];
        boom = false;

        initMap();

        int counter2 = 15;
        int randomRow, randomCol;
        Random RGenerator = new Random();

        while (counter2 > 0) {
            randomRow = Math.abs(RGenerator.nextInt() % rowMax);
            randomCol = Math.abs(RGenerator.nextInt() % colMax);

            if (trymove(randomRow, randomCol)) counter2--;
        }
    }
    
    /**
     * Initializing maps.
     */
    private void initMap() {
        for (int row = 0; row < rowMax; row++) {
            for (int col = 0; col < colMax; col++) {
                mines[row][col] = false;
                visible[row][col] = false;
            }
        }
    }
    
    /**
     * @param randomRow
     * @param randomCol
     */
    private boolean trymove(int randomRow, int randomCol) {
        if (mines[randomRow][randomCol]) return false;
        mines[randomRow][randomCol] = true;
        return true;
    }
    
    /**
     * Checks if coordinates contains mine.
     */
    private void boom() {
        for (int row = 0; row < rowMax; row++) {
            for (int col = 0; col < colMax; col++) {
                if (mines[row][col]) visible[row][col] = true;
            }
        }
        boom = true;
        show();
    }
    
    /**
     * Returns a char 0-8 if row and col is visible and is not a mine.
     * @param row
     * @param col
     * @return char
     */
    private char drawChar(int row, int col) {
        if(!visible[row][col]){
            if(boom) return '-';
            return '?';
        }   
        if(mines[row][col]) return '*';
        
        return Integer.toString(checkSurroundingFields(row, col)).charAt(0);
    }
    
    /**
     * Counts the number of mines surrounding the coordinates that were entered.
     * @param row
     * @param col
     * @return count
     */
    public int checkSurroundingFields(int row, int col){
        int count = 0;
        for(int irow=row-1;irow<=row+1;irow++){
            for(int icol=col-1;icol<=col+1;icol++){
                if(icol>=0&&icol<colMax&&irow>=0&&irow<rowMax){
                    if(mines[irow][icol]) count++;
                }
            }
        }
        return count;
    }
    
    /**
     * @return boom
     */
    public boolean getBoom() {
        return boom;
    }
    
    /**
     * Checks if you made a legal move.
     * @param input
     * @return true/false
     */
    public boolean legalMoveString(String input) {
        String[] separated = input.split(" ");
        int row;
        int col;
        try {
            row = Integer.parseInt(separated[0]);
            col = Integer.parseInt(separated[1]);
            if (row < 0 || col < 0 || row >= rowMax || col >= colMax) {
                throw new java.io.IOException();
            }
        } catch (Exception e) {
            System.out.println("\nInvalid Input!");
            return false;
        }
        if (legalMoveValue(row, col)) return true;
        return false;
    }
    
    /**
     * Checks if you made a legal move.
     * @param row
     * @param col
     * @return true/false
     */
    private boolean legalMoveValue(int row, int col) {

        if (visible[row][col]) {
            System.out.println("You stepped in allready revealed area!");
            return false;
        }
        visible[row][col] = true;

        if (mines[row][col]) {
            boom();
            return false;
        }
        return true;
    }
    
    /**
     * Writes the minefield to screen.
     */
    public void show() {
        System.out.println("\n    0 1 2 3 4 5 6 7 8 9 ");
        System.out.println("   ---------------------");
        for (int row = 0; row < rowMax; row++) {
            System.out.print(row + " |");
            for (int col = 0; col < colMax; col++) {
                System.out.print(" " + drawChar(row, col));
            }
            System.out.println(" |");
        }
        System.out.println("   ---------------------");
    }

}

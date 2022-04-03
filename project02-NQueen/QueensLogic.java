import net.sf.javabdd.*;

public class QueensLogic implements IQueensLogic {
    private int size;		// Size of quadratic game board (i.e. size = #rows = #columns)
    private int[][] board;	// Content of the board. Possible values: 0 (empty), 1 (queen), -1 (no queen allowed)
    private BDDFactory factory;
    private BDD bdd;
    
    public void initializeBoard(int size) {
        this.size = size;
        this.board = new int[size][size];
        this.bdd = make(size);
    }
   
    public int[][] getBoard() {
        return board;
    }

    public void insertQueen(int column, int row) {
        if (board[column][row] == -1) return; // Just return if selected sqaure has value -1

        board[column][row] = 1; // place the queen on the position
        this.bdd = this.bdd.restrict(getIthVar(column, row));
        
        // check for unsatisfiability, and mark squares as "no queen allowed"
        int availableQueenSqaures = 0;
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                if (this.bdd.restrict(getIthVar(c, r)).isZero()) {
                    board[c][r] = -1;
                } else {
                    availableQueenSqaures++;
                }
            }
        }

        // fill out all the squares that can contain queens, if available queen sqaures is equal to the number of queens required
        if (availableQueenSqaures == size) {                   
            for (int r = 0; r < size; r++) {
                for (int c = 0; c < size; c++) {
                    if (board[c][r] != -1) {
                        board[c][r] = 1;
                    }
                }
            }
        }
    }

    private BDD getIthVar(int column, int row) {
        return factory.ithVar(row*size+column);
    }

    private BDD setupRules() {
        BDD general = factory.one();
        for (int row = 0; row < size; row++) {
            BDD oneInRow = factory.zero();
            for (int column = 0; column < size; column++) {
                BDD square = getIthVar(column, row);
                oneInRow = oneInRow.or(square);
                BDD noTake = factory.one();

                // column restriction
                for (int r = 0; r < size; r++) {
                    if (row != r) {
                        noTake = noTake.and(getIthVar(column, r).not());
                    }
                }

                // row restriction
                for (int c = 0; c < size; c++) {
                    if (column != c) {
                        noTake = noTake.and(getIthVar(c, row).not());
                    }
                }

                // diagonal restriction 
                for (int r = 0; r < size; r++) {
                    for (int c = 0; c < size; c++) {
                        if (Math.abs(row-r) == Math.abs(column-c) && (row != r)) { // is on a diagonal
                            noTake = noTake.and(getIthVar(c, r).not());
                        }
                    }
                }

                general = general.and(square.imp(noTake)); // make sure to apply sub bdd to general bdd 
            }
            general = general.and(oneInRow);
        }

        return general;
    }

    private BDD make(int n) {
        factory = JFactory.init(2000000, 200000);
        factory.setVarNum(n*n);
        return setupRules();
    }
}

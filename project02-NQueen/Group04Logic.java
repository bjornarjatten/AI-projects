import net.sf.javabdd.*;

public class Group04Logic implements IQueensLogic{
    private int size;		// Size of quadratic game board (i.e. size = #rows = #columns)
    private int[][] board;  // Content of the board. Possible values: 0 (empty), 1 (queen), -1 (no queen allowed)
    private int movesLeft;  // An int to keep track of how many squares a queen can be placed in
    private BDDFactory fac;
    private BDD True;
    private BDD False;
    private BDD bdd;

    //TO COMPILE USE THIS:  javac -cp .\javabdd-1.0b2.jar *.java in vscode
    //TO RUN USE THIS: java -cp "javabdd-1.0b2.jar;." Queens OurLogic 6 

    public void initializeBoard(int size) {
        this.size = size;
        System.out.println("Loading");
        this.board = new int[size][size];
        buildBDD();
        updateboard();
        System.out.println("Game is ready\n");
        System.out.println("Queens to place " + size);
        System.out.println("you have " + movesLeft + " options");
    }
   
    public int[][] getBoard() {
        return board;
    }

    //building the BDD and adding Rules
    public void buildBDD(){
        //creating factory
        this.fac = JFactory.init(2000000, 200000);
        this.fac.setVarNum(this.size*this.size); 
        this.False = this.fac.zero();
        this.True = this.fac.one();
        this.bdd = True;
        rules();
    }

    // specifying a rule that Each row must contain one queen
    private void allRowsRule(){
        for(int y = 0; y < size; y++){
            BDD subBdd = False;
            for(int x = 0; x < size; x++){
                subBdd = subBdd.or(this.fac.ithVar(place(x, y)));
            }
            this.bdd = this.bdd.and(subBdd);
        }
    }

    //Assembling the calls to implement rules 
    private void rules(){
        allRowsRule();
        for(int x = 0; x < size; x++){
            BDD subBdd = False;
            for(int y = 0; y < size; y++){
                eval(x,y);
                subBdd = subBdd.or(this.fac.ithVar(place(x, y)));
            }
            this.bdd = this.bdd.and(subBdd);
        }
    }
    

    //evaluating a given position in relation to its horizontal, vertical and diagnol fields
    private void eval(int x, int y){
        BDD unavailableBDD = False;
        BDD availableBDD = True;      
        
        //all y should be false
        for(int column = 0; column < size; column++){
            if (y != column) availableBDD = availableBDD.and(this.fac.nithVar(place(x,column)));
        }

        //all x should false
        for(int row = 0; row < size; row++){
            if (x != row) availableBDD = availableBDD.and(this.fac.nithVar(place(row,y)));
        }
    
        // all diagonal left should false 
        for (int row = 0; row < size; row++) {
            if (x != row) {
                if ((y+row-x < size) && (y+row-x > 0)) {
                    availableBDD = availableBDD.and(this.fac.nithVar(place(row,y+row-x)));
                }
            }
        }

        // all diagonal right should false 
        for (int row = 0; row < size; row++) {
            if (x != row) {
                if ((y-row+x < size) && (y-row+x > 0)) {
                    availableBDD = availableBDD.and(this.fac.nithVar(place(row,y-row+x)));
                }
            }
        }


        unavailableBDD = unavailableBDD.or(this.fac.nithVar(place(x,y)));
        unavailableBDD = unavailableBDD.or(availableBDD);

        this.bdd = this.bdd.and(unavailableBDD);

    }
    
    // returns a variable number for a collum*row in the BDD
    private int place(int column,int row) {
        return row*this.size+column;
      }

    // checks is a given place is valid or not
    private boolean isInvalid(int column, int row){
        BDD testBDD = this.bdd.restrict(this.fac.ithVar(place(column,row)));
        return testBDD.isZero();
    }
    
    //runs through the board and and updates the fields in accordance to the rules
    // additionaly contains a check to autofill the board if no options left
    private void updateboard(){
        int noQueen = 0;
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                if(isInvalid(i, j)){
                    board[i][j] = -1;
                }else{
                    noQueen++;
                }
            }
        }

        movesLeft = noQueen;
        if(noQueen == size){
            for (int r = 0; r < size; r++) {
                for (int c = 0; c < size; c++) {
                    if (board[c][r] != -1) {
                        board[c][r] = 1;
                    }
                }
            }
        }
        
    }

    public void insertQueen(int column, int row) {
        if (board[column][row] == -1 || board[column][row] == 1){
            return;
        } 

        board[column][row] = 1;
        this.bdd = this.bdd.restrict(this.fac.ithVar(row*this.size+column));
        updateboard();
        movesLeft = movesLeft -1;
        if(movesLeft <= size){
            System.out.println("No options left, autofilling board");
        }else{
            System.out.println("You have "+ movesLeft + " possible options");
        }
        
    }    
}

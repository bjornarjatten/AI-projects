import net.sf.javabdd.*;

public class OurLogic implements IQueensLogic{
    private int size;		// Size of quadratic game board (i.e. size = #rows = #columns)
    private int[][] board;	// Content of the board. Possible values: 0 (empty), 1 (queen), -1 (no queen allowed)
    private BDDFactory fac;
    private BDD True;
    private BDD False;
    private BDD bdd;

    //TO COMPILE USE THIS:  javac -cp .\javabdd-1.0b2.jar *.java in vscode
    //TO RUN USE THIS: java -cp "javabdd-1.0b2.jar;." Queens OurLogic 6 

    public void initializeBoard(int size) {
        this.size = size;
        System.out.println("this is size " + size);
        this.board = new int[size][size];
        buildBDD();
    }
   
    public int[][] getBoard() {
        return board;
    }

    public void buildBDD(){
        this.fac = JFactory.init(2000000, 200000);
        this.fac.setVarNum(this.size*this.size); 
        this.False = this.fac.zero();
        this.True = this.fac.one();
        this.bdd = True;
        rules();
        eightRule();
        System.out.println("is ready");
    }

    private void eightRule(){
        for(int y = 0; y < size; y++){
            BDD subBdd = False;
            for(int x = 0; x < size; x++){
                subBdd = subBdd.or(this.fac.ithVar(place(x, y)));
            }
            this.bdd = this.bdd.and(subBdd);
        }
    }

    private void rules(){
        for(int x = 0; x < size; x++){
            for(int y = 0; y < size; y++){
                eval(x,y);
            }
        }
    }
    

    private void eval(int x, int y){
        BDD unavailableBDD = False;
        BDD availableBDD = True;      
        
        //all y = false
        for(int y2 = 0; y2 < size; y2++){
            if (y != y2) availableBDD = availableBDD.and(this.fac.nithVar(place(x,y2)));
        }

        //all x = false
        for(int x2 = 0; x2 < size; x2++){
            if (x != x2) availableBDD = availableBDD.and(this.fac.nithVar(place(x2,y)));
        }
    
        // all diagonal left = false 
        for (int x2 = 0; x2 < size; x2++) {
            if (x != x2) {
                if ((y+x2-x < size) && (y+x2-x > 0)) {
                    availableBDD = availableBDD.and(this.fac.nithVar(place(x2,y+x2-x)));
                }
            }
        }

        // all diagonal right = false 
        for (int x2 = 0; x2 < size; x2++) {
            if (x != x2) {
                if ((y-x2+x < size) && (y-x2+x > 0)) {
                    availableBDD = availableBDD.and(this.fac.nithVar(place(x2,y-x2+x)));
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

    private boolean isInvalid(int column, int row){
        BDD testBDD = this.bdd.restrict(this.fac.ithVar(place(column,row)));
        return testBDD.isZero();
    }
    
    private void updateboard(){
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                if(isInvalid(i, j)){
                    board[i][j] = -1;
                }
            }
        }
    }

    public void insertQueen(int column, int row) {
        board[column][row] = 1;
        this.bdd = this.bdd.restrict(this.fac.ithVar(row*this.size+column));
        updateboard();
    }    
}

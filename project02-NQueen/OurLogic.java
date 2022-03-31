import net.sf.javabdd.*;

public class OurLogic implements IQueensLogic{
    private int size;		// Size of quadratic game board (i.e. size = #rows = #columns)
    private int[][] board;	// Content of the board. Possible values: 0 (empty), 1 (queen), -1 (no queen allowed)
    
    private BDDFactory fac;
    private BDD yes;
    private BDD no;
    private BDD bdd;

    public void initializeBoard(int size) {
        this.size = size;
        this.board = new int[size][size];
        buildBDD();
        updateboard();
    }
   
    public int[][] getBoard() {
        return board;
    }

    public void buildBDD(){
        this.fac = JFactory.init(2000000, 200000);
        this.fac.setVarNum(this.size*this.size); 
        this.no = this.fac.zero();
        this.yes = this.fac.one();
        this.bdd = yes;

        for(int x = 0; x < size; x++){
            for(int y = 0; y < size; y++){
                eval(y,x);
            }
        }
        eightRule();
    }

    private void eightRule(){
        for(int x = 0; x < size; x++){
            BDD subBdd = no;
            for(int y = 0; y < size; y++){
                subBdd = subBdd.or(this.fac.ithVar(place(y, x)));
            }
            this.bdd = this.bdd.and(subBdd);
        }
    }


    

    private void eval(int y, int x){
        BDD unavailableBDD = no;
        BDD availableBDD = yes;      
        
        //all y = false
        for(int y2 = 0; y2 < size; y2++){
            if (y != y2) availableBDD = availableBDD.and(this.fac.nithVar(place(y2,x)));
        }

        //all x = false
        for(int x2 = 0; x2 < size; x2++){
            if (x != x2) availableBDD = availableBDD.and(this.fac.nithVar(place(y,x2)));
        }

        unavailableBDD = unavailableBDD.or(this.fac.nithVar(place(x,y)));
        unavailableBDD = unavailableBDD.or(availableBDD);

        this.bdd = this.bdd.and(unavailableBDD);

    }
    

    private int place(int y,int x) {
      return x*this.size+y;
    }

    private boolean isInvalid(int y, int x){
        BDD testBDD = this.bdd.restrict(this.fac.ithVar(place(y,x)));
        return testBDD.isZero();
    }
    
    private void updateboard(){
        for(int i = 0; i < size; i++){
            for(int j = 0; i < size; j++){
                if(isInvalid(j, i)){
                    board[j][i] = -1;
                }
            }
        }
    }

    public void insertQueen(int column, int row) {
        board[column][row] = 1;
        updateboard();
    }    
    
}

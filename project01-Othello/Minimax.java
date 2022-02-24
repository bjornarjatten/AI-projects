

public class Minimax implements IOthelloAI {

    public static void name(String args[]) {
    
    }

    public Position decideMove(GameState state){
        return new Position(0, 0);
    }

    public void minimaxSearch(GameState s){
        /*player←game.TO-MOVE(state)
        value, move←MAX-VALUE(game, state)
        return move
        */
    }

    public Pair maxValue(GameState state){
        if(state.isFinished() == false){
            return new Pair (2, null);
        }

        for (var a : state.legalMoves()){
            GameState result = minValue(state, a);

        }
        
    }

    public Pair minValue(GameState state){
        return state;
    }
}



public class Minimax implements IOthelloAI {
// int to state what side is using the minimax
private int player;
// the limit to what depth the ai is allowed to look forward
private int depth = 5;

    /**sets what player is playing with minimax,
     * then defaults to the first legal move being the best until anouther move is found
     * initializez the start depth to be 0
     * then begins looking for the best move by calling maxvalue
    */
    public Position decideMove(GameState s){
        player = s.getPlayerInTurn();
        Position pos = s.legalMoves().get(0);
        int step = 0;
        Position movePosition = maxValue(s, pos, Integer.MIN_VALUE, Integer.MAX_VALUE, step).pos;
        return movePosition;
    }

    /** Mimics the pseudocode from the book
     * creates a new pair based on the current move
     * then checks if we have reached our cutoff or the game is finished, if so returns the current move
     * otherwise we go trough the list of legal moves
     * calling minvalue on each move and procceding through the game, returning the best score at when a cutoff is reached
     * this score is then compared to the current moves score and the best move is selected as the new current move
     * 
     */
    public Pair maxValue(GameState s, Position pos, int alpha, int beta, int step){
        Pair curr  = new Pair(findScore(s), pos);
        if(s.isFinished()|| step >= depth) return curr;
        for (Position p : s.legalMoves()){
            Pair v2a2 = minValue(result(s, p), curr.pos, alpha, beta, step+1);
            if (v2a2.score > curr.score){
                curr = new Pair(v2a2.score, p);
            }
            if (curr.score >= beta){
                return curr;
            }
            
            alpha = Math.max(alpha, curr.score);
        } 
        return curr;
    }

    public Pair minValue(GameState s, Position pos, int alpha, int beta, int step){
        Pair curr  = new Pair(findScore(s), pos);
        if(s.isFinished()|| step >= depth) return curr;
        for (Position p : s.legalMoves()){
            Pair v2a2 = maxValue(result(s, p), curr.pos, alpha, beta, step+1);
            if (v2a2.score < curr.score){
                curr = new Pair(v2a2.score, p);
            }
            if (curr.score <= alpha){
                return curr;
            }
    
            beta = Math.max(beta, curr.score);
        } 
        return curr;
    }


    //find the socre of the current game state
    private int findScore(GameState s){
        int[] Scores = s.countTokens();
        if(player == 1) return Scores[0] - Scores[1];
        else return Scores[1] - Scores[0];
    }

    private GameState result(GameState s, Position p){
        GameState t = new GameState(s.getBoard(),s.getPlayerInTurn());
        t.insertToken(p);
        return t;
    }
    
}

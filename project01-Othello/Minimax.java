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
        int step = 0;
        Position temp =  s.legalMoves().get(0);
        System.out.println(temp);
        Pair movePosition = maxValue(s, Integer.MIN_VALUE, Integer.MAX_VALUE, step);
        return movePosition.pos;
    }

    /** Mimics the pseudocode from the book
     * creates a new pair based on the current move
     * then checks if we have reached our cutoff or the game is finished, if so returns the current move
     * otherwise we go trough the list of legal moves
     * calling minvalue on each move and procceding through the game, returning the best score at when a cutoff is reached
     * this score is then compared to the current moves score and the best move is selected as the new current move
     */
    public Pair maxValue(GameState s, int alpha, int beta, int step){
        if(s.isFinished()|| step >= depth) return new Pair(Integer.MIN_VALUE, null);
        Pair curPair = new Pair(Integer.MIN_VALUE, null);
        for(Position a : s.legalMoves()){
            Pair v2a2 = minValue( result(s, a), alpha, beta, step+1);
            if(v2a2.score > findScore(s) ){
                System.out.println("found better option");
                curPair = new Pair(v2a2.score, a);
                alpha = Math.max(alpha, curPair.score);
            }
            if (curPair.score >= beta) return curPair;
        }
        return curPair;
    }

    public Pair minValue(GameState s, int alpha, int beta, int step){
        if(s.isFinished()|| step >= depth) return new Pair(Integer.MAX_VALUE, null);
        Pair curPair = new Pair(Integer.MAX_VALUE, null);
        for(Position a : s.legalMoves()){
            Pair v2a2 = maxValue( result(s, a), alpha, beta, step+1);
            if(v2a2.score < findScore(s) ){
                curPair = new Pair(v2a2.score, a);
                beta = Math.min(beta, curPair.score);
            }
            if (curPair.score <= alpha) return curPair;
        }
        return curPair;
    }


    //find the socre of the current game state
    private int findScore(GameState s){
        int[] Scores = s.countTokens();
        if(player == 1) return Scores[0];
        else return Scores[1];
    }

    private GameState result(GameState s, Position p){
        GameState t = new GameState(s.getBoard(),s.getPlayerInTurn());
        t.insertToken(p);
        return t;
    }
    
}

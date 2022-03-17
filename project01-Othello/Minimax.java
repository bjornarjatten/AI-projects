public class Minimax implements IOthelloAI {

    // int to state what side is using the minimax
private int player;

// the limit to what depth the ai is allowed to look forward
private int depth = 7; // <= 7 can be played at 8 but will have loading times upwards the 10 sec limit

    /**sets what player is playing with minimax,
     * then defaults to the first legal move being the best until anouther move is found
     * initializez the start depth to be 0
     * then begins looking for the best move by calling maxvalue
    */
    public Position decideMove(GameState s){
        player = s.getPlayerInTurn();
        if(!s.legalMoves().isEmpty()){
            Position temp = s.legalMoves().get(0);
            return maxValue(s, temp, 0, Integer.MIN_VALUE, Integer.MAX_VALUE).pos; 
        } return new Position(-1, -1);
    }


    /** Mimics the pseudocode from the book
     * checks if we have reached our cutoff or the game is finished, if so returns the current best move
     * we assign the int v to the lowest integer
     * otherwise we go trough the list of legal moves calling minvalue and saving its score
     * the score is then compared with v, wich is the current highest score
     * if the minvalue score is higher its saved at the new v and its position is saved as pos
     * lastly we set our new alpha value for future pruning 
     * and then do the pruning by checking if v is bigger than beta 
     */
    public Pair maxValue(GameState s, Position pos, int step, int alpha, int beta){
        if (s.isFinished()|| step >= depth) return new Pair(findScore(s), pos);
        int v = Integer.MIN_VALUE;
        for(Position p : s.legalMoves()){
            Pair v2a2 = minValue(result(s, p), p, step+1, alpha, beta);
            if (v2a2.score > v){
                v = v2a2.score;
                pos = p;
                alpha = Math.max(alpha, v);
            }
            if (v >= beta) return new Pair(v, pos);
        }
        return new Pair(v, pos);
    }

    /** Same as above, but checking for the lovest value 
     *  Setting a new beta and checking against alpha
     */
    public Pair minValue(GameState s, Position pos, int step, int alpha, int beta){
        if (s.isFinished()|| step >= depth) return new Pair(findScore(s), pos);
        int v = Integer.MAX_VALUE;
        for(Position p : s.legalMoves()){
            Pair v2a2 = maxValue(result(s, p), p, step+1, alpha, beta);
            if (v2a2.score < v){
                v = v2a2.score;
                pos = p;
                beta = Math.min(beta, v);
            }
            if (v <= alpha) return new Pair(v, pos);
        }
        return new Pair(v, pos);
    }

    /** Finds the current "score" of the game 
     *  by checking the diffrence in tiles betwen the players
     *  returning the diffrence in relation to what player is currenlty
     *  using our minimax algorithem
     */
    private int findScore(GameState s){
        int[] Scores = s.countTokens();
        if(player == 1) return Scores[0]-Scores[1];
        else return Scores[1]-Scores[0];
    }

    /** Provides a gamestate 
     * as result of inserting a token at
     * a given place 
     */
    private GameState result(GameState s, Position p){
        GameState t = new GameState(s.getBoard(),s.getPlayerInTurn());
        t.insertToken(p);
        return t;
    }
    
}

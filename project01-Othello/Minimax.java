public class Minimax implements IOthelloAI {
// int to state what side is using the minimax
private int player;
// the limit to what depth the ai is allowed to look forward
private int depth = 5; // >=5 seems optimal, 6 works but some long load times, 7 is slow AF

    public Position decideMove(GameState s){
        player = s.getPlayerInTurn();
        if(!s.legalMoves().isEmpty()){
            Position temp = s.legalMoves().get(0);
            return maxValue(s, temp, 0, Integer.MIN_VALUE, Integer.MAX_VALUE).pos; 
        } return new Position(-1, -1);
    }

    public Pair maxValue(GameState s, Position pos, int step, int alpha, int beta){
        if (s.isFinished()|| step >= depth) return new Pair(findScore(s), null);
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

    public Pair minValue(GameState s, Position pos, int step, int alpha, int beta){
        if (s.isFinished()|| step >= depth) return new Pair(findScore(s), null);
        int v = Integer.MAX_VALUE;
        for(Position p : s.legalMoves()){
            Pair v2a2 = minValue(result(s, p), p, step+1, alpha, beta);
            if (v2a2.score < v){
                v = v2a2.score;
                pos = p;
                beta = Math.min(beta, v);
            }
            if (v <= alpha) return new Pair(v, pos);
        }
        return new Pair(v, pos);
    }

    
    private int findScore(GameState s){
        int[] Scores = s.countTokens();
        if(player == 1) return Scores[0]-Scores[1];
        else return Scores[1]-Scores[0];
    }

    private GameState result(GameState s, Position p){
        GameState t = new GameState(s.getBoard(),s.getPlayerInTurn());
        t.insertToken(p);
        return t;
    }
    
}

public class MinimaxV2 implements IOthelloAI {
    private int player;
    private final int depth = 3;

    public Position decideMove(GameState s){
        player = s.getPlayerInTurn();
        if (!s.legalMoves().isEmpty()) return minimaxSearch(s);
            else return new Position(-1,-1);
    }
    
    
    public Position minimaxSearch(GameState s){
        Position bestMove = null;
        int bestValue = Integer.MIN_VALUE;
        for(Position p : s.legalMoves()){
            int temp = minValue(result(s, p), Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
            if (temp >= bestValue){
                bestMove = p;
                bestValue = temp;
            } 
        }
        return bestMove;
    }

    public int maxValue(GameState s, int alpha, int beta, int step){
        if (s.isFinished()|| step == depth) return findScore(s);
        int v = Integer.MIN_VALUE;
        for(Position p : s.legalMoves()){
            v = Math.max(v, minValue(result(s, p), alpha, beta, step));
            if (v >= beta) return v;
            alpha = Math.max(alpha, v);
        }
        return v;
    }

    public int minValue(GameState s, int alpha, int beta, int step){
        if (s.isFinished()|| step == depth) return findScore(s);
        int v = Integer.MAX_VALUE;
        for(Position p : s.legalMoves()){
            v = Math.min(v, maxValue(result(s, p), alpha, beta, step+1));
            if (v <= alpha) return v;
            beta = Math.min(beta, v);
        }
        return v;
    }

    //find the socre of the current game state
    private int findScore(GameState s){
        int[] Scores = s.countTokens();
        if(player == 1){
            return Scores[0]-Scores[1];
        }else return Scores[1]-Scores[0];
    }

    private GameState result(GameState s, Position p){
        GameState t = new GameState(s.getBoard(), s.getPlayerInTurn());
        t.insertToken(p);
        return t;
    }


}
    

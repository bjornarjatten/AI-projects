public class MinimaxV2 implements IOthelloAI {
    //cant currently go first
    private int player;

    public Position decideMove(GameState s){
        player = s.getPlayerInTurn();
        if (!s.legalMoves().isEmpty()) return minimaxSearch(s);
        else return new Position(-1,-1);
    }
    
    
    public Position minimaxSearch(GameState s){
        Position bestMove = new Position(-1,-1);;
        int bestValue = -9999;
        for(Position p : s.legalMoves()){
            int temp = minValue(result(s, p));
            if (temp >= bestValue){
                bestMove = p;
                bestValue = temp;
            } 
        }
        return bestMove;
    }

    public int maxValue(GameState s){
        if (s.isFinished()) return findScore(s);
        int v = -99999;
        for(Position p : s.legalMoves()){
            int minScore = minValue(result(s, p));
            if(v < minScore) v = minScore;
        }
        return v;
    }

    public int minValue(GameState s){
        if (s.isFinished()) return findScore(s);
        int v = 99999;
        for(Position p : s.legalMoves()){
            int maxScore = maxValue(result(s, p));
            if(v > maxScore) v = maxScore;
        }
        return v;
    }

    //find the socre of the current game state
    private int findScore(GameState s){
        int[] Scores = s.countTokens();
        if(player == 1) return Scores[0] - Scores[1];
        else return Scores[1] - Scores[0];
    }

    private GameState result(GameState s, Position p){
        GameState t = new GameState(s.getBoard(), s.getPlayerInTurn());
        t.insertToken(p);
        return t;
    }


}
    

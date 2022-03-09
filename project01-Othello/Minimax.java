

public class Minimax implements IOthelloAI {
private int player;

    public Position decideMove(GameState s){
        player = s.getPlayerInTurn();
        Position movePosition = maxValue(s, null).pos;
        return movePosition;
    }

    public Pair maxValue(GameState s, Position pos){
        if(s.isFinished()) return new Pair(findScore(s), pos);
        Pair curr  = new Pair(findScore(s), pos);
        for (Position p : s.legalMoves()){
            Pair v2a2 = minValue(result(s, p), curr.pos);
            if (v2a2.score > curr.score){
                curr = new Pair(v2a2.score, p);
            }
        } 
        return curr;
    }

    public Pair minValue(GameState s, Position pos){
        if(s.isFinished()) return new Pair(findScore(s), pos);
        Pair curr  = new Pair(findScore(s), pos);
        for (Position p : s.legalMoves()){
            Pair v2a2 = maxValue(result(s, p), curr.pos);
            if (v2a2.score < curr.score){
                curr = new Pair(v2a2.score, p);
            }
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

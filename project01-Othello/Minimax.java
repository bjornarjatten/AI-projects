
public class Minimax implements IOthelloAI {

    // public Position decideMove(GameState s){
	// 	ArrayList<Position> moves = s.legalMoves();

	// 	if ( !moves.isEmpty() )
	// 		return moves.get(0);
	// 	else
	// 		return new Position(-1,-1);
	// }

    public Position decideMove(GameState s){
        //Position firstLegal = s.legalMoves().get(0);
        Position movePosition = minimaxSearch(s);
        s.insertToken(movePosition);
        return movePosition;
    }

    public Position minimaxSearch(GameState s){
        System.out.println("minimax SEARCH");
        Pair utilityAndPos = maxValue(s);
        System.out.println("found move with utility: " + utilityAndPos.utility +  ", and pos: " + utilityAndPos.pos);
        if (utilityAndPos.pos == null) return new Position(-1,-1);
        else return utilityAndPos.pos;
    }

    public Pair maxValue(GameState s){
        System.out.println("max SEARCH");
        

        if(s.isFinished() == true) return new Pair(getResult(s), new Position(-1, -1));

        Pair curr = new Pair(0, new Position(-1, -1));
        curr.utility = Double.NEGATIVE_INFINITY;
        curr.utility = findUtilityScore(s, curr.pos);
        for (var a : s.legalMoves()){
            Pair min = minValue(s);
            
            if(min.utility > curr.utility){
                curr = min; 
            }
        }
        
        return curr;
    }

    public Pair minValue(GameState s){
        System.out.println("min SEARCH");
        
        if(s.isFinished() == true) return new Pair(getResult(s), new Position(-1, -1));

        Pair curr = new Pair(0, new Position(-1, -1));
        curr.utility = Double.POSITIVE_INFINITY;
        curr.utility = findUtilityScore(s, curr.pos);
        for (var a : s.legalMoves()){
            Pair max = maxValue(s);
            if(max.utility < curr.utility){
                curr = max; 
            }
        }
        
        return curr;
    }

    public double findUtilityScore(GameState s, Position p ){
        System.out.println("find utility SCORE");
        s.insertToken(p);
        int[] temp = s.countTokens();
        int currScore = temp[0]-temp[1];
        return currScore;
    }

    public int getResult(GameState s){
        int[] player = s.countTokens();
        int score = player[0]-player[1];
        return score;
    }
    
}

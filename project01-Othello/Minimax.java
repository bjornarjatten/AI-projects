

public class Minimax implements IOthelloAI {

    public Position decideMove(GameState s){
        Position firstLegal = s.legalMoves().get(0);
        Position movePosition = minimaxSearch(s);
        System.out.println("Trying this move: "+ movePosition);
        System.out.println("this is first move in legal: " + firstLegal);
        System.out.println(s.legalMoves().contains(movePosition));
        return movePosition;
    }

    public Position minimaxSearch(GameState s){
        Pair doMove = maxValue(s);
        System.out.println("found this move: "+ doMove.pos);
        if (doMove.pos == null) return new Position(-1,-1);
        else return doMove.pos;
    }

    public Pair maxValue(GameState s){
        Pair curr = new Pair(0, null);

        if(s.isFinished() == true) return curr;

        for (var a : s.legalMoves()){
            curr = new Pair(findCapScore(s, a), a);
            s.insertToken(a);
            Pair min = minValue(s);
            if(min.score > curr.score){
                curr = min; 
            }
        }
        return curr;
    }

    public Pair minValue(GameState s){
        Pair curr = new Pair(0, null);
        if(s.isFinished() == true) return curr;

        for (var a : s.legalMoves()){
            curr = new Pair(findCapScore(s, a), a);
            s.insertToken(a);
            Pair max = maxValue(s);
            if(max.score < curr.score){
                curr = max; 
            }
        }
        return curr;
    }

    //finde score based on a move, in relation to the amount of tokens each player would have
    public int findCapScore(GameState s, Position p ){
        int turn = s.getPlayerInTurn()-1;
        s.insertToken(p);
        int[] temp = s.countTokens();
        //should maybe be the diffrence between the two players, and not just the respective players score as here ?
        int currScore = temp[turn];
        return currScore;
    }
    
}

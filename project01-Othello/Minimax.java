

public class Minimax implements IOthelloAI {

    public Position decideMove(GameState s){
        Position movePosition = minimaxSearch(s);
        System.out.println("Trying this move:");
        System.out.println(movePosition);
        return movePosition;
    }

    public Position minimaxSearch(GameState s){
        Pair doMove = maxValue(s);
        System.out.println("found this move: "+doMove.pos);
        if (doMove.pos == null) return new Position(-1,-1);
        else return doMove.pos;
    }

    public Pair maxValue(GameState s){
        Pair curr = new Pair(0, null);

        if(s.isFinished() == true)return curr;

        for (var a : s.legalMoves()){
            curr = new Pair(findCapScore(s, a), a); //im not sure about this
            Pair min = minValue(s);
            if(min.score > curr.score){ //im not sure about this
                curr = min; //im not sure about this
            }
        }
        return curr;
    }

    public Pair minValue(GameState s){
        Pair curr = new Pair(0, null);
        if(s.isFinished() == true){
            return curr;
        }
        for (var a : s.legalMoves()){
            curr = new Pair(findCapScore(s, a), a);//im not sure about this
            Pair max = maxValue(s);
            if(max.score < curr.score){ //im not sure about this
                curr = max; //im not sure about this
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

import java.util.ArrayList;

public class Minimax implements IOthelloAI {

    public int player;

    public Position decideMove(GameState s){
        return minimaxSearch(s);
    }

    public Position minimaxSearch(GameState s){
        player = s.getPlayerInTurn();
        Pair valueMove = maxValue(s, null);

        return valueMove.move;
    }

    public Pair maxValue(GameState s, Position pos){
        if(s.isFinished()){
            return new Pair(s.countTokens()[player], pos);
        }

        Pair vMove = new Pair(Integer.MIN_VALUE, null);

        for(Position p: s.legalMoves()){
            s.insertToken(p);
            Pair vMove2 = minValue(s, p);
            
            if(vMove2.utility > vMove.utility){
                vMove = new Pair(vMove2.utility, vMove.move);
            }
        }

        if(vMove.move != null){
            System.out.println(vMove.move.toString());
        }

        return vMove;
    }

    public Pair minValue(GameState s, Position pos){
        if(s.isFinished()){
            return new Pair(s.countTokens()[player], pos);
        }

        Pair vMove = new Pair(Integer.MAX_VALUE, null);

        for(Position p: s.legalMoves()){
            s.insertToken(p);
            Pair vMove2 = maxValue(s, p);

            if(vMove2.utility < vMove.utility){
                vMove = new Pair(vMove2.utility, vMove.move);
            }
        }

        if(vMove.move != null){
            System.out.println(vMove.move.toString());
        }

        return vMove;
    }

/*     public int getValue(GameState s, Position p){
        int possibleScore = 0;

        possibleScore =+ s.captureInDirection(p, 1, 0);
        possibleScore =+ s.captureInDirection(p, -1, 0);
        possibleScore =+ s.captureInDirection(p, 0, 1);
        possibleScore =+ s.captureInDirection(p, 0, -1);
        possibleScore =+ s.captureInDirection(p, 1, 1);
        possibleScore =+ s.captureInDirection(p, -1, -1);
        possibleScore =+ s.captureInDirection(p, 1, -1);
        possibleScore =+ s.captureInDirection(p, -1, 1);
        possibleScore =+ s.captureInDirection(p, 0, 0);

        return possibleScore;
    }

    public Pair getLowestLegalMove(GameState s){
        Pair lowestScorePosition;

        if(s.legalMoves().size() == 0){
            lowestScorePosition = new Pair(0, null);
        }else{
            lowestScorePosition = new Pair(getValue(s, s.legalMoves().get(0)), s.legalMoves().get(0));
        }

        for(Position p: s.legalMoves()){
            if(getValue(s, p) < lowestScorePosition.utility){
                lowestScorePosition = new Pair(getValue(s, p), p);
            }
        }

        return lowestScorePosition;
    }

    public Pair getGreatestLegalMove(GameState s){
        Pair greatestScorePosition;

        if(s.legalMoves().size() == 0){
            greatestScorePosition = new Pair(0, null);
        }else{
            greatestScorePosition = new Pair(getValue(s, s.legalMoves().get(0)), s.legalMoves().get(0));
        }

        for(Position p: s.legalMoves()){
            if(getValue(s, p) > greatestScorePosition.utility){
                greatestScorePosition = new Pair(getValue(s, p), p);
            }
        }

        return greatestScorePosition;
    }
 */
     
/*     public Position decideMove(GameState s){
        Position firstLegal = s.legalMoves().get(0); */
/*         Position movePosition = minimaxSearch(s);
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
    } */
    
}

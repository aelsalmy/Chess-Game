package ChessCore;

import ChessCore.Pieces.Piece;
import java.util.ArrayList;

public class PlayerBuilder {
    
    public Player build(){
        return new Player();
    }
    public Player copy(Player src){
        Player res = new Player();
        res.playerPieces = new ArrayList<Piece>(src.playerPieces);
        res.setPlayerColor(src.getPlayerColor());
        
        return res;
    }
}

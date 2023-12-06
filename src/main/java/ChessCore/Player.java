package ChessCore;

import ChessCore.Pieces.Piece;
import ChessCore.enums.Colors;
import java.util.ArrayList;

public class Player {
    
    public ArrayList <Piece> playerPieces = new ArrayList();
    private Colors playerColor;
    
    //Setters and Getters
    public Colors getPlayerColor() {
        return playerColor;
    }
    public void setPlayerColor(Colors playerColor) {
        this.playerColor = playerColor;
    }
    
}

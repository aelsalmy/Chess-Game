package ChessCore.Controllers;

import ChessCore.ChessGame;
import ChessCore.Move;
import ChessCore.Pieces.Piece;
import ChessCore.exceptions.NoPieceFoundException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import ChessCore.Subject;

public class StaleMateController implements Subject{
    
    private ChessGame game;
    
    public StaleMateController(ChessGame g){
        this.game = g;
    }
    
    @Override
    public void update(){
        if(game.gameInProgress)
            game.gameInProgress = !this.isStaleMate();
    }
    
    public boolean isStaleMate(){
        CheckMateController check = new CheckMateController(game);
        try{
            if(check.kingIsInCheck(game.turn.getPlayerColor()))
                return false;
            else{
                for(Piece p : game.turn.playerPieces){
                    ArrayList<Move> moves = game.getAllValidMovesFromSquare(p.getCurrentPosition());
                    if(!moves.isEmpty()){
                        return false;
                    }   
                }
                JOptionPane.showMessageDialog(null , "StaleMate !!");
                return true;
            }
        }
        catch(NoPieceFoundException e){
            System.out.println("ERROR");
            return false;
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ChessCore.Controllers;

import ChessCore.ChessGame;
import ChessCore.Pieces.Bishop;
import ChessCore.Pieces.Knight;
import ChessCore.Pieces.Queen;
import ChessCore.Pieces.Rook;
import ChessCore.Spot;
import ChessCore.enums.Colors;
import ChessCore.exceptions.NoPieceFoundException;
import javax.swing.JOptionPane;

/**
 *
 * @author abdul
 */
public class PromotionController {
    ChessGame game;
    
    public PromotionController(ChessGame g){
        this.game = g;
    }
    
    public void promotePawn(Spot pawnLoc) throws NoPieceFoundException{
        Colors currColor = pawnLoc.getCurrentPiece().getPieceColor();
        
        String[] options = {"Queen" , "Bishop" , "Rook" , "Knight"}; 
        int selected = JOptionPane.showOptionDialog(null, "Select Piece Wanted", "Promotion", JOptionPane.DEFAULT_OPTION , JOptionPane.INFORMATION_MESSAGE , null , options, options[0]);
        
        //Remove Pawn From player Pieces
        if(currColor == Colors.White)
            game.playerWhite.playerPieces.remove(pawnLoc.getCurrentPiece());
        else if(currColor == Colors.Black)
            game.playerBlack.playerPieces.remove(pawnLoc.getCurrentPiece());
        
        switch(selected){
            case 0 -> pawnLoc.setCurrentPiece(new Queen(currColor , pawnLoc , game.mainBoard));
            case 1 -> pawnLoc.setCurrentPiece(new Bishop(currColor , pawnLoc , game.mainBoard));
            case 2 -> pawnLoc.setCurrentPiece(new Rook(currColor , pawnLoc , game.mainBoard));
            case 3 -> pawnLoc.setCurrentPiece(new Knight(currColor , pawnLoc , game.mainBoard));
        }
        
        
        
        //add new piece to player pieces
        if(currColor == Colors.White)
            game.playerWhite.playerPieces.add(pawnLoc.getCurrentPiece());
        else if(currColor == Colors.Black)
            game.playerBlack.playerPieces.add(pawnLoc.getCurrentPiece());
            
        System.out.println(currColor + " Promoted to " + pawnLoc.getCurrentPiece().getType());    
    }
}

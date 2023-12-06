/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ChessCore.Controllers;

import ChessCore.ChessGame;
import ChessCore.Pieces.Piece;
import ChessCore.Spot;
import ChessCore.enums.Colors;
import ChessCore.exceptions.NoPieceFoundException;

/**
 *
 * @author abdul
 */
public class EnPassantController{
    
    ChessGame game;
    
    public EnPassantController(ChessGame g){
        this.game = g;
    }
    
    public void captureEnPassant(Spot loctoCapture) throws NoPieceFoundException {
        
        Piece p = loctoCapture.getCurrentPiece();
        
        p.setIsCaptured(true);
        
        
        
        if (p.getPieceColor() == Colors.White) {
            game.playerWhite.playerPieces.remove(p);
            

        } else {
            game.playerBlack.playerPieces.remove(p);
        }
        
        
        loctoCapture.setCurrentPiece(null);
        loctoCapture.setIsOcuppied(false);

    }
}

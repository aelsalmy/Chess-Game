/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ChessCore.Controllers;

import ChessCore.ChessGame;
import ChessCore.MoveObservor;
import ChessCore.Observor;
import ChessCore.Pieces.Piece;
import ChessCore.enums.PieceTypes;
import javax.swing.JOptionPane;

/**
 *
 * @author abdul
 */
public class InSuffMaterialController implements Observor{
    
    ChessGame game;
    
    public InSuffMaterialController(ChessGame g){
        this.game = g;
    }
    
    @Override
    public void update(){
        if(game.gameInProgress){
            game.gameInProgress = !this.insuffMaterial();
            if(!game.gameInProgress)
                JOptionPane.showMessageDialog( null , "InSufficient Material to End Game !!");
        }
    }
    
    //check if there is insuff pieces to continue game
    public boolean insuffMaterial(){
        //case of two lone kings
        if(game.playerWhite.playerPieces.size() == 1 && game.playerBlack.playerPieces.size() == 1)
            return true;
        //case white has bishop or knight with king and black has king only
        else if(game.playerWhite.playerPieces.size() == 2 && game.playerBlack.playerPieces.size() == 1){
            for(Piece p : game.playerWhite.playerPieces)
                if(p.getType() != PieceTypes.King && p.getType() != PieceTypes.Bishop && p.getType() != PieceTypes.Knight)
                    return false;
            return true;
        }
        //case black has bishop or knight with king and white has king only
        else if(game.playerBlack.playerPieces.size() == 2 && game.playerWhite.playerPieces.size() == 1){
            for(Piece p : game.playerBlack.playerPieces)
                if(p.getType() != PieceTypes.King && p.getType() != PieceTypes.Bishop && p.getType() != PieceTypes.Knight)
                    return false;
            return true;
        }
        //case both players have king with bishop or knight
        else if(game.playerWhite.playerPieces.size() == 2 && game.playerBlack.playerPieces.size() == 2){
            for(Piece p : game.playerWhite.playerPieces)
                if(p.getType() != PieceTypes.King && p.getType() != PieceTypes.Bishop && p.getType() != PieceTypes.Knight)
                    return false;
            for(Piece p : game.playerBlack.playerPieces)
                if(p.getType() != PieceTypes.King && p.getType() != PieceTypes.Bishop && p.getType() != PieceTypes.Knight)
                    return false;
            return true;
        }
        //case white has 2 knights and king and black has king
        else if(game.playerWhite.playerPieces.size() == 3 && game.playerBlack.playerPieces.size() == 1){
            for(Piece p : game.playerWhite.playerPieces)
                if(p.getType() != PieceTypes.King && p.getType() != PieceTypes.Knight)
                    return false;
            return true;
        }
        //case black has 2 knights and king and white has king
        else if(game.playerBlack.playerPieces.size() == 3 && game.playerWhite.playerPieces.size() == 1){
                for(Piece p : game.playerBlack.playerPieces)
                if(p.getType() != PieceTypes.King && p.getType() != PieceTypes.Knight)
                    return false;
            return true;
        }
        
        return false;   
    }
}

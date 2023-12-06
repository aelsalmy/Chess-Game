/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ChessCore.UndoOperation;

import ChessCore.CaptureMove;
import ChessCore.ChessGame;
import ChessCore.Move;
import ChessCore.Pieces.Piece;
import ChessCore.enums.Colors;
import ChessCore.exceptions.NoPieceFoundException;

/**
 *
 * @author abdul
 */
public class PromotionUndoCommand implements Command {
    
    private Move mv;
    private ChessGame game;
    private Piece piece;
    
    public PromotionUndoCommand(Move m , ChessGame g , Piece p){
        this.mv = m;
        this.game = g;
        this.piece = p;    
    }
    
    @Override
    public void execute() throws NoPieceFoundException {
        piece.setCurrentPosition(mv.getStartPosition());
            
            if(!game.gameInProgress)
                game.gameInProgress = true;
        
            if(game.turn.getPlayerColor() == Colors.Black){
                game.playerWhite.playerPieces.remove(mv.getFinalPosition().getCurrentPiece());
                game.playerWhite.playerPieces.add(piece);
            }
            else{
                game.playerBlack.playerPieces.remove(mv.getFinalPosition().getCurrentPiece());
                game.playerBlack.playerPieces.add(piece);
            }
            
            mv.getFinalPosition().setIsOcuppied(false);
            mv.getStartPosition().setIsOcuppied(true);
            mv.getFinalPosition().setCurrentPiece(null);
            mv.getStartPosition().setCurrentPiece(piece);
                        
            //return capture if available
            if(mv.getHasCapture()){
                CaptureMove.CaptureMoveMemento cap = game.undoController.getCaptureMove();
                CaptureMove c = CaptureMove.extractCaptureMove(cap);
                            
                c.undoCaptureMove();
            }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ChessCore.UndoOperation;

import ChessCore.ChessGame;
import ChessCore.Move;
import ChessCore.Pieces.Piece;
import ChessCore.enums.Colors;
import ChessCore.enums.PieceTypes;
import ChessCore.exceptions.NoPieceFoundException;

/**
 *
 * @author abdul
 */
public class CastleUndoCommand implements Command{
    
    private Move mv;
    private ChessGame game;
    private Piece piece;
    
    public CastleUndoCommand(Move m , ChessGame g , Piece p){
        this.mv = m;
        this.game = g;
        this.piece = p;    
    }
    
    @Override
    public void execute() throws NoPieceFoundException {
        
        piece.setCurrentPosition(mv.getStartPosition());
        //return king to Original Position
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
            
        //return Rook to Original Position
        int row = piece.getCurrentPosition().getRow();
        int col = piece.getCurrentPosition().getColumn();
          
        if(game.mainBoard.board[row][col + 1].getCurrentPiece().getType() == PieceTypes.Rook){
            System.out.println("INSIDE");
            Piece rook = game.mainBoard.board[row][col + 1].getCurrentPiece();
            rook.setCurrentPosition(game.mainBoard.board[row][7]);
            game.mainBoard.board[row][7].setIsOcuppied(true);
            game.mainBoard.board[row][col + 1].setIsOcuppied(false);
            game.mainBoard.board[row][7].setCurrentPiece(rook);
            game.mainBoard.board[row][col + 1].setCurrentPiece(null);
        }
        else{
            if(game.mainBoard.board[row][col - 1].getCurrentPiece().getType() == PieceTypes.Rook){
                   
            Piece rook = game.mainBoard.board[row][col - 1].getCurrentPiece();
                
            rook.setCurrentPosition(game.mainBoard.board[row][0]);
            game.mainBoard.board[row][0].setIsOcuppied(true);
            game.mainBoard.board[row][col - 1].setIsOcuppied(false);
            game.mainBoard.board[row][0].setCurrentPiece(rook);
            game.mainBoard.board[row][col - 1].setCurrentPiece(null);
                
            }
        }
            
    }
    
}

package ChessCore.Controllers;

import ChessCore.ChessGame;
import ChessCore.Move;
import ChessCore.Pieces.Piece;
import ChessCore.exceptions.NoPieceFoundException;

public class CastleController {
    ChessGame game;
    
    public CastleController(ChessGame g){
        this.game = g;
    }
    
    //castle king with rook
    public void castleKing(Move m) throws NoPieceFoundException{
        int startRow = m.getStartPosition().getRow();
        int startColumn = m.getStartPosition().getColumn();
        int finalColumn = m.getFinalPosition().getColumn();
        

            //castling with rook in far right
            if(finalColumn > startColumn){
                Piece p = game.mainBoard.board[startRow][7].getCurrentPiece();
                game.mainBoard.board[startRow][finalColumn - 1].setCurrentPiece(p);
                game.mainBoard.board[startRow][finalColumn - 1].setIsOcuppied(true);
                game.mainBoard.board[startRow][7].setCurrentPiece(null);
                game.mainBoard.board[startRow][7].setIsOcuppied(false);
            }
            //castling with rook in far left
            else if(finalColumn < startColumn){
                Piece p = game.mainBoard.board[startRow][0].getCurrentPiece();
                game.mainBoard.board[startRow][finalColumn + 1].setCurrentPiece(p);
                game.mainBoard.board[startRow][finalColumn + 1].setIsOcuppied(true);
                game.mainBoard.board[startRow][0].setCurrentPiece(null);
                game.mainBoard.board[startRow][0].setIsOcuppied(false); 
        } 
    }
    
    public boolean checkCastling(Move m)throws NoPieceFoundException{
        
        CheckMateController checkController = new CheckMateController(game);
        int finalColumn = m.getFinalPosition().getColumn();
        int startRow = m.getStartPosition().getRow();
        int startColumn = m.getStartPosition().getColumn();
        
        //case King has Moved Before
        if(m.getStartPosition().getCurrentPiece().getHasMoved())
            return false;
        //case King is in check
        else if(checkController.kingIsInCheck(m.getStartPosition().getCurrentPiece().getPieceColor()))
            return false;
        //case king jumps through checkmate while castling
        else{
            if(startColumn > finalColumn)
                return !checkController.spotIsInCheck(game.mainBoard.board[startRow][finalColumn + 1]);
            else if (startColumn < finalColumn){
                return !checkController.spotIsInCheck(game.mainBoard.board[startRow][finalColumn - 1]);
            }
        }
        return true;
    }
}

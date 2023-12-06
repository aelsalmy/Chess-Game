package ChessCore.Pieces;

import ChessCore.ChessBoard;
import ChessCore.Move;
import ChessCore.Spot;
import ChessCore.enums.Colors;
import ChessCore.enums.PieceTypes;
import ChessCore.exceptions.NoPieceFoundException;

public class King extends Piece{
    
    public King(Colors color , Spot pos , ChessBoard board){
        super(color , pos , board);
        this.setType(PieceTypes.King);
        if(this.getPieceColor() == Colors.White)
            this.setPieceImg("WhiteKing.png");
        else 
            this.setPieceImg("BlackKing.png");
    }
    
    @Override
    public boolean isValidMove(Move move) throws NoPieceFoundException{
        int startRow = move.getStartPosition().getRow();
        int startColumn = move.getStartPosition().getColumn();
        int finalRow = move.getFinalPosition().getRow();
        int finalColumn = move.getFinalPosition().getColumn();
        
        //check if final position has piece of same color
        if(move.getFinalPosition().getIsOcuppied()){
            if(move.getFinalPosition().getCurrentPiece().getPieceColor() == this.getPieceColor()){
                return false;
            }            
        }
        //castling attempt
        if(startRow == finalRow && Math.abs(finalColumn - startColumn) == 2 && !this.getHasMoved()){
            //castling with rook in far right
            if(finalColumn > startColumn){
                if(getBoard().board[startRow][7].getCurrentPiece().getType() == PieceTypes.Rook && !getBoard().board[startRow][7].getCurrentPiece().getHasMoved()){
                    for(int i = startColumn + 1 ; i < 7 ; i++)
                        if(getBoard().board[startRow][i].getIsOcuppied())
                            return false;
                    return true;
                }
            }
            //castling with rook in far left
            else if(finalColumn < startColumn){
                if(getBoard().board[startRow][0].getCurrentPiece().getType() == PieceTypes.Rook && !getBoard().board[startRow][0].getCurrentPiece().getHasMoved()){
                    for(int i = startColumn - 1 ; i > 0 ; i--)
                        if(getBoard().board[startRow][i].getIsOcuppied())
                            return false;
                    return true;
                }
            }
        }
        //king can move one block in any direction
        if(Math.abs(finalRow - startRow) * Math.abs(finalColumn - startColumn) <= 1 && Math.abs(finalRow - startRow) <= 1 && Math.abs(finalColumn - startColumn) <= 1)
            return true;  
        return false;
    }
    
}
    
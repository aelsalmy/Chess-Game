package ChessCore.Pieces;

import ChessCore.ChessBoard;
import ChessCore.Move;
import ChessCore.Spot;
import ChessCore.enums.Colors;
import ChessCore.enums.PieceTypes;
import ChessCore.exceptions.NoPieceFoundException;

public class Queen extends Piece{
    
    public Queen(Colors color , Spot pos , ChessBoard board){
        super(color , pos , board);
        this.setType(PieceTypes.Queen);
        if(this.getPieceColor() == Colors.White)
            this.setPieceImg("WhiteQueen.png");
        else 
            this.setPieceImg("BlackQueen.png");
    }
    
    @Override
    public boolean isValidMove(Move move) throws NoPieceFoundException{
        int startRow = move.getStartPosition().getRow();
        int startColumn = move.getStartPosition().getColumn();
        int finalRow = move.getFinalPosition().getRow();
        int finalColumn = move.getFinalPosition().getColumn();
        
        int columnDiff = finalColumn - startColumn;
        int rowDiff = finalRow - startRow;
        
        if(move.getFinalPosition().getIsOcuppied() && move.getFinalPosition().getCurrentPiece().getPieceColor() == this.getPieceColor()){
            return false;
        }
            
        
        //moves for Rook
        if(rowDiff == 0){
            if(finalColumn > startColumn){
                for(int i = startColumn + 1; i < finalColumn ; i++){
                    if(getBoard().board[startRow][i].getIsOcuppied())
                        return false;
                } 
                return true;
            }
            else{
                if(finalColumn < startColumn){
                    for(int i = startColumn - 1 ; i > finalColumn ; i--){
                        if(getBoard().board[startRow][i].getIsOcuppied()){ 
                            return false;
                        }
                    } 
                    return true;
                }
            }
        }
        else if(columnDiff == 0){
                if(finalRow > startRow){                     
                    for(int i = startRow + 1 ; i < finalRow ; i++){
                        if(getBoard().board[i][startColumn].getIsOcuppied())
                            return false;
                    }
                    return true;
                }
                else{
                    if(finalRow < startRow){
                        for(int i = startRow - 1 ; i > finalRow ; i--){
                            if(getBoard().board[i][startColumn].getIsOcuppied())
                                return false;
                        } 
                        return true;
                    }
                }
        }
        //moves of pawn
        if(Math.abs(rowDiff) == Math.abs(columnDiff)){
            if(rowDiff > 0 && columnDiff > 0 && rowDiff == columnDiff){
                for(int i = 1 ; i < rowDiff ; i++)
                    if(getBoard().board[startRow + i][startColumn + i].getIsOcuppied())
                        return false;
                return true; 
            }
            else if(rowDiff < 0 && columnDiff > 0 && Math.abs(rowDiff) == Math.abs(columnDiff)){
                for(int i = 1 ; i < columnDiff ; i++)
                    if(getBoard().board[startRow - i][startColumn + i].getIsOcuppied())
                        return false;
                return true;
            }
            else if(rowDiff > 0 && columnDiff < 0 && Math.abs(rowDiff) == Math.abs(columnDiff)){
                for(int i = 1 ; i < rowDiff ; i++)
                    if(getBoard().board[startRow + i][startColumn - i].getIsOcuppied())
                        return false;
                return true;
            }
            else if(rowDiff < 0 && columnDiff < 0 && Math.abs(rowDiff) == Math.abs(columnDiff)){
                for(int i = 1 ; i < Math.abs(columnDiff) ; i++)
                    if(getBoard().board[startRow - i][startColumn - i].getIsOcuppied())
                        return false;
                return true;
            }
        }
        return false;
    }
    
}

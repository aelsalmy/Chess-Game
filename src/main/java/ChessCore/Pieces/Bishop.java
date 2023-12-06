package ChessCore.Pieces;

import ChessCore.ChessBoard;
import ChessCore.Move;
import ChessCore.Spot;
import ChessCore.enums.Colors;
import ChessCore.enums.PieceTypes;
import ChessCore.exceptions.NoPieceFoundException;

public class Bishop extends Piece{
    
    public Bishop(Colors color , Spot pos , ChessBoard board){
        super(color , pos , board);
        this.setType(PieceTypes.Bishop);
        if(this.getPieceColor() == Colors.White)
            this.setPieceImg("WhiteBishop.png");
        else 
            this.setPieceImg("BlackBishop.png");
    }
    
    @Override
    public boolean isValidMove(Move move){
        int startRow = move.getStartPosition().getRow();
        int startColumn = move.getStartPosition().getColumn();
        int finalRow = move.getFinalPosition().getRow();
        int finalColumn = move.getFinalPosition().getColumn();
        
        int rowDiff = finalRow - startRow;
        int columnDiff = finalColumn - startColumn;
        
        try{
            if(move.getFinalPosition().getCurrentPiece().getPieceColor() == this.getPieceColor())
                return false;  
        }
        catch(NoPieceFoundException e){
        
        }
        //move down right
        if(rowDiff > 0 && columnDiff > 0 && rowDiff == columnDiff){
            //check if there is other pieces on the way
            for(int i = 1 ; i < rowDiff ; i++)
                if(getBoard().board[startRow + i][startColumn + i].getIsOcuppied())
                    return false;
            return true; 
        }
        //move up right
        else if(rowDiff < 0 && columnDiff > 0 && Math.abs(rowDiff) == Math.abs(columnDiff)){
            //check if there is other pieces on the way
            for(int i = 1 ; i < columnDiff ; i++)
                if(getBoard().board[startRow - i][startColumn + i].getIsOcuppied())
                    return false;
            return true;
        }
        //move down left
        else if(rowDiff > 0 && columnDiff < 0 && Math.abs(rowDiff) == Math.abs(columnDiff)){
            for(int i = 1 ; i < rowDiff ; i++)
                if(getBoard().board[startRow + i][startColumn - i].getIsOcuppied())
                    return false;
            return true;
        }
        //move up left
        else if(rowDiff < 0 && columnDiff < 0 && Math.abs(rowDiff) == Math.abs(columnDiff)){
            for(int i = 1 ; i < Math.abs(columnDiff) ; i++)
                if(getBoard().board[startRow - i][startColumn - i].getIsOcuppied())
                    return false;
            return true;
        }            
        return false;
    }  
}

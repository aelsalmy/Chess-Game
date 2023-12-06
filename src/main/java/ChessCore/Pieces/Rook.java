package ChessCore.Pieces;

import ChessCore.ChessBoard;
import ChessCore.Move;
import ChessCore.Spot;
import ChessCore.enums.Colors;
import ChessCore.enums.PieceTypes;
import ChessCore.exceptions.NoPieceFoundException;

public class Rook extends Piece{
    
    public Rook(Colors color , Spot pos , ChessBoard board){
        super(color , pos , board);
        this.setType(PieceTypes.Rook);
        if(this.getPieceColor() == Colors.White)
            this.setPieceImg("WhiteRook.png");
        else 
            this.setPieceImg("BlackRook.png");
    }
    
    @Override
    public boolean isValidMove(Move move){
        int startRow = move.getStartPosition().getRow();
        int startColumn = move.getStartPosition().getColumn();
        int finalRow = move.getFinalPosition().getRow();
        int finalColumn = move.getFinalPosition().getColumn();
        
        if(move.getFinalPosition().getIsOcuppied())
            try{
                if(move.getFinalPosition().getCurrentPiece().getPieceColor() == this.getPieceColor())
                    return false;
            }
            catch(NoPieceFoundException e){
                System.out.println("Initial Spot is Empty !");
            }
        if(finalRow == startRow){
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
                        if(getBoard().board[startRow][i].getIsOcuppied())
                            return false;
                    } 
                    return true;
                }
            }
        }
        else if(finalColumn == startColumn){
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
        return false;
    }
  
}

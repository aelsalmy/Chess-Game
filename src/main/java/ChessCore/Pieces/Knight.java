package ChessCore.Pieces;

import ChessCore.ChessBoard;
import ChessCore.Move;
import ChessCore.Spot;
import ChessCore.enums.Colors;
import ChessCore.enums.PieceTypes;
import ChessCore.exceptions.NoPieceFoundException;

public class Knight extends Piece{
    
    public Knight(Colors color , Spot pos , ChessBoard board){
        super(color , pos , board);
        this.setType(PieceTypes.Knight);
        if(this.getPieceColor() == Colors.White)
            this.setPieceImg("WhiteKnight.png");
        else 
            this.setPieceImg("BlackKnight.png");
    }
    
    @Override
    public boolean isValidMove(Move move) throws NoPieceFoundException{
       int startRow = move.getStartPosition().getRow();
       int startColumn = move.getStartPosition().getColumn();
       int finalRow = move.getFinalPosition().getRow();
       int finalColumn = move.getFinalPosition().getColumn();
       
       //System.out.println(move.getFinalPosition().getCurrentPiece().getPieceColor() + " and " + this.getPieceColor());
       
       if(move.getFinalPosition().getIsOcuppied() && move.getFinalPosition().getCurrentPiece().getPieceColor() == this.getPieceColor()){
            return false;
       }
       else{
           //knight can move 2 then one in any direction
           if(Math.abs(finalRow - startRow) * Math.abs(finalColumn - startColumn) == 2)
               return true;
           else 
               return false;
        }
    }
    
}

package ChessCore;

import ChessCore.Pieces.Piece;
import ChessCore.exceptions.NoPieceFoundException;

public class Spot {
   
    private Piece currentPiece;         //to show what piece type is on
    private boolean isOcuppied = false; //flag to show whether spot occupied or not
    private int row;
    private int column;
    
     public Spot(Piece piece){
        this.currentPiece = piece;
        if(this.currentPiece != null)
            this.isOcuppied = true;
    }
     
    //setters and getters
    public Piece getCurrentPiece() throws NoPieceFoundException{
        if(this.currentPiece == null)
            throw new NoPieceFoundException();
        else
            return currentPiece;
    }
    public void setCurrentPiece(Piece currentPiece) {
        this.currentPiece = currentPiece;
    }
    public boolean getIsOcuppied() {
        return isOcuppied;
    }
    public void setIsOcuppied(boolean isOcuppied) {
        this.isOcuppied = isOcuppied;
    }
    public int getRow() {
        return row;
    }
    public void setRow(int row) {
        this.row = row;
    }
    public int getColumn() {
        return column;
    }
    public void setColumn(int column) {
        this.column = column;
    }
}

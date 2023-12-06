package ChessCore.Pieces;

import ChessCore.ChessBoard;
import ChessCore.Move;
import ChessCore.Spot;
import ChessCore.enums.Colors;
import ChessCore.enums.PieceTypes;
import ChessCore.exceptions.NoPieceFoundException;

public abstract class Piece {

   
    private String pieceImg;
    private PieceTypes type;            
    private boolean hasMoved = false;   
    private boolean isCaptured = false; 
    private Colors pieceColor;
    private Spot currentPosition;
    private ChessBoard currentBoard;
    
    public Piece(Colors color , Spot initialPlace , ChessBoard chessBoard){
        this.pieceColor = color;
        this.currentPosition = initialPlace;
        this.currentBoard = chessBoard;
    }
    
    public abstract boolean isValidMove (Move move) throws NoPieceFoundException;
    
    //public abstract ArrayList<Move> allValidMoves();
    
    
    //setters and getters
    public Colors getPieceColor() {
        return pieceColor;
    }
    public void setPieceColor(Colors pieceColor) {
        this.pieceColor = pieceColor;
    }
    public boolean getHasMoved() {
        return hasMoved;
    }
    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }
    public boolean isIsCaptured() {
        return isCaptured;
    }
    public void setIsCaptured(boolean isCaptured) {
        this.isCaptured = isCaptured;
    }
    public PieceTypes getType() {
        return type;
    }
    public void setType(PieceTypes type) {
        this.type = type;
    }
    public Spot getCurrentPosition() {
        return currentPosition;
    }
    public void setCurrentPosition(Spot currentPosition) {
        this.currentPosition = currentPosition;
    }
    public ChessBoard getBoard() {
        return currentBoard;
    }
    public void setBoard(ChessBoard board) {
        this.currentBoard = board;
    }
    public void setPieceImg(String str){
        this.pieceImg = str;
    }
    public String getPieceImg(){
        return this.pieceImg;
    }
}

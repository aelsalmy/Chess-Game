package ChessCore;

import ChessCore.Pieces.PieceFactory;
import ChessCore.exceptions.NoPieceFoundException;

public class ChessBoardBuilder {
    
    public ChessBoard build(){
        return new ChessBoard();
    }
    
    public ChessBoard copy(ChessBoard src){
        ChessBoard res = new ChessBoard();
        PieceFactory pieceFactory = new PieceFactory();
        
        for(int i = 0 ; i < 8 ; i++)
            for(int j = 0 ; j < 8 ; j++){
                if(src.board[i][j].getIsOcuppied()){
                    try{
                        pieceFactory.setColor(src.board[i][j].getCurrentPiece().getPieceColor());
                        res.board[i][j] = new Spot(pieceFactory.copy(src.board[i][j].getCurrentPiece()));
                    }
                    catch(NoPieceFoundException e){}
                }
                else
                    res.board[i][j] = new Spot(null);
                
                res.board[i][j].setRow(i);
                res.board[i][j].setColumn(j);
                res.board[i][j].setIsOcuppied(src.board[i][j].getIsOcuppied());
            }
        return res;
    }
}

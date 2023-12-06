/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ChessCore.Pieces;

import ChessCore.ChessBoard;
import ChessCore.Spot;
import ChessCore.enums.Colors;
import ChessCore.enums.PieceTypes;

/**
 *
 * @author abdul
 */
public class PieceFactory {
    
    private Colors color;
    
    public void setColor(Colors col){
        this.color = col;
    }
    
    public Piece getPiece(Spot sp , ChessBoard b , PieceTypes type){
        Piece p = null;
        switch(type){
            case Pawn -> p = new Pawn(color , sp , b);
            case King -> p = new King(color , sp , b);
            case Queen -> p = new Queen(color , sp , b);
            case Rook -> p = new Rook(color , sp , b);
            case Bishop -> p = new Bishop(color , sp , b);
            case Knight -> p = new Knight(color , sp , b);
        }
        return p;
    }
    public Piece copy(Piece p){
        this.setColor(p.getPieceColor());
        return this.getPiece(p.getCurrentPosition() , p.getBoard() , p.getType());
    }
    
}

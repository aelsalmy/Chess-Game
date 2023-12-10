package ChessCore.Controllers;

import ChessCore.ChessGame;
import ChessCore.Move;
import ChessCore.Pieces.King;
import ChessCore.Pieces.Pawn;
import ChessCore.Pieces.Piece;
import ChessCore.Spot;
import ChessCore.enums.Colors;
import ChessCore.enums.PieceTypes;
import ChessCore.exceptions.NoPieceFoundException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import ChessCore.Subject;

public class CheckMateController implements Subject {
   
    ChessGame game;
    
    public CheckMateController(ChessGame g){
        this.game = g;
    }
    
    @Override
    public void update(){
        try{
           if(game.gameInProgress){
                game.gameInProgress = !this.endGame();
                if(!game.gameInProgress){
                    if(game.turn.getPlayerColor() == Colors.White)
                        JOptionPane.showMessageDialog(null , "Black Won !!");
                    else
                        JOptionPane.showMessageDialog(null , "White Won !!");
                }
           }
        }
        catch(NoPieceFoundException e){}
    }
    
    private boolean endGame() throws NoPieceFoundException{
        
        if(this.kingIsInCheck(game.turn.getPlayerColor())){
            System.out.println(game.turn.getPlayerColor());
            for(Piece p : game.turn.playerPieces){
                if(!game.getAllValidMovesFromSquare(p.getCurrentPosition()).isEmpty()){
                    System.out.println(p.getCurrentPosition().getCurrentPiece().getPieceColor());
                    System.out.println("");
                    for(Move m : game.getAllValidMovesFromSquare(p.getCurrentPosition())){
                        System.out.println(m.getFinalPosition().getRow() + " " + m.getFinalPosition().getColumn());
                    }
                   
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    
    public boolean spotIsInCheck(Spot loc) throws NoPieceFoundException{
        //if white turn then attacking is black
        
        if(game.turn.getPlayerColor() == Colors.White){
            //see if any black piece attacks the stated spot
            for(Piece p : game.playerBlack.playerPieces){
                ArrayList<Move> allMoves;
                if(p.getType() == PieceTypes.Pawn){
                    Pawn pd = (Pawn) p;
                    allMoves = pd.getCaptureMoves();
                }
                else{
                    allMoves = game.getAllValidMovesFromSquareBeforeCheck(p.getCurrentPosition());
                }
                if(game.containsMove(allMoves, new Move(p.getCurrentPosition() , loc))){
                    return true;
                }
            }
            return false;
        }
        else{
            for(Piece p : game.playerWhite.playerPieces){
                ArrayList<Move> allMoves;
                if(p.getType() == PieceTypes.Pawn){
                    Pawn pd = (Pawn) p;
                    allMoves = pd.getCaptureMoves();
                }
                else{
                    allMoves = game.getAllValidMovesFromSquareBeforeCheck(p.getCurrentPosition());
                }
                if(game.containsMove(allMoves, new Move(p.getCurrentPosition() , loc))){
                    return true;
                }
            } 
            return false;
        }
    }
    
    //check if king is in check or not
    public boolean kingIsInCheck(Colors color) throws NoPieceFoundException{
        
        return this.spotIsInCheck(getKing(color).getCurrentPosition());
        
    }
    
    //to check if move puts king in chechmate or not
    public boolean moveMakesKingInCheck(Move m) throws NoPieceFoundException{
        
        Piece pieceInFinal = null;
        boolean hasMovedHolder = m.getStartPosition().getCurrentPiece().getHasMoved();
        
        if(m.getFinalPosition().getIsOcuppied())
            pieceInFinal = m.getFinalPosition().getCurrentPiece();
        
        m.getFinalPosition().setCurrentPiece(null);
        
        if(game.turn.getPlayerColor() == Colors.White && pieceInFinal != null){
            game.playerBlack.playerPieces.remove(pieceInFinal);
        }
        else{
            game.playerWhite.playerPieces.remove(pieceInFinal);
        }
        
        m.makeMove();
        
        boolean flag = kingIsInCheck(game.turn.getPlayerColor());
        
        m.undo();
        
        if(pieceInFinal != null){
            m.getFinalPosition().setCurrentPiece(pieceInFinal);
            m.getFinalPosition().setIsOcuppied(true);
            if(game.turn.getPlayerColor() == Colors.White){
                game.playerBlack.playerPieces.add(pieceInFinal);
            }
            else{
                game.playerWhite.playerPieces.add(pieceInFinal);
            }
        }
        
        m.getStartPosition().getCurrentPiece().setHasMoved(hasMovedHolder);
        
        return flag;
    }
    
    public King getKing(Colors color){
        if(color == Colors.Black){
            for(Piece p : game.playerBlack.playerPieces){
                if(p.getType() == PieceTypes.King)
                    return (King) p;
            }
        }
        else{
            for(Piece p : game.playerWhite.playerPieces){
                if(p.getType() == PieceTypes.King)
                    return (King) p;
            }
        }   
        System.out.println("ERROR: NO KING");
        return null;
    }
}

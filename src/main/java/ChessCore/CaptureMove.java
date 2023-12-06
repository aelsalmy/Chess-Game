package ChessCore;

import ChessCore.Pieces.Piece;
import ChessCore.enums.Colors;
import ChessCore.exceptions.NoPieceFoundException;

public class CaptureMove {
    private Move m;
    private Piece pieceCaptured;
    private ChessGame game;
    
    public CaptureMove(Move mv , Piece p , ChessGame g){
        this.m = mv;
        this.pieceCaptured = p;
        this.game = g;
    }
    
    public void capturePiece(ChessGame game) throws NoPieceFoundException {

        m .getFinalPosition().getCurrentPiece().setIsCaptured(true);
        System.out.println(m.getFinalPosition().getCurrentPiece().getType() + " Captured");

        if (m.getFinalPosition().getCurrentPiece().getPieceColor() == Colors.Black) {
            game.playerBlack.playerPieces.remove(m.getFinalPosition().getCurrentPiece());
        } else if (m.getFinalPosition().getCurrentPiece().getPieceColor() == Colors.White) {
            game.playerWhite.playerPieces.remove(m.getFinalPosition().getCurrentPiece());
        }
    }
    
    public void undoCaptureMove(){
        
        this.pieceCaptured.setIsCaptured(false);
        
        if(pieceCaptured.getPieceColor() == Colors.White)
            game.playerWhite.playerPieces.add(pieceCaptured);
        else
            game.playerBlack.playerPieces.add(pieceCaptured);
        
        pieceCaptured.getCurrentPosition().setIsOcuppied(true);
        
        pieceCaptured.getCurrentPosition().setCurrentPiece(pieceCaptured);
    }
    
    public Piece getCapturedPiece(){
        return this.pieceCaptured;
    }
    
    public CaptureMoveMemento createCaptureMoveScreenShot(){
        return new CaptureMoveMemento(this);
    }
    
    public static CaptureMove extractCaptureMove(CaptureMoveMemento c){
        return c.getCaptureMoveFromScreenShot();
    }
    
    public class CaptureMoveMemento{
        private final CaptureMove capMove;
        
        private CaptureMoveMemento(CaptureMove c){
            this.capMove = c;
        }
        private CaptureMove getCaptureMoveFromScreenShot(){
            return this.capMove;
        }
        
    }
}

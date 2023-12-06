package ChessCore.UndoOperation;

import ChessCore.CaptureMove;
import ChessCore.CaptureMove.CaptureMoveMemento;
import ChessCore.ChessGame;
import ChessCore.Move;
import ChessCore.Move.MoveMemento;
import ChessCore.Observor;
import ChessCore.Pieces.Piece;
import ChessCore.enums.Colors;
import ChessCore.exceptions.NoPieceFoundException;
import java.util.Stack;
import javax.swing.JOptionPane;

public class UndoController implements Observor{
    
    private Stack<MoveMemento> moveHistory = new Stack();
    private Stack<CaptureMoveMemento> captureHistory = new Stack();
    
    private Move m = null;
    private Piece p = null;
    private CaptureMoveMemento captureMoveSS = null;
    private ChessGame game;
    
    public UndoController(ChessGame g){
        this.game = g;
    }
    
    public void setMove(Move move){
        this.m = move;
    }
    
    public void setPiece(Piece piece){
        this.p = piece;
    }
    
    public void setCaptureMove(CaptureMoveMemento capMoveMem){
        this.captureMoveSS = capMoveMem;
    }
    
    @Override
    public void update(){
        
        MoveMemento ss = m.createMoveScreenShot(p);
        moveHistory.push(ss);
        //System.out.println("Moves Stack: " + moveHistory.size());
        
        if(captureMoveSS != null){
            captureHistory.push(captureMoveSS);
        }
        
        //System.out.println("Capture Stack: " + captureHistory.size());
        
        this.p = null;
        this.m = null;
        this.captureMoveSS = null;
    }
    
    public CaptureMoveMemento getCaptureMove(){
        return captureHistory.pop();
    }
    
    public void restore(ChessGame game){
        Invoker inv = new Invoker();
        //return move
        try{
            if(moveHistory.empty()){
                JOptionPane.showMessageDialog( null , "No Moves Prior !!");
                return;
            }
            
            MoveMemento moveMemento = moveHistory.pop();
            Move mv = Move.extractMove(moveMemento);
            Piece piece = Move.extractPiece(moveMemento);
            
            switch(mv.getMoveType()){
                case Move -> inv.executeCommand(new MoveUndoCommand(mv , game , piece));
                case EnPassant -> inv.executeCommand(new EnPassantUndoCommand(mv , game , piece));
                case Promotion -> inv.executeCommand(new PromotionUndoCommand(mv , game , piece));
                case Castle -> inv.executeCommand(new CastleUndoCommand(mv , game , piece));
                
            }
            
            if(game.turn.getPlayerColor() == Colors.White){
                game.turn = game.playerBlack;
            
            }
            else{
                game.turn = game.playerWhite;
            }
        }
        catch(NoPieceFoundException e){
            System.out.println("LOL");
        }
    }
    
}

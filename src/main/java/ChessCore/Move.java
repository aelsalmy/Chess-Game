package ChessCore;

//Class storing final and starting position to show a move

import ChessCore.Controllers.CastleController;
import ChessCore.Controllers.PromotionController;
import ChessCore.Controllers.EnPassantController;
import ChessCore.Pieces.Pawn;
import ChessCore.Pieces.Piece;
import ChessCore.Pieces.PieceFactory;
import ChessCore.enums.Colors;
import ChessCore.enums.MoveType;
import ChessCore.enums.PieceTypes;
import ChessCore.exceptions.NoPieceFoundException;

public class Move{
    
    private Spot startPosition;
    private Spot finalPosition;
    private boolean moveHasCapture = false;
    private MoveType moveType = MoveType.Move;
   
    public Move(Spot start , Spot end){
       this.startPosition = start;
       this.finalPosition = end;
    }
    
    //perform move operation
    public void makeMove() throws NoPieceFoundException{
        Spot finalSpot = this.getStartPosition();
        Piece p = this.getStartPosition().getCurrentPiece();
        
        p.setHasMoved(true);
        p.setCurrentPosition(this.getFinalPosition());
        this.getFinalPosition().setCurrentPiece(p); 
        this.getFinalPosition().setIsOcuppied(true);
        this.getStartPosition().setCurrentPiece(null);
        this.getStartPosition().setIsOcuppied(false);
    
    }
    
    public void move(ChessGame game) {
        //check if game ended or still in progress
        CastleController castleController = new CastleController(game);
        PromotionController promController = new PromotionController(game);
        EnPassantController enPassantController = new EnPassantController(game);
        
        
        try {
            if (game.gameInProgress) {
                int finalRow = this.getFinalPosition().getRow();
                int startRow = this.getStartPosition().getRow();
                int finalColumn = this.getFinalPosition().getColumn();
                int startColumn = this.getStartPosition().getColumn();
                
                //check if move played is in its turn
                if (this.getStartPosition().getCurrentPiece().getPieceColor() != game.turn.getPlayerColor()) {
                    System.out.println("Invalid Move: Not this Player's turn !");
                    return;
                }
               
                //promotion
                if ((finalRow == 0 || finalRow == 7) && this.getStartPosition().getCurrentPiece().getType() == PieceTypes.Pawn) {
                        Piece oldPawn = new PieceFactory().copy(this.getStartPosition().getCurrentPiece());
                        promController.promotePawn(this.getStartPosition());
                        
                        game.undoController.setPiece(oldPawn);
                        game.undoController.setMove(this);
                        
                        if(this.getFinalPosition().getIsOcuppied()){
                            CaptureMove capMove = new CaptureMove(this , this.getFinalPosition().getCurrentPiece() , game);
                            this.moveHasCapture = true;
                            game.undoController.setCaptureMove(capMove.createCaptureMoveScreenShot());
                            capMove.capturePiece(game);
                        }
                        
                        this.makeMove();
                        
                        if (game.turn.getPlayerColor() == Colors.White) {
                            game.turn = game.playerBlack;
                        } else {
                            game.turn = game.playerWhite;
                        }
                        
                        this.moveType =  MoveType.Promotion;
                        
                        game.notifyControllers();
                        return;       
                }
                //check if positions are the same 
               
                if (this.getFinalPosition() == this.getStartPosition()) {
                    
                    System.out.println("Invalid Move: Initial and Final Position are the same!");
                } else if (game.containsMove(game.getAllValidMovesFromSquare(this.getStartPosition()),this)) {                    
                    
                    //to capture piece
                    if (this.getFinalPosition().getIsOcuppied() && this.getFinalPosition().getCurrentPiece().getPieceColor() != game.turn.getPlayerColor()) {       
                        CaptureMove capMove = new CaptureMove(this , this.getFinalPosition().getCurrentPiece() , game);
                        this.moveHasCapture = true;
                        game.undoController.setCaptureMove(capMove.createCaptureMoveScreenShot());
                        capMove.capturePiece(game);
                    }
                    if ((finalRow == 0 || finalRow == 7) && this.getStartPosition().getCurrentPiece().getType() == PieceTypes.Pawn) {                        
                        promController.promotePawn(this.getFinalPosition());     
                        return;
                    }
                    
                    //check castling possibility
                    else if (this.getStartPosition().getCurrentPiece().getType() == PieceTypes.King && Math.abs(this.getFinalPosition().getColumn() - this.getStartPosition().getColumn()) == 2) {
                        if(castleController.checkCastling(this)){
                            castleController.castleKing(this);
                            this.moveType = MoveType.Castle;
                        }
                    }
   
                    Piece piece = this.getStartPosition().getCurrentPiece();
                    
                    //to set Pawn First two Move Step
                    if (((startRow == 1 && finalRow == 3) || (startRow == 6 && finalRow == 4)) && piece.getType() == PieceTypes.Pawn && startColumn - finalColumn == 0) {
                        Pawn pawnpiece = (Pawn) piece;
                        pawnpiece.setFirstTwoMoveStep(true);
                    } 
                    
                    //to capture a possible enpassant
                    else if (piece.getType() == PieceTypes.Pawn && game.mainBoard.board[startRow][finalColumn].getIsOcuppied() && game.mainBoard.board[startRow][finalColumn].getCurrentPiece().getType() == PieceTypes.Pawn && Math.abs(finalColumn - startColumn) == 1 && Math.abs(finalRow - startRow) == 1) {
                        Pawn peicetocapture = (Pawn) game.mainBoard.board[startRow][finalColumn].getCurrentPiece();
    
                        if (peicetocapture.getFirstTwoMoveStep()){
                            CaptureMove capMove = new CaptureMove(this , game.mainBoard.board[startRow][finalColumn].getCurrentPiece() , game);
                            
                            enPassantController.captureEnPassant(game.mainBoard.board[startRow][finalColumn]);
                            this.moveType = MoveType.EnPassant;
                            this.moveHasCapture = true;
                            game.undoController.setCaptureMove(capMove.createCaptureMoveScreenShot());
                        }
                    }
                    
                    else if (piece.getType() == PieceTypes.Pawn) {
                        
                        Pawn winpawn = (Pawn) piece;
                        winpawn.setFirstTwoMoveStep(false);
                        
                    }
                    
                    game.undoController.setPiece(new PieceFactory().copy(this.getStartPosition().getCurrentPiece()));
                    game.undoController.setMove(this);
                                      
                    this.makeMove();

                    //System.out.println(line);
                    
                    if (game.turn.getPlayerColor() == Colors.White) {
                        for(Piece p : game.playerBlack.playerPieces){
                            if(p.getType() == PieceTypes.Pawn){
                                Pawn pawn = (Pawn)p;
                                pawn.setFirstTwoMoveStep(false);
                            }
                        }
                        game.turn = game.playerBlack;
                    } else {
                        for(Piece p : game.playerWhite.playerPieces){
                            if(p.getType() == PieceTypes.Pawn){
                                Pawn pawn = (Pawn)p;
                                pawn.setFirstTwoMoveStep(false);
                            }
                        }
                        game.turn = game.playerWhite;      
                    }
                    
                    game.notifyControllers();

                } else {
                    System.out.println("Invalid Move");
                }
            } 
            else{
                System.out.println("Game Already Ended");
            }

        } catch (NoPieceFoundException e) {
            System.out.println("Invalid Move: Starting Position is Empty");
        }
    }
    
    public void undo(){
        Spot startPlace = this.finalPosition;
        Spot endPlace = this.startPosition;
        
        try{
            new Move(startPlace , endPlace).makeMove();
          
        }
        catch(NoPieceFoundException e){}
    }
        
    public MoveMemento createMoveScreenShot(Piece p){
        return new MoveMemento(this , p);
    }
    
    public static Move extractMove(MoveMemento m){
        return m.getMoveFromScreenShot();
    }
    
    public static Piece extractPiece(MoveMemento m){
        return m.getPieceFromScreenShot();
    }
    
    public class MoveMemento{
        
        private final Move moveScreenShot;
        private final Piece pieceScreenShot;
        
        private MoveMemento(Move m , Piece p){
            this.moveScreenShot = m;
            this.pieceScreenShot = p;
        }
        private Move getMoveFromScreenShot(){
            return this.moveScreenShot;
        }
        private Piece getPieceFromScreenShot(){
            return this.pieceScreenShot;
        }
    }
    
    
    //setters and getters
    public Spot getStartPosition() {
        return startPosition;
    }
    public void setStartPosition(Spot startPosition) {
        this.startPosition = startPosition;
    }
    public Spot getFinalPosition() {
        return finalPosition;
    }
    public void setFinalPosition(Spot finalPosition) {
        this.finalPosition = finalPosition;
    }
    public boolean getHasCapture(){
        return this.moveHasCapture;
    }
    public MoveType getMoveType(){
        return this.moveType;
    }

}

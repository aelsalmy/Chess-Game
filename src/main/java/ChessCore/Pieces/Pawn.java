package ChessCore.Pieces;

import ChessCore.ChessBoard;
import ChessCore.Move;
import ChessCore.Spot;
import ChessCore.enums.Colors;
import ChessCore.enums.PieceTypes;
import static ChessCore.enums.PieceTypes.Pawn;
import ChessCore.exceptions.NoPieceFoundException;
import java.util.ArrayList;

public class Pawn extends Piece {

    private boolean firstTwoMoveStep = false;

    public Pawn(Colors color, Spot pos, ChessBoard board) {
        super(color, pos, board);
        this.setType(PieceTypes.Pawn);
        if(this.getPieceColor() == Colors.White)
            this.setPieceImg("WhitePawn.png");
        else 
            this.setPieceImg("BlackPawn.png");
    }

    //method to return possible capture moves of a pawn
    public ArrayList<Move> getCaptureMoves() {
        ArrayList<Move> moves = new ArrayList();
        int startRow = this.getCurrentPosition().getRow();
        int startColumn = this.getCurrentPosition().getColumn();
        if(this.getPieceColor() == Colors.Black){
            if (startColumn <= 6) {
                moves.add(new Move(this.getCurrentPosition(), getBoard().board[startRow + 1][startColumn + 1]));
            }
            if (startColumn >= 1) {
                moves.add(new Move(this.getCurrentPosition(), getBoard().board[startRow + 1][startColumn - 1]));
            }
        }
        else{
            if (startColumn <= 6) {
                moves.add(new Move(this.getCurrentPosition(), getBoard().board[startRow - 1][startColumn + 1]));
            }
            if (startColumn >= 1) {
                moves.add(new Move(this.getCurrentPosition(), getBoard().board[startRow - 1][startColumn - 1]));
            }
        }

        return moves;
    }

    @Override
    public boolean isValidMove(Move move) {
        int startRow = move.getStartPosition().getRow();
        int startColumn = move.getStartPosition().getColumn();
        int finalRow = move.getFinalPosition().getRow();
        int finalColumn = move.getFinalPosition().getColumn();

        //normal pawn moves 2 forward pieces in first move or 1 piece forward if final position empty
        if (!move.getFinalPosition().getIsOcuppied()) {
            if (Math.abs(finalColumn - startColumn) == 0 && finalRow - startRow == 1 && this.getPieceColor() == Colors.Black) {
                return true;
            }
            if (Math.abs(finalColumn - startColumn) == 0 && finalRow - startRow == -1 && this.getPieceColor() == Colors.White) {
                return true;
            }
            if (!this.getHasMoved()) {
                if (Math.abs(finalColumn - startColumn) == 0 && finalRow - startRow == 2 && this.getPieceColor() == Colors.Black) {
                    for(int i = startRow + 1; i < startRow + 2 ; i++)
                        if(getBoard().board[i][startColumn].getIsOcuppied())
                            return false;
                    return true;
                }
                if (Math.abs(finalColumn - startColumn) == 0 && finalRow - startRow == -2 && this.getPieceColor() == Colors.White) {
                    for(int i = startRow - 1 ; i > startRow - 2 ; i--)
                        if(getBoard().board[i][startColumn].getIsOcuppied())
                            return false;
                    return true;
                }
            }
            //en passant capture
            try{
                if(finalRow - startRow == 1 && Math.abs(finalColumn - startColumn) == 1 && this.getPieceColor() == Colors.Black){
                    
                    ChessBoard b = this.getBoard();
                    Spot nextspot = b.board[startRow][finalColumn];
                    
                    if (!nextspot.getIsOcuppied()) {
                        return false;
                    }
                    if (nextspot.getCurrentPiece().getType() != Pawn || nextspot.getCurrentPiece().getPieceColor() == this.getPieceColor()) {
                        return false;
                    }
                    Pawn p = (Pawn) nextspot.getCurrentPiece();
                    if (p.firstTwoMoveStep == true) {
                        return true;
                    }
                }
                if (finalRow - startRow == -1 && Math.abs(finalColumn - startColumn) == 1 && this.getPieceColor() == Colors.White){
                    ChessBoard b = this.getBoard();
                    Spot nextspot = b.board[startRow][finalColumn];
                    if (nextspot.getIsOcuppied() == false) {
                        return false;
                    }
                    if (nextspot.getCurrentPiece().getType() != Pawn || nextspot.getCurrentPiece().getPieceColor() == this.getPieceColor()) {
                        return false;
                    }
                    Pawn p = (Pawn) nextspot.getCurrentPiece();
                    if (p.firstTwoMoveStep == true) {
                        return true;
                    }
                }
            } 
            catch (NoPieceFoundException e) {}
            
        } 
        //pawn can capture moving diagonally
        else {
            try {
                //pawn cant capture while moving forward
                if (move.getFinalPosition().getCurrentPiece().getPieceColor() == this.getPieceColor()) {
                    return false;
                }
                //pawn moving diagonally down to capture 
                if (finalRow - startRow == 1 && Math.abs(finalColumn - startColumn) == 1 && this.getPieceColor() == Colors.Black) {
                    return true;
                }
                //pawn moving diagonally up to capture
                if (finalRow - startRow == -1 && Math.abs(finalColumn - startColumn) == 1 && this.getPieceColor() == Colors.White) {
                    return true;
                }

            } catch (NoPieceFoundException e) {
                System.out.println("Initial Spot is Empty !");
            }
        }
        return false;
    }
    
    public void setFirstTwoMoveStep(boolean firstmove2step) {
        this.firstTwoMoveStep = firstmove2step;
    }
    public boolean getFirstTwoMoveStep() {
        return firstTwoMoveStep;
    }
}
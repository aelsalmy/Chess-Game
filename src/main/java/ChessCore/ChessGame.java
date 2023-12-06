package ChessCore;

import ChessCore.Controllers.*;
import ChessCore.Pieces.*;
import ChessCore.UndoOperation.UndoController;
import ChessCore.enums.*;
import ChessCore.exceptions.NoPieceFoundException;
import java.util.ArrayList;

public class ChessGame {

    public Player playerWhite;
    public Player playerBlack;
    public ChessBoard mainBoard;
    public Player turn = playerWhite;
    public boolean gameInProgress;
    private MoveObservor mObs;
    public UndoController undoController = new UndoController(this);

    public ChessGame(Player white, Player black , MoveObservor m) {
        this.playerWhite = white;
        playerWhite.setPlayerColor(Colors.White);

        this.playerBlack = black;
        playerBlack.setPlayerColor(Colors.Black);
        
        this.mObs = m;
        this.mObs.addSubscription(undoController);
    }

    public void startGame() {

        this.mainBoard = new ChessBoard();
        this.gameInProgress = true;
        
        PieceFactory pieceFactory = new PieceFactory();
        
        pieceFactory.setColor(Colors.Black);

        //Put all White Pieces on Board
        mainBoard.board[0][0] = new Spot(pieceFactory.getPiece(mainBoard.board[0][0], this.mainBoard , PieceTypes.Rook));
        mainBoard.board[0][1] = new Spot(pieceFactory.getPiece(mainBoard.board[0][1], this.mainBoard , PieceTypes.Knight));
        mainBoard.board[0][2] = new Spot(pieceFactory.getPiece(mainBoard.board[0][2], this.mainBoard , PieceTypes.Bishop));
        mainBoard.board[0][3] = new Spot(pieceFactory.getPiece(mainBoard.board[0][3], this.mainBoard , PieceTypes.Queen));
        mainBoard.board[0][4] = new Spot(pieceFactory.getPiece(mainBoard.board[0][4], this.mainBoard , PieceTypes.King));
        mainBoard.board[0][5] = new Spot(pieceFactory.getPiece(mainBoard.board[0][5], this.mainBoard , PieceTypes.Bishop));
        mainBoard.board[0][6] = new Spot(pieceFactory.getPiece(mainBoard.board[0][6], this.mainBoard , PieceTypes.Knight));
        mainBoard.board[0][7] = new Spot(pieceFactory.getPiece(mainBoard.board[0][7], this.mainBoard , PieceTypes.Rook));

        for (int i = 0; i < 8; i++) {
            mainBoard.board[1][i] = new Spot(pieceFactory.getPiece(mainBoard.board[1][i], this.mainBoard , PieceTypes.Pawn));
        }
        
        pieceFactory.setColor(Colors.White);

        //Put all Black Pieces on Board
        mainBoard.board[7][0] = new Spot(pieceFactory.getPiece(mainBoard.board[7][0], this.mainBoard , PieceTypes.Rook));
        mainBoard.board[7][1] = new Spot(pieceFactory.getPiece(mainBoard.board[7][1], this.mainBoard , PieceTypes.Knight));
        mainBoard.board[7][2] = new Spot(pieceFactory.getPiece(mainBoard.board[7][2], this.mainBoard , PieceTypes.Bishop));
        mainBoard.board[7][3] = new Spot(pieceFactory.getPiece(mainBoard.board[7][3], this.mainBoard , PieceTypes.Queen));
        mainBoard.board[7][4] = new Spot(pieceFactory.getPiece(mainBoard.board[7][4], this.mainBoard , PieceTypes.King));
        mainBoard.board[7][5] = new Spot(pieceFactory.getPiece(mainBoard.board[7][5], this.mainBoard , PieceTypes.Bishop));
        mainBoard.board[7][6] = new Spot(pieceFactory.getPiece(mainBoard.board[7][6], this.mainBoard , PieceTypes.Knight));
        mainBoard.board[7][7] = new Spot(pieceFactory.getPiece(mainBoard.board[7][7], this.mainBoard , PieceTypes.Rook));

        for (int i = 0; i < 8; i++) {
            mainBoard.board[6][i] = new Spot(pieceFactory.getPiece(mainBoard.board[6][i] , this.mainBoard , PieceTypes.Pawn));
        }

        //to set row and column for all spots
        //set pieces to its player
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                //Initialize all empty spots as empty
                if (mainBoard.board[i][j] == null) {
                    mainBoard.board[i][j] = new Spot(null);
                } else {
                    try {
                        mainBoard.board[i][j].getCurrentPiece().setCurrentPosition(mainBoard.board[i][j]);
                    } catch (NoPieceFoundException e) {
                    }
                }

                mainBoard.board[i][j].setRow(i);
                mainBoard.board[i][j].setColumn(j);

                try {
                    if (mainBoard.board[i][j].getCurrentPiece().getPieceColor() == Colors.Black) {
                        playerBlack.playerPieces.add(mainBoard.board[i][j].getCurrentPiece());
                    } else if (mainBoard.board[i][j].getCurrentPiece().getPieceColor() == Colors.White) {
                        playerWhite.playerPieces.add(mainBoard.board[i][j].getCurrentPiece());
                    }
                } catch (NoPieceFoundException e) {
                }
            }
        }

        this.turn = playerWhite;

    }

    public void visualiseBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                try {
                    System.out.print(mainBoard.board[i][j].getCurrentPiece().getType() + " ");
                } catch (NoPieceFoundException e) {
                    System.out.print("null ");
                }
            }
            System.out.println();
        }
    }

    public void printPieces() {
        System.out.println("Black Pieces: ");
        for (Piece p : playerBlack.playerPieces) {
            System.out.println(p.getType());
        }
        System.out.println("");
        System.out.println("White Pieces: ");
        for (Piece p : playerWhite.playerPieces) {
            System.out.println(p.getType());
        }

    }
    
    public ArrayList<Move> getAllValidMovesFromSquare(Spot sq) throws NoPieceFoundException {
        ArrayList<Move> moves = getAllValidMovesFromSquareBeforeCheck(sq);
        ArrayList<Move> toBeDeleted = new ArrayList();
        
        CastleController castleController = new CastleController(this);
        
        CheckMateController checkController = new CheckMateController(this);
        for (Move m : moves) {
            if (sq.getCurrentPiece().getType() == PieceTypes.King && checkController.spotIsInCheck(m.getFinalPosition())) {
                toBeDeleted.add(m);
            } else if (checkController.moveMakesKingInCheck(m)) {
                toBeDeleted.add(m);
            }
            else if(m.getStartPosition().getCurrentPiece().getType() == PieceTypes.King && Math.abs(m.getFinalPosition().getColumn() - m.getStartPosition().getColumn()) == 2){
                if(!castleController.checkCastling(m)){
                    toBeDeleted.add(m);
                }
            }
            
        }
        for (Move m : toBeDeleted) {
            moves.remove(m);
        }                     
        return moves;
    }

    public ArrayList getAllValidMovesFromSquareBeforeCheck(Spot square) {
        ArrayList<Move> moves = new ArrayList();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Move m = new Move(square, this.mainBoard.board[i][j]);
                if (this.isValidMove(m)) {
                    moves.add(m);
                }
            }
        }
        return moves;
    }

    private boolean isValidMove(Move move) {
        try {
            
            return move.getStartPosition().getCurrentPiece().isValidMove(move);
            
        } catch (NoPieceFoundException e) {
            return false;
        }
    }

    public void notifyControllers() throws NoPieceFoundException {
        
        InSuffMaterialController inSuffController = new InSuffMaterialController(this);
        mObs.addSubscription(inSuffController);
        
        CheckMateController checkController = new CheckMateController(this);
        mObs.addSubscription(checkController);
        
        StaleMateController staleMateController = new StaleMateController(this);
        mObs.addSubscription(staleMateController);
        
        mObs.notifySubscribers();
    }

    //search in arrayList for a specific move
    public boolean containsMove(ArrayList<Move> arr , Move res){
        for(int i = 0 ; i < arr.size() ; i++){
            if(arr.get(i).getFinalPosition() == res.getFinalPosition())
                return true;
        }
        return false;
    }
}

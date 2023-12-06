package ChessCore;

public class ChessGameBuilder {
    private Player playerB;
    private Player playerW;
    private MoveObservor mObs;
    
    public void setPlayerBlack(Player p){
        this.playerB = p;
    }
    
    public void setPlayerWhite(Player p){
        this.playerW = p;
    }
    
    public void setMoveObservor(MoveObservor m){
        this.mObs = m;
    }
    
    public ChessGame build(){
        return new ChessGame(playerW , playerB , mObs);
    }
}

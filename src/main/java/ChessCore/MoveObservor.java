package ChessCore;

import java.util.ArrayList;

public class MoveObservor {
    
    private ArrayList<Observor> subscriptions = new ArrayList();
    
    public void addSubscription(Observor o){
        subscriptions.add(o);
    }
    
    public void removeSubscription(Observor o){
        subscriptions.remove(o);
    }
    
    public void notifySubscribers(){
        for(Observor obs : subscriptions)
            obs.update();
    }
}

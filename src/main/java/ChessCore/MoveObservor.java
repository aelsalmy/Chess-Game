package ChessCore;

import java.util.ArrayList;

public class MoveObservor {
    
    private ArrayList<Subject> subscriptions = new ArrayList();
    
    public void addSubscription(Subject o){
        subscriptions.add(o);
    }
    
    public void removeSubscription(Subject o){
        subscriptions.remove(o);
    }
    
    public void notifySubscribers(){
        for(Subject obs : subscriptions)
            obs.update();
    }
}

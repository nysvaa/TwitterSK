package fr.nashoba24.twittersk;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import twitter4j.Status;
import twitter4j.User;

public class EvtOnFavorite extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	private Status status;
	private User source;
	private User target;
 
    public EvtOnFavorite(User s, User t, Status st) {
    	this.status = st;
    	this.source = s;
    	this.target = t;
    }
    
    public Status getStatus() {
    	return this.status;
    }
    
    public User getSource() {
    	return this.source;
    }
    
    public User getTarget() {
    	return this.target;
    }

	public HandlerList getHandlers() {
		return handlers;
	}
	
    public static HandlerList getHandlerList() {
        return handlers;
    }
 
}

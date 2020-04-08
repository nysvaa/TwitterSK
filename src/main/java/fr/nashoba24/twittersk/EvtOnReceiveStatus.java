package fr.nashoba24.twittersk;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import twitter4j.Status;

public class EvtOnReceiveStatus extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	private Status s;
 
    public EvtOnReceiveStatus(Status status) {
    	this.s = status;
    }
    
    public Status getStatus() {
    	return this.s;
    }

	public HandlerList getHandlers() {
		return handlers;
	}
	
    public static HandlerList getHandlerList() {
        return handlers;
    }
 
}

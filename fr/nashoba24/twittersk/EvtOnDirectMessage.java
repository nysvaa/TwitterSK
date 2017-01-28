package fr.nashoba24.twittersk;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import twitter4j.DirectMessage;

public class EvtOnDirectMessage extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	private DirectMessage dm;
 
    public EvtOnDirectMessage(DirectMessage message) {
    	this.dm = message;
    }
    
    public String getDirectMessage() {
    	return "@" + dm.getSenderScreenName() + " - " + dm.getText() + " (id: " + dm.getId() + ")";
    }

	public HandlerList getHandlers() {
		return handlers;
	}
	
    public static HandlerList getHandlerList() {
        return handlers;
    }
 
}

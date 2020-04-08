package fr.nashoba24.twittersk;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import twitter4j.User;

public class EvtOnFollow extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	private User source;
 
    public EvtOnFollow(User user) {
    	this.source = user;
    }
    
    public User getSource() {
    	return this.source;
    }

	public HandlerList getHandlers() {
		return handlers;
	}
	
    public static HandlerList getHandlerList() {
        return handlers;
    }
 
}

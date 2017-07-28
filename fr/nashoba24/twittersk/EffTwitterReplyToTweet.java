package fr.nashoba24.twittersk;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.TwitterException;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class EffTwitterReplyToTweet extends Effect {
	
	private Expression<Status> status;
	private Expression<String> msg;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		msg = (Expression<String>) expr[0];
		status = (Expression<Status>) expr[1];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean b) {
		return "reply to tweet";
	}
	
	@Override
	protected void execute(Event e) {
		if(TwitterSK.tf==null) { return; }
		try {
			StatusUpdate su = new StatusUpdate(msg.getSingle(e));
			su.setInReplyToStatusId(status.getSingle(e).getId());
			TwitterSK.tf.getInstance().updateStatus(su);
		} catch (TwitterException e1) {
			e1.printStackTrace();
			System.out.println("Failed to reply: " + e1.getMessage());
		}
	}
}

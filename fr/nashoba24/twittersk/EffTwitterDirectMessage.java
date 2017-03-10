package fr.nashoba24.twittersk;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import twitter4j.TwitterException;
import twitter4j.User;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class EffTwitterDirectMessage extends Effect {
	
	private Expression<String> msg;
	private Expression<User> user;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		msg = (Expression<String>) expr[0];
		user = (Expression<User>) expr[1];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean b) {
		return "send direct message";
	}
	
	@Override
	protected void execute(Event e) {
		if(TwitterSK.tf==null) { return; }
		try {
			TwitterSK.tf.getInstance().sendDirectMessage(user.getSingle(e).getId(), msg.getSingle(e));
		} catch (TwitterException e1) {
			e1.printStackTrace();
			TwitterSK.getInstance().getLogger().severe("Failed to unfavorite status: " + e1.getMessage());
		}
	}
}

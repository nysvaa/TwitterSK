package fr.nashoba24.twittersk;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import twitter4j.TwitterException;
import twitter4j.User;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class EffTwitterBlockUser extends Effect {
	
	private Expression<User> user;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		user = (Expression<User>) expr[0];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean b) {
		return "block user";
	}
	
	@Override
	protected void execute(Event e) {
		if(TwitterSK.tf==null) { return; }
		try {
			TwitterSK.tf.getInstance().createBlock(user.getSingle(e).getScreenName());
		} catch (TwitterException e1) {
			e1.printStackTrace();
			TwitterSK.getInstance().getLogger().severe("Failed to block user: " + e1.getMessage());
		}
	}
}

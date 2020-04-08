package fr.nashoba24.twittersk;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import twitter4j.TwitterException;
import twitter4j.User;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprTwitterUserByScreenName extends SimpleExpression<User>{
	
	private Expression<String> screen;
	
	@Override
	public boolean isSingle() {
		return true;
	}
	
	@Override
	public Class<? extends User> getReturnType() {
		return User.class;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		screen = (Expression<String>) expr[0];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "user";
	}
	
	@Override
	@Nullable
	protected User[] get(Event e) {
		if(TwitterSK.tf==null) { return null; }
		try {
			return new User[] { TwitterSK.tf.getInstance().showUser(screen.getSingle(e)) };
		} catch (TwitterException e1) {
			e1.printStackTrace();
			System.out.println("Failed to get user: " + e1.getMessage());
			return null;
		}
	}
}


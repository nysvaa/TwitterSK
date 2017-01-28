package fr.nashoba24.twittersk;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import twitter4j.TwitterException;
import twitter4j.User;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprTwitterSelfUser extends SimpleExpression<User>{
	
	@Override
	public boolean isSingle() {
		return true;
	}
	
	@Override
	public Class<? extends User> getReturnType() {
		return User.class;
	}
	
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "my twitter account";
	}
	
	@Override
	@Nullable
	protected User[] get(Event e) {
		if(TwitterSK.tf==null) { return null; }
		try {
			return new User[] { TwitterSK.tf.getInstance().showUser(TwitterSK.tf.getInstance().getAccountSettings().getScreenName()) };
		} catch (TwitterException e1) {
			System.out.println("Failed to get your account: " + e1.getMessage());
			e1.printStackTrace();
		}
		return null;
	}
}


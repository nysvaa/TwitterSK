package fr.nashoba24.twittersk;

import java.util.List;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import twitter4j.TwitterException;
import twitter4j.User;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprTwitterSearchUser extends SimpleExpression<User>{
	
	private Expression<String> search;
	
	@Override
	public boolean isSingle() {
		return false;
	}
	
	@Override
	public Class<? extends User> getReturnType() {
		return User.class;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		search = (Expression<String>) expr[0];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "search users";
	}
	
	@Override
	@Nullable
	protected User[] get(Event e) {
		if(TwitterSK.tf==null) { return null; }
		try {
			List<User> result = TwitterSK.tf.getInstance().searchUsers(search.getSingle(e), 1);
			User[] l = new User[result.size()];
			l = result.toArray(l);
			return l;
		} catch (TwitterException e1) {
			e1.printStackTrace();
			return null;
		}
	}
}


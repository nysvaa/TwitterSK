package fr.nashoba24.twittersk;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import twitter4j.TwitterException;
import twitter4j.User;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprTwitterFavoriteCount extends SimpleExpression<Integer>{
	
	private Expression<User> user;
	private boolean self = false;
	
	@Override
	public boolean isSingle() {
		return true;
	}
	
	@Override
	public Class<? extends Integer> getReturnType() {
		return Integer.class;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		if(matchedPattern==0) {
			self = true;
			return true;
		}
		user = (Expression<User>) expr[0];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "favorite count";
	}
	
	@Override
	@Nullable
	protected Integer[] get(Event e) {
		if(TwitterSK.tf==null) { return null; }
		if(self) {
			try {
				return new Integer[] { TwitterSK.tf.getInstance().showUser(TwitterSK.tf.getInstance().getAccountSettings().getScreenName()).getFavouritesCount() };
			} catch (TwitterException e1) {
				e1.printStackTrace();
			}
		}
		return new Integer[] { user.getSingle(e).getFavouritesCount() };
	}
}


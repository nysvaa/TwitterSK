package fr.nashoba24.twittersk;

import javax.annotation.Nullable;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.event.Event;

import twitter4j.TwitterException;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprTwitterOutgoingFriendships extends SimpleExpression<Long>{
	
	@Override
	public boolean isSingle() {
		return false;
	}
	
	@Override
	public Class<? extends Long> getReturnType() {
		return Long.class;
	}
	
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "outgoing friendships";
	}
	
	@Override
	@Nullable
	protected Long[] get(Event e) {
		if(TwitterSK.tf==null) { return null; }
		try {
			return ArrayUtils.toObject(TwitterSK.tf.getInstance().getOutgoingFriendships(-1).getIDs());
		} catch (TwitterException e1) {
			e1.printStackTrace();
			System.out.println("Failed to get outgoing friendships: " + e1.getMessage());
			return null;
		}
	}
}


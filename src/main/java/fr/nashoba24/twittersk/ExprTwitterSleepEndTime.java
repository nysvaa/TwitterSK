package fr.nashoba24.twittersk;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import twitter4j.TwitterException;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprTwitterSleepEndTime extends SimpleExpression<String>{
	
	@Override
	public boolean isSingle() {
		return true;
	}
	
	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}
	
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "sleep end time";
	}
	
	@Override
	@Nullable
	protected String[] get(Event e) {
		if(TwitterSK.tf==null) { return null; }
		try {
			return new String[] { TwitterSK.tf.getInstance().getAccountSettings().getSleepEndTime() };
		} catch (TwitterException e1) {
			e1.printStackTrace();
			return null;
		}
	}
}


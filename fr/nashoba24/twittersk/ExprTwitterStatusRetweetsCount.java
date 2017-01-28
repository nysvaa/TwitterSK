package fr.nashoba24.twittersk;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import twitter4j.Status;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprTwitterStatusRetweetsCount extends SimpleExpression<Integer>{
	
	private Expression<Status> status;
	
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
		status = (Expression<Status>) expr[0];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "status retweets count";
	}
	
	@Override
	@Nullable
	protected Integer[] get(Event e) {
		if(TwitterSK.tf==null) { return null; }
		return new Integer[]{ status.getSingle(e).getRetweetCount() };
	}
}


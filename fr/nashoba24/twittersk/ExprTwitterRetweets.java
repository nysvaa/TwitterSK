package fr.nashoba24.twittersk;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.TwitterException;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprTwitterRetweets extends SimpleExpression<Status>{
	
	private Expression<Status> status;
	
	@Override
	public boolean isSingle() {
		return false;
	}
	
	@Override
	public Class<? extends Status> getReturnType() {
		return Status.class;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		status = (Expression<Status>) expr[0];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "retweets";
	}
	
	@Override
	@Nullable
	protected Status[] get(Event e) {
		if(TwitterSK.tf==null) { return null; }
		try {
			ResponseList<Status> list = TwitterSK.tf.getInstance().getRetweets(status.getSingle(e).getId());
			Status[] l = new Status[list.size()];
			l = list.toArray(l);
			return l;
		} catch (TwitterException e1) {
			e1.printStackTrace();
			System.out.println("Failed to get retweets: " + e1.getMessage());
			return null;
		}
	}
}


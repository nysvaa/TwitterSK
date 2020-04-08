package fr.nashoba24.twittersk;

import java.util.List;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import twitter4j.Query;
import twitter4j.Status;
import twitter4j.TwitterException;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprSearchTweets extends SimpleExpression<Status>{
	
	private Expression<String> search;
	
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
		search = (Expression<String>) expr[0];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "search tweets";
	}
	
	@Override
	@Nullable
	protected Status[] get(Event e) {
		if(TwitterSK.tf==null) { return null; }
		try {
			List<Status> result = TwitterSK.tf.getInstance().search(new Query(search.getSingle(e))).getTweets();
			Status[] l = new Status[result.size()];
			l = result.toArray(l);
			return l;
		} catch (TwitterException e1) {
			e1.printStackTrace();
			return null;
		}
	}
}


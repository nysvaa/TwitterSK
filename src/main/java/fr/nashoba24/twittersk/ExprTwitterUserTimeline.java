package fr.nashoba24.twittersk;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.User;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprTwitterUserTimeline extends SimpleExpression<Status>{
	
	private Expression<User> user;
	
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
		user = (Expression<User>) expr[0];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "user timeline";
	}
	
	@Override
	@Nullable
	protected Status[] get(Event e) {
		if(TwitterSK.tf==null) { return null; }
		try {
			ResponseList<Status> list = TwitterSK.tf.getInstance().getUserTimeline(user.getSingle(e).getScreenName());
			Status[] l = new Status[list.size()];
			l = list.toArray(l);
			return l;
		} catch (TwitterException e1) {
			e1.printStackTrace();
			System.out.println("Failed to get timeline: " + e1.getMessage());
			return null;
		}
	}
}


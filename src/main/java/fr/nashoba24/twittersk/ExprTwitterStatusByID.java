package fr.nashoba24.twittersk;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import twitter4j.Status;
import twitter4j.TwitterException;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprTwitterStatusByID extends SimpleExpression<Status>{
	
	private Expression<Long> id;
	
	@Override
	public boolean isSingle() {
		return true;
	}
	
	@Override
	public Class<? extends Status> getReturnType() {
		return Status.class;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		id = (Expression<Long>) expr[0];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "status";
	}
	
	@Override
	@Nullable
	protected Status[] get(Event e) {
		if(TwitterSK.tf==null) { return null; }
		try {
			return new Status[] { TwitterSK.tf.getInstance().showStatus(id.getSingle(e)) };
		} catch (TwitterException e1) {
			e1.printStackTrace();
			System.out.println("Failed to get status: " + e1.getMessage());
			return null;
		}
	}
}


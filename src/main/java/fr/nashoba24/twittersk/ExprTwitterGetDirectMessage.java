package fr.nashoba24.twittersk;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import twitter4j.DirectMessage;
import twitter4j.TwitterException;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprTwitterGetDirectMessage extends SimpleExpression<String>{
	
	private Expression<Long> id;
	
	@Override
	public boolean isSingle() {
		return true;
	}
	
	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		id = (Expression<Long>) expr[0];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "direct message";
	}
	
	@Override
	@Nullable
	protected String[] get(Event e) {
		if(TwitterSK.tf==null) { return null; }
		try {
			DirectMessage msg = TwitterSK.tf.getInstance().showDirectMessage(id.getSingle(e));
			return new String[] { "@" + msg.getSenderScreenName() + " - " + msg.getText() + "(" + msg.getId() + ")" };
		} catch (TwitterException e1) {
			e1.printStackTrace();
			return null;
		}
	}
}


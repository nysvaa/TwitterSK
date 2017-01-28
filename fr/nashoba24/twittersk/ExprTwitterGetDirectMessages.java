package fr.nashoba24.twittersk;

import java.util.ArrayList;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import twitter4j.DirectMessage;
import twitter4j.ResponseList;
import twitter4j.TwitterException;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprTwitterGetDirectMessages extends SimpleExpression<String>{
	
	@Override
	public boolean isSingle() {
		return false;
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
		return "direct messages";
	}
	
	@Override
	@Nullable
	protected String[] get(Event e) {
		if(TwitterSK.tf==null) { return null; }
		try {
			ResponseList<DirectMessage> list = TwitterSK.tf.getInstance().getDirectMessages();
			ArrayList<String> msg = new ArrayList<String>();
			for(DirectMessage dm : list) {
				msg.add("@" + dm.getSenderScreenName() + " - " + dm.getText() + " (id: " + dm.getId() + ")");
			}
			String[] l = new String[msg.size()];
			l = msg.toArray(l);
			return l;
		} catch (TwitterException e1) {
			e1.printStackTrace();
			return null;
		}
	}
}


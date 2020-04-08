package fr.nashoba24.twittersk;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import twitter4j.Status;
import twitter4j.TwitterException;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class EffRemoveTweet extends Effect {
	
	private Expression<Status> status;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		status = (Expression<Status>) expr[0];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean b) {
		return "remove status";
	}
	
	@Override
	protected void execute(Event e) {
		if(TwitterSK.tf==null) { return; }
		try {
			TwitterSK.tf.getInstance().destroyStatus(status.getSingle(e).getId());
		} catch (TwitterException e1) {
			e1.printStackTrace();
			TwitterSK.getInstance().getLogger().severe("Failed to favorite status: " + e1.getMessage());
		}
	}
}

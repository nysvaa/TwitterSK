package fr.nashoba24.twittersk;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import twitter4j.TwitterException;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class CondTwittererExists extends Condition {
	
	private Expression<String> screen;

    @SuppressWarnings("unchecked")
	@Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kl, ParseResult pr) {
    	screen = (Expression<String>) expr[0];
        return true;
    }

    @Override
    public String toString(@Nullable Event e, boolean b) {
        return "user exists";
    }

    @Override
    public boolean check(Event e) {
    	if(TwitterSK.tf==null) { return false; }
		try {
			TwitterSK.tf.getInstance().showUser(screen.getSingle(e));
		} catch (TwitterException e1) {
			if(e1.getStatusCode()==404) {
				return false;
			}
			else {
				e1.printStackTrace();
				return false;
			}
		}
		return true;
    }

}
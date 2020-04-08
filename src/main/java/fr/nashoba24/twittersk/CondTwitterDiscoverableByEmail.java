package fr.nashoba24.twittersk;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import twitter4j.TwitterException;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class CondTwitterDiscoverableByEmail extends Condition {

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kl, ParseResult pr) {
        return true;
    }

    @Override
    public String toString(@Nullable Event e, boolean b) {
        return "discoverable by email";
    }

    @Override
    public boolean check(Event e) {
    	if(TwitterSK.tf==null) { return false; }
    	try {
			return TwitterSK.tf.getInstance().getAccountSettings().isDiscoverableByEmail();
		} catch (TwitterException e1) {
			e1.printStackTrace();
			return false;
		}
    }

}
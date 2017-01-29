package fr.nashoba24.twittersk;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import twitter4j.TwitterException;
import twitter4j.User;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class CondTwitterFollow extends Condition {
	
	private Expression<User> user1;
	private Expression<User> user2;

    @SuppressWarnings("unchecked")
	@Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kl, ParseResult pr) {
    	user1 = (Expression<User>) expr[0];
    	user2 = (Expression<User>) expr[1];
        return true;
    }

    @Override
    public String toString(@Nullable Event e, boolean b) {
        return "user follow";
    }

    @Override
    public boolean check(Event e) {
    	if(TwitterSK.tf==null) { return false; }
		try {
			return TwitterSK.tf.getInstance().showFriendship(user1.getSingle(e).getId(), user2.getSingle(e).getId()).isSourceFollowingTarget();
		} catch (TwitterException e1) {
			e1.printStackTrace();
		}
		return false;
    }

}
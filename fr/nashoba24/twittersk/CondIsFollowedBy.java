package fr.nashoba24.twittersk;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import twitter4j.TwitterException;
import twitter4j.User;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class CondIsFollowedBy extends Condition {
	
	private Expression<User> user;

    @SuppressWarnings("unchecked")
	@Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kl, ParseResult pr) {
    	user = (Expression<User>) expr[0];
        return true;
    }

    @Override
    public String toString(@Nullable Event e, boolean b) {
        return "is followed by";
    }

    @Override
    public boolean check(Event e) {
    	if(TwitterSK.tf==null) { return false; }
    	try {
			return TwitterSK.tf.getInstance().lookupFriendships(user.getSingle(e).getScreenName()).get(0).isFollowedBy();
		} catch (TwitterException e1) {
			e1.printStackTrace();
			return false;
		}
    }

}
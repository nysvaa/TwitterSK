package fr.nashoba24.twittersk;

import java.util.ArrayList;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import twitter4j.ResponseList;
import twitter4j.TwitterException;
import twitter4j.User;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprTwitterUsersBlocked extends SimpleExpression<User>{
	
	@Override
	public boolean isSingle() {
		return false;
	}
	
	@Override
	public Class<? extends User> getReturnType() {
		return User.class;
	}
	
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "users blocked";
	}
	
	@Override
	@Nullable
	protected User[] get(Event e) {
		if(TwitterSK.tf==null) { return null; }
		try {
			ResponseList<User> list = TwitterSK.tf.getInstance().getBlocksList();
			ArrayList<User> users = new ArrayList<User>();
			for(User u : list) {
				users.add(u);
			}
			User[] l = new User[users.size()];
			l = users.toArray(l);
			return l;
		} catch (TwitterException e1) {
			e1.printStackTrace();
			return null;
		}
	}
}


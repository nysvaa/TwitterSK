package fr.nashoba24.twittersk;

import java.util.ArrayList;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.TwitterException;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprTwitterGetFavorites extends SimpleExpression<Status>{
	
	private Expression<Integer> page;
	private boolean paging = false;
	
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
		if(matchedPattern == 1) {
			page = (Expression<Integer>) expr[0];
			paging = true;
		}
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "favorite status";
	}
	
	@Override
	@Nullable
	protected Status[] get(Event e) {
		if(TwitterSK.tf==null) { return null; }
		try {
			if(paging) {
				ResponseList<Status> list = TwitterSK.tf.getInstance().getFavorites();
				ArrayList<Status> fvrt = new ArrayList<Status>();
				for(Status status : list) {
					fvrt.add(status);
				}
				Status[] l = new Status[fvrt.size()];
				l = fvrt.toArray(l);
				return l;
			}
			else {
				ResponseList<Status> list = TwitterSK.tf.getInstance().getFavorites(new Paging(page.getSingle(e)));
				ArrayList<Status> fvrt = new ArrayList<Status>();
				for(Status status : list) {
					fvrt.add(status);
				}
				Status[] l = new Status[fvrt.size()];
				l = fvrt.toArray(l);
				return l;
			}
		} catch (TwitterException e1) {
			e1.printStackTrace();
			return null;
		}
	}
}


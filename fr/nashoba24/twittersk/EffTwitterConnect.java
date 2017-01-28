package fr.nashoba24.twittersk;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class EffTwitterConnect extends Effect {
	
	private Expression<String> consumer_key;
	private Expression<String> consumer_secret;
	private Expression <String> access_token;
	private Expression <String> access_token_secret;
	private boolean debug = false;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		if(matchedPattern == 0) {
			debug = false;
		}
		else {
			debug = true;
		}
		consumer_key = (Expression<String>) expr[0];
		consumer_secret = (Expression<String>) expr[1];
		access_token = (Expression<String>) expr[2];
		access_token_secret = (Expression<String>) expr[3];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean b) {
		return "connect to twitter";
	}
	
	@Override
	protected void execute(Event e) {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(debug)
		  .setOAuthConsumerKey(consumer_key.getSingle(e))
		  .setOAuthConsumerSecret(consumer_secret.getSingle(e))
		  .setOAuthAccessToken(access_token.getSingle(e))
		  .setOAuthAccessTokenSecret(access_token_secret.getSingle(e));
		TwitterSK.tf = new TwitterFactory(cb.build());
		try {
			if(TwitterSK.tf.getInstance().verifyCredentials().isVerified()) {
				TwitterSK.tf = null;
				return;
			}
		} catch (TwitterException e1) {
			e1.printStackTrace();
			TwitterSK.tf = null;
			return;
		}
		TwitterSK.registerEvents(debug, consumer_key.getSingle(e), consumer_secret.getSingle(e), access_token.getSingle(e), access_token_secret.getSingle(e));
	}
}

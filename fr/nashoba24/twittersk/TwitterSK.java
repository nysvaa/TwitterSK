package fr.nashoba24.twittersk;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.Classes;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import twitter4j.DirectMessage;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.User;
import twitter4j.UserList;
import twitter4j.UserStreamListener;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterSK extends JavaPlugin {

	private static TwitterSK instance;
	
	public static TwitterFactory tf;
	public static TwitterStream twitterStream;
	
	  @Override
	  public void onDisable()
	  {
		  Bukkit.getLogger().info(ChatColor.translateAlternateColorCodes('&', "&bTwitterSK Disabled!"));
	  }
	  
	  @Override
	  public void onEnable()
	  {
		  instance = this;
		  TwitterSK.getInstance().registerAll();
		  Bukkit.getLogger().info(ChatColor.translateAlternateColorCodes('&', "&aTwitterSK Enabled!"));
	  }
	
	public void registerAll() {
		Classes.registerClass(new ClassInfo<User>(User.class, "twitterer").user("twitterer").name("twitterer").parser(new Parser<User>() {

			@Override
			public String getVariableNamePattern() {
				return ".+";
			}

			@Override
			@Nullable
			public User parse(String arg0, ParseContext arg1) {
				return null;
			}

			@Override
			public String toString(User arg0, int arg1) {
				return "@" + arg0.getScreenName();
			}

			@Override
			public String toVariableNameString(User arg0) {
				return "@" + arg0.getScreenName();
			}
		   
		}));
		Classes.registerClass(new ClassInfo<Status>(Status.class, "tweet").user("tweet").name("tweet").parser(new Parser<Status>() {

			@Override
			public String getVariableNamePattern() {
				return ".+";
			}

			@Override
			@Nullable
			public Status parse(String arg0, ParseContext arg1) {
				return null;
			}

			@Override
			public String toString(Status arg0, int arg1) {
				return arg0.getText();
			}

			@Override
			public String toVariableNameString(Status arg0) {
				return arg0.getText();
			}
		   
		}));
		Skript.registerCondition(CondTwitterFollow.class, "%twitterer% follow[s] %twitterer%");
		Skript.registerCondition(CondIsPossiblySensitive.class, "%tweet% is possibly sensitive");
		Skript.registerCondition(CondIsRetweeted.class, "%tweet% is retweeted");
		Skript.registerCondition(CondIsRetweetedByMe.class, "%tweet% is retweeted by me");
		Skript.registerCondition(CondTwitterDiscoverableByEmail.class, "twitter account is discoverable by [e]mail");
		Skript.registerCondition(CondTwitterGeoEnabled.class, "twitter account is geo enabled");
		Skript.registerCondition(CondTwitterDiscoverableByEmail.class, "%tweet% is favorited");
		Skript.registerCondition(CondTwitterAddonConnected.class, "addon is connected to twitter");
		Skript.registerCondition(CondTwittererExists.class, "(twitter user|twitterer) %string% exist[s]");
		Skript.registerEffect(EffPostTweet.class, "tweet %string%");
		Skript.registerEffect(EffRemoveTweet.class, "destroy %tweet%");
		Skript.registerEffect(EffTwitterBlockUser.class, "block %twitterer%");
		Skript.registerEffect(EffTwitterConnect.class, "twitter connect with consumer key %string%, consumer secret %string%, access token %string%( and|,) secret token %string%", "twitter debug connect with consumer key %string%, consumer secret %string%, access token %string%( and|,) secret token %string%");
		Skript.registerEffect(EffTwitterCreateFavorite.class, "favorite %tweet%");
		Skript.registerEffect(EffTwitterDeleteDirectMessage.class, "delete (direct message|dm) with id %long%");
		Skript.registerEffect(EffTwitterCreateFavorite.class, "unfavorite %tweet%");
		Skript.registerEffect(EffTwitterFollow.class, "follow %twitterer%");
		Skript.registerEffect(EffTwitterFollow.class, "report %twitterer% for spam[ing]");
		Skript.registerEffect(EffTwitterRetweet.class, "retweet %tweet%");
		Skript.registerEffect(EffTwitterUnblockUser.class, "(un|de)block %twitterer%");
		Skript.registerEffect(EffTwitterUnfollow.class, "unfollow %twitterer%");
		Skript.registerExpression(ExprSearchTweets.class, Status.class, ExpressionType.PROPERTY, "search tweet[s] (for|with query) %string%");
		Skript.registerExpression(ExprStatusUser.class, User.class, ExpressionType.PROPERTY, "(author|twitterer) of %tweet%");
		Skript.registerExpression(ExprTwitterAccessLevel.class, Integer.class, ExpressionType.PROPERTY, "twitter access level");
		Skript.registerExpression(ExprTwitterFavoriteCount.class, Integer.class, ExpressionType.PROPERTY, "my twitter favorite count", "twitter favorite count of %twitterer%");
		Skript.registerExpression(ExprTwitterFollowersCount.class, Integer.class, ExpressionType.PROPERTY, "my twitter follower[s] count", "twitter follower[s] count of %twitterer%");
		Skript.registerExpression(ExprTwitterFollowersList.class, User.class, ExpressionType.PROPERTY, "my [twitter ]follower[s]", "[twitter ]follower[s] of %twitterer%");
		Skript.registerExpression(ExprTwitterFollowersCount.class, Integer.class, ExpressionType.PROPERTY, "my twitter friend[s] count", "twitter friend[s] count of %twitterer%");
		Skript.registerExpression(ExprTwitterFriendsList.class, User.class, ExpressionType.PROPERTY, "my twitter friend[s]", "twitter friend[s] of %twitterer%");
		Skript.registerExpression(ExprTwitterGetDirectMessage.class, String.class, ExpressionType.PROPERTY, "(direct message|dm) with id %long%");
		Skript.registerExpression(ExprTwitterGetDirectMessages.class, String.class, ExpressionType.PROPERTY, "direct messages");
		Skript.registerExpression(ExprTwitterGetFavorites.class, Status.class, ExpressionType.PROPERTY, "favo[u]rites tweets");
		Skript.registerExpression(ExprTwitterHomeTimeline.class, Status.class, ExpressionType.PROPERTY, "home timeline");
		Skript.registerExpression(ExprTwitterIncomingFriendships.class, Long.class, ExpressionType.PROPERTY, "incoming friendship[s]");
		Skript.registerExpression(ExprTwitterLanguage.class, String.class, ExpressionType.PROPERTY, "twitter language");
		Skript.registerExpression(ExprTwitterMentionTimeline.class, Status.class, ExpressionType.PROPERTY, "mention timeline");
		Skript.registerExpression(ExprTwitterRateLimit.class, Integer.class, ExpressionType.PROPERTY, "twitter rate limit");
		Skript.registerExpression(ExprTwitterRateLimitRemaining.class, Integer.class, ExpressionType.PROPERTY, "twitter rate limit remaining");
		Skript.registerExpression(ExprTwitterRateLimitResetTime.class, Integer.class, ExpressionType.PROPERTY, "twitter rate limit reset time");
		Skript.registerExpression(ExprTwitterRateLimitResetTime.class, Integer.class, ExpressionType.PROPERTY, "twitter time until reset rate limit");
		Skript.registerExpression(ExprTwitterRetweets.class, Status.class, ExpressionType.PROPERTY, "retweet[s] of %tweet%");
		Skript.registerExpression(ExprTwitterSearchUser.class, User.class, ExpressionType.PROPERTY, "search user[s] (for|with query) %string%");
		Skript.registerExpression(ExprTwitterSleepEndTime.class, String.class, ExpressionType.PROPERTY, "twitter sleep end time");
		Skript.registerExpression(ExprTwitterSleepStartTime.class, String.class, ExpressionType.PROPERTY, "twitter sleep start time");
		Skript.registerExpression(ExprTwitterStatusByID.class, Status.class, ExpressionType.PROPERTY, "tweet with id %long%");
		Skript.registerExpression(ExprTwitterStatusFavoriteCount.class, Integer.class, ExpressionType.PROPERTY, "favorite[s] count of %tweet%");
		Skript.registerExpression(ExprTwitterStatusRetweetsCount.class, Integer.class, ExpressionType.PROPERTY, "retweet[s] count of %tweet%");
		Skript.registerExpression(ExprTwitterStatusText.class, String.class, ExpressionType.PROPERTY, "text of %tweet%");
		Skript.registerExpression(ExprTwitterUserByID.class, User.class, ExpressionType.PROPERTY, "user with id %long%");
		Skript.registerExpression(ExprTwitterUserByScreenName.class, User.class, ExpressionType.PROPERTY, "user with [screen ]name %string%");
		Skript.registerExpression(ExprTwitterUserDescription.class, String.class, ExpressionType.PROPERTY, "description of %twitterer%");
		Skript.registerExpression(ExprTwitterUserID.class, Long.class, ExpressionType.PROPERTY, "id of %twitterer%");
		Skript.registerExpression(ExprTwitterUserName.class, String.class, ExpressionType.PROPERTY, "(username|screen name) of %twitterer%");
		Skript.registerExpression(ExprTwitterUsersBlocked.class, User.class, ExpressionType.PROPERTY, "twitter blocked users");
		Skript.registerExpression(ExprTwitterUserStatusCount.class, Integer.class, ExpressionType.PROPERTY, "status count of %twitterer%");
		Skript.registerExpression(ExprTwitterUserTimeline.class, Status.class, ExpressionType.PROPERTY, "timeline of %twitterer%");
		Skript.registerExpression(ExprTwitterOutgoingFriendships.class, Long.class, ExpressionType.PROPERTY, "outgoing friendship[s]");
		Skript.registerEvent("Direct Message Event", SimpleEvent.class, EvtOnDirectMessage.class, "twitter[ direct] message");
		Skript.registerExpression(ExprTwitterStatusID.class, Long.class, ExpressionType.PROPERTY, "id of %tweet%");
		Skript.registerEvent("Direct Message Event", SimpleEvent.class, EvtOnDirectMessage.class, "twitter[ direct] message");
		Skript.registerExpression(ExprTwitterSelfUser.class, User.class, ExpressionType.PROPERTY, "my twitter account");
		EventValues.registerEventValue(EvtOnDirectMessage.class, String.class, new Getter<String, EvtOnDirectMessage>() {
			public String get(EvtOnDirectMessage e) {
				return e.getDirectMessage();
			}
		}, 0);
		Skript.registerEvent("Twitter Favorite Event", SimpleEvent.class, EvtOnFavorite.class, "tweet favorite[d]");
		EventValues.registerEventValue(EvtOnFavorite.class, Status.class, new Getter<Status, EvtOnFavorite>() {
			public Status get(EvtOnFavorite e) {
				return e.getStatus();
			}
		}, 0);
		EventValues.registerEventValue(EvtOnFavorite.class, User.class, new Getter<User, EvtOnFavorite>() {
			public User get(EvtOnFavorite e) {
				return e.getSource();
			}
		}, 0);	
		Skript.registerEvent("Twitter Unfavorite Event", SimpleEvent.class, EvtOnUnfavorite.class, "tweet unfavorite[d]");
		EventValues.registerEventValue(EvtOnUnfavorite.class, Status.class, new Getter<Status, EvtOnUnfavorite>() {
			public Status get(EvtOnUnfavorite e) {
				return e.getStatus();
			}
		}, 0);
		EventValues.registerEventValue(EvtOnUnfavorite.class, User.class, new Getter<User, EvtOnUnfavorite>() {
			public User get(EvtOnUnfavorite e) {
				return e.getSource();
			}
		}, 0);
		Skript.registerEvent("Twitter Favorite Retweet Event", SimpleEvent.class, EvtOnFavoriteRetweet.class, "retweet favorite[d]");
		EventValues.registerEventValue(EvtOnFavoriteRetweet.class, Status.class, new Getter<Status, EvtOnFavoriteRetweet>() {
			public Status get(EvtOnFavoriteRetweet e) {
				return e.getStatus();
			}
		}, 0);
		EventValues.registerEventValue(EvtOnFavoriteRetweet.class, User.class, new Getter<User, EvtOnFavoriteRetweet>() {
			public User get(EvtOnFavoriteRetweet e) {
				return e.getSource();
			}
		}, 0);
		Skript.registerEvent("Twitter Follow Event", SimpleEvent.class, EvtOnFollow.class, "follow");
		EventValues.registerEventValue(EvtOnFollow.class, User.class, new Getter<User, EvtOnFollow>() {
			public User get(EvtOnFollow e) {
				return e.getSource();
			}
		}, 0);
		/*Skript.registerEvent("Twitter Unfollow Event", SimpleEvent.class, EvtOnUnfollow.class, "unfollow");
		EventValues.registerEventValue(EvtOnUnfollow.class, User.class, new Getter<User, EvtOnUnfollow>() {
			public User get(EvtOnUnfollow e) {
				return e.getSource();
			}
		}, 0);*/
		Skript.registerEvent("Twitter Quoted Status Event", SimpleEvent.class, EvtOnQuotedTweet.class, "tweet quote[d]");
		EventValues.registerEventValue(EvtOnQuotedTweet.class, Status.class, new Getter<Status, EvtOnQuotedTweet>() {
			public Status get(EvtOnQuotedTweet e) {
				return e.getStatus();
			}
		}, 0);
		EventValues.registerEventValue(EvtOnQuotedTweet.class, User.class, new Getter<User, EvtOnQuotedTweet>() {
			public User get(EvtOnQuotedTweet e) {
				return e.getSource();
			}
		}, 0);
		Skript.registerEvent("Twitter Receive Tweet Event", SimpleEvent.class, EvtOnReceiveStatus.class, "receive tweet");
		EventValues.registerEventValue(EvtOnReceiveStatus.class, Status.class, new Getter<Status, EvtOnReceiveStatus>() {
			public Status get(EvtOnReceiveStatus e) {
				return e.getStatus();
			}
		}, 0);
		Skript.registerEvent("Twitter Retweeted Retweet Event", SimpleEvent.class, EvtOnRetweetedRetweet.class, "retweet retweet[ed[ tweet]]");
		EventValues.registerEventValue(EvtOnRetweetedRetweet.class, Status.class, new Getter<Status, EvtOnRetweetedRetweet>() {
			public Status get(EvtOnRetweetedRetweet e) {
				return e.getStatus();
			}
		}, 0);
		EventValues.registerEventValue(EvtOnRetweetedRetweet.class, User.class, new Getter<User, EvtOnRetweetedRetweet>() {
			public User get(EvtOnRetweetedRetweet e) {
				return e.getSource();
			}
		}, 0);
	}
	
	public static void registerEvents(boolean debug, String key, String secret, String access_token, String access_token_secret) {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(debug)
		  .setOAuthConsumerKey(key)
		  .setOAuthConsumerSecret(secret)
		  .setOAuthAccessToken(access_token)
		  .setOAuthAccessTokenSecret(access_token_secret);
		 TwitterStream ts = new TwitterStreamFactory(cb.build()).getInstance();
		 UserStreamListener userStreamListener = new UserStreamListener() {

			@Override
			public void onDeletionNotice(StatusDeletionNotice arg0) {
			}
			@Override
			public void onScrubGeo(long arg0, long arg1) {
			}
			@Override
			public void onStallWarning(StallWarning arg0) {
			}
			@Override
			public void onStatus(Status arg0) {
				TwitterSK.getInstance().getServer().getPluginManager().callEvent(new EvtOnReceiveStatus(arg0));
			}
			@Override
			public void onTrackLimitationNotice(int arg0) {
			}
			@Override
			public void onException(Exception arg0) {
			}
			@Override
			public void onBlock(User arg0, User arg1) {
			}
			@Override
			public void onDeletionNotice(long arg0, long arg1) {
			}

			@Override
			public void onDirectMessage(DirectMessage arg0) {
				TwitterSK.getInstance().getServer().getPluginManager().callEvent(new EvtOnDirectMessage(arg0));
			}

			@Override
			public void onFavorite(User arg0, User arg1, Status arg2) {
				try {
					TwitterSK.getInstance().getServer().getPluginManager().callEvent(new EvtOnFavorite(arg0, arg1, arg2));
				} catch (IllegalStateException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFavoritedRetweet(User arg0, User arg1, Status arg2) {
				try {
					TwitterSK.getInstance().getServer().getPluginManager().callEvent(new EvtOnFavoriteRetweet(arg0, arg1, arg2));
				} catch (IllegalStateException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFollow(User arg0, User arg1) {
				try {
					TwitterSK.getInstance().getServer().getPluginManager().callEvent(new EvtOnFollow(arg0));
				} catch (IllegalStateException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFriendList(long[] arg0) {
			}

			@Override
			public void onQuotedTweet(User arg0, User arg1, Status arg2) {
				try {
					TwitterSK.getInstance().getServer().getPluginManager().callEvent(new EvtOnQuotedTweet(arg0, arg1, arg2));
				} catch (IllegalStateException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onRetweetedRetweet(User arg0, User arg1, Status arg2) {
				try {
					TwitterSK.getInstance().getServer().getPluginManager().callEvent(new EvtOnRetweetedRetweet(arg0, arg1, arg2));
				} catch (IllegalStateException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onUnblock(User arg0, User arg1) {
			}

			@Override
			public void onUnfavorite(User arg0, User arg1, Status arg2) {
				try {
					TwitterSK.getInstance().getServer().getPluginManager().callEvent(new EvtOnUnfavorite(arg0, arg1, arg2));
				} catch (IllegalStateException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onUnfollow(User arg0, User arg1) {
				try {
					TwitterSK.getInstance().getServer().getPluginManager().callEvent(new EvtOnUnfollow(arg0));
				} catch (IllegalStateException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onUserDeletion(long arg0) {
			}
			@Override
			public void onUserListCreation(User arg0, UserList arg1) {
			}
			@Override
			public void onUserListDeletion(User arg0, UserList arg1) {
			}
			@Override
			public void onUserListMemberAddition(User arg0, User arg1, UserList arg2) {
			}
			@Override
			public void onUserListMemberDeletion(User arg0, User arg1, UserList arg2) {
			}
			@Override
			public void onUserListSubscription(User arg0, User arg1, UserList arg2) {
			}
			@Override
			public void onUserListUnsubscription(User arg0, User arg1, UserList arg2) {
			}
			@Override
			public void onUserListUpdate(User arg0, UserList arg1) {
			}
			@Override
			public void onUserProfileUpdate(User arg0) {
			}
			@Override
			public void onUserSuspension(long arg0) {
			}				
		 };
		 ts.addListener(userStreamListener);
		 ts.user();
		 twitterStream = ts;
	}
	
	public static TwitterSK getInstance() {
		return TwitterSK.instance;
	}
}

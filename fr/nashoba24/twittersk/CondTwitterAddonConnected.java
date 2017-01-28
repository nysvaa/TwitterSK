package fr.nashoba24.twittersk;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class CondTwitterAddonConnected extends Condition {

	@Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kl, ParseResult pr) {
        return true;
    }

    @Override
    public String toString(@Nullable Event e, boolean b) {
        return "is connected to twitter";
    }

    @Override
    public boolean check(Event e) {
    	return TwitterSK.tf!=null;
    }

}
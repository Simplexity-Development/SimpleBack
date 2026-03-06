package simplexity.simpleback.handlers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Location;
import simplexity.simpleback.SimpleBack;
import simplexity.simpleback.config.LocaleMessage;

public class MessageHandler {

    private static final MiniMessage miniMessage = SimpleBack.getMiniMessage();

    public static Component getParsedLocationMessage(LocaleMessage localeMessage, Location location) {
        Component xLocation = miniMessage.deserialize(LocaleMessage.INSERT_X_LOC.getMessage(),
                Placeholder.unparsed("value", String.valueOf(location.getBlockX())));
        Component yLocation = miniMessage.deserialize(LocaleMessage.INSERT_Y_LOC.getMessage(),
                Placeholder.unparsed("value", String.valueOf(location.getBlockY())));
        Component zLocation = miniMessage.deserialize(LocaleMessage.INSERT_Z_LOC.getMessage(),
                Placeholder.unparsed("value", String.valueOf(location.getBlockZ())));
        Component worldName = miniMessage.deserialize(LocaleMessage.INSERT_WORLD.getMessage(),
                Placeholder.unparsed("value", location.getWorld().getName()));
        return miniMessage.deserialize(localeMessage.getMessage(),
                Placeholder.component("x-loc", xLocation),
                Placeholder.component("y-loc", yLocation),
                Placeholder.component("z-loc", zLocation),
                Placeholder.component("world", worldName));
    }

    public static Component getParsedTimeMessage(LocaleMessage localeMessage, long millis) {
        long seconds = (millis / 1000) % 60;
        long minutes = (millis / (1000 * 60)) % 60;
        long hours = millis / (1000 * 60 * 60);
        Component hoursComponent = Component.empty();
        Component minutesComponent = Component.empty();
        Component secondsComponent = Component.empty();
        if (hours > 0) hoursComponent = miniMessage.deserialize(
                LocaleMessage.INSERT_HOUR.getMessage(),
                Placeholder.unparsed("hour", String.valueOf(hours)));
        if (minutes > 0) minutesComponent = miniMessage.deserialize(
                LocaleMessage.INSERT_MINUTE.getMessage(),
                Placeholder.unparsed("minute", String.valueOf(minutes)));
        if (seconds > 0) secondsComponent = miniMessage.deserialize(
                LocaleMessage.INSERT_SECOND.getMessage(),
                Placeholder.unparsed("second", String.valueOf(seconds)));
        return miniMessage.deserialize(localeMessage.getMessage(),
                Placeholder.component("hour", hoursComponent),
                Placeholder.component("minute", minutesComponent),
                Placeholder.component("second", secondsComponent));
    }
}

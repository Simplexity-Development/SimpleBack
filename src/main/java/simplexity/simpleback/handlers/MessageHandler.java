package simplexity.simpleback.handlers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Location;
import simplexity.simpleback.SimpleBack;
import simplexity.simpleback.config.Message;

public class MessageHandler {

    private static final MiniMessage miniMessage = SimpleBack.getMiniMessage();

    public static Component getParsedLocationMessage(Message message, Location location) {
        Component xLocation = miniMessage.deserialize(Message.INSERT_X_LOC.getMessage(),
                Placeholder.unparsed("value", String.valueOf(location.getBlockX())));
        Component yLocation = miniMessage.deserialize(Message.INSERT_Y_LOC.getMessage(),
                Placeholder.unparsed("value", String.valueOf(location.getBlockY())));
        Component zLocation = miniMessage.deserialize(Message.INSERT_Z_LOC.getMessage(),
                Placeholder.unparsed("value", String.valueOf(location.getBlockZ())));
        Component worldName = miniMessage.deserialize(Message.INSERT_WORLD.getMessage(),
                Placeholder.unparsed("value", location.getWorld().getName()));
        return miniMessage.deserialize(message.getMessage(),
                Placeholder.component("x-loc", xLocation),
                Placeholder.component("y-loc", yLocation),
                Placeholder.component("z-loc", zLocation),
                Placeholder.component("world", worldName));
    }

    public static Component getParsedTimeMessage(Message message, long millis) {
        long seconds = (millis / 1000) % 60;
        long minutes = (millis / (1000 * 60)) % 60;
        long hours = millis / (1000 * 60 * 60);
        Component hoursComponent = Component.empty();
        Component minutesComponent = Component.empty();
        Component secondsComponent = Component.empty();
        if (hours > 0) hoursComponent = miniMessage.deserialize(
                Message.INSERT_HOUR.getMessage(),
                Placeholder.unparsed("hour", String.valueOf(hours)));
        if (minutes > 0) minutesComponent = miniMessage.deserialize(
                Message.INSERT_MINUTE.getMessage(),
                Placeholder.unparsed("minute", String.valueOf(minutes)));
        if (seconds > 0) secondsComponent = miniMessage.deserialize(
                Message.INSERT_SECOND.getMessage(),
                Placeholder.unparsed("second", String.valueOf(seconds)));
        return miniMessage.deserialize(message.getMessage(),
                Placeholder.component("hour", hoursComponent),
                Placeholder.component("minute", minutesComponent),
                Placeholder.component("second", secondsComponent));
    }
}

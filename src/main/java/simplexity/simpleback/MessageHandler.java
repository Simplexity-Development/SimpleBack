package simplexity.simpleback;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Location;

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
}

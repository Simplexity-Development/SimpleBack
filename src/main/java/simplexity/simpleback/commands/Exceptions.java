package simplexity.simpleback.commands;

import com.mojang.brigadier.Message;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import io.papermc.paper.command.brigadier.MessageComponentSerializer;
import net.kyori.adventure.text.minimessage.MiniMessage;
import simplexity.simpleback.SimpleBack;
import simplexity.simpleback.config.LocaleMessage;
import simplexity.simpleback.handlers.MessageHandler;

@SuppressWarnings("UnstableApiUsage")
public class Exceptions {
    private static final MiniMessage miniMessage = SimpleBack.getMiniMessage();

    public static final SimpleCommandExceptionType NO_BACK_LOCATIONS = new SimpleCommandExceptionType(
            parseMessage(LocaleMessage.ERROR_NO_BACK_LOCATIONS)
    );

    public static final SimpleCommandExceptionType BLACKLISTED_WORLD = new SimpleCommandExceptionType(
            parseMessage(LocaleMessage.ERROR_BLACKLISTED_WORLD)
    );

    public static final SimpleCommandExceptionType PLAYER_NOT_FOUND = new SimpleCommandExceptionType(
            parseMessage(LocaleMessage.ERROR_PLAYER_INVALID)
    );

    public static final SimpleCommandExceptionType MUST_PROVIDE_PLAYER = new SimpleCommandExceptionType(
            parseMessage(LocaleMessage.ERROR_MUST_PROVIDE_PLAYER)
    );

    public static final DynamicCommandExceptionType COOLDOWN_NOT_FINISHED = new DynamicCommandExceptionType(time -> {
        return MessageComponentSerializer.message().serialize(
                MessageHandler.getParsedTimeMessage(LocaleMessage.ERROR_COOLDOWN, (long) time)
        );
    });

    private static Message parseMessage(LocaleMessage message){
        return MessageComponentSerializer.message().serialize(
                miniMessage.deserialize(message.getMessage())
        );
    }
}

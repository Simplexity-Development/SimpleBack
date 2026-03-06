package simplexity.simpleback.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver;
import org.bukkit.entity.Player;
import simplexity.simpleback.SimpleBack;
import simplexity.simpleback.config.ConfigHandler;
import simplexity.simpleback.handlers.CooldownHandler;
import simplexity.simpleback.handlers.BackPermission;
import simplexity.simpleback.handlers.TeleportHandler;

import java.util.List;

@SuppressWarnings({"UnstableApiUsage", "SameReturnValue"})
public class Back {

    public static LiteralArgumentBuilder<CommandSourceStack> createCommand() {
        return Commands.literal("back")
                .requires(Back::canExecute)
                .executes(Back::execute)
                .then(Commands.argument("player", ArgumentTypes.player())
                        .requires(Back::canExecuteWithArg)
                        .executes(Back::executeWithArg));
    }


    private static int execute(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        if (!(ctx.getSource().getSender() instanceof Player player)) throw Exceptions.MUST_PROVIDE_PLAYER.create();
        if (!SimpleBack.getInstance().getBackLocations().containsKey(player.getUniqueId()))
            throw Exceptions.NO_BACK_LOCATIONS.create();
        if (!CooldownHandler.cooldownExpired(player))
            throw Exceptions.COOLDOWN_NOT_FINISHED.create(CooldownHandler.getLeftoverCooldownTime(player.getUniqueId()));
        if (TeleportHandler.blacklistedWorld(player)) throw Exceptions.BLACKLISTED_WORLD.create();
        if (ConfigHandler.getInstance().isTeleportDelay()) {
            TeleportHandler.delayTeleport(player);
            return Command.SINGLE_SUCCESS;
        }
        TeleportHandler.teleport(player);
        return Command.SINGLE_SUCCESS;
    }

    private static int executeWithArg(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        PlayerSelectorArgumentResolver argResolver = ctx.getArgument("player", PlayerSelectorArgumentResolver.class);
        List<Player> playerList = argResolver.resolve(ctx.getSource());
        if (playerList.isEmpty()) throw Exceptions.PLAYER_NOT_FOUND.create();
        Player player = playerList.getFirst();
        if (!SimpleBack.getInstance().getBackLocations().containsKey(player.getUniqueId()))
            throw Exceptions.NO_BACK_LOCATIONS.create();
        if (!CooldownHandler.cooldownExpired(player))
            throw Exceptions.COOLDOWN_NOT_FINISHED.create(CooldownHandler.getLeftoverCooldownTime(player.getUniqueId()));
        if (TeleportHandler.blacklistedWorld(player)) throw Exceptions.BLACKLISTED_WORLD.create();
        if (ConfigHandler.getInstance().isTeleportDelay()) {
            TeleportHandler.delayTeleport(player);
            return Command.SINGLE_SUCCESS;
        }
        TeleportHandler.teleport(player);
        return Command.SINGLE_SUCCESS;
    }

    private static boolean canExecute(CommandSourceStack css) {
        if (css.getSender() instanceof Player player) {
            return player.hasPermission(BackPermission.BACK_USE.getPermission());
        }
        return css.getSender().hasPermission(BackPermission.FORCE_BACK.getPermission());
    }

    private static boolean canExecuteWithArg(CommandSourceStack css) {
        if (css.getSender() instanceof Player) return false;
        return css.getSender().hasPermission(BackPermission.FORCE_BACK.getPermission());
    }
}

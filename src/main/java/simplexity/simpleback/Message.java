package simplexity.simpleback;

public enum Message {
    TELEPORT_PLEASE_WAIT("message.teleport-delay", "<green>Teleporting! Please wait <value> seconds!</green>"),
    TELEPORT_SUCCESSFUL("message.teleport-successful", "<green>Successfully teleported to <x-loc>, <y-loc>, <z-loc> in <world>!"),
    TELEPORT_CANCELLED("message.teleport-cancelled", "<red>Teleport Cancelled</red>"),
    INSERT_X_LOC("insert.x-location", "<yellow><value>x</yellow>"),
    INSERT_Y_LOC("insert.y-location", "<yellow><value>y</yellow>"),
    INSERT_Z_LOC("insert.z-location", "<yellow><value>z</yellow>"),
    INSERT_WORLD("insert.world", "<yellow><value></yellow>"),
    INSERT_HOUR("insert.hour", "<yellow><hour>H </yellow>"),
    INSERT_MINUTE("insert.minute", "<yellow><minute>m </yellow>"),
    INSERT_SECOND("insert.second", "<yellow><second>s</yellow>"),
    ERROR_MUST_BE_PLAYER("error.must-be-player", "<red>Sorry, you must be a player to run this command!</red>"),
    ERROR_NO_BACK_LOCATIONS("error.no-back-locations", "<gray>No back locations found!</gray>"),
    ERROR_COOLDOWN("error.command-cooldown", "<gray>Sorry, that command is on cooldown for: [<hour><minute><second>]!</gray>"),
    PLUGIN_RELOADED("plugin.reloaded", "<gold>SimpleBack plugin reloaded</gold>"),
    ;
    private final String path;
    private String message;

    Message(String path, String message) {
        this.path = path;
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

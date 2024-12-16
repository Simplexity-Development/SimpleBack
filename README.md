## Commands

### `/back`

- Teleports you to the last location you teleported from
- Permission: `back.use`

### `/backreload`

- Reloads the plugin configuration and locale
- Permission: `back.reload`

## Permissions

| Permission             | What it do                                                     |
|------------------------|----------------------------------------------------------------|
| `back.use`             | Use the back command                                           |
| `back.bypass`          | Bypass restrictions set in the config                          |
| `back.bypass.delay`    | Bypass the teleport delay                                      |
| `back.bypass.movement` | Bypass the requirement to stay still while waiting to teleport |
| `back.bypass.worlds`   | Bypass the world blacklist for the back command                |
| `back.bypass.cooldown` | Bypass the cooldown for the back command                       |

----
# Config
### Teleport Causes
A list of teleport causes that will be tracked for the back command. Default is `PLUGIN` and `COMMAND` but any causes in [this list](https://jd.papermc.io/paper/1.21.4/org/bukkit/event/player/PlayerTeleportEvent.TeleportCause.html) are allowed

### Teleport Delay

- `enabled`
  - Boolean
    - Default: `false`
  - Causes a delay between when the player sends the command and when the teleport is executed.
- `delay-in-seconds`
  - Integer
    - Default: `5`
  - The amount of time in seconds that the player will need to wait
- `cancel-tp-on-move`
  - Boolean
    - Default: `true`
  - Cancels the pending teleport if the player moves above the configured 'buffer distance' during the delay time
- `buffer-distance-in-blocks`
  - Double
    - Default: `0.5`
  - The space in blocks that a player is allowed to move before their pending teleport is cancelled. 

### Teleport Cooldown 
- `enabled`
  - Boolean
    - Default: `false`
  - Requires a cooldown before users can use `/back` again. This is after the teleport is successful, and is separate from the delay.
- `length-in-seconds`
  - Integer
    - Default: `30`
  - The amount of time a player must wait between using `/back`

### ETC

- `cache-clear-time-in-seconds`
  - Integer
    - Default: `30`
  - The amount of time that it takes before cache clears for someone after they leave the server. Keeping the cache for a short amount of time prevents users from losing their `/back` location when they need to relog quickly to fix something, and prevents people from trying to avoid the cooldowns.
- `blacklisted-worlds`
  - String list
    - Default: `null`
  - List of worlds players should not be able to use `/back` to get into. Useful for event worlds or other worlds that should only be entered through specific means.

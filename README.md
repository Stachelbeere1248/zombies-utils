# zombies-utils

##### Download the latest release [here](https://github.com/Stachelbeere1248/zombies-utils/releases/latest)

Hello, I am currently working on this mod. More features will come. For now it has:

- An accurate timer + Automatic splitting
- Tracking of splits & segment PBs (with custom categories)
- SLA hud
- A chat macro
- Spawn-times HUD: visual, auditory, RL-mode
- Player Visibility

#### Disclaimers

- If you are using a Hypixel language other than the selected one the mod may not work entirely. Check config.

## For Users

The timer automatically splits every round. The PB/Segment recorder automatically distinguishes maps and difficulties,
but not player count.

### Config

- `Language`: The selected Hypixel language. Currently supports EN,FR,DE.
- ###### Timer:
    - `Default Category`: The record-category to be selected when starting the game.
    - `PB Announcements`: Whether to show **\*\*\*NEW PERSONAL BEST\*\*\*** on PB in summaries.
- ###### SST:
    - `Enabled`: Enables / disables this feature.
    - `Auditory`: A List of tick offsets that a sound should be played. Default (-40, -20, 0) means 2s and 1s in
      advance, as well as on spawn.
    - `RL pre-timing`: During RL mode, how much SST times sohuld be offsetted. Defaults to 1.4s earlier. Affects HUD as
      well as auditory.
    - `Truncate`: Whether to show passed rounds in the HUD.
- ###### SLA:
    - `Enabled`: Whether the SLA HUD should automatically be shown when starting a game.
    - `Truncate`: Whether inactive windows and rooms should be shown.
- ###### Player Visibility:
    - `Enabled`: Whether to enable PlayerVisibility by default.
    - `Range`: The range within which players are hidden while enabled.
- `Macro Message`: The Message to be sent when pressing the Chat Macro Key. Do NOT use "§" as symbol.
- `CPS Counter`: A simple CPS Counter which shows the amount of clicks within the last 20 gameticks.

### Commands

- /category \<name> - Switches to the category called name. All recorded times are bound to its category. Tabcomplete
  suggests already existing categories, but you can insert a new (clean) one as well.
    - Examples:
        - /category pistol_only
        - /category no_doors_solo
    - note: you do NOT need to make your own categories to seperate difficulties or map
- /sla \<off|map|quick|rotate|mirror|offset>
    - /sla off - Disables the SLA hud
    - /sla map \<de|bb|aa|p> - forcefully set the map
    - /sla quick \<mogi_a|ghxula|ghxula-garden>
    - useless for most players:
        - /sla rotate - rotates all windows around the axis (0,y,0)
        - /sla mirror \<x|z> - mirrors all windows along the plane (0,y,z) or (x,y,0)
        - /sla offset \<x> \<y> \<z> - set an offset, allowing you to use sla on map-recreations, such as housings
- /zombiesutils \<timer>
    - /zombiesutils timer \<kill|split>
        - /zombiesutils timer kill - Stops the running timer completely
        - /zombiesutils timer split \<round> - Splits as if \<round> was passed, not recommended to use as it might
          create impossible PBs.
- /qz \<de|bb|aa|p> - sends you to a new game of Dead End, Bad Blood, Alien Arcadium or Prison

### Hotkeys

- Chat Macro: Sends the message specified in the config.
- RL Mode: Toggles usage of the rocket launcher mode spawn-time offset.
- Player Visibility: Toggles whether to show players that are within a 4 block radius.

### Extra

- Managing split-categories: In your game directory (aka `.minecraft`) is a folder called `zombies` which contains the
  folder `splits`. You can simply rename or delete the folders inside `splits`, they represent your categories. You can
  also edit your splits, the data is stored as a list of ticks inside the `MAP_DIFFICULTY.times` files, a simple text
  editor (such as Notepad on Windows) should be able to edit it (UTF-16 encoded text). The other subfolder, runs, logs
  all the splits for every run you play.

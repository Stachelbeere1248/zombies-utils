# zombies-utils
##### Download latest release: [v1.2.4_pre5](https://github.com/Stachelbeere1248/zombies-utils/releases/tag/v1.2.4_pre5).
Hello, I am currently working on this mod. More features will come. For now it has:
- An accurate timer + Automatic splitting
- Tracking of splits & segment PBs (with custom categories)
- SLA hud
- 1 chat macro
- Spawntime (no aa colors yet, but Rocket Launcher mode)
#### Disclaimers
- If you are using a Hypixel language other than english the mod may not work entirely. Use /lang en.
## For Users
The timer automatically splits every round. The Personal-Best-recorder automatically distinguishes between maps and difficulties.
### Config
- SLA Launcher: if on, sets and displays SLA when the mod starts the timer
- short SLA: if on, hides inactive windows & rooms from the SLA display
- chat macro: the text to be sent when pressing the respective keybind (-> control settings), DO NOT use "§" as symbol
- default category name: the category to be tracked until /category is used
- Rocket Launcher offset: The amount of ticks to be added to wave-spawn times during RL mode
- passed Waves: Whether to show or hide spawn-times for previous waves.
### Commands
- /category \<name> - Switches to the category called name. All recorded times are bound to its category. Tabcomplete suggests already existing categories, but you can insert a new (clean) one as well.
  - Examples:
    - /category pistol_only
    - /category no_doors_solo
  - note: you do NOT need to make your own categories to seperate difficulties or map
- /sla \<off|map|quick|rotate|mirror|offset>
  - /sla off - Disables the SLA hud
  - /sla map \<de|bb|aa> - forcefully set the map
  - /sla quick \<mogi_a|ghxula|ghxula-garden>
  - /sla rotate - rotates all windows around the axis (0,y,0)
  - /sla mirror \<x|z> - mirrors all windows along the plane (0,y,z) or (x,y,0)
  - /sla offset \<x> \<y> \<z> - set an offset, allowing you to use sla on map-recreations, such as housings
- /zombiesutils \<timer>
  - /zombiesutils timer \<kill|split>
    - /zombiesutils timer kill - Stops the running timer completely
    - /zombiesutils timer split \<round> - Splits as if \<round> was passed, not recommended to use as it might create impossible PBs
- /qz \<de|bb|aa> - sends you to a new game of Dead End, Bad Blood or Alien Arcadium
### Hotkeys
- Chat Macro: sends the message specified in the config
- RL Mode: Toggle usage of the rocket launcher mode spawn-time offset.
### Extra
- Managing split-categories: In your minecraft folder is a folder called "zombies" which contains the folder "splits". You can simply rename or delete the folders inside "splits". You can also edit your splits, the data is stored in ticks inside the MAP_DIFFICULTY.times files, a simple text editor should be able to edit it (UTF-16 encoded text).

This Overfiew file is currently outdated. I will update within 1-2 days.
# zombies-utils
Hello, I am currently working on this mod. More features will come. For now it has:
- An accurate timer
- Automatic splitting
- Tracking of splits & segment PBs (with custom categories)
- SLA hud
- 1 chat macro
#### Disclaimers
- If you are using a Hypixel language other than english the mod may not work entirely. Use /lang en.
## For Users
The timer automatically splits every round. The Personal-Best-recorder automatically distinguishes between maps and difficulties.
### Config
- SLA Launcher: if on, sets and displays SLA when the mod starts the timer
- short SLA: if on, hides inactive windows & rooms from the SLA display
- chat macro: the text to be sent when pressing the respective keybind (-> control settings), DO NOT use "§" as symbol
- default category name: the category to be tracked until /runCategory is used
### Commands
- /runCategory \<name> - Switches to the category called name. All recorded times are bound to its category. Tabcomplete suggests already existing categories, but you can insert a new (clean) one as well.
  - Examples:
    - /runCategory pistol_only
    - /runCategory casual
  - note: you do NOT need to make your own categories to distinguish difficulties or map
- /sla \<off|map|quick|rotate|mirror|offset>
  - /sla off - Disables the SLA hud
  - /sla map \<de|bb|aa> - forcefully set the map
  - /sla quick \<mogi_a|ghxula|ghxula-garden>
  - /sla rotate - rotates all windows around the axis (0,y,0)
  - /sla mirror \<x|z> - mirrors all windows along the plane (0,y,z) or (x,y,0)
  - /sla offset \<x> \<y> \<z> - set an offset, allowing you to use sla on map-recreations, such as housings
### Extra
- Managing split-categories: In your minecraft folder is a folder called "zombies". You can simply rename or delete the sub-folders.

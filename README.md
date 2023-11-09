# zombies-utils
Disclaimers
- If you are using a Hypixel language other than english the mod may not work entirely. Use /lang en.
## For Users
The Timer automatically splits every round. The Personal-Best-recorder automatically distinguishes between maps and difficulties.
### Commands
- /runCategory \<name> - Switches to the category called name. All recorded times are bound to its category. Tabcomplete suggests already existing categories, but you can insert a new (clean) one as well.
  - Examples:
    - /runCategory pistol_only
    - /runCategory casual
  - default name: general
  - note: you do NOT need to make your own categories to distinguish difficulties or map
- /sla \<off|map|quick|rotate|mirror|offset>
  - /sla toggle - Enables / disables the overlay
    - default state: disabled
  - /sla set \<de|bb|aa> - forcefully set the map
  - /sla quick \<mogi_a|ghxula|ghxula-garden>
  - /sla rotate - rotates all windows around the axis (0,y,0)
  - /sla mirror \<x|z> - mirrors all windows along the plane (0,y,z) or (x,y,0)
  - /sla offset \<x> \<y> \<z> - set an offset, allowing you to use sla on map-recreations, such as housings
### Extra
- Migration of split-category names: In your minecraft folder is a folder called "zombies". You can simply rename the sub-folders.
- Mogi_a housing (quick-command might be released):
  - rotations: 3
  - x mirrored: no
  - y mirrored: no
  - offset: -3 35 -9

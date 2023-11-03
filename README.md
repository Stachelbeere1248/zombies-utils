# zombies-utils
Disclaimers
- SST v1.15.0 by @Seosean might not be compatible. Use an older version if you want to use this mod. Compatiblity might be implemented at a later date.
- If you are using a Hypixel language other than english the mod may not work entirely. Use /lang en.
## For Users
The Timer automatically splits every round. The Personal-Best-recorder automatically distinguishes between maps and difficulties.
### Commands
- /runCategory \<name> - Switches to the category called name. All recorded times are bound to its category. Tabcomplete suggests already existing categories, but you can insert a new (clean) one as well.
  - Examples:
    - /runCategory pistol_only
    - /runCategory casual
  - default name: general
- /sla \<toggle|set|offset>
  - /sla toggle - Enables / disables the overlay
    - default state: disabled
  - /sla set \<de|bb|aa> - forcefully set the map
  - /sla offset \<x> \<y> \<z> - set an offset, allowing you to use sla on map-recreations, such as housings
### Extra
- Migration of split-category names: In your minecraft folder is a folder called "zombies". You can simply rename the sub-folders.

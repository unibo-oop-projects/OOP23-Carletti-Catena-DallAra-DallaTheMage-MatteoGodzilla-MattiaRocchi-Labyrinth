# Labyrinth
Project repository for the development of a videogame version based on the board game "Magic Labyrinth".

## Proposal
Player view variants (to choose):  
+ First person (Hard),
+ Isometric(Very hard),
+ (recommended) From above(Medium).  

## Multiplayer management
+ Local (Magic Labyrinth rogue-like / Classic Game Player VS AI),  
+ Multi-client (Classic game with features).

## Rules
+ Starting point: Standard "Magic Labyrinth" board game.
+ One player can't see the maze tiles until they are "explored" by him or by another player.  
+ Each player must "complete a quest" objective but first they need to find the right object to do it (if they want they can also steal another player's object and leave it the next turn).  
Generic meme example:  
"Goofy must capture the "homing pigeon" but to do so he will need a rifle."

## First idea: Objective briefing cards 
# (Multiplayer, more ironic) 
+ **Objective**: The loan shark  
    "That bastard beat my brother, teach him a lesson!"  
    Requirements:                                       
    (baseball bat)                                     

+ **Objective**: Pimping pigeon  
     "Gastón must understand what borders mean, we must  
     send a message. A clear message."                   
     Requirements:                                       
     (shotgun)  

# (Single player, more epic) 
+ **Objective**: Balhazak
     "The Darkness will come, your people will fall.
      Prepare yourself for the arrival."                   
     Requirements:                                       
     (Chains of Eternal Bond)
 
## Second idea: Inserting special tiles
For example:  
+ A tile that allow one player to rotate a "group" of tiles once chosen the perimeter to rotate (⭯).  
+ A tile that allows you to teleport a player somewhere else (✼).  
+ A tile that contain a musical challenge that gives buffs once completed successfully (🎜).  
+ A tile that once reached shows the surrounding ones with a bigger radius than normal, or scans tiles in cardinal lines until a wall is met
+ A tile that alters any of the player stats randomly
+ A tile called "crawler" that when stepped on crawls in all surrounding tiles using dfs with a fixed depth limit
+ A tile that show a circular perimeter which approximately surrounds the area of ​​the map where the player's objective is.
+ (Single player) Tiles that contains Mobs.
+ (Single player) Tiles that contains chests.
+ (Single player) Boss tile.
+ Etc...

## Player stats
+ Speed: measured in #tiles per turn, dictates how much the player can move in a single turn. All players start at 1, but can be increased over time 
+ Height/Recon: measured in #tiles, is used when calculating the area of tiles that should reveal around the player
* Luck: changes how many tiles around the player will be special ones/won't have walls blocking the player (debating on it)
* (Single player) Equipment / spells. 
* (Single player) Inventory. 

## Extra
Implementation of the ability to choose the size of the game map. 
Implementation of the ability to choose the map (example: cemetery, undergrounds, dark woods), modify mobs and tiles pattern. 

## The pros of choosing this project 
+ It's a very flexible project because it allows us to add new rules and features at will without too much added complexity.  
+ Easily modularized and decomposable problem.  
+ A good balance between originality and complexity of creation.  
+ Good chance of meeting deadlines and guaranteeing a good final product.  

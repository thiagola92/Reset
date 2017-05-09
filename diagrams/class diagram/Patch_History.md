# Index #
* [Version 002](#version-002)
* [Version 003](#version-003)
* [Version 004](#version-004)
* [Verison 005](#version-005)
* [Version 006](#version-006)

# Version 002 #

## Hand ##
Class Hand **Deleted**

Reason: For now, there is no reason to have one class responsabile for one single Array of **Card**, i am creating this Array on class **Player**.

## Player ##
As said in class **Hand** , received one Array of **Card**

## Board ##
Created two methods
* buy_from_deck(Player): void
* buy_from_discard(Player): void

# Version 003 #

## Character ##
Renamed to class **Status**.  
Moved variables that represent status from player to the class **Status**.

Reason: I wanted to compress all information that is a int and could be changed every time in one game into one single class.  
Also, right now have one "character" that have one unique ability will make the game too complex.  
I may add after the basic things end.

## State ##
One enumeration that say the state of player.

Reason: Now you only have to check this variable to see if the player can do some action.

## Player (enumeration) ##
Changed Arrays to ArrayList<Card>.

## Card ##
Now **Card** is an interface.

Reason: Before you would create one object **Card** that would implement the interface **Card** and that would have an enum **Function** just so you could know what type of thing that card would do in the game.  
And you would have to write the function for each card, when most of them would be the same action "attack X" or  "equip this".
Now you don't depend of one enum **Function** to check what this card will do, there will be 3 class that will implement from **Card** and two of them will what they should be doing.

## Card (interface) ##
Deleted

Reason: Read above

## Deck ##
Deleted

Reason: **Deck** is just an Array, doesn't need to be an entire class.

## Discard ##
Deleted

Reason: **Discard** is just an Array, doesn't need to be an entire class.

## Board ##
Created two variables that represent the old class **Card** and **Deck**.

## Weapon ##
Class that will do the basic thing when attack:
* Ask who you want to attack.
* Check the distance from the enemy.
* Ask if the enemy want to block.
 * If yes, they lose the block and end.
 * Else, reduce enemy health.
 * If enemy is dead, you receive his "reset".
* If you attack more than one time, repeat everything above.

## Equipement ##
Class that will do the basic thing when equiping:
* Who you want to equip.
* Equip the item to the player.

## Event ##
I don't have idea.

## Game ##
Still not sure what this class represent.

## Player_connection ##
Every information about the conection with this player, things like socket will be here.

# Version 004 #

## Status ##
Deleted

Reason: It seems that most of the status are not necessary, you can calculate they at time during actions.  
For example, how much damage will you do with one weapon that have 2 of damage?  
Go through every equipment and check if they increase your damage.  
There is no reason to have one variable inside **Status** that will hold the total.

## Card (interface) ##
Add variable name so each card have a title (one way to identify).

## Player ##
Receive two variables from **Status**.  
Deleted one variable that the class didn't even existed.  
And there is no reason for the diagram tell variable that you can already suspect by seeing the conection. For example,  
You can delete variable team: Color becauseYou can see by the arrow that this class have a enumeration of color.

## Board ##
Board received some methods that will do the basic things of the cards, like attack/equip. Still not sure if this should be there or inside class **Weapon** and **Equipement**.

## Game ##
This class will be responsabile for reciving one command and translate to the game. Also the other way.

# Version 005 #

## Board ##
All methods (excluding use_card) were moved to **Player**.
All variables that are already represented in connection were removed;

Reason:  
"buy_from_deck"  
"discard_card"  
"attack_player"  
"equip_player"  
Who do this things? The **Player**, not the **Board**. The **Board** is just one way of comunication, so i will move every single action to the **Player**.  
I will probably change the name of the method use_card;

## Player ##
All variables that are already represented in connection were removed;
Received many methods from **Board**.

Reason: Read the **Board** reason.

## Historic ##
Name changed to **HistoricEntry**.
It will hold one ArrayList with every **Play**.

Reason: I could let save as string and if needed to see the historic, i could translate. But is better to let something easy to translate or to record... (i could explain better but i am lazy, sorry).

## Play ##
New class.

Reason: Instead that create a string saying "THIAGO, ATTACK, LEO", i would save on one class with all the info. Easy to change if needed.

# Version 006 #

Update on associoation lines.    
Enumerations are nothing more than associations.    
It's better call "player_id" than just "player" so many variables and methods have been update to be clear that what you should be putting there is an ID.

## Play ##
Every **Play** connect to one **Card**.    
**Play**s are created by **HistoryEntry**, so there is no reason to connect to **Player**.    

# Version 007 #
Nothing to see here
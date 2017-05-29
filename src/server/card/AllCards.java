package server.card;

import java.util.ArrayList;

/**
 * You probably came from Board.
 * <br>This class will make:
 * <li>Create all cards.</li>
 * <li>Putting them on one ArrayList</li>
 * <li>Remove at random each one and putting on deck (in other words, shuffling the deck)</li>
 * <br>If you want to add a new card to the game, you should make this here.
 * @author		Thiago Lages de Alencar
 * @version		%I%, %G%
 */
public class AllCards {
	
	private ArrayList<Card> deck;
	
	public AllCards(ArrayList<Card> deck) {
		this.deck = deck;
		
		// Equipments
		
		equipment(5, "EQUIP1", "NULL", 1, 0, 0, 0, (card, player, board) -> {
			player.equipCard(card);
		});
		
		equipment(5, "EQUIP2", "NULL", 0, 1, 0, 0, (card, player, board) -> {
			player.equipCard(card);
		});
		
		equipment(5, "EQUIP3", "NULL", 0, 0, 1, 0, (card, player, board) -> {
			player.equipCard(card);
		});
		
		equipment(5, "EQUIP4", "NULL", 0, 0, 0, 1, (card, player, board) -> {
			player.equipCard(card);
		});
		
		// Weapons
		
		weapon(5, "WEAPON1", "NULL", 4, 1, (card, player, board) -> {
			player.attackPlayer(card);
		});
		
		weapon(5, "WEAPON2", "NULL", 3, 2, (card, player, board) -> {
			player.attackPlayer(card);
		});
		
		weapon(5, "WEAPON3", "NULL", 2, 3, (card, player, board) -> {
			player.attackPlayer(card);
		});
		
		weapon(5, "WEAPON4", "NULL", 1, 4, (card, player, board) -> {
			player.attackPlayer(card);
		});
		
		// Events
		
		event(5, "BUY CARD", "NULL", (card, player, board) -> {
			player.receiveCards(board.pickFromDeck(1));;
		});
		
		event(5, "BLOCK", "NULL", (card, player, board) -> {
		});
		
		event(5, "BLOCK", "NULL", (card, player, board) -> {
		});
		
		event(5, "BLOCK", "NULL", (card, player, board) -> {
		});
		
		System.out.println(">>Created all cards in the game");
	}
	
	/**
	 * A function that helps to create X equals equipments and put on the array allCards.
	 * @param quantity			How many of this card
	 * @param name				Name of the card
	 * @param description		Description of the card
	 * @param damage			How much increase the damage of the player
	 * @param attacks			How many times more the player can attack
	 * @param distance			How much increase the distance from this player to others
	 * @param range				How much increase the range from the attacks of this player
	 * @param function			What the card is suppose to do when used
	 */
	public void equipment(int quantity, String name, String description, int damage, int attacks, int distance, int range, FunctionEquipment function) {
		for(int i=0; i < quantity; ++i)
			deck.add(new Equipment(name, description, damage, attacks, distance, range, function));
		
		System.out.format(">>%d cards of '%s' were add to the game\n", quantity, name);
	}

	/**
	 * A function that helps to create X equals weapons and put on the array allCards.
	 * @param quantity			How many of this card
	 * @param name				Name of the card
	 * @param description		Description of the card
	 * @param damage			Damage this weapon causes if not blocked.
	 * @param range				Enemy must be this close to you for you hit.
	 * @param function			What the card is suppose to do when used
	 */
	public void weapon(int quantity, String name, String description, int damage, int range, FunctionWeapon function) {
		for(int i=0; i < quantity; ++i)
			deck.add(new Weapon(name, description, damage, range, function));
		
		System.out.format(">>%d cards of '%s' were add to the game\n", quantity, name);
	}

	/**
	 * A function that helps to create X equals events and put on the array allCards.
	 * @param quantity			How many of this card
	 * @param name				Name of the card
	 * @param description		Description of the card
	 * @param function			What the card is suppose to do when used
	 */
	public void event(int quantity, String name, String description, FunctionEvent function) {
		for(int i=0; i < quantity; ++i)
			deck.add(new Event(name, description, function));
		System.out.format(">>%d cards of '%s' were add to the game\n", quantity, name);
	}

}

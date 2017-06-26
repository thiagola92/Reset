package server.player;

import java.util.ArrayList;

import lang.Language;
import server.board.Board;
import server.card.Equipment;
import server.card.Weapon;
import server.card.interfaces.Card;

public class Player {
	
	private Board board;
	
	private ConnectionClient connection;
	
	private String name;
	private int resets;
	private int health;
	private Color team;
	private State state;
	
	private int handSize;
	
	private ArrayList<Card> hand;
	private ArrayList<Equipment> equipments;
	
	public Player(Board board, ConnectionClient connection) {
		this.board = board;
		this.connection = connection;
		
		this.name = "";
		setResets(4);
		setHealth(5);
		setState(State.WAITING_TURN);
		
		this.handSize = 7;

		this.hand = new ArrayList<Card>();
		this.equipments = new ArrayList<Equipment>();
		
		connection.sendMessage(Language.ASKTEXT + Language.SEPARATOR + Language.submit_your_nickname);
		name = connection.receiveMessage()[0];
	}

	/*
	 * get
	 ***************************************************************
	 ***************************************************************
	 ***************************************************************
	 ***************************************************************
	 ***************************************************************
	 */
	
	public String getName() {
		return name;
	}

	public int getResets() {
		return resets;
	}

	public int getHealth() {
		return health;
	}

	public Color getTeam() {
		return team;
	}

	public State getState() {
		return state;
	}
	
	public int getHandSize() {
		return handSize;
	}
	
	public int getDamage() {
		int damage = 0;

		synchronized(equipments) {
		for(Equipment equip: equipments)
			damage += equip.getDamage();
		}
		
		return damage;
	}
	
	public int getAttacks() {
		int attacks = 0;
		
		synchronized(equipments) {
			for(Equipment equip: equipments)
				attacks += equip.getAttacks();
		}
		
		return attacks;
	}

	public int getDistance() {
		int distance = 0;

		synchronized(equipments) {
			for(Equipment equip: equipments)
				distance += equip.getDistance();
		}
		
		return distance;
	}
	
	public int getRange() {
		int range = 0;

		synchronized(equipments) {
			for(Equipment equip: equipments)
				range += equip.getRange();
		}
		
		return range;
	}
	
	public ArrayList<String> getLogInfo() {
		return connection.getHistoric();
	}

	public ArrayList<String> getPlayerInfo(Player player, boolean secret) {
		ArrayList<String> playerInfo = new ArrayList<String>();

		playerInfo.add(Language.player_name + Language.SEPARATOR + this.getName());
		
		playerInfo.add(Language.resets + Language.SEPARATOR + this.getResets());
		playerInfo.add(Language.health + Language.SEPARATOR + this.getHealth());
		
		if(secret == false) {
			playerInfo.add(Language.team + Language.SEPARATOR + this.getTeam());
			playerInfo.add(Language.state + Language.SEPARATOR + this.getState());
		}
		
		playerInfo.add(Language.damage + Language.SEPARATOR + this.getDamage());
		playerInfo.add(Language.attacks + Language.SEPARATOR + this.getAttacks());
		playerInfo.add(Language.distance + Language.SEPARATOR + board.distanceFromPlayer1ToPlayer2(player, this));
		playerInfo.add(Language.range + Language.SEPARATOR + this.getRange());
		
		playerInfo.add(Language.number_of_cards_holding + Language.SEPARATOR + this.hand.size());
		
		if(secret == false) {
			synchronized(hand) {
				for(Card c: hand)
					playerInfo.add(Language.cards + Language.SEPARATOR + c.getName());
			}
		}
		
		synchronized(equipments) {
			for(Card c: equipments)
				playerInfo.add(Language.equipment + Language.SEPARATOR + c.getName());
		}
		
		return playerInfo;
	}
	
	/*
	 * set
	 ***************************************************************
	 ***************************************************************
	 ***************************************************************
	 ***************************************************************
	 ***************************************************************
	 */
	
	public void setResets(int resets) {
		this.resets = resets;
		
		if(this.resets <= 0) {
			this.resets = 0;

			board.setEndGame();
		}
	}
	
	public void setHealth(int health) {
		this.health = health;
		
		if(this.health <= 0) {
			this.health = 0;
			this.state = State.DEAD;

			
			setResets(getResets() - 1);
		}
	}

	public void setTeam(Color team) {
		this.team = team;
	}
	
	public void setState(State state) {
		this.state = state;
		
		if(state == State.PLAYING)
			connection.sendMessage(Language.TALK);
		else 
			connection.sendMessage(Language.DONTTALK);
		
	}
	
	/*
	 ***************************************************************
	 ***************************************************************
	 ***************************************************************
	 ***************************************************************
	 ***************************************************************
	 */
	
	public void equipCard(Equipment equipment) {
		if(hand.remove(equipment))
			equipments.add(equipment);
	}
	
	/**
	 * Add X cards to your hand.
	 * <br>Cards don't have access to the hand, they will only be able to change using methods.
	 * @param card		Cards that is going to your hand
	 */
	public void receiveCards(Card[] card) {
		for(int i=0; i < card.length; ++i) {
			hand.add(card[i]);
			System.out.format(">>Player %s received card %s\n", name, card[i].getName());
		}
	}
	
	public void receiveCards(Card card) {
		Card[] x = new Card[1];
		x[0] = card;
		receiveCards(x);
	}
	
	/**
	 * Search on hand and equipments the card that will be discard.
	 * <br>Good side is that work as an unequipCard one time that you have know exactly the card to be removed.
	 * <br>This happens because you are giving the exactly card.
	 * @param card		Card to discard
	 */
	public void discardCard(Card card) {
		
		for(int i=0; i < hand.size(); ++i) {
			if(hand.get(i) == card) {
				card = hand.remove(i);
				break;
			}
		}
		
		for(int i=0; i < equipments.size(); ++i) {
			if(equipments.get(i) == card) {
				card = equipments.remove(i);
				break;
			}
		}
		
		System.out.format(">>Player %s discarded %s\n", name, card.getName());
		board.discardCard(card);
	}
	
	/**
	 * The player use one card, search his hand for the card, if exist then use.
	 * <br>Notice that the card will not be removed from the hand, this will be the cards job's.
	 * <br>Why? Some cards can go to the discard and others can be moved to the equipment, so i can't know for sure where it will go, just the card knows.
	 * @param cardName		Name of the card to search.
	 */
	public void useCard(String cardName) {

		System.out.format(">>Player %s tried to use card %s\n", this.getName(), cardName);
	
		for(Card card: hand) {
			if(card.getName().compareTo(cardName) == 0) {
				
				card.useCard(this, board);
				break;
			}
		}
	}
	
	/**
	 * Ask the player to discard until the hand size be less than the max.
	 */
	private void limitCards() {
		
		synchronized(hand) {
			while(hand.size() > getHandSize()) {
				System.out.format(">>You have %d cards on hand when the max is %d\n", hand.size(), getHandSize());
				
				String message = Language.OPTIONS + Language.SEPARATOR + Language.chose_one_card_to_discard;
				
				for(Card card: hand)
					message += Language.SEPARATOR + card.getName();
				
				connection.sendMessage(message);
				String answer = connection.receiveMessage()[0];
	
				for(Card card: hand) {
					if(card.getName().compareTo(answer) == 0) {
						discardCard(card);
						break;
					}
				}
			}
		}
	}
	
	/**
	 * The player attacks another using one Weapon.
	 * <li>Check if the player can still attack.</li>
	 * <li>Search all players alive (is not State.DEAD).</li>
	 * <li>Get all players in range.</li>
	 * <li>Ask the client the target.</li>
	 * <li>Call blockPlayer() to know if the target is going to block.</li>
	 * <li>If he die after the attack (doesn't matter if blocked or not), you gain you reset.</li>
	 * @param weapon	Weapon used in the attack.
	 */
	public void attackPlayer(Weapon weapon) {
		
		if(board.getAttacksThisTurn() > getAttacks()) {
			System.out.format(">>You can not attack more than %s time(s) this turn\n", board.getAttacksThisTurn());
			
			return;
		}
		
		ArrayList<Player> playersThatCanBeAttacked = board.getPlayersWithState(State.WAITING_TURN);
		String message = Language.OPTIONS + Language.SEPARATOR + Language.chose_one_player_to_attack;
		
		for(int i=0; i < playersThatCanBeAttacked.size();) {
			Player player = playersThatCanBeAttacked.get(i);
			
			if(board.distanceFromPlayer1ToPlayer2(this, player) <= weapon.getRange() + this.getRange()) {
				message += Language.SEPARATOR + player.getName();
				++i;
			} else
				playersThatCanBeAttacked.remove(player);
		}
		
		System.out.format(">>Options that this player can attack \n%s\n", message);
		
		connection.sendMessage(message);
		String answer = connection.receiveMessage()[0];
		System.out.format(">>Chosen target: %s\n", answer);
		
		for(Player player: playersThatCanBeAttacked) {
			if(player.getName().compareTo(answer) == 0) {
				
				System.out.format(">>Target %s attacked\n", player.getName());
				
				board.setAttacksThisTurn(board.getAttacksThisTurn() + 1);
				
				player.blockPlayer(this, weapon);
				
				discardCard(weapon);
				
				if(player.state == State.DEAD) {
					System.out.format(">>Player %s gain one reset\n", this.getName());
					setResets(getResets() + 1);
				}
				
				return;
			}
		}
		
		System.out.println(">>Target not found.");
	}
	
	/**
	 * Check if the player will block the other player attack.
	 * <li>Get all cards that can block</li>
	 * <li>Ask player what he wants to do</li>
	 * <li>Check if his answer is one card that can block</li>
	 * @param player
	 * @param weapon
	 */
	public void blockPlayer(Player player, Weapon weapon) {
		ArrayList<Card> cardsThatCanBlock = new ArrayList<Card>();
		String message = Language.OPTIONS + Language.SEPARATOR + Language.chose_a_block_card;
		
		for(Card card: hand) {
			if(card.getName().compareTo("Block") == 0) {
				message += (Language.SEPARATOR + card.getName());
				cardsThatCanBlock.add(card);
			}
		}
		
		System.out.format(">>Options that the other player can chose \n%s\n", message);
		
		connection.sendMessage(message);
		String answer = connection.receiveMessage()[0];
		System.out.format(">>Target chose: %s\n", answer);
		
		for(Card card: cardsThatCanBlock) {
			if(card.getName().compareTo(answer) == 0) {
				System.out.format(">>Player %s blocked with %s\n", this.getName(), card.getName());
				
				discardCard(card);
				
				return;
			}
		}

		System.out.format(">>Player %s didn't block\n", this.getName());
		setHealth(getHealth() - weapon.getDamage() - player.getDamage());
	}
	
	public void updatePlayer(ArrayList<String> publicInfo) {
		String message = Language.UPDATE;
		
		for(String s: publicInfo)
			message += Language.SEPARATOR + s;
		
		connection.sendMessage(message);
	}
	
	/**
	 * Get the player message and translate to one action/command.
	 * <br>Right now the options are:
	 * <li>NEXTTURN</li>
	 * <li>USECARD</li>
	 */
	public void command() {
		String[] arguments = connection.receiveMessage();
		
		if(arguments[0].compareTo(Language.NEXTTURN) == 0) {
			
			limitCards();
			board.nextTurn();
			
		} else if(arguments[0].compareTo(Language.USECARD) == 0 && arguments.length == 2) {
			
			this.useCard(arguments[1]);
			
		}
	}
}

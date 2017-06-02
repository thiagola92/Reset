package server.card;

import server.board.Board;
import server.player.Player;

/**
 * You came here from Player.
 * <br>This a unique type of card called weapon.
 * <br>They are known for attacking others players.
 * @author		Thiago Lages de Alencar
 * @version		%I%, %G%
 */
public class Weapon implements Card {
	
	private String name;
	private String description;
	private int damage;
	private int range;
	private FunctionWeapon function;

	public Weapon(String name, String description,
			int damage, int range,
			FunctionWeapon function) {

		this.name = name;
		this.description = description;
		this.damage = damage;
		this.range = range;
		this.function = function;
	}
	
	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public int getDamage() {
		return damage;
	}

	public int getRange() {
		return range;
	}

	public void useCard(Player player, Board board) {
		function.useCard(this, player, board);
	}

}

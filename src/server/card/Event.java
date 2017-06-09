package server.card;

import server.board.Board;
import server.player.Player;

/**
 * I don't know how you get here.
 * <br>This a unique type of card called event.
 * <br>Basically do 'something', could damage, could buff, could buy card... I don't know what each card events do.
 * @author		Thiago Lages de Alencar
 * @version		%I%, %G%
 */
public class Event implements Card {

	private String name;
	private FunctionEvent function;
	
	public Event(String name, FunctionEvent function) {
		this.name = name;
		this.function = function;
	}
	
	public String getName() {
		return name;
	}

	public void useCard(Player player, Board board) {
		function.useCard(this, player, board);
	}

}

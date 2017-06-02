package server.card;

import server.board.Board;
import server.player.Player;

/**
 * .
 * @author		Thiago Lages de Alencar
 * @version		%I%, %G%
 */
public interface FunctionWeapon {
	public void useCard(Weapon card, Player player, Board board);
}

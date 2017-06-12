package client.window.playerpanel;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import lang.Language;

@SuppressWarnings("serial")
public class CardsPanel extends JPanel {
	
	private String location;
	private String format;
	
	private ArrayList<String> cardsName;
	private ArrayList<JLabel> cardsImages;

	public CardsPanel() {
		this.location = "./src/cards/";
		this.format = ".png";

		this.cardsImages = new ArrayList<JLabel>();
		
		//	System.out.println(System.getProperty("user.dir"));			Get the directory of the application 
		for(int i=0; i < 7; ++i) {
			ImageIcon image = new ImageIcon(location + Language.TEMPLATE + format);
			JLabel label = new JLabel("", image, JLabel.CENTER);
			cardsImages.add(label);
			
			this.add(cardsImages.get(i));
		}
		
	}
	
	public ArrayList<String> getCardsName() {
		return cardsName;
	}
	
	public ArrayList<JLabel> getCardsImages() {
		return cardsImages;
	}
	
	/**
	 * Receive an Array with name of cards and print their image in order from left to right.
	 * @param cards			Name of cards that should be draw
	 */
	public void updateCardsPanel(ArrayList<String> cards) {
		this.cardsName = cards;
		
		for(int i = 0; i < cardsImages.size(); ++i) {
			ImageIcon image = new ImageIcon(location + Language.TRANSPARENT + format);
			
			if(i < cards.size())
				image = new ImageIcon(location + cards.get(i) + format);
			
			cardsImages.get(i).setIcon(image);
		}
	}
}
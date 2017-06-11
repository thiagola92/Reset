package client.window;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import client.Translator;

@SuppressWarnings("serial")
public class HandPanel extends CardsPanel {
	
	private Translator translator;
	
	public HandPanel(Translator translator) {
		super();
		
		this.translator = translator;
		
		listenToClick();
	}
	
	public void listenToClick() {
		ArrayList<JLabel> cardsImages = super.getCardsImages();
		
		synchronized(cardsImages) {
			for(JLabel image: cardsImages) {
				image.addMouseListener(new SelectCardListener(this, image));
			}
		}
	}
	
	public void clearSelection() {
		ArrayList<JLabel> cardsImages = super.getCardsImages();
		
		synchronized(cardsImages) {
			for(JLabel image: cardsImages) {
				image.setBorder(null);
			}
		}
		
		translator.cardSelected("");
	}
	
	public void selectCard(JLabel selectThisImage) {
		ArrayList<JLabel> cardsImages = super.getCardsImages();
		
		clearSelection();
		
		synchronized(cardsImages) {
			for(int i=0; i < cardsImages.size(); ++i) {
				
				// If this is the label that you clicked AND if this label is not an invisible label
				if(cardsImages.get(i) == selectThisImage && i < super.getCardsName().size()) {
					cardsImages.get(i).setBorder(BorderFactory.createLineBorder(Color.RED));
					
					translator.cardSelected(super.getCardsName().get(i));
					
					break;
				}
				
			}
		}
	}
	
	public void updateCardsPanel(ArrayList<String> cards) {
		clearSelection();
		
		super.updateCardsPanel(cards);
	}
}

package pl.parys.blackjack.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Deck {
	private ObservableList<Card> deck = FXCollections.observableArrayList();
	
	public Deck() {
		for(int j = 0; j < 4; j++) {
			if(j == 0) {
				for(int i = 1; i < 14; i++) {
					deck.add(new Card(i, 'H'));
				}
			}
			else if(j == 1) {
				for(int i = 1; i < 14; i++) {
					deck.add(new Card(i, 'S'));
				}
			}
			else if(j == 2) {
				for(int i = 1; i < 14; i++) {
					deck.add(new Card(i, 'D'));
				}
			}
			else if(j == 3) {
				for(int i = 1; i < 14; i++) {
					deck.add(new Card(i, 'C'));
				}
			}
		}
	}
	
	public ObservableList<Card> getDeck() {
		return deck;
	}
	
	

}

package pl.parys.blackjack.model;

import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Table {

	private Deck deck;
	
	private int betOnTable;
	
	private ObservableList<Card> shuffledDeck = FXCollections.observableArrayList();
	
	public Table() {
		deck = new Deck();
	}
	
	public int getBetOnTable() {
		return betOnTable;
	}
	
	public void setBetOnTable(int betOnTable) {
		this.betOnTable = betOnTable;
	}
	
	public void shuffleCards() {
		shuffledDeck = deck.getDeck();
		Collections.shuffle(shuffledDeck);
	}
	
	public Card getNextCard() {
		Card card;
		card = shuffledDeck.get(0);
		shuffledDeck.remove(0);
		return card;
	}
	
	public int getCardsLeft() {
		return shuffledDeck.size();
	}
	
}

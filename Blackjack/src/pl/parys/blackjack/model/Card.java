package pl.parys.blackjack.model;

public class Card {
	int value;
	char name;
	
	public Card(int value, char name) {
		this.value = value;
		this.name = name;
	}
	
	public int getCardValue() {
		return this.value;
	}
	
	public char getCardName() {
		return this.name;
	}
}

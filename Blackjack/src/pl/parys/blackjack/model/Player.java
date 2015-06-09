package pl.parys.blackjack.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Player {
	private int score;
	private ObservableList<Card> cards = FXCollections.observableArrayList();
	private String name;
	private int money;
	private String status;
	private int bet;

	public Player(String name, int money) {
		this.name = name;
		this.money = money;
		this.score = 0;
		this.status = "w grze";
		this.bet = 0;
	}
	
	
	public int getScore() {
		return score;
	}
	public void setScore(Card c) {
			if(c.getCardValue() > 10) {
				score += 10;
			} else score += c.getCardValue();
	}
	public void eraseScore() {
		score = 0;
	}
	public void eraseCards() {
		cards.clear();
	}
	public ObservableList<Card> getCards() {
		return cards;
	}
	public void setCards(Card card) {
		this.cards.add(card);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getBet() {
		return bet;
	}
	public void setBet(int bet) {
		this.bet = bet;
	}

}

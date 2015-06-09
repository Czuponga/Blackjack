package pl.parys.blackjack.view;

import java.util.Optional;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import pl.parys.blackjack.model.Card;
import pl.parys.blackjack.model.Croupier;
import pl.parys.blackjack.model.Player;
import pl.parys.blackjack.model.Table;

public class TableViewController {
	@FXML
	private TextField bet;
	@FXML
	private Label playerName;
	@FXML
	private Label playerTokens;
	@FXML
	private Label playerScore;
	@FXML
	private HBox showPlayerCards;
	@FXML
	private HBox showCroupierCards;
	@FXML
	private Button getC;
	@FXML
	private Button stand;
	@FXML
	private Button sBet;

	
	private Table table;
	private Player player;
	private Card playerCard;
	private Croupier croupier;
	private Card croupierCard;

	public TableViewController() {
		table = new Table();
		player = new Player("Gracz", 100);
		croupier = new Croupier("Krupier");
	}

	@FXML
	private void initialize() {
		showPlayer(player);
		sBet.setVisible(false);
		stand.setVisible(false);
		getC.setVisible(false);
		bet.setVisible(false);
	}

	@FXML
	private void shuffleDeck() {
		table.shuffleCards();
		sBet.setVisible(true);
		getC.setVisible(true);
		bet.setVisible(true);
		stand.setVisible(true);
	}

	@FXML
	private void stand() {
		player.setStatus("stand");

		if (player.getStatus() == "AS" && player.getScore() + 10 < 21) {
			player.setScore(new Card(player.getScore() + 10, 'J'));
		}

		showCroupierCards.getChildren().clear();
		for (Card c : croupier.getCards()) {
			Image img = new Image(
					TableViewController.class.getResourceAsStream(cardColor(c)));
			ImageView imgView = new ImageView(img);
			showCroupierCards.getChildren().add(imgView);
		}
		while (croupier.getScore() < 17) {
			getCroupierCard();
		}

		if (croupier.getScore() > player.getScore())
			endGameAlert();
		else
			wonGameAlert();
	}

	@FXML
	private void getCard() {
		if (!player.getStatus().equals("w grze")) {
			getPlayerCard();
		} else {
			Alert noBet = new Alert(AlertType.ERROR);
			noBet.setHeaderText("Postaw zak≥ad!");
			noBet.showAndWait();
		}
	}

	private void getPlayerCard() {
		playerCard = table.getNextCard();

		if (playerCard.getCardValue() == 1)
			player.setStatus("AS");

		player.setCards(playerCard);
		Image img = new Image(
				TableViewController.class
						.getResourceAsStream(cardColor(playerCard)));
		ImageView imgView = new ImageView(img);
		showPlayerCards.getChildren().add(imgView);
		setPlayerScore(playerCard);
		if (player.getScore() > 21) {
			endGameAlert();
		} else if (player.getScore() == 21) {
			wonGameAlert();
		}
	}

	private void setPlayerScore(Card card) {
		player.setScore(card);
		if (player.getStatus().equals("AS")) {
			if (player.getScore() + 10 < 22) {
				playerScore.setText(Integer.toString(player.getScore())
						+ " lub " + Integer.toString(player.getScore() + 10));
				if ((player.getScore() + 10) == 21) {
					wonGameAlert();
				}
			} else {
				playerScore.setText(Integer.toString(player.getScore()));
			}
		} else {
			playerScore.setText(Integer.toString(player.getScore()));
		}
	}

	private void getCroupierCard() {
		croupierCard = table.getNextCard();
		croupier.setCards(croupierCard);
		if (croupier.getCards().size() == 1) {
			Image img = new Image(
					TableViewController.class.getResourceAsStream("back.jpg"));
			ImageView imgView = new ImageView(img);
			showCroupierCards.getChildren().add(imgView);
		} else {
			Image img = new Image(
					TableViewController.class
							.getResourceAsStream(cardColor(croupierCard)));
			ImageView imgView = new ImageView(img);
			showCroupierCards.getChildren().add(imgView);
		}
		croupier.setScore(croupierCard);
		if (croupier.getScore() > 21) {
			wonGameAlert();
		}
	}

	private void endGameAlert() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setHeaderText("Przegra≥eú!");
		alert.setContentText("Co chcesz teraz zrobiÊ?");

		ButtonType buttonNew = new ButtonType("Nowa Gra");
		ButtonType buttonEx = new ButtonType("Wyjdü");

		alert.getButtonTypes().setAll(buttonNew, buttonEx);

		Optional<ButtonType> result = alert.showAndWait();
		//W tym miejscu gracz i krupier dostaja nowe karty, czysci sie okno wyswietlajace 
		//karty, punkty sa zerowane
		//
		if (result.get() == buttonNew) {
			showPlayerCards.getChildren().clear();
			showCroupierCards.getChildren().clear();
			player.eraseScore();
			croupier.eraseScore();
			player.eraseCards();
			croupier.eraseCards();
			playerScore.setText(Integer.toString(player.getScore()));
			player.setStatus("w grze");
		} else if (result.get() == buttonEx) {
			Platform.exit();
		}
	}

	
	private void wonGameAlert() {
		Alert wonGame = new Alert(AlertType.CONFIRMATION);
		wonGame.setHeaderText("Wygra≥eú!");
		wonGame.setContentText("Twoja wygrana to " + table.getBetOnTable() * 2);
		player.setMoney(player.getMoney() + table.getBetOnTable() * 2);
		playerTokens.setText(Integer.toString(player.getMoney()));

		ButtonType buttonNew = new ButtonType("Nowa Gra");
		ButtonType buttonEx = new ButtonType("Wyjdü");

		wonGame.getButtonTypes().setAll(buttonNew, buttonEx);

		Optional<ButtonType> result = wonGame.showAndWait();
		//W tym miejscu gracz i krupier dostaja nowe karty, czysci sie okno wyswietlajace 
		//karty, punkty sa zerowane
		if (result.get() == buttonNew) {
			showPlayerCards.getChildren().clear();
			showCroupierCards.getChildren().clear();
			player.eraseScore();
			player.eraseCards();
			croupier.eraseCards();
			croupier.eraseScore();
			playerScore.setText(Integer.toString(player.getScore()));
			player.setStatus("w grze");
		} else if (result.get() == buttonEx) {
			Platform.exit();
		}
	}

	public String cardColor(Card c) {
		String cardPath = Integer.toString(c.getCardValue())
				+ Character.toString(c.getCardName()) + ".JPG";
		return cardPath;
	}

	private void showPlayer(Player player) {
		playerName.setText(player.getName());
		playerTokens.setText(Integer.toString(player.getMoney()));
	}

	@FXML
	private void setBet() {
		if (player.getStatus().equals("w grze")) {
			int playBet;
			playBet = Integer.parseInt(bet.getText());
			table.setBetOnTable(playBet);
			playerTokens.setText(Integer.toString(player.getMoney() - playBet));
			player.setMoney(player.getMoney() - playBet);
			player.setStatus("postawi≥");

			do {
				getPlayerCard();
				getCroupierCard();
			} while (player.getCards().size() < 2
					&& croupier.getCards().size() < 2);

		} else {
			Alert cantSetBet = new Alert(AlertType.ERROR);
			cantSetBet.setHeaderText("Nie moøesz obstawiaÊ w trakcie gry!");
			cantSetBet.showAndWait();
		}
	}

}

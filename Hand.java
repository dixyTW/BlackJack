import java.util.Stack;

public class Hand {
	Stack<Cards> cards;
	int value;
	boolean blackjack;
	int bet;
	boolean finish;
	boolean db_down;
	
	public Hand() { 
		this.cards = new Stack<Cards>();
		this.value = 0;
		this.blackjack = false;
		this.bet = 0;
		this.finish = false;
		this.db_down = false;
	}
	
	public String toString() { 
		StringBuilder sb = new StringBuilder(100);
		sb.append("[");
		for (int i = 0; i < cards.size(); i++) {
			Cards card = cards.get(i);
			sb.append(card);
			sb.append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("]");
		return sb.toString();
	}
	
	
	public void update_val() {
		int num_aces = 0;
		int face_card = 0;
		this.value = 0;
		for (int i = 0; i < cards.size(); i++) { 
			Cards card = cards.get(i);
			if (card.name.equals("Ace")) {
				num_aces += 1;
				this.value += 11;
			}
			else if (card.name.equals("Jack") || card.name.equals("Queen")|| card.name.equals("King")) { 
				this.value += 10;
				face_card += 1;
			}
			else {
				this.value += card.value;
			}
		}
		if (num_aces == 1 && face_card == 1 && cards.size() == 2) {
			this.blackjack = true;
		}
		while (this.value>21 && num_aces>0) {
			this.value -= 10;
			num_aces -= 1;
		}
		
		if (this.value>21) {
			//busted 
			System.out.println("BUSTED!! Hand value is " + this.value);
			System.out.println(cards);
			this.finish = true;
		}
	}
	
	public String showHands(boolean start) { 
		StringBuilder sb = new StringBuilder(100);
		sb.append("[");
		if (start) {
			Cards card = cards.get(0);
			sb.append(card);
			sb.append(",");
			sb.append("FOLD");
			sb.append(",");
		}
		else {
			for (int i = 0; i < cards.size(); i++) {
				Cards card = cards.get(i);
				sb.append(card);
				sb.append(",");
			}
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("]");
		return sb.toString();
	}
	
}

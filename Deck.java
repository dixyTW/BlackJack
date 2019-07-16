import java.util.Stack;
import java.util.Collections;

public class Deck {
	Stack<Cards> deck; 
	String symbol[] = {"♣", "♦", "❤", "♠"};
	int value[] = {11,2,3,4,5,6,7,8,9,10,10,10,10};
	String name[] = {"Ace","Two","Three","Four","Five","Six","Seven","Eight","Nine","Ten","Jack","Queen","King"};
	
	public Deck(){
		this.deck = new Stack<Cards>(); 
		init_deck();
		Collections.shuffle(deck);
	}
	
	public void reset(Player player, Dealer dealer) {
		for (int i = 0; i < player.num_hands; i++) {
			while (!player.hand[i].cards.empty()) { 
				deck.push(player.hand[i].cards.pop());
			}
		}
		player.num_hands = 1;
		while (!dealer.hand.cards.empty()) {
			deck.push(dealer.hand.cards.pop());
		}
		Collections.shuffle(deck);
	}
	
	public void dealCards_P(Player player, int i) {
		player.hand[i].cards.push(deck.pop());
		
	}
	
	public void dealCards_D(Dealer dealer) {
		dealer.hand.cards.push(deck.pop());
	}
	
	public String toString() { 
		StringBuilder sb = new StringBuilder(512);
		sb.append("[");
		for (int r = 0; r < 52; r++) {
			Cards card = deck.pop();
			sb.append(card);
			sb.append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("]");
		return sb.toString();
	}
	

	public void init_deck() {
		for (int sym = 1; sym < 5; sym++) { 
			for (int val = 1; val < 14; val++) {
				Cards new_card = new Cards(value[val-1], symbol[sym-1], name[val-1]);
				deck.push(new_card);
			}
		}
	}
}

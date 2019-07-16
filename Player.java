public class Player {
	int money;
    Hand hand[];
	String name;
	int num_hands;
	
	public Player(String name) {
		this.name = name;
		this.hand = new Hand[4];
		this.money = 1000;
		this.num_hands = 1;
		
	}
	
	public void reset() { 
		for (int i = 0; i<this.num_hands; i++) {
			hand[i].blackjack = false;
			hand[i].bet = 0;
			hand[i].finish = false;
			hand[i].db_down = false;
		}
		this.num_hands = 1;
	}
	
}

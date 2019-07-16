import java.util.InputMismatchException;
import java.util.Scanner;

public class BlackJack {
	Player player;
	Dealer dealer;
	Deck deck;
	
	public void init_player(Scanner input, int num_player) {
		System.out.println("Player please enter your name: ");
		String p_name = input.nextLine();
		this.player = new Player(p_name);
		for (int i = 0; i < 4; i++) {
			Hand new_hand = new Hand();
			player.hand[i] = new_hand;
		}
		if (num_player == 2) {
			System.out.println("Dealer please enter your name: ");
			String d_name = input.nextLine();
			this.dealer = new Dealer(d_name);
		}
		else { 
			this.dealer = new Dealer("CPU");
		}
	}
	
	public void place_bet(Scanner input) { 
		int money_bet;
		while (true) {
			try {
	            System.out.println("Place your bet: ");
	    		money_bet = input.nextInt();
	    		input.nextLine();
	    		if (money_bet > player.money) {
	    			System.out.println("You don't have that much money");
	    			continue;
	    		}
	    		if (money_bet < 0) {
	    			System.out.println("Can't place negative bets");
	    			continue;
	    		}
	    		break;
	        }
	        catch(InputMismatchException e) {
	            System.err.println("Wrong Input! Only integers allowed...");
	            input.nextLine();
	            System.out.println("\n");
	            continue;
	        }	
		}
		
		player.money -= money_bet;
		player.hand[0].bet += money_bet;
	}
	
	public boolean check_split(int i, int bet) {
		if (player.money < bet) {
			return false;
		}
		Cards first = player.hand[i].cards.get(0);
		Cards second = player.hand[i].cards.get(1);
		if (player.hand[i].cards.size() == 2 && first.is_equal(second)) {
			return true;
		}
		return false;
	}
	
	public void hit_me(int i) {

		deck.dealCards_P(player, i);
		player.hand[i].update_val();
		
	}
	
	public boolean all_finish() {
		for (int i = 0; i < player.num_hands; i++) {
			if (!player.hand[i].finish) {
				return false;
			}
		}
		return true;
	}
	
	
	public void split(int i) {
		int index = player.num_hands;
		player.money -= player.hand[i].bet;
		player.hand[index].bet = player.hand[i].bet;
		player.hand[index].cards.push(player.hand[i].cards.pop()); //split cards between the two hands
		deck.dealCards_P(player, i);
		deck.dealCards_P(player, index);
		player.hand[index].update_val();
		player.hand[i].update_val();
		player.num_hands++;
		
	}
	
	public void results() {
		int d_score, p_score;
		int money_sum = 0;
		int money_bet; 
		int mult;
		for (int index = 0; index < player.num_hands; index++) {
			if (player.hand[index].db_down) {
				mult = 1;
			}
			else {
				mult = 0;
			}
			money_bet = player.hand[index].bet;
			System.out.println("Hand Number: " + (index+1));
			System.out.println("Value of bet: " + money_bet);
			d_score = dealer.hand.value;
			p_score = player.hand[index].value;
			
			System.out.println("dealer score " + d_score);
			System.out.println("player score " + p_score);
			if (d_score > 21 && p_score >21) {
				System.out.println("Both Busted, No money loss");
				player.money += (money_bet + money_bet*mult);
			}
			else if (p_score > 21) {
				System.out.println("Player Busted");
				money_sum -= (money_bet + money_bet*mult);
			}
			else if (d_score > 21) {
				System.out.println("Dealer Busted");
				player.money += (2*money_bet + money_bet*mult);
				money_sum += (money_bet + money_bet*mult); 
			}
			else if (player.hand[index].blackjack && dealer.hand.blackjack) {
				System.out.println("Both side BlackJack, no money loss");
				player.money += (money_bet + money_bet*mult);
			}
			else if (player.hand[index].blackjack) {
				System.out.println("Player has BlackJack");
				player.money += (2*money_bet + money_bet*mult);
				money_sum += (money_bet + money_bet*mult); 
			}
			else if (dealer.hand.blackjack) {
				System.out.println("Dealer has BlackJack");
				money_sum -= (money_bet + money_bet*mult);
			}
			else if (d_score > p_score) {
				System.out.println("Dealer has more points than Player, money loss");
				money_sum -= (money_bet + money_bet*mult);
			}
			else if (d_score < p_score) {
				System.out.println("Player has more points than Dealer, money gain");
				player.money += (2*money_bet + money_bet*mult);
				money_sum += (money_bet + money_bet*mult); 
			}
			else {
				System.out.println("Same Points, no money loss");
			}
			
			
		}
		System.out.println("Total net gain/loss: " + money_sum);
		System.out.println("Player has: $" + player.money);
	}
	
	public boolean playBJ(Scanner input) {
		this.deck = new Deck();
		int num_player;
		boolean broke = false;
		String cashout = "n";
		boolean start = true;
		int decision;
		boolean options = true;
		boolean choosing;
		

		
		//Ask user if its two player or one player
		while (true) {
			try {
	            System.out.println("Enter number of players, (1 or 2)");
	    		num_player = input.nextInt();
	    		input.nextLine();
	    		if (num_player != 1 && num_player != 2) {
	    			System.out.println("Only 1 or 2 is allowed");
	    			continue;
	    		}
	    		break;
	        }
	        catch(InputMismatchException e) {
	            System.err.println("Wrong Input! Only 1 or 2 is allowed...");
	            input.nextLine();
	            System.out.println("\n");
	            continue;
	        }	
		}
		
		init_player(input, num_player);
		
		while (!(broke || cashout.equals("y"))) { 
			//Distribute Cards
			start = true; //for dealer to show his cards, probably don't need it
			deck.reset(player, dealer);
			player.reset();
			System.out.println("Distributing Cards....");
			deck.dealCards_P(player, 0);
			deck.dealCards_D(dealer);
			deck.dealCards_P(player, 0);
			deck.dealCards_D(dealer);
			player.hand[0].update_val();
			dealer.hand.update_val();
			
			//Player place bets
			
			place_bet(input);
			options = true;
			
			//Ask player what move to perform
			
			while (options) {
				//options = false when all hands are finished
				for(int i = 0; i < player.num_hands; i++) {
					System.out.println("Hand Number " + (i+1));
					if (player.hand[i].finish) {
						continue;
					}
					else {
						choosing = true; 
						//loops through every hand the player has
						System.out.println("------Your Hand-------");
						System.out.println(player.hand[i]);
						System.out.println("Current card value: " + player.hand[i].value);
						System.out.println("------Dealer's Hand-------");
						System.out.println(dealer.hand.showHands(start));
						while (choosing) {
							//until the player chooses a valid option
							while (true) {
								try {
									System.out.println("What Would You Like to Do?");
									System.out.println("1. Hit Me!");
									System.out.println("2. Stand");
									if (player.money >= player.hand[i].bet) {
										System.out.println("3. Double Down!");
									}
									if (check_split(i, player.hand[i].bet)) {
										//even though the option is hidden, need some way to prevent the user from using it
										System.out.println("4. Split");
									}
									//Need to check for strings
									decision = input.nextInt();
									input.nextLine();
									break;
								}
								catch(InputMismatchException e) {
									System.err.println("Wrong Input! Only integers allowed");
						            input.nextLine();
						            System.out.println("\n");
						            continue;
								}
							}
							if (decision == 4 && !check_split(i,player.hand[i].bet)) {
								//prevents player from choosing split 
								decision = 5;
							}
							switch (decision) {
								case 1:
									hit_me(i);
									choosing = false;
									break;
								case 2:
									player.hand[i].finish = true;
									choosing = false;
									break;
								case 3: 
									hit_me(i);
									player.hand[i].bet += player.hand[i].bet;
									player.money -= player.hand[i].bet;
									player.hand[i].finish = true;
									player.hand[i].db_down = true;
									choosing = false;
									break;
								case 4:
									split(i);
									choosing = false;
									break;
								default:
									System.out.println("Decision invalid. Try again");
									choosing = true;
							}
						}
					}
				}
				if (all_finish()) {
					//if all hands of the player is busted or in stand state, exit the loop
					options = false;
				}
			}
			//reveal dealers hand
			while (dealer.hand.value < 17) {
				deck.dealCards_D(dealer);
				dealer.hand.update_val();
			}
			System.out.println(dealer.hand);
			
			//Compare results and adjust money accordingly
			results();
			
			
			//Continue or Not
			broke = (player.money <= 0);
			if (!broke) {
				while (true) {
					System.out.println("Would you like to cashout? (y/n)");
					cashout = input.nextLine();
					//check for valid input
					if (!cashout.equals("n") || !cashout.equals("y")) {
						System.out.println("Only enter y or n");
					}
					else {
						break;
					}
				
				}
			}
			
		}
		return false;
	}
}

import java.util.Scanner;


public class Main {
	public static void main(String[] args) {
		BlackJack Game = new BlackJack();
		Scanner input = new Scanner(System.in);
		System.out.println("-------This is BlackJack-------");
		while (true) { 
			if (Game.playBJ(input)) {
				continue;
			}
			else {
				break;
			}
		}
		input.close();
	}
}

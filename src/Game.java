import java.util.Random;
import java.util.Scanner;

import littlelib.Tools;

public class Game {
	private static final Random random = new Random();
	private static final Scanner scanner = new Scanner(System.in);
	
	private Player player;
	private AttributeImprover attrImprover;
	
	public Game() {
		player = Player.instanceOf();
		attrImprover = new AttributeImprover(player);
	}
	
	public void mainLoop() {
		while (true) {
			System.out.println("[1] Wait");
			System.out.println("[2] Show Character");
			System.out.println("[3] Improve Attributes");
			System.out.println("[0] Leave Game");
			
			int choice = Tools.saveIntInput("Your choice: ", 0, 3);
			System.out.println();
			
			if (choice == 1) {
				waitMenue();
				Main.savePlayer(player);
			} else if (choice == 2) {
				showCharacter();
			} else if (choice == 3) {
				improveAttributes();
			} else if (choice == 0) {
				System.out.println("Bye.");
				break;
			}
		}
	}
	
	private void waitMenue() {
		int secondsLowerLimit = 0;
		int secondsUpperLimit = 30 * player.getEndurance();
		
		System.out.println("You can wait between " + secondsLowerLimit + " and " + secondsUpperLimit + " seconds. ");
		int seconds = Tools.saveIntInput("Input: ", secondsLowerLimit, secondsUpperLimit);
		System.out.println();
		doNothing(seconds);
	}
	
	private void doNothing(int seconds) {
		for (int i = 0; i < seconds; i++) {
			if (player.getPatience() < 100) {
				try {
					Thread.sleep(1000 - player.getPatience() * 10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			int experienceGain = Tools.generateRandomInt(player.calcExperienceGain()[0], player.calcExperienceGain()[1]);
			int goldGain = Tools.generateRandomInt(player.calcGoldGain()[0], player.calcGoldGain()[1]);
			
			player.gainExperience(experienceGain);
			player.gainGold(goldGain);
			
			System.out.println("You've gained " + experienceGain + " experience.");
			System.out.println("You've gained " + goldGain + " gold.");
			
			if (player.levelUpIfPossible()) {
				System.out.println("\n" + Tools.makeBorderString("Congrats! You are now level " + player.getLevel() + "!"));
			}
		}
		
		System.out.println();
	}
	
	private void improveAttributes() {
		while (true) {
			System.out.println("Letter = improve with attr. points; Number = improve with gold");
			System.out.println("Gold: " + player.getGold() + "; AP: " + player.getAttributePoints());
			System.out.println("[A] [1] Intelligence: " + player.getIntelligence() + " (AP: " + Constants.intelligenceAttrPrice + ", Gold: " + attrImprover.calcIntImprovementPrice() + ")");
			System.out.println("[B] [2] Dexterity:    " + player.getDexterity() + " (AP: " + Constants.dexterityAttrPrice + ", Gold: " + attrImprover.calcDexImprovementPrice() + ")");
			System.out.println("[C] [3] Endurance:    " + player.getEndurance() + " (AP: " + Constants.enduranceAttrPrice + ", Gold: " + attrImprover.calcEnduImprovementPrice() + ")");
			System.out.println("[D] [4] Patience:     " + player.getPatience() + " (AP: " + Constants.patienceAttrPrice + ", Gold: " + attrImprover.calcPatImprovementPrice() + ")");
			System.out.println("[0] Leave ");
			
			System.out.print("Input: ");
			String input = scanner.nextLine().trim();
			System.out.println();
			
			boolean purchaseSuccessful = false;
			
			// improve with attribute points
			if (input.equals("A")) {
				purchaseSuccessful = attrImprover.imprIntelligenceWithAttrPoints();
			} else if (input.equals("B")) {
				purchaseSuccessful = attrImprover.imprDexterityWithAttrPoints();
			} else if (input.equals("C")) {
				purchaseSuccessful = attrImprover.imprEnduranceWithAttrPoints();
			} else if (input.equals("D")) {
				if (player.getPatience() < 100) {
					purchaseSuccessful = attrImprover.imprPatienceWithAttributePoints();
				} else {
					System.out.println("You can't improve this attribute further.");
				}
			}
			
			// improve with gold
			else if (input.equals("1")) {
				
				purchaseSuccessful = attrImprover.imprIntelligenceWithGold();
			} else if (input.equals("2")) {
				purchaseSuccessful = attrImprover.imprDexterityWithGold();
			} else if (input.equals("3")) {
				purchaseSuccessful = attrImprover.imprEnduranceWithGold();
			} else if (input.equals("4")) {
				if (player.getPatience() < 100) {
					purchaseSuccessful = attrImprover.imprPatienceWithGold();
				} else {
					System.out.println("You can't improve this attribute further.");
				}
			} else if (input.equals("0")) {
				return;
			} else {
				System.out.println("You input is invalid\n");
				continue;
			}
			
			if (purchaseSuccessful) {
				System.out.println("Purchase successful!");
			} else {
				System.out.println("Not enough gold or attribute Points!");
			}
			System.out.println();
			Main.savePlayer(player);
		}
	}
	
	private void showCharacter() {
		System.out.println(player + "\n");
	}
}

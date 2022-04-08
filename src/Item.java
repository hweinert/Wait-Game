import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.io.Serializable;
import littlelib.Tools;

public class Item implements Serializable {
	private static final long serialVersionUID = -8081502569795662803L;
	
	public static final String[] types = new String[] {"Robe", "Ring", "Amulet"};
	public static final String[] qualityTypes = new String[] {"Ordinary", "Good", "Legendary"};
	
	private static final String[][] names = new String[][] {
		{"Pajama", "Priest Robe", "Suit with Tie", "Bathrobe", "Turtleneck Pullover", "Pullover", "Gap", "Gulf"},
		{"Wedding Band", "Gold Band", "Silver Band", "Smart Band", "Apple IBand"},
		{"Gold Amulet", "Silver Amulet", "Wood Amulet", "Diadem", "Coronet", "Tiara"}
	};
	
	private static final String[] descriptions = new String[] {
			"of Happiness", "of eternal Love", "of Horror", "of Lover", "of the cunning", "of the cutthroat",
			"of Wisdom", "of Spaghetti", "of Patience", "of Anger", "of Freedom", "of the Swagger"
	};
	
	private String name;
	private String type;
	private String qualityType;
	private int price;
	
	private int minGoldBonus;
	private int maxGoldBonus;
	private int minExpBonus;
	private int maxExpBonus;
	
	private int intBonus;
	private int dexBonus;
	private int enduBonus;
	private int patBonus;
	
	// i do not want to build a constructor with a giant parameter list, so i init all values here
	public static Item makeRandomByPlayerStats(Player player) {
		Item item = new Item();
		
		int chosenType = Tools.generateRandomInt(0, types.length-1);
		item.type = types[chosenType];
		
		String qualityType;
		int qualityLevel = 0; // is later needed for bonus value determination
		int random = Tools.generateRandomInt(1, 100);
		if (random > 98) {
			item.qualityType = qualityTypes[2];
			qualityLevel = 3;
		} else if (random > 85) {
			item.qualityType = qualityTypes[1];
			qualityLevel = 2;
		} else {
			item.qualityType = qualityTypes[0];
			qualityLevel = 1;
		}	
		
		String name = names[chosenType][Tools.generateRandomInt(0, names[chosenType].length-1)];
		name += " " + descriptions[Tools.generateRandomInt(0, descriptions.length-1)];
		name += " [" + item.qualityType + "]";
		item.name = name;
		
		// determine number of boni
		int numOfBoni = 0;
		if (item.qualityType.equals(qualityTypes[0])) {
			numOfBoni = Tools.generateRandomInt(2, 3);
		} else if (item.qualityType.equals(qualityTypes[1])) {
			numOfBoni = Tools.generateRandomInt(4, 5);
		} else if (item.qualityType.equals(qualityTypes[2])) {
			numOfBoni = Tools.generateRandomInt(6, 8);
		}
		
		// determine which boni the item should have; and its price
		AttributeImprover attrImprover = player.getAttributeImprover();
		int boniAmountSoFar = 0;
		int possibleBoniAmount = 8;
		List<Integer> boniGiven = new ArrayList<>();
		while (boniAmountSoFar < numOfBoni) {
			int chosenBoni = Tools.generateRandomInt(0, possibleBoniAmount-1);
			if (boniGiven.contains(chosenBoni)) {
				continue;
			}
			
			else if (chosenBoni == 0) { 
				int minGoldBonusMin = (int) Math.round(player.calcGoldGain()[1] * 0.1 * qualityLevel);
				int minGoldBonuxMax = (int) Math.round(player.calcGoldGain()[1] * 0.15 * qualityLevel);
				item.minGoldBonus = Tools.generateRandomInt(minGoldBonusMin, minGoldBonuxMax);
				item.price += attrImprover.calcDexImprovementPrice();
			} else if (chosenBoni == 1) {
				int maxGoldBonusMin = (int) Math.round(player.calcGoldGain()[1] * 0.1 * qualityLevel);
				int maxGoldBonusMax = (int) Math.round(player.calcGoldGain()[1] * 0.15 * qualityLevel);
				item.maxGoldBonus = Tools.generateRandomInt(maxGoldBonusMin, maxGoldBonusMax);
				item.price += attrImprover.calcDexImprovementPrice();
			} else if (chosenBoni == 2) {
				int minExpBonusMin = (int) Math.round(player.calcExperienceGain()[1] * 0.1 * qualityLevel);
				int minExpBonusMax = (int) Math.round(player.calcExperienceGain()[1] * 0.15 * qualityLevel);
				item.minExpBonus = Tools.generateRandomInt(minExpBonusMin, minExpBonusMax);
				item.price += attrImprover.calcIntImprovementPrice();
			} else if (chosenBoni == 3) {
				int maxExpBonusMin = (int) Math.round(player.calcExperienceGain()[1] * 0.1 * qualityLevel);
				int maxExpBonusMax = (int) Math.round(player.calcExperienceGain()[1] * 0.15 * qualityLevel);
				item.maxExpBonus = Tools.generateRandomInt(maxExpBonusMin, maxExpBonusMax);
				item.price += attrImprover.calcIntImprovementPrice();
			} else if (chosenBoni == 4) {
				int minIntBonus = (int) Math.round(player.getIntelligence() * 0.1 * qualityLevel);
				int maxIntBonus = (int) Math.round(player.getIntelligence() * 0.15 * qualityLevel);
				item.intBonus = Tools.generateRandomInt(minIntBonus, maxIntBonus) + 1;
				item.price += attrImprover.calcIntImprovementPrice() * 0.1 * item.intBonus;
			} else if (chosenBoni == 5) {
				int minDexBonus = (int) Math.round(player.getDexterity() * 0.1 * qualityLevel);
				int maxDexBonus = (int) Math.round(player.getDexterity() * 0.15 * qualityLevel);
				item.dexBonus = Tools.generateRandomInt(minDexBonus, maxDexBonus) + 1;
				item.price += attrImprover.calcDexImprovementPrice() * 0.1 * item.dexBonus;
			} else if (chosenBoni == 6) {
				int minEnduBonus = (int) Math.round(player.getEndurance() * 0.1 * qualityLevel);
				int maxEnduBonus = (int) Math.round(player.getEndurance() * 0.15 * qualityLevel);
				item.enduBonus = Tools.generateRandomInt(minEnduBonus, maxEnduBonus) + 1;
				item.price += attrImprover.calcEnduImprovementPrice() * 0.1 * item.enduBonus;
			} else if (chosenBoni == 7) {
				int minPatBonus = 1 * qualityLevel;
				int maxPatBonus = 3 * qualityLevel;
				// patience improvement price can be giant, so the player gets int impr price instead
				item.price += attrImprover.calcIntImprovementPrice();
				item.patBonus = Tools.generateRandomInt(minPatBonus, maxPatBonus);
			}
			
			boniGiven.add(chosenBoni);
			boniAmountSoFar++;
		}
		
		return item;
	}
	
	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public String getQualityType() {
		return qualityType;
	}

	public int getPrice() {
		return price;
	}

	public int getMinGoldBonus() {
		return minGoldBonus;
	}

	public int getMaxGoldBonus() {
		return maxGoldBonus;
	}

	public int getMinExpBonus() {
		return minExpBonus;
	}

	public int getMaxExpBonus() {
		return maxExpBonus;
	}

	public int getIntBonus() {
		return intBonus;
	}

	public int getDexBonus() {
		return dexBonus;
	}

	public int getEnduBonus() {
		return enduBonus;
	}

	public int getPatBonus() {
		return patBonus;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(name + " ");
		
		List<String> attributes = new ArrayList<>();
		
		if (minGoldBonus > 0) {
			attributes.add("Min-Gold + " + minGoldBonus);
		} 
		if (maxGoldBonus > 0) {
			attributes.add("Max-Gold + " + maxGoldBonus);
		} 
		if (minExpBonus > 0) {
			attributes.add("Min-Exp + " + minExpBonus);
		} 
		if (maxExpBonus > 0) {
			attributes.add("Max-Exp + " + maxExpBonus);
		} 
		if (intBonus > 0) {
			attributes.add("Int + " + intBonus);
		} 
		if (dexBonus > 0) {
			attributes.add("Dex + " + dexBonus);
		} 
		if (enduBonus > 0) {
			attributes.add("Endu + " + enduBonus);
		} 
		if (patBonus > 0) {
			attributes.add("Pat + " + patBonus);
		}
		
		return sb.append(String.join(", ", attributes)).toString() + " (Value: " + price + " Gold)";
	}
}

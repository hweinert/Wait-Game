import java.io.File;
import java.io.Serializable;

public class Player implements Serializable {
	private static final long serialVersionUID = -5405016952686912472L;
	private int level;
	private int experience;
	private int gold;
	private int attributePoints;

	// skills
	private int intelligence; // improves experience gain
	private int dexterity; // improves gold gain
	private int endurance; // extends wait time
	private int patience; // shortens wait time duration
	private int lookiness; // find items more often
	private int muscularity; // increase number of item slots
	
	private AttributeImprover attrImprover;
	private ItemManager itemManager;

	private Player(int level, int experience, int gold, int attributePoints, int intelligence, int dexterity, 
			       int endurance, int patience, int lookiness, int muscularity) {
		this.level = level;
		this.experience = experience;
		this.gold = gold;
		this.attributePoints = attributePoints;

		this.intelligence = intelligence;
		this.dexterity = dexterity;
		this.endurance = endurance;
		this.patience = patience;
		this.lookiness = lookiness;
		this.muscularity = muscularity;
	}
	
	private Player() {
		this(1, 0, 0, 0, 0, 0, 1, 0, 0, 0);
	}
	
	public static Player instanceOf() {
		File f = new File(Constants.saveFile);
		if (f.exists()) {
			return Main.loadPlayer();
		} else {
			return new Player();
		}
	}

	public int getLevel() {
		return level;
	}

	public int getExperience() {
		return experience;
	}
	
	public int getAttributePoints() {
		return attributePoints;
	}
	
	public int getGold() {
		return gold;
	}
	
	public int getIntelligence() {
		return intelligence;
	}
	
	public int getDexterity() {
		return dexterity;
	}
	
	public int getEndurance() {
		return endurance;
	}
	
	public int getPatience() {
		return patience;
	}
	
	public int getLookiness() {
		return lookiness;
	}
	
	public int getMuscularity() {
		return muscularity;
	}
	
	public int getTotalIntelligence() {
		return intelligence + itemManager.calcTotalIntBonus();
	}
	
	public int getTotalDexterity() {
		return dexterity + itemManager.calcTotalDexBonus();
	}
	
	public int getTotalEndurance() {
		return endurance + itemManager.calcTotalEnduBonus();
	}
	
	public int getTotalPatience() {
		return patience + itemManager.calcTotalPatBonus();
	}
	
	public AttributeImprover getAttributeImprover() {
		return attrImprover;
	}
	
	public void setAttributeImprover(AttributeImprover attrImprover) {
		this.attrImprover = attrImprover;
	}
	
	public ItemManager getItemManager() {
		return this.itemManager;
	}
	
	public void setItemManager(ItemManager itemManager) {
		if (this.itemManager == null) {
			this.itemManager = itemManager;
		}
	}
	
	public void gainExperience(int value) {
		experience += value;
	}
	
	public void gainGold(int value) {
		gold += value;
	}
	
	public int[] calcExperienceGain() {
		double minDouble = (Constants.expGain + getTotalIntelligence()) * (1 + (getLevel() * 3) / 100.0)
				+ itemManager.calcTotalMinExpBons();
		double maxDouble = (Constants.expGain + getTotalIntelligence()) * (1 + (getLevel() * 3) / 100.0) * 2
				+ itemManager.calcTotalMaxExpBonus();
		int min = (int) Math.round(minDouble);
		int max = (int) Math.round(maxDouble);
		return new int[] {min, max};
	}
	
	public int[] calcGoldGain() {
		double minDouble = (Constants.expGain + getTotalDexterity()) * (1 + (getLevel() * 3) / 100.0) 
				+ itemManager.calcTotalMinGoldBonus();
		double maxDouble = (Constants.expGain + getTotalDexterity()) * (1 + (getLevel() * 3) / 100.0) * 2
				+ itemManager.calcTotalMaxGoldBonus();
		int min = (int) Math.round(minDouble);
		int max = (int) Math.round(maxDouble);
		return new int[] {min, max};
	}

	public int calcExperienceNeeded() {
		return level * level * Constants.levelUpExpNeeded + level * Constants.levelUpExpNeeded;
	}
	
	public boolean levelUpIfPossible() {
		boolean returnValue = false;
		while (experience >= calcExperienceNeeded()) {
			experience -= calcExperienceNeeded();
			level++;
			attributePoints += 2;
			returnValue = true;
		}
		return returnValue;
	}
	
	public boolean payGold(int value) {
		if (value <= gold) {
			gold -= value;
			return true;
		}
		
		return false;
	}
	
	public boolean payAttributePoints(int value) {
		if (value <= attributePoints) {
			attributePoints -= value;
			return true;
		}
		
		return false;
	}
	
	public void improveEndurance(int value) {
		endurance += value;
	}
	
	public void improveIntelligence(int value) {
		intelligence += value;
	}
	
	public void improveDexterity(int value) {
		dexterity += value;
	}
	
	public void improvePatience(int value) {
		patience += value;
	}
	
	public void improveLookiness(int value) {
		lookiness += value;
	}
	
	public void improveMuscularity(int value) {
		muscularity += value;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Level             " + level + "\n");
		sb.append("Experience:       " + experience + " / " + calcExperienceNeeded() + "\n");
		sb.append("Attribute Points: " + attributePoints + "\n");
		sb.append("Gold:             " + gold + "\n\n");
		
		sb.append("----- Attributes (item boni included)-----\n");
		sb.append("Intelligence: " + getTotalIntelligence() + " (improves experience gain)\n");
		sb.append("Dexterity:    " + getTotalDexterity() + " (improves gold gain)\n");
		sb.append("Endurance:    " + getTotalEndurance() + " (extends wait time)\n");
		sb.append("Patience:     " + getTotalPatience() + " (shortens wait time duration by 1%)\n");
		sb.append("Lookiness:    " + lookiness + " (find items more often)\n");
		sb.append("Muscularity:  " + muscularity + " (increases item slots)\n\n");
				
		sb.append("Experience Gain: " + calcExperienceGain()[0] + " - " + calcExperienceGain()[1] + "\n");
		sb.append("Gold Gain:       " + calcGoldGain()[0] + " - " + calcGoldGain()[1]);
		return sb.toString();
	}
}
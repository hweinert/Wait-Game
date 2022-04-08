import java.io.Serializable;

public class AttributeImprover implements Serializable {
	private static final long serialVersionUID = 7912180931788682915L;
	
	private Player player;
	
	public AttributeImprover(Player player) {
		this.player = player;
	}
	
	public int calcIntImprovementPrice() {
		return (player.getIntelligence()+1) * (player.getIntelligence()+1) * Constants.intelligencePrice 
				+ (player.getIntelligence()+1) * Constants.intelligencePrice;
	}

	public int calcDexImprovementPrice() {
		return (player.getDexterity()+1) * (player.getDexterity()+1) * Constants.dexterityPrice 
				+ (player.getDexterity()+1) * Constants.dexterityPrice;
	}
	
	public int calcEnduImprovementPrice() {
		return player.getEndurance() * player.getEndurance() * Constants.endurancePrice 
				+ player.getEndurance() * Constants.endurancePrice;
	}

	public int calcPatImprovementPrice() {
		return (int) (Constants.patiencePrice * Math.pow(2, player.getPatience()));
	}
	
	public int calcLookImprovementPrice() {
		return (player.getLookiness()+1) * (player.getLookiness()+1) * Constants.lookinessPrice
				+ (player.getLookiness()+1) * Constants.lookinessPrice;
	}
	
	public int calcMuscImprovementPrice() {
		return (player.getMuscularity()+1) * (player.getMuscularity()+1) * Constants.muscularityPrice
				+ (player.getMuscularity()+1)  * Constants.muscularityPrice;
	}

	public boolean imprIntelligenceWithAttrPoints() {
		if (player.payAttributePoints(Constants.intelligenceAttrPrice)) {
			player.improveIntelligence(1);
			return true;
		}
		return false;
	}

	public boolean imprIntelligenceWithGold() {
		if (player.payGold(calcIntImprovementPrice())) {
			player.improveIntelligence(1);
			return true;
		}
		return false;
	}

	public boolean imprDexterityWithAttrPoints() {
		if (player.payAttributePoints(Constants.dexterityAttrPrice)) {
			player.improveDexterity(1);
			return true;
		}
		return false;
	}
	
	public boolean imprDexterityWithGold() {
		if (player.payGold(calcDexImprovementPrice())) {
			player.improveDexterity(1);
			return true;
		}
		return false;
	}
	
	public boolean imprEnduranceWithGold() {
		if (player.payGold(calcEnduImprovementPrice())) {
			player.improveEndurance(1);
			return true;
		}
		return false;
	}
	
	public boolean imprEnduranceWithAttrPoints() {
		if (player.payAttributePoints(Constants.enduranceAttrPrice)) {
			player.improveEndurance(1);
			return true;
		}
		return false;
	}

	public boolean imprPatienceWithGold() {
		if (player.payGold(calcPatImprovementPrice())) {
			player.improvePatience(1);
			return true;
		}
		return false;
	}
	
	public boolean imprPatienceWithAttributePoints() {
		if (player.payAttributePoints(Constants.patienceAttrPrice)) {
			player.improvePatience(1);
			return true;
		}
		return false;
	}
	
	public boolean imprLookinessWithGold() {
		if (player.payGold(calcLookImprovementPrice())) {
			player.improveLookiness(1);
			return true;
		}
		return false;
	}
	
	public boolean imprLookinessWithAttributePoints() {
		if (player.payAttributePoints(Constants.lookinessAttrPrice)) {
			player.improveLookiness(1);
			return true;
		}
		return false;
	}
	
	public boolean imprMuscularityWithGold() {
		if (player.payGold(calcMuscImprovementPrice())) {
			player.improveMuscularity(1);
			return true;
		}
		return false;
	}
	
	public boolean imprMuscularityWithAttributePoints() {
		if (player.payAttributePoints(Constants.muscularityAttrPrice)) {
			player.improveMuscularity(1);
			return true;
		}
		return false;
	}
}

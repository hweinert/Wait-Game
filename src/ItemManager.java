import java.io.Serializable;
import java.util.List;

import littlelib.Tools;

import java.util.ArrayList;

public class ItemManager implements Serializable {
	private static final long serialVersionUID = 2506546218175599182L;
	
	private Player player;
	private Item equippedRobe;
	private Item equippedAmulet;
	private Item equippedRing;
	private List<Item> slots;
	private int slotLimit;
	
	public ItemManager(int slotLimit) {
		this.equippedRobe = null;
		this.equippedAmulet = null;
		this.equippedRing = null;
		this.slots = new ArrayList<>();
		this.slotLimit = slotLimit;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public List<Item> getSlots() {
		return slots;
	}
	
	public void equipItem(Item item) {
		if (item.getType().equals(Item.types[0])) {
			if (equippedRobe != null) {
				slots.add(equippedRobe);
			} 
			equippedRobe = item;
		} else if (item.getType().equals(Item.types[1])) {
			if (equippedRing != null) {
				slots.add(equippedRing);
			}
			equippedRing = item;
		} else if (item.getType().equals(Item.types[2])) {
			if (equippedAmulet != null) {
				slots.add(equippedAmulet);
			}
			equippedAmulet = item;
		}
	}
	
	public Item obtainItem(Item item) {
		slots.remove(item);
		return item;
	}
	
	// returns true if item was successfully added.
	public boolean storeItem(Item item) {
		if (slots.size() >= slotLimit) {
			return false;
		}
		
		slots.add(item);
		return true;
	}
	
	public void increaseSlotLimit(int value) {
		slotLimit += value;
	}
	
	public void manageInventory() {
		while (true) {
			StringBuilder equippedSb = new StringBuilder();
			equippedSb.append("Equipped:\n");
			equippedSb.append("Robe:   " + (equippedRobe != null ? equippedRobe.toString() : "empty") + "\n");
			equippedSb.append("Ring:   " + (equippedRing != null ? equippedRing.toString() : "empty") + "\n");
			equippedSb.append("Amulet: " + (equippedAmulet != null ? equippedAmulet.toString() : "empty") + "\n");
			
			StringBuilder slotSb = new StringBuilder();
			slotSb.append("Stored in bagpack:\n");
			if (slots.size() > 0) {
				for (int i = 1; i <= slots.size(); i++) {
					slotSb.append("[" + i + "] " + slots.get(i-1).getType() + ": " + slots.get(i-1).toString() + "\n");
				}
			} else {
				slotSb.append("You have no items in your bagpack.\n");
			}
			
			System.out.println(Tools.makeBorder(equippedSb.toString()) + "\n");
			System.out.println(Tools.makeBorder(slotSb.toString() + 
					slots.size() + " of " + this.slotLimit + " item slots used.") + "\n");
			
			if (slots.size() == 0) {
				return;
			}
			
			System.out.println("[0] Leave");
			int input = Tools.saveIntInput("Input: ", 0, slots.size());
			System.out.println();
			
			Item item = null;
			if (input != 0) {
				item = slots.get(input-1);
				itemMenue(item);
			} else {
				return;
			}
		}
	}
	
	private void itemMenue(Item item) {
		while (true) {
			if (item.getType().equals(Item.types[0])) {
				System.out.println("Equipped: " + (equippedRobe != null ? equippedRobe.toString() : "empty"));
			} else if (item.getType().equals(Item.types[1])) {
				System.out.println("Equipped: " + (equippedRing != null ? equippedRing.toString() : "empty"));
			} else if (item.getType().equals(Item.types[2])) {
				System.out.println("Equipped: " + (equippedAmulet != null ? equippedAmulet.toString() : "empty"));
			}
			System.out.println("Choice:   " + item.toString());
			System.out.println();
			
			System.out.println("[1] Equip this item");
			System.out.println("[2] Sell this item");
			System.out.println("[0] Back to inventory");
			int input = Tools.saveIntInput("Input: ", 0, 2);
			
			if (input == 1) {
				obtainItem(item);
				this.equipItem(item);
				System.out.println();
				Main.savePlayer(player);
				return;
			} else if (input == 2) {
				obtainItem(item);
				player.gainGold(item.getPrice());
				System.out.println("\n" + Tools.makeBorder("You got " + item.getPrice() + " gold!") + "\n");
				Main.savePlayer(player);
				return;
			} else if (input == 0) {
				System.out.println();
				return;
			}
		}
	}
	
	// bonus methods
	
	public int calcTotalMinGoldBonus() {
		int returnValue = 0;
		returnValue += equippedRobe != null ? equippedRobe.getMinGoldBonus() : 0;
		returnValue += equippedRing != null ? equippedRing.getMinGoldBonus() : 0;
		returnValue += equippedAmulet != null ? equippedAmulet.getMinGoldBonus() : 0;
		return returnValue;
	}
	
	public int calcTotalMaxGoldBonus() {
		int returnValue = 0;
		returnValue += equippedRobe != null ? equippedRobe.getMaxGoldBonus() : 0;
		returnValue += equippedRing != null ? equippedRing.getMaxGoldBonus() : 0;
		returnValue += equippedAmulet != null ? equippedAmulet.getMaxGoldBonus() : 0;
		return returnValue;
	}
	
	public int calcTotalMinExpBons() {
		int returnValue = 0;
		returnValue += equippedRobe != null ? equippedRobe.getMinExpBonus() : 0;
		returnValue += equippedRing != null ? equippedRing.getMinExpBonus() : 0;
		returnValue += equippedAmulet != null ? equippedAmulet.getMinExpBonus() : 0;
		return returnValue;
	}
	
	public int calcTotalMaxExpBonus() {
		int returnValue = 0;
		returnValue += equippedRobe != null ? equippedRobe.getMaxExpBonus() : 0;
		returnValue += equippedRing != null ? equippedRing.getMaxExpBonus() : 0;
		returnValue += equippedAmulet != null ? equippedAmulet.getMaxExpBonus() : 0;
		return returnValue;				
	}
	
	public int calcTotalIntBonus() {
		int returnValue = 0;
		returnValue += equippedRobe != null ? equippedRobe.getIntBonus() : 0;
		returnValue += equippedRing != null ? equippedRing.getIntBonus() : 0;
		returnValue += equippedAmulet != null ? equippedAmulet.getIntBonus() : 0;
		return returnValue;
	}
	
	public int calcTotalDexBonus() {
		int returnValue = 0;
		returnValue += equippedRobe != null ? equippedRobe.getDexBonus() : 0;
		returnValue += equippedRing != null ? equippedRing.getDexBonus() : 0;
		returnValue += equippedAmulet != null ? equippedAmulet.getDexBonus() : 0;
		return returnValue;
	}
	
	public int calcTotalEnduBonus() {
		int returnValue = 0;
		returnValue += equippedRobe != null ? equippedRobe.getEnduBonus() : 0;
		returnValue += equippedRing != null ? equippedRing.getEnduBonus() : 0;
		returnValue += equippedAmulet != null ? equippedAmulet.getEnduBonus() : 0;
		return returnValue;
	}
	
	public int calcTotalPatBonus() {
		int returnValue = 0;
		returnValue += equippedRobe != null ? equippedRobe.getPatBonus() : 0;
		returnValue += equippedRing != null ? equippedRing.getPatBonus() : 0;
		returnValue += equippedAmulet != null ? equippedAmulet.getPatBonus() : 0;
		return returnValue;
	}
}

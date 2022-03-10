import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import littlelib.Tools;

public class Main {
	public static void main(String[] args) {
		Game game = new Game();
		game.mainLoop();
	}
	
	public static void savePlayer(Player player) {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(Constants.saveFile))) {
			oos.writeObject(player);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Player loadPlayer() {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(Constants.saveFile))) {
			return (Player) ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
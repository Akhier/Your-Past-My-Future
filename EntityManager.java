/**
 * @author Akhier Dragonheart
 * @version 1.0
 */

import java.util.UUID;

public class EntityManager {
	public static String newId() {
		String nId = UUID.randomUUID().toString();
		ECS_Storage.Id.add(nId);
		return nId;
	}

	public static void removeId(String id) {
		ECS_Storage.Id.remove(id);
	}
}

/**
 * @author Akhier Dragonheart
 * @version 1.0
 */

import java.util.HashMap;

public class ComponentManager {
	public static HashMap<String, Object> mapOf(String componentname) {
		return ECS_Storage.Component.get(componentname);
	}

	public static void addComponent(String componentname, String id, Object component) {
		HashMap<String, Object> tempMap = mapOf(componentname);
		tempMap.put(id, component);
		ECS_Storage.Component.put(componentname, tempMap);
	}

	public static void removeComponent(String componentname, String id) {
		HashMap<String, Object> tempMap = mapOf(componentname);
		tempMap.remove(id);
		ECS_Storage.Component.put(componentname, tempMap);
	}

	public static void removeId(String id) {
		for(String key : ECS_Storage.Component.keySet()) {
			removeComponent(key, id);
		}
	}

	public static Object getComponent(String componentname, String id) {
		return ECS_Storage.Component.get(componentname).get(id);
	}

	public static boolean checkForComponent(String componentname, String id) {
		if(ECS_Storage.Component.get(componentname).get(id) != null) {
			return true;
		} else {
			return false;
		}
	}
}

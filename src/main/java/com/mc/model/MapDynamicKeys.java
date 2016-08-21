package com.mc.model;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class MapDynamicKeys {

	Map<String, String> map;
	
	public Map<String, String> getMap() {
		Objects.requireNonNull(map, "Map is mandatory");
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	public Set<String> keySet(){
		return map.keySet();
	}

}

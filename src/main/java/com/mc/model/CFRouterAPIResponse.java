package com.mc.model;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class CFRouterAPIResponse {

	public static final String REQUIRE_NON_NULL_MSG_MAP = "Map being populated before use is mandatory";

	private Map<String, CFRouterAPIInstanceDetails[]> map;
	
	private MultiValueMap<String, String> ipKeyedMap = new LinkedMultiValueMap<String, String>();
	
	public Map<String, CFRouterAPIInstanceDetails[]> getMap() {
		Objects.requireNonNull(map, REQUIRE_NON_NULL_MSG_MAP);
		return map;
	}

	@Deprecated // A builder pattern would be preferable here
	public void setMap(Map<String, CFRouterAPIInstanceDetails[]> map) {
		this.map = map;
	}

	public Set<String> keySet(){
		Objects.requireNonNull(map, REQUIRE_NON_NULL_MSG_MAP);
		return map.keySet();
	}

}

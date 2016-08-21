package com.mc.model;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class CFRouterAPIResponseWrapper {

	private MultiValueMap<String, String> ipKeyedMap = new LinkedMultiValueMap<String, String>();
	private CFRouterAPIResponse cfRouterAPIResponse;

	// For Json only
	@Deprecated
	public CFRouterAPIResponseWrapper() {

	}

	public CFRouterAPIResponseWrapper(CFRouterAPIResponse cfRouterAPIResponse) {
		super();
		this.cfRouterAPIResponse = cfRouterAPIResponse;
	}

	public List<String> getRoutesForIP(String string) {
		Assert.notEmpty(ipKeyedMap);
		return ipKeyedMap.get(string);
	}

	public void generateMappings() {
		Objects.requireNonNull(cfRouterAPIResponse);
		Map<String, CFRouterAPIInstanceDetails[]> map = cfRouterAPIResponse.getMap();
		Objects.requireNonNull(map, CFRouterAPIResponse.REQUIRE_NON_NULL_MSG_MAP);
		addToMapViaStream(map);
	}

	@Deprecated
	private void addToMapOldSchoolStyle(Map<String, CFRouterAPIInstanceDetails[]> map) {
		Set<Entry<String, CFRouterAPIInstanceDetails[]>> entrySet = map.entrySet();
		for (Entry<String, CFRouterAPIInstanceDetails[]> entry : entrySet) {
			CFRouterAPIInstanceDetails[] instanceDetailsArray = entry.getValue();
			for (CFRouterAPIInstanceDetails cfRouterAPIInstanceDetails : instanceDetailsArray) {
				ipKeyedMap.add(extractIp(cfRouterAPIInstanceDetails.getAddress()), entry.getKey());
			}
		}
	}
	
	private void addToMapViaStream(Map<String, CFRouterAPIInstanceDetails[]> map) {
		map.entrySet().stream().forEach(e -> {
			CFRouterAPIInstanceDetails[] details = e.getValue();
			Arrays.stream(details).forEach(d -> {
				ipKeyedMap.add(extractIp(d.getAddress()), e.getKey());
			});
			;
		});
	}

	private String extractIp(String address) {
		return address.split(":")[0];
	}

}

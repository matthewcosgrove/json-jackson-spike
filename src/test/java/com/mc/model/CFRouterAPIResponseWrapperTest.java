package com.mc.model;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class CFRouterAPIResponseWrapperTest {

	private static final String ROUTE = "uaa.myhost.cf-app.com";

	private static final String IP_ADDRESS = "10.0.0.8";

	CFRouterAPIResponseWrapper unit;

	@Mock
	CFRouterAPIResponse mockedResponse;

	@Rule
	public MockitoRule mockitoRule = MockitoJUnit.rule();

	@Before
	public void setUp() {
		unit = new CFRouterAPIResponseWrapper(mockedResponse);
		Map<String, CFRouterAPIInstanceDetails[]> map = createNonEmptyMap();
		when(mockedResponse.getMap()).thenReturn(map);
		unit.generateMappings();
	}

	private static Map<String, CFRouterAPIInstanceDetails[]> createNonEmptyMap() {
		Map<String, CFRouterAPIInstanceDetails[]> map = new HashMap<String, CFRouterAPIInstanceDetails[]>();
		CFRouterAPIInstanceDetails details = new CFRouterAPIInstanceDetails();
		details.setAddress(IP_ADDRESS);
		CFRouterAPIInstanceDetails[] arr = {details};
		map.put(ROUTE, arr);
		return map;
	}

	@Test
	public void canGetRoutesFromIP() {
		List<String> routesForIP = unit.getRoutesForIP(IP_ADDRESS);
		verify(mockedResponse).getMap();
		assertTrue(routesForIP.contains(ROUTE));
	}
	
	@Test(expected=NullPointerException.class)
	public void instantiatedWithoutWrappedObject(){
		unit = new CFRouterAPIResponseWrapper();
		unit.generateMappings();
	}

}

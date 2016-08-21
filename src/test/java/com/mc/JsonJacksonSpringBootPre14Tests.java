package com.mc;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.mc.criteria.FullTestCriteria;
import com.mc.model.CFRouterAPIResponse;
import com.mc.model.CFRouterAPIResponseJsonStdDeserializer;
import com.mc.model.CFRouterAPIResponseWrapper;
import com.mc.model.CFRouterAPIResponseWrapperJsonStdDeserializer;
import com.mc.model.DeeplyNestedField;
import com.mc.model.DeeplyNestedFieldJsonStdDeserializer;
import com.mc.model.MapDynamicKeys;
import com.mc.model.MapDynamicKeysJsonDeserializer;
import com.mc.model.MapDynamicKeysJsonStdDeserializer;
import com.mc.model.TrivialMutableResponse;

@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = JsonJacksonSpikeApplication.class)
public class JsonJacksonSpringBootPre14Tests implements FullTestCriteria {

	private static final String IP_FOR_DOPPLER_AND_LOGGREGATOR = "10.0.0.21";
	private static final String ROUTE_FOR_UAA = "uaa.myhost.cf-app.com";
	private static final String IP_FOR_UAA = "10.0.0.15";
	private static final Object ROUTE_FOR_LOGGREGATOR = "loggregator.myhost.cf-app.com";
	private static final Object ROUTE_FOR_DOPPLER = "doppler.myhost.cf-app.com";
	ObjectMapper objectMapper;

	@Before
	public void setUp() throws Exception {
		objectMapper = Jackson2ObjectMapperBuilder.json()
				.deserializerByType(MapDynamicKeys.class, new MapDynamicKeysJsonStdDeserializer())
				.deserializerByType(DeeplyNestedField.class, new DeeplyNestedFieldJsonStdDeserializer())
				.deserializerByType(CFRouterAPIResponse.class, new CFRouterAPIResponseJsonStdDeserializer())
				.deserializerByType(CFRouterAPIResponseWrapper.class, new CFRouterAPIResponseWrapperJsonStdDeserializer())
				.build();
	}

	@Override
	@Test
	public void canDeserializeWithSimpleCleanJson() throws Exception {
		InputStream json = TestUtils.retrieveJsonFromClasspathAsInputStream("simple-clean-json.json");
		// TODO: How to enable scanning of annotation @JsonCreator to detect
		// constructor on immutable value object?
		// See TrivialResponse.class
		TrivialMutableResponse response = objectMapper.readValue(json, TrivialMutableResponse.class);
		assertNotNull(response.getBody());
		assertTrue(response.getBody().startsWith("Hello"));
	}

	@Test
	public void canDeserializeWithDynamicKeys() throws Exception {
		String json = TestUtils.retrieveJsonFromClasspathAsString("custom-map-dynamic-keys-simple.json");
		MapDynamicKeys response = objectMapper.readValue(json, MapDynamicKeys.class);
		assertNotNull(response);
		assertNotNull(response.keySet());
		assertTrue(response.keySet().contains("random-dynamic-key-1"));
		assertTrue(response.keySet().size() == 2);
	}

	@Override
	@Test
	public void canDeserializeNestedFields() throws Exception {
		String json = TestUtils.retrieveJsonFromClasspathAsString("deeply-nested-json-field.json");
		DeeplyNestedField response = objectMapper.readValue(json, DeeplyNestedField.class);
		assertNotNull(response);
		assertNotNull(response.getDeeplyNestedField());
		assertTrue(response.getDeeplyNestedField().equals("deeplyNestedValue"));
	}

	@Override
	@Test
	public void canDeserializeWithSimpleModule() throws Exception {
		// here we show what will happen behind the scenes when registering with
		// Jackson2ObjectMapperBuilder.deserializerByType()
		objectMapper = Jackson2ObjectMapperBuilder.json().build();
		SimpleModule sm = new SimpleModule();
		sm.addDeserializer(MapDynamicKeys.class, new MapDynamicKeysJsonDeserializer());
		objectMapper.registerModule(sm);
		String json = TestUtils.retrieveJsonFromClasspathAsString("custom-map-dynamic-keys-simple.json");
		MapDynamicKeys response = objectMapper.readValue(json, MapDynamicKeys.class);
		assertNotNull(response);
		assertNotNull(response.keySet());
		assertTrue(response.keySet().contains("random-dynamic-key-1"));
		assertTrue(response.keySet().size() == 2);
	}

	@Override
	@Test
	public void canDeserializeCFRouterTables() throws Exception {
		String json = TestUtils.retrieveJsonFromClasspathAsString("cf-router-api-response.json");
		CFRouterAPIResponse response = objectMapper.readValue(json, CFRouterAPIResponse.class);
		assertNotNull(response);
		assertNotNull(response.keySet());
		assertTrue(response.keySet().stream().anyMatch(e -> e.startsWith("uaa")));
		assertTrue(response.keySet().size() == 29);
		assertTrue(response.getMap().get(ROUTE_FOR_UAA).length == 1);
		assertTrue(response.getMap().get(ROUTE_FOR_UAA)[0].getAddress().startsWith(IP_FOR_UAA));
	}

	@Override
	@Test
	public void canDeserializeCFRouterTablesAndRetrieveByIP() throws Exception {
		String json = TestUtils.retrieveJsonFromClasspathAsString("cf-router-api-response.json");
		CFRouterAPIResponseWrapper response = objectMapper.readValue(json, CFRouterAPIResponseWrapper.class);
		assertNotNull(response);
		List<String> routesForIP = response.getRoutesForIP(IP_FOR_UAA);
		assertNotNull(routesForIP);
		assertTrue(routesForIP.contains(ROUTE_FOR_UAA));
		routesForIP = response.getRoutesForIP(IP_FOR_DOPPLER_AND_LOGGREGATOR);
		assertNotNull(routesForIP);
		assertTrue(routesForIP.contains(ROUTE_FOR_DOPPLER) && routesForIP.contains(ROUTE_FOR_LOGGREGATOR));
	}

}

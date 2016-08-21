package com.mc.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class MapDynamicKeysJsonStdDeserializer extends StdDeserializer<MapDynamicKeys> {

	public MapDynamicKeysJsonStdDeserializer() {
		super(MapDynamicKeys.class);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 6017767639544143853L;

	@Override
	public MapDynamicKeys deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		JsonNode node = p.getCodec().readTree(p);
		Map<String, String> map = new HashMap<String, String>();
		Iterator<String> iterator = node.fieldNames();
		while(iterator.hasNext()){
			String key = iterator.next();
			map.put(key, node.get(key).textValue());
		}
		MapDynamicKeys mapDynamicKeys = new MapDynamicKeys();
		mapDynamicKeys.setMap(map);
		return mapDynamicKeys;
	}
}

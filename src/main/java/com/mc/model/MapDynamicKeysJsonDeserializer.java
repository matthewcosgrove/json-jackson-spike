package com.mc.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;


/**
 * TODO: Understand why this following sentence is in the javadoc of JsonDeserializer
 * 
 *<p>
 * Custom deserializers should usually not directly extend this class, (i.e. JsonDeserializer)
 * but instead extend {@link com.fasterxml.jackson.databind.deser.std.StdDeserializer}
 * (or its subtypes like {@link com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer}).
 *<p>
 *
 *@see MapDynamicKeysJsonStdDeserializer
 *
 */
@Deprecated
public class MapDynamicKeysJsonDeserializer extends JsonDeserializer<MapDynamicKeys> {

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

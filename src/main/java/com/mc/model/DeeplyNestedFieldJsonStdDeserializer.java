package com.mc.model;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class DeeplyNestedFieldJsonStdDeserializer extends StdDeserializer<DeeplyNestedField> {

	public DeeplyNestedFieldJsonStdDeserializer() {
		super(DeeplyNestedField.class);
	}

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * 
	 */
	private static final long serialVersionUID = 6017767639544143853L;

	@Override
	public DeeplyNestedField deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		JsonNode node = p.getCodec().readTree(p);
		String fieldName = "deeplyNestedField";
		JsonNode foundValue = node.findValue(fieldName);
		log.info(String.format("Found first value in JSON (%s) for key %s", foundValue.textValue(), fieldName));
		DeeplyNestedField deeplyNestedField = new DeeplyNestedField();
		deeplyNestedField.setDeeplyNestedField(foundValue.textValue());
		return deeplyNestedField;
	}
}

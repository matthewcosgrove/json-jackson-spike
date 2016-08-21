package com.mc.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class CFRouterAPIResponseJsonStdDeserializer extends StdDeserializer<CFRouterAPIResponse> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4529800655266950920L;

	public CFRouterAPIResponseJsonStdDeserializer() {
		super(CFRouterAPIResponse.class);
	}

	@Override
	public CFRouterAPIResponse deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		JsonNode node = p.getCodec().readTree(p);
		Map<String, CFRouterAPIInstanceDetails[]> map = new HashMap<String, CFRouterAPIInstanceDetails[]>();
		Iterator<String> iterator = node.fieldNames();
		while (iterator.hasNext()) {
			String key = iterator.next();
			JsonNode jsonNode = node.get(key);
			if(!jsonNode.isArray()){
				throw new IllegalArgumentException("Node should be array but is type: " + jsonNode.getNodeType());
			}
			Stream<JsonNode> stream = convertToStream(jsonNode);
			List<CFRouterAPIInstanceDetails> instanceDetails = stream.map(jsonNodeToCFRouterAPIDetails)
					.collect(Collectors.<CFRouterAPIInstanceDetails> toList());
			map.put(key, instanceDetails.toArray(new CFRouterAPIInstanceDetails[0]));
		}
		CFRouterAPIResponse response = new CFRouterAPIResponse();
		response.setMap(map);
		return response;
	}

	private Stream<JsonNode> convertToStream(JsonNode jsonNode) {
		Iterable<JsonNode> iterable = () -> jsonNode.elements();
		Stream<JsonNode> stream = StreamSupport.stream(iterable.spliterator(), false);
		return stream;
	}

	private Function<JsonNode, CFRouterAPIInstanceDetails> jsonNodeToCFRouterAPIDetails = new Function<JsonNode, CFRouterAPIInstanceDetails>() {

		public CFRouterAPIInstanceDetails apply(JsonNode node) {
			CFRouterAPIInstanceDetails details = new CFRouterAPIInstanceDetails();
			JsonNode address = node.get("address");
			details.setAddress(address.textValue());
			return details;
		}
	};

}

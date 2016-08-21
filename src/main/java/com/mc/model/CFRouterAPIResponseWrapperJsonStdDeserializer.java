package com.mc.model;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class CFRouterAPIResponseWrapperJsonStdDeserializer extends StdDeserializer<CFRouterAPIResponseWrapper> {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6207892765442313321L;
	
	private CFRouterAPIResponseJsonStdDeserializer wrappedDeserializer = new CFRouterAPIResponseJsonStdDeserializer();

	public CFRouterAPIResponseWrapperJsonStdDeserializer() {
		super(CFRouterAPIResponseWrapper.class);
	}

	@Override
	public CFRouterAPIResponseWrapper deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		CFRouterAPIResponse nakedResponse = wrappedDeserializer.deserialize(p, ctxt);
		CFRouterAPIResponseWrapper response = new CFRouterAPIResponseWrapper(nakedResponse);
		response.generateMappings();
		return response;
	}
}

package com.mc;

import java.io.IOException;

import org.springframework.boot.jackson.JsonComponent;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.boot.jackson.JsonObjectSerializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.mc.model.TrivialResponse;

@JsonComponent
public class TrivialResponseJsonComponent {

	public static class Serializer extends JsonObjectSerializer<TrivialResponse> {
		@Override
		protected void serializeObject(TrivialResponse object, JsonGenerator jGen, SerializerProvider sp)
				throws IOException {
			jGen.writeStringField("bob", object.getBody());
		}
	}
	
	public static class Deserializer extends JsonObjectDeserializer<TrivialResponse> {

		@Override
		protected TrivialResponse deserializeObject(JsonParser parser, DeserializationContext dcontext, ObjectCodec codec,
				JsonNode tree) throws IOException {
			String body = tree.get("body").asText();
			JsonNode requiredNode = getRequiredNode(tree, "body");
			return new TrivialResponse(body);
		}
		
	}
}

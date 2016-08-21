package com.mc;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.boot.test.json.ObjectContent;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mc.criteria.TestCriteriaForNestedFields;
import com.mc.model.DeeplyNestedField;
import com.mc.model.TrivialResponse;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@JsonTest
public class JsonJacksonSpringBootSliceTests implements TestCriteriaForNestedFields{

	private JacksonTester<TrivialResponse> json;
	private JacksonTester<DeeplyNestedField> jsonNestedFields;
	
    @Before
    @Deprecated // This should be handled by the framework surely?? Seems to be standard set up for JacksonTester
    public void setup() {
        ObjectMapper objectMapper = new ObjectMapper();
        JacksonTester.initFields(this, objectMapper);
    }
	
	@Test
	public void serialize() throws Exception {
		TrivialResponse response = new TrivialResponse("Somebody");
		JsonContent<TrivialResponse> write = json.write(response);
		System.out.println(write.getJson());
		// throws exception but fixed in 1.4.1
		// https://github.com/spring-projects/spring-boot/issues/6502
//		to overcome this problem in 1.4.0:
//
//			added dependency to com.google.code.gson:gson
//			in src/test/resources/application.yml I've added:
//			spring:
//			  autoconfigure.exclude: org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration
		assertThat(write).isEqualToJson("expected.json");
	}

	@Override
	public void canDeserializeNestedFields() throws Exception {
		// TODO Auto-generated method stub
//		ObjectContent<MyDeeplyNestedInJsonResponse> jsonObjectContent = jsonNestedFields.read("deeply-nested-json-field.json");
//		// TODO: Call JsonObjectDeserializer.getRequiredNode
		
	}

}

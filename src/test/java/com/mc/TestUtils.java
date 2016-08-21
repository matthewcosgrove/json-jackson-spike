package com.mc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class TestUtils {

	public static String retrieveJsonFromClasspathAsString(String fileName) throws IOException {
		Resource resource = new ClassPathResource(fileName);
		InputStream inputStream = resource.getInputStream();
		String json = new BufferedReader(new InputStreamReader(inputStream)).lines().collect(Collectors.joining("\n"));
		return json;
	}

	public static InputStream retrieveJsonFromClasspathAsInputStream(String fileName) throws IOException {
		Resource resource = new ClassPathResource(fileName);
		return resource.getInputStream();
	}

}

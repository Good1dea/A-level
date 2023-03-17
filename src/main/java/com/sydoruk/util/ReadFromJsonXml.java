package com.sydoruk.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadFromJsonXml {

    private static ReadFromJsonXml instance;

    private ReadFromJsonXml() {
    }

    public static ReadFromJsonXml getInstance() {
        if (instance == null) {
            instance = new ReadFromJsonXml();
        }
        return instance;
    }

    public Map<String, String> readFromXmlFile() throws IOException {
        String line;
        final Map<String, String> mapXml = new HashMap<>();
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (final InputStream resourceAsStream = classLoader.getResourceAsStream("car.xml");
             BufferedReader br = new BufferedReader(new InputStreamReader(resourceAsStream))) {
            while ((line = br.readLine()) != null) {
                Pattern pattern = Pattern.compile("<(.*)>(.*)<");
                Matcher matcher = pattern.matcher(line);
                while (matcher.find()) {
                    mapXml.put(matcher.group(1), matcher.group(2));
                }
            }
        }
        return mapXml;
    }

    public Map<String, String> readFromJsonFile() throws IOException {
        String line;
        final Map<String, String> mapJson = new HashMap<>();
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (final InputStream resourceAsStream = classLoader.getResourceAsStream("car.json");
             BufferedReader br = new BufferedReader(new InputStreamReader(resourceAsStream))) {
            while ((line = br.readLine()) != null) {
                Pattern pattern = Pattern.compile("[{,]?\"(\\w*?)\":\"?(\\w\\s?.*?)[\"},]");
                Matcher matcher = pattern.matcher(line);
                while (matcher.find()) {
                    mapJson.put(matcher.group(1), matcher.group(2));
                }
            }
        }
        return mapJson;
    }
}
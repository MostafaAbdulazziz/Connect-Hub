package com.socialnetwork.connecthub.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for reading and writing generic objects to/from JSON files.
 * This class uses Jackson's ObjectMapper for serialization and deserialization of Java objects to JSON and vice versa.
 *
 * @param <T> The type of the object to be handled by this utility (e.g., User, Post, etc.).
 */
public class JsonFileUtil<T> {

    // Jackson ObjectMapper instance used for converting Java objects to JSON and vice versa
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Jackson ObjectWriter instance used to write JSON in a pretty (human-readable) format
    private final ObjectWriter prettyWriter = objectMapper.writerWithDefaultPrettyPrinter();

    // The type of the array (used for deserialization)
    private final Class<T[]> type;

    /**
     * Constructor to initialize the JsonFileUtil with the type of the objects being processed.
     * This type is used for deserialization to correctly convert JSON into the desired Java object.
     *
     * @param type The Class of the array type (e.g., User[].class).
     */
    public JsonFileUtil(Class<T[]> type) {
        this.type = type;
    }

    /**
     * Loads a list of objects from a JSON file and deserializes it into a List of type T.
     * If the file does not exist, an empty list is returned.
     *
     * @param filePath The path of the JSON file to read from.
     * @return A List of objects of type T, or an empty list if the file does not exist or an error occurs.
     */
    public List<T> loadFromFile(String filePath) {
        File file = new File(filePath);

        // If the file does not exist, return an empty list
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try {
            // Use ObjectMapper to read the JSON file and convert it to a List of objects of type T
            return List.of(objectMapper.readValue(file, type));
        } catch (IOException e) {
            // Print the stack trace in case of an error (e.g., file not found, invalid JSON)
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Serializes a list of objects and writes it to a JSON file.
     * The JSON is written in a human-readable, pretty format.
     *
     * @param filePath The path of the JSON file to write to.
     * @param items The list of objects to serialize and save to the file.
     */
    public void saveToFile(String filePath, List<T> items) {
        try {
            // Use ObjectWriter to write the list of objects as pretty JSON
            prettyWriter.writeValue(new File(filePath), items);
        } catch (IOException e) {
            // Print the stack trace in case of an error (e.g., issues with writing to the file)
            e.printStackTrace();
        }
    }
}

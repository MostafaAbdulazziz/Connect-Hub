package com.socialnetwork.connecthub.util.idgenerator;

public class IncrementalIdGenerator implements IdGenerator {
    private static int counter = 0;  // This will keep track of the last generated ID.

    @Override
    public String generateId() {
        // Increment the counter and return as a string
        counter++;
        return String.valueOf(counter);
    }
}

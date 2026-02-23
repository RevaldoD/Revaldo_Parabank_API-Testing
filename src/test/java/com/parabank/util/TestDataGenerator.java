package com.parabank.util;

import java.util.Random;
import java.util.UUID;

public class TestDataGenerator {

    private final Random random = new Random();

    public String getRandomAddress() {
        return random.nextInt(9999) + " Main Street";
    }

    public String getRandomCity() {
        String[] cities = {"New York", "Los Angeles", "Chicago", "Houston", "Phoenix"};
        return cities[random.nextInt(cities.length)];
    }

    public String getRandomState() {
        String[] states = {"NY", "CA", "IL", "TX", "AZ"};
        return states[random.nextInt(states.length)];
    }

    public String getRandomZipCode() {
        return String.format("%05d", random.nextInt(99999));
    }

    public String getRandomPhone() {
        return String.format("555-%04d-%04d", random.nextInt(9999), random.nextInt(9999));
    }

    public String getRandomSSN() {
        return String.format("%03d-%02d-%04d",
                random.nextInt(999),
                random.nextInt(99),
                random.nextInt(9999));
    }

    public String getRandomUsername() {
        return "user_" + UUID.randomUUID().toString().substring(0, 8);
    }
}

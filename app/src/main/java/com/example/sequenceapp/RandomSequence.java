package com.example.sequenceapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandomSequence {
    //variables
    private String[] colors = {"red", "blue", "green", "yellow"};
    private Random random = new Random();

    //generates a sequence the size of the given sequence length
    public String[] GenerateSequence(int sequenceLength) {
        List<String> sequence = new ArrayList<>();
        for (int i = 0; i < sequenceLength; i++) {
            int randomIndex = random.nextInt(colors.length);
            String randomColor = colors[randomIndex];
            sequence.add(randomColor);
        }
        return sequence.toArray(new String[0]);
    }
}


package com.wandercosta.witsgenerator.generator;

import java.util.Random;

/**
 * Responsible for creating each data line. It uses a random interval between -10000 and 10000 to generate the data, and
 * does not guarantee any trend.
 *
 * @author Wander Costa (www.wandercosta.com)
 */
public class WitsLineGenerator {

    private static final String WRONG_RECORD = "Record number must be greater than 0.";
    private static final String WRONG_ITEM = "Item number must be greater than 0.";

    private static final float MAX_VALUE = 10000;
    private static final float MIN_VALUE = -10000;

    private final Random random = new Random();
    private final float min;
    private final float max;

    public WitsLineGenerator() {
        this(MIN_VALUE, MAX_VALUE);
    }

    public WitsLineGenerator(float min, float max) {
        this.min = min;
        this.max = max;
    }

    public String generate(int record, int item) {
        validateParam(record, WRONG_RECORD);
        validateParam(item, WRONG_ITEM);

        String recordStr = String.valueOf(record);
        String itemStr = String.valueOf(item);
        String valueStr = String.valueOf(randomValue());

        if (recordStr.length() == 1) {
            recordStr = "0".concat(recordStr);
        }
        if (itemStr.length() == 1) {
            itemStr = "0".concat(itemStr);
        }

        return recordStr.concat(itemStr).concat(valueStr);
    }

    private void validateParam(int value, String message) {
        if (value <= 0 || value >= 100) {
            throw new IllegalArgumentException(message);
        }
    }

    private float randomValue() {
        return (random.nextFloat() * (max - min)) + min;
    }

}

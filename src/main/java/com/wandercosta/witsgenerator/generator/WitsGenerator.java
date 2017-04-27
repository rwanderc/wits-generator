package com.wandercosta.witsgenerator.generator;

/**
 * This class is responsible for generating the Wits blocks.
 *
 * @author Wander Costa (www.wandercosta.com)
 */
public class WitsGenerator {

    private static final String WRONG_RECORDS = "Records must be between 1 and 99";
    private static final String WRONG_ITEMS = "Items must be between 1 and 99";

    private final WitsLineGenerator lineGenerator;

    public WitsGenerator(WitsLineGenerator lineGenerator) {
        this.lineGenerator = lineGenerator;
    }

    /**
     * Generates sequential records.
     *
     * @param records the number of records to be generated.
     * @param items the number of items to be generated in each record.
     * @return the resulted string with the records generated.
     */
    public String generate(int records, int items) {
        assertParam(records, WRONG_RECORDS);
        assertParam(items, WRONG_ITEMS);

        StringBuilder str = new StringBuilder();
        for (int currentRecord = 1; currentRecord <= records; currentRecord++) {
            str.append("&&\n");
            for (int currentItem = 1; currentItem <= items; currentItem++) {
                str.append(lineGenerator.generate(currentRecord, currentItem)).append("\n");
            }
            str.append("!!");
            if (currentRecord != records) {
                str.append("\n");
            }
        }
        return str.toString();
    }

    private void assertParam(int value, String message) {
        if (value <= 0 || value >= 100) {
            throw new IllegalArgumentException(message);
        }
    }

}

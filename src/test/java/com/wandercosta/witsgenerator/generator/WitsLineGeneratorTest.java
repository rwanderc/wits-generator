package com.wandercosta.witsgenerator.generator;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Class to test WitsLineGenerator.
 *
 * @author Wander Costa (www.wandercosta.com)
 */
public class WitsLineGeneratorTest {

    @Test
    public void shouldGenerate() {
        for (int i = 1; i < 100; i++) {
            String line = new WitsLineGenerator().generate(1, 1);
            assertTrue(line.length() >= 5);

            int record = Integer.valueOf(line.substring(0, 2));
            int item = Integer.valueOf(line.substring(2, 4));
            Float.valueOf(line.substring(4));

            assertTrue(record > 0 && record < 100);
            assertTrue(item > 0 && item < 100);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToGenerateWithWrongRecord0() {
        new WitsLineGenerator().generate(0, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToGenerateWithWrongRecord100() {
        new WitsLineGenerator().generate(100, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToGenerateWithWrongItem0() {
        new WitsLineGenerator().generate(1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToGenerateWithWrongItem100() {
        new WitsLineGenerator().generate(1, 100);
    }

}

package com.wandercosta.witsgenerator.generator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Class to test WitsGenerator.
 *
 * @author Wander Costa (www.wandercosta.com)
 */
public class WitsGeneratorTest {

    private final WitsLineGenerator lineGenerator = new WitsLineGenerator();
    private final WitsGenerator generator = new WitsGenerator(lineGenerator);

    @Test
    public void shouldGenerate1Block() {
        String generated = generator.generate(1, 1);

        String[] lines = generated.split("\n");
        assertEquals(3, lines.length);
        assertTrue(lines[0].equals("&&"));
        assertTrue(lines[1].startsWith("0101"));
        assertTrue(lines[2].equals("!!"));
    }

    @Test
    public void shouldGenerate2Blocks() {
        String generated = generator.generate(2, 10);

        String[] lines = generated.split("\n");
        assertEquals(2 * 12, lines.length);

        assertTrue(lines[0].equals("&&"));
        assertTrue(lines[1].startsWith("0101"));
        for (int i = 1; i < 11; i++) {
            assertTrue(lines[i].length() > 4);
            assertRecordItem(lines[i], 1, i);
        }
        assertTrue(lines[11].equals("!!"));
        assertTrue(lines[12].equals("&&"));
        for (int i = 13; i < 23; i++) {
            assertTrue(lines[i].length() > 4);
            assertRecordItem(lines[i], 2, i - 12);
        }
        assertTrue(lines[23].equals("!!"));
    }

    @Test
    public void shouldGenerate99Blocks() {
        String generated = generator.generate(99, 99);

        String[] lines = generated.split("\n");
        assertEquals(99 * 101, lines.length);

        for (int i = 1; i < 100; i++) {
            assertTrue(lines[101 * (i - 1)].equals("&&"));
            for (int j = 1; j < 100; j++) {
                assertRecordItem(lines[101 * (i - 1) + j], i, j);
                assertTrue(lines[1].startsWith("0101"));
            }
            assertTrue(lines[101 * i - 1].equals("!!"));
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToGenerateBlockWith0Records() {
        generator.generate(0, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToGenerateBlockWithNegativeRecords() {
        generator.generate(-1, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToGenerateBlockWith0Items() {
        generator.generate(1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToGenerateBlockWithNevatigeItems() {
        generator.generate(1, -1);
    }

    private void assertRecordItem(String line, Integer record, Integer item) {
        assertEquals(record, Integer.valueOf(line.substring(0, 2)));
        assertEquals(item, Integer.valueOf(line.substring(2, 4)));
    }

}

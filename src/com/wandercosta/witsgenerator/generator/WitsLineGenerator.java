/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Wander Costa (www.wandercosta.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.wandercosta.witsgenerator.generator;

import java.util.Random;

/**
 * Responsible for creating each data line. It uses a random interval between
 * -10000 and 10000 to generate the data, and does not guarantee any trend.
 *
 * @author Wander Costa (www.wandercosta.com)
 */
public class WitsLineGenerator {

    private static final float MAX_VALUE = 10000;
    private static final float MIN_VALUE = -10000;
    private static final Random r = new Random();

    private float randomValue() {

        return (r.nextFloat() * (MAX_VALUE - MIN_VALUE)) + MIN_VALUE;

    }

    public String generate(int record, int item) {

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

}

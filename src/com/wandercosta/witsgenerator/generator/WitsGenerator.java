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

/**
 * This class is responsible for generating the Wits blocks.
 *
 * @author Wander Costa (www.wandercosta.com)
 */
public class WitsGenerator {

    private final WitsLineGenerator lineGenerator;
    private final int records;
    private final int items;

    public WitsGenerator(int records, int items) {

        this.lineGenerator = new WitsLineGenerator();
        this.records = records;
        this.items = items;

    }

    public String generate() {

        StringBuilder str = new StringBuilder();

        for (int currentRecord = 1; currentRecord <= records; currentRecord++) {

            str.append("&&\n");

            for (int currentItem = 1; currentItem <= items; currentItem++) {

                str.append(lineGenerator.generate(currentRecord, currentItem)).append("\n");

            }

            str.append("!!\n");

        }

        return str.toString();

    }

}

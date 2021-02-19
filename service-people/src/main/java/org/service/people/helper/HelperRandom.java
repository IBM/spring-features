//
// Copyright 2020- IBM Inc. All rights reserved
// SPDX-License-Identifier: Apache2.0
//
package org.service.people.helper;

public class HelperRandom {

    private static final String ALPHA_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMERIC_STRING = "0123456789";

    public static String randomAlpha(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_STRING.length());
            builder.append(ALPHA_STRING.charAt(character));
        }
        return builder.toString();
    }

    public static String randomNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * NUMERIC_STRING.length());
            builder.append(NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }
}
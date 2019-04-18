package com.rzlearn.base.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;

/**
 * <p>ClassName:RandomUtil</p>
 * <p>Description:</p>
 *
 * @author JiPeigong
 * @date 2018 -08-25 17:05
 */
@Slf4j
public class RandomUtil {

    /**
     * Gets string random.
     *
     * @param length the length
     * @return the string random
     */
    public static String getStringRandom(int length) {
        StringBuilder val = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            if ("char".equalsIgnoreCase(charOrNum)) {
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val.append((char) (random.nextInt(26) + temp));
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                val.append(String.valueOf(random.nextInt(10)));
            }
        }
        return val.toString();
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        System.out.println(RandomUtil.getStringRandom(8));
    }
}

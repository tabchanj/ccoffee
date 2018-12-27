package com.gouermazi.craw;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author jie·chen
 */
public class Main {
    public static void main(String[] args) {
        Agency agency = new Agency("/home/chen/xxx");
        agency.execute(new ArrayList(){{
            add("https://www.youtube.com/");
        }});

    }
}

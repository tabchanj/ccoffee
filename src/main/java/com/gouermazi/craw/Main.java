package com.gouermazi.craw;

import com.gouermazi.craw.refactoring.Agency;
import com.gouermazi.craw.refactoring.LinkHunter;

import java.io.IOException;

/**
 * @author jie·chen
 */
public class Main {
    public static void main(String[] args) throws IOException {
        Agency agency = new HuntDog("/home/chen/xxx");
        agency.takeDown("");
    }
}

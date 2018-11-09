package com.playground;

import java.util.HashSet;

public class Mp4Headers {
    
    private static HashSet<String> possibleHeaders;
    
    static {
        possibleHeaders = new HashSet();

        possibleHeaders.add("ftypavc1");
        possibleHeaders.add("ftypiso2");
        possibleHeaders.add("ftypisom");
        possibleHeaders.add("ftypM4A ");
        possibleHeaders.add("ftypM4B ");
        possibleHeaders.add("ftypM4P ");
        possibleHeaders.add("ftypM4V ");
        possibleHeaders.add("ftypM4VH");
        possibleHeaders.add("ftypM4VP");
        possibleHeaders.add("ftypmmp4");
        possibleHeaders.add("ftypmp41");
        possibleHeaders.add("ftypmp42");
        possibleHeaders.add("ftypmp71");
        possibleHeaders.add("ftypMSNV");
        possibleHeaders.add("ftypNDAS");
        possibleHeaders.add("ftypNDSC");
        possibleHeaders.add("ftypNDSH");
        possibleHeaders.add("ftypNDSM");
        possibleHeaders.add("ftypNDSP");
        possibleHeaders.add("ftypNDSS");
        possibleHeaders.add("ftypNDXC");
        possibleHeaders.add("ftypNDXH");
        possibleHeaders.add("ftypNDXM");
        possibleHeaders.add("ftypNDXP");
        possibleHeaders.add("ftypNDXS");
    }

    public static boolean isMp4Header(String inputHeader) {
        return possibleHeaders.contains(inputHeader);
    }
}

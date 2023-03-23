package com.relaxiTaxi.onboarding.common;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DocumentType {

    public static final String PAN="PAN";
    public static final String AADHAAR="AADHAAR";
    public static final String DRIVING_LICENSE="DRIVING_LICENSE";
    public static final String REGISTRATION="REGISTRATION";
    public static final String INSURANCE="INSURANCE";

    public static final Set<String> documentSet = new HashSet<>();

    public static final Map<String, String> documentMap = new HashMap<>();

    static {
        documentSet.add("PAN");
        documentSet.add("AADHAAR");
        documentSet.add("DRIVING_LICENSE");
        documentSet.add("REGISTRATION");
        documentSet.add("INSURANCE");
    }

    static {
        documentMap.put("PAN", "uploadedPanPathReference");
        documentMap.put("AADHAAR", "uploadedAadhaarPathReference");
        documentMap.put("DRIVING_LICENSE", "uploadedDLPathReference");
    }
}

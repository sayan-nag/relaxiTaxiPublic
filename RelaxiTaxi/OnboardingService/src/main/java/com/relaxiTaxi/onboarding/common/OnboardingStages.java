package com.relaxiTaxi.onboarding.common;

import java.util.HashMap;
import java.util.Map;

public class OnboardingStages {

    private static Map<Integer, Stage> stageReference = new HashMap<>();

    static {
        stageReference.put(1, Stage.INITIAL_CONTACT);//
        stageReference.put(2, Stage.EMAIL_VERIFICATION);//
        stageReference.put(3, Stage.ADDRESS_INFORMATION);//
        stageReference.put(4, Stage.PERSONAL_DOCUMENT_INFORMATION);//
        stageReference.put(5, Stage.PERSONAL_DOCUMENT_UPLOAD);//
        stageReference.put(6, Stage.VEHICLE_DOCUMENT_INFORMATION);//
        stageReference.put(7, Stage.VEHICLE_DOCUMENT_UPLOAD);//
        stageReference.put(8, Stage.DEVICE_ALLOTMENT);//
        stageReference.put(9, Stage.DEVICE_SHIPMENT);//
        stageReference.put(10, Stage.DEVICE_ACTIVATION);//
        stageReference.put(11, Stage.DRIVER_ACTIVATION);//
    }

    public enum Stage {
        INITIAL_CONTACT,
        EMAIL_VERIFICATION,
        ADDRESS_INFORMATION,
        PERSONAL_DOCUMENT_INFORMATION,
        PERSONAL_DOCUMENT_UPLOAD,
        VEHICLE_DOCUMENT_INFORMATION,
        VEHICLE_DOCUMENT_UPLOAD,
        DEVICE_ALLOTMENT,
        DEVICE_SHIPMENT,
        DEVICE_ACTIVATION,
        REGISTRATION, DRIVER_ACTIVATION

    }
}

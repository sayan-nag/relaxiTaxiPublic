package com.relaxiTaxi.onboarding.stage;

import com.relaxiTaxi.onboarding.common.OnboardingStages;
import com.relaxiTaxi.onboarding.common.StageResponse;

import java.util.Map;

public interface StageManager<T> {

    OnboardingStages.Stage getStage();

    StageResponse performStageActions(int previousStageId, T payload);



    String getVersion();

}

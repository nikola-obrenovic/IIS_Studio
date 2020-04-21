package iisc.smtsolver;

import iisc.CheckConstraint;

public interface CheckCntInferenceChecker {

    public boolean checkInferrence(CheckConstraint from, CheckConstraint to);
    
}

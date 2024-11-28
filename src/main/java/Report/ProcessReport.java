/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Report;

import POJOs.Gender;
import POJOs.SignalType;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author laura
 */
public class ProcessReport {
     // Valores normales para EMG
    private static final float EMG_MIN = 0.1f;
    private static final float EMG_MAX = 5.0f;

    // Valores normales para ECG según género
    private static final float ECG_MIN_MALE = 0.6f;
    private static final float ECG_MAX_MALE = 3.5f;

    private static final float ECG_MIN_FEMALE = 0.4f;
    private static final float ECG_MAX_FEMALE = 2.8f;
    
    /**
     * Analyzes a list of signal values for a given signal type and determines if they are normal or abnormal,
     * considering gender-specific ranges for ECG.
     *
     * @param signalType the type of signal (e.g., EMG or ECG)
     * @param signalValues the list of signal values to process
     * @param gender the gender of the patient (Male or Female)
     * @return a string indicating whether the signals are normal or abnormal
     */
    public static String analyzeSignals(SignalType signalType, List<Float> signalValues, Gender gender) {
        // Validate input
        if (signalValues == null || signalValues.isEmpty()) {
            return "No signal data available.";
        }

        // Define ranges based on signal type and gender
        float minRange, maxRange;
        if (signalType == SignalType.EMG) {
            minRange = EMG_MIN;
            maxRange = EMG_MAX;
        } else if (signalType == SignalType.ECG) {
            if (gender == Gender.MALE) {
                minRange = ECG_MIN_MALE;
                maxRange = ECG_MAX_MALE;
            } else if (gender == Gender.FEMALE) {
                minRange = ECG_MIN_FEMALE;
                maxRange = ECG_MAX_FEMALE;
            } else {
                return "Unsupported or unspecified gender.";
            }
        } else {
            return "Unsupported signal type.";
        }

        // Analyze the signal values
        boolean isNormal = signalValues.stream()
                .allMatch(value -> value >= minRange && value <= maxRange);

        return isNormal ? "All signals are within the normal range."
                        : "Some signals are outside the normal range.";
    }
    
    public static void main(String[] args) {
        // Example usage
        List<Float> emgValues = Arrays.asList(1.2f, 2.3f, 4.0f);
        List<Float> ecgValues = Arrays.asList(2.0f, 3.2f, 3.4f);

        // Analyze EMG signals
        String emgResult = ProcessReport.analyzeSignals(SignalType.EMG, emgValues, Gender.MALE);
        System.out.println(emgResult);

        // Analyze ECG signals for Male
        String ecgResultMale = ProcessReport.analyzeSignals(SignalType.ECG, ecgValues, Gender.MALE);
        System.out.println(ecgResultMale);

        // Analyze ECG signals for Female
        String ecgResultFemale = ProcessReport.analyzeSignals(SignalType.ECG, ecgValues, Gender.FEMALE);
        System.out.println(ecgResultFemale);
    }
}

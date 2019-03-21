package hospital.model.comparator;

import hospital.model.Disease;
import hospital.model.Patient;

import java.util.Comparator;

public class PatientComparator implements Comparator<Patient> {
    final String PRIORITY_LASTNAME = "Kowalski";

    @Override
    public int compare(Patient p1, Patient p2) {
        if (patientHasHighPriorityName(p1) && !patientHasHighPriorityName(p2)) {
            return -1;
        } else if (patientHasHighPriorityName(p2) && !patientHasHighPriorityName(p1)) {
            return 1;
        }

        if (patientCaughtSomethingSerious(p1) && !patientCaughtSomethingSerious(p2)) {
            return -1;
        } else if (patientCaughtSomethingSerious(p2) && !patientCaughtSomethingSerious(p1)) {
            return 1;
        }

        if (getPriority(p1) > getPriority(p2)) {
            return -1;
        } else if (getPriority(p1) == getPriority(p2)) {
            return 0;
        } else {
            return 1;
        }
    }

    private boolean patientCaughtSomethingSerious(Patient patient) {
        return patient.getDisease().equals(Disease.SOMETHING_SERIOUS);
    }

    private boolean patientHasHighPriorityName(Patient patient) {
        return patient.getLastName().equalsIgnoreCase(PRIORITY_LASTNAME);
    }

    private int getPriority(Patient patient) {
        return patient.getAngerLevel() * patient.getDisease().getContagiousness();
    }
}

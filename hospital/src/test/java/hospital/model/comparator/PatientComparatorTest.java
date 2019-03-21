package hospital.model.comparator;

import hospital.model.Disease;
import hospital.model.Patient;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

//TODO: Test more combinations of types of priority
public class PatientComparatorTest {
    PatientComparator patientComparator = new PatientComparator();

    @Test
    public void shouldPrioritizePriorityLastName() {
        //GIVEN
        Patient patientWithPriorityLastName = new Patient("Jan", patientComparator.PRIORITY_LASTNAME, 1, Disease.INFLUENZA);
        Patient otherPatient = new Patient("Janusz", "Łyczany", 100, Disease.SOMETHING_SERIOUS);

        //WHEN
        int result = patientComparator.compare(patientWithPriorityLastName, otherPatient);

        //THEN
        assertTrue(result < 0);
    }

    @Test
    public void shouldPrioritizeSomethingSerious() {
        //GIVEN
        Patient patientWithSomethingSerious = new Patient("Bożena", "Markulis", 1, Disease.SOMETHING_SERIOUS);
        Patient otherPatient = new Patient("Janusz", "Łyczany", 100, Disease.INFLUENZA);

        //WHEN
        int result = patientComparator.compare(patientWithSomethingSerious, otherPatient);

        //THEN
        assertTrue(result < 0);
    }

    @Test
    public void shouldPrioritizeBasedOnAngerLevel() {
        //GIVEN
        Patient angryPatient = new Patient("Dawid", "Kiedrowicz", 100, Disease.INFLUENZA);
        Patient otherPatient = new Patient("Janusz", "Łyczany", 1, Disease.INFLUENZA);

        //WHEN
        int result = patientComparator.compare(angryPatient, otherPatient);

        //THEN
        assertTrue(result < 0);
    }

    @Test
    public void shouldPrioritizeBasedOnContagiousness() {
        //GIVEN
        Patient highlyContagiousPatient = new Patient("Marek", "Białoń", 1, Disease.DIARRHEA);
        Patient otherPatient = new Patient("Janusz", "Łyczany", 1, Disease.INFLUENZA);

        //WHEN
        int result = patientComparator.compare(highlyContagiousPatient, otherPatient);

        //THEN
        assertTrue(result < 0);
    }
}
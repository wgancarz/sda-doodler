package hospital.menu;

import hospital.model.Disease;
import hospital.model.Patient;
import hospital.service.HospitalQueueService;

import java.util.Scanner;

public class PatientCreatorMenu {
    HospitalQueueService hospitalQueueService;

    Scanner scanner = new Scanner(System.in);

    public PatientCreatorMenu(HospitalQueueService hospitalQueueService) {
        this.hospitalQueueService = hospitalQueueService;
    }

    public void addNewPatientMenu() {
        System.out.println("Creating a new patient...");
        Patient patient = patientCreatorMenu();

        System.out.println("Adding patient to queue:");
        System.out.println(patient);

        hospitalQueueService.add(patient);
    }

    private Patient patientCreatorMenu() {
        Patient patient = new Patient();

        System.out.println("Enter patient's first name:");
        patient.setFirstName(scanner.next());

        System.out.println("Enter patient's last name:");
        patient.setLastName(scanner.next());

        System.out.println("Enter patient's anger level:");
        while (!scanner.hasNextInt()) {
            scanner.next();
        }
        patient.setAngerLevel(scanner.nextInt());

        System.out.println("Select patient's disease:");
        Disease disease = diseaseSelectorMenu();
        patient.setDisease(disease);

        return patient;
    }

    private Disease diseaseSelectorMenu() {
        int diseaseOrdinal;

        while (true) {
            System.out.println("Available diseases:");
            for (Disease disease : Disease.values()) {
                System.out.println("[" + disease.ordinal() + "] " + disease);
            }

            while (!scanner.hasNextInt()) {
                scanner.next();
            }

            diseaseOrdinal = scanner.nextInt();
            if (diseaseOrdinal >= 0 && diseaseOrdinal < Disease.values().length)
                break;
        }

        Disease disease = Disease.values()[diseaseOrdinal];
        return disease;
    }
}

package hospital.menu;

import hospital.model.Disease;
import hospital.model.Patient;
import hospital.service.HospitalQueueService;

import java.util.Scanner;

public class Menu {
    private Scanner scanner = new Scanner(System.in);

    public void mainMenu(HospitalQueueService hospitalQueueService) {
        while (true) {
            System.out.println("[1] Poll next patient");
            System.out.println("[2] Peek next patient");
            System.out.println("[3] Add a new patient");
            System.out.println("[0] Quit");

            while (!scanner.hasNextInt()) {
                scanner.next();
            }

            switch (scanner.nextInt()) {
                case 1:
                    pollNextPatientMenu(hospitalQueueService);
                    break;
                case 2:
                    peekNextPatientMenu(hospitalQueueService);
                    break;
                case 3:
                    addNewPatientMenu(hospitalQueueService);
                    break;
                case 0:
                    return;
            }
        }
    }

    private void addNewPatientMenu(HospitalQueueService hospitalQueueService) {
        Patient patient = new Patient();

        System.out.println("Creating a new patient...");

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
        diseaseSelectorMenu(patient);

        hospitalQueueService.add(patient);
    }

    private void peekNextPatientMenu(HospitalQueueService hospitalQueueService) {
        System.out.println("The next patient in the queue:");
        System.out.println(hospitalQueueService.peek());
    }

    private void pollNextPatientMenu(HospitalQueueService hospitalQueueService) {
        System.out.println("The next patient in the queue:");
        System.out.println(hospitalQueueService.next());
        System.out.println("Removed the next patient from the queue.");
    }

    private void diseaseSelectorMenu(Patient patient) {
        int diseaseId;

        while (true) {
            System.out.println("Available diseases:");
            for (Disease disease : Disease.values()) {
                System.out.println("[" + disease.ordinal() + "] " + disease.name());
            }

            while (!scanner.hasNextInt()) {
                scanner.next();
            }

            diseaseId = scanner.nextInt();
            if (diseaseId >= 0 && diseaseId < Disease.values().length)
                break;
        }

        Disease disease = Disease.values()[diseaseId];
        patient.setDisease(disease);
    }
}

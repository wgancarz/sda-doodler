package hospital.menu;

import hospital.model.Disease;
import hospital.model.Patient;
import hospital.service.HospitalQueueService;

import java.util.Scanner;

public class Menu {
    private Scanner scanner = new Scanner(System.in);

    private HospitalQueueService hospitalQueueService;

    public Menu(HospitalQueueService hospitalQueueService){
        this.hospitalQueueService = hospitalQueueService;
    }

    public void mainMenu() {
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
                    pollNextPatientMenu();
                    break;
                case 2:
                    peekNextPatientMenu();
                    break;
                case 3:
                    addNewPatientMenu();
                    break;
                case 0:
                    return;
            }
        }
    }

    private void addNewPatientMenu() {
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

    private void peekNextPatientMenu() {
        System.out.println("The next patient in the queue:");
        System.out.println(hospitalQueueService.peek());
    }

    private void pollNextPatientMenu() {
        System.out.println("The next patient in the queue:");
        System.out.println(hospitalQueueService.next());
        System.out.println("Removed the next patient from the queue.");
    }

    private void diseaseSelectorMenu(Patient patient) {
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
        patient.setDisease(disease);
    }
}

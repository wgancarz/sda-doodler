package hospital.menu;

import hospital.demo.PatientGenerator;
import hospital.model.Patient;
import hospital.service.HospitalQueueService;

import java.util.Scanner;

public class MainMenu {
    private HospitalQueueService hospitalQueueService;

    private PatientGenerator patientGenerator = new PatientGenerator();
    private Scanner scanner = new Scanner(System.in);

    public MainMenu(HospitalQueueService hospitalQueueService) {
        this.hospitalQueueService = hospitalQueueService;
    }

    public void mainMenu() {
        while (true) {
            System.out.println("[1] Poll next patient");
            System.out.println("[2] Peek next patient");
            System.out.println("[3] Add a random patient");
            System.out.println("[4] Add a custom patient");
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
                    addRandomPatientMenu();
                    break;
                case 4:
                    addCustomPatientMenu();
                    break;
                case 0:
                    return;
            }
        }
    }

    private void addRandomPatientMenu() {
        Patient patient = patientGenerator.get();

        System.out.println("Adding random patient to queue:");
        System.out.println(patient);

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

    private void addCustomPatientMenu() {
        PatientCreatorMenu patientCreatorMenu = new PatientCreatorMenu(hospitalQueueService);
        patientCreatorMenu.addNewPatientMenu();
    }
}

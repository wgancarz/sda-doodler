package hospital.menu;

import hospital.service.HospitalQueueService;

import java.util.Scanner;

public class MainMenu {
    private Scanner scanner = new Scanner(System.in);

    private HospitalQueueService hospitalQueueService;

    public MainMenu(HospitalQueueService hospitalQueueService) {
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

    private void peekNextPatientMenu() {
        System.out.println("The next patient in the queue:");
        System.out.println(hospitalQueueService.peek());
    }

    private void pollNextPatientMenu() {
        System.out.println("The next patient in the queue:");
        System.out.println(hospitalQueueService.next());
        System.out.println("Removed the next patient from the queue.");
    }

    private void addNewPatientMenu() {
        PatientCreatorMenu patientCreatorMenu = new PatientCreatorMenu(hospitalQueueService);
        patientCreatorMenu.addNewPatientMenu();
    }
}

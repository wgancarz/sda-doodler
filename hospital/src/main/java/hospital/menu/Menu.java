package hospital.menu;

import hospital.model.Disease;
import hospital.model.Patient;
import hospital.service.HospitalQueueService;

import java.util.Scanner;

public class Menu {
    private static Scanner scanner = new Scanner(System.in);

    public static Disease diseaseSelectorMenu(){

        System.out.println("Available diseases:");
        for (Disease disease : Disease.values()){
            System.out.println(disease.name());
        }

        // TODO: handle incorrect ENUM input
        Disease disease = Disease.valueOf(scanner.next());
        return disease;
    }

    public static Patient newPatientMenu() {
        Patient patient = new Patient();

        System.out.println("Creating a new patient...");

        System.out.println("Enter patient's first name:");
        patient.setFirstName(scanner.next());

        System.out.println("Enter patient's last name:");
        patient.setLastName(scanner.next());

        System.out.println("Enter patient's anger level:");
        patient.setAngerLevel(scanner.nextInt());

        System.out.println("Select patient's disease:");
        patient.setDisease(diseaseSelectorMenu());

        return patient;
    }

    public static void mainMenu(HospitalQueueService hospitalQueueService) {
        while (true) {
            System.out.println("[1] Poll next patient");
            System.out.println("[2] Peek next patient");
            System.out.println("[3] Add a new patient");
            System.out.println("[0] Quit");

            switch (scanner.nextInt()) {
                case 1:
                    System.out.print("Removing the patient from the head of the queue: ");
                    System.out.println(hospitalQueueService.next());
                    System.out.println("The patient has been removed from the queue.");
                    break;
                case 2:
                    System.out.println(hospitalQueueService.peek());
                    break;
                case 3:
                    hospitalQueueService.add(newPatientMenu());
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Incorrect input.");
            }
        }
    }
}

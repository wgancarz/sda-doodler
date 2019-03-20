package hospital;

import hospital.menu.Menu;
import hospital.model.Disease;
import hospital.model.Patient;
import hospital.service.HospitalQueueService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        HospitalQueueService hospitalQueueService = new HospitalQueueService();

        Menu menu = new Menu();
        menu.mainMenu(hospitalQueueService);
    }
}

package hospital;

import hospital.menu.Menu;
import hospital.service.HospitalQueueService;

public class Main {

    public static void main(String[] args) {
        HospitalQueueService hospitalQueueService = new HospitalQueueService();

        Menu menu = new Menu();
        menu.mainMenu(hospitalQueueService);
    }
}

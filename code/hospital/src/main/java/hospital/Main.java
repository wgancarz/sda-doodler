package hospital;

import hospital.menu.MainMenu;
import hospital.service.HospitalQueueService;

public class Main {

    public static void main(String[] args) {
        HospitalQueueService hospitalQueueService = new HospitalQueueService();

        MainMenu mainMenu = new MainMenu(hospitalQueueService);
        mainMenu.mainMenu();
    }
}

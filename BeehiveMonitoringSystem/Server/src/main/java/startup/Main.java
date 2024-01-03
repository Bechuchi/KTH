package startup;

import controller.Controller;

public class Main {
    public static void main(String[] args) {
        try {
            Controller controller = new Controller();
            controller.scanLocalArea();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

import App.HotelApp;
import Views.ConsoleView;

public class Main {
    public static void main(String[] args) {
        HotelApp app = new HotelApp();
        ConsoleView view = new ConsoleView(app);
        view.start();
    }
}

import javax.swing.*;

/**
 * @author Leonid Zulin
 * @date 20.07.2023 15:10
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Client();
            }
        });
    }
}

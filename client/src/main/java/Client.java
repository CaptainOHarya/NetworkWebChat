import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * @author Leonid Zulin
 * @date 20.07.2023 14:54
 */
public class Client extends JFrame implements ActionListener, IConnectionObserver {
    // размеры нашего окна
    private static final int WIDTH = 600;
    private static final int HEIGHT = 500;
    private Connection connection;

    private final JTextArea areaLog = new JTextArea();
    private final JTextField nickName;
    private final JTextField input = new JTextField();
    private final JFrame frame = new JFrame("Сетевой чат");

    // JButton send_button = new JButton("Ваше имя в чате ");

    private String nickNameUser;

    public Client() {
        nickNameUser = JOptionPane.showInputDialog("Введите пожалуйста Ваше имя");
        nickName = new JTextField(nickNameUser);
        LoggerClient.setLoggerName(nickNameUser);
        Toolkit toolkit = Toolkit.getDefaultToolkit(); // так получим размер нашего окна
        Dimension dimension = toolkit.getScreenSize(); // так расположение нашего окна не будет зависеть от разрешения экрана
        // координаты левого верхнего угла окна клиента
        int xCoordinate = dimension.width / 2 - 250;
        int yCoordinate = dimension.height / 2 - 250;
        frame.setBounds(xCoordinate, yCoordinate, WIDTH, HEIGHT);
        frame.setAlwaysOnTop(true); // окно всегда поверх других окон
        setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        areaLog.setEditable(false);
        areaLog.setLineWrap(true);
        frame.add(areaLog, BorderLayout.CENTER);
        input.addActionListener(this);// Сработает когда нажмём Enter

        frame.add(input, BorderLayout.SOUTH);
        frame.add(nickName, BorderLayout.NORTH);

        frame.setVisible(true);// всегда видеть наше окно
        try {
            connection = new Connection(this, SettingsClient.getSettings().getLeft(), SettingsClient.getSettings().getRight());
            connection.sendMessage("Вошёл новый пользователь по ником  " + nickNameUser);
            LoggerClient.getInstance().logWrite("Вошёл новый пользователь по ником  " + nickNameUser);

        } catch (IOException e) {
            printMessage("Connection exception " + e);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String text = input.getText();
        // если строка пустая, то и передавать её никуда не надо
        if (text.equals("")) return;
        // если строчка есть, то стираем то, что в поле input
        input.setText(null);
        connection.sendMessage(nickNameUser + ": " + text);
        //LoggerClient.getInstance().logWrite(nickNameUser + ": " + text);
    }

    @Override
    public void connectionIsReady(Connection connection) {
        printMessage("Connection ready");

    }

    @Override
    public void acceptText(Connection connection, String text) {
        printMessage(text);

    }

    @Override
    public void connectionIsDisconnect(Connection connection) {
        printMessage("Connection close");

    }

    @Override
    public void throwException(Connection connection, Exception ex) {
        printMessage("Connection exception " + ex);

    }

    // метод логирования в наше окошко
    private synchronized void printMessage(String text) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                areaLog.append(text + "\n");
               LoggerClient.getInstance().logWrite(text);
                // текст всегда надо поднимать
                areaLog.setCaretPosition(areaLog.getDocument().getLength());

            }
        });
    }


}

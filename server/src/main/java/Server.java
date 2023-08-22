import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Leonid Zulin
 * @date 29.06.2023 6:40
 */
public class Server implements IConnectionObserver{
    // Список наших соединений
    private final List<Connection> connections = new ArrayList<>();

    public Server() {

        try (ServerSocket serverSocket = new ServerSocket(Settings.getSettings())) {
            // System.out.println("Server running...");
            Logger.getInstance().logWrite("Server running...");
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    new Connection(this, socket);

                } catch (IOException e) {
                   // System.err.println("Connection exception " + e);
                   Logger.getInstance().logWrite("Connection exception " + e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public synchronized void connectionIsReady(Connection connection) {
        connections.add(connection);
        sendToAllClients("Client connected: " + connection);
        Logger.getInstance().logWrite("Client connected " + connection);


    }

    @Override
    public synchronized void acceptText(Connection connection, String text) {
       sendToAllClients(text);
        Logger.getInstance().logWrite(connection + " send message: " + text);

    }

    @Override
    public synchronized void connectionIsDisconnect(Connection connection) {
        connections.remove(connection);
        sendToAllClients("Client disconnect: " + connection);
        Logger.getInstance().logWrite("Client disconnected " + connection);
    }

    @Override
    public synchronized void throwException(Connection connection, Exception ex) {
        System.out.println("Connection " + connection + " exception " + ex);
        Logger.getInstance().logWrite("Connection " + connection + " exception " + ex);


    }
    private void sendToAllClients(String line) {
        // если строка пустая, то и отсылать её никуда не нужно
        if (line == null || line.isEmpty()) return;
        System.out.println(line);
        for (Connection connection: connections) {
            connection.sendMessage(line);

        }



    }
}

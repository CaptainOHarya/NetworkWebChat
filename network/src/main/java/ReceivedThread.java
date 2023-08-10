import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author Leonid Zulin
 * @date 27.06.2023 20:27
 */
public class ReceivedThread extends Thread {
    IConnectionObserver eventObserver;
    Connection connection;
    BufferedReader in;

    public ReceivedThread(IConnectionObserver eventObserver, Connection connection, BufferedReader in) {
        this.eventObserver = eventObserver;
        this.connection = connection;
        this.in = in;
    }

    @Override
    public void run() {
        try {
            eventObserver.connectionIsReady(connection);
            while (!this.isInterrupted()) {
                String message = in.readLine();
                eventObserver.acceptText(connection, message);
            }

        } catch (IOException e) {
            eventObserver.throwException(connection, e);
        } finally {
            eventObserver.connectionIsDisconnect(connection);
        }
    }
}


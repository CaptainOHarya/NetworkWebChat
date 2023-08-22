import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * @author Leonid Zulin
 * @date 26.06.2023 21:36
 */
public class Connection {
    private final Socket socket;
    private final ReceivedThread rThread;
    private final BufferedReader in;
    private final BufferedWriter out;
    private final IConnectionObserver eventObserver;

    public Connection(IConnectionObserver eventObserver, Socket socket) throws IOException {
        this.eventObserver = eventObserver;
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream(), Charset.forName("UTF-8")));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), Charset.forName("UTF-8")));
        rThread = new ReceivedThread(eventObserver, this, in);
        rThread.start();

    }

    public Connection(IConnectionObserver eventObserver, String ipAddress, int port) throws IOException {
        this(eventObserver, new Socket(ipAddress, port));
    }

    public synchronized void sendMessage(String message) {
        try {
            if ("exit".equalsIgnoreCase(message)) {
                disconnect();
            } else {
                out.write(message + "\r\n");
                out.flush();
            }

        } catch (IOException e) {
            eventObserver.throwException(this, e);
            // так как строчку не удалаось передать, то вызовем метод дисконнект
            disconnect();
        }
    }

    private synchronized void disconnect() {
        // прервать поток
        rThread.interrupt();
        // закрыть наш сокет
        try {
            socket.close();
        } catch (IOException e) {
            eventObserver.throwException(this, e);

        }
    }

    @Override
    public String toString() {
        return "Connection " + socket.getInetAddress() + "; " + socket.getPort();
    }
}

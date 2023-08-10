/**
 * @author Leonid Zulin
 * @date 26.06.2023 21:12
 */
public interface IConnectionObserver {
    void connectionIsReady(Connection connection);
    void acceptText(Connection connection, String text);
    void connectionIsDisconnect(Connection connection);
    void throwException(Connection connection, Exception ex);
}

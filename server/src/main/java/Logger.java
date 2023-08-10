
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Leonid Zulin
 * @date 05.07.2023 20:38
 */

public class Logger {
    private static Logger instance = null;
    private static String systemFS = System.getProperty("file.separator");
    private final Charset charset = Charset.forName("UTF-8");



    private final String LOG_DIR;
    private final boolean APP_TO_END;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss");

    public String getLOG_DIR() {
        return LOG_DIR;
    }
    // used pattern Singleton - private constructor
    private Logger() {
        this.APP_TO_END = true;
        this.LOG_DIR = Settings.MAIN_DIR + systemFS + "file.log";
        // Settings.makeDir(Settings.MAIN_DIR);
    }

    public static Logger getInstance() {
        if (instance == null) {
            synchronized (Logger.class) {
                if (instance == null) {
                    instance = new Logger();
                }

            }
        }

        return instance;
    }

    public void logWrite(String text) {
        try (FileOutputStream fos = new FileOutputStream(LOG_DIR, APP_TO_END)) {
            synchronized (this) {
                String textLoggingString = getTimeView(LocalDateTime.now()) + " : " + text + "\n";
                System.out.println(textLoggingString);
                fos.write(textLoggingString.getBytes(charset));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static String getTimeView(LocalDateTime time) {
        return time.format(DATE_TIME_FORMATTER);
    }


}

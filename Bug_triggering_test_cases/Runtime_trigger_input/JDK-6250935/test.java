
import java.net.MalformedURLException;
import java.net.URL;
import org.jdesktop.jdic.desktop.Desktop;
import org.jdesktop.jdic.desktop.DesktopException;

public class Test {
        /**
         *   bring up a browser and load in the pass-in URL.
         *
         * @param name  string format of the url.
         * @return              none
         * @throws              none
         */
        public static void browseURL(String url) {
            try {
                Desktop.browse(new URL(url));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (DesktopException e) {
                e.printStackTrace();
            }
        }

	public static void main(String[] args) {
	    Test.browseURL(args[0]);
	}
}


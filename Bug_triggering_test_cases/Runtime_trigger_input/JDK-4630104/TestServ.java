
import java.util.*;
import java.net.*;
public class TestServ {
        public static void main(String args[]) throws Exception {
                ServerSocket listen = new ServerSocket(1234);
                Socket s = listen.accept();
                byte b = (byte)'a';
                while (true) {
                        s.getOutputStream().write(b);
                        if (b == (byte)'z') {
                                b = (byte)'a';
                        } else {
                                b++;
                        }
                }
        }
}


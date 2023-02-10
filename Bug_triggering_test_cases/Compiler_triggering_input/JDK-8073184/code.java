import java.util.*;
import java.io.*;

class code {
    public static void main(String[] args) throws Exception {
        Random r = new Random(42);
        int x = 0;
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 1000000; ++i) {
            int v = Math.abs(r.nextInt());
            sb.append('+').append(v).append('\n');
            x += v;
            while(x < 0) x += 1000000000;
            sb.append('=').append(x).append('\n');
        }
        if (sb.toString().hashCode() != 0xaba94591) {
          throw new Exception("Unexpected result");
        }
    }
}



import java.util.Arrays;
import java.util.*;

 class Codec {
    static int CODE_LEN = 6;
    Map<String, String> hash;
    String alpha;
    public Codec() {
        alpha = "abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        hash = new HashMap<>();
    }

    public String generateCode() {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < CODE_LEN; i++) {
            int index = (int)(Math.random()*alpha.length());
            code.append(alpha.charAt(index));
        }
        return code.toString();
    }
    // Encodes a URL to a shortened URL.
    public String encode(String longUrl) {
        String prefix = "http://tinyurl.com/";
        String code = generateCode();
        int attempt = 2;
        while(hash.containsKey(prefix + code)) {
            System.out.println(code + " exist try to find a new one attempt: " + attempt++);
            code = generateCode();
            continue;
        }
        String shortUrl = prefix+code;
        hash.put(shortUrl, longUrl);
        return shortUrl;
    }

    // Decodes a shortened URL to its original URL.
    public String decode(String shortUrl) {
        return hash.get(shortUrl);
    }
}

class Solution {
    public static void main(String [] args) {
        Codec codec = new Codec();
        String longUrl = "http://google.com/searchthisthingforever";
        for (int i = 0; i < 1000000000; i++) {
            String shortUrl = codec.encode(longUrl);
            String tmp = codec.decode(shortUrl);
            if (longUrl.compareTo(tmp) != 0) {
                System.out.println(shortUrl + "didn't work");
            }
        }
    }
}

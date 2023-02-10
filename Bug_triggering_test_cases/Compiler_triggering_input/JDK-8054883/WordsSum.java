import java.util.*;
import java.io.*;

class WordsSum {
    public static void main(String args[]) throws IOException
    {
        Scanner input = new Scanner(new File("/usr/share/dict/words"));
        List<String> words = new ArrayList<>();
        long start = System.currentTimeMillis();
        while(input.hasNext()) {
            words.add(input.next());
        }
        System.out.println("Reading time = "+ (System.currentTimeMillis() - start));
        maxSum4(words);
    }

    public static void maxSum4(List<String> words)
    {
        long start = System.currentTimeMillis();
        int wordMax[] = new int[1 << 26];
        String wordStringMax[] = new String[1 << 26];
        int result = 0;
        for(String word : words) {
            result = 0;
            for(char c : word.toCharArray()) {
                if(c >= 'a' && c <= 'z')
                    result |= 1 << (c - 'a');
                else {
                    result = -1;
                    break;
                }
            }
            if(result > 0 && wordMax[result] < word.length()) {
                wordStringMax[result] = word;
                wordMax[result] = word.length();
            }
        }

        for(int i = 1; i < (1 << 26); i++) {
            int max = wordMax[i];
            for(int j = 0; j < 31; j++) {
                int cand = ~(1 << j) & i;
                if(max < wordMax[cand]) {
                    wordMax[i] = wordMax[cand];
                    wordStringMax[i] = wordStringMax[cand];
                }
            }
        }

        int max = -1;
        String w1 = null;
        String w2 = null;
        String word1 = null;
        String word2 = null;
        for(String word : words) {
            result = 0;
            for(char c : word.toCharArray()) {
                if(c >= 'a' && c <= 'z')
                    result |= 1 << (c - 'a');
                else {
                    result = -1;
                    break;
                }
            }
            if(result > 0 && wordMax[~result]*word.length() > max) {
                max = wordMax[~result] * word.length();
                w1 = word;
                w2 = wordStringMax[~result];
            }
        }
        System.out.println(w1 + " " + w2 + " " + (System.currentTimeMillis() - start));
    }
}
public class Bug { 
    public static int[] run(int[] arr) { 
        int max = 0; 
        int min = 0; 
        for (int i : arr) { 
            if (i > max) { 
                max = i; 
            } 
            if (i < min) { 
                min = i; 
            } 
        } 

        int[] counts = new int[10]; 

        int i = 0; 
        for (i = 0; i < counts.length; i += 1) { 
            for (int j = 0; j < counts[i]; j += 1) { 
            } 
        } 

        while (i < max) { 
            for (int j = 0; j < counts[i]; j += 1) { 
                arr[0] = i; 
            } 
        } 

        return arr; 
    } 

    public static void main(String[] args) { 
        int[] arr = new int[1000 * 1000]; 

        for (int i = 0; i < 100; i++) { 
            run(arr); 
        } 
    } 
} 

__ByValue final class Empty {

    private Empty() {
    }

    __ValueFactory public static Empty createEmpty() {
Empty e = __MakeDefault Empty();
return e;
    }
    
}

public class Main {
    public static void main(String[] args) {
Empty e = Empty.createEmpty();
Empty[] array = new Empty[10];
e = array[1];
    }
}

public final class InetOverflow {
    public static void main(final String[] args)throws Exception {
        for(int i = 0; i < args.length; i++){
            java.net.InetAddress.getAllByName(args[i]);
        }
    }
}


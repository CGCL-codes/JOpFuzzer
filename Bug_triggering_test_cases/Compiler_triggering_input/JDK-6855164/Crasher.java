class Crasher{
    public static void main(String[] args) throws Exception{
        StringBuffer builder = new StringBuffer();

        for(int i = 0; i < 100; i++)
            builder.append("I am the very model of a modern major general\n");

        for(int j = 0; j < builder.length(); j++){
            previousSpaceIndex(builder, j);
        }
    }

    private static final int previousSpaceIndex(CharSequence sb, int seek) {
        seek--;
        while (seek > 0) {
            if (sb.charAt(seek) == ' ') {
                while (seek > 0 && sb.charAt(seek - 1) == ' ')
                    seek--;
                return seek;
            }
            seek--;
        }
        return 0;
    }
}
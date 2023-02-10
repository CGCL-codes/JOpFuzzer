
class ShowTimes {
    public static synchronized void main(String[] args) throws Throwable {
        for (;;) {
            ShowTimes.class.wait(1000);
            System.out.println(
                System.currentTimeMillis() + " - " +
                System.nanoTime()
            );
        }
   }
}

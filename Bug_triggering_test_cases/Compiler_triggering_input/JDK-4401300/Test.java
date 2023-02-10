public class Test extends Thread {

    java.util.Vector queue = new java.util.Vector();
    
    private long prev_sys_time = Long.MIN_VALUE;
    private long time_to_add = 0;
    
    private long currentTimeMillis() {
    long time = System.currentTimeMillis();
    if (time < prev_sys_time) {
    long diff_ms = prev_sys_time - time;
    time_to_add += diff_ms;
    System.err.println("!!!!!BAD System.currentTimeMillis, negative offset:"+(diff_ms/1000.0)+"s !!!!!");
    
    }
    prev_sys_time = time;
    return time_to_add + time;
    }
    
    void post_event()
    {
    synchronized (queue) {
    
    long shouldDeliverTime = currentTimeMillis() + 200;
    queue.addElement(new Long(shouldDeliverTime));
    queue.notify();
    }
    }
    
    
    public void run() {
    int eventCount = 0;
    while (true) {
    Long head = null;
    synchronized (queue) {
    if (queue.size() == 0) {
    try { queue.wait(); }
    catch (InterruptedException ex) { }
    }
    else {
    head = (Long)queue.elementAt(0);
    long time = currentTimeMillis();
    long shouldDeliverTime = head.longValue();
    long delay = head.longValue() - time;
    if (delay > 0) {
    head = null;
    try { queue.wait(delay); }
    catch (InterruptedException ex) { }
    }
    else {
    queue.removeElementAt(0);
    }
    }
    }
    if (head != null) {
    System.err.println("NEW EVENT " + (++eventCount));
    post_event();
    }
    }
    }
    
    public static void main(String[] args) {
    Test t = new Test();
    t.post_event();
    t.start();
    }
    
    }
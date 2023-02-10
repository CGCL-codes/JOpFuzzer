import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

/**
 * Test case to show ArrayIndexOutOfBoundsException bug on
 * toArray(Object[]) call to a LinkedList. This only happens in
 * a multithreaded environment and running on a server JVM.
 *
 * Tested VMs:
 * 1.4.1_03-b02 - does not fail
 * 1.4.2_02-b03 - fails
 * 1.4.2_02-b28 - fails
 * 1.4.2_03-b02 - fails
 *
 * Tested OSs:
 * Redhat Linux 8 with 2.4.24 kernel
 * Windows 2000 SP 2
 * Windows XP SP1
 *
 * Tested hardware:
 * Intel P-II 333Mhz, 396MB RAM PC33, Legend Mobo LX440
 * AMD Athlon 2500+, 512MB RAM PC4000, Asus Mobo
 * AMD Athlon 1Ghz on a Sony Vaio
 *
 * To execute this test run:
 * java.exe -server Test
 *
 * A log file will be created along with standard output. If this
 * test does not fail for you right away increase the number of runs
 * and threads and try again.
 *
 */
public class Test {
    
    //main entry
    public static void main(String[] args) throws IOException {
        Test test = new Test();
        test.test();
        
    }//main()
    
    //main run method
    public void test() throws IOException {
        
        //modify the following params to increase the number
        //of inner and outer iterations as well as the thread count
        int runs = 1000;
        int threads = 100;
        int outerRuns = 20;
        
        
        //create counter and synchronizer
        Counter cnt = new Counter("test.log");
        cnt.status("Starting");
        
        try {
            
            //iterate through outer runs
            for(int x = 0; x < 10; x++) {
                cnt.status("Outer Run Starting: " + x);
                
                //create and initialize all threads
                RunThread[] rt = new RunThread[threads];
                for(int i = 0; i < threads; i++) {
                    rt[i] = new RunThread("Thread " + i, runs, cnt);
                    rt[i].start();
                }//for
                
                //tell all threads they can start working
                cnt.run();
                cnt.status("Outer Run Waiting: " + x);
                
                //wait for about 30 seconds for all of them to complete
                int c = 0;
                while(cnt.count > 0) {
                    c++;
                    try { Thread.sleep(200); }
                    catch(Throwable e) { e.printStackTrace(); }
                    
                    if(c > 1500) {
                        cnt.stop();
                        cnt.status("Waited too long, aborting");
                    }//if
                }//while
                
                //flag stop to all threads and get ready to
                //reinitialize
                cnt.stop();
                cnt.status("Outer Run Finished: " + x);
            }//for
        } catch(Throwable e) {
            
            //catch outer iteration exceptions
            cnt.status("Outer Failure:");
            cnt.error(e);
        
        }//try..catch
        
        //finish up, close log file
        cnt.status("Done");
        cnt.stop();
        cnt.done();
    }//main()
    
    
    //main test thread
    private class RunThread extends Thread {
        protected int runs;
        protected String name;
        protected Counter cnt;
        
        public RunThread(String n, int r, Counter c) {
            name = n;
            runs = r;
            cnt = c;
            
        }//RunThread()

        
        //main inner iteration method
        public void run() {
            
            //tell counter we are ready to start
            cnt.inc(1);
                        
            //wait for counter to say its ok to start
            while(!cnt.isRunning()) {
                try { sleep(100); }
                catch(Throwable e) { e.printStackTrace(); }
            }//while
            
            //initialize local vars
            int lsize = -1;
            LinkedList lst = new LinkedList();
            Integer[] last = null;
            
            try {
                
                //main inner iteration loop
                for(int i = 0; i < runs && cnt.isRunning(); i++) {
                    
                    //add to list
                    lst.add(new Integer(i+1));
                    
                    //record size prior to toArray call
                    lsize = lst.size();
                    last = new Integer[lst.size()];
                    
                    //place toArrayCall
                    last = (Integer[]) lst.toArray(last);
                                
                }//for
                
                //this is a sucessfull complete, tell counter we are done
                cnt.inc(-1);
            } catch(Throwable e) {
                
                //this is an error complete, still tell counter we are done
                cnt.inc(-1);
                
                //try to log the error
                try {
                    
                    //make sure no one else is writing to the log
                    //at the same time
                    synchronized(cnt) {
                        
                        //record failed thread, sizes, error, and list contents
                        cnt.status("");
                        cnt.status(name + ": Failed");
                        cnt.status("Last Size: " + lsize);
                        cnt.status("Reported Size: " + lst.size());
                        cnt.status("Feed Size: " + last.length);
                        cnt.error(e);
                        cnt.status("Dump: " + lst.toString());
                        cnt.status("");
                        
                    }//syncronized
                    
                } catch(Throwable ee) { ee.printStackTrace(); }
            }//try..catch
        }
    }
    
    
    //main counter and synchronizer, takes care of waiting and logs
    protected class Counter {
        int count = 0;
        boolean running = false;
        PrintWriter buff; //log buffer
        
        //initialize log file
        public Counter(String file) throws IOException {
            buff = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            
        }//Counter()
        
        //run state methods
        public synchronized void run() { running = true; }
        public synchronized void stop() { running = false; }
        public synchronized boolean isRunning() { return running; }
        
        //running count increment/decrement method
        public synchronized void inc(int i) { count += i; }
        
        //status output method
        public synchronized void status(String str) throws IOException {
            System.out.println(str);
            buff.println(str);
            buff.flush();
            
        }//status();
        
        //error output method
        public synchronized void error(Throwable e) {
            e.printStackTrace();
            e.printStackTrace(buff);
            buff.flush();
        }//status();
        
        //finishing method, log file close
        public synchronized void done() {
            buff.flush();
            buff.close();
            
        }//done()
    }//Counter
}//Test
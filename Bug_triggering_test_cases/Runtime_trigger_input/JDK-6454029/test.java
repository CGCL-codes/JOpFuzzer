
public class JMMTest
{
  public JMMTest(int n)
  {
    int[] pairs = new int[]{30, 60, 100};

    for (int i = 0; i < pairs.length; i++)
    {
      for (int k = 1; k < 3; k++)
      {
        System.out.println("-- Type: " + k);
        System.out.println("---- " + pairs[i] + " Pairs ...");
        Replier[] replier = new Replier[pairs[i]];
        Requestor[] requestor = new Requestor[pairs[i]];
        for (int j = 0; j < pairs[i]; j++)
        {
          replier[j] = new Replier(n);
          requestor[j] = new Requestor(new RequestFactory(k), replier[j], n);
        }
        for (int j = 0; j < pairs[i]; j++)
        {
          replier[j].start();
          requestor[j].start();
        }
        for (int j = 0; j < pairs[i]; j++)
        {
          try
          {
            replier[j].join();
            requestor[j].join();
          } catch (InterruptedException e)
          {
            e.printStackTrace();
          }
        }
      }
    }

  }
  public static void main(String args[])
  {
    new JMMTest(Integer.parseInt(args[0]));
  }

  private class Requestor extends Thread
  {
    RequestFactory factory = null;
    Replier replier = null;
    int n = 0;

    public Requestor(RequestFactory factory, Replier replier, int n)
    {
      this.factory = factory;
      this.replier = replier;
      this.n = n;
    }

    public void run()
    {
      for (int i = 0; i < n; i++)
      {
        Request request = factory.createRequest();
        replier.setRequest(request);
        replier.getSemaphore().notifySingleWaiter();
        request.getSemaphore().waitHere();
        synchronized (replier)
        {
          if (request.getReply() == null)
          {
            System.err.println("Reply == null, n=" + request.getN() + ", class=" + request.getClass().getName());
            System.exit(-1);
          }
        }
      }
    }
  }

  private class Replier extends Thread
  {
    Request request = null;
    Semaphore semaphore = new Semaphore();
    int n = 0;

    public Replier(int n)
    {
      this.n = n;
    }

    public Semaphore getSemaphore()
    {
      return semaphore;
    }

    public synchronized void setRequest(Request request)
    {
      this.request = request;
    }

    public void run()
    {
      for (int i = 0; i < n; i++)
      {
        semaphore.waitHere();
        synchronized (this)
        {
          if (request == null)
          {
            System.err.println("request == null, n=" + n);
            System.exit(-1);
          }
          semaphore.reset();
          Semaphore sem = request.getSemaphore();
          request.setReply(new Reply(request.getN()));
          request = null;
          sem.notifySingleWaiter();
        }
      }
    }
  }


  private class Semaphore
  {
    boolean notified = false;

    public Semaphore()
    {
    }

    public synchronized void waitHere()
    {
      if (!notified)
      {
        try
        {
          wait();
        } catch (Exception ignored)
        {
        }
      }
    }

    public synchronized void waitHere(long ms)
    {
      if (!notified)
      {
        try
        {
          wait(ms);
        } catch (Exception ignored)
        {
        }
      }
    }

    public synchronized void notifySingleWaiter()
    {
      notified = true;
      notify();
    }

    public synchronized void reset()
    {
      notified = false;
    }
  }

  private class RequestFactory
  {
    int type = 0;
    int n = 0;

    public RequestFactory(int type)
    {
      this.type = type;
    }

    public Request createRequest()
    {
      Request r = null;
      switch (type)
      {
        case 0:
          r = new URequest(++n);
          break;
        case 1:
          r = new VRequest(++n);
          break;
        case 2:
          r = new SRequest(++n);
          break;
      }
      return r;
    }

  }

  private interface Request
  {
    public int getN();

    public Reply getReply();

    public void setReply(Reply reply);

    public Semaphore getSemaphore();
  }

  private class URequest implements Request
  {
    int n = 0;
    Reply reply = null;
    Semaphore semaphore = new Semaphore();

    public URequest(int n)
    {
      this.n = n;
    }

    public int getN()
    {
      return n;
    }

    public Reply getReply()
    {
      return reply;
    }

    public void setReply(Reply reply)
    {
      this.reply = reply;
    }

    public Semaphore getSemaphore()
    {
      return semaphore;
    }
  }

  private class VRequest implements Request
  {
    int n = 0;
    volatile Reply reply = null;
    Semaphore semaphore = new Semaphore();

    public VRequest(int n)
    {
      this.n = n;
    }

    public int getN()
    {
      return n;
    }

    public Reply getReply()
    {
      return reply;
    }

    public void setReply(Reply reply)
    {
      this.reply = reply;
    }

    public Semaphore getSemaphore()
    {
      return semaphore;
    }
  }

  private class SRequest implements Request
  {
    int n = 0;
    Reply reply = null;
    Semaphore semaphore = new Semaphore();

    public SRequest(int n)
    {
      this.n = n;
    }

    public int getN()
    {
      return n;
    }

    public synchronized Reply getReply()
    {
      return reply;
    }

    public synchronized void setReply(Reply reply)
    {
      this.reply = reply;
    }

    public Semaphore getSemaphore()
    {
      return semaphore;
    }
  }

  private class Reply
  {
    int n = 0;
    boolean ok = false;
    Exception exception = null;
    boolean timeout = false;
    int requestNumber = 0;
    transient byte[] b = new byte[1024];

    public Reply(int n)
    {
      this.n = n;
    }
  }
}


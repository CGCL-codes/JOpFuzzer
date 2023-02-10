import java.util.*;

public class Finally
{
    public static void main(String argv[])
	throws Exception
    {
	long t = System.currentTimeMillis();
	List l = new ArrayList();
	for(int i=0; i<1000; i++){
	    try{
		    l.add("Test"+i);
	    }finally{
		    l.clear();
	    }
	    try{
		    l.add("Test"+i);
	    }finally{
		    l.clear();
	    }

	}
	System.out.println("Time taken: "+(System.currentTimeMillis()-t));
    }
}


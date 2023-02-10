
import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedAction;

import sun.misc.Unsafe;

public class Test
{
    static final Unsafe theUnsafe;

    static final int BYTE_ARRAY_BASE_OFFSET;

    static
    {
        theUnsafe = (Unsafe) AccessController.doPrivileged(
            new PrivilegedAction<Object>()
            {
                public Object run()
                {
try
{
Field f = Unsafe.class.getDeclaredField( " theUnsafe " );
f.setAccessible(true);
return f.get(null);
}
catch (Exception e)
{
throw new RuntimeException(e);
}
                }
            }
        );
        BYTE_ARRAY_BASE_OFFSET = theUnsafe.arrayBaseOffset(byte[].class);

        // sanity check - this should never fail
        if (theUnsafe.arrayIndexScale(byte[].class) != 1) {
            throw new AssertionError();
        }
    }

    public static void main(String[] args) throws Exception
    {
        byte[] testAry =  " TheTestingByteArray " .getBytes();

        System.out.println( " BYTE_ARRAY_BASE_OFFSET =  "  + BYTE_ARRAY_BASE_OFFSET);

        int offset = 0;
        int offsetAdj = offset + BYTE_ARRAY_BASE_OFFSET;
        System.out.print( " Getting Unsafe Long with additional offset 0 " );
        long lw = theUnsafe.getLong(testAry, offsetAdj);
        System.out.println( "  done " );

        offset = 8;
        offsetAdj = offset + BYTE_ARRAY_BASE_OFFSET;
        System.out.print( " Getting Unsafe Long with additional offset 8 " );
        lw = theUnsafe.getLong(testAry, offsetAdj);
        System.out.println( "  done " );

        offset = 16;
        offsetAdj = offset + BYTE_ARRAY_BASE_OFFSET;
        System.out.print( " Getting Unsafe Long with additional offset 16 " );
        lw = theUnsafe.getLong(testAry, offsetAdj);
        System.out.println( "  done " );

        offset = 4;
        offsetAdj = offset + BYTE_ARRAY_BASE_OFFSET;
        System.out.print( " Getting Unsafe Long with additional offset 4 " );
        lw = theUnsafe.getLong(testAry, offsetAdj);
        System.out.println( "  done " );

        System.out.println( " Testing Done " );
    }
}

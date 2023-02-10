
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ExampleTest {

    @Test
    public void test() throws Exception {
        ClassLoader classLoader = mock(ClassLoader.class);
        when(classLoader.loadClass(String.class.getName())).thenReturn((Class) String.class);
        Class.forName(String.class.getName(), false, classLoader);
    }
}

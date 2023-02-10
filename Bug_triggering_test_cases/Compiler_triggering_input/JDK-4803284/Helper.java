/**
 * Invoked methods are in a different class, to make optimizations
 * harder (compilers may special-case for "local" calls).
 * Method bodies are minimal because we are only interested in the call.
 */
public class Helper
{
    /**
     * Holds computed data, with non-optimizable initial value.
     */
    public static int x = Integer.parseInt("0");

    /**
     * The polymorphic virtual method (Hard).
     */
    public void polyHardF (int value) { x += value; }

    /**
     * The virtual method (Hard).
     */
    public void virtualHardF (int value) { x += value; }

    /**
     * The non-virtual method (Hard).
     */
    public final void finalHardF (int value) { x += value; }

    /**
     * The static method (Hard).
     */
    public static void staticHardF (int value) { x += value; }

    /**
     * The polymorphic virtual method (Easy).
     */
    public void polyEasyF (int value) { x++; }

    /**
     * The virtual method (Easy).
     */
    public void virtualEasyF (int value) { x++; }

    /**
     * The non-virtual method (Easy).
     */
    public final void finalEasyF (int value) { x++; }

    /**
     * The static method (Easy).
     */
    public static void staticEasyF (int value) { x++; }
}
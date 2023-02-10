/**
 * Used only to override the polymorphic method.
 */
public class HelperC extends HelperA
{
    /**
     * Makes Helper.polyHardF() even more polymorphic.
     */
    public void polyHardF (int value) { x |= value; }
}
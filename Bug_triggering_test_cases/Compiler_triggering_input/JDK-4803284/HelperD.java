/**
 * Used only to override the polymorphic method.
 */
public class HelperD extends HelperC implements HelperInterface
{
    /**
     * Makes Helper.polyHardF() even more polymorphic.
     */
    public void polyHardF (int value) { x ^= value; }
}
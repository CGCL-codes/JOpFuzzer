/**
 * Used only to override the polymorphic method.
 */
public class HelperA extends Helper
{
    /**
     * Makes Helper.polyHardF() polymorphic.
     */
    public void polyHardF (int value) { x -= value; }

    /**
     * Makes Helper.polyEasyF() polymorphic.
     */
    public void polyEasyF (int value) { x--; }
}
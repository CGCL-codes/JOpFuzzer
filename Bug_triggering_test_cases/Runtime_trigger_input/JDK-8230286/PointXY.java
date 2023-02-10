public class PointXY{
public record PointXY(int x, int y) {
    public static void main(String[] args) {
	PointXY origin = new PointXY(0, 0);

	System.out.println(origin);
    } //main
}
}
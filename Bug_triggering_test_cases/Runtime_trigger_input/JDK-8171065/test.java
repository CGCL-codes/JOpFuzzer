
public class BugExample {
    public static void main(String[] args) {
        BufferedImage staticImage = new BufferedImage(224, 224, BufferedImage.TYPE_BYTE_GRAY);
        BufferedImage image = new BufferedImage(224, 224, BufferedImage.TYPE_BYTE_GRAY);

        double scale = Double.NaN;

        AffineTransform transform = new AffineTransform();
        transform.scale(scale, scale);
        AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
        op.filter(image, staticImage);
    }
}

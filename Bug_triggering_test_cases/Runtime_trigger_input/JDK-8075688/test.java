
/* DISCLAIMER: This program is logically incorrect */
import java.io.IOException;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.xml.bind.DatatypeConverter;

final class ZipDigest {
	private ZipDigest() {}

	public static final void main(final String[] args) {
		final MessageDigest md;
		try {
			md = MessageDigest.getInstance(args[1].toUpperCase());
		} catch (NoSuchAlgorithmException noSuchAlgorithmException) {
			System.err.println("Message digest algorithm \"" + args[1] +
			                   "\" unknown: " +
                               noSuchAlgorithmException.getLocalizedMessage());
			System.exit(1);
			return;
		}
		final JarFile jarFile;
		try {
			for (final Enumeration<JarEntry> e = (jarFile =
			     new JarFile(args[0])).entries(); e.hasMoreElements();) {
					final JarEntry je;
					final DigestInputStream dis;
					(dis = new DigestInputStream(jarFile.getInputStream(
						je = e.nextElement()), md)).read((byte[])null,
						                                 0, (int)je.getSize());
				System.out.println(md.getAlgorithm() + ": " + DatatypeConverter.printBase64Binary(md.digest()));
				md.reset();
			}
		} catch (IOException ioException) {
			System.err.println("Could not read JAR file " + args[0] +
                               ": " + ioException.getLocalizedMessage());
			System.exit(1);
		}
	}
}

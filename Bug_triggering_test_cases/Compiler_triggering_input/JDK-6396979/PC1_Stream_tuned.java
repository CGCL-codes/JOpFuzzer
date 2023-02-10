public class PC1_Stream_tuned {
    int si, x1a2;
    int x1a0[] = new int[8];
    byte cle[] = new byte[17]; // Hold key
    private boolean usedAsEncrypter = false;
    private boolean alreadyUsed = false;

    /**
     * Creates a PC1_InputStream. Decodes an input stream of encoded data.
     *
     * @param in       The input stream to decode.
     * @param password 16 byte encryption key
     */

    public PC1_Stream_tuned(byte[] password) {
        System.arraycopy(password, 0, cle, 0, Math.min(16, password.length));
    }

    private int assemble() {
        x1a0[0] = ((cle[0] * 256) + cle[1]);

        int inter = code(0);

        x1a0[1] = (x1a0[0] ^ ((cle[2] * 256) + cle[3]));
        inter = inter ^ code(1);

        x1a0[2] = (x1a0[1] ^ ((cle[4] * 256) + cle[5]));
        inter = inter ^ code(2);

        x1a0[3] = (x1a0[2] ^ ((cle[6] * 256) + cle[7]));
        inter = inter ^ code(3);

        x1a0[4] = (x1a0[3] ^ ((cle[8] * 256) + cle[9]));
        inter = inter ^ code(4);

        x1a0[5] = (x1a0[4] ^ ((cle[10] * 256) + cle[11]));
        inter = inter ^ code(5);

        x1a0[6] = (x1a0[5] ^ ((cle[12] * 256) + cle[13]));
        inter = inter ^ code(6);

        x1a0[7] = (x1a0[6] ^ ((cle[14] * 256) + cle[15]));
        inter = inter ^ code(7);

        return inter;
    }

    protected int code(final int i) {
        int ax = x1a0[i];
        int dx = (x1a2 + i);
        int cx = 0x015a;
        int tmp;
        final int bx = 0x4e35;

        tmp = ax;
        ax = si;
        si = tmp;

        tmp = ax;
        ax = dx;
        dx = tmp;

        if (ax != 0) {
            ax = (ax * bx);
        }

        tmp = ax;
        ax = cx;
        cx = tmp;

        if (ax != 0) {
            ax = (ax * si);
            cx = (ax + cx);
        }

        tmp = ax;
        ax = si;
        si = tmp;

        ax = (ax * bx);
        dx = (cx + dx);
        ax++;

        x1a2 = dx;
        x1a0[i] = ax;

        return (ax ^ dx);
    }

    /**
     * Returns a plain byte, which has been unencrypted from the underlying
     * InputStream.
     *
     * @see java.io.FilterInputStream
     */

    public byte[] decrypt(byte[] encryptedData) {
        checkUsage(false);

        for (int i = 0; i < encryptedData.length; i++) {
            int c = encryptedData[i];

            int inter = assemble();
            int cfc = (inter >> 8);
            int cfd = (inter & 255);

            c = c ^ (cfc ^ cfd);

            for (int compte = 0; compte <= 15; compte++) {
                /* we mix the plaintext byte with the key */
                cle[compte] = (byte) (cle[compte] ^ c);
            }

            encryptedData[i] = (byte) c;
        }

        return encryptedData;
    }

    public byte[] encrypt(byte[] data) {
        checkUsage(true);

        for (int i = 0; i < data.length; i++) {
            int c = data[i];

            int inter = assemble();
            int cfc = (inter >> 8);
            int cfd = (inter & 255);

            for (int compte = 0; compte <= 15; compte++) {
                /* we mix the plaintext byte with the key */
                cle[compte] = (byte) (cle[compte] ^ c);
            }

            c = c ^ (cfc ^ cfd);
            data[i] = (byte) c;
        }

        return data;
    }

    private void checkUsage(boolean isEncrypter) {
        if (alreadyUsed) {
            if (usedAsEncrypter != isEncrypter) {
                throw new IllegalArgumentException("You may either use this class as encrypter or decrypter, not both!");
            }
        } else {
            alreadyUsed = true;
            usedAsEncrypter = isEncrypter;
        }
    }

    public static void main(String[] args) {
        byte[] testData = new byte[1024 * 1024];
        for (int i = 0; i < testData.length; i++) {
            testData[i] = (byte) (i % 127);
        }

        PC1_Stream_tuned dec = new PC1_Stream_tuned(testData);
        PC1_Stream_tuned enc = new PC1_Stream_tuned(testData);

        for (int i = 0; i < 5; i++) {
            enc.encrypt(testData);
            dec.decrypt(testData);
        }

        for (int m = 0; m < 10; m++) {
            int encCount = 50;
            System.out.println("Starte VerschlÃ¼sselung");
            long start = System.currentTimeMillis();
            for (int i = 0; i < encCount; i++) {
                enc.encrypt(testData);
                dec.decrypt(testData);
            }
            long end = System.currentTimeMillis();
            long duration = end - start;
            System.out.println("Encryption took: " + duration + " with " + ((double) encCount / ((double) duration / (double) 1000)) + "mb/s ");
        }
    }
}
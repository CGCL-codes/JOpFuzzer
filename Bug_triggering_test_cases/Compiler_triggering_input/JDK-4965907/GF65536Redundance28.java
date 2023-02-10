/*
 * Created on 03.08.2003
 * (C) by Micha Riser, 2003
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Random;

/**
 * @author micha
 *
 */
public class GF65536Redundance28 {

    static public class Data {

        public long getSize() {return size;}
        public long size;
        public byte[][] chunks;

    }

    public int nofBlocks(Data d) {
        int totalrawnofchunks = (int)Math.ceil((double)d.getSize()/(double)chunksize);
        return (int)Math.ceil((double)totalrawnofchunks/BLOCKSIZE);
    }

    public int neededChunks(Data d, int block) {
        int totalrawnofchunks = (int)Math.ceil((double)d.getSize()/(double)chunksize);
        int blocks = (int)Math.ceil((double)totalrawnofchunks/BLOCKSIZE);
        if (block == blocks-1) {
            int ret = totalrawnofchunks % BLOCKSIZE;
            if (ret==0) return BLOCKSIZE; else return ret;
        } else {
            return BLOCKSIZE;
        }
    }

    public int nofChunks(int neededchunks) {
        return (int)Math.ceil(neededchunks*1.28);
    }


    public Data encode(java.io.InputStream input, long datalength) {

        int totalrawnofchunks = (int)Math.ceil((double)datalength/(double)chunksize);
        int totaldesnofchunks = (int)Math.ceil(totalrawnofchunks*1.28);
        int blocks = (int)Math.ceil((double)totalrawnofchunks/BLOCKSIZE);

// now encode
        try{

            Data ret = new Data();
            ret.chunks = new byte[totaldesnofchunks][chunksize];
            ret.size = datalength;

            for(int blocki=0; blocki<blocks; blocki++) {

                int rawnofchunks,desnofchunks;

                if (blocki==blocks-1) {
                    rawnofchunks = totalrawnofchunks % BLOCKSIZE;
                    if (rawnofchunks==0) rawnofchunks = BLOCKSIZE;
                    desnofchunks = (int)Math.ceil(rawnofchunks*1.28);
                } else {
                    rawnofchunks = BLOCKSIZE;
                    desnofchunks = (int)Math.ceil(BLOCKSIZE*1.28);
                }

                short[] idbase = new short[rawnofchunks];
                for(short i=0; i<rawnofchunks; i++) idbase[i]=i;

                short[][]lix = GF65536.calculateLix(idbase,rawnofchunks,desnofchunks);

                short[] in = new short[rawnofchunks];
                short[] out = new short[desnofchunks];

                long retsize = ret.getSize();

                for(int i=0; i<chunksize; i+=2) {
                    int offset = i*rawnofchunks;
                    for(int j=0; j<rawnofchunks; j++) {
                        if (offset+2*j<retsize-1) {
                            in[j] = (short)( (input.read() & 0xff)+ (input.read() << 8) &0xffff );
                        } else {
                            if (offset+2*j == retsize-1) {
                                in[j] = (short)input.read();
                            } else {
                                in[j] = 0;
                            }
                        }

                    }
                    GF65536.polyeval(in,lix,out);
                    for(int j=0; j<desnofchunks; j++) {
                        ret.chunks[BLOCKSIZEENC*blocki+j][i] = (byte)((out[j] & 0xff00)>>8);
                        ret.chunks[BLOCKSIZEENC*blocki+j][i+1] = (byte)(out[j] & 0xff);
                    }
                }
            }

            return ret;

        } catch (java.io.IOException e) {
            return null;
        }

    }

    public static void main(String[] args) throws java.io.IOException {

        int SIZE =1630400;
        byte[] random = new byte[SIZE];
        java.util.Random rnd = new Random(1234);
        rnd.nextBytes(random);

        GF65536Redundance28 encoder = new GF65536Redundance28();
        GF65536.init();

        System.out.println("encoding..");
        Data enc = encoder.encode(new ByteArrayInputStream(random),SIZE);

    }

    private final int chunksize = 256*1024;
    static private final int BLOCKSIZE = 100;
    static private final int BLOCKSIZEENC = 128;

}
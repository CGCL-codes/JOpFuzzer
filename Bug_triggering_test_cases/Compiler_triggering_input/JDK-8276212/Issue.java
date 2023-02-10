import jdk.incubator.vector.FloatVector;
import jdk.incubator.vector.VectorOperators;
import jdk.incubator.vector.VectorSpecies;

import java.io.Serializable;
import java.util.Random;

/**
 * Run with: javac --release 17 --enable-preview --add-modules jdk.incubator.vector Issue.java \
 * && java --enable-preview --add-modules jdk.incubator.vector Issue
 */
public class Issue {

    @FunctionalInterface
    interface DistanceFunction<TVector, TDistance> extends Serializable {
        TDistance distance(TVector u, TVector v);
    }

    static class VectorFloatCosineDistance implements DistanceFunction<float[], Float> {
        private final VectorSpecies<Float> species;

        public VectorFloatCosineDistance(VectorSpecies<Float> species) {
            this.species = species;
        }

        @Override
        public Float distance(float[] u, float[] v) {
            FloatVector vecSum = FloatVector.zero(species);
            FloatVector xSquareV = FloatVector.zero(species);
            FloatVector ySquareV = FloatVector.zero(species);;

            for (int i = 0; i + (species.length()) <= u.length; i += species.length()) {
                FloatVector vecX = FloatVector.fromArray(species, u, i);
                FloatVector vecY = FloatVector.fromArray(species, v, i);
                vecSum = vecX.fma(vecY, vecSum);
                xSquareV = vecX.fma(vecX, xSquareV);
                ySquareV = vecY.fma(vecY, ySquareV);
            }
            float dot = vecSum.reduceLanes(VectorOperators.ADD);
            float nrv = ySquareV.reduceLanes(VectorOperators.ADD);
            float nru = xSquareV.reduceLanes(VectorOperators.ADD);

            float similarity = dot / (float)(Math.sqrt(nru) * Math.sqrt(nrv));
            return 1 - similarity;
        }
    }

    static class VectorFloat128CosineDistance implements DistanceFunction<float[], Float> {

        private static final VectorSpecies<Float> SPECIES_FLOAT_128 = FloatVector.SPECIES_128;

        @Override
        public Float distance(float[] u, float[] v) {
            FloatVector vecSum = FloatVector.zero(SPECIES_FLOAT_128);
            FloatVector xSquareV = FloatVector.zero(SPECIES_FLOAT_128);
            FloatVector ySquareV = FloatVector.zero(SPECIES_FLOAT_128);;

            for (int i = 0; i + (SPECIES_FLOAT_128.length()) <= u.length; i += SPECIES_FLOAT_128.length()) {
                FloatVector vecX = FloatVector.fromArray(SPECIES_FLOAT_128, u, i);
                FloatVector vecY = FloatVector.fromArray(SPECIES_FLOAT_128, v, i);
                vecSum = vecX.fma(vecY, vecSum);
                xSquareV = vecX.fma(vecX, xSquareV);
                ySquareV = vecY.fma(vecY, ySquareV);
            }
            float dot = vecSum.reduceLanes(VectorOperators.ADD);
            float nrv = ySquareV.reduceLanes(VectorOperators.ADD);
            float nru = xSquareV.reduceLanes(VectorOperators.ADD);

            float similarity = dot / (float)(Math.sqrt(nru) * Math.sqrt(nrv));
            return 1 - similarity;
        }
    }

    public static void main(String[] args) {

        float[] floatVector1 = createRandomVector(128);
        float[] floatVector2 = createRandomVector(128);

        long time1 = time(floatVector1, floatVector2, new VectorFloat128CosineDistance());
        long time2 = time(floatVector1, floatVector2, new VectorFloatCosineDistance(FloatVector.SPECIES_128));

        System.out.println(time1);
        System.out.println(time2);
    }

    private static long time(float[] floatVector1,
                             float[] floatVector2,
                             DistanceFunction<float[], Float> distanceFunction) {

        long start = System.currentTimeMillis();

        for (int i = 0; i < 100_000_000; i++) {
            distanceFunction.distance(floatVector1, floatVector2);
        }
        long end = System.currentTimeMillis();
        return end - start;
    }


    private static float[] createRandomVector(int size) {
        Random random = new Random();

        float[] result = new float[size];

        for (int i = 0; i < size; i++) {
            result[i] = random.nextFloat(1f);
        }

        return result;
    }

} 
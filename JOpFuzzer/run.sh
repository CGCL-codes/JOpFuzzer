case "$1" in
    -h|--help|?)
    echo "Usage: <jdkPath> <regressionTestPath> <seedNumber> <mutationRound>"
    echo "jdkPath: absolute path to the target jdk"
    echo "regressionTestPath: path to regression test"
    echo "seedNumber: seed number for mutation"
    echo "mutationRound: mutation round"
    exit 0
;;
esac

JDK_PATH=$1
REGRESSION_TEST_PATH=$2
SEED_NUMBER=$3
MUTATION_ROUND=$4
R=$(pwd)
cd JDK_PATH
lcov -i -c -d.-o init.info
cd $R
$JDK_PATH/bin/java -jar -jdkPath $JDK_PATH -regressionTestPath $REGRESSION_TEST_PATH -seedNumber $SEED_NUMBER -mutationRound $MUTATION_ROUND
cd $JDK_PATH
lcov -c -d . -o cover.info
lcov -a init.info -a cover.info -o total.info
lcov -e total.info "$JDK_PATH/src/hotspot/*" -o hotspot.info
lcov -l hotspot.info

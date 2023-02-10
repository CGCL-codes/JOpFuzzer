package Preprocess;

import Options.Option;
import javaslang.Tuple3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum ProfileData {
    PrintInlining {
        public List<Tuple3<String, List<Integer>, Boolean>> getOptionAndValue() {
            int[] index = new int[]{0, 38};
            return getValue(index);
        }

    }, PrintEliminateAutoBox {
        public List<Tuple3<String, List<Integer>, Boolean>> getOptionAndValue() {
            int[] index = new int[]{38, 40};
            return getValue(index);
        }
    }, PrintEliminateLocks {
        public List<Tuple3<String, List<Integer>, Boolean>> getOptionAndValue() {
            int[] index = new int[]{40, 42};
            return getValue(index);
        }
    }, PrintEliminateAllocations {
        public List<Tuple3<String, List<Integer>, Boolean>> getOptionAndValue() {
            int[] index = new int[]{42, 45};
            return getValue(index);
        }
    }, PrintEscapeAnalysis {
        public List<Tuple3<String, List<Integer>, Boolean>> getOptionAndValue() {
            int[] index = new int[]{45, 46};
            return getValue(index);
        }
    }, TraceOptimizeFill {
        public List<Tuple3<String, List<Integer>, Boolean>> getOptionAndValue() {
            int[] index = new int[]{46, 47};
            return getValue(index);
        }
    }, PrintBlockElimination {
        public List<Tuple3<String, List<Integer>, Boolean>> getOptionAndValue() {
            int[] index = new int[]{47, 48};
            return getValue(index);
        }
    }, PrintCanonicalization {
        public List<Tuple3<String, List<Integer>, Boolean>> getOptionAndValue() {
            int[] index = new int[]{48, 49};
            return getValue(index);
        }
    }, PrintCEE {
        public List<Tuple3<String, List<Integer>, Boolean>> getOptionAndValue() {
            int[] index = new int[]{49, 50};
            return getValue(index);
        }
    }, PrintNullCheckElimination {
        public List<Tuple3<String, List<Integer>, Boolean>> getOptionAndValue() {
            int[] index = new int[]{50, 51};
            return getValue(index);
        }
    }, PrintValueNumbering {
        public List<Tuple3<String, List<Integer>, Boolean>> getOptionAndValue() {
            int[] index = new int[]{51, 53};
            return getValue(index);
        }
    }, PrintOptimizeStringConcat {
        public List<Tuple3<String, List<Integer>, Boolean>> getOptionAndValue() {
            int[] index = new int[]{53, 54};
            return getValue(index);
        }
    }, TraceLoopOpts {
        public List<Tuple3<String, List<Integer>, Boolean>> getOptionAndValue() {
            int[] index = new int[]{54, 65};
            return getValue(index);
        }
    }, TraceLoopPredicate {
        public List<Tuple3<String, List<Integer>, Boolean>> getOptionAndValue() {
            int[] index = new int[]{65, 66};
            return getValue(index);
        }
    }, TraceLoopUnswitching {
        public List<Tuple3<String, List<Integer>, Boolean>> getOptionAndValue() {
            int[] index = new int[]{66, 67};
            return getValue(index);
        }
    }, TracePartialPeeling {
        public List<Tuple3<String, List<Integer>, Boolean>> getOptionAndValue() {
            int[] index = new int[]{67, 69};
            return getValue(index);
        }
    }, TraceSuperWord {
        public List<Tuple3<String, List<Integer>, Boolean>> getOptionAndValue() {
            int[] index = new int[]{69, 70};
            return getValue(index);
        }
    }, TraceSuperWordLoopUnrollAnalysis {
        public List<Tuple3<String, List<Integer>, Boolean>> getOptionAndValue() {
            int[] index = new int[]{70, 71};
            return getValue(index);
        }
    }, TraceRangeCheckElimination {
        public List<Tuple3<String, List<Integer>, Boolean>> getOptionAndValue() {
            int[] index = new int[]{71, 72};
            return getValue(index);
        }
    }, PrintOptoPeephole {
        public List<Tuple3<String, List<Integer>, Boolean>> getOptionAndValue() {
            int[] index = new int[]{72, 73};
            return getValue(index);
        }
    }, PrintDominators {
        public List<Tuple3<String, List<Integer>, Boolean>> getOptionAndValue() {
            int[] index = new int[]{73, 74};
            return getValue(index);
        }
    }, PrintOptimizePtrCompare {
        public List<Tuple3<String, List<Integer>, Boolean>> getOptionAndValue() {
            int[] index = new int[]{74, 75};
            return getValue(index);
        }
    }, PrintUnsafeOptimization {
        public List<Tuple3<String, List<Integer>, Boolean>> getOptionAndValue() {
            int[] index = new int[]{75, 76};
            return getValue(index);
        }
    }, PrintTieredEvents {
        public List<Tuple3<String, List<Integer>, Boolean>> getOptionAndValue() {
            int[] index = new int[]{76, 77};
            return getValue(index);
        }
    }, PrintCFG {
        public List<Tuple3<String, List<Integer>, Boolean>> getOptionAndValue() {
            int[] index = new int[]{77, 78};
            return getValue(index);
        }
    };

    public List<Tuple3<String, List<Integer>, Boolean>> getValue(int[] index) {
        List<Tuple3<String, List<Integer>, Boolean>> result = new ArrayList<>();
        List<Option> op = new ArrayList<>(Arrays.asList(Option.values()).subList(index[0], index[1]));
        for (Option option : op) {
            int[] values = option.getRange();
//            int defaultValue = values[0];
            List<Integer> testValue = new ArrayList<>();
            testValue.add(values[1]);
            testValue.add(values[2]);
//            if (defaultValue == values[1]) {
//                testValue.add(values[2]);
//            } else {
//                testValue.add(defaultValue);
//            }
            Boolean isBoolean = values[2] == 1;
            result.add(new Tuple3<>(option.toString(), testValue, isBoolean));
        }
        return result;
    }


    public abstract List<Tuple3<String, List<Integer>, Boolean>> getOptionAndValue();
}
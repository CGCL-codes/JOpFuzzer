private void readVMIntConstants() {
        String symbol = "heapOopSize"; // global int constant and value is initialized at runtime.
        addIntConstant(symbol, (int)lookupInProcess(symbol).getCIntegerAt(0, 4, false));
        }
# JOpFuzzer
This is a supplementary repository for paper submission **Detecting JVM JIT Compiler Bugs via Exploring Two-Dimensional Input Spaces**.
## Confirmed Bugs
Since unconfirmed bugs cannot be shown in [Java Bug System](https://bugs.openjdk.org/secure/Dashboard.jspa)(JBS), we only show the bugs that are confirmed by developers. Also, since the information of the reporter cannot be found in JBS, there are several screenshots of the confirmation email sent by Oracle under the directory [**Confirmed**](https://github.com/JOpFuzzer/JOpFuzzer/tree/main/Confirmed).

Affected Versions| Bug ID | Link
|-------|------|------|
8, 11, 17, 18|JDK-8284944|https://bugs.openjdk.org/browse/JDK-8284944 |
17, 19|JDK-8284883|https://bugs.openjdk.org/browse/JDK-8284883|
17, 18, 19, 20| JDK-8291775 |https://bugs.openjdk.org/browse/JDK-8291775
20 | JDK-8291781 | https://bugs.openjdk.org/browse/JDK-8291781
20 | JDK-8291785 | https://bugs.openjdk.org/browse/JDK-8291785
20 | JDK-8293798 | https://bugs.openjdk.org/browse/JDK-8293798
11 | JDK-8291715 |https://bugs.openjdk.org/browse/JDK-8291715
11 | JDK-8291713 | https://bugs.openjdk.org/browse/JDK-8291713
8 |JDK-8283745| https://bugs.openjdk.org/browse/JDK-8283745 |
11, 17, 18, 19|JDK-8284881|https://bugs.openjdk.org/browse/JDK-8284881|
11, 17, 19|JDK-8284879|https://bugs.openjdk.org/browse/JDK-8284879|
11, 17, 18, 19, 20| JDK-8293410 |https://bugs.openjdk.org/browse/JDK-8293410
8, 11| JDK-8284882|https://bugs.openjdk.org/browse/JDK-8284882|
 8 |JDK-8284945|https://bugs.openjdk.org/browse/JDK-8284945 |
 8 |JDK-8284946|https://bugs.openjdk.org/browse/JDK-8284946|
 8 |JDK-8284952|https://bugs.openjdk.org/browse/JDK-8284952|
 8, 11, 18, 19|JDK-8283740|https://bugs.openjdk.org/browse/JDK-8283740|
 8 |JDK-8284947|https://bugs.openjdk.org/browse/JDK-8284947|
 8 |JDK-8284954|https://bugs.openjdk.org/browse/JDK-8284954|
 17, 18, 19|JDK-8285301|https://bugs.openjdk.org/browse/JDK-8285301|
 8 |JDK-8284731|https://bugs.openjdk.org/browse/JDK-8284731|
 8 |JDK-8284733|https://bugs.openjdk.org/browse/JDK-8284733|
 11, 17, 18, 19|JDK-8284951|https://bugs.openjdk.org/browse/JDK-8284951 |
 8 |JDK-8291463|https://bugs.openjdk.org/browse/JDK-8291463
 11 | JDK-8291461| https://bugs.openjdk.org/browse/JDK-8291461
 20 | JDK-8292584 | https://bugs.openjdk.org/browse/JDK-8292584
 20 | JDK-8291790 |https://bugs.openjdk.org/browse/JDK-8291790
 11 | JDK-8292863 | https://bugs.openjdk.org/browse/JDK-8292863
 20 | JDK-8292582 | https://bugs.openjdk.org/browse/JDK-8292582
 17, 19, 20| JDK-8291809 | https://bugs.openjdk.org/browse/JDK-8291809

## Empirical Study
We provide the raw data and the results in our whole study.
Raw data contains two parts: bug reports and bug triggering test cases. We store the bug reports in Google Drive ([JDK_data](https://drive.google.com/file/d/1wVk8cGMrYR88TRoD1pk32oVUWWWf3A8s/view?usp=sharing)),
and bug triggering test cases in directory [Bug_triggering_test_cases](https://github.com/JOpFuzzer/JOpFuzzer/tree/main/Bug_triggering_test_cases).

We use the tool in the directory [Scripts]() to extract the result in the empirical study. 
Users should download the JDK_data and Compiler_data and run the process.py with a proper option.

## Usage of JOpFuzzer
**Step 1: Enviroment Setup**
JOpFuzzer needs the debug build of JVM, so users should download the source code of JVM and set the debug flag. Here we take the OpenJDK11 as an example.
```
# git clone https://github.com/openjdk/jdk11u.git
# cd jdk11u
# bash configure --enable-debug
# make images
```
To test regression tests in OpenJDK or other JDKs, users also need to download JTreg and set the location of JTreg at configuration time.
```
# git clone https://github.com/openjdk/jtreg.git
# cd jtreg
# sh make/build.sh --jdk <path to JDK home>

# cd jdk11u
# bash configure --enable-debug --with-jtreg=<path to jtreg home>
# make images
```

**Step 2: Run the Tool**
JOpFuzzer is developed as a maven project, so to configure and run JOpFuzzer, you can import it directly into your IntelliJ IDEA workspace as a maven project.
Execute **MainEntry** under src/main where the arguments are set as shown below:
```
-jdkPath                the jdk under test (e.g., /Library/Java/JavaVirtualMachines/jdk-11.jdk/Contents/Home)
-seedPath               the seed for learning correlation (e.g., ./compiler, the regression test for compiler)
-testNumber             the number of tests generated by JavaFuzzer (e.g., 1000)
-mutationRound          the number of mutation round (e.g., 1000)
```
Note that we use spoon for java source code structure analysis, but there is a dependency conflict with the relevant code in **tbar**, and the package name where the conflict occurs is the same. So pre-generate the adapted **tbar** as JavaMutator.jar to avoid conflicts.

## List of profile data and optimization options  

### PrintInlining
```
AlwaysIncrementalInline
ArrayCopyLoadStoreMaxElem
ArrayOperationPartialInlineSize
C1InlineStackLimit
C1MaxInlineLevel
C1MaxInlineSize
C1MaxRecursiveInlineLevel
C1MaxTrivialSize
C1ProfileInlinedCalls
ClipInlining
DebugInlinedCalls
FreqInlineSize
IncrementalInline
IncrementalInlineForceCleanup
IncrementalInlineMH
IncrementalInlineVirtual
Inline
InlineAccessors
InlineArrayCopy
InlineClassNatives
InlineIntrinsics
InlineMathNatives
InlineMethodsWithExceptionHandlers
InlineNatives
InlineNIOCheckIndex
InlineObjectCopy
InlineObjectHash
InlineReflectionGetCallerClass
InlineSmallCode
InlineSynchronizedMethods
InlineThreadNatives
InlineUnsafeOps
InlineWarmCalls
LiveNodeCountInliningCutoff
MultiArrayExpandLimit
NestedInliningSizeRatio
NodeCountInliningCutoff
NodeCountInliningStep
```
### PrintEliminateAutoBox
```
EliminateAutoBox
AggressiveUnboxing
AutoBoxCacheMax
```
### PrintEliminateLocks
```
EliminateLocks
EliminateNestedLocks
```
### PrintEliminateAllocations
```
EliminateAllocationArraySizeLimit
EliminateAllocationFieldsLimit
EliminateAllocations
```
### PrintEscapeAnalysis
```
DoEscapeAnalysis
```
### TraceOptimizeFill
```
OptimizeFill
```
### PrintBlockElimination
```
EliminateBlocks
```
### PrintCanonicalization
```
CanonicalizeNodes
```
### PrintCEE
```
DoCEE
```
### PrintNullCheckElimination
```
EliminateNullChecks
```
### PrintValueNumbering
```
UseGlobalValueNumbering
UseLocalValueNumbering
```
### PrintNullCheckElimination
```
PrintOptimizeStringConcat
```
### TraceLoopOpts
```
AlignVector
BlockLayoutRotateLoops
LoopMaxUnroll
LoopOptsCount
LoopPercentProfileLimit
LoopStripMiningIter
LoopStripMiningIterShortLoop
LoopUnrollLimit
LoopUnrollMin
MaxLoopPad
NumberOfLoopInstrToAlign
```
### TraceLoopPredicate
```
UseLoopPredicate
```
### TraceLoopUnswitching
```
LoopUnswitching
```
### TracePartialPeeling
```
PartialPeelLoop
PartialPeelNewPhiDelta
```
### TraceSuperWord
```
UseSuperWord
```
### TraceSuperWordLoopUnrollAnalysis
```
SuperWordLoopUnrollAnalysis
```
### TraceRangeCheckElimination
```
RangeCheckElimination
```
### PrintOptoPeephole
```
OptoPeephole
```
### PrintDominators
```
DominatorSearchLimit
```
### PrintOptimizePtrCompare
```
OptimizePtrCompare
```
### PrintUnsafeOptimization
```
OptimizeUnsafes
```
### PrintTieredEvents
```
TieredCompilation
```
### PrintCFG
```
SplitIfBlocks
```


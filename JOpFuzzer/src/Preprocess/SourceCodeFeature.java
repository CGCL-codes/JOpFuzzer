package Preprocess;

public enum SourceCodeFeature {
    assignmentStmt, returnStmt, ifStmt, invocationStmt, switchStmt, constructorCallStmt, tryStmt, throwStmt,
    arithmeticOperator, shiftOperator, compareOperator, logicalOperator, unaryOperator,
    booleanType, byteType, charType, integerType, floatType, doubleType, longType, shortType, nullType,
    loopDepthOne, loopDepthTwo, loopDepthThreeOrMore,
    arrayDimensionOne, arrayDimensionTwo, arrayDimensionThreeOrMore,
    fieldAccess, synchronizedAccess, enumValue, instance, lambda
}
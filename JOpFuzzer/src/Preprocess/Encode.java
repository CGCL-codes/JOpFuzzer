package Preprocess;

import spoon.reflect.code.*;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtEnumValue;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.reference.CtArrayTypeReference;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.visitor.filter.LineFilter;
import spoon.reflect.visitor.filter.TypeFilter;
import spoon.support.reflect.code.CtBlockImpl;


import java.util.ArrayList;
import java.util.List;

public class Encode {
    int[] code = new int[SourceCodeFeature.values().length];
    String methodName;
    Boolean uselessMethod = false;

    public Encode(CtMethod method) {
        methodName = method.getSimpleName();
        CtBlock block = method.getBody();
        if (block == null || block.getElements(new LineFilter()).size() <= 3 || block.getElements(new LineFilter()).size() > 100) { // do not consider methods with less three statements, or with too many statements
            uselessMethod = true;
            return;
        }
        analyzeStmt(method);
        analyzeOperator(method);
        analyzeType(method);
        analyzeLoop(method);
    }

    public Encode(CtStatement statement) {
        analyzeParent(statement);
        analyzeStmt(statement);
        analyzeOperator(statement);
        analyzeType(statement);
        analyzeLoop(statement);
    }

    private void analyzeParent(CtStatement statement) {
        try {
            CtElement p = statement.getParent();
            identifyParent(p);
        } catch (Exception ignored) {
        }
    }

    private void identifyParent(CtElement p) {
        if (p instanceof CtMethod) {
            return;
        } else if (p instanceof CtLoop) {
            if (code[SourceCodeFeature.loopDepthTwo.ordinal()] == 1) {
                code[SourceCodeFeature.loopDepthThreeOrMore.ordinal()] = 1;
            } else if (code[SourceCodeFeature.loopDepthOne.ordinal()] == 1) {
                code[SourceCodeFeature.loopDepthTwo.ordinal()] = 1;
            } else {
                code[SourceCodeFeature.loopDepthOne.ordinal()] = 1;
            }
        } else if (p instanceof CtIf) {
            code[SourceCodeFeature.ifStmt.ordinal()] = 1;
        } else if (p instanceof CtSwitch) {
            code[SourceCodeFeature.switchStmt.ordinal()] = 1;
        } else if (p instanceof CtTry) {
            code[SourceCodeFeature.tryStmt.ordinal()] = 1;
        } else if (p instanceof CtThrow) {
            code[SourceCodeFeature.throwStmt.ordinal()] = 1;
        }
        identifyParent(p.getParent());
    }

    private void analyzeLoop(CtMethod method) {
        List<CtLoop> loops = method.getElements(new TypeFilter<>(CtLoop.class));
        if (loops.size() > 0)
            code[SourceCodeFeature.loopDepthOne.ordinal()] = 1;
        else
            return;
//        Comparator<CtLoop> comparator = Comparator.comparingInt(l -> ((CtBlockImpl) l.getBody()).getStatements().size());
//        loops.sort(comparator.reversed());
        loops.sort((l1, l2) -> {
            int size1 = 0;
            int size2 = 0;
            if (l1.getBody() != null)
                size1 = ((CtBlockImpl) l1.getBody()).getStatements().size();
            if (l2.getBody() != null)
                size2 = ((CtBlockImpl) l2.getBody()).getStatements().size();
            return size2 - size1;
        });
        for (CtLoop l : loops) {
            if (l.getBody() == null)
                continue;
            List<CtLoop> nestedLoop = l.getBody().getElements(new TypeFilter<>(CtLoop.class));
            if (nestedLoop.size() != 0) {
                code[SourceCodeFeature.loopDepthTwo.ordinal()] = 1;
                for (CtLoop nl : nestedLoop) {
                    if (nl.getBody() == null)
                        continue;
                    if (nl.getBody().getElements(new TypeFilter<>(CtLoop.class)).size() != 0) {
                        code[SourceCodeFeature.loopDepthThreeOrMore.ordinal()] = 1;
                        return;
                    }
                }
            }
        }
    }

    private void analyzeLoop(CtStatement snippet) {
        List<CtLoop> loops = snippet.getElements(new TypeFilter<>(CtLoop.class));
        if (loops.size() > 0)
            code[SourceCodeFeature.loopDepthOne.ordinal()] = 1;
        else
            return;
//        Comparator<CtLoop> comparator = Comparator.comparingInt(l -> ((CtBlockImpl) l.getBody()).getStatements().size());
//        loops.sort(comparator.reversed());
        loops.sort((l1, l2) -> {
            int size1 = 0;
            int size2 = 0;
            if (l1.getBody() != null)
                size1 = ((CtBlockImpl) l1.getBody()).getStatements().size();
            if (l2.getBody() != null)
                size2 = ((CtBlockImpl) l2.getBody()).getStatements().size();
            return size2 - size1;
        });
        for (CtLoop l : loops) {
            if (l.getBody() == null)
                continue;
            List<CtLoop> nestedLoop = l.getBody().getElements(new TypeFilter<>(CtLoop.class));
            if (nestedLoop.size() != 0) {
                code[SourceCodeFeature.loopDepthTwo.ordinal()] = 1;
                for (CtLoop nl : nestedLoop) {
                    if (nl.getBody() == null)
                        continue;
                    if (nl.getBody().getElements(new TypeFilter<>(CtLoop.class)).size() != 0) {
                        code[SourceCodeFeature.loopDepthThreeOrMore.ordinal()] = 1;
                        return;
                    }
                }
            }
        }
    }

    public ArrayList<Integer> getStructureIndex() {
        ArrayList<Integer> structureIndex = new ArrayList<Integer>();
        for (int i = 0; i < this.code.length; i++) {
            if (code[i] == 1) {
                structureIndex.add(i);
            }
        }
        return structureIndex;
    }

    public String getMethodName() {
        return methodName;
    }

    private void analyzeType(CtMethod method) {
        List<CtVariableAccess> variables = method.getElements(new TypeFilter<>(CtVariableAccess.class));
        for (CtVariableAccess variable : variables) {
            CtTypeReference type = variable.getType();
            if (type == null)
                continue;
            if (type instanceof CtArrayTypeReference) {
                int dimension = ((CtArrayTypeReference) type).getDimensionCount();
                if (dimension > 2)
                    code[SourceCodeFeature.arrayDimensionThreeOrMore.ordinal()] = 1;
                else if (dimension == 2)
                    code[SourceCodeFeature.arrayDimensionTwo.ordinal()] = 1;
                else
                    code[SourceCodeFeature.arrayDimensionOne.ordinal()] = 1;
            } else if (type.getSimpleName().equals("byte")) {
                code[SourceCodeFeature.byteType.ordinal()] = 1;
            } else if (type.getSimpleName().equals("boolean")) {
                code[SourceCodeFeature.booleanType.ordinal()] = 1;
            } else if (type.getSimpleName().equals("char")) {
                code[SourceCodeFeature.charType.ordinal()] = 1;
            } else if (type.getSimpleName().equals("int")) {
                code[SourceCodeFeature.integerType.ordinal()] = 1;
            } else if (type.getSimpleName().equals("float")) {
                code[SourceCodeFeature.floatType.ordinal()] = 1;
            } else if (type.getSimpleName().equals("double")) {
                code[SourceCodeFeature.doubleType.ordinal()] = 1;
            } else if (type.getSimpleName().equals("short")) {
                code[SourceCodeFeature.shortType.ordinal()] = 1;
            } else if (type.getSimpleName().equals("null")) {
                code[SourceCodeFeature.nullType.ordinal()] = 1;
            }

        }
    }


    private void analyzeType(CtStatement snippet) {
        List<CtVariableAccess> variables = snippet.getElements(new TypeFilter<>(CtVariableAccess.class));
        for (CtVariableAccess variable : variables) {
            CtTypeReference type = variable.getType();
            if (type == null)
                continue;
            if (type instanceof CtArrayTypeReference) {
                int dimension = ((CtArrayTypeReference) type).getDimensionCount();
                if (dimension > 2)
                    code[SourceCodeFeature.arrayDimensionThreeOrMore.ordinal()] = 1;
                else if (dimension == 2)
                    code[SourceCodeFeature.arrayDimensionTwo.ordinal()] = 1;
                else
                    code[SourceCodeFeature.arrayDimensionOne.ordinal()] = 1;
            } else if (type.getSimpleName().equals("byte")) {
                code[SourceCodeFeature.byteType.ordinal()] = 1;
            } else if (type.getSimpleName().equals("boolean")) {
                code[SourceCodeFeature.booleanType.ordinal()] = 1;
            } else if (type.getSimpleName().equals("char")) {
                code[SourceCodeFeature.charType.ordinal()] = 1;
            } else if (type.getSimpleName().equals("int")) {
                code[SourceCodeFeature.integerType.ordinal()] = 1;
            } else if (type.getSimpleName().equals("float")) {
                code[SourceCodeFeature.floatType.ordinal()] = 1;
            } else if (type.getSimpleName().equals("double")) {
                code[SourceCodeFeature.doubleType.ordinal()] = 1;
            } else if (type.getSimpleName().equals("short")) {
                code[SourceCodeFeature.shortType.ordinal()] = 1;
            } else if (type.getSimpleName().equals("null")) {
                code[SourceCodeFeature.nullType.ordinal()] = 1;
            }

        }
    }

    private void analyzeOperator(CtMethod method) {
        List<CtBinaryOperator> operators = method.getElements(new TypeFilter<>(CtBinaryOperator.class));
        if (operators.size() != 0) {
            for (CtBinaryOperator bo : operators) {
                BinaryOperatorKind op = bo.getKind();
                if (op == BinaryOperatorKind.AND || op == BinaryOperatorKind.OR)
                    code[SourceCodeFeature.logicalOperator.ordinal()] = 1;
                else if (op == BinaryOperatorKind.DIV || op == BinaryOperatorKind.MINUS || op == BinaryOperatorKind.PLUS || op == BinaryOperatorKind.MUL || op == BinaryOperatorKind.MOD)
                    code[SourceCodeFeature.arithmeticOperator.ordinal()] = 1;
                else if (op == BinaryOperatorKind.SL || op == BinaryOperatorKind.SR || op == BinaryOperatorKind.USR)
                    code[SourceCodeFeature.shiftOperator.ordinal()] = 1;
                else if (op == BinaryOperatorKind.INSTANCEOF)
                    code[SourceCodeFeature.instance.ordinal()] = 1;
                else if (op == BinaryOperatorKind.EQ || op == BinaryOperatorKind.GT || op == BinaryOperatorKind.GE || op == BinaryOperatorKind.NE || op == BinaryOperatorKind.LE || op == BinaryOperatorKind.LT)
                    code[SourceCodeFeature.compareOperator.ordinal()] = 1;
            }
        }
        if (method.getElements(new TypeFilter<>(CtUnaryOperator.class)).size() != 0)
            code[SourceCodeFeature.unaryOperator.ordinal()] = 1;
    }

    private void analyzeOperator(CtStatement snippet) {
        List<CtBinaryOperator> operators = snippet.getElements(new TypeFilter<>(CtBinaryOperator.class));
        if (operators.size() != 0) {
            for (CtBinaryOperator bo : operators) {
                BinaryOperatorKind op = bo.getKind();
                if (op == BinaryOperatorKind.AND || op == BinaryOperatorKind.OR)
                    code[SourceCodeFeature.logicalOperator.ordinal()] = 1;
                else if (op == BinaryOperatorKind.DIV || op == BinaryOperatorKind.MINUS || op == BinaryOperatorKind.PLUS || op == BinaryOperatorKind.MUL || op == BinaryOperatorKind.MOD)
                    code[SourceCodeFeature.arithmeticOperator.ordinal()] = 1;
                else if (op == BinaryOperatorKind.SL || op == BinaryOperatorKind.SR || op == BinaryOperatorKind.USR)
                    code[SourceCodeFeature.shiftOperator.ordinal()] = 1;
                else if (op == BinaryOperatorKind.INSTANCEOF)
                    code[SourceCodeFeature.instance.ordinal()] = 1;
                else if (op == BinaryOperatorKind.EQ || op == BinaryOperatorKind.GT || op == BinaryOperatorKind.GE || op == BinaryOperatorKind.NE || op == BinaryOperatorKind.LE || op == BinaryOperatorKind.LT)
                    code[SourceCodeFeature.compareOperator.ordinal()] = 1;
            }
        }
        if (snippet.getElements(new TypeFilter<>(CtUnaryOperator.class)).size() != 0)
            code[SourceCodeFeature.unaryOperator.ordinal()] = 1;
    }


    public void analyzeStmt(CtMethod method) {
        if (method.getElements(new TypeFilter<>(CtAssignment.class)).size() != 0)
            code[SourceCodeFeature.assignmentStmt.ordinal()] = 1;
        if (method.getElements(new TypeFilter<>(CtReturn.class)).size() != 0)
            code[SourceCodeFeature.returnStmt.ordinal()] = 1;
        if (method.getElements(new TypeFilter<>(CtIf.class)).size() != 0)
            code[SourceCodeFeature.ifStmt.ordinal()] = 1;
        if (method.getElements(new TypeFilter<>(CtInvocation.class)).size() != 0)
            code[SourceCodeFeature.invocationStmt.ordinal()] = 1;
        if (method.getElements(new TypeFilter<>(CtSwitch.class)).size() != 0)
            code[SourceCodeFeature.switchStmt.ordinal()] = 1;
        if (method.getElements(new TypeFilter<>(CtConstructorCall.class)).size() != 0)
            code[SourceCodeFeature.constructorCallStmt.ordinal()] = 1;
        if (method.getElements(new TypeFilter<>(CtTry.class)).size() != 0)
            code[SourceCodeFeature.tryStmt.ordinal()] = 1;
        if (method.getElements(new TypeFilter<>(CtThrow.class)).size() != 0)
            code[SourceCodeFeature.throwStmt.ordinal()] = 1;
        if (method.getElements(new TypeFilter<>(CtFieldAccess.class)).size() != 0)
            code[SourceCodeFeature.fieldAccess.ordinal()] = 1;
        if (method.getElements(new TypeFilter<>(CtSynchronized.class)).size() != 0)
            code[SourceCodeFeature.synchronizedAccess.ordinal()] = 1;
        if (method.getElements(new TypeFilter<>(CtEnumValue.class)).size() != 0)
            code[SourceCodeFeature.enumValue.ordinal()] = 1;
        if (method.getElements(new TypeFilter<>(CtLambda.class)).size() != 0)
            code[SourceCodeFeature.lambda.ordinal()] = 1;
    }

    public void analyzeStmt(CtStatement snippet) {
        if (snippet.getElements(new TypeFilter<>(CtAssignment.class)).size() != 0)
            code[SourceCodeFeature.assignmentStmt.ordinal()] = 1;
        if (snippet.getElements(new TypeFilter<>(CtReturn.class)).size() != 0)
            code[SourceCodeFeature.returnStmt.ordinal()] = 1;
        if (snippet.getElements(new TypeFilter<>(CtIf.class)).size() != 0)
            code[SourceCodeFeature.ifStmt.ordinal()] = 1;
        if (snippet.getElements(new TypeFilter<>(CtInvocation.class)).size() != 0)
            code[SourceCodeFeature.invocationStmt.ordinal()] = 1;
        if (snippet.getElements(new TypeFilter<>(CtSwitch.class)).size() != 0)
            code[SourceCodeFeature.switchStmt.ordinal()] = 1;
        if (snippet.getElements(new TypeFilter<>(CtConstructorCall.class)).size() != 0)
            code[SourceCodeFeature.constructorCallStmt.ordinal()] = 1;
        if (snippet.getElements(new TypeFilter<>(CtTry.class)).size() != 0)
            code[SourceCodeFeature.tryStmt.ordinal()] = 1;
        if (snippet.getElements(new TypeFilter<>(CtThrow.class)).size() != 0)
            code[SourceCodeFeature.throwStmt.ordinal()] = 1;
        if (snippet.getElements(new TypeFilter<>(CtFieldAccess.class)).size() != 0)
            code[SourceCodeFeature.fieldAccess.ordinal()] = 1;
        if (snippet.getElements(new TypeFilter<>(CtSynchronized.class)).size() != 0)
            code[SourceCodeFeature.synchronizedAccess.ordinal()] = 1;
        if (snippet.getElements(new TypeFilter<>(CtEnumValue.class)).size() != 0)
            code[SourceCodeFeature.enumValue.ordinal()] = 1;
        if (snippet.getElements(new TypeFilter<>(CtLambda.class)).size() != 0)
            code[SourceCodeFeature.lambda.ordinal()] = 1;
    }

}


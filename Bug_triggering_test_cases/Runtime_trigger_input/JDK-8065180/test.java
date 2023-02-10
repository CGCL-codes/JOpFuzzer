
Here's ASM 5 code that generates a class that triggers the error:

         ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        classWriter.visit(
            Opcodes.V1_6, Opcodes.ACC_PUBLIC, "foo/Bar", null, "java/lang/Object", null
        );
        MethodVisitor mv = classWriter.visitMethod(
                Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC,
                "main",
                "([Ljava/lang/String;)V",
                null, null
        );
    
        InstructionAdapter ia = new InstructionAdapter(mv);
    
        ia.iconst(3);
        ia.newarray(Type.INT_TYPE);
        ia.iconst(0);
        ia.iconst(0);
        ia.invokevirtual("[I", "set", "(II)V", false);
        ia.areturn(Type.VOID_TYPE);
    
        mv.visitMaxs(-1, -1);


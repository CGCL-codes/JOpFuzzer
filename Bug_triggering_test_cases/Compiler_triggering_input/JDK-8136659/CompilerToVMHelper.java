/*
 * Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package jdk.internal.jvmci.hotspot;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import jdk.internal.jvmci.code.InstalledCode;
import jdk.internal.jvmci.code.InvalidInstalledCodeException;
import jdk.internal.jvmci.code.TargetDescription;
import jdk.internal.jvmci.hotspotvmconfig.HotSpotVMField;
import jdk.internal.jvmci.meta.JavaType;
import jdk.internal.jvmci.meta.ResolvedJavaMethod;
import jdk.internal.jvmci.meta.ResolvedJavaType;
import jdk.internal.jvmci.meta.SpeculationLog;
import sun.misc.Unsafe;

/*
 * A simple "proxy" class to get test access to CompilerToVM package-private methods
 */
public class CompilerToVMHelper {
    public static final CompilerToVM CTVM = new CompilerToVM();

    /**
     * Copies the original bytecode of {@code method} into a new byte array and returns it.
     *
     * @return a new byte array containing the original bytecode of {@code method}
     */
    public static byte[] getBytecode(HotSpotResolvedJavaMethodImpl method) {
        return CTVM.getBytecode(method);
    }

    /**
     * Gets the number of entries in {@code method}'s exception handler table or 0 if it has not
     * exception handler table.
     */
    public static int getExceptionTableLength(HotSpotResolvedJavaMethodImpl method) {
        return CTVM.getExceptionTableLength(method);
    }

    /**
     * Gets the address of the first entry in {@code method}'s exception handler table.
     *
     * Each entry is a native object described by these fields:
     *
     * <ul>
     * <li>{@link HotSpotVMConfig#exceptionTableElementSize}</li>
     * <li>{@link HotSpotVMConfig#exceptionTableElementStartPcOffset}</li>
     * <li>{@link HotSpotVMConfig#exceptionTableElementEndPcOffset}</li>
     * <li>{@link HotSpotVMConfig#exceptionTableElementHandlerPcOffset}</li>
     * <li>{@link HotSpotVMConfig#exceptionTableElementCatchTypeIndexOffset}
     * </ul>
     *
     * @return 0 if {@code method} has no exception handlers (i.e.
     *         {@code getExceptionTableLength(method) == 0})
     */
    public static long getExceptionTableStart(HotSpotResolvedJavaMethodImpl method) {
        return CTVM.getExceptionTableStart(method);
    }

    public static boolean hasBalancedMonitors(HotSpotResolvedJavaMethodImpl method) {
        return CTVM.hasBalancedMonitors(method);
    }

    /**
     * Determines if {@code method} can be inlined. A method may not be inlinable for a number of
     * reasons such as:
     * <ul>
     * <li>a CompileOracle directive may prevent inlining or compilation of methods</li>
     * <li>the method may have a bytecode breakpoint set</li>
     * <li>the method may have other bytecode features that require special handling by the VM</li>
     * </ul>
     */
    public static boolean canInlineMethod(HotSpotResolvedJavaMethodImpl method) {
        return CTVM.canInlineMethod(method);
    }

    /**
     * Determines if {@code method} should be inlined at any cost. This could be because:
     * <ul>
     * <li>a CompileOracle directive may forces inlining of this methods</li>
     * <li>an annotation forces inlining of this method</li>
     * </ul>
     */
    public static boolean shouldInlineMethod(HotSpotResolvedJavaMethodImpl method) {
        return CTVM.shouldInlineMethod(method);
    }

    /**
     * Used to implement {@link ResolvedJavaType#findUniqueConcreteMethod(ResolvedJavaMethod)}.
     *
     * @param method the method on which to base the search
     * @param actualHolderType the best known type of receiver
     * @return the method result or 0 is there is no unique concrete method for {@code method}
     */
    public static HotSpotResolvedJavaMethodImpl findUniqueConcreteMethod(
            HotSpotResolvedObjectTypeImpl actualHolderType,
            HotSpotResolvedJavaMethodImpl method) {
        return CTVM.findUniqueConcreteMethod(actualHolderType, method);
    }

    /**
     * Gets the implementor for the interface class {@code type}.
     *
     * @return the implementor if there is a single implementor, 0 if there is no implementor, or
     *         {@code type} itself if there is more than one implementor
     */
    public static HotSpotResolvedObjectTypeImpl getImplementor(HotSpotResolvedObjectTypeImpl type) {
        return CTVM.getImplementor(type);
    }

    /**
     * Determines if {@code method} is ignored by security stack walks.
     */
    public static boolean methodIsIgnoredBySecurityStackWalk(HotSpotResolvedJavaMethodImpl method) {
        return CTVM.methodIsIgnoredBySecurityStackWalk(method);
    }

    /**
     * Converts a name to a type.
     *
     * @param name a well formed Java type in {@linkplain JavaType#getName() internal} format
     * @param accessingClass the context of resolution (must not be null)
     * @param resolve force resolution to a {@link ResolvedJavaType}. If true, this method will
     *            either return a {@link ResolvedJavaType} or throw an exception
     * @return the type for {@code name} or 0 if resolution failed and {@code resolve == false}
     * @throws LinkageError if {@code resolve == true} and the resolution failed
     */
    public static HotSpotResolvedObjectTypeImpl lookupType(String name,
            Class<?> accessingClass, boolean resolve) {
        return CTVM.lookupType(name, accessingClass, resolve);
    }

    /**
     * Resolves the entry at index {@code cpi} in {@code constantPool} to an object.
     *
     * The behavior of this method is undefined if {@code cpi} does not denote an entry that can be
     * resolved to an object.
     */
    public static Object resolveConstantInPool(HotSpotConstantPool constantPool, int cpi) {
        return CTVM.resolveConstantInPool(constantPool, cpi);
    }

    /**
     * Resolves the entry at index {@code cpi} in {@code constantPool} to an object, looking in the
     * constant pool cache first.
     *
     * The behavior of this method is undefined if {@code cpi} does not denote an entry that can be
     * resolved to an object.
     */
    public static Object resolvePossiblyCachedConstantInPool(HotSpotConstantPool constantPool, int cpi) {
        return CTVM.resolvePossiblyCachedConstantInPool(constantPool, cpi);
    }

    /**
     * Gets the {@code JVM_CONSTANT_NameAndType} index from the entry at index {@code cpi} in
     * {@code constantPool}.
     *
     * The behavior of this method is undefined if {@code cpi} does not denote an entry containing a
     * {@code JVM_CONSTANT_NameAndType} index.
     */
    public static int lookupNameAndTypeRefIndexInPool(HotSpotConstantPool constantPool, int cpi) {
        return CTVM.lookupNameAndTypeRefIndexInPool(constantPool, cpi);
    }

    /**
     * Gets the name of the {@code JVM_CONSTANT_NameAndType} entry at index {@code cpi} in
     * {@code constantPool}.
     *
     * The behavior of this method is undefined if {@code cpi} does not denote a
     * {@code JVM_CONSTANT_NameAndType} entry.
     */
    public static String lookupNameRefInPool(HotSpotConstantPool constantPool, int cpi) {
        return CTVM.lookupNameRefInPool(constantPool, cpi);
    }

    /**
     * Gets the signature of the {@code JVM_CONSTANT_NameAndType} entry at index {@code cpi} in
     * {@code constantPool}.
     *
     * The behavior of this method is undefined if {@code cpi} does not denote a
     * {@code JVM_CONSTANT_NameAndType} entry.
     */
    public static String lookupSignatureRefInPool(HotSpotConstantPool constantPool, int cpi) {
        return CTVM.lookupSignatureRefInPool(constantPool, cpi);
    }

    /**
     * Gets the {@code JVM_CONSTANT_Class} index from the entry at index {@code cpi} in
     * {@code constantPool}.
     *
     * The behavior of this method is undefined if {@code cpi} does not denote an entry containing a
     * {@code JVM_CONSTANT_Class} index.
     */
    public static int lookupKlassRefIndexInPool(HotSpotConstantPool constantPool, int cpi) {
        return CTVM.lookupKlassRefIndexInPool(constantPool, cpi);
    }

    /**
     * Looks up a class denoted by the {@code JVM_CONSTANT_Class} entry at index {@code cpi} in
     * {@code constantPool}. This method does not perform any resolution.
     *
     * The behavior of this method is undefined if {@code cpi} does not denote a
     * {@code JVM_CONSTANT_Class} entry.
     *
     * @return the resolved class entry or a String otherwise
     */
    public static Object lookupKlassInPool(HotSpotConstantPool constantPool, int cpi) {
        return CTVM.lookupKlassInPool(constantPool, cpi);
    }

    /**
     * Looks up a method denoted by the entry at index {@code cpi} in {@code constantPool}. This
     * method does not perform any resolution.
     *
     * The behavior of this method is undefined if {@code cpi} does not denote an entry representing
     * a method.
     *
     * @param opcode the opcode of the instruction for which the lookup is being performed or
     *            {@code -1}. If non-negative, then resolution checks specific to the bytecode it
     *            denotes are performed if the method is already resolved. Should any of these
     *            checks fail, 0 is returned.
     * @return the resolved method entry, 0 otherwise
     */
    public static HotSpotResolvedJavaMethodImpl lookupMethodInPool(
            HotSpotConstantPool constantPool, int cpi, byte opcode) {
        return CTVM.lookupMethodInPool(constantPool, cpi, opcode);
    }

    /**
     * Ensures that the type referenced by the specified {@code JVM_CONSTANT_InvokeDynamic} entry at
     * index {@code cpi} in {@code constantPool} is loaded and initialized.
     *
     * The behavior of this method is undefined if {@code cpi} does not denote a
     * {@code JVM_CONSTANT_InvokeDynamic} entry.
     */
    public static void resolveInvokeDynamicInPool(HotSpotConstantPool constantPool, int cpi) {
        CTVM.resolveInvokeDynamicInPool(constantPool, cpi);
    }

    /**
     * Ensures that the type referenced by the entry for a <a
     * href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-2.html#jvms-2.9">signature
     * polymorphic</a> method at index {@code cpi} in {@code constantPool} is loaded and
     * initialized.
     *
     * The behavior of this method is undefined if {@code cpi} does not denote an entry representing
     * a signature polymorphic method.
     */
    public static void resolveInvokeHandleInPool(HotSpotConstantPool constantPool, int cpi) {
        CTVM.resolveInvokeHandleInPool(constantPool, cpi);
    }

    /**
     * Gets the resolved type denoted by the entry at index {@code cpi} in {@code constantPool}.
     *
     * The behavior of this method is undefined if {@code cpi} does not denote an entry representing
     * a class.
     *
     * @throws LinkageError if resolution failed
     */
    public static HotSpotResolvedObjectTypeImpl resolveTypeInPool(
            HotSpotConstantPool constantPool, int cpi) throws LinkageError {
        return CTVM.resolveTypeInPool(constantPool, cpi);
    }

    /**
     * Looks up and attempts to resolve the {@code JVM_CONSTANT_Field} entry at index {@code cpi} in
     * {@code constantPool}. The values returned in {@code info} are:
     *
     * <pre>
     *     [(int) flags,   // only valid if field is resolved
     *      (int) offset]  // only valid if field is resolved
     * </pre>
     *
     * The behavior of this method is undefined if {@code cpi} does not denote a
     * {@code JVM_CONSTANT_Field} entry.
     *
     * @param info an array in which the details of the field are returned
     * @return the type defining the field if resolution is successful, 0 otherwise
     */
    public static HotSpotResolvedObjectTypeImpl resolveFieldInPool(
            HotSpotConstantPool constantPool, int cpi, byte opcode, long[] info) {
        return CTVM.resolveFieldInPool(constantPool, cpi, opcode, info);
    }

    /**
     * Converts {@code cpci} from an index into the cache for {@code constantPool} to an index
     * directly into {@code constantPool}.
     *
     * The behavior of this method is undefined if {@code ccpi} is an invalid constant pool cache
     * index.
     */
    public static int constantPoolRemapInstructionOperandFromCache(
            HotSpotConstantPool constantPool, int cpci) {
        return CTVM.constantPoolRemapInstructionOperandFromCache(constantPool, cpci);
    }

    /**
     * Gets the appendix object (if any) associated with the entry at index {@code cpi} in
     * {@code constantPool}.
     */
    public static Object lookupAppendixInPool(HotSpotConstantPool constantPool, int cpi) {
        return CTVM.lookupAppendixInPool(constantPool, cpi);
    }

    /**
     * Installs the result of a compilation into the code cache.
     *
     * @param target the target where this code should be installed
     * @param compiledCode the result of a compilation
     * @param code the details of the installed CodeBlob are written to this object
     * @return the outcome of the installation which will be one of
     *         {@link HotSpotVMConfig#codeInstallResultOk},
     *         {@link HotSpotVMConfig#codeInstallResultCacheFull},
     *         {@link HotSpotVMConfig#codeInstallResultCodeTooLarge},
     *         {@link HotSpotVMConfig#codeInstallResultDependenciesFailed} or
     *         {@link HotSpotVMConfig#codeInstallResultDependenciesInvalid}.
     */
    public static int installCode(TargetDescription target,
            HotSpotCompiledCode compiledCode, InstalledCode code, SpeculationLog speculationLog) {
        return CTVM.installCode(target, compiledCode, code, speculationLog);
    }

    public static int getMetadata(TargetDescription target,
            HotSpotCompiledCode compiledCode, HotSpotMetaData metaData) {
        return CTVM.getMetadata(target, compiledCode, metaData);
    }

    /**
     * Notifies the VM of statistics for a completed compilation.
     *
     * @param id the identifier of the compilation
     * @param method the method compiled
     * @param osr specifies if the compilation was for on-stack-replacement
     * @param processedBytecodes the number of bytecodes processed during the compilation, including
     *            the bytecodes of all inlined methods
     * @param time the amount time spent compiling {@code method}
     * @param timeUnitsPerSecond the granularity of the units for the {@code time} value
     * @param installedCode the nmethod installed as a result of the compilation
     */
    public static void notifyCompilationStatistics(int id,
            HotSpotResolvedJavaMethodImpl method, boolean osr,
            int processedBytecodes, long time, long timeUnitsPerSecond,
            InstalledCode installedCode) {
        CTVM.notifyCompilationStatistics(id, method, osr, processedBytecodes,
                time, timeUnitsPerSecond, installedCode);
    }

    /**
     * Resets all compilation statistics.
     */
    public static void resetCompilationStatistics() {
        CTVM.resetCompilationStatistics();
    }

    /**
     * Initializes the fields of {@code config}.
     */
    public static long initializeConfiguration() {
        return CTVM.initializeConfiguration();
    }

    /**
     * Resolves the implementation of {@code method} for virtual dispatches on objects of dynamic
     * type {@code exactReceiver}. This resolution process only searches "up" the class hierarchy of
     * {@code exactReceiver}.
     *
     * @param caller the caller or context type used to perform access checks
     * @return the link-time resolved method (might be abstract) or {@code 0} if it can not be
     *         linked
     */
    public static HotSpotResolvedJavaMethodImpl resolveMethod(
            HotSpotResolvedObjectTypeImpl exactReceiver,
            HotSpotResolvedJavaMethodImpl method,
            HotSpotResolvedObjectTypeImpl caller) {
        return CTVM.resolveMethod(exactReceiver, method, caller);
    }

    /**
     * Gets the static initializer of {@code type}.
     *
     * @return 0 if {@code type} has no static initializer
     */
    public static HotSpotResolvedJavaMethodImpl getClassInitializer(
            HotSpotResolvedObjectTypeImpl type) {
        return CTVM.getClassInitializer(type);
    }

    /**
     * Determines if {@code type} or any of its currently loaded subclasses overrides
     * {@code Object.finalize()}.
     */
    public static boolean hasFinalizableSubclass(HotSpotResolvedObjectTypeImpl type) {
        return CTVM.hasFinalizableSubclass(type);
    }

    /**
     * Gets the method corresponding to {@code holder} and slot number {@code slot} (i.e.
     * {@link Method#slot} or {@link Constructor#slot}).
     */
    public static HotSpotResolvedJavaMethodImpl getResolvedJavaMethodAtSlot(Class<?> holder,
            int slot) {
        return CTVM.getResolvedJavaMethodAtSlot(holder, slot);
    }

    /**
     * Gets the maximum absolute offset of a PC relative call to {@code address} from any position
     * in the code cache.
     *
     * @param address an address that may be called from any code in the code cache
     * @return -1 if {@code address == 0}
     */
    public static long getMaxCallTargetOffset(long address) {
        return CTVM.getMaxCallTargetOffset(address);
    }

    /**
     * Gets a textual disassembly of {@code codeBlob}.
     *
     * @return a non-zero length string containing a disassembly of {@code codeBlob} or null if
     *         {@code codeBlob} could not be disassembled for some reason
     */
    public static String disassembleCodeBlob(long codeBlob) {
        return CTVM.disassembleCodeBlob(codeBlob);
    }

    /**
     * Gets a stack trace element for {@code method} at bytecode index {@code bci}.
     */
    public static StackTraceElement getStackTraceElement(
            HotSpotResolvedJavaMethodImpl method, int bci) {
        return CTVM.getStackTraceElement(method, bci);
    }

    /**
     * Executes some {@code installedCode} with arguments {@code args}.
     *
     * @return the result of executing {@code installedCode}
     * @throws InvalidInstalledCodeException if {@code installedCode} has been invalidated
     */
    public static Object executeInstalledCode(Object[] args,
            InstalledCode installedCode) throws InvalidInstalledCodeException {
        return CTVM.executeInstalledCode(args, installedCode);
    }

    /**
     * Gets the line number table for {@code method}. The line number table is encoded as (bci,
     * source line number) pairs.
     *
     * @return the line number table for {@code method} or null if it doesn't have one
     */
    public static long[] getLineNumberTable(HotSpotResolvedJavaMethodImpl method) {
        return CTVM.getLineNumberTable(method);
    }

    /**
     * Gets the number of entries in the local variable table for {@code method}.
     *
     * @return the number of entries in the local variable table for {@code method}
     */
    public static int getLocalVariableTableLength(HotSpotResolvedJavaMethodImpl method) {
        return CTVM.getLocalVariableTableLength(method);
    }

    /**
     * Gets the address of the first entry in the local variable table for {@code method}.
     *
     * Each entry is a native object described by these fields:
     *
     * <ul>
     * <li>{@link HotSpotVMConfig#localVariableTableElementSize}</li>
     * <li>{@link HotSpotVMConfig#localVariableTableElementLengthOffset}</li>
     * <li>{@link HotSpotVMConfig#localVariableTableElementNameCpIndexOffset}</li>
     * <li>{@link HotSpotVMConfig#localVariableTableElementDescriptorCpIndexOffset}</li>
     * <li>{@link HotSpotVMConfig#localVariableTableElementSignatureCpIndexOffset}
     * <li>{@link HotSpotVMConfig#localVariableTableElementSlotOffset}
     * <li>{@link HotSpotVMConfig#localVariableTableElementStartBciOffset}
     * </ul>
     *
     * @return 0 if {@code method} does not have a local variable table
     */
    public static long getLocalVariableTableStart(HotSpotResolvedJavaMethodImpl method) {
        return CTVM.getLocalVariableTableStart(method);
    }

    /**
     * Reads an object pointer within a VM data structure. That is, any {@link HotSpotVMField} whose
     * {@link HotSpotVMField#type() type} is {@code "oop"} (e.g.,
     * {@code ArrayKlass::_component_mirror}, {@code Klass::_java_mirror},
     * {@code JavaThread::_threadObj}).
     *
     * Note that {@link Unsafe#getObject(Object, long)} cannot be used for this since it does a
     * {@code narrowOop} read if the VM is using compressed oops whereas oops within VM data
     * structures are (currently) always uncompressed.
     *
     * @param address address of an oop field within a VM data structure
     */
    public static Object readUncompressedOop(long address) {
        return CTVM.readUncompressedOop(address);
    }

    /**
     * Determines if {@code method} should not be inlined or compiled.
     */
    public static void doNotInlineOrCompile(HotSpotResolvedJavaMethodImpl method) {
        CTVM.doNotInlineOrCompile(method);
    }

    /**
     * Invalidates the profiling information for {@code method} and (re)initializes it such that
     * profiling restarts upon its next invocation.
     */
    public static void reprofile(HotSpotResolvedJavaMethodImpl method) {
        CTVM.reprofile(method);
    }

    /**
     * Invalidates {@code installedCode} such that {@link InvalidInstalledCodeException} will be
     * raised the next time {@code installedCode} is executed.
     */
    public static void invalidateInstalledCode(InstalledCode installedCode) {
        CTVM.invalidateInstalledCode(installedCode);
    }

    /**
     * Collects the current values of all JVMCI benchmark counters, summed up over all threads.
     */
    public static long[] collectCounters() {
        return CTVM.collectCounters();
    }

    /**
     * Determines if {@code metaspaceMethodData} is mature.
     */
    public static boolean isMature(long metaspaceMethodData) {
        return CTVM.isMature(metaspaceMethodData);
    }

    /**
     * Generate a unique id to identify the result of the compile.
     */
    public static int allocateCompileId(HotSpotResolvedJavaMethodImpl method,
            int entryBCI) {
        return CTVM.allocateCompileId(method, entryBCI);
    }

    /**
     * Determines if {@code method} has OSR compiled code identified by {@code entryBCI} for
     * compilation level {@code level}.
     */
    public static boolean hasCompiledCodeForOSR(
            HotSpotResolvedJavaMethodImpl method, int entryBCI, int level) {
        return CTVM.hasCompiledCodeForOSR(method, entryBCI, level);
    }

    /**
     * Gets the value of {@code metaspaceSymbol} as a String.
     */
    public static String getSymbol(long metaspaceSymbol) {
        return CTVM.getSymbol(metaspaceSymbol);
    }

    /**
     * Looks for the next Java stack frame matching an entry in {@code methods}.
     *
     * @param frame the starting point of the search, where {@code null} refers to the topmost frame
     * @param methods the methods to look for, where {@code null} means that any frame is returned
     * @return the frame, or {@code null} if the end of the stack was reached during the search
     */
    public static HotSpotStackFrameReference getNextStackFrame(
            HotSpotStackFrameReference frame,
            HotSpotResolvedJavaMethodImpl[] methods, int initialSkip) {
        return CTVM.getNextStackFrame(frame, methods, initialSkip);
    }

    /**
     * Materializes all virtual objects within {@code stackFrame} updates its locals.
     *
     * @param invalidate if {@code true}, the compiled method for the stack frame will be
     *            invalidated.
     */
    public static void materializeVirtualObjects(
            HotSpotStackFrameReference stackFrame, boolean invalidate) {
        CTVM.materializeVirtualObjects(stackFrame, invalidate);
    }

    /**
     * Gets the v-table index for interface method {@code method} in the receiver {@code type} or
     * {@link HotSpotVMConfig#invalidVtableIndex} if {@code method} is not in {@code type}'s
     * v-table.
     */
    public static int getVtableIndexForInterface(HotSpotResolvedObjectTypeImpl type,
            HotSpotResolvedJavaMethodImpl method) {
        return CTVM.getVtableIndexForInterface(type, method);
    }

    /**
     * Determines if debug info should also be emitted at non-safepoint locations.
     */
    public static boolean shouldDebugNonSafepoints() {
        return CTVM.shouldDebugNonSafepoints();
    }

    /**
     * Writes {@code length} bytes from {@code buf} starting at offset {@code offset} to the
     * HotSpot's log stream. No range checking is performed.
     */
    public static void writeDebugOutput(byte[] bytes, int offset, int length) {
        CTVM.writeDebugOutput(bytes, offset, length);
    }

    /**
     * Flush HotSpot's log stream.
     */
    public static void flushDebugOutput() {
        CTVM.flushDebugOutput();
    }

    /**
     * Read a value representing a metaspace Method* and return the
     * {@link HotSpotResolvedJavaMethodImpl} wrapping it. This method does no checking that the
     * location actually contains a valid Method*. If the {@code base} object is a
     * {@link MetaspaceWrapperObject} then the metaspace pointer is fetched from that object and
     * used as the base. Otherwise the object itself is used as the base.
     *
     * @param base an object to read from or null
     * @param displacement
     * @return null or the resolved method for this location
     */
    public static HotSpotResolvedJavaMethodImpl getResolvedJavaMethod(Object base,
            long displacement) {
        return CTVM.getResolvedJavaMethod(base, displacement);
    }

    /**
     * Read a value representing a metaspace ConstantPool* and return the
     * {@link HotSpotConstantPool} wrapping it. This method does no checking that the location
     * actually contains a valid ConstantPool*. If the {@code base} object is a
     * {@link MetaspaceWrapperObject} then the metaspace pointer is fetched from that object and
     * used as the base. Otherwise the object itself is used as the base.
     *
     * @param base an object to read from or null
     * @param displacement
     * @return null or the resolved method for this location
     */
    public static HotSpotConstantPool getConstantPool(Object base, long displacement) {
        return CTVM.getConstantPool(base, displacement);
    }

    /**
     * Read a value representing a metaspace Klass* and return the
     * {@link HotSpotResolvedObjectTypeImpl} wrapping it. The method does no checking that the
     * location actually contains a valid Klass*. If the {@code base} object is a
     * {@link MetaspaceWrapperObject} then the metaspace pointer is fetched from that object and
     * used as the base. Otherwise the object itself is used as the base.
     *
     * @param base an object to read from or null
     * @param displacement
     * @param compressed true if the location contains a compressed Klass*
     * @return null or the resolved method for this location
     */
    public static HotSpotResolvedObjectTypeImpl getResolvedJavaType(Object base,
            long displacement, boolean compressed) {
        return CTVM.getResolvedJavaType(base, displacement, compressed);
    }
}

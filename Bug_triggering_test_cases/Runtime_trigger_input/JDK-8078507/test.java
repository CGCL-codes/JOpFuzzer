
This is not a complete test case but my invokedynamic bootstrap:

package org.robolectric.internal.bytecode;

import java.lang.invoke.CallSite;
import java.lang.invoke.ConstantCallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.SwitchPoint;
import org.robolectric.internal.ShadowConstants;

import static java.lang.invoke.MethodHandles.catchException;
import static java.lang.invoke.MethodHandles.dropArguments;
import static java.lang.invoke.MethodHandles.filterArguments;
import static java.lang.invoke.MethodHandles.lookup;
import static java.lang.invoke.MethodHandles.throwException;
import static java.lang.invoke.MethodType.methodType;
import static org.robolectric.internal.ShadowConstants.CONSTRUCTOR_METHOD_NAME;
import static org.robolectric.internal.bytecode.RoboCallSite.Kind.CONSTRUCTOR;
import static org.robolectric.internal.bytecode.RoboCallSite.Kind.REGULAR;
import static org.robolectric.internal.bytecode.RoboCallSite.Kind.STATIC;

@SuppressWarnings("UnusedDeclaration") public class InvokeDynamicSupport {
  private static final MethodHandles.Lookup lookup = lookup();
  private static final MethodHandle FALLBACK;
  private static final MethodHandle PROXY_TEST;
  private static final MethodHandle CLEAN_EXCEPTION;

  static {
    try {
      FALLBACK = lookup.findStatic(InvokeDynamicSupport.class, "fallback",
          methodType(Object.class, RoboCallSite.class, Object[].class));
      PROXY_TEST = lookup.findStatic(InvokeDynamicSupport.class, "isProxyCall",
          methodType(boolean.class, Object.class, Object.class));
      CLEAN_EXCEPTION = lookup.findStatic(RobolectricInternals.class, "cleanStackTrace",
          methodType(Throwable.class, Throwable.class));
    } catch (NoSuchMethodException | IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  public static CallSite bootstrapStatic(MethodHandles.Lookup caller, String name, MethodType type,
      String signature, MethodHandle original)
      throws NoSuchMethodException, IllegalAccessException {
    try {
      RoboCallSite site = new RoboCallSite(type, caller.lookupClass(), signature, original, STATIC);

      bindCallSite(site);

      return site;
    } catch (Throwable t) {
      t.printStackTrace();
      throw t;
    }
  }

  public static CallSite bootstrap(MethodHandles.Lookup caller, String name, MethodType type,
      String signature, MethodHandle original)
      throws NoSuchMethodException, IllegalAccessException {
    try {
      // Method calls looks like:
      // <this>, shadow, <arg>...
      RoboCallSite.Kind kind = name.equals(CONSTRUCTOR_METHOD_NAME) ? CONSTRUCTOR : REGULAR;
      RoboCallSite site = new RoboCallSite(type, caller.lookupClass(), signature, original, kind);

      bindCallSite(site);

      return site;
    } catch (Throwable t) {
      t.printStackTrace();
      throw t;
    }
  }

  private static Object fallback(RoboCallSite callSite, Object[] args) throws Throwable {
    MethodHandle mh = bindCallSite(callSite);
    return mh.invokeWithArguments(args);
  }

  private static MethodHandle bindCallSite(RoboCallSite site) throws IllegalAccessException {
    MethodHandle mh = findMethod(site);

    boolean isOriginal = mh == site.getOriginal();
    if (!isOriginal && site.isRegular()) {
      // Guard for invocations by proxy, that is that "shadow" is the object
      // we actually want to call the method on.
      //MethodHandle originalHandle = dropArguments(original, 0, Object.class);
      //mh = guardWithTest(PROXY_TEST, mh.asType(type), originalHandle.asType(type));
    }

    MethodType type = site.type();

    SwitchPoint switchPoint =
        RobolectricInternals.getShadowInvalidator().getSwitchPoint(site.getCaller());
    MethodHandle fallback =
        FALLBACK.asCollector(Object[].class, type.parameterCount()).bindTo(site);
    // TODO: Do we want to cast here?
    mh = switchPoint.guardWithTest(mh.asType(type), fallback.asType(type));

    mh = cleanStackTraces(mh);

    site.setTarget(mh.asType(type));

    return mh;
  }

  private static MethodType getActualType(RoboCallSite site) {
    if (site.isStatic()) {
      return site.type();
    } else {
      // Erase type of <this>
      return site.getOriginal().type().changeParameterType(0, Object.class);
    }
  }

  private static MethodHandle findMethod(RoboCallSite site) throws IllegalAccessException {
    MethodType type = getActualType(site);
    MethodHandle mh = RobolectricInternals.findShadowMethod(site.getSignature(), site.isStatic(),
        site.getCaller(), type);

    if (mh == null) {
      // Call original
      if (site.isStatic()) {
        return site.getOriginal();
      } else {
        // Drop shadow -> <this>, arg...
        return dropArguments(site.getOriginal(), 1, Object.class);
      }
    }

    // Robolectric doesn't actually look for static, this for example happens
    // in MessageQueue.nativeInit() which used to be void non-static in 4.2.
    if (!site.isStatic()) {
      // Drop <this> -> shadow, arg...
      return dropArguments(mh, 0, Object.class);
    } else {
      return mh;
    }
  }

  private static MethodHandle cleanStackTraces(MethodHandle mh) {
    MethodHandle handler = throwException(mh.type().returnType(), Throwable.class);
    handler = filterArguments(handler, 0, CLEAN_EXCEPTION);
    return catchException(mh, Throwable.class, handler);
  }

  private static boolean isProxyCall(Object caller, Object shadow) {
    return !caller.getClass().isInstance(shadow);
  }
}

  @Override public MethodHandle findShadowMethod(String signature, boolean isStatic,
      Class<?> theClass, MethodType type) throws IllegalAccessException {
    Plan plan = methodInvoked(signature, isStatic, theClass);

    if (plan == CALL_REAL_CODE_PLAN) return null;
    if (plan == DO_NOTHING_PLAN) {
      return dropArguments(DO_NOTHING, 0, type.parameterArray());
    } else {
      Method method = ((ShadowMethodPlan) plan).getShadowMethod();
      MethodHandle mh = MethodHandles.lookup().unreflect(method);

      // Robolectric doesn't actually look for static, this for example happens
      // in MessageQueue.nativeInit() which used to be void non-static in 4.2.
      if (!isStatic && Modifier.isStatic(method.getModifiers())) {
        return dropArguments(mh, 0, Object.class);
      } else {
        return mh;
      }
    }
  }

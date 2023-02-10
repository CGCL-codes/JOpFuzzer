
TEST_CASE("Test: Basic JVM Creation + Java Method Call", "[basic]") {
    JavaVM *jvm;
    JNIEnv *env;
    JavaVMInitArgs vm_args;
    JavaVMOption *options = new JavaVMOption[1];
    char *opt = "-Djava.class.path=/Users/iranhutchinson/Workspaces/ISC/Workspace/ClionProjects/JavaIntegration/java/target/test-classes";
    options[0].optionString = opt;
    vm_args.version = JNI_VERSION_1_6;
    vm_args.nOptions = 1;
    vm_args.options = options;
    
    vm_args.ignoreUnrecognized = false;

    jint response = JNI_CreateJavaVM(&jvm, (void **) &env, &vm_args);

    jclass cls = env->FindClass("com/intersystems/tbd/internal/TestRunner");
    jmethodID mid = env->GetStaticMethodID(cls, "main", "([Ljava/lang/String;)V");
    jobjectArray mainargs = env->NewObjectArray(2, env->FindClass("java/lang/String"), NULL);
    env->CallStaticVoidMethod(cls, mid, mainargs);

    delete options;

    jvm->DestroyJavaVM();
}

public class TestRunner {
    public TestRunner(){
        System.out.println("In Constructor of TestRunner");
    }
    public TestRunner(String args){
        if(args != null)
            System.out.println("Args passed in: " + args);
    }
    public Object testObjectMethod(){
        return new Object();
    }
    public Object[] testObjectArrayMethod(){
        return new Object[10];
    }
    public Integer testObjectSizeMethod(Object... args){
        return args.length;
    }
    public Collection<String> testArrayCollectionMethod(){
        Collection<String> coll = new ArrayList<>(10);
        coll.add("Value One");
        return coll;
    }
    public Map<String, Object> testHashMapMethod(){
        Map<String, Object> map = new HashMap<>(10);
        map.put("Key One", "Value One");
        return map;
    }
    public int testHashMapMethod(Map<String, Object> args){
        return args.size();
    }
    protected String testProtectedStringMethod(){
        return "Test Protected String Method";
    }
    private String testStringMethod(){
        return "Test String Method";
    }
    private Long testLongMethod(){
        return Long.valueOf(0);
    }
    private Integer tesIntMethod(Integer arg){
        if(arg == 10)
            return 0;
        else
            return 1;
    }
    public static String[] testStringMethod(String...args){
        for(String s: args){
            if(args != null){
                System.out.println("s->" + s);
            }
        }
        String[] value = {"Value 1", "Value 2"};
        return value;
    }

    public static void main(String args[]){
        System.out.println("Test Runner");
        if(args != null){
            for(int idx=0; idx<args.length; idx++){
                System.out.println("Argument "+ idx + "=" + args[idx]);
            }
        }
    }
}

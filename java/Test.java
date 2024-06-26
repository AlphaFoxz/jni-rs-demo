import example.ComplexObject;
import java.nio.file.Paths;

public class Test {
    private static String OS = System.getProperty("os.name").toLowerCase();
    static {
        String currentWorkingDir = Paths.get("").toAbsolutePath().toString();
        if (OS.contains("win")) {
            System.load(currentWorkingDir + "/../target/release/jni_rs_demo.dll");
        } else {
            System.load(currentWorkingDir + "/../target/release/jni_rs_demo.so");
        }
    }

    static native void update(ComplexObject complexObject);

    static native ComplexObject alloc();

    static native ComplexObject allocWithCache();

    public static void main(String[] args) {
        long t = System.currentTimeMillis();
        for (int i = 0; i < 10_0000; i++) {
            new ComplexObject();
        }
        System.out.println("native spent(ms): " + (System.currentTimeMillis() - t));
        System.gc();
        t = System.currentTimeMillis();
        for (int i = 0; i < 10_0000; i++) {
            // alloc();
            allocWithCache();
        }
        System.out.println("jni spent(ms): " + (System.currentTimeMillis() - t));
    }

}

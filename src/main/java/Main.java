import AmazonTest.runTest;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

public class Main {
    public static void main(String[] args) {
        JUnitCore.runClasses(runTest.class);
    }
}
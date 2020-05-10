import org.testng.annotations.Test;

public class TestFunctions {

    @Test
    public void printOS()
    {
        String os=System.getProperty("os.arch");
        String user =System.getProperty("user.name");
        System.out.println(os);
        System.out.println(user);
    }
}

public class MainTest {

    public static void main(String[] args) {
        try {
            Tests tests = new Tests();
            tests.signUp();

        } catch (Exception ex) {
            System.out.println( "Test Failed " + ex.getMessage());
        }
    }
}

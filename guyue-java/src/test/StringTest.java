


public class StringTest {

    public static void main(String[] args) {
        ThreadA a = new ThreadA();
        a.run();
    }

    static class ThreadA extends Thread {

        public ThreadA(String name) {
            super(name);
        }

        public void run() {
        }

    }
}

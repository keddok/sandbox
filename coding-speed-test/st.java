public class st {
    public static void main(String[] args) {
        for (int i=1;i<=100;i++) {
            System.out.println(i%3==0?(i%5==0?"MissKiss":"Miss"):i);
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
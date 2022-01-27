public class MainClass {
    /**
     * java多线程同步锁的使用
     * 示例：三个售票窗口同时出售10张票
     * */
    static int count = 0;
    public static void main(String[] args) throws InterruptedException {

        //实例化站台对象，并为每一个站台取名字
        /*Station station1=new Station("窗口1");
        Station station2=new Station("窗口2");
        Station station3=new Station("窗口3");

        // 让每一个站台对象各自开始工作
        station1.start();
        station2.start();
        station3.start();*/
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<=50;i++){
                    count = 2;
                    //System.out.println(count);
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<=50;i++){
                    count = 1;
                    //System.out.println(count);
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<=50;i++){
                    count = 3;
                    //System.out.println(count);
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<=50;i++){
                    count = 4;
                   // System.out.println(count);
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<=50;i++){
                    count = 5;
                    //System.out.println(count);
                }
            }
        }).start();
        System.out.println("=="+count);

    }

}
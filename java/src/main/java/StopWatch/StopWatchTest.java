package StopWatch;

import org.junit.Test;
import org.springframework.util.StopWatch;

/**
 * spring的任务计时器,只能在单线程下使用
 */
public class StopWatchTest {
    @Test
    public void Test1() throws InterruptedException {
        //任务计时器
        StopWatch stopWatch = new StopWatch();
        //计时开始，并设置任务1名称
        stopWatch.start("task1");
        Thread.sleep(2000);
        stopWatch.stop();
        Thread.sleep(1000);
        //计时开始，并设置任务2名称
        stopWatch.start("task2");
        Thread.sleep(1000);
        stopWatch.stop();
        //打印任务执行情况
        System.out.println(stopWatch.prettyPrint());
        //打印任务执行耗时
        System.out.println(stopWatch.getTotalTimeSeconds());
    }
}

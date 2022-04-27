package StopWatch;

import org.junit.Test;
import org.springframework.util.StopWatch;

/**
 * spring的任务计时器
 */
public class StopWatchTest {
    @Test
    public void Test1() throws InterruptedException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("demo");
        Thread.sleep(2000);
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        System.out.println(stopWatch.getTotalTimeMillis());
    }
}

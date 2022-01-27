package thread;

import java.util.Random;
import java.util.concurrent.Callable;

public class MyCallable implements Callable {
    @Override
    public Integer call() throws Exception {
        return new Random().nextInt();
    }
}

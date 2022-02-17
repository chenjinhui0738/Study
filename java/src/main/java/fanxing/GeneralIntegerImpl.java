package fanxing;

import java.util.Random;

/**
 * 实现类才是真正控制接口返回类型的
 */
public class GeneralIntegerImpl implements IGeneral<Integer> {
    @Override
    public Integer get() {
        return new Random().nextInt();
    }
}

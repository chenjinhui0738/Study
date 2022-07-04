package fanxing;

import java.util.UUID;

/**
 * 实现类才是真正控制接口返回类型的
 */
public class GeneralStringImpl implements IGeneral<String> {
    @Override
    public String get() {
        return UUID.randomUUID().toString();
    }
}

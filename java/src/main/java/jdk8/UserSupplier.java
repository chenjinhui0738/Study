package jdk8;

import java.util.Random;
import java.util.function.Supplier;

public class UserSupplier implements Supplier<User> {
    private int index = 10;
    private Random random = new Random();

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    @Override
    public User get() {
        return new User(index++, "pancm" + random.nextInt(10));
    }
}

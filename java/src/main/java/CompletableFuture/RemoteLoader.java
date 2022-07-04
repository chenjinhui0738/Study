package CompletableFuture;

public interface RemoteLoader {
    String load();

    default void delay() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

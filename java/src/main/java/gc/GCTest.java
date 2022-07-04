package gc;

import org.junit.jupiter.api.Test;

import java.lang.ref.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GCTest {
    /**
     * 1.强引用
     * 一般赋值操作都是强引用，设置为null就是为了“特意提醒”JVM这块资源可以进行垃圾回收了。
     */
    @Test
    public void Test1() {
        Student student = new Student();
        student = null;
        System.gc();
    }

    /**
     * 2.软引用
     * 软引用就是把对象用SoftReference包裹一下，当我们需要从软引用对象获得包裹的对象，只要get一下就可以了：
     * 当内存不足，会触发JVM的GC，如果GC后，内存还是不足，就会把软引用的包裹的对象给干掉，也就是只有在内存不足，JVM才会回收该对象。
     * 使用场景：比较适合用作缓存，当内存足够，可以正常的拿到缓存，当内存不够，就会先干掉缓存，不至于马上抛出OOM。
     */
    @Test
    public void Test2() {
        //实例
        SoftReference<Student> studentSoftReference = new SoftReference<Student>(new Student());
        Student student = studentSoftReference.get();
        System.out.println(student);

        //测试
        //我定义了一个软引用对象，里面包裹了byte[]，byte[]占用了10M，然后又创建了10Mbyte[]。
        //运行程序，需要带上一个参数：-Xmx20M 代表最大堆内存是20M。
        SoftReference<byte[]> softReference = new SoftReference<byte[]>(new byte[1024 * 1024 * 10]);
        System.out.println(softReference.get());
        System.gc();
        System.out.println(softReference.get());
        byte[] bytes = new byte[1024 * 1024 * 10];
        System.out.println(softReference.get());
        //运行结果：
        //[B@4cc8eb05
        //[B@4cc8eb05
        //null
        //可以很清楚的看到手动完成GC后，软引用对象包裹的byte[]还活的好好的，但是当我们创建了一个10M的byte[]后，
        // 最大堆内存不够了，所以把软引用对象包裹的byte[]给干掉了，如果不干掉，就会抛出OOM。
    }

    /**
     * 3.弱引用
     * 弱引用的特点是不管内存是否足够，只要发生GC，都会被回收
     */
    @Test
    public void Test3() {
        WeakReference<byte[]> weakReference = new WeakReference<byte[]>(new byte[1]);
        System.out.println(weakReference.get());
        System.gc();
        System.out.println(weakReference.get());
        //运行结果：
        //[B@12028586
        //null
        //可以很清楚的看到明明内存还很充足，但是触发了GC，资源还是被回收了。弱引用在很多地方都有用到，比如ThreadLocal、WeakHashMap。
    }

    /**
     * 4.虚引用
     * 无法通过虚引用来获取对一个对象的真实引用。
     * 虚引用必须与ReferenceQueue一起使用，当GC准备回收一个对象，如果发现它还有虚引用，就会在回收之前，把这个虚引用加入到与之关联的ReferenceQueue中。
     * 在NIO中，就运用了虚引用管理堆外内存。
     */
    @Test
    public void Test4() {
        //实例：
        ReferenceQueue queue1 = new ReferenceQueue();
        PhantomReference<byte[]> reference1 = new PhantomReference<byte[]>(new byte[1], queue1);
        System.out.println(reference1.get());
        //测试
        ReferenceQueue queue = new ReferenceQueue();
        List<byte[]> bytes = new ArrayList<>();
        PhantomReference<Student> reference = new PhantomReference<Student>(new Student(), queue);

        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                bytes.add(new byte[1024 * 1024]);
            }
        }).start();

        new Thread(() -> {
            while (true) {
                Reference poll = queue.poll();
                if (poll != null) {
                    System.out.println("虚引用被回收了：" + poll);
                }
            }
        }).start();

        Scanner scanner = new Scanner(System.in);
        scanner.hasNext();
        //解释：
        //第一个线程往集合里面塞数据，随着数据越来越多，肯定会发生GC。第二个线程死循环，从queue里面拿数据，如果拿出来的数据不是null，就打印出来。
        //从运行结果可以看到：当发生GC，虚引用就会被回收，并且会把回收的通知放到ReferenceQueue中。
    }
}

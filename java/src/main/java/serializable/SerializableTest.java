package serializable;

import java.io.*;

public class SerializableTest {
    public static void main(String[] args) throws Exception {
        //序列化数据到磁盘
        FileOutputStream fos = new FileOutputStream("worker.out");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        Worker testObject = new Worker();
        testObject.setName("alex");
        testObject.setSalary(10);
        oos.writeObject(testObject);
        oos.flush();
        oos.close();
        //反序列化磁盘数据并解析数据状态
        FileInputStream fis = new FileInputStream("worker.out");
        ObjectInputStream ois = new ObjectInputStream(fis);
        Worker deTest = (Worker) ois.readObject();
        System.out.println(deTest);

    }
}

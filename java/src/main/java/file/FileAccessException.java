package file;

/**
 * 自定义异常类
 */
public class FileAccessException extends Exception {
    public FileAccessException() {
        super("文件传输异常");
    }
}

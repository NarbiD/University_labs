import org.bytedeco.javacv.FrameGrabber;

public class Bootstrap {
    public static void main(String[] args) throws FrameGrabber.Exception {
        Grabber grabber = new Grabber();
        grabber.showFrame("test");
    }
}

import org.bytedeco.javacv.FrameGrabber;

import java.io.IOException;

public class Bootstrap {
    public static void main(String[] args) throws IOException {
        Grabber grabber = new Grabber();
        grabber.showFrame("test");
    }
}

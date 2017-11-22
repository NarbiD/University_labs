import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameGrabber;

public class Grabber {
    private final static int DEVICE_NUMBER = 0;
    FrameGrabber grabber;
    Frame frame;

    public Grabber() throws FrameGrabber.Exception {
        grabber = new OpenCVFrameGrabber(DEVICE_NUMBER);
        grabber.start();
        frame = grabber.grabFrame();
    }

    void showFrame(String windowName) throws FrameGrabber.Exception {
        CanvasFrame canvasFrame = new CanvasFrame(windowName);
        canvasFrame.setCanvasSize(frame.imageWidth, frame.imageHeight);
        while(canvasFrame.isVisible() && (frame = grabber.grab()) != null) {
            canvasFrame.showImage(frame);
        }
    }
}

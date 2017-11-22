import org.bytedeco.javacv.*;
import org.bytedeco.javacpp.*;
import org.bytedeco.javacpp.indexer.*;
import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;
import static org.bytedeco.javacpp.opencv_calib3d.*;
import static org.bytedeco.javacpp.opencv_objdetect.*;


import org.bytedeco.javacv.*;

import static org.bytedeco.javacpp.opencv_objdetect.*;

public class Grabber {
    private final static int DEVICE_NUMBER = 0;
    FrameGrabber grabber;
    Frame frame;

    OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
    IplImage image = null;

    CvHaarClassifierCascade classifier;

    public Grabber() throws FrameGrabber.Exception {
        grabber = new OpenCVFrameGrabber(DEVICE_NUMBER);
        grabber.start();
        frame = grabber.grabFrame();
        classifier = new CvHaarClassifierCascade(cvLoad(("C:\\Users\\ignas\\OneDrive\\Programming\\University_labs\\ComputerVision\\PatternRecognition\\src\\main\\resources\\haarcascade_frontalface_default.xml")));
    }

    void showFrame(String windowName) throws FrameGrabber.Exception {
        CanvasFrame canvasFrame = new CanvasFrame(windowName);
        canvasFrame.setCanvasSize(frame.imageWidth, frame.imageHeight);
        while(canvasFrame.isVisible() && (frame = grabber.grab()) != null) {
            image = converter.convert(frame);

            findObjects(image);

            canvasFrame.showImage(converter.convert(image));
        }
    }

    void findObjects(IplImage image) {
        CvMemStorage storage = CvMemStorage.create();
        CvSeq objects = cvHaarDetectObjects(image, classifier, storage, 1.1, 3, CV_HAAR_MAGIC_VAL);
        int amount = objects.total();
        for (int i = 0; i < amount; i++) {
            int x, y, width, height;
            CvRect rect = new CvRect(cvGetSeqElem(objects, i));

            rectangle(cvarrToMat(image),
                    new Rect(rect.x(), rect.y(), rect.width(), rect.height()),
                    new Scalar(0, 255, 0 , 0),
                    2 ,0 ,0);
        }
    }
}

import org.bytedeco.javacv.*;
import org.bytedeco.javacpp.*;
import org.bytedeco.javacpp.indexer.*;
import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_face.createLBPHFaceRecognizer;
import static org.bytedeco.javacpp.opencv_imgproc.*;
import static org.bytedeco.javacpp.opencv_calib3d.*;
import static org.bytedeco.javacpp.opencv_objdetect.*;
import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.*;


import org.bytedeco.javacv.*;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.bytedeco.javacpp.opencv_objdetect.*;

public class Grabber {
    private final static int DEVICE_NUMBER = 0;
    FrameGrabber grabber;
    Frame frame;
    opencv_face.FaceRecognizer faceRecognizer;
    Map<Integer, String> associations = new HashMap<>();

    int count = 0;

    OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
    IplImage image = null;

    CvHaarClassifierCascade classifier;

    public Grabber() throws FrameGrabber.Exception {
        grabber = new OpenCVFrameGrabber(0);
//        grabber = new FFmpegFrameGrabber("D:\\Downloads\\b.mp4");
        faceRecognizer = createLBPHFaceRecognizer();
        grabber.setAudioStream(0);
        grabber.setFormat("mp4");
        grabber.setFrameNumber(400);
        grabber.start();
        frame = grabber.grabFrame();
        classifier = new CvHaarClassifierCascade(cvLoad(("C:\\Users\\ignas\\OneDrive\\Programming\\University_labs\\ComputerVision\\PatternRecognition\\src\\main\\resources\\haarcascade_frontalface_default.xml")));
    }

    void showFrame(String windowName) throws FrameGrabber.Exception {
        CanvasFrame canvasFrame = new CanvasFrame(windowName);
        canvasFrame.setCanvasSize(frame.imageWidth, frame.imageHeight);
        train();
        while(canvasFrame.isVisible() && (frame = grabber.grab()) != null) {
            image = converter.convert(frame);

            findObjects(image);

            canvasFrame.showImage(converter.convert(image));
        }
    }

    public void train() {
        File imagesDir = new File("C:\\Users\\ignas\\OneDrive\\Programming\\University_labs\\ComputerVision\\PatternRecognition\\src\\main\\resources\\img\\");
        FilenameFilter imgFilter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                name = name.toLowerCase();
                return name.endsWith(".jpg");
            }
        };

        File[] imageFiles = imagesDir.listFiles(imgFilter);
        MatVector images = new MatVector(imageFiles.length);

        Mat labels = new Mat(imageFiles.length, 1, CV_32SC1);
        IntBuffer labelsBuf = labels.createBuffer();
        int counter = 0;

        for (File image : imageFiles) {
            Mat baseImage = imread(image.getAbsolutePath(), CV_LOAD_IMAGE_GRAYSCALE);
            int label = Integer.parseInt(image.getName().split("\\.")[0]);
            associations.put(label, image.getName());
            images.put(counter, baseImage);
            labelsBuf.put(counter, label);
            counter++;
        }

        faceRecognizer.train(images, labels);
        faceRecognizer.save("C:\\Users\\ignas\\OneDrive\\Programming\\University_labs\\ComputerVision\\PatternRecognition\\src\\main\\resources\\result_train.xml");
    }

    public IplImage getSubImageFromIpl(IplImage img, int x, int y, int w, int h) {
        IplImage resizeImage = IplImage.create(w, h, img.depth(), img.nChannels());
        cvSetImageROI(img, cvRect(x, y, w, h));
        if (img.depth() == resizeImage.depth()) {
            cvCopy(img, resizeImage);
        }
        cvResetImageROI(img);
        return resizeImage;
    }

    public IplImage resizeImage(IplImage img,  int w, int h) {
        IplImage resizeImage = IplImage.create(w, h, img.depth(), img.nChannels());
        cvResize(img, resizeImage);
        return resizeImage;
    }

    void findObjects(IplImage image) {
        CvMemStorage storage = CvMemStorage.create();
        CvSeq objects = cvHaarDetectObjects(image, classifier, storage, 1.5, 5, CV_HAAR_DO_CANNY_PRUNING);
        int amount = objects.total();
        for (int i = 0; i < amount; i++) {
            CvRect rect = new CvRect(cvGetSeqElem(objects, i));
            if (count++ % 50 == 0) {
                IplImage obj = getSubImageFromIpl(image, rect.x(), rect.y(), rect.width(), rect.height());
                obj = resizeImage(obj, 100, 100);

                IplImage grayImage = IplImage.create(obj.width(),obj.height(),  IPL_DEPTH_8U, 1);
                cvCvtColor(obj, grayImage, CV_BGR2GRAY);
                int label = faceRecognizer.predict_label(cvarrToMat(grayImage));
                System.out.println(label);
                IplImage img = cvLoadImage("C:\\Users\\ignas\\OneDrive\\Programming\\University_labs\\ComputerVision\\PatternRecognition\\src\\main\\resources\\img\\" + associations.get(label), CV_LOAD_IMAGE_GRAYSCALE);


//                IplImage grayImage = IplImage.create(obj.width(),obj.height(),  IPL_DEPTH_8U, 1);
//                cvCvtColor(obj, grayImage, CV_BGR2GRAY);
//                cvSaveImage("C:\\Users\\ignas\\OneDrive\\Programming\\University_labs\\ComputerVision\\PatternRecognition\\src\\main\\resources\\img\\" + count/50 + ".jpg", grayImage);
            }
            rectangle(cvarrToMat(image),
                    new Rect(rect.x(), rect.y(), rect.width(), rect.height()),
                    new Scalar(0, 255, 0 , 0),
                    2 ,0 ,0);
        }
    }
}

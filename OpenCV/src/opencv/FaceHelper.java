/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opencv;

import com.googlecode.javacv.cpp.opencv_contrib.FaceRecognizer;
import static com.googlecode.javacv.cpp.opencv_contrib.createLBPHFaceRecognizer;
import com.googlecode.javacv.cpp.opencv_core;
import static com.googlecode.javacv.cpp.opencv_core.CV_AA;
import static com.googlecode.javacv.cpp.opencv_core.IPL_DEPTH_8U;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import com.googlecode.javacv.cpp.opencv_core.MatVector;
import static com.googlecode.javacv.cpp.opencv_core.cvClearMemStorage;
import static com.googlecode.javacv.cpp.opencv_core.cvCopy;
import static com.googlecode.javacv.cpp.opencv_core.cvCreateImage;
import static com.googlecode.javacv.cpp.opencv_core.cvGetSeqElem;
import static com.googlecode.javacv.cpp.opencv_core.cvGetSize;
import static com.googlecode.javacv.cpp.opencv_core.cvLoad;
import static com.googlecode.javacv.cpp.opencv_core.cvPoint;
import static com.googlecode.javacv.cpp.opencv_core.cvRectangle;
import static com.googlecode.javacv.cpp.opencv_core.cvSetImageROI;
import static com.googlecode.javacv.cpp.opencv_highgui.cvLoadImage;
import static com.googlecode.javacv.cpp.opencv_highgui.cvSaveImage;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_BGR2GRAY;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_INTER_LINEAR;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvCvtColor;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvEqualizeHist;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvResize;
import com.googlecode.javacv.cpp.opencv_objdetect;
import static com.googlecode.javacv.cpp.opencv_objdetect.CV_HAAR_DO_CANNY_PRUNING;
import static com.googlecode.javacv.cpp.opencv_objdetect.cvHaarDetectObjects;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import static opencv.FaceHelper.CropFace;

/**
 *
 * @author Zhangwei
 */
public class FaceHelper {

    public static String faceRecognize(String trainingDir, String imgDir) {
        IplImage testImg = cvLoadImage(imgDir);
        IplImage grayTestImg = IplImage.create(testImg.width(), testImg.height(), IPL_DEPTH_8U, 1);
        FilenameFilter imgFilter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                name = name.toLowerCase();
                return name.endsWith(".jpg") || name.endsWith(".pgm") || name.endsWith(".png");
            }
        };
        File root = new File(trainingDir);
        File[] imgFiles = root.listFiles(imgFilter);
        MatVector images = new MatVector(imgFiles.length);
        ArrayList<StuId> stu = new ArrayList<>();
        //int[] labels = new int[imgFiles.length];
        int counter = 0;
        for (File image : imgFiles) {
            IplImage img = cvLoadImage(image.getAbsolutePath());
            System.out.println(image.getName());
            //CropFace("Origin/"+image.getName());
            StuId stuId = new StuId();
            stuId.setAndrewId(image.getName().split("\\.")[0]);
            stuId.setId(counter);
            //int label = Integer.parseInt(image.getName().split("\\-")[0]);
            IplImage grayImg = IplImage.create(img.width(), img.height(), IPL_DEPTH_8U, 1);
            cvCvtColor(img, grayImg, CV_BGR2GRAY);
            images.put(counter, grayImg);
            //labels[counter]= label;
            counter++;
        }
        int[] labels = new int[stu.size()];
        for (int i = 0; i < stu.size(); i++) {
            labels[i] = stu.get(i).getId();
            System.out.println(labels[i]);
        }
        FaceRecognizer faceRecognizer = createLBPHFaceRecognizer();
        faceRecognizer.train(images, labels);
        cvCvtColor(testImg, grayTestImg, CV_BGR2GRAY);
        int predictedLabel = faceRecognizer.predict(grayTestImg);
        for (int i = 0; i < stu.size(); i++) {
            if (predictedLabel == stu.get(i).getId());
            return stu.get(i).getAndrewId();
        }
        return null;
    }

    public static void CropFace(String oriImgName){
               String CASCADE_FILE ="src/haarcascade_frontalface_alt.xml";
                String OUT_FILE = "src/train/"+oriImgName;
                
                IplImage origImg = cvLoadImage(oriImgName, 1);
                
                // convert to grayscale
                IplImage grayImg = IplImage.create(origImg.width(),origImg.height(), IPL_DEPTH_8U, 1);
                cvCvtColor(origImg, grayImg, CV_BGR2GRAY);
                
                // scale the grayscale (to speed up face detection)
                IplImage smallImg = IplImage.create(grayImg.width(),grayImg.height(), IPL_DEPTH_8U, 1);
                cvResize(grayImg, smallImg, CV_INTER_LINEAR);

                // equalize the small grayscale
                IplImage equImg = IplImage.create(smallImg.width(),smallImg.height(), IPL_DEPTH_8U, 1);
                cvEqualizeHist(smallImg, equImg);

                // create temp storage, used during object detection
                opencv_core.CvMemStorage storage = opencv_core.CvMemStorage.create();

                // instantiate a classifier cascade for face detection

                opencv_objdetect.CvHaarClassifierCascade cascade =new opencv_objdetect.CvHaarClassifierCascade(cvLoad(CASCADE_FILE));
                System.out.println("Detecting faces...");

                opencv_core.CvSeq faces = cvHaarDetectObjects(equImg, cascade, storage,1.1, 3, CV_HAAR_DO_CANNY_PRUNING);

                cvClearMemStorage(storage);

                // draw thick yellow rectangles around all the faces
                int total = faces.total();
                System.out.println("Found " + total + " face(s)");
                //IplImage faceImg;

                for (int i = 0; i < total; i++) {

                        opencv_core.CvRect r = new opencv_core.CvRect(cvGetSeqElem(faces, i));
                        cvRectangle(origImg, cvPoint( r.x(), r.y() ),cvPoint( (r.x() + r.width()),(r.y() + r.height()) ),opencv_core.CvScalar.RED, 6, CV_AA, 0);
 
                        String strRect = String.format("CvRect(%d,%d,%d,%d)", r.x(), r.y(), r.width(), r.height());
                        
                        System.out.println(strRect);
                }
                
                if (total > 0) {
                        System.out.println("Saving marked-faces version of " + " in " + OUT_FILE);
                        opencv_core.CvRect r=new opencv_core.CvRect(cvGetSeqElem(faces,0));
                       // CvRect r_origin = new CvRect(r.x()*SCALE,r.y()*SCALE,r.width()*SCALE,r.height()*SCALE);
                        System.out.println("x:"+r.x()+"y:"+r.y()+"width:"+r.width()+"height:"+r.height());
                        cvSetImageROI(origImg,r);
                        IplImage cropped=cvCreateImage(cvGetSize(origImg),origImg.depth(),origImg.nChannels());
                        cvCopy(origImg,cropped);
                        cvSaveImage(OUT_FILE, cropped);
                }
    }

        public static void main(String[] args) {
                  FilenameFilter imgFilter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                name = name.toLowerCase();
                return name.endsWith(".jpg") || name.endsWith(".pgm") || name.endsWith(".png");
            }
        };
        //  FaceHelper fh
              File root = new File("src/Origin");
        File[] imgFiles = root.listFiles(imgFilter);
                    for (File image : imgFiles) {
            System.out.println("Origin/"+image.getName());
            CropFace("Origin/"+image.getName());
        }
        System.out.println("Face label: " + new FaceHelper().faceRecognize("src/train", "src/testim/gu.jpg"));
    }
}

class StuId {

    private int id;
    private String andrewId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAndrewId() {
        return andrewId;
    }

    public void setAndrewId(String andrewId) {
        this.andrewId = andrewId;
    }
}


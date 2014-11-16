/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package opencv;

import com.googlecode.javacv.cpp.opencv_contrib.FaceRecognizer;
import static com.googlecode.javacv.cpp.opencv_contrib.createLBPHFaceRecognizer;
import static com.googlecode.javacv.cpp.opencv_core.IPL_DEPTH_8U;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import com.googlecode.javacv.cpp.opencv_core.MatVector;
import static com.googlecode.javacv.cpp.opencv_highgui.cvLoadImage;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_BGR2GRAY;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvCvtColor;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;



public class NewClass {
   
        String trainingDir = "train";//
        IplImage testImage = cvLoadImage("testim/camera.jpg");//
        FilenameFilter jpgFilter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".jpg");
            }
        };
        File root = new File(trainingDir);
        File[] imageFiles = root.listFiles(jpgFilter);
        MatVector images = new MatVector(imageFiles.length);
        int[] labels = new int[imageFiles.length];
        int counter = 0;
        int label;
        ArrayList<StuId> stu = new ArrayList<>();
        IplImage img;
        IplImage grayImg;
        void face(){
        for (File image : imageFiles) {
            img = cvLoadImage(image.getAbsolutePath());
                        System.out.println(image.getName());
            StuId stu = new StuId();
             label=Integer.parseInt(image.getName().split("\\.")[0]);
             stu.setId(counter);
            grayImg = IplImage.create(img.width(), img.height(), IPL_DEPTH_8U, 1);
            cvCvtColor(img, grayImg, CV_BGR2GRAY);
            images.put(counter, grayImg);
           labels[counter] = label;
            counter++;
        }
 //       int[] labels = new int[stu.size()];
        }
            IplImage greyTestImage = IplImage.create(testImage.width(), testImage.height(), IPL_DEPTH_8U, 1);

     public static void main(String[] args) {
        //FaceRecognizer faceRecognizer = createFisherFaceRecognizer();
        //FaceRecognizer faceRecognizer = createEigenFaceRecognizer();
        FaceRecognizer faceRecognizer = createLBPHFaceRecognizer();
        NewClass one=new NewClass();
        one.face();
        faceRecognizer.train(one.images, one.labels );

        cvCvtColor(one.testImage, one.greyTestImage, CV_BGR2GRAY);

        int predictedLabel = faceRecognizer.predict(one.greyTestImage);

        System.out.println("Predicted label: " + predictedLabel);
        
    }
}

//class StuId{
//private int id;
//private String andrewId;
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getAndrewId() {
//        return andrewId;
//    }
//
//    public void setAndrewId(String andrewId) {
//        this.andrewId = andrewId;
//    }

//}
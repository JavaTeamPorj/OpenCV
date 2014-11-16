/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package opencv;

//import org.bytedeco.javacpp.opencv_core.IplImage;
//import org.bytedeco.javacv.CanvasFrame;
//import org.bytedeco.javacv.OpenCVFrameGrabber;

/**
 *
 * @author Zhangwei
 */
import com.googlecode.javacv.CanvasFrame;
import com.googlecode.javacv.OpenCVFrameGrabber;
import com.googlecode.javacv.cpp.opencv_core.IplImage;

public class CaptureImage {
    
    IplImage image;
    static CanvasFrame canvas = new CanvasFrame("Web Cam");
    
    public CaptureImage() {
        canvas.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
    }
    
    private static void captureFrame() {
        // 0-default camera, 1 - next...so on
        final OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
        try {
            grabber.start();
            IplImage img = grabber.grab();
            if (img != null) {
                //cvSaveImage(name, img);
                //cvSaveImage("Image",img);
                canvas.showImage(img);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws InterruptedException {
        while(true){
            captureFrame(); 
            Thread.sleep(100);
        }
    }     
}
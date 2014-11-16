/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package opencv;

/**
 *
 * @author Administrator
 */
//import java.awt.Graphics2D;  
//import java.awt.event.ActionEvent;  
//import java.awt.event.ActionListener;  
//import java.awt.event.MouseAdapter;  
//import java.awt.event.MouseEvent;  
//import java.awt.image.BufferedImage;  
//import java.io.File;  
//import java.io.IOException;  
//  
//import javax.imageio.ImageIO;  
//import javax.swing.Timer;  
//  
////import com.googlecode.javacv.CanvasFrame;  
////import com.googlecode.javacv.OpenCVFrameGrabber;  
////import com.googlecode.javacv.cpp.opencv_core.IplImage;  
////import static com.googlecode.javacv.cpp.opencv_core.cvReleaseImage;  
//  
//  
//public class CameraCapture {  
//    public static String savedImageFile = "c:\\tmp\\my.jpg";  
//      
//    //timer for image capture animation  
//    static class TimerAction implements ActionListener {  
//        private Graphics2D g;  
//        private CanvasFrame canvasFrame;  
//        private int width,height;  
//          
//        private int delta=10;  
//        private int count = 0;  
//          
//        private Timer timer;  
//        public void setTimer(Timer timer){  
//            this.timer = timer;  
//        }  
//           
//        public TimerAction(CanvasFrame canvasFrame){  
//            this.g = (Graphics2D)canvasFrame.getCanvas().getGraphics();   
//            this.canvasFrame = canvasFrame;  
//            this.width = canvasFrame.getCanvas().getWidth();  
//            this.height = canvasFrame.getCanvas().getHeight();  
//        }  
//        public void actionPerformed(ActionEvent e) {  
//            int offset = delta*count;  
//            if(width-offset>=offset && height-offset >= offset) {          
//                g.drawRect(offset, offset, width-2*offset, height-2*offset);  
//                canvasFrame.repaint();  
//                count++;  
//            }else{  
//                //when animation is done, reset count and stop timer.  
//                timer.stop();  
//                count = 0;  
//            }              
//        }  
//    }  
//  
//    public static void main(String[] args) throws Exception {  
//        //open camera source  
//        OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);  
//        grabber.start();  
//          
//        //create a frame for real-time image display  
//        CanvasFrame canvasFrame = new CanvasFrame("Camera");  
//        IplImage image = grabber.grab();  
//        int width = image.width();  
//        int height = image.height();  
//        canvasFrame.setCanvasSize(width, height);  
//          
//        //onscreen buffer for image capture  
//        final BufferedImage bImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);  
//        Graphics2D bGraphics = bImage.createGraphics();       
//          
//        //animation timer  
//        TimerAction timerAction = new TimerAction(canvasFrame);  
//        final Timer timer=new Timer(10, timerAction);  
//        timerAction.setTimer(timer);  
//           
//        //click the frame to capture an image  
//        canvasFrame.getCanvas().addMouseListener(new MouseAdapter(){  
//            public void mouseClicked(MouseEvent e){       
//                timer.start(); //start animation  
//                try {  
//                    ImageIO.write(bImage, "jpg", new File(savedImageFile));  
//                } catch (IOException e1) {  
//                    e1.printStackTrace();  
//                }                     
//            }                  
//        });  
//          
//        //real-time image display  
//        while(canvasFrame.isVisible() && (image=grabber.grab()) != null){  
//            if(!timer.isRunning()) { //when animation is on, pause real-time display  
//                canvasFrame.showImage(image);     
//                //draw the onscreen image simutaneously  
//                bGraphics.drawImage(image.getBufferedImage(),null,0,0);    
//            }  
//        }  
//          
//        //release resources  
//        cvReleaseImage(image);     
//        grabber.stop();  
//        canvasFrame.dispose();  
//    }  
//  
//}  

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.core.Core;


/**
 *
 * @author SOHAM GANDHI
 */
public class CamCap extends javax.swing.JFrame
{

    private DaemonThread myThread = null;
    int count = 0;
    VideoCapture webSource = null;

    Mat frame = new Mat();
    MatOfByte mem = new MatOfByte();

    String File_path="";

    class DaemonThread implements Runnable
    {
    protected volatile boolean runnable = false;

    @Override
    public  void run()
    {
        synchronized(this)
        {
            while(runnable)
            {
                if(webSource.grab())
                {
		    	try
                        {
                            webSource.retrieve(frame);
			    Highgui.imencode(".bmp", frame, mem);
			    Image im = ImageIO.read(new ByteArrayInputStream(mem.toArray()));

			    BufferedImage buff = (BufferedImage) im;
			    Graphics g=jPanel1.getGraphics();

			    if (g.drawImage(buff, 0, 0, getWidth(), getHeight() -150 , 0, 0, buff.getWidth(), buff.getHeight(), null))
			    
			    if(runnable == false)
                            {
			    	System.out.println("Going to wait()");
			    	this.wait();
			    }
			 }
			 catch(Exception ex)
                         {
			    System.out.println("Error");
                         }
                }
            }
        }
     }
   }


    /** Creates new form CamCap */
    public CamCap() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Capture");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Cambria", 0, 18));
        jButton1.setText("Start");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Cambria", 0, 18));
        jButton2.setText("Stop");
        jButton2.setEnabled(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setPreferredSize(new java.awt.Dimension(320, 240));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 323, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 236, Short.MAX_VALUE)
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setAlignmentX(0.1F);
        jPanel2.setAlignmentY(0.1F);

        jComboBox1.setFont(new java.awt.Font("Calibri", 0, 16));
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "From WebCam", "From File" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Calibri", 0, 18));
        jButton3.setText("...");
        jButton3.setEnabled(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Calibri", 0, 16));
        jLabel1.setText("Capture Method:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        if ( (jButton1.getText()).equals("Start") )
        {
            if (jComboBox1.getSelectedItem().equals("From WebCam"))
            {
                webSource =new VideoCapture(0);
            }
            else
                webSource =new VideoCapture(File_path);
            
            myThread = new DaemonThread();
            Thread t = new Thread(myThread);
            t.setDaemon(true);
            myThread.runnable = true;
            t.start();

            jButton1.setEnabled(false);
            jButton2.setEnabled(true);
            jComboBox1.setEnabled(false);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        if ( (jButton2.getText()).equals("Stop") )
        {
            myThread.runnable = false;
            jButton2.setEnabled(false);
            jButton1.setEnabled(true);
            jComboBox1.setEnabled(true);
            Highgui.imwrite("camera.jpg", frame);
    	    System.out.println("OK");
            webSource.release();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("AVI","avi");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION)
        {
            File_path=chooser.getSelectedFile().getPath();
            jButton1.setEnabled(true);
        }
        else
            File_path="";
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
        if(jComboBox1.getSelectedItem().equals("From File"))
        {
            jButton3.setEnabled(true);
            jButton1.setEnabled(false);
        }
        else
        {
            jButton3.setEnabled(false);
            jButton1.setEnabled(true);
        }
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
            

    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        if (myThread == null) {
        }
        else
             if (myThread.runnable)
             {
                myThread.runnable = false;
                webSource.release();
              }
    }//GEN-LAST:event_formWindowClosing

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    	//System.loadLibrary("OpenCV");
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                CamCap.setDefaultLookAndFeelDecorated(true);
                try
                {
                        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                }
                catch (Exception ex)
                {
                        System.out.println("Failed loading L&F: ");
                        System.out.println(ex);
                        System.out.println("Loading default Look & Feel Manager!");
                }

                new CamCap().setVisible(true);
            }
        });
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables

}

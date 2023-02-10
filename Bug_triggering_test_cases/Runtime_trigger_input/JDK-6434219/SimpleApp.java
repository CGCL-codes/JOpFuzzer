
/*
 *  you need jogl to run this test   http://jogl.dev.java.net
 */


import java.nio.*;
import java.util.*;
import javax.media.opengl.*;
import javax.media.opengl.glu.*;



/**
 * This class causes the EXCEPTION_ACCESS_VIOLATION error
 * @author JoeyS
 */
public class SimpleGLEventListener implements GLEventListener  {
    
    
    ByteBuffer point = null;
    public Random random = new Random();
    public int halfLength = 25;
    
    
    
    public static int NUMBER_OF_BYTES_IN_FLOAT = 4;
    public static int NUMBER_OF_DIMENSION = 3;
    
    public static int NUMBER_OF_POINTS = 10000;
    public static int NUMBER_OF_BYTES_IN_VERTEX = NUMBER_OF_BYTES_IN_FLOAT * NUMBER_OF_DIMENSION;
    public static int BUFFER_SIZE = NUMBER_OF_BYTES_IN_VERTEX * NUMBER_OF_POINTS;
    
    /**
     * Creates a new instance of SimpleGLEventListener
     */
    public SimpleGLEventListener() {
    }
    
    public void init(GLAutoDrawable gLAutoDrawable) {
        
        
        point = ByteBuffer.allocateDirect(BUFFER_SIZE);
        point.order(ByteOrder.nativeOrder());
        point.clear();
        
        //this vertex appears
        Set<String> pointSet = new HashSet<String>();
        StringBuilder builder = new StringBuilder();
        int count = 0;
        int max = (halfLength * halfLength * halfLength)* 2;
        int capacity = point.capacity();
        for(int i = 0; i < NUMBER_OF_POINTS && count < max;i++,count++){
            
            float x = random.nextInt(halfLength*2) - halfLength + random.nextFloat();
            float y = random.nextInt(halfLength*2) - halfLength + random.nextFloat();
            float z = random.nextInt(halfLength*2) - halfLength + random.nextFloat();
            
            builder.setLength(0);
            builder.append(x);
            builder.append("|");
            builder.append(y);
            builder.append("|");
            builder.append(z);
            
            String pointKey = builder.toString();
            if(!pointSet.contains(pointKey)){
                pointSet.add(pointKey);
                point.putFloat(x).putFloat(y).putFloat(z);
            } else {
                i--;
            }
        }
        
        
        GL gl = gLAutoDrawable.getGL();
        GLU glu = new GLU();
        glu.gluLookAt(0.0, 1.0, 6.0,
                0.0, 0.0, 0.0,
                0.0, 1.0, 0.0);
        gl.glClearColor(0.0f,0.0f,0.0f,0.0f);
        gl.glPointSize(1);
        gl.glEnable(GL.GL_DEPTH_TEST);
        
        
        gl.glEnableClientState(GL.GL_VERTEX_ARRAY);
        this.point.flip();
        gl.glVertexPointer(3,GL.GL_FLOAT,0,this.point);
        
    }
    
    private float m_angle = 0f;
    private float m_depth= -5f;
    private float m_incrementer = -0.2f;
    
    
    
    public void display(GLAutoDrawable gLAutoDrawable) {
        
        m_angle += 0.5f;
        m_depth += m_incrementer;
        if(random.nextInt(100) == 0){
            m_incrementer = m_incrementer * -1;
        }
        
        GL gl = gLAutoDrawable.getGL();
        GLU glu = new GLU();
        
        
        // clear the screen
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        
        // move back 5 units and rotate about all 3 axes
        gl.glTranslatef(0.0f, 0.0f, -50f);
//        gl.glRotatef(m_angle, 1.0f, 0.0f, 0.0f);
//        gl.glRotatef(m_angle, 0.0f, 1.0f, 0.0f);
//        gl.glRotatef(m_angle, 0.0f, 0.0f, 1.0f);
        
        
        
        gl.glDrawArrays(GL.GL_POINTS,0,this.point.limit());
        
        
        
        //gl.glBegin(GL.GL_POINTS);
        //gl.glVertex3f(-7.263897f,-0.289715f,8.930271f);
        //gl.glEnd();
        
    }
    
    public void reshape(GLAutoDrawable gLAutoDrawable, int x, int y, int width, int height) {
        GL gl = gLAutoDrawable.getGL();
        GLU glu = new GLU();
        
        
        
        gl.glViewport(0,0,width,height);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(90.0,(width/height),1.0,100.0);
        
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();

        
    }
    
    public void displayChanged(GLAutoDrawable gLAutoDrawable, boolean modeChanged, boolean deviceChanged) {
    }
}



/*********************************************************************/
/**  driver class has the main method
/*********************************************************************/

import com.sun.opengl.util.Animator;
import com.sun.opengl.util.FPSAnimator;
import java.awt.*;
import javax.swing.*;
import javax.media.opengl.*;


/**
 *
 * @author JoeyS
 */
public class SimpleApp extends JFrame {
    
    
    SimpleGLEventListener  trigGLEventListener = new SimpleGLEventListener();
    GLCanvas gLCanvas;
    
    static Animator animator = null;
    
    public static void main(String[] args){
        
        final SimpleApp app = new SimpleApp();
        
        SwingUtilities.invokeLater( new Runnable(){
            public void run(){
                app.setVisible(true);
            }
        });
        
        //start the animator
        SwingUtilities.invokeLater(
                new Runnable() {
            public void run() {
                animator.start();
            }
        }
        );
        
    }
    
    
    
    
    /**
     * Creates a new instance of SimpleApp
     */
    public SimpleApp() {
        //set the JFrame title
        super("Sine, Cosine and Tangent");
        
        //kill the process when the JFrame is closed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        GLCapabilities gLCapabilities = new GLCapabilities();
        gLCanvas = new GLCanvas(gLCapabilities);
        gLCanvas.addGLEventListener(trigGLEventListener);
        
        
        
        
        //add the GLCanvas just like we would any Component
        getContentPane().add(gLCanvas, BorderLayout.CENTER);
        setSize(500, 300);
        
        
        animator = new FPSAnimator(gLCanvas,30);
        
        gLCanvas.setAutoSwapBufferMode(true);
        
        //center the JFrame on the screen
        centerWindow(this);
        
    }
    
    
    public void centerWindow(Component frame) {
        Dimension screenSize =
                Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize  = frame.getSize();
        
        if (frameSize.width  > screenSize.width )
            frameSize.width  = screenSize.width;
        if (frameSize.height > screenSize.height)
            frameSize.height = screenSize.height;
        
        frame.setLocation(
                (screenSize.width  - frameSize.width ) >> 1,
                (screenSize.height - frameSize.height) >> 1
                );
    }
}





package org.yourorghere;

import com.sun.opengl.util.Animator;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Lab5 implements GLEventListener {
    private static double alpha = 0;
    private static Land land = null;
    private static Sky sky = null;
    private static Camera cam = null;
    private static Trees tree = null;
    private Lego model = null;
    private final boolean flag = true;
    public static void main(String[] args) {
        Frame frame = new Frame("5 Лабораторная. Нецветаев Антон Александрович ЭВМБ-18-1 ИИТиАД");
        GLCanvas canvas = new GLCanvas();
        canvas.addGLEventListener(new Lab5());
        frame.add(canvas);
        frame.setSize(1920, 1080);
        final Animator animator = new Animator(canvas);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new Thread(new Runnable() {
                    public void run() {
                        animator.stop();
                        System.exit(0);
                    }
                }).start();
            }
        });
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        animator.start();
    }
    
    public void defaultVal() {
        alpha = 180;
        cam.defaultVal();
        model.defaultVal();
    }

    public void init(GLAutoDrawable drawable) {
       // System.out.print(Lab5.class.getResource("resouces/sky.png").toString());
        
        float[] fogColors = {0.9f, 0.9f, 1, 0};
        GL gl = drawable.getGL();
        gl.setSwapInterval(1);
        gl.glShadeModel(GL.GL_SMOOTH);
        land = new Land(gl);
        sky = new Sky(gl);
        cam = new Camera();
        tree = new Trees(gl);
        model = new Lego(gl);
        defaultVal();
        gl.glClearColor(.9f, .9f, 1, 0);
        gl.glClearColor(fogColors[0], fogColors[1], fogColors[2], fogColors[3]);
        gl.glEnable(GL.GL_ALPHA_TEST);
        gl.glAlphaFunc(GL.GL_GREATER, 0);
        gl.glEnable(GL.GL_FOG);
        gl.glFogf(GL.GL_FOG_MODE, GL.GL_EXP);
        gl.glFogfv(GL.GL_FOG_COLOR, fogColors, 0);
        drawable.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent ke) {

            }

            public void keyPressed(KeyEvent ke) {
                switch (ke.getKeyCode()) {
                    case KeyEvent.VK_K: Camera.aView += 1; break;
                    case KeyEvent.VK_L: Camera.aView -= 1; break;
                    case KeyEvent.VK_Y: Camera.yCam += 0.5; break;
                    case KeyEvent.VK_U: Camera.yCam -= 0.5; break;
                    case KeyEvent.VK_ESCAPE:
                        model.defaultVal();
                        cam.defaultVal();
                        break;
                    case KeyEvent.VK_W: 
                        Lego.straight = true;
                        model.straight(true);
                        cam.straight(true);  
                        break;
                    case KeyEvent.VK_S:
                        Lego.straight = true;
                        model.straight(false);
                        cam.straight(false);
                        break;    
                    case KeyEvent.VK_A:
                        Lego.straight = true;
                        model.side(true);
                        cam.side(true);
                        break;
                    case KeyEvent.VK_D:
                        Lego.straight = true;
                        model.side(false);
                        cam.side(false);
                        break;  
                    case KeyEvent.VK_Q: 
                        alpha += 5;
                        Lego.yRotate += 5;
                        Trees.yRotate += 5;
                        break;
                    case KeyEvent.VK_E: 
                        alpha -= 5;
                        Lego.yRotate -= 5;
                        Trees.yRotate -= 5;
                        break;
                }
                Camera.xEye = Camera.xCam + Math.sin(alpha * Math.PI / 180);
                Camera.zEye = Camera.zCam + Math.cos(alpha * Math.PI / 180);
                Camera.xCam = Lego.x - 3 * -Math.sin(alpha * Math.PI / 180);
                Camera.zCam = Lego.z - 3 *  Math.cos(alpha * Math.PI / 180);
            }

            public void keyReleased(KeyEvent ke) {
            }
        });
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL gl = drawable.getGL();
        if (height <= 0) {
            height = 1;
        }
        gl.glViewport(0, 0, width, height);
    }
    
    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        GLU glu = new GLU();
        gl.glFogf(GL.GL_FOG_DENSITY, 0);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(Camera.aView, (double) -2 / 1, 6, 360);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
        glu.gluLookAt(Camera.xCam, Camera.yCam, Camera.zCam, Camera.xEye, Camera.yEye, Camera.zEye, 0, 20, 0);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glEnable(GL.GL_TEXTURE_2D);
        gl.glTexEnvf(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_MODULATE);
        sky.draw();
        tree.draw();
        land.draw();
        land.setLevels(Lego.x, Lego.z);
        sky.changeTranslated(Lego.x, 0, Lego.z);
        Lego.y = Land.yLevel;
        model.draw();
        System.out.println("xCamera: "+ Camera.xCam);
        System.out.println("yCamera: "+ Camera.yCam);
        System.out.println("zCamera: "+ Camera.zCam);
        System.out.println("aCamera: "+ Camera.aView);
        System.out.println("xCameraEye: "+ Camera.xEye);
        System.out.println("yCameraEye: "+ Camera.yEye);
        System.out.println("zCameraEye: "+ Camera.zEye);
        System.out.println("xModel: "+ Lego.x);
        System.out.println("yModel: "+ Lego.y);
        System.out.println("zModel: "+ Lego.z);
        gl.glFlush();
    }
    
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
}


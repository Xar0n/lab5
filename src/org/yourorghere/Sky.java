package org.yourorghere;

import com.sun.opengl.util.texture.Texture;
import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

public class Sky {
    private Texture cloud = null;
    private int size;
    private double yRotate;
    private double xTranslated, yTranslated, zTranslated;
    private GL gl;
    private int modelListSky, modelListSphere;
    private GLU glu = new GLU();
    private GLUquadric quadric = glu.gluNewQuadric();
    Sky(GL gl) {
        cloud = TextureLoader.load(getClass().getResource("./sky.png").getPath());
        size = 300;
        yRotate = 0;
        xTranslated = 0;
        yTranslated = 0;
        zTranslated = 0;
        this.gl = gl;     
    }

    public void changeRotate(double deltYRotate) {
        yRotate += deltYRotate;
    }

    public void changeTranslated(double x, double y, double z) {
        xTranslated = x;
        yTranslated = y;
        zTranslated = z;
    }
    private void box( double vX1, double vY1, double vX2, double vY2, double vX3, double vY3,
            double vX4, double vY4, double vXS1, double vYS1, double vZS1, double vXS2, 
            double vYS2, double vZS2, double vXS3, double vYS3, double vZS3, double vXS4, 
            double vYS4, double vZS4) {
        gl.glBegin(GL.GL_QUADS);
            gl.glTexCoord2d(vX1, vY1); gl.glColor3d(1, 1, 1); gl.glVertex3d(vXS1, vYS1, vZS1);
            gl.glTexCoord2d(vX2, vY2); gl.glColor3d(1, 1, 1); gl.glVertex3d(vXS2, vYS2, vZS2);
            gl.glTexCoord2d(vX3, vY3); gl.glColor3d(1, 1, 1); gl.glVertex3d(vXS3, vYS3, vZS3);
            gl.glTexCoord2d(vX4, vY4); gl.glColor3d(1, 1, 1); gl.glVertex3d(vXS4, vYS4, vZS4);
        gl.glEnd();
    }

    public void draw() {
        gl.glPushMatrix();
            gl.glRotated(yRotate, 0, 1, 0);
            gl.glTranslated(xTranslated, yTranslated, zTranslated);
            box(0.25, 0.34, 0.25, 0.67, 0, 0.67, 0, 0.34, -size, size, -size, -size,
                    -size, -size, size, -size, -size, size, size, -size);
            box(1, 0.34, 1, 0.67, 0.75, 0.67, 0.75, 0.34, size, size, -size, size,
                    -size, -size, size, -size, size, size, size, size);
            box(0.5, 0.34, 0.5, 0.67, 0.75, 0.67, 0.75, 0.34, -size, size, size, -size,
                    -size, size, size, -size, size, size, size, size);
            box(0.25, 0.34, 0.25, 0.67, 0.5, 0.67, 0.5, 0.34, -size, size, -size, -size,
                    -size, -size, -size, -size, size, -size, size, size);
            box(0.25, 0.34, 0.25, 0.1, 0.5, 0.1, 0.5, 0.34, -size, size, -size, 
                    size, size, -size, size, size, size, -size, size, size);
        gl.glPopMatrix();
        gl.glNewList(modelListSphere, GL.GL_COMPILE);
            gl.glPushMatrix();
                gl.glEnable(GL.GL_ALPHA_TEST); 
                gl.glAlphaFunc(GL.GL_GREATER, 0);
                gl.glColor3d(1, 1, 1);
                gl.glBindTexture(GL.GL_TEXTURE_2D, cloud.getTextureObject());
                glu.gluSphere(quadric, 200, 40, 40);
                gl.glRotated(90, 1, 0, 0);
            gl.glPopMatrix();
        gl.glEndList();
        gl.glCallList(modelListSphere);
        gl.glFlush();
    }
}

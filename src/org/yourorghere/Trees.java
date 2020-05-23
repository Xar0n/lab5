package org.yourorghere;

import com.sun.opengl.util.texture.Texture;
import java.util.Random;
import javax.media.opengl.GL;

public class Trees {
    private int RandomTreeIndex1 = (new Random()).nextInt(4);
    private int RandomTreeIndex2 = (new Random()).nextInt(4);
    private int RandomTreeIndex3 = (new Random()).nextInt(4);
    private int RandomTreeIndex4 = (new Random()).nextInt(4);
    private long testCount = 0;
    private GL gl;
    public class coord {
        private int x, z;
        private double y;
        coord() {
            x = 0;
            y = 0;
            z = 0;
        }
        public void setXYZ(int x1, double y1, int z1) {
            this.x = x1;
            this.z = z1;
            this.y = y1;
        }
    }
    private int kol = 100;
    public static double yRotate;
    private coord[] Mass = new coord[kol];
    private Texture TextureSet[] = {null, null, null, null};

    Trees(GL gl) {
        this.gl = gl;
        yRotate = 0;
        for (int i = 0; i < 4; i++) {
            TextureSet[i] = TextureLoader.load(getClass().getResource("./tree" + (i + 1) + ".png").getPath());
        }
        Random rand;
        for (int i = 0; i < kol; i++) {
            rand = new Random();
            int sign = rand.nextInt(2);
            if (sign % 2 == 0) sign = 1;
            else sign = -1;
            int x = sign * rand.nextInt(100);
            sign = rand.nextInt(2);
            if (sign % 2 == 0) sign = 1;
            else sign = -1;
            int z = sign * rand.nextInt(100);
            coord newel = new coord();
            newel.setXYZ(x, -Land.getHeight(Math.abs(x % Land.mapSize), Math.abs(z % Land.mapSize)) / 5, z);
            Mass[i] = newel;
        }
    }
    
    private void drawTree(int i, int randomIndex) {
        gl.glBindTexture(GL.GL_TEXTURE_2D, TextureSet[randomIndex].getTextureObject());
        gl.glPushMatrix();
        gl.glTranslated(Mass[i].x, Mass[i].y, Mass[i].z);
        gl.glRotated(yRotate, 0, 1, 0);
        gl.glBegin(GL.GL_POLYGON);
            gl.glTexCoord2d(1, 0); gl.glVertex3d(- 5, 10, 0);
            gl.glTexCoord2d(1, 1); gl.glVertex3d(- 5, 0, 0);
            gl.glTexCoord2d(0, 1); gl.glVertex3d(5, 0, 0);
            gl.glTexCoord2d(0, 0); gl.glVertex3d(5, 10, 0);
        gl.glEnd();
        gl.glPopMatrix(); 
    }

    public void draw() {
        for (int i = 0; i < kol; i++) {
            if(i < 20) drawTree(i, RandomTreeIndex1);
            if(i >= 20 && i < 30) drawTree(i, RandomTreeIndex2);
            if(i >= 30 && i < 40) drawTree(i, RandomTreeIndex3);
            if(i >= 40 && i < 50) drawTree(i, RandomTreeIndex4); 
        }
    }
}

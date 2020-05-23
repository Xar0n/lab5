package org.yourorghere;

import com.sun.opengl.util.texture.Texture;
import javax.media.opengl.GL;

public class Land {

    public static int mapSize = 1024;
    private static byte[] pHeightMap;
    private Texture grass = null;
    public static double xLevel, yLevel, zLevel;
    private static double yMax, yMin;
    private static boolean flag;
    private static int stepSize = 120;
    private int koef = 100;
    private GL gl;

    public Land(GL gl) {
        pHeightMap = TerrainLoader.load(getClass().getResource("./Terrain.data").getPath(), mapSize);
        grass = TextureLoader.load(getClass().getResource("./grass.png").getPath());
        xLevel = 0;
        zLevel = 0;
        yMax = -100;
        yMin = 0;
        flag = false;
        stepSize = 120;
        this.gl = gl;
    }

    public void setLevels(double x, double z) {
        xLevel = x;
        zLevel = z;
    }

    public static double getHeight(int x, int z) {
        int mapX = x % mapSize;
        int mapZ = z % mapSize;
        return (pHeightMap[mapX + (mapZ * mapSize)] & 0xFF);
    }

    private double max(double y1, double y2, double y3, double y4) {
        y1 = Math.max(y1, y2);
        y2 = Math.max(y3, y4);
        return Math.max(y1, y2);
    }

    private double min(double y1, double y2, double y3, double y4) {
        y1 = Math.min(y1, y2);
        y2 = Math.min(y3, y4);
        return Math.min(y1, y2);
    }
    
    private void vec(double x, double y, double z, 
            double vX, double vY, boolean normal, boolean vertex) {
        if(flag) gl.glColor3d(1 - Math.abs((yMax - y) / (yMax - yMin)), 
            1 - Math.abs((yMax - y) / (yMax - yMin)), 
            1 - Math.abs((yMax - y) / (yMax - yMin)));
        if(normal)  gl.glNormal3d(0, -1, 0);
        gl.glVertex3d(x, y, z); 
        if(vertex) gl.glTexCoord2d(vX, vY);
    }

    public void draw() {
        for (float i = - 1; i < 2; i++) {
            for (float j = -1; j < 2; j++) {
                for (int x = 0; x < mapSize; x += stepSize) {
                    for (int z = 0; z < mapSize; z += stepSize) {
                        double y1 = -getHeight(x, z) / 5;
                        double y2 = -getHeight(x + stepSize, z) / 5;
                        double y3 = -getHeight(x + stepSize, z + stepSize) / 5;
                        double y4 = -getHeight(x, z + stepSize) / 5;
                        double x1 = koef * x / mapSize - koef + koef * i;
                        double x2 = koef * (x + stepSize) / mapSize - koef + koef * i;
                        double z1 = koef * z / mapSize - koef + koef * j;
                        double z2 = koef * (z + stepSize) / mapSize - koef + koef * j;
                        if ((xLevel % mapSize >= x1) && (xLevel <= x2)
                                && (zLevel >= z1) && (zLevel <= z2)) {
                            yLevel = (y1 + y2 + y3 + y4) / 4;
                        }
                        if (max(y1, y2, y3, y4) > yMax) yMax = max(y1, y2, y3, y4);
                        if (min(y1, y2, y3, y4) < yMin) yMin = min(y1, y2, y3, y4);
                        gl.glBindTexture(GL.GL_TEXTURE_2D, grass.getTextureObject());
                        gl.glBegin(GL.GL_TRIANGLES);
                            gl.glNormal3d(0, -1, 0); gl.glTexCoord2d(0, 0);
                            vec(x1, y1, z1, 1, 0, false, true);
                            vec(x2, y2, z1, 1, 1, false, true);
                            vec(x2, y3, z2, 1, 1, true, true);
                            vec(x2, y3, z2, 0, 1, false, true);
                            vec(x1, y4, z2, 0, 0, false, true);
                            vec(x1, y1, z1, 0, 0, false, false);
                        gl.glEnd();
                    }
                }
                flag = true;
            }
        }
        gl.glDisable(GL.GL_TEXTURE_2D);
        gl.glFlush();
    }
}

package org.yourorghere;

import javax.media.opengl.GL;


public class Lego {
   private OBJ body = null;
   private OBJ rightArm = null;
   private OBJ leftArm = null;
   private OBJ rightFoot = null;
   private OBJ leftFoot = null;
   private GL gl = null;
   public static double x, y, z;
   public static double xRotate, yRotate, zRotate;
   public static boolean straight;
   private int bound = 60;
   private int speed = 2;
   private double differenceX, differenceZ;
   public Lego(GL gl) {
       this.gl = gl;
       x = -8;
       y = -20;
       z = 1;
       xRotate = 0;
       yRotate = 0;
       zRotate = 0;
       straight = false;
   }
   
   public void defaultVal() {
       x = -8;
       y = -20;
       z = 1;
       xRotate = 0;
       yRotate = 0;
       zRotate = 0;
       straight = false;
       differenceX = x - Camera.xCam;
       differenceZ = z - Camera.zCam;
   }
   
   public void side(boolean flag) {
       if(flag) x = x + speed; 
       else x = x - speed; 
       if (x > bound) {
            x = -bound - 44;
            Camera.xCam = x - differenceX;
            Camera.xEye = x - differenceX;
       }
       if (x < -bound - 44) {
            x = bound;
            Camera.xCam = x - differenceX;
            Camera.xEye = x - differenceX;
       }
   }
   
   public void straight(boolean flag) {
        if(flag) Lego.z += speed;
        else Lego.z -= speed;
        if (z > bound) {
            z = -bound - 44;
            Camera.zCam = z - differenceZ;
            Camera.zEye = z - differenceZ;
        } 
        if (z < -bound - 44) {
            z = bound;
            Camera.zCam = z - differenceZ;
            Camera.zEye = z - differenceZ;
        }
    }
   
   public void draw()
   {
       gl.glPushMatrix();
            y = -Land.getHeight((int)Math.abs(x % Land.mapSize), (int)Math.abs(z % Land.mapSize)) / 5;
            gl.glTranslated(x, y, z);
            gl.glRotated(180, 0, 1, 0);
            gl.glRotated(xRotate, 1, 0, 0);
            gl.glRotated(yRotate, 0, 1, 0);
            gl.glRotated(zRotate, 0, 0, 1);   
            this.body = new OBJ(getClass().getResource("./body.obj").getPath(), this.gl);
            this.body.drawModel(gl);  
            gl.glPushMatrix();
                if(straight) {
                    gl.glTranslated(0, 16.6, 37.64);
                    gl.glRotated(-60, 1, 0, 0);
                }    
                this.leftArm = new OBJ(getClass().getResource("./Left_arm.obj").getPath(), getClass().getResource("grass.png").getPath(), this.gl);  
                this.leftArm.drawModel(gl);
            gl.glPopMatrix();
            gl.glPushMatrix();
                if(straight) {
                    gl.glTranslated(0, 16.6, 37.64);
                    gl.glRotated(-60, 1, 0, 0);
                }    
                this.rightArm = new OBJ(getClass().getResource("./Right_arm.obj").getPath(), this.gl);
                this.rightArm.drawModel(gl);
            gl.glPopMatrix();
            gl.glPushMatrix();
                if(straight) {
                    gl.glTranslated(0, 4.96, 17.52);
                    gl.glRotated(-60, 1, 0, 0); 
                }    
                this.rightFoot = new OBJ(getClass().getResource("./Right_foot.obj").getPath(), this.gl);
                this.rightFoot.drawModel(gl);
            gl.glPopMatrix();
            gl.glPushMatrix();
                if(straight) {  
                    gl.glTranslated(0, 4.96, 17.52);
                    gl.glRotated(-60, 1, 0, 0); 
                    straight = false;
                }    
                this.leftFoot = new OBJ(getClass().getResource("./Left_foot.obj").getPath(), this.gl);
                this.leftFoot.drawModel(gl);
            gl.glPopMatrix();
       gl.glPopMatrix();
   }
}

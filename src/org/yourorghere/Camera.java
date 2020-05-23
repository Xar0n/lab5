package org.yourorghere;

public class Camera {
    public static double aView, xCam, yCam, zCam, xEye, yEye, zEye;
    private int speed = 2;
    public Camera() {
        xCam = 19.493;
        yCam = 55.8;
        zCam = -11.019;
        xEye = 19.5;
        yEye = 2;
        zEye = -12.019;
        aView = 101.5;
    }

    public void defaultVal() {
        xCam = Lego.x - 3 * -Math.sin(180 * Math.PI / 180);
        zCam = Lego.z - 3 * Math.cos(180 * Math.PI / 180);
        xEye = xCam + Math.sin(180 * Math.PI / 180);
        zEye = zCam + Math.cos(180 * Math.PI / 180);
//        xCam = -36.5;
        yCam = 50;
//        zCam = 6.98;
        //xEye = -36.5;
        yEye = -2;
        //zEye = 5.981;
        aView = 101.5;
        
    }

    public void changeZoom(boolean flag) {
        if (flag) {
            aView = aView - .5;
        } else {
            aView = aView + .5;
        }
    }
    
    public void side(boolean flag) {
       if(flag) {
           xEye += speed;
           xCam += speed;
       } else {
           xEye -= speed;
           xCam -= speed;
       }
   }
    
    public void straight(boolean flag) {
          if(flag) {
              zEye += speed;
              zCam += speed;
          } else {
              zEye -= speed;
              zCam -= speed;
          }
    }
}

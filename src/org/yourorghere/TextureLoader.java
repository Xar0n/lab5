package org.yourorghere;
import javax.media.opengl.*;
import com.sun.opengl.util.texture.*;
import java.io.*;

public class TextureLoader {
    public static Texture load(String fileName) {
        Texture text = null;
        try {
            text = TextureIO.newTexture(new File(fileName), false);
            text.setTexParameteri(GL.GL_TEXTURE_MAG_FILTER,
                    GL.GL_NEAREST);
            text.setTexParameteri(GL.GL_TEXTURE_MIN_FILTER,
                    GL.GL_NEAREST);
            text.setTexParameteri(GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
            text.setTexParameteri(GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(fileName);
        }
        return text;
    }
}

package org.yourorghere;

import com.sun.opengl.util.texture.Texture;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.media.opengl.GL;
import org.yourorghere.TextureLoader;

public class OBJ {
    private int modelList;
    private ArrayList faces = new ArrayList();
    private ArrayList normals = new ArrayList();
    private ArrayList vertices = new ArrayList(); 
    private ArrayList textureCoords = new ArrayList();
    private String texname;
    public OBJ(String filename, GL gl){
        this(filename,null,gl);
    }

    public OBJ(String filename, String texname, GL gl) {
        this.texname = texname;
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;
            float[] tmp;
            while ((line = br.readLine()) != null) {
                if (line.length() > 2) {
                    StringTokenizer tok = new StringTokenizer(line);
                    tok.nextToken();
                    switch (line.charAt(0)) {
                        case 'v':
                            switch (line.charAt(1)) {
                                case ' ':
                                    tmp = new float[]{Float.parseFloat(tok.nextToken()), Float.parseFloat(tok.nextToken()), Float.parseFloat(tok.nextToken())};
                                    vertices.add(tmp);
                                    break;
                                case 'n':
                                    tmp = new float[]{Float.parseFloat(tok.nextToken()), Float.parseFloat(tok.nextToken()), Float.parseFloat(tok.nextToken())};
                                    normals.add(tmp);
                                    break;
                                case 't':
                                    tmp = new float[]{Float.parseFloat(tok.nextToken()), 1 - Float.parseFloat(tok.nextToken())};
                                    textureCoords.add(tmp);
                                    break;
                            }
                            break;
                        case 'f':
                            ArrayList face = new ArrayList();
                            while(tok.hasMoreTokens()){
                                String[] idxstr = tok.nextToken().split("/");
                                int[] idx = new int[]{Integer.parseInt(idxstr[0])-1, Integer.parseInt(idxstr[1])-1, Integer.parseInt(idxstr[2])-1};
                                face.add(idx);
                            }
                            faces.add(face);
                            break;
                    }
                }
            }
            Texture texture = TextureLoader.load(texname);
            gl.glNewList(modelList, GL.GL_COMPILE);
            if(texture != null){
                gl.glBindTexture(GL.GL_TEXTURE_2D, texture.getTextureObject());
            }
            for (Object face : faces) {
                if(face == null) break;
                gl.glBegin(GL.GL_POLYGON);
                for (Object indexes : (ArrayList) face) {
                    if(indexes == null) break;
                    if (normals.size() > 0) {
                        float[] normal = (float[]) normals.get(((int[]) indexes)[2]);
                        gl.glNormal3f(normal[0], normal[1], normal[2]);
                    }
                    if (textureCoords.size() > 0) {
                        float[] texCoord = (float[]) textureCoords.get(((int[]) indexes)[1]);
                        gl.glTexCoord2f(texCoord[0], texCoord[1]);
                    }
                    if (vertices.size() > 0){
                        float[] vertice = (float[]) vertices.get(((int[]) indexes)[0]);
                        gl.glVertex3f(vertice[0], vertice[1], vertice[2]);
                    }
                }
                gl.glEnd();
            }
            gl.glEndList();
        } catch (IOException ex) {
            System.out.println("Ошибка загрузки модели!");
        }
    }

    public void drawModel(GL gl) {
            Texture texture = TextureLoader.load(texname);
            gl.glNewList(modelList, GL.GL_COMPILE);
            if(texture != null){
                gl.glBindTexture(GL.GL_TEXTURE_2D, texture.getTextureObject());
            }
            for (Object face : faces) {
                if(face == null) break;
                gl.glBegin(GL.GL_POLYGON);
                for (Object indexes : (ArrayList) face) {
                    if(indexes == null) break;
                    if (normals.size() > 0) {
                        float[] normal = (float[]) normals.get(((int[]) indexes)[2]);
                        gl.glNormal3f(normal[0], normal[1], normal[2]);
                    }
                    if (textureCoords.size() > 0) {
                        float[] texCoord = (float[]) textureCoords.get(((int[]) indexes)[1]);
                        gl.glTexCoord2f(texCoord[0], texCoord[1]);
                    }
                    if (vertices.size() > 0){
                        float[] vertice = (float[]) vertices.get(((int[]) indexes)[0]);
                        gl.glVertex3f(vertice[0], vertice[1], vertice[2]);
                    }
                }
                gl.glEnd();
            }
            gl.glEndList();
        gl.glCallList(modelList);
    }
}
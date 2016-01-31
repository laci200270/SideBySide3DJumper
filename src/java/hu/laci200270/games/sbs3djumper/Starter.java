package hu.laci200270.games.sbs3djumper;

import hu.laci200270.games.sbs3djumper.obj.ObjFile;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.zip.ZipFile;

import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glGetError;
import static org.lwjgl.opengl.GL11.glTranslatef;

/**
 * Created by Laci on 2016. 01. 29..
 */
public class Starter {

    public static void main(String[] args){

        System.setProperty("org.lwjgl.librarypath", new File("natives/windows").getAbsolutePath());
        try {
            Display.create();
            Display.setDisplayMode(new DisplayMode(640,480));
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
        ObjFile file=null;
        try {
            file=new ObjFile(new File("d:/untitled.obj"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (file != null) {
            try {
                file.parse();
            } catch (ObjFile.ObjFormatException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        float rotation =0f;
        //GL11.glEnable(GL11.GL_DEPTH_TEST);
        while(!Display.isCloseRequested()){
            GL11.glMatrixMode(GL11.GL_PROJECTION);
            GL11.glLoadIdentity();
           // GL11.glOrtho(0, Display.getDisplayMode().getWidth(), 0, Display.getDisplayMode().getHeight(), -1, 1);
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT); //clear screen
            rotation++;
            System.out.println(glGetError());
            glTranslatef(-1,-1,-1);
            file.render();
            Display.update();
            Display.sync(60);


        }
        System.exit(0);
    }

}

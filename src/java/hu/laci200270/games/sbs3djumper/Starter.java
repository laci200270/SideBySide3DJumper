package hu.laci200270.games.sbs3djumper;

import hu.laci200270.games.sbs3djumper.models.ModelLoaderRegistry;
import hu.laci200270.games.sbs3djumper.obj.ObjLoader;
import hu.laci200270.games.sbs3djumper.obj.ObjModel;
import hu.laci200270.games.sbs3djumper.utils.GLUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by Laci on 2016. 01. 29..
 */
public class Starter {

    private static long lastFrame;

    public static void main(String[] args){

        System.setProperty("org.lwjgl.librarypath", new File("natives/windows").getAbsolutePath());
        try {
            Display.create();
            Display.setDisplayMode(new DisplayMode(640,480));
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
        ObjModel file=null;
        ModelLoaderRegistry.registerModelLoader(new ObjLoader());
        float rotation =0f;
        try {
            file= (ObjModel) ModelLoaderRegistry.getModel(new File("d:/knife/knife.obj"),"obj");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            ModelLoaderRegistry.uploadTextures();
        } catch (IOException e) {
            e.printStackTrace();
        }
        glEnable(GL_DEPTH_TEST);
        glDepthFunc(GL_LEQUAL);
        Camera.create();
        while(!Display.isCloseRequested()){
            GL11.glMatrixMode(GL11.GL_PROJECTION);
            GL11.glLoadIdentity();
            GL11.glDisable(GL11.GL_CULL_FACE);

           // GL11.glShadeModel(G);
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT); //clear screen

            Camera.acceptInput(getDelta());
            Camera.apply();
            GL11.glLoadIdentity();
            GL11.glClearColor(0.5f,0.5f,0.25f,0);

            GL11.glPushMatrix();
            //GLUtils.drawFloor(-1);
            GL11.glTranslatef(0, -1, 0);
            GL11.glScalef(0.1f, 0.1f, 0.1f);
            GL11.glRotatef(rotation, 0, 1, 0f);
            rotation++;

            glDisable(GL_CULL_FACE);

            file.render();
            GL11.glPopMatrix();

            Display.update();
            Display.sync(120);


        }
        System.exit(0);
    }

    public static int getDelta() {
        long time = Sys.getTime();
        int delta = (int) (time - lastFrame);
        lastFrame = time;

        return delta;
    }

}

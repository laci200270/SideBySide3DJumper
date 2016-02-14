package hu.laci200270.games.sbs3djumper;

/**
 * Created by Laci on 2016. 02. 14..
 */
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;

import org.lwjgl.glfw.GLFW;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

public class VertexBufferObjectDemo {

    public VertexBufferObjectDemo() {
        System.setProperty("org.lwjgl.librarypath", new ResourceLocation("natives/windows").file.getAbsolutePath());
        Starter.init();

        GLContext.createFromCurrent();
        GL.createCapabilities(true);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(1, 1, 1, 1, 1, -1);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

        int amountOfVertices = 4;
        int vertexSize = 4;
        int colorSize = 3;
        int texSize=3;
        int stride = (vertexSize + colorSize+texSize) * 4;

        float[] vertexArray = new float[]{-0.5f, -0.5f, 0,1, 1,0,0, 0.5f, -0.5f, 0,1, 0,1,0, 0.5f, 0.5f, 0,1, 0,0,1, -0.5f, 0.5f, 0,1, 1,1,1};

        FloatBuffer vertexData = BufferUtils.createFloatBuffer(vertexArray.length);
        vertexData.put(vertexArray);
        vertexData.flip();

        int vboVertexHandle = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboVertexHandle);
        glBufferData(GL_ARRAY_BUFFER, vertexData, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        int offset = 12;

        while (GLFW.glfwWindowShouldClose(Starter.window) == GL11.GL_FALSE) {
            glClear(GL_COLOR_BUFFER_BIT);

            glBindBuffer(GL_ARRAY_BUFFER, vboVertexHandle);
            glVertexPointer(vertexSize, GL_FLOAT, stride, 0L);

            glColorPointer(colorSize,GL_FLOAT,stride,offset);
            //glTexCoordPointer();
            glEnableClientState(GL_VERTEX_ARRAY);
            glEnableClientState(GL_COLOR_ARRAY);
            glEnableClientState(GL_TEXTURE_COORD_ARRAY);
            glDrawArrays(GL_TRIANGLES, 0, amountOfVertices);
            glDisableClientState(GL_TEXTURE_COORD_ARRAY);
            glDisableClientState(GL_COLOR_ARRAY);
            glDisableClientState(GL_VERTEX_ARRAY);

            GLFW.glfwSwapBuffers(Starter.window);
            GLFW.glfwPollEvents();

        }

        glDeleteBuffers(vboVertexHandle);


        System.exit(0);
    }

    public static void main(String[] args) {
        new VertexBufferObjectDemo();
    }
}

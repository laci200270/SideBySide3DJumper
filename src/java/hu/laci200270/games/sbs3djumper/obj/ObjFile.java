package hu.laci200270.games.sbs3djumper.obj;

import hu.laci200270.games.sbs3djumper.Constants;
import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Laci on 2016. 01. 30..
 */
public class ObjFile {
    private final File file;

    private final BufferedReader fileReader;

    private ArrayList<ObjInstruction> instructions = new ArrayList<>();

    public ObjFile(File file) throws FileNotFoundException {
        this.file = file;
        this.fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(this.file)));
    }

    public void parse() throws ObjFormatException, IOException {
        String line = fileReader.readLine();
        System.out.println("Parsing");
        while (line != null) {
            String[] words = line.split(" ");
            InstructionType instructionType = ObjInstruction.keySetInstructions.get(words[0]);
            //System.out.println("")
            ObjInstruction instruction = new ObjInstruction(instructionType);
            for (int i = 1; i < words.length; i++) {
                instruction.addParam(words[i]);
            }
            instructions.add(instruction);
            line=fileReader.readLine();
        }
    }

    public void render() {
        GL11.glBegin(GL11.GL_TRIANGLES);
        GL11.glColor3d(128,128,0);
        System.out.println("Rendering");
        System.out.println("Instrctions size: "+instructions.size());
        for (ObjInstruction objInstruction : instructions) {

            switch (objInstruction.type) {
                case VERTEX:
                    handleVertex(objInstruction);
                    break;
                case NORMAL:
                    handleNormal(objInstruction);
                    break;
                case FACE:
                    //handleFace(objInstruction);
                    break;
                default:
                    Constants.logger.warning("Unhandled instruction:" + objInstruction);
                    break;

            }
        }
        GL11.glEnd();
    }

    private static void handleVertex(ObjInstruction vertex){
        float x, y, z, normal;
        String params[]=new String[vertex.params.size()];
        for (int i=0;i<vertex.params.size();i++){
            params[i]=vertex.params.get(i);
        }
        boolean hasNormal=params.length==4;
        x=Float.parseFloat(params[0]);
        y=Float.parseFloat(params[1]);
        z=Float.parseFloat(params[2]);
        System.out.println("Handling vertex");
        if(hasNormal) {
            normal = Float.parseFloat(params[3]);
            GL11.glVertex4f(x,y,z,normal);
        }
        else {
            GL11.glVertex3f(x,y,z);
        }
    }

    private static void handleNormal(ObjInstruction normal){
        float x, y, z;
        String params[]=new String[normal.params.size()];
        for (int i=0;i<normal.params.size();i++){
            params[i]=normal.params.get(i);
        }
        x=Float.parseFloat(params[0]);
        y=Float.parseFloat(params[1]);
        z=Float.parseFloat(params[2]);
        GL11.glNormal3f(x,y,z);
    }

    private static void handleFace(ObjInstruction vertex){
        float x, y, z, normal;
        String params[]=new String[vertex.params.size()];
        for (int i=0;i<vertex.params.size();i++){
            params[i]=vertex.params.get(i);
        }
        boolean hasNormal=params.length==4;
        x=Float.parseFloat(params[0]);
        y=Float.parseFloat(params[1]);
        z=Float.parseFloat(params[2]);
        System.out.println("Handling face");
        if(hasNormal) {
            normal = Float.parseFloat(params[3]);
            GL11.glVertex4f(x,y,z,normal);
        }
        else {
            GL11.glVertex3f(x,y,z);
        }
    }

    public static class ObjFormatException extends Exception {

    }
}

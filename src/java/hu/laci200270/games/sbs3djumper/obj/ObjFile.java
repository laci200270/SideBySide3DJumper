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

    private ArrayList<Vertex4F> vertexes = new ArrayList<>();
    private ArrayList<Face> faces=new ArrayList<>();
    private ArrayList<Vertex3F> normals=new ArrayList<>();
    private ArrayList<Vertex3F> textures=new ArrayList<>();

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
            switch (instructionType){
                case VERTEX:
                    vertexes.add(handleVertex(line));
                    break;
                case FACE:
                    faces.add(handleFace(line));
                    break;
                case NORMAL:
                    normals.add(handleNormal(line));
                    break;


            }
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
        Constants.logger.fine("Rendering");
        Constants.logger.fine("Instructions size size: " + instructions.size());
        for (ObjInstruction objInstruction : instructions) {

            switch (objInstruction.type) {
                case VERTEX:

                    break;
                case NORMAL:
                    handleNormal(objInstruction);
                    break;
                case FACE:
                    handleFace(objInstruction);
                    break;
                default:
                    Constants.logger.warning("Unhandled instruction:" + objInstruction);
                    break;

            }
        }
        GL11.glEnd();
    }

    private static Vertex4F handleVertex(String line){
        float x, y, z, normal;
        ObjInstruction vertex=makeInstruction(line);
        String params[]=new String[vertex.params.size()];
        normal=1f;
        for (int i=1;i<vertex.params.size();i++){
            params[i]=vertex.params.get(i);
        }
        boolean hasNormal=params.length==4;
        x=Float.parseFloat(params[0]);
        y=Float.parseFloat(params[1]);
        z=Float.parseFloat(params[2]);
        Constants.logger.fine("Handling vertex");
        if(hasNormal) {
            normal = Float.parseFloat(params[3]);
        }

    }

    private static Vertex3F handleNormal(String line){
        float x, y, z;
        ObjInstruction normal=makeInstruction(line);
        String params[]=new String[normal.params.size()];
        for (int i=0;i<normal.params.size();i++){
            params[i]=normal.params.get(i);
        }
        x=Float.parseFloat(params[0]);
        y=Float.parseFloat(params[1]);
        z=Float.parseFloat(params[2]);
        return new Vertex3F(x,y,z);
    }

    private Face handleFace(String line){
        float x, y, z, normal;
        ObjInstruction faceobjstr=makeInstruction(line);

        String params[]=new String[faceobjstr.params.size()];
        for (int i=0;i<faceobjstr.params.size();i++){
            params[i]=faceobjstr.params.get(i);
        }
        ArrayList<FacePoint> poligons=new ArrayList<>();
        for(String param: params){
            String[] parts=param.split("/");
            if(parts.length==1){
                int var1=Integer.parseInt(parts[0]);
                poligons.add(new FacePoint(vertexes.get(var1),null,null));
            }
            if(parts.length==2){
                int var1=Integer.parseInt(parts[0]);
                int var2=Integer.parseInt(parts[1]);
                poligons.add(new FacePoint(vertexes.get(var1),textures.get(var2),null));
            }
            if(parts.length==3){
                int var1=Integer.parseInt(parts[0]);
                int var2=Integer.parseInt(parts[1]);
                int var3=Integer.parseInt(parts[2]);
                poligons.add(new FacePoint(vertexes.get(var1),textures.get(var2),normals.get(var3)));
            }



        }
        return new Face(poligons);
    }

    public static ObjInstruction makeInstruction(String line){
        String[] words = line.split(" ");
        InstructionType instructionType = ObjInstruction.keySetInstructions.get(words[0]);

        ObjInstruction instruction = new ObjInstruction(instructionType);
        for (int i = 1; i < words.length; i++) {
            instruction.addParam(words[i]);
        }
        return instruction;
    }

    public static class ObjFormatException extends Exception {

    }
}

package hu.laci200270.games.sbs3djumper.obj;

import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.Vec4;
import hu.laci200270.games.sbs3djumper.Constants;
import hu.laci200270.games.sbs3djumper.ResourceLocation;
import hu.laci200270.games.sbs3djumper.models.IModel;
import hu.laci200270.games.sbs3djumper.models.IModelLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Laci on 2016. 02. 06..
 */
public class ObjLoader implements IModelLoader {

    private BufferedReader fileReader;

    private ArrayList<Vec4> vertexes = new ArrayList<>();

    private ArrayList<Face> faces = new ArrayList<>();

    private ArrayList<Vec3> normals = new ArrayList<>();

    private ArrayList<Vec3> textures = new ArrayList<>();

    private static Vec4 handleVertex(String line) {
        float x, y, z, normal;
        ObjInstruction vertex = makeInstruction(line);
        String params[] = new String[vertex.params.size()];
        normal = 1f;
        for (int i = 0; i < vertex.params.size(); i++) {
            params[i] = vertex.params.get(i);
        }
        boolean hasNormal = params.length == 4;
        x = Float.parseFloat(params[0]);
        y = Float.parseFloat(params[1]);
        z = Float.parseFloat(params[2]);
        Constants.logger.fine("Handling vertex");
        if (hasNormal) {
            normal = Float.parseFloat(params[3]);
        }
        return new Vec4(x, y, z, normal);
    }

    private static Vec3 handleNormal(String line) {
        float x, y, z;
        ObjInstruction normal = makeInstruction(line);
        String params[] = new String[normal.params.size()];
        for (int i = 0; i < normal.params.size(); i++) {
            params[i] = normal.params.get(i);
        }
        x = Float.parseFloat(params[0]);
        y = Float.parseFloat(params[1]);
        z = Float.parseFloat(params[2]);
        return new Vec3(x, y, z);
    }

    public static ObjInstruction makeInstruction(String line) {
        String[] words = line.split(" ");
        InstructionType instructionType = ObjInstruction.keySetInstructions.get(words[0]);

        ObjInstruction instruction = new ObjInstruction(instructionType);
        for (int i = 1; i < words.length; i++) {
            instruction.addParam(words[i]);
        }
        return instruction;
    }


    public IModel loadModel(InputStream inputStream) throws IOException {
        this.fileReader = new BufferedReader(new InputStreamReader(inputStream));
        vertexes = new ArrayList<>();
        faces = new ArrayList<>();
        normals = new ArrayList<>();
        textures = new ArrayList<>();
        String line = null;
        line = fileReader.readLine();

        System.out.println("Parsing");
        while (line != null) {
            String[] words = line.split(" ");
            InstructionType instructionType = ObjInstruction.keySetInstructions.get(words[0]);
            try {
                switch (instructionType) {
                    case VERTEX:
                        vertexes.add(handleVertex(line));
                        break;
                    case FACE:
                        faces.add(handleFace(line));
                        break;
                    case NORMAL:
                        normals.add(handleNormal(line));
                        break;
                    case TEXTURE:
                        textures.add(handleTexture(line));

                }
            } catch (NullPointerException e) {
                System.out.println(words[0]);
            }
            ObjInstruction instruction = new ObjInstruction(instructionType);
            for (int i = 1; i < words.length; i++) {
                instruction.addParam(words[i]);
            }
            // instructions.add(instruction);

            line = fileReader.readLine();

        }
        fileReader.close();

        return new ObjModel(vertexes, faces, normals, textures);
    }

    private Vec3 handleTexture(String line) {
        float x, y, z;
        ObjInstruction normal = makeInstruction(line);
        String params[] = new String[normal.params.size()];
        for (int i = 0; i < normal.params.size(); i++) {
            params[i] = normal.params.get(i);
        }
        x = Float.parseFloat(params[0]);
        y = Float.parseFloat(params[1]);
        z = 1;
        if (params.length == 3)
            z = Float.parseFloat(params[2]);

        return new Vec3(x, y, z);
    }

    @Override
    public IModel loadModel(ResourceLocation loc) throws IOException {
        return loadModel(loc.getInputStream());
    }

    @Override
    public String getTypeFormat() {
        return "obj";
    }

    private Face handleFace(String line) {
        float x, y, z, normal;
        ObjInstruction faceobjstr = makeInstruction(line);

        String params[] = new String[faceobjstr.params.size()];
        for (int i = 0; i < faceobjstr.params.size(); i++) {
            params[i] = faceobjstr.params.get(i);
        }
        ArrayList<FacePoint> poligons = new ArrayList<>();
        for (String param : params) {
            String[] parts = param.split("/");
            if (parts.length == 1) {
                int var1 = Integer.parseInt(parts[0]) - 1;
                FacePoint point = new FacePoint(vertexes.get(var1), null, null);
                point.color = new Vec3(Constants.random.nextFloat(), Constants.random.nextFloat(), Constants.random.nextFloat());
                poligons.add(point);
            }
            if (parts.length == 2) {
                int var1 = Integer.parseInt(parts[0]) - 1;
                int var2 = Integer.parseInt(parts[1]) - 1;
                FacePoint point = new FacePoint(vertexes.get(var1), textures.get(var2), null);
                point.color = new Vec3(Constants.random.nextFloat(), Constants.random.nextFloat(), Constants.random.nextFloat());

                poligons.add(point);
            }
            if (parts.length == 3) {
                int var1 = Integer.parseInt(parts[0]) - 1;
                if (parts[1].isEmpty()) {
                    int var3 = Integer.parseInt(parts[2]) - 1;
                    FacePoint point = new FacePoint(vertexes.get(var1), null, normals.get(var3));
                    point.color = new Vec3(Constants.random.nextFloat(), Constants.random.nextFloat(), Constants.random.nextFloat());
                    poligons.add(point);
                } else {
                    int var2 = Integer.parseInt(parts[1]) - 1;
                    int var3 = Integer.parseInt(parts[2]) - 1;
                    FacePoint point = new FacePoint(vertexes.get(var1), textures.get(var2), normals.get(var3));
                    point.color = new Vec3(Constants.random.nextFloat(), Constants.random.nextFloat(), Constants.random.nextFloat());

                    poligons.add(point);
                }

            }


        }
        return new Face(poligons);
    }

}

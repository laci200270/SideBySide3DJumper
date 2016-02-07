package hu.laci200270.games.sbs3djumper.obj;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Laci on 2016. 01. 30..
 */
public class ObjInstruction {
    public static HashMap<String, InstructionType> keySetInstructions = new HashMap<String, InstructionType>();

    static {
        keySetInstructions.put("f", InstructionType.FACE);
        keySetInstructions.put("vn", InstructionType.NORMAL);
        keySetInstructions.put("v", InstructionType.VERTEX);
        keySetInstructions.put("o", InstructionType.OBJECT);
        keySetInstructions.put("#", InstructionType.COMMENT);
        keySetInstructions.put("mtllib", InstructionType.MATERIAL_LIB_LOAD);
        keySetInstructions.put("usemtl", InstructionType.MATERIAL_SELECT);
        keySetInstructions.put("s", InstructionType.SHADING);
        keySetInstructions.put("vt", InstructionType.TEXTURE);
    }

    public InstructionType type;

    public ArrayList<String> params = new ArrayList<>();

    public ObjInstruction(InstructionType type) {
        this.type = type;

    }

    public void addParam(String parameter) {
        params.add(parameter);
    }

    @Override
    public String toString() {
        return "ObjInstruction{" +
                "type=" + type +
                ", params=" + params +
                '}';
    }
}



package hu.laci200270.games.sbs3djumper.models;

import hu.laci200270.games.sbs3djumper.ResourceLocation;
import hu.laci200270.games.sbs3djumper.Texture;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

/**
 * Created by Laci on 2016. 03. 24..
 */
public abstract class AbstractModel implements IModel {


    protected int vboId = GL15.glGenBuffers();
    protected int colorVboId = GL15.glGenBuffers();
    protected int texVboId = GL15.glGenBuffers();
    protected int normalVboId = GL15.glGenBuffers();
    protected int vaoId = GL30.glGenVertexArrays();
    protected String texName;
    protected Texture texture;




    @Override
    public String getTextureName() {
        return texName;
    }

    @Override
    public void setTextureName(String name) {
        texName=name;
    }


    @Override
    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    @Override
    public void loadTexture() {
        if (!getTextureName().equals("")) {
            if (new ResourceLocation(String.format("textures/%s.png", texName)).getInputStream() != null)
                setTexture(new Texture(new ResourceLocation(String.format("textures/%s.png", texName))));
        }
    }

    /***
     *  Delets VBOs and finnaly the VAO
     *
     *
     * @throws Throwable if there is an error
     */
    @Override
    protected void finalize() throws Throwable{
        GL30.glBindVertexArray(vaoId);
        GL15.glDeleteBuffers(vboId);
        GL15.glDeleteBuffers(colorVboId);
        GL15.glDeleteBuffers(texVboId);
        GL30.glDeleteVertexArrays(vaoId);
        super.finalize();
    }
}

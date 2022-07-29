package cga.exercise.components.geometry

import cga.exercise.components.shader.ShaderProgram
import cga.exercise.components.texture.Texture2D
import org.joml.Vector2f
import org.lwjgl.opengl.ARBVertexArrayObject.glBindVertexArray
import org.lwjgl.opengl.ARBVertexArrayObject.glGenVertexArrays
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL15
import org.lwjgl.opengl.GL15.*
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL20.glEnableVertexAttribArray
import org.lwjgl.opengl.GL20.glVertexAttribPointer
import org.lwjgl.opengl.GL30

/**
 * Creates a Mesh object from vertexdata, intexdata and a given set of vertex attributes
 *
 * @param vertexdata plain float array of vertex data
 * @param indexdata  index data
 * @param attributes vertex attributes contained in vertex data
 * @throws Exception If the creation of the required OpenGL objects fails, an exception is thrown
 *
 * Created by Fabian on 16.09.2017.
 */
class Mesh(vertexdata: FloatArray, indexdata: IntArray, attributes: Array<VertexAttribute>,private val material: Material) {
    private var vaoID = 0
    private var vboID = 0
    private var iboID = 0
    private var indexcount =indexdata.count()

    init {

        vaoID = glGenVertexArrays()
        glBindVertexArray(vaoID)

        vboID= glGenBuffers()
        glBindBuffer(GL_ARRAY_BUFFER,vboID)
        glBufferData(GL_ARRAY_BUFFER,vertexdata,GL_STATIC_DRAW)
        for (a in attributes.indices){
            glEnableVertexAttribArray(a)
            glVertexAttribPointer(a,attributes[a].n,GL_FLOAT,false,attributes[a].stride,attributes[a].offset.toLong())
        }
        iboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iboID)
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexdata, GL_STATIC_DRAW)

        glBindVertexArray(0)

        glBindBuffer(GL_ARRAY_BUFFER, 0)
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0)
    }

    /**
     * renders the mesh
     */
    fun render() {
        glBindVertexArray(vaoID)
        glDrawElements(GL_TRIANGLES,indexcount, GL_UNSIGNED_INT,0)
        glBindVertexArray(0)
    }
    fun render(shaderProgram: ShaderProgram) {
            material.bind(shaderProgram)
            glBindVertexArray(vaoID)
            glDrawElements(GL_TRIANGLES, indexcount, GL_UNSIGNED_INT, 0)
            glBindVertexArray(0)
    }

    /**
     * Deletes the previously allocated OpenGL objects for this mesh
     */
    fun cleanup() {
        if (iboID != 0) GL15.glDeleteBuffers(iboID)
        if (vboID != 0) GL15.glDeleteBuffers(vboID)
        if (vaoID != 0) GL30.glDeleteVertexArrays(vaoID)
    }
}
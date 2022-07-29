package cga.exercise.components.geometry

import cga.exercise.components.shader.ShaderProgram
import org.joml.Vector3f

interface IRenderable {
    fun render(shaderProgram: ShaderProgram,staticColor: Vector3f=Vector3f(1f,1f,1f))
}

class Renderable(val meshes :MutableList<Mesh>):IRenderable,Transformable(){
    override fun render(shaderProgram: ShaderProgram,staticColor:Vector3f) {
        shaderProgram.setUniform("model_matrix",getWorldModelMatrix(),false)
        shaderProgram.setUniform("staticColor",staticColor)
        for (i in meshes){
            i.render(shaderProgram)
        }
    }
}
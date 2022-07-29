package cga.exercise.components.light

import cga.exercise.components.geometry.Transformable
import cga.exercise.components.shader.ShaderProgram
import org.joml.Vector3f

open class PointLight(lPosI: Vector3f, val rgb: Vector3f):Transformable(),IPointLight {
    init{
        preTranslate(lPosI)
    }
    override fun bind(shaderProgram: ShaderProgram) {
        shaderProgram.setUniform("lightPos",getWorldPosition())
        shaderProgram.setUniform("lightColor",rgb)
    }
    fun bind(shaderProgram: ShaderProgram,staticColor: Vector3f = Vector3f(1f,1f,1f)) {
        shaderProgram.setUniform("lightPos",getWorldPosition())
        shaderProgram.setUniform("lightColor",staticColor)
    }
}
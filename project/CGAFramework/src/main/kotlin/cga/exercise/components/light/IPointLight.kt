package cga.exercise.components.light

import cga.exercise.components.shader.ShaderProgram
import org.joml.Vector3f

interface IPointLight {
    fun bind(shaderProgram: ShaderProgram)
}
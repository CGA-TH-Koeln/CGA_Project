package cga.exercise.components.light

import cga.exercise.components.shader.ShaderProgram
import org.joml.Math.cos
import org.joml.Matrix3f
import org.joml.Matrix4f
import org.joml.Vector3f
import org.joml.Vector4f

class SpotLight(
    lPosI: Vector3f,
    rgb: Vector3f,
    private val outerAngle:Float= Math.toRadians(25.5).toFloat(),//cutoff
    private val innerAngle:Float= Math.toRadians(15.0).toFloat()
):PointLight(lPosI,rgb),ISpotLight{

    override fun bind(shaderProgram: ShaderProgram, viewMatrix: Matrix4f,index:Int) {

        shaderProgram.setUniform("spotPos$index",getWorldPosition())
        shaderProgram.setUniform("spotColor$index",rgb)
        shaderProgram.setUniform("spotDir$index",getWorldZAxis().negate().mul(Matrix3f(viewMatrix)))
        shaderProgram.setUniform("outerAngle$index",cos(outerAngle))
        shaderProgram.setUniform("innerAngle$index",cos(innerAngle))
    }
}
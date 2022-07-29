package cga.exercise.components.camera

import cga.exercise.components.geometry.Transformable
import cga.exercise.components.shader.ShaderProgram
import org.joml.Matrix4f
import org.joml.Vector3f

interface ICamera {

    /*
     * Calculate the ViewMatrix according the lecture
     * values needed:
     *  - eye –> the position of the camera
     *  - center –> the point in space to look at
     *  - up –> the direction of 'up'
     */
    fun getCalculateViewMatrix(): Matrix4f

    /*
     * Calculate the ProjectionMatrix according the lecture
     * values needed:
     *  - fov – the vertical field of view in radians (must be greater than zero and less than PI)
     *  - aspect – the aspect ratio (i.e. width / height; must be greater than zero)
     *  - zNear – near clipping plane distance
     *  - zFar – far clipping plane distance
     */
    fun getCalculateProjectionMatrix(): Matrix4f

    fun bind(shader: ShaderProgram)
}

class TronCamera():ICamera,Transformable(){

    override fun getCalculateViewMatrix(): Matrix4f {

        val newMat=Matrix4f()
        val eye=getWorldPosition()
        val center=getWorldPosition().sub(getWorldZAxis())
        val up=getWorldYAxis()
        return newMat.lookAt(eye,center,up)
    }

    override fun getCalculateProjectionMatrix(): Matrix4f {
        val fov=Math.toRadians(90.0).toFloat()
        val aspect=16f/9f
        val near=0.1f
        val far=100f
        val newMat=Matrix4f()

        return newMat.perspective(fov,aspect,near,far)
    }

    override fun bind(shader: ShaderProgram) {
        shader.setUniform("view_matrix",getCalculateViewMatrix(),false)
        shader.setUniform("projection_matrix",getCalculateProjectionMatrix(),false)
    }
}
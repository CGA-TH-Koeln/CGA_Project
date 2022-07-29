package cga.exercise.components.geometry

import org.joml.Matrix4f
import org.joml.Vector3f

open class Transformable(private var modelMatrix: Matrix4f = Matrix4f(), var parent: Transformable? = null) {
    /**
     * Returns copy of object model matrix
     * @return modelMatrix
     */
    fun getModelMatrix(): Matrix4f {
        val copy= Matrix4f()
        modelMatrix.get(copy)
        return copy
        //throw NotImplementedError()
    }

    /**
     * Returns multiplication of world and object model matrices.
     * Multiplication has to be recursive for all parents.
     * Hint: scene graph
     * @return world modelMatrix
     */

    fun getWorldModelMatrix(): Matrix4f{
        if(parent!=null){
            return (parent!!.getWorldModelMatrix()).mul(getModelMatrix())
        }else{
            return getModelMatrix()
        }
       // throw NotImplementedError()
    }

    /**
     * Rotates object around its own origin.
     * @param pitch radiant angle around x-axis ccw
     * @param yaw radiant angle around y-axis ccw
     * @param roll radiant angle around z-axis ccw
     */
    fun rotate(pitch: Float, yaw: Float, roll: Float) {
        val m = Matrix4f()
        m.rotateXYZ(pitch,yaw,roll)
        m.mul(modelMatrix,modelMatrix)
       // throw NotImplementedError()
    }
    fun rotateOwn(pitch: Float, yaw: Float, roll: Float) {
        val m = Matrix4f()
        m.rotateXYZ(pitch,yaw,roll)
        modelMatrix.mul(m,modelMatrix)
        // throw NotImplementedError()
    }

    /**
     * Rotates object around given rotation center.
     * @param pitch radiant angle around x-axis ccw
     * @param yaw radiant angle around y-axis ccw
     * @param roll radiant angle around z-axis ccw
     * @param altMidpoint rotation center
     */
    fun rotateAroundPoint(pitch: Float, yaw: Float, roll: Float, altMidpoint: Vector3f) {
        val m = Matrix4f()
        m.translate(altMidpoint)
        m.rotateXYZ(pitch,yaw,roll)
        m.translate(altMidpoint.negate())
        m.mul(modelMatrix,modelMatrix)
       //throw NotImplementedError()
    }

    /**
     * Translates object based on its own coordinate system.
     * @param deltaPos delta positions
     */
    fun translate(deltaPos: Vector3f) {
        val m = Matrix4f()
        m.translate(deltaPos)
        modelMatrix.mul(m,modelMatrix)
     //   throw NotImplementedError()
    }

    /**
     * Translates object based on its parent coordinate system.
     * Hint: this operation has to be left-multiplied
     * @param deltaPos delta positions (x, y, z)
     */
    fun preTranslate(deltaPos: Vector3f) {
        val m=Matrix4f()
        m.translate(deltaPos)
        m.mul(getWorldModelMatrix(),modelMatrix)
    }

    /**
     * Scales object related to its own origin
     * @param scale scale factor (x, y, z)
     */
    fun scale(scale: Vector3f) {
        modelMatrix.scale(scale)
        //throw NotImplementedError()
    }

    /**
     * Returns position based on aggregated translations.
     * Hint: last column of model matrix
     * @return position
     */
    fun getPosition(): Vector3f {
        return getModelMatrix().getColumn(3,Vector3f())
       // throw NotImplementedError()
    }

    /**
     * Returns position based on aggregated translations incl. parents.
     * Hint: last column of world model matrix
     * @return position
     */
    fun getWorldPosition(): Vector3f {
        return getWorldModelMatrix().getColumn(3,Vector3f())
       // throw NotImplementedError()
    }

    /**
     * Returns x-axis of object coordinate system
     * Hint: first normalized column of model matrix
     * @return x-axis
     */
    fun getXAxis(): Vector3f {
        val pos=Vector3f()
        getModelMatrix().getColumn(0,pos)
        return pos.normalize()
       // throw NotImplementedError()
    }

    /**
     * Returns y-axis of object coordinate system
     * Hint: second normalized column of model matrix
     * @return y-axis
     */
    fun getYAxis(): Vector3f {
        val pos=Vector3f()
        getModelMatrix().getColumn(1,pos)
        return pos.normalize()
        //throw NotImplementedError()
    }

    /**
     * Returns z-axis of object coordinate system
     * Hint: third normalized column of model matrix
     * @return z-axis
     */
    fun getZAxis(): Vector3f {
        val pos=Vector3f()
        getModelMatrix().getColumn(2,pos)
        return pos.normalize()
//        throw NotImplementedError()
    }

    /**
     * Returns x-axis of world coordinate system
     * Hint: first normalized column of world model matrix
     * @return x-axis
     */
    fun getWorldXAxis(): Vector3f {
        val pos=Vector3f()
        getWorldModelMatrix().getColumn(0,pos)
        return pos.normalize()
//        throw NotImplementedError()
    }

    /**
     * Returns y-axis of world coordinate system
     * Hint: second normalized column of world model matrix
     * @return y-axis
     */
    fun getWorldYAxis(): Vector3f {
        val pos=Vector3f()
        getWorldModelMatrix().getColumn(1,pos)
        return pos.normalize()
//        throw NotImplementedError()
    }

    /**
     * Returns z-axis of world coordinate system
     * Hint: third normalized column of world model matrix
     * @return z-axis
     */
    fun getWorldZAxis(): Vector3f {
        val pos=Vector3f()
        getWorldModelMatrix().getColumn(2,pos)
        return pos.normalize()

//        throw NotImplementedError()
    }
}
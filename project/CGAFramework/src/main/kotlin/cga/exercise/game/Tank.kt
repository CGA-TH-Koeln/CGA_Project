package cga.exercise.game

import cga.exercise.components.shader.ShaderProgram
import cga.framework.ModelLoader.loadModel

class Tank{
    var lifePoints=5
    var base= loadModel("assets/models/Tank/toon_tank_base.obj",Math.toRadians(0.0).toFloat(),Math.toRadians(0.0).toFloat(),0f)
    var tower= loadModel("assets/models/Tank/toon_tank_tower.obj",Math.toRadians(0.0).toFloat(),Math.toRadians(0.0).toFloat(),0f)
    var barrel= loadModel("assets/models/Tank/toon_tank_barrel.obj",Math.toRadians(0.0).toFloat(),Math.toRadians(0.0).toFloat(),0f)

    init{
        tower?.parent= base
        barrel?.parent=tower
    }

    fun loseLp(){
        lifePoints--
        if (lifePoints<=0){
            println("u dead son")
        }
    }

    fun gainLp(){
        if (lifePoints<5){
            lifePoints++
        } else println("u're cheating!")
    }

    fun isDead():Boolean=lifePoints<=0

    fun render(shaderProgram: ShaderProgram){
        base?.render(shaderProgram)
        tower?.render(shaderProgram)
        barrel?.render(shaderProgram)
    }

}
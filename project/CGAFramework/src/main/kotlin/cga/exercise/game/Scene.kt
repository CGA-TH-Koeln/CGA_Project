package cga.exercise.game

import cga.exercise.components.camera.TronCamera
import cga.exercise.components.geometry.Material
import cga.exercise.components.geometry.Mesh
import cga.exercise.components.geometry.Renderable
import cga.exercise.components.geometry.VertexAttribute
import cga.exercise.components.light.PointLight
import cga.exercise.components.light.SpotLight
import cga.exercise.components.shader.ShaderProgram
import cga.exercise.components.texture.Texture2D
import cga.framework.GLError
import cga.framework.GameWindow
import cga.framework.ModelLoader.loadModel
import cga.framework.OBJLoader
import org.joml.Math.sin
import org.joml.Matrix4f
import org.joml.Vector2f
import org.joml.Vector3f
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL13
import org.lwjgl.opengl.GL20.glUniformMatrix4fv
import java.nio.FloatBuffer


/**
 * Created by Fabian on 16.09.2017.
 */
class Scene(private val window: GameWindow) {
    private val staticShader: ShaderProgram
    private var renderList: MutableList<Renderable> = mutableListOf()
    private val motorrad:Renderable?
    val newCam=TronCamera()
    var pointLight:PointLight
    var spotLight1:SpotLight
    var spotLight2:SpotLight
    var staticColor=Vector3f(0f,1f,0f)
    var cXPos=0.0
    val Player1=Tank()
    val Player2=Tank()
    var currentPlayer=Player1

    //scene setup

    init {
        staticShader = ShaderProgram("assets/shaders/tron_vert.glsl", "assets/shaders/tron_frag.glsl")

        //initial opengl state
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f); GLError.checkThrow()
        glEnable(GL_CULL_FACE); GLError.checkThrow()
        glFrontFace(GL_CCW); GLError.checkThrow()//CCW=CounterClockWise
        glCullFace(GL_BACK); GLError.checkThrow()
        glEnable(GL_DEPTH_TEST); GLError.checkThrow()
        glDepthFunc(GL_LESS); GLError.checkThrow()

        //ObjectLoader
        //load an object and create a mesh

        var objRes = OBJLoader.loadOBJ("assets/models/ground.obj")
        //Get all meshes of the first object
        var objMeshList = objRes.objects[0].meshes[0]

        var vertices= objMeshList.vertexData
        var indices= objMeshList.indexData

        var attributePos =VertexAttribute(3, GL_FLOAT,32,0)
        var attributeTex= VertexAttribute(2, GL_FLOAT,32,3*4)
        var attributeNorm= VertexAttribute(3, GL_FLOAT,32,5*4)
        var attributes= arrayOf(attributePos,attributeTex,attributeNorm)

        val diff= Texture2D("assets/textures/ground_diff.png",true)
        diff.setTexParams(GL_REPEAT, GL_REPEAT, GL_LINEAR_MIPMAP_LINEAR, GL_LINEAR)
        val emit= Texture2D("assets/textures/ground_emit.png",true)
        emit.setTexParams(GL_REPEAT, GL_REPEAT, GL_LINEAR_MIPMAP_LINEAR, GL_LINEAR)
        val specular= Texture2D("assets/textures/ground_spec.png",true)
        specular.setTexParams(GL_REPEAT, GL_REPEAT, GL_LINEAR_MIPMAP_LINEAR, GL_LINEAR)
        val shininess=5.0f
        val tcMultiplier= Vector2f(64f,64f)
        val material = Material(diff,emit,specular,shininess,tcMultiplier)

        val rBoden=Renderable(mutableListOf(Mesh(vertices,indices,attributes,material)))
       // rBoden.scale(Vector3f(0.03f))
        var angle=Math.toRadians(45.0).toFloat()
        //rBoden.rotate(0f,0f,angle)

        renderList.add(rBoden)

        motorrad= loadModel("assets/models/CargoShip/ship.obj",Math.toRadians(0.0).toFloat(),Math.toRadians(0.0).toFloat(),0f)
        println(motorrad!!.meshes)
        motorrad?.scale(Vector3f(0.8f))

        angle=Math.toRadians(-35.0).toFloat()
        newCam.preTranslate(Vector3f(0f,4f,10f))
        newCam.rotateWorld(angle,0f,0f)
        newCam.parent=currentPlayer.base

        pointLight= PointLight(Vector3f(0f,0.5f,0f),Vector3f(0.6f,0.1f,0.9f))
        pointLight.parent=motorrad

        spotLight1=SpotLight(Vector3f(0f,0.5f,-2f),Vector3f(1f,1f,1f),Math.toRadians(60.0).toFloat(),Math.toRadians(30.0).toFloat())
        spotLight1.rotateWorld(Math.toRadians(-5.0).toFloat(),0f,0f)
        spotLight1.parent=motorrad

        // Weitere Lichtquelle
        spotLight2=SpotLight(Vector3f(0f,5f,0f),Vector3f(1f,0f,0f),Math.toRadians(20.0).toFloat(),Math.toRadians(15.0).toFloat())
        spotLight2.rotateWorld(Math.toRadians(-90.0).toFloat(),0f,0f)
        Player1.base?.translate(Vector3f(0f,0f,10f))
        Player2.base?.translate(Vector3f(0f,0f,-10f))
        println("Test")
    }

    fun render(dt: Float, t: Float) {
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
        staticShader.use()
        newCam.bind(staticShader) // Macht es einen Unterschied, wenn wir die Camera vor den Objekten Binden oder danach?
        var newColor= Vector3f(sin(t)*0.3f,sin(t)*0.6f,sin(t)*0.9f)
       // var newColor= Vector3f(0f,1f,0f)
        pointLight.bind(staticShader,newColor)
        spotLight1.bind(staticShader,newCam.getCalculateViewMatrix(),1)
        spotLight2.bind(staticShader,newCam.getCalculateViewMatrix(),2)
       // for (i in renderList) {
            //i.render(staticShader)
        renderList[0].render(staticShader, staticColor)
      // }
      //  motorrad?.render(staticShader,newColor)//,Vector3f(1f,1f,1f))
        Player1.render(staticShader)
        Player2.render(staticShader)


    }

    fun update(dt: Float, t: Float) {
        val z = 8f
        val angle = Math.toRadians(60.0).toFloat()
        if (window.getKeyState(GLFW_KEY_W)) currentPlayer.base?.translate(Vector3f(0f, 0f, -z * dt))
            if (window.getKeyState(GLFW_KEY_A)) currentPlayer.base!!.rotate(0f, angle * dt, 0f)
            if (window.getKeyState(GLFW_KEY_D)) currentPlayer.base!!.rotate(0f, -angle * dt, 0f)

        if (window.getKeyState(GLFW_KEY_S)) currentPlayer.base?.translate(Vector3f(0f, 0f, z * dt))
//            if (window.getKeyState(GLFW_KEY_A)) newTank.base?.rotate(0f, angle * dt, 0f)
//            if (window.getKeyState(GLFW_KEY_D)) newTank.base?.rotate(0f, -angle * dt, 0f)

    }

    fun onKey(key: Int, scancode: Int, action: Int, mode: Int) {
        if(key==GLFW_KEY_E&&action==GLFW_PRESS) newCam.parent=currentPlayer.barrel
    }

    fun onMouseMove(xpos: Double, ypos: Double) {
        val dXPos = cXPos-xpos

        val m=newCam.getCalculateViewMatrix().mul( currentPlayer.base?.getWorldModelMatrix())
        val motopos= m.getColumn(3,Vector3f())

        newCam.rotateWorld(0f,Math.toRadians(dXPos*0.02).toFloat(),0f)
        currentPlayer.tower?.rotate(0f,Math.toRadians(dXPos*0.02).toFloat(),0f)
        //newCam.rotate(0f,Math.toRadians(dXPos*0.02).toFloat(),0f)
        cXPos=xpos
    }

    fun aim(key:Int,action:Int){

    }
    fun cleanup() {}
}
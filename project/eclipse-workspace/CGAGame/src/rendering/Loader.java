package rendering;

import java.util.List;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import models.RawModel;

//Loading 3d Model into memory
public class Loader {
	private List<Integer> vaos = new ArrayList<Integer>();
	private List<Integer> vbos = new ArrayList<Integer>();
	private List<Integer> textures = new ArrayList<Integer>();
  	
	
 public RawModel loadtoVAO(float[] positions,float[] textureCoords, float[] normals , int [] indices) {
	 int vaoID = createVAO();
	 bindIndicesBuffer(indices);
	 dataInAttrList(0,3,positions);
	 dataInAttrList(1,2,textureCoords);
	 dataInAttrList(2,3,normals); 			// posi 2, 3 lang
	 unbindVAO();
	 return new RawModel(vaoID,indices.length);
 }
 
 public void CleanUp() {
	 for(int vao:vaos) {
		 GL30.glDeleteVertexArrays(vao);
	 }
	 for(int vbo:vbos) {
		 GL15.glDeleteBuffers(vbo);
	 }
	 for(int texture:textures) {
		 GL11.glDeleteTextures(texture);
	 }
 }
 
 public int loadTexture(String fileName) {
	Texture texture = null;
	try {
		texture = TextureLoader.getTexture("PNG",new FileInputStream("res/"+fileName+".png"));
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	int textureID = texture.getTextureID();
	textures.add(textureID);
	return textureID;
 }
 
 private int createVAO() {
	 int vaoID = GL30.glGenVertexArrays();
	 vaos.add(vaoID);
	 GL30.glBindVertexArray(vaoID);
	 return vaoID;
 }
 
 private void dataInAttrList(int attrNr,int coordinateSize ,float[] data) {
	int vboID = GL15.glGenBuffers();
	vbos.add(vboID);
	GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);				//Type vom vbo
	FloatBuffer buffer = storeDataInFloatBuffer(data);			//Daten in Float Buffer konvertieren
	GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STREAM_DRAW);
	GL20.glVertexAttribPointer(attrNr, coordinateSize, GL11.GL_FLOAT, false,0,0);
	GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);			// 0 = vbo unbind
 }
 
 private void unbindVAO(){
	 GL30.glBindVertexArray(0);
 }
 
 //indices buffer laden und binden 
 private void bindIndicesBuffer(int[] indices) {
	 int vboID = GL15.glGenBuffers();						//Leere VBO erstellen
	 vbos.add(vboID);										//Zur liste hinzufügen
	 GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID); 	//Sagt OpenGL dass indices buffer genutzt wird
	 IntBuffer buffer = storeDataInIntBuffer(indices);
	 GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW); 
 }
 
 //Indices werden im Int Buffer gespeichert
 private IntBuffer storeDataInIntBuffer (int[] data) {
	 IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
	 buffer.put(data);
	 buffer.flip();
	 return buffer;
 }
 
 private FloatBuffer storeDataInFloatBuffer(float[] data) {
	 FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
	 buffer.put(data);
	 buffer.flip();
	 return buffer;
 }
 
}

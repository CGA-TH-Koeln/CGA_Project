package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;

public class Entity {

	private TexturedModel model;
	private Vector3f position;
	private float rotX;						//Rotation x y z
	private float rotY;
	private float rotZ;
	private float scale;					//Skalierung
	
	public Entity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super();
		this.model = model;
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
	}
	
	
	
	public void increasePosition(float dx, float dy, float dz) {
		this.position.x+=dx;
		this.position.y+=dy;
		this.position.z+=dz;
	}
	
	public void increaseRotation(float dx, float dy, float dz) {
		this.rotX+=dx;
		this.rotY+=dy;
		this.rotZ+=dz;
	}

	public TexturedModel getModel() {
		return model;
	}

	public void setModel(TexturedModel model) {
		this.model = model;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public float getRotX() {
		return rotX;
	}

	public void setRotX(float rotX) {
		this.rotX = rotX;
	}

	public float getRotY() {
		return rotY;
	}

	public void setRotY(float rotY) {
		this.rotY = rotY;
	}

	public float getRotZ() {
		return rotZ;
	}

	public void setRotZ(float rotZ) {
		this.rotZ = rotZ;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}
	
	//Bewegen der Entitäten
	public void move() {
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
			position.y+=0.0002f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
			position.x+=0.0002f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
			position.x-=0.0002f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
			position.y-=0.0002f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			position.z+=0.0002f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			position.z-=0.0002f;
		}
		
		
	}
	
	
}

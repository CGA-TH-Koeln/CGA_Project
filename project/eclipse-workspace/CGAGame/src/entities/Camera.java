package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class Camera {

	private Vector3f position = new Vector3f(-0.06f, -0.17999998f, 0.20000012f);
	private float pitch = -55f;
	private float yaw;
	private float roll;
	
	public Camera() {}
	
	
	public void move() {
		/*
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
			position.y-=0.02f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
			position.x+=0.02f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
			position.x-=0.02f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
			position.y+=0.02f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			position.z+=0.02f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			position.z-=0.02f;
		}*/
		if(Keyboard.isKeyDown(Keyboard.KEY_Z)) {
			if(position.y < -0.09599992f && position.z > 0.11599999f) {
				position.y+=0.002f;
				position.z-=0.002f;
			}	
		}else if(Keyboard.isKeyDown(Keyboard.KEY_U)) {
			if(position.y > -0.36940148f || position.z < 0.3894016f) {
				position.y-=0.002f;
				position.z+=0.002f;
			}
			
			//System.out.println(position);
			//System.out.println(Mouse.getDWheel());
		}
		
		
		
		if(Keyboard.isKeyDown(Keyboard.KEY_O)) {
			pitch+=0.051f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_L)) {
			pitch-=0.051f;
		}
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}
	
	
	
}

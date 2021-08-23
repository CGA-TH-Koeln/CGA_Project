package mainthing;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;


import entities.Camera;
import entities.Entity;
import entities.Light;
import models.RawModel;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;
import rendering.DisplayManager;
import rendering.Loader;
import rendering.MasterRenderer;
import textures.ModellTexture;
import toolbox.Spielregeln;

public class MainGame {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		
		
		  //Erstellen des Spielbrettes
		  ModelData dataBrett = OBJFileLoader.loadOBJ("Spielbrett");
		  RawModel modelBrett = loader.loadtoVAO(dataBrett.getVertices(), dataBrett.getTextureCoords(),dataBrett.getNormals() ,dataBrett.getIndices());
		  TexturedModel textBrett = new TexturedModel(modelBrett,new ModellTexture(loader.loadTexture("SpielfeldSchwarz")));
		  Entity entityBrett = new Entity(textBrett, new Vector3f(0,0,0),90,0,0,0.05f);
		  
		  //Würfel 
		  ModelData dataCube = OBJFileLoader.loadOBJ("wuerfel");
		  RawModel modelCube = loader.loadtoVAO(dataCube.getVertices(), dataCube.getTextureCoords(),dataCube.getNormals() ,dataCube.getIndices());
		  TexturedModel textCube = new TexturedModel(modelCube,new ModellTexture(loader.loadTexture("W0")));
		  Entity entityCube = new Entity(textCube,new Vector3f(0.04739993f, 0.01859998f, 0.030399967f),-90,180,-90,0.009f);
		  
		  //Erstellen der ersten Spielerfigur
		  ModelData dataFig1 = OBJFileLoader.loadOBJ("Spielfigur");
		  RawModel modelFig1 = loader.loadtoVAO(dataFig1.getVertices(), dataFig1.getTextureCoords(),dataFig1.getNormals() ,dataFig1.getIndices());
		  TexturedModel textFig1 = new TexturedModel(modelFig1,new ModellTexture(loader.loadTexture("red")));
		  Entity entityFig1 = new Entity(textFig1, new Vector3f(-0.0035999992f, -0.012199994f, 0.022199977f),90,0,0,0.0055f);
		  	
		  Light light = new Light(new Vector3f(-0.06f, -0.37000036f, 0.3900005f),new Vector3f(1.5f,1.5f,1.5f));
		  Camera camera = new Camera();
		  
		  boolean mouseButton1 = false;
		  boolean spacetast = false;
		  int aktuellePosi = -1; 	//-1 da mit dem ersten Auge [0] ist
		  
		  MasterRenderer renderer = new MasterRenderer();
		while(!Display.isCloseRequested()) {
			//entityBrett.increaseRotation(0, 1, 0);
			//entityCube.increaseRotation(0, 0, 0);
			//entityCube.move();
			entityFig1.move();
			camera.move();
			//System.out.println(camera.getPitch());
			//System.out.println(camera.getPosition());
			renderer.render(light, camera);
			renderer.processEntity(entityFig1);
			renderer.processEntity(entityBrett);
			renderer.processEntity(entityCube);
			//Spielregeln.steineErzeugen(loader,renderer); //restliche Figuren erzeugen
			aktuellePosi = Spielregeln.klick(entityCube,loader,entityFig1,mouseButton1,aktuellePosi); //Methode zum würfeln
			entityCube.getModel().getRawModel();
			
			
			//Hilfe Um Positionen der Punkte zu notieren
			if(Keyboard.isKeyDown(Keyboard.KEY_SPACE) && !spacetast) {
				//entityBrett.setModel(new TexturedModel(modelBrett,new ModellTexture(loader.loadTexture("red"))));
				//System.out.println(entity1.getPosition());
				System.out.println("new Vector3f("+entityFig1.getPosition().x +"f, "+entityFig1.getPosition().y+"f, "+entityFig1.getPosition().z+"f)");
				//System.out.println("new Vector3f("+entityCube.getPosition().x +"f, "+entityCube.getPosition().y+"f, "+entityCube.getPosition().z+"f)");
			}
				
		    // Damit der Klick nicht x-mal aufgerufen wird
			spacetast = Keyboard.isKeyDown(Keyboard.KEY_SPACE);
		    mouseButton1 = Mouse.isButtonDown(0);
		    
			DisplayManager.updateDisplay();
		}
		renderer.cleanUp();
		loader.CleanUp();
		DisplayManager.closeDisplay();
	}	

}

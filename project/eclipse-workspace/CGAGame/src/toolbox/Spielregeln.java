package toolbox;

import java.util.Random;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import models.TexturedModel;
import objConverter.OBJFileLoader;
import rendering.Loader;
import rendering.MasterRenderer;
import textures.ModellTexture;

public class Spielregeln {

	public static void steineErzeugen (Loader loader, MasterRenderer renderer) {
		int y = 1;		//Nur damit weniger erzeugt werden wegen performance 
		int i = 1;
		String modellFigur = "Spielfigur";
		String texturFigur = "";
			for(Vector3f posi:FeldPositionen.startPosis) {
				if(i <= 3) {
					texturFigur = "red";
				}else if(i >= 4 && i <= 7) {
					texturFigur = "blue";
				}else if(i >= 8 && i <= 11) {
					texturFigur = "green";
				}else if(i >= 12 && i <= 15) {
					texturFigur = "yellow";
				}
				
				i++;
				y++;
				
				/*
				if(y == 3) {
					y = 1;
					renderer.render(new Entity(new TexturedModel(loader.loadtoVAO(
							OBJFileLoader.loadOBJ(modellFigur).getVertices(), 
							OBJFileLoader.loadOBJ(modellFigur).getTextureCoords(),
							OBJFileLoader.loadOBJ(modellFigur).getNormals(),
							OBJFileLoader.loadOBJ(modellFigur).getIndices()),
							  new ModellTexture(loader.loadTexture(texturFigur))),
							  new Vector3f(posi.x, posi.y, posi.z),90,0,0,0.0055f),shader);
				}
				*/
				
				
				renderer.processEntity(new Entity(new TexturedModel(loader.loadtoVAO(
						OBJFileLoader.loadOBJ(modellFigur).getVertices(), 
						OBJFileLoader.loadOBJ(modellFigur).getTextureCoords(),
						OBJFileLoader.loadOBJ(modellFigur).getNormals(),
						OBJFileLoader.loadOBJ(modellFigur).getIndices()),
						  new ModellTexture(loader.loadTexture(texturFigur))),
						  new Vector3f(posi.x, posi.y, posi.z),90,0,0,0.0055f));
					  
						  
			}
		
	}
	
	public static int klick(Entity wuerfel,Loader loader ,Entity entity1, boolean mouseButton1,int i) { 
		
		Random rand = new Random();
		if (Mouse.isButtonDown(0) && !mouseButton1)
	    {    
			int n = rand.nextInt(6);
			n+=1; // zwischen 1-6
			System.out.println("Es wurde eine " +n+ " gewürfelt");
			//Switch um Textur zu wechseln, wenn gewürfelt wurde
			switch(n) {
			case 1:
				wuerfel.setModel(new TexturedModel(wuerfel.getModel().getRawModel(),new ModellTexture(loader.loadTexture("W1"))));
				break;
			case 2:
				wuerfel.setModel(new TexturedModel(wuerfel.getModel().getRawModel(),new ModellTexture(loader.loadTexture("W2"))));
				break;
			case 3:
				wuerfel.setModel(new TexturedModel(wuerfel.getModel().getRawModel(),new ModellTexture(loader.loadTexture("W3"))));
				break;
			case 4:
				wuerfel.setModel(new TexturedModel(wuerfel.getModel().getRawModel(),new ModellTexture(loader.loadTexture("W4"))));
				break;
			case 5:
				wuerfel.setModel(new TexturedModel(wuerfel.getModel().getRawModel(),new ModellTexture(loader.loadTexture("W5"))));
				break;
			case 6:
				wuerfel.setModel(new TexturedModel(wuerfel.getModel().getRawModel(),new ModellTexture(loader.loadTexture("W6"))));
				break;
			}
			
				//i+=n;
			
				//Durchlaufen
				/*
				if((i+n)>FeldPositionen.feldPosis.length-1) {
					i = i+n-FeldPositionen.feldPosis.length;
				}else {
					i+=n;
				}
				*/
			
				if(i >= 0) {							//Ob schon ein Schritt gegangen wurde
					if((i+n) >= 40 && (i+n) <= 43) {	//Ob das letzte Feld vor den farbigen Feldern erreicht wurde
						i += n;							//Aktuelle Schritte erhöhen
						int rest = i - 39 - 1;			//Restschritte berechnen / - Reguläre Felder - 1 (erste Posi = [0])
						entity1.setPosition(FeldPositionen.endPosis[rest]);
					}else if((i+n) < 40){				//
						i += n;
						entity1.setPosition(FeldPositionen.feldPosis[i]);
						
					}
				}else if(i == -1 && n == 6) {			//Noch kein Schritt und 6 gewürfelt
					i++;
					entity1.setPosition(FeldPositionen.feldPosis[i]);
				}
				
				
					
				
				System.out.println("Aktuelle Schritte: "+ (i+1));
				System.out.println();
				//entity1.setPosition(FeldPositionen.feldPosis[i]);    
	    }
		return i;
	}
	
	
	
}

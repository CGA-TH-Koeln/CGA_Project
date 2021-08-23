package models;

import textures.ModellTexture;

public class TexturedModel {

	private RawModel rawModel;
	private ModellTexture texture;
	
	public TexturedModel(RawModel model, ModellTexture texture) {
		this.rawModel = model;
		this.texture = texture;
	}

	public RawModel getRawModel() {
		return rawModel;
	}

	public ModellTexture getTexture() {
		return texture;
	}
	
}

package com.dao.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class LoseSign extends Actor
{
	private Texture image;
	
	public LoseSign() 
	{
		image = new Texture("Lose.jpeg");
	}
	@Override
	public void draw(Batch batch, float parentAlpha) {
			batch.draw(image, 190, 150, 650, 650);
	}

}

package com.dao.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class WinSign extends Actor
{
	private Texture image;
	
	public WinSign() 
	{
		image = new Texture("Win.png");
	}
	@Override
	public void draw(Batch batch, float parentAlpha) {
			batch.draw(image, 190, 150, 650, 650);
	}

}

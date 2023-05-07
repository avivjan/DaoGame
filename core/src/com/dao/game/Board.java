package com.dao.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Board extends Actor
{
	Texture boardImage;
	DaoGame daoGame;
	public Board()
	{
		boardImage = new Texture("Board4x4.png");
		daoGame = (DaoGame)Gdx.app.getApplicationListener();
	}
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(boardImage, 0, 0,daoGame.getPixelsOnWidth(), daoGame.getPixelsOnHeight());
	}

}

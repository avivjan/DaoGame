package com.dao.game;

import java.io.ObjectInputStream.GetField;

import com.badlogic.gdx.Gdx;

public class LocationInPixels
{
	private float x;
	private float y;
	private DaoGame daoGame;
	
	public LocationInPixels(float x, float y)
	{
		this.x = x;
		this.y = y;
		daoGame = (DaoGame)Gdx.app.getApplicationListener();
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public Cordinate GetCordinate()
	{
		return new Cordinate((int)(x/(daoGame.getPixelsOnWidth() / daoGame.getSquaresOnWidth())),
							((int)(y/(daoGame.getPixelsOnHeight() / daoGame.getSquaresOnHeight( )))));
	}

}

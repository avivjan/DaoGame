package com.dao.game;

import com.badlogic.gdx.Gdx;

public class Cordinate 
{
	private int x;
	private int y;
	private DaoGame daoGame;
		
	
	public Cordinate(Cordinate cor) {
		this.x = cor.x;
		this.y = cor.y;
		daoGame = (DaoGame)Gdx.app.getApplicationListener();
	}

	public Cordinate(int xCordinate, int yCordinate)
	{
		this.x = xCordinate;
		this.y = yCordinate;
		daoGame = (DaoGame)Gdx.app.getApplicationListener();
	}

	public int getX() 
	{
		return x;

	}
	
	public int getY() 
	{
		return y;

	}
	
	public LocationInPixels getPixelsToPutOn()
	{
		System.err.println("cordinate: " + this + "printed: " + daoGame.getPixelsOnWidth() / daoGame.getSquaresOnWidth() * this.getX()+ ", "
							+ daoGame.getPixelsOnHeight() / daoGame.getSquaresOnHeight() * (3 - this.getY()));
		
		return new LocationInPixels(daoGame.getPixelsOnWidth() / daoGame.getSquaresOnWidth() * this.getX(),
									daoGame.getPixelsOnHeight() / daoGame.getSquaresOnHeight() * (3 - this.getY()));
		
	}
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}

}

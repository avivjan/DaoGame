package com.dao.game;


import java.io.ObjectInputStream.GetField;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.actions.AddAction;

public class Cell 
{
	private Cordinate cordinate;
	private Piece pieceOnIt;
	private Texture optionImage;
	private Rectangle optionObject;
	private boolean isOption;

	public Cell(Cordinate cor, Piece piece)
	{
		this.cordinate = cor;
		isOption = false;
		pieceOnIt = piece;
		optionImage = new Texture("4grey.png");
		optionObject = new Rectangle();
		LocationInPixels pixelsToPutOn = cor.getPixelsToPutOn();
		optionObject.width = 230;
		optionObject.height = 230;
		optionObject.x = pixelsToPutOn.getX();
		optionObject.y = pixelsToPutOn.getY();
	}
	
	public Rectangle getOptionObject() {
		return optionObject;
	}
	public Texture getOptionImage()
	{
		return optionImage;
	}
	public void markAsOption() throws Exception
	{
		if (!isEmpty())
		{
			throw new Exception("You cannot markAsOption cell  " + cordinate + "because it is occupied with piece " + pieceOnIt.getId());
		}
		isOption = true;
	}
	public void unMarkAsOption() throws Exception
	{
		if (!isOption)
		{
			throw new Exception("You cannot unMarkAsOption cell  " + cordinate + "because it is not marked as optinal");
		}
		isOption = false;
	}
	public boolean isEmpty()
	{
		return pieceOnIt == null;
	}
	
	public Cordinate getCordinate()
	{
		return cordinate;
	}
	
	public Piece getPieceOnIt() {
		return pieceOnIt;
	}
	public boolean isOption() {
		return isOption;
	}
}

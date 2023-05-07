package com.dao.game;


import java.io.ObjectInputStream.GetField;
import java.util.concurrent.ExecutionException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.AddAction;

public class Cell extends Actor
{
	private Cordinate cordinate;
	private Piece pieceOnIt;
	private Texture optionImage;
	private float width = 230;
	private float height = 230;
	private boolean isOption;

	public Cell(Cordinate cor, Piece piece)
	{
		this.cordinate = cor;
		super.setX(cor.getX());
		super.setY(cor.getY());
		isOption = false;
		pieceOnIt = piece;
		optionImage = new Texture("4grey.png");
		this.setBounds(cor.getPixelsToPutOn().getX(), cor.getPixelsToPutOn().getY(), width, height);
		
	}
	@Override
	public void draw(Batch batch, float parentAlpha)
	{
		if (isOption)
		{
			batch.draw(optionImage, getX(), getY(), width, height);
		}
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
	
	public void putPieceOnIt(Piece piece) throws Exception 
	{
		if (!isEmpty())
		{
			throw new Exception("You cannot put Piece cell :" + cordinate + "because it is occupaied with piece with id: " + pieceOnIt.getId());
		}
		pieceOnIt = piece;

	}
	public void deletePeiceOnIt() {
		pieceOnIt = null;
	}
}

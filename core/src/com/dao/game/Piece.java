package com.dao.game;

import java.net.PasswordAuthentication;

import org.w3c.dom.Text;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Piece extends Actor
{
	private Cordinate cordinate;
	private boolean isHumanPiece;
	private boolean isSelected;
	private int id;
	private Texture image;
	private float width = 230;
	private float height = 230;
	private DaoGame daoGame;
	
	
		
	private static int counter = 0;
	
	
	public Piece(Cordinate cor, boolean _isHumanPiece) 
	{	
		super.setX(cor.getPixelsToPutOn().getX());
		super.setY(cor.getPixelsToPutOn().getY());
		cordinate = cor;
		isHumanPiece = _isHumanPiece;
		isSelected = false;
		id = ++counter;
		daoGame = (DaoGame)Gdx.app.getApplicationListener();
		if (_isHumanPiece)
		{
			image = new Texture(Gdx.files.internal("Black.png"));
		}
		else 
		{
			image = new Texture(Gdx.files.internal("Red.png"));
		}
	}

	
	@Override
	public void draw(Batch batch, float parentAlpha) 
	{
		batch.draw(image, super.getX(), super.getY(), width , height);
	}
	public int getId() {
		return id;
	}
	public Cordinate getLocation() {
		return new Cordinate(cordinate);
	}
	public Texture getImage() {
		return image;
	}
	public boolean isSelected() {
		return isSelected;
	}
	public boolean isHumanPiece() {
		return isHumanPiece;
	}
	public void setCordinate(Cordinate cordinate)
	{
		this.cordinate = cordinate;
	}
	
	public void select() throws Exception
	{
		if (isSelected) {throw new Exception("This piece is already selected");}
		if (!isHumanPiece) {throw new Exception("You cannot select a non-Human piece");}
		MovementManager movementManager = daoGame.getMovementManager();
		for (Cordinate cor:movementManager.getOptionalCordinatesForMovment(this))
		{
			daoGame.markCellAsOption(cor);
		}
		isSelected = true;
		daoGame.addSelected(this);
	}
	
	public void Deselect() throws Exception
	{
		if (!isSelected) {throw new Exception("This piece is not selected");}
		if (!isHumanPiece) {throw new Exception("You cannot Deselect a non-Human piece");}
		daoGame.MarkAllCellsAsNonOption();
		isSelected = false;
		daoGame.SetSelectedToNull();
	}
}

package com.dao.game;

import org.w3c.dom.Text;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Piece 
{
	private Cordinate location;
	private boolean isHumanPiece;
	private boolean isSelected;
	private int id;
	private Texture image;
	private Rectangle pieceObject;
	private DaoGame daoGame;
	
		
	private static int counter = 0;
	
	
	public Piece(Cordinate cor, boolean _isHumanPiece)
	{	
		location = cor;
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
		pieceObject = new Rectangle();
		LocationInPixels PixelsToPutOn = cor.getPixelsToPutOn();
		pieceObject.x = PixelsToPutOn.getX();
		pieceObject.y = PixelsToPutOn.getY();
		pieceObject.width = 230;
		pieceObject.height = 230;
		
	}
	public int getId() {
		return id;
	}
	public Cordinate getLocation() {
		return new Cordinate(location);
	}
	public Rectangle getRectangle() {
		return pieceObject;
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
	
	public void select() throws Exception
	{
		if (isSelected) {throw new Exception("This piece is already selected");}
		if (!isHumanPiece) {throw new Exception("You cannot select a non-Human piece");}
		DaoGame daoGame = (DaoGame)Gdx.app.getApplicationListener();
		MovementManager movementManager = daoGame.getMovementManager();
		for (Cordinate cor:movementManager.getOptionalCordinatesForMovment(this))
		{
			daoGame.markCellAsOption(cor);
		}
		isSelected = true;
	}
	
	public void Deselect() throws Exception
	{
		if (!isSelected) {throw new Exception("This piece is not selected");}
		if (!isHumanPiece) {throw new Exception("You cannot Deselect a non-Human piece");}
		daoGame.MarkAllCellsAsNonOption();
		isSelected = false;
	}
	//public void goToCell(Cell cell) throws Exception
	//{
	//	if (!cell.isEmpty())
	//	{
	//		throw new Exception("This piece (id = " + this.getId() + ") cannot go to cell: " + cell.getCordinate() +
	//							"because it is already occupied with piece " +cell.getPieceOnIt().getId());
	//	}
		
		
		
	//}
}

package com.dao.game;

import java.io.ObjectInputStream.GetField;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;
import javax.xml.stream.events.StartDocument;

import org.w3c.dom.TypeInfo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.AddAction;

public class MovementManager 
{
	private DaoGame daoGame;
	
	public MovementManager()
	{
		daoGame = (DaoGame)Gdx.app.getApplicationListener();
	}
	
	public void move(Piece piece, Cell destinationCell, float time) throws Exception
	{
		piece.addAction(Actions.moveTo(destinationCell.getCordinate().getPixelsToPutOn().getX(),destinationCell.getCordinate().getPixelsToPutOn().getY(), time));
		daoGame.deletePieceFromCell(piece);
		piece.setCordinate(destinationCell.getCordinate());
		daoGame.deleteSelectedIfExist();
		destinationCell.putPieceOnIt(piece);
	}
	
	
	
	public List<Cordinate> getOptionalCordinatesForMovment(Piece piece) 
	{
		List<Cordinate> optionalCordinatesForMovment = new ArrayList<Cordinate>();
		Cordinate locationCordinate = piece.getLocation();
		for (int xDirection = -1; xDirection <= 1; xDirection++) //  -1, 0, 1
		{
			for (int yDirection = -1; yDirection <= 1; yDirection++)
			{
				if (xDirection == 0 && yDirection == 0)
				{
					continue;
				}
				int iteration = 1;
				int xCordinateBeingChecked = locationCordinate.getX();
				int yCordinateBeingChecked = locationCordinate.getY();
				while(isLegalOption(new Cordinate(locationCordinate.getX() + xDirection * iteration, locationCordinate.getY() + yDirection * iteration)))
				{
					xCordinateBeingChecked = locationCordinate.getX() + xDirection * iteration;
					yCordinateBeingChecked = locationCordinate.getY() + yDirection * iteration;
					iteration++;
				}	
				if (!(xCordinateBeingChecked == locationCordinate.getX() && yCordinateBeingChecked == locationCordinate.getY()))
				{
					optionalCordinatesForMovment.add(new Cordinate(xCordinateBeingChecked, yCordinateBeingChecked));
				}
			}	
		}
		return optionalCordinatesForMovment;
		
	}
	
	
	private boolean isLegalOption(Cordinate cordinate)
	{
		return isLegalCor(cordinate.getX(), cordinate.getY()) && daoGame.isEmptyCell(cordinate);
	}
	
	private boolean isLegalCor(int x, int y)
	{
		return x >= 0 && 
				x < daoGame.getSquaresOnWidth() &&
				y >= 0 && 
				y  < daoGame.getSquaresOnHeight();
	}	
}

package com.dao.game;

import java.io.ObjectInputStream.GetField;
import java.lang.StackWalker.Option;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.ScreenUtils;

public class DaoGame extends ApplicationAdapter 
{
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Sprite boardBackground;
	private MovementManager movementManager;
	private Cell[][] board = new Cell[4][4];
	private Piece selecedPiece;
	
	final private int squaresOnWidth = 4;
	final private int squaresOnHeight = 4;
	final private int pixelsOnWidth = 960;
	final private int pixelsOnHeight = 960;
	
	
	


	
	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		boardBackground = new Sprite(new Texture("Board4x4.png"));
		camera.setToOrtho(false, 960, 960);	
		movementManager = new MovementManager();
		
		CreateCellsWithPieces();
	}

	@Override
	public void render () 
	{
		try 
		{
			camera.update();
			ScreenUtils.clear(255, 255, 255, 1);
			batch.setProjectionMatrix(camera.combined);
			if (Gdx.input.isTouched())
			{ 
				handleClick(new LocationInPixels(Gdx.input.getX(), Gdx.input.getY()));//In getY the origin is top!
			}
			batch.begin(); 
			draw();
			batch.end();
			
		}
		catch (Exception e) 
		{
			System.err.println("Render function ran into a prob: " + e.toString());
			
		}
		
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

	
	public int getPixelsOnHeight() {
		return pixelsOnHeight;
	}
	
	public int getPixelsOnWidth() {
		return pixelsOnWidth;
	}
	
	public int getSquaresOnWidth()
	{
		return squaresOnWidth;
	}
	
	public int getSquaresOnHeight()
	{
		return squaresOnHeight;
	}
	
	public MovementManager getMovementManager() 
	{
		return movementManager;
	}

	public boolean isEmptyCell(Cordinate cordinate)
	{
		return board[cordinate.getX()][cordinate.getY()].isEmpty();
	}
	
	public void markCellAsOption(Cordinate cordinate) throws Exception 
	{
		board[cordinate.getX()][cordinate.getY()].markAsOption();
		
	}
	public void MarkAllCellsAsNonOption() throws Exception
	{
		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				if(board[i][j].isOption())
				{
					board[i][j].unMarkAsOption();
				}
			}
		}
				
	}


	private void CreateCellsWithPieces()
	{	
		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				Cordinate cor = new Cordinate(i, j);
				if (i == j)
				{
					board[i][j] = new Cell(cor, new Piece(cor, true));
					continue;
					
				}
				
				if (i + j == 3)
				{
					board[i][j] = new Cell(cor, new Piece(cor, false));
					continue;
				}
				board[i][j] = new Cell(cor, null);
			}
		}
	}
	private void draw()
	{
		batch.draw(boardBackground, 0, 0,pixelsOnWidth, pixelsOnHeight);
		drawAllpieces();
	}
	
	private void handleClick(LocationInPixels locationOfClick) throws Exception
	{
		Cordinate clickCordinate =  locationOfClick.GetCordinate();
		Cell pressedCell = board[clickCordinate.getX()][clickCordinate.getY()];
		//if (pressedCell.isEmpty())
		//{
		//	if (pressedCell.isOption())
		//	{
				//Move
		//	}
		//	return;
		//}
		
		if (pressedCell.isEmpty())//TODO: Delete!!
		{
			return;
		}
		
		if (pressedCell.getPieceOnIt().isSelected())
		{
			pressedCell.getPieceOnIt().Deselect();
			return;
		}
		
		if (pressedCell.getPieceOnIt().isHumanPiece())
		{
			if (selecedPiece != null)
			{
				selecedPiece.Deselect();
			}
			pressedCell.getPieceOnIt().select();
			
		}	
	}
	
	private void drawAllpieces()
	{
		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				if (!isEmptyCell(new Cordinate(i,j)))
				{
					Piece pieceOnThisCell = board[i][j].getPieceOnIt();
					Rectangle recOfPieceOnThisCell = pieceOnThisCell.getRectangle();
					batch.draw(pieceOnThisCell.getImage(), recOfPieceOnThisCell.x, recOfPieceOnThisCell.y, recOfPieceOnThisCell.width ,recOfPieceOnThisCell.height);
				}
				if (board[i][j].isOption())
				{
					Rectangle  optionObject = board[i][j].getOptionObject();
					batch.draw(board[i][j].getOptionImage(), optionObject.x, optionObject.y, optionObject.width ,optionObject.height);
				}
				
			}
		}
		
	}

	
	
	
}

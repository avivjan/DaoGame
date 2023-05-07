package com.dao.game;

import java.io.ObjectInputStream.GetField;
import java.lang.StackWalker.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.management.loading.PrivateClassLoader;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.AddAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;



public class DaoGame extends ApplicationAdapter 
{
	private MovementManager movementManager;
	private Cell[][] board = new Cell[4][4];
	private Piece selecedPiece;
	private Stage stage;
	private List<Piece> computerPieces;
	
	final private int squaresOnWidth = 4;
	final private int squaresOnHeight = 4;
	final private int pixelsOnWidth = 960;
	final private int pixelsOnHeight = 960;
	

	@Override
	public void create () {
		movementManager = new MovementManager();
		computerPieces = new ArrayList<Piece>();
		stage = new Stage(new StretchViewport(960, 960));
		Gdx.input.setInputProcessor(stage);
		stage.addActor(new Board());
		CreateCellsWithPieces();
	}
	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void render () 
	{
		try 
		{
			ScreenUtils.clear(255, 255, 255, 1);
			float delta = Gdx.graphics.getDeltaTime();
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			stage.act(delta);
			stage.draw();
			
		}
		catch (Exception e) 
		{
			System.err.println("Render function ran into a prob: " + e.toString());
			
		}
		
	}
	
	@Override
	public void dispose () {
		
		stage.dispose();
	}
	
	public void SetSelectedToNull() 
	{
		selecedPiece = null;
	}
	public void addSelected(Piece piece)
	{
		selecedPiece = piece;
		
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
	public void deletePeiceOnSelecedCell() 
	{	
		if (selecedPiece != null)
		{ 
			Cordinate selectedLoc = selecedPiece.getLocation();
			board[selectedLoc.getX()][selectedLoc.getY()].deletePeiceOnIt();
		}
		
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
	public void deleteSelectedIfExist() throws Exception
	{
		if (selecedPiece != null)
		{ 
			selecedPiece.Deselect();
			SetSelectedToNull();
		}
	}
	public void deletePieceFromCell(Piece piece)
	{
		Cordinate cordinate = piece.getLocation();
		board[cordinate.getX()][cordinate.getY()].deletePeiceOnIt();
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
					Piece piece = new Piece(cor, true);
					Cell cell = new Cell(cor, piece);
					stage.addActor(piece);
					stage.addActor(cell);
					addCellListener(cell);
					board[i][j] = cell;
					continue;
				}
				
				if (i + j == 3)
				{
					Piece piece = new Piece(cor, false);
					Cell cell = new Cell(cor, piece);
					stage.addActor(piece);
					stage.addActor(cell);
					addCellListener(cell);
					board[i][j] = cell;
					computerPieces.add(piece);
					continue;
				}
				Cell cell = new Cell(cor, null);
				board[i][j] = cell;
				stage.addActor(cell);
				addCellListener(cell);
			}
		}
	}
	private <T> T get(List<T> list)
	{
		System.out.println(new Random().nextInt(list.size()));
		return list.get(new Random().nextInt(list.size()));
	}
	
	private void addCellListener(Cell cell) 
	{	
		cell.addListener(new InputListener()
		{
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) 
			{
				try 
				{
					Cell pressedCell = (Cell)event.getListenerActor();
					
					if (pressedCell.isEmpty())
					{
						if (pressedCell.isOption())
						{
							movementManager.move(selecedPiece, pressedCell, 0.1f);
							computerPlay();
						}
						return true;
					}
					
					if (pressedCell.getPieceOnIt().isSelected())
					{
						pressedCell.getPieceOnIt().Deselect();
						return true;
					}
					
					if (pressedCell.getPieceOnIt().isHumanPiece())
					{
						if (selecedPiece != null)
						{
							selecedPiece.Deselect();
						}
						pressedCell.getPieceOnIt().select();
						return true;
						
					}	
					return true;
					
				} 
				catch (Exception e)
				{
					System.err.println("Error in touchDown");
					e.printStackTrace();
					return true;
				}
				
			}});
	}
	private void computerPlay() throws Exception
	{
	    Piece computerPiece = get(computerPieces);
	    Cordinate targetcorCordinate = get(movementManager.getOptionalCordinatesForMovment(computerPiece));
	    movementManager.move(computerPiece, board[targetcorCordinate.getX()][targetcorCordinate.getY()], 1);
	}
	
	

}

package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.GlobalClasses.Assets;
import com.mygdx.game.Loading.LoadingScreen;
import com.mygdx.game.MyBaseClasses.MyScreen;

import java.lang.reflect.InvocationTargetException;
import java.util.Stack;

public class MyGdxGame extends Game {

	public final Stack<Class> backButtonStack = new Stack();

	public Label.LabelStyle getLabelStyle() {
		return getLabelStyle(100, Color.YELLOW);
	}

	public Label.LabelStyle getLabelStyle(int size) {
		return getLabelStyle(size, Color.YELLOW);
	}

	public Label.LabelStyle getLabelStyle(int size, Color color) {
		Label.LabelStyle style;
		style = new com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle();
		style.font = Assets.manager.get(Assets.ACMEREGULAR_FONT100);
		style.fontColor = color;
		switch (size){
			case 10:
				style.font = Assets.manager.get(Assets.ACMEREGULAR_FONT10);
				break;
			case 25:
				style.font = Assets.manager.get(Assets.ACMEREGULAR_FONT25);
				break;
			case 50:
				style.font = Assets.manager.get(Assets.ACMEREGULAR_FONT50);
				break;
			case 80:
				style.font = Assets.manager.get(Assets.ACMEREGULAR_FONT80);
				break;
			case 100:
				style.font = Assets.manager.get(Assets.ACMEREGULAR_FONT100);
				break;
			case 140:
				style.font = Assets.manager.get(Assets.ACMEREGULAR_FONT140);
				break;
			default:
				new Exception("Nem kezelt betűméret: " + size);
		}
		return style;
	}


/*
	public TextField.TextFieldStyle getTextFieldStyle() {
		TextField.TextFieldStyle style = new TextField.TextFieldStyle();
		style.background = new TextureRegionDrawable(new TextureRegion(Assets.manager.get(Assets.TEST_TEXTURE)));
		style.background.setLeftWidth(style.background.getLeftWidth()+20);
		style.background.setRightWidth(style.background.getRightWidth()+20);
		style.font = Assets.manager.get(Assets.ACMEREGULAR_FONT100);
		style.cursor = new TextureRegionDrawable(new TextureRegion(new TextureRegion(Assets.manager.get(Assets.TEST_TEXTURE))));
		style.cursor.setMinWidth(50);
		style.fontColor = Color.BLACK;
		Pixmap p = new Pixmap(1,1, Pixmap.Format.RGB888);
		p.setColor(0.4f,0.2f,0.8f, 0.5f);
		p.fill();
		style.selection = new TextureRegionDrawable(new TextureRegion(new Texture(p)));
		return style;
	}

*/
	public TextButton.TextButtonStyle getTextButtonStyle() {

		TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
		textButtonStyle.font = Assets.manager.get(Assets.ACMEREGULAR_FONT100);

		/*Pixmap p = new Pixmap(1,1, Pixmap.Format.RGB888);
		p.setColor(0.1f,0.2f,0.2f, 0.5f);
		p.fill();*/
		textButtonStyle.up = new TextureRegionDrawable(new TextureRegion(Assets.manager.get(Assets.TEXTBUTTONUP)));

		/*p.setColor(0.3f,0.5f,0.8f, 0.5f);
		p.fill();*/
		textButtonStyle.over = new TextureRegionDrawable(new TextureRegion(Assets.manager.get(Assets.TEXTBUTTONOVER)));

		/*p.setColor(1f,0.5f,0.8f, 1f);
		p.fill();*/
		textButtonStyle.down = new TextureRegionDrawable(new TextureRegion(Assets.manager.get(Assets.TEXTBUTTONDOWN)));

		return textButtonStyle;
	}


	@Override
	public void create () {
		Assets.prepare();
		setScreen(new LoadingScreen(this));
	}

	@Override
	public void resume() {
		super.resume();
		Assets.manager.update();
	}

	@Override
	public void dispose () {
		super.dispose();
		Assets.unload();
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void setScreen(Screen screen) {
		setScreen(screen,true);
	}

	public void setScreenBackByStackPop(){
		if (backButtonStack.size()>1){
			try {
				setScreen((MyScreen) backButtonStack.pop().getConstructor(MyGdxGame.class).newInstance(this),false);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		else
		{
			Gdx.app.exit();
		}
	}


	public void setScreen(Screen screen, boolean pushToStack) {
		Screen prevScreen = getScreen();
		if (prevScreen!=null) {
			if (pushToStack) {backButtonStack.push(prevScreen.getClass());}
			try {
				prevScreen.dispose();
			}
			catch (Exception e){
				e.printStackTrace();
			}
		}
		super.setScreen(screen);
	}

}

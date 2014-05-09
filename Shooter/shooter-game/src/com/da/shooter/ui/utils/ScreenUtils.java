package com.da.shooter.ui.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;

public class ScreenUtils {
	public static TextButton createButton(String label, float x, float y){
		TextureAtlas atlas = new TextureAtlas("gui/gui.pack");
		Skin skin = new Skin(atlas);
		
		TextButtonStyle buttonStyle = new TextButtonStyle();
		buttonStyle.up = skin.getDrawable("button1");
		buttonStyle.over = skin.getDrawable("button1-over");
		buttonStyle.down = skin.getDrawable("button1-down");
		buttonStyle.font = new BitmapFont(Gdx.files.internal("fonts/halo.fnt"),false);
		buttonStyle.font.setScale(0.5f);
		TextButton textButton = new TextButton(label, buttonStyle);
		textButton.setHeight(50f);
		textButton.setWidth(100f);
		textButton.setPosition(x, y);
		
		return textButton;
	}
	
	public static Label createLabel(String text,float x, float y){
		
		BitmapFont font = new BitmapFont(Gdx.files.internal("fonts/white30bold.fnt"));		
		Label label = new Label(text, new LabelStyle(font, Color.WHITE));
		label.setPosition(x,y);
		
		return label;
	}
}

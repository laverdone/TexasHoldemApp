package com.glm.texas.holdem.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.glm.texas.holdem.game.bean.Card;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class TexasHoldemGame extends ApplicationAdapter  implements InputProcessor {
	//private Vector3 touchPoint=new Vector3();
	private Rectangle rect;


	private Vector<Sprite> mMyCards = new Vector<Sprite>();
	private Game mGame;
	SpriteBatch batch;
	Texture img;
	OrthographicCamera camera;
	TextureRegion region;
	Sprite sprite;

	Sprite mBackCard1,mBackCard2,mBackCard3,mBackCard4,mBackCard5;
	private boolean isJustTouched=false;
	@Override
	public void create () {
		rect = new Rectangle(0, 0, 1, 1);
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		camera = new OrthographicCamera(1, h / w);
		batch = new SpriteBatch();
		img = new Texture("green_table.jpg");
		img.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		//region =
		//		new TextureRegion(img, 0, 0, 800, 420);
		sprite = new Sprite(img);
		sprite.setSize(w,h);
		sprite.setPosition(0,0);

		initMyCard(w,h);

		initEnemy(w,h,4);

		//,mCard2,mCard3,mCard4,mCard5
	}

	/**
	 * TODO get number of enemy
	 * */
	private void initEnemy(float w, float h,int enemyNumber) {
		mBackCard1 = new Sprite(new Texture("Cards/card-back.png"));
		mBackCard1.setPosition(h/2,(w/2)-(mBackCard1.getRegionWidth()/2)+300);
		mBackCard1.setSize(mBackCard1.getWidth()/2,mBackCard1.getHeight()/2);

		mBackCard2 = new Sprite(new Texture("Cards/card-back.png"));
		mBackCard2.setPosition(h/2+(mBackCard1.getWidth()/2),(w/2)-(mBackCard1.getRegionWidth()/2)+300);
		mBackCard2.setSize(mBackCard1.getWidth()/2,mBackCard1.getHeight()/2);

		mBackCard3 = new Sprite(new Texture("Cards/card-back.png"));
		mBackCard3.setPosition(h/2+(mBackCard2.getWidth()/2)+(mBackCard2.getWidth()/2),(w/2)-(mBackCard2.getRegionWidth()/2)+300);
		mBackCard3.setSize(mBackCard1.getWidth()/2,mBackCard1.getHeight()/2);

		mBackCard4 = new Sprite(new Texture("Cards/card-back.png"));
		mBackCard4.setPosition(h/2+(mBackCard3.getWidth()/2)+(mBackCard3.getWidth()/2)+(mBackCard3.getWidth()/2),(w/2)-(mBackCard3.getRegionWidth()/2)+300);
		mBackCard4.setSize(mBackCard1.getWidth()/2,mBackCard1.getHeight()/2);

		mBackCard5 = new Sprite(new Texture("Cards/card-back.png"));
		mBackCard5.setPosition(h/2+(mBackCard4.getWidth()/2)+(mBackCard4.getWidth()/2)+(mBackCard4.getWidth()/2)+(mBackCard4.getWidth()/2),(w/2)-(mBackCard4.getRegionWidth()/2)+300);
		mBackCard5.setSize(mBackCard5.getWidth()/2,mBackCard5.getHeight()/2);

	}

	/**
	 * TODO Get cards from Server
	 *
	 * @param w
	 * @param h*/
	private void initMyCard(float w, float h) {

		while(mGame==null
				|| mGame.getGamePlayer()==null
				|| mGame.getGamePlayer().getHand()==null){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		if(mGame!=null
				&& mGame.getGamePlayer()!=null
					&& mGame.getGamePlayer().getHand()!=null){
			HashMap<Integer, Card> mCards = mGame.getGamePlayer().getHand().displayAll();

			for (Map.Entry<Integer, Card> entry : mCards.entrySet()) {
				Integer key = entry.getKey();
				Card value = entry.getValue();
				mMyCards.add(new Sprite(new Texture("Cards/"+value.getCardRank()+"_"+value.getmNaturalSuit()+".png")));

			}
		}

		if(mMyCards.size()<5) return;
		mMyCards.get(0).setPosition(w/Const.MY_CARD_OFFSET,0);
		mMyCards.get(0).setSize(mMyCards.get(0).getWidth()/2,mMyCards.get(0).getHeight()/2);
		//mCard1.rotate90(true);
		//mCard2 = new Sprite(new Texture("Cards/120_clubs.png"));
		mMyCards.get(1).setPosition((w/Const.MY_CARD_OFFSET)+mMyCards.get(0).getWidth()/2,0);
		mMyCards.get(1).setSize(mMyCards.get(1).getWidth()/2,mMyCards.get(1).getHeight()/2);

		//mCard3 = new Sprite(new Texture("Cards/50_spades.png"));
		mMyCards.get(2).setPosition((w/Const.MY_CARD_OFFSET)+(mMyCards.get(1).getWidth()/2)+(mMyCards.get(1).getWidth()/2),0);
		mMyCards.get(2).setSize(mMyCards.get(2).getWidth()/2,mMyCards.get(2).getHeight()/2);

		//mCard4 = new Sprite(new Texture("Cards/10_hearts.png"));
		mMyCards.get(3).setPosition((w/Const.MY_CARD_OFFSET)+(mMyCards.get(2).getWidth()/2)+(mMyCards.get(2).getWidth()/2)+(mMyCards.get(2).getWidth()/2),0);
		mMyCards.get(3).setSize(mMyCards.get(3).getWidth()/2,mMyCards.get(3).getHeight()/2);

		//mCard5 = new Sprite(new Texture("Cards/100_diamonds.png"));
		mMyCards.get(4).setPosition((w/Const.MY_CARD_OFFSET)+(mMyCards.get(3).getWidth()/2)+(mMyCards.get(3).getWidth()/2)+(mMyCards.get(3).getWidth()/2)+(mMyCards.get(3).getWidth()/2),0);
		mMyCards.get(4).setSize(mMyCards.get(4).getWidth()/2,mMyCards.get(4).getHeight()/2);
	}


	@Override
	public void render () {
		isJustTouched = Gdx.input.justTouched();
		if(isJustTouched){
			processInput();
		}


		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		sprite.draw(batch);
		mMyCards.get(0).draw(batch);
		mMyCards.get(1).draw(batch);
		mMyCards.get(2).draw(batch);
		mMyCards.get(3).draw(batch);
		mMyCards.get(4).draw(batch);
		mBackCard1.draw(batch);
		mBackCard2.draw(batch);
		mBackCard3.draw(batch);
		mBackCard4.draw(batch);
		mBackCard5.draw(batch);
		//batch.draw(img, 0, 0);
		batch.end();
	}

	private void processInput() {


		Gdx.app.log("debug","x: "+Gdx.input.getX()+" y: "+(Gdx.graphics.getHeight()-Gdx.input.getY()));
		rect.setPosition(Gdx.input.getX(), Gdx.input.getY());
		for(int i = 0; i < mMyCards.size(); i++)
		{
			Gdx.app.log("debug","rect_y: "+mMyCards.get(i).getY()+" rect_x: "+mMyCards.get(i).getX());
			if(rect.overlaps(mMyCards.get(i).getBoundingRectangle()))
			{
				mMyCards.get(i).getBoundingRectangle().setSize(mMyCards.get(i).getBoundingRectangle().getWidth()*2,mMyCards.get(i).getBoundingRectangle().getHeight()*2);
			}
		}
	}

	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}

	public void setGame(Game game) {
		mGame = game;
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {


		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}

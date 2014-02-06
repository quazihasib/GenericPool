package com.example.genericpool2;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.color.Color;
import org.andengine.util.debug.Debug;
import android.view.Display;

public class MainActivity extends SimpleBaseGameActivity implements IOnSceneTouchListener
{

	static int CAMERA_WIDTH;
	static int CAMERA_HEIGHT; 
	public Camera mCamera;   
	public static Scene mScene;
	public static VertexBufferObjectManager vertexBufferObjectManager;
	public static MainActivity MainActivityInstace;
	
	public static BitmapTextureAtlas mBitmapTextureAtlasWhiteChalk;
	public static ITextureRegion mWhiteChalkTextureRegion;
	
	public static Sprite player;
	public static int bulletCount;
	
	Sprite sprite1, sprite2, sprite3;
	Sprite[] sp = new Sprite[2000];
	public static int spCounter;
	FruitPool pool;
	int i, j;
	
	@Override
	public EngineOptions onCreateEngineOptions() 
	{
		// TODO Auto-generated method stub
		MainActivityInstace = this;
		Display display = getWindowManager().getDefaultDisplay();
		CAMERA_HEIGHT = display.getHeight();
		CAMERA_WIDTH = display.getWidth();

		mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR,
				new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);
	}

	@Override
	protected void onCreateResources() 
	{
		// TODO Auto-generated method stub
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("iWriteGFX/");
		
		mBitmapTextureAtlasWhiteChalk = new BitmapTextureAtlas(this.getTextureManager(), 50, 50, TextureOptions.BILINEAR);
	
		mWhiteChalkTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlasWhiteChalk, this,
				"chalk2.png", 0, 0,  1, 1);
		
		mBitmapTextureAtlasWhiteChalk.load();
	}

	@Override
	protected Scene onCreateScene() 
	{
		// TODO Auto-generated method stub
		mScene = new Scene();
		mScene.setBackground(new Background(Color.BLUE));
		vertexBufferObjectManager = getVertexBufferObjectManager();
		
		pool = new FruitPool(mWhiteChalkTextureRegion);
		
		i=0;
		j=0;
		
		mScene.setOnSceneTouchListener(this);
		
//		mScene.registerUpdateHandler(new IUpdateHandler() 
//		{
//			
//			@Override
//			public void reset() 
//			{
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void onUpdate(float pSecondsElapsed) 
//			{
//				// TODO Auto-generated method stub
//				Debug.d("sp:"+spCounter);
//			}
//		});
		
		
		Rectangle rect = new Rectangle(300,  100, 100, 100, vertexBufferObjectManager)
		{
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, 
					final float pTouchAreaLocalX, final float pTouchAreaLocalY) 
			{
				if(pSceneTouchEvent.isActionDown())
				{
					i++;
					
					if(i==1)
					{
						runOnUpdateThread(new Runnable() 
						{
						    @Override
						    // to safely detach and re-attach the sprite
						    public void run()
						    {
						    	if(!(spCounter<=0))
						    	{
						    		pool.recyclePoolItem(sp[spCounter]);
						    		spCounter--;
						    		Debug.d("spCounter--:"+spCounter);
						    	}
						    }  
						});
					}
 				}
				else if(pSceneTouchEvent.isActionUp())
				{
					i=0;
				}
			
				return true;
			}
		};
		rect.setColor(Color.RED);
		mScene.registerTouchArea(rect);
		mScene.attachChild(rect);
		
		return mScene;
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) 
	{
		// TODO Auto-generated method stub
		if(pSceneTouchEvent.isActionDown())
		{
			Debug.d("Down");
			j++;
			if(j==1)
			{
				spCounter++;
	    	
				sp[spCounter] = pool.obtainPoolItem();
	    		sp[spCounter].setPosition(rand(10, CAMERA_WIDTH/2),rand(10, CAMERA_HEIGHT/2));
	    		
	    		if(!sp[spCounter].hasParent())
	    		{
	    			mScene.attachChild(sp[spCounter]);
	    			Debug.d("New Child created");
	    		}
	    		else
	    		{
	    			 
	    		}
	    		Debug.d("spCounter++:"+spCounter);
	    		
			}
			
		}
		else if(pSceneTouchEvent.isActionUp())
		{
			j=0;
		}
		
		return true;
	}
	
	
	public static int rand(int Min, int Max)
	{
		return Min + (int)(Math.random() * ((Max - Min) + 1));
	}

}

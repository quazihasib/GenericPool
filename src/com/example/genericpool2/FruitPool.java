package com.example.genericpool2;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.adt.pool.GenericPool;

public class FruitPool extends GenericPool<Sprite> 
{ 
	
	private final ITextureRegion mTextureRegion;
	
	public FruitPool(final ITextureRegion pFruitTextureRegion) 
	{
	    this.mTextureRegion = pFruitTextureRegion;
	}
	
	@Override
	protected Sprite onAllocatePoolItem() 
	{
	    return new Sprite(0, 0, this.mTextureRegion, MainActivity.vertexBufferObjectManager);
	}
	@Override
	protected void onHandleObtainItem(final Sprite pItem)
	{
	    pItem.reset();
	}
	@Override
	protected void onHandleRecycleItem(final Sprite pItem)
	{
	    pItem.setVisible(false);
	    pItem.setIgnoreUpdate(true);
	}
	// ===========================================================          
	// Methods          
	// ===========================================================  

	// ===========================================================          
	// Inner and Anonymous Classes          
	// ===========================================================  
	}
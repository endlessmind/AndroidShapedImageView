package com.example.androidshapedimageview.Classes;

import com.example.androidshapedimageview.View.ShapeImageView;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.widget.ImageView;

public abstract class RenderShape {
	
	protected Bitmap image = null;
	protected ShapeImageView view = null;
	public Paint paint;
	public Paint paintBorder;
	
	protected int borderWidth;
	public int canvasSize;
	
	public RenderShape(ShapeImageView v) {
		view = v;
		
 		paint = new Paint();
 		paint.setAntiAlias(true);

 		paintBorder = new Paint();
 		paintBorder.setAntiAlias(true);
		
	}
	

	
	public abstract void draw(Canvas canvas);
	
	public abstract void setBorderColor(int borderColor);
	
	public abstract void setBorderWidth(int width);
	
	public abstract void addShadow();
	
	public abstract void removeShadow();
	
	protected void invalidate() {
		if (view != null)
			view.invalidate();
	}

	protected Bitmap CenterCrop(Bitmap srcBmp) {
		int size;
		if (srcBmp.getWidth() >= srcBmp.getHeight()){
			size = srcBmp.getHeight();

		}else{
			size = srcBmp.getWidth();
		}
		
		return ThumbnailUtils.extractThumbnail(srcBmp, size, size);
		
	}
	
	protected Bitmap drawableToBitmap(Drawable drawable) {
		if (drawable == null) {
			return null;
		} else if (drawable instanceof BitmapDrawable) {
			return ((BitmapDrawable) drawable).getBitmap();
		}

		Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
		drawable.draw(canvas);

		return bitmap;
	}

}

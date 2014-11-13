package com.example.androidshapedimageview.View;

import com.example.androidshapedimageview.Classes.RenderShape;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.BlurMaskFilter;
import android.graphics.BlurMaskFilter.Blur;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Region;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.view.View;
import android.widget.ImageView;

public class OctagonShape extends RenderShape {
	
	Path MainPath = new Path();
	Path BorderPath = new Path();
	Paint shadow; 
	
	public OctagonShape(ShapeImageView v) {
		super(v);
		shadow = new Paint(Paint.ANTI_ALIAS_FLAG);
		shadow.setColor(Color.WHITE); //Dummy color
	}

	
	@Override
	public void draw(Canvas canvas) {
		image = CenterCrop(drawableToBitmap(view.getDrawable())); // we center-crop to maintain the aspect ratio of the image
		
		
		if (image != null) {

			canvasSize = canvas.getWidth();
			if(canvas.getHeight()<canvasSize)
				canvasSize = canvas.getHeight();

			BitmapShader shader = new BitmapShader(Bitmap.createScaledBitmap(image, canvasSize, canvasSize, false), Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
			
			// init shader
			if (paint != null)
				paint.setShader(shader);
			
			// circleCenter is the x or y of the view's center
			// radius is the radius in pixels of the cirle to be drawn
			// paint contains the shader that will texture the shape
			int circleCenter = canvasSize / 2;
			float radius = ((canvasSize - (borderWidth * 2)) / 2) - 4.0f;
			float Borderradius = ((canvasSize - (borderWidth * 2)) / 2) + borderWidth - 4.0f;

			
			
			float StartX = (float)circleCenter + (float)radius*(float)Math.cos(0*2*Math.PI/8);
			float StartY = (float)circleCenter + (float)radius*(float)Math.sin(0*2*Math.PI/8);
			float BStartX = (float)circleCenter + (float)Borderradius*(float)Math.cos(0*2*Math.PI/8);
			float BStartY = (float)circleCenter + (float)Borderradius*(float)Math.sin(0*2*Math.PI/8);
			MainPath.moveTo(StartX, StartY);
			BorderPath.moveTo(BStartX, BStartY);
			//Let's create the other points of the shape
			for(int i=1; i<8; i++) {
				
				float X = (float)circleCenter + (float)radius*(float)Math.cos(i*2*Math.PI/8);
				float Y = (float)circleCenter + (float)radius*(float)Math.sin(i*2*Math.PI/8);
				
				float bX = (float)circleCenter + (float)Borderradius*(float)Math.cos(i*2*Math.PI/8);
				float bY = (float)circleCenter + (float)Borderradius*(float)Math.sin(i*2*Math.PI/8);
				MainPath.lineTo(X,  Y);
				BorderPath.lineTo(bX,  bY);
			}
			//and back to the startingpoint to close the shape
			MainPath.moveTo(StartX, StartY);
			BorderPath.moveTo(BStartX, BStartY);
			
			

			if (view.hasShadow)
				canvas.drawPath(BorderPath, shadow);


			//We draw the paths
			//We start with the border
			canvas.clipPath(BorderPath, Region.Op.INTERSECT);
			canvas.drawPaint(paintBorder);
			//and then we do the image is self.
			canvas.clipPath(MainPath, Region.Op.INTERSECT);
			canvas.drawPaint(paint);
			


			canvas.save();
			
		}
		
	}
	
	@Override
	public void setBorderColor(int borderColor) {
		if (paintBorder != null)
			paintBorder.setColor(borderColor);
		
		invalidate();
	}
	
	@Override
	public void setBorderWidth(int width) {
		borderWidth = width;
		
		view.requestLayout();
		invalidate();
	}
	
	@Override
	public void addShadow() {
		view.setLayerType(View.LAYER_TYPE_SOFTWARE, shadow);
		shadow.setShadowLayer(4.0f, 0.0f, 2.0f, Color.BLACK);
		
		invalidate();
	}
	
	@Override
	public void removeShadow() {
		view.setLayerType(View.LAYER_TYPE_SOFTWARE, shadow);
		shadow.setShadowLayer(0f, 0.0f, 2.0f, Color.BLACK);
		
		invalidate();
	}
	



	
	





}

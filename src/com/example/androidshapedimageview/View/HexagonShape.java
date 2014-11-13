package com.example.androidshapedimageview.View;

import com.example.androidshapedimageview.Classes.RenderShape;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.graphics.Shader;
import android.view.View;
import android.widget.ImageView;


public class HexagonShape extends RenderShape {
	
	Path MainPath = new Path();
	Path BorderPath = new Path();
	Paint shadow; 

	public HexagonShape(ShapeImageView v) {
		super(v);
		shadow = new Paint(Paint.ANTI_ALIAS_FLAG);
		shadow.setColor(Color.WHITE); //Dummy color
	}

	@Override
	public void draw(Canvas canvas) {
		image = CenterCrop(drawableToBitmap(view.getDrawable())); // we center-crop to maintain the aspect ratio of the image
		
		// init shader
		if (image != null) {

			canvasSize = canvas.getWidth();
			if(canvas.getHeight()<canvasSize)
				canvasSize = canvas.getHeight();

			BitmapShader shader = new BitmapShader(Bitmap.createScaledBitmap(image, canvasSize, canvasSize, false), Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
			
			if (paint != null)
				paint.setShader(shader);

			// circleCenter is the x or y of the view's center
			// radius is the radius in pixels of the cirle to be drawn
			// paint contains the shader that will texture the shape
			int circleCenter = canvasSize / 2;
			float radius = ((canvasSize - (borderWidth * 2)) / 2) - 4.0f;
			float Borderradius = ((canvasSize - (borderWidth * 2)) / 2) + borderWidth - 4.0f;

			
			
			float StartX = (float)circleCenter + (float)radius*(float)Math.cos(0*2*Math.PI/6);
			float StartY = (float)circleCenter + (float)radius*(float)Math.sin(0*2*Math.PI/6);
			float BStartX = (float)circleCenter + (float)Borderradius*(float)Math.cos(0*2*Math.PI/6);
			float BStartY = (float)circleCenter + (float)Borderradius*(float)Math.sin(0*2*Math.PI/6);
			MainPath.moveTo(StartX, StartY);
			BorderPath.moveTo(BStartX, BStartY);
			//Let's create the other points of the shape
			for(int i=1; i<6; i++) {
				
				float X = (float)circleCenter + (float)radius*(float)Math.cos(i*2*Math.PI/6);
				float Y = (float)circleCenter + (float)radius*(float)Math.sin(i*2*Math.PI/6);
				
				float bX = (float)circleCenter + (float)Borderradius*(float)Math.cos(i*2*Math.PI/6);
				float bY = (float)circleCenter + (float)Borderradius*(float)Math.sin(i*2*Math.PI/6);
				MainPath.lineTo(X,  Y);
				BorderPath.lineTo(bX,  bY);
			}
			//and back to the startingpoint to close the shape
			MainPath.moveTo(StartX, StartY);
			BorderPath.moveTo(BStartX, BStartY);
			

			//Before we draw, lets rotate the canvas so we get a hexagon that's "standing up"
			//Because 0 degrees on Androids canvas, is -90 degrees to what we want it to be.
			//We want 0 degrees to be at the top, not on the left side. It's needed for this shape, but for e.i a Hexagon it would be.
			canvas.rotate(90, circleCenter, circleCenter);
			
			//First we draw our shadow
			if (view.hasShadow)
				canvas.drawPath(BorderPath, shadow);
			//Now we draw the paths
			//We start with the border
			canvas.clipPath(BorderPath, Region.Op.INTERSECT);
			canvas.drawPaint(paintBorder);
			//and then we do the image is self.
			canvas.clipPath(MainPath, Region.Op.INTERSECT);
			//Rotate it back and then draw the image, or else the image will be on it's side
			canvas.rotate(-90, circleCenter, circleCenter);
			canvas.drawPaint(paint);
			//and now we rotate it back, so that 0 degrees is at the top.
			canvas.rotate(90, circleCenter, circleCenter);
			
			
			
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

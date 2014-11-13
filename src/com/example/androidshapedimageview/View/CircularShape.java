package com.example.androidshapedimageview.View;

import com.example.androidshapedimageview.Classes.RenderShape;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.view.View;
import android.widget.ImageView;


public class CircularShape extends RenderShape {
	


	public CircularShape(ShapeImageView v) {
		super(v);
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
			int circleCenter = (canvasSize - (borderWidth * 2)) / 2;
			canvas.drawCircle(circleCenter + borderWidth, circleCenter + borderWidth, ((canvasSize - (borderWidth * 2)) / 2) + borderWidth - 4.0f, paintBorder);
			canvas.drawCircle(circleCenter + borderWidth, circleCenter + borderWidth, ((canvasSize - (borderWidth * 2)) / 2) - 4.0f, paint);
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
		view.setLayerType(View.LAYER_TYPE_SOFTWARE, paintBorder);
		paintBorder.setShadowLayer(4.0f, 0.0f, 2.0f, Color.BLACK);
		
		invalidate();
	}

	@Override
	public void removeShadow() {
		view.setLayerType(View.LAYER_TYPE_SOFTWARE, paintBorder);
		paintBorder.setShadowLayer(0f, 0.0f, 2.0f, Color.BLACK);
		
		invalidate();
	}





}

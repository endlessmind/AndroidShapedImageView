package com.example.androidshapedimageview.View;

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

import com.example.androidshapedimageview.Classes.RenderShape;

public class TriangleShape extends RenderShape {
	
	Path MainPath = new Path();
	Path BorderPath = new Path();
	Paint shadow; 
	
	public TriangleShape(ShapeImageView v) {
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

			
			
			//Let's create the other points of the shape
			//We leave a little room for the border
			
			//Top mid
			MainPath.moveTo(canvasSize / 2 ,2 + borderWidth);
			BorderPath.moveTo(canvasSize / 2 - borderWidth, 2);
			
			//Bottom left
			MainPath.lineTo(borderWidth * 2,  canvasSize - (40 + borderWidth));
			BorderPath.lineTo(0,  canvasSize - 40);
			
			//Bottom right
			MainPath.lineTo(canvasSize  - borderWidth,  canvasSize - (40 + borderWidth) );
			BorderPath.lineTo(canvasSize + borderWidth ,  canvasSize - 40 );
			
			

			//and back to the startingpoint to close the shape
			MainPath.lineTo(canvasSize / 2,2 + borderWidth);
			BorderPath.lineTo(canvasSize / 2  + borderWidth   , 2);
			
			if (view.hasShadow)
				canvas.drawPath(BorderPath, shadow);
			

			//Now we draw the paths
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

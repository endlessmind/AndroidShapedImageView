package com.example.androidshapedimageview.View;



import com.example.androidshapedimageview.R;
import com.example.androidshapedimageview.Classes.RenderShape;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.graphics.Region;
import android.media.ThumbnailUtils;

public class ShapeImageView extends ImageView {
	
	private RenderShape renderShape;
	private TypedArray attributes;
	public boolean hasShadow = false;
	Context c;
	
public ShapeImageView(Context context) {
	this(context, null);
}

public ShapeImageView(Context context, AttributeSet attrs) {
	this(context, attrs, R.attr.ShapeImageViewStyle);
}

public ShapeImageView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    c = context;
    


 		// load the styled attributes and set their properties
 		attributes = context.obtainStyledAttributes(attrs, R.styleable.ShapeImageView, defStyle, 0);
 		
 		String shape = attributes.getString(R.styleable.ShapeImageView_shape);
 		shape = shape == null ? "circular" : shape; //If it's null, we set 'circular' as the standard shape
 		
 		if (shape.equals("circular"))
 			renderShape = new CircularShape(this);  //Invalidation is handled by the RenderShape to give you more control
 		else if (shape.equals("octagon"))
 			renderShape = new OctagonShape(this);
 		else if (shape.equals("hexagon"))
 			renderShape = new HexagonShape(this);

 		loadAttributes();
 		

}

protected void loadAttributes() {
	int defaultBorderSize = (int) (4 * getContext().getResources().getDisplayMetrics().density + 0.5f);
 	if(attributes.getBoolean(R.styleable.ShapeImageView_border, true)) {
 			
 		setBorderWidth(attributes.getDimensionPixelOffset(R.styleable.ShapeImageView_border_width, defaultBorderSize));
 		setBorderColor(attributes.getColor(R.styleable.ShapeImageView_border_color, Color.WHITE));
 	}
 	
 	hasShadow = attributes.getBoolean(R.styleable.ShapeImageView_shadow, false);

 	if(hasShadow)
 		addShadow();
}

public void setShape(RenderShape shape) { //Maybe we set a custom shape that's undefinable via XML?
	renderShape = shape;
	loadAttributes();
}

public RenderShape getShape() {
	return renderShape;
}

public void setBorderWidth(int borderWidth) {
	if (renderShape != null)
 		renderShape.setBorderWidth(borderWidth);
}

public void setBorderColor(int borderColor) {
	if (renderShape != null)
		renderShape.setBorderColor(borderColor);
}

public void addShadow() {
	if (renderShape != null)
		renderShape.addShadow();
}

public void removeShadow() {
	if (renderShape != null)
		renderShape.removeShadow();
}



@Override
protected void onDraw(Canvas canvas) {
	if (renderShape != null)
		renderShape.draw(canvas);
}

@Override
protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	int width = measureWidth(widthMeasureSpec);
	int height = measureHeight(heightMeasureSpec);
	setMeasuredDimension(width, height);
}

private int measureWidth(int measureSpec) {
	int result = 0;
	int specMode = MeasureSpec.getMode(measureSpec);
	int specSize = MeasureSpec.getSize(measureSpec);

	if (specMode == MeasureSpec.EXACTLY) {
		// The parent has determined an exact size for the child.
		result = specSize;
	} else if (specMode == MeasureSpec.AT_MOST) {
		// The child can be as large as it wants up to the specified size.
		result = specSize;
	} else {
		// The parent has not imposed any constraint on the child.
		if (renderShape != null)
			result = renderShape.canvasSize;
	}

	return result;
}

private int measureHeight(int measureSpecHeight) {
	int result = 0;
	int specMode = MeasureSpec.getMode(measureSpecHeight);
	int specSize = MeasureSpec.getSize(measureSpecHeight);

	if (specMode == MeasureSpec.EXACTLY) {
		// We were told how big to be
		result = specSize;
	} else if (specMode == MeasureSpec.AT_MOST) {
		// The child can be as large as it wants up to the specified size.
		result = specSize;
	} else {
		// Measure the text (beware: ascent is a negative number)
		if (renderShape != null)
			result = renderShape.canvasSize;
	}

	return (result + 2);
}




}

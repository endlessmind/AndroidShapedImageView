package com.example.androidshapedimageview;

import com.example.androidshapedimageview.View.CircularShape;
import com.example.androidshapedimageview.View.HexagonShape;
import com.example.androidshapedimageview.View.OctagonShape;
import com.example.androidshapedimageview.View.ShapeImageView;
import com.example.androidshapedimageview.View.TriangleShape;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity  {
	
	private ShapeImageView img;
	private Button btnCir, btnHex, btnOct, btnTri;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		img = (ShapeImageView) findViewById(R.id.shapeImageView2);
		btnCir = (Button) findViewById(R.id.btnCir);
		btnHex = (Button) findViewById(R.id.btnHex);
		btnOct = (Button) findViewById(R.id.btnOct);
		btnTri = (Button) findViewById(R.id.btnTri);
		
		btnCir.setOnClickListener(btnClick);
		btnHex.setOnClickListener(btnClick);
		btnOct.setOnClickListener(btnClick);
		btnTri.setOnClickListener(btnClick);
		
		
	}
	
	private OnClickListener btnClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (v.equals(btnCir))
				img.setShape(new CircularShape(img));
			
			if (v.equals(btnHex))
				img.setShape(new HexagonShape(img));
			
			if (v.equals(btnOct))
				img.setShape(new OctagonShape(img));
			
			if (v.equals(btnTri))
				img.setShape(new TriangleShape(img));

			
		}
		
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

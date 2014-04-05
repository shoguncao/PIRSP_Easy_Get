package com.example.pirsp_easy_get;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		PirspLog.GetInstance().log("******************************LOG BEGIN******************************");
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ImageView imgViewPublish = (ImageView)findViewById(R.id.imgViewPublish);
		imgViewPublish.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				View popupView = getLayoutInflater().inflate(R.layout.activity_popup_publish, null);
				PopupWindow popupWindow = new PopupWindow(popupView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);
				popupWindow.setTouchable(true);
				popupWindow.setOutsideTouchable(true);
				popupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
				popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, v.getHeight()+40);
				
				Button openAlbum = (Button)popupView.findViewById(R.id.action_openAlbum);
				openAlbum.setOnClickListener(new Button.OnClickListener() {
					public void onClick(View v) {
						Intent intent = new Intent();
						intent.setClass(MainActivity.this, OpenAlbumActivity.class);
						startActivity(intent);
					}
				});
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		switch (itemId) {
			case R.id.action_exit:
				this.finish();
				break;
			default:
				break;
		}
		return true;
	}
}

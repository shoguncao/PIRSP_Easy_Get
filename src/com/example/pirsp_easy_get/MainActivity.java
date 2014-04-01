package com.example.pirsp_easy_get;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		PirspLog.GetInstance().log("******************************LOG BEGIN******************************");
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button btnOpenAlbum = (Button)findViewById(R.id.btnOpenAlbum);
		btnOpenAlbum.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, OpenAlbumActivity.class);
				startActivity(intent);
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

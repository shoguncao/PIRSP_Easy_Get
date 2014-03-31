package com.example.pirsp_easy_get;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class OpenAlbumActivity extends Activity {
	
	public static  int REQUEST_CODE = 0x123;
	protected Bitmap bitmap;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType("image/*");
		startActivityForResult(intent, REQUEST_CODE);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (REQUEST_CODE == requestCode) {
			if (null != data) {
				Uri uri = data.getData();
				try {
					byte[] content = readStream(getContentResolver().openInputStream(Uri.parse(uri.toString())));
					bitmap = getPicFromBytes(content, null);
					PirspLog.GetInstance().log("response open album, requestCode:%d, data length:%d", REQUEST_CODE, content.length);
					
					onImageSelected();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					PirspLog.GetInstance().log("response open album, requestCode:%d, get exception:%s", REQUEST_CODE, e.getMessage());
				}
			} else {
				PirspLog.GetInstance().log("response open album, requestCode:%d, data length:0", REQUEST_CODE);
			}
		}
	}
	
	protected void onImageSelected() {
		setContentView(R.layout.activity_open_album);
		
		if (null != bitmap) {
			ImageView imageView = (ImageView)findViewById(R.id.bitmapView);
			imageView.setImageBitmap(bitmap);
		}
	}
	
	protected static byte[] readStream(InputStream inStream) throws Exception { 
        byte[] buffer = new byte[1024]; 
        int len = -1; 
        ByteArrayOutputStream outStream = new ByteArrayOutputStream(); 
        while ((len = inStream.read(buffer)) != -1) { 
                 outStream.write(buffer, 0, len); 
        } 
        byte[] data = outStream.toByteArray(); 
        outStream.close(); 
        inStream.close(); 
        return data; 
	}
	
	protected static Bitmap getPicFromBytes(byte[] bytes, BitmapFactory.Options opts) { 
        if (bytes != null) 
            if (opts != null) 
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length,opts); 
            else 
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length); 
        return null; 
	}
}

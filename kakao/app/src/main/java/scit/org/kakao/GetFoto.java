package scit.org.kakao;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by kotra on 2017-06-20.
 */

public class GetFoto extends AsyncTask<String,String,Bitmap> {
    public Activity mContext;
    Bitmap bm;
    public GetFoto(Activity context){
        this.mContext=context;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        ImageView iv=(ImageView)mContext.findViewById(R.id.image_foto);
        iv.setImageBitmap(bm);
        super.onPostExecute(bitmap);
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        String url="http://mud-kage.kakao.co.kr/14/dn/btqgFs8pUa7/ztkpcXbwQE7C51ak6K4EC1/o.jpg";
        try {
            URLConnection conn = new URL(url).openConnection();
            conn.connect();
            BufferedInputStream bis = new BufferedInputStream(conn.getInputStream(), 8192);
            bm = BitmapFactory.decodeStream(bis);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}

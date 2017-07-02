package scit.org.kakao;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Statistic extends Activity {
	WebView webView;
	Activity activity;
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity=this;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

		activity.setTheme(R.style.NO12);
		String id=(String)getIntent().getSerializableExtra("id");
		setContentView(R.layout.stat);
		webView=(WebView)findViewById(R.id.webview);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.loadUrl("http://52.78.115.181/stat/"+id+"/all");
		urlTask task=new urlTask();
		task.execute(id);
	}
}



class urlTask extends AsyncTask<String,String,Long> {


	@Override
	protected Long doInBackground(String... params) {

		Log.e("main","doinBack"+params[0]+"");
		Log.e("main","Length"+params.length+"");

		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder()
				.url("http://52.78.115.181/stat/"+"nameaneee")
				.build();
		try {
			Response response = client.newCall(request).execute();
			Log.e("main",""+response);

		} catch (IOException e) {
			e.printStackTrace();
		}



		return null;
	}
}




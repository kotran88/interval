package scit.org.kakao;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.kakao.auth.ErrorCode;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends Activity {
	final String PREFS_NAME = "MyPrefsFile";
	private Activity activity;
	int count;
	int fin;
	private View view;
	private View view_configure;
	static public StableArrayAdapter listviewadapter;
	EditText LeftTimeRest;
	int fin_first;
	EditText LeftTime;
	private Handler mHandler;
	private Handler mHandler_rest;
	private Runnable mRunnable;
	Button button;
	static DynamicListView listView;
	int position;
	Spinner spinner;
	static  ArrayList<Exercise> list_result;
	EditText timer;
	TextToSpeech t1;
	int restingTime;
	boolean flag=false;
	int position_first;
	Exercise exercise=new Exercise();
	public ArrayList<Exercise> list_exercise;
	static ArrayList<Exercise> list_exercise_pass;
	DbHelper helper;
	static SQLiteDatabase db;
	View lay;
	ToggleButton interval_toggle;
	ToggleButton sound_toggle;
	View view_main;
	boolean sound=true;
	boolean interval_to=true;
	int interval_time;
	View view_list;
	private int totalHeight;
	View lay_direct;
	RadioButton five_second;
	RadioButton ten_second;
	RadioButton twenty_second;
	RadioButton thirty_second;
	RadioButton direct;
	private View view_helper;
	Dialog help;
	Window window2;
	int result;
	Button btn_next;
	private Button btn_end;
	Vibrator vibrate;
	private ToggleButton screen_toggle;
	boolean screen_on=false;
	private SharedPreferences screen;
	private ArrayList<String> list_exerciseTitle=new ArrayList<>();
	private ArrayList<Integer> list_exerciseTime=new ArrayList<>();
	private LinearLayout lin_back;
	private String kakaoNickname;

	public enum Rest{SUPERLEAST("10초"),LEAST("20초"),MIDIUM("30초"),LAGEST("40초"),SUPERLARGE("50초");
		private String span;
		Rest(String months){
			span=months;
		}
		public String getSpan(){
			return span;
		}

	};


	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_list,menu);

		return super.onCreateOptionsMenu(menu);

	}

	public void delete_id(ArrayList<Exercise> list){
		Log.e("main","지운 리스트 "+list);

		Log.e("main","sss"+listviewadapter);
		listviewadapter.setmIdMap(list);
		listviewadapter.notifyDataSetChanged();
	}
/*
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()){
			case R.id.exercise_plus :
				Log.e("main", "운동 추가 ");
				view=activity.getLayoutInflater().inflate(R.layout.exer_config,null);
				final Dialog listViewDialog=new Dialog(activity);
				// 리스트뷰 설정된 레이아웃
				listViewDialog.setContentView(view);

				Button bt=(Button)view.findViewById(R.id.Exer_start);
				Window window = listViewDialog.getWindow();
				window.setLayout(lin_back.getWidth(), lin_back.getHeight()*9/10);

//
				bt.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						EditText exer_time_min=(EditText)view.findViewById(R.id.exer_time_minute);
						EditText exer_time_sec=(EditText)view.findViewById(R.id.exer_time_second);
						int total_time=0;
						int min=0;
						int sec=0;
						if(exer_time_min.getText().toString().equals("")){
							sec=Integer.parseInt(exer_time_sec.getText().toString());

							total_time=sec;
							//초만입력했을때
						}else if(exer_time_sec.getText().toString().equals("")){
							//분만입력했을때,
							min=Integer.parseInt(exer_time_min.getText().toString())*60;
							total_time=min;
						}else{
							//분초 모두입력시
							min=Integer.parseInt(exer_time_min.getText().toString())*60;
							sec=Integer.parseInt(exer_time_sec.getText().toString());
							total_time=min+sec;
						}

						Log.e("main","분,초 : "+total_time);


						EditText exer_title=(EditText)view.findViewById(R.id.exer_title);
						EditText exer_rest=(EditText)view.findViewById(R.id.exer_rest);
						EditText exer_interval=(EditText)view.findViewById(R.id.exer_interval);
						if(exer_title.getText().length()==0){
							Log.e("main","입력해주세요");
						}else{

							Log.e("main","운동 시작 버튼 클릭"+exer_title.getText()+exer_interval.getText());
							Log.e("main","clicked!");
							String query="INSERT INTO exercise " +
									"(title,time,rest,interval,selected) "+
									"VALUES('" +exer_title.getText().toString()+ "' , '"+total_time+"','"+exer_rest.getText()+"','"+exer_interval.getText()+"','false')";
							db.execSQL(query);
							Log.e("main", "회원가입완료");

							listViewDialog.dismiss();


							Exercise ex=new Exercise(Integer.parseInt(exer_interval.getText().toString() ),Integer.parseInt(exer_rest.getText().toString()),
									total_time, exer_title.getText().toString(),false);
							list_exercise.add(ex);
							listviewadapter.setmIdMap(list_exercise);
							listviewadapter.notifyDataSetChanged();
							//listviewadapter.setExercise_List(list_exercise);
							//listviewadapter.setTitle(exer_title.getText().toString());


						}

					}
				});

				// 다이얼로그 보기
				listViewDialog.show();


				break;
			case R.id.exercise_play:
				 list_result=new ArrayList<>();
				for(int i=0; i<list_exercise.size(); i++){
					if(list_exercise.get(i).isSelected()==true){
						list_result.add(list_exercise.get(i));

					}
				}

				Log.e("main","총 플레이할 것은 : "+list_result);

				if(list_result.size()==0){
					Log.e("main","no list error");
					Toast.makeText(getApplicationContext(), "시작할 운동을 선택해주세요",Toast.LENGTH_SHORT).show();
				}else {
					Log.e("main", sound + "소리여부");
					if (sound == false) {
						Log.e("main", sound + "ㅎㅎㅎ");
					} else {
						String toSpeak=null;
						Log.e("main", sound + "ㅋㅋㅎㅎ");
						if(list_result.get(0).getTime()>59&&list_result.get(0).getTime()%60==0){
							toSpeak = list_result.get(0).getTitle() + "를" + list_result.get(0).getTime()/60 + "분 시작합니다.";
						}else if(list_result.get(0).getTime()>59&&list_result.get(0).getTime()%60!=0){
							toSpeak = list_result.get(0).getTitle() + "를" + list_result.get(0).getTime()/60 + "분 "+list_result.get(0).getTime()%60+"초 시작합니다.";
						}else if(list_result.get(0).getTime()<60){

							toSpeak = list_result.get(0).getTitle() + "를" + list_result.get(0).getTime()%60+"초 시작합니다.";
						}
						t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
					}
					//운동화면으로 넘어간다.
					Intent intent = new Intent(this, MainActivity2.class);
					intent.putExtra("exercise", list_result);
					startActivity(intent);

				}

				break;

			case R.id.help:

				help=new Dialog(activity);
				view_helper=activity.getLayoutInflater().inflate(R.layout.help_desk,null);

				Window window2 = help.getWindow();
				window2.setLayout(lin_back.getWidth()*9/10, lin_back.getHeight()*9/10);

				help.setContentView(view_helper);
				help.show();
				btn_next=(Button)view_helper.findViewById(R.id.btn_next);
				btn_next.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						help.dismiss();
						final Dialog help=new Dialog(activity);

						View view_helper2=activity.getLayoutInflater().inflate(R.layout.help_desk_2,null);

						Window window2 = help.getWindow();
						window2.setLayout(lin_back.getWidth()*9/10, lin_back.getHeight()*11/10);

						help.setContentView(view_helper2);
						help.show();

						btn_end=(Button)view_helper2.findViewById(R.id.btn_end);
						btn_end.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								help.dismiss();
							}
						});
					}
				});
				break;
			case R.id.logout:

				UserManagement.requestLogout(new LogoutResponseCallback() {
					@Override
					public void onCompleteLogout() {
						redirectLoginActivity();
					}
				});

				break;
			case R.id.stat:
					Toast.makeText(getApplicationContext(),"nick : "+kakaoNickname,Toast.LENGTH_LONG).show();
				break;
			case  R.id.configuration:

				final Dialog configure=new Dialog(activity);
				configure.setTitle("설정");
				//LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(view_main.getWidth(),view_main.getHeight());
				view_configure=activity.getLayoutInflater().inflate(R.layout.configurator,null);
				//view_configure.setLayoutParams(params);
				lay=view_configure.findViewById(R.id.Linear);
				lay_direct=view_configure.findViewById(R.id.lay_direct);
				sound_toggle=(ToggleButton) view_configure.findViewById(R.id.sound_toggle);
				interval_toggle=(ToggleButton) view_configure.findViewById(R.id.interval_toggle);
				screen_toggle=(ToggleButton)view_configure.findViewById(R.id.screen_toggle);
				SharedPreferences setting=getSharedPreferences("shared_interval_time",0);
				int Interval =setting.getInt("interval_time",0);
				SharedPreferences setting_sound=getSharedPreferences("shared_sound",0);
				boolean Sound=setting_sound.getBoolean("sound",true);
				SharedPreferences setting_=getSharedPreferences("shared_screen",0);
				boolean screens=setting_.getBoolean("screen",true);
				Button save_button=(Button)view_configure.findViewById(R.id.configure_save);
				five_second=(RadioButton)view_configure.findViewById(R.id.five_second);
				ten_second=(RadioButton)view_configure.findViewById(R.id.ten_second);
				twenty_second=(RadioButton)view_configure.findViewById(R.id.twenty_second);
				thirty_second=(RadioButton)view_configure.findViewById(R.id.thirty_second);
				direct=(RadioButton)view_configure.findViewById(R.id.direct);
				if(screens==true){
					screen_toggle.setChecked(true);
				}else{
					screen_toggle.setChecked(false);
				}
				if(Sound==true){
					sound_toggle.setChecked(true);
				}else{
					sound_toggle.setChecked(false);
				}


				switch (Interval){
					case 5:
						five_second.setChecked(true);
						break;
					case 10:
						ten_second.setChecked(true);
						break;
					case 20:
						twenty_second.setChecked(true);
						break;
					case 30:
						thirty_second.setChecked(true);
						break;
				}

				RadioButton direct_radio=(RadioButton) lay.findViewById(R.id.direct);
				direct_radio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						if(isChecked)
							lay_direct.setVisibility(View.VISIBLE);
						else
							lay_direct.setVisibility(View.INVISIBLE);

					}
				});
				interval_toggle.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						if(interval_toggle.isChecked()){

							lay.setVisibility(View.VISIBLE);


						}else{
							lay.setVisibility(View.INVISIBLE);
							lay_direct.setVisibility(View.INVISIBLE);
						}
					}
				});

				configure.setContentView(view_configure);
				configure.show();
				Window window1 = configure.getWindow();
				window1.setLayout(lin_back.getWidth(), lin_back.getHeight()*9/10);
				five_second=(RadioButton)view_configure.findViewById(R.id.five_second);
				ten_second=(RadioButton)view_configure.findViewById(R.id.ten_second);
				twenty_second=(RadioButton)view_configure.findViewById(R.id.twenty_second);
				thirty_second=(RadioButton)view_configure.findViewById(R.id.thirty_second);
				direct=(RadioButton)view_configure.findViewById(R.id.direct);
				final EditText wished_interval_time=(EditText)view_configure.findViewById(R.id.wished_inteval_time);

				save_button.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						if(lay.VISIBLE==0){
							if(wished_interval_time.getText().toString().equals("")){
								Toast.makeText(getApplicationContext(),"인터벌 시간을 입력해주세요", Toast.LENGTH_SHORT).show();
								return;
							}
						}

							Toast.makeText(getApplicationContext(), "저장되었습니다", Toast.LENGTH_SHORT).show();
							if (five_second.isChecked())
								Log.e("main", "5초");
							interval_time = 5;


							if (ten_second.isChecked()) {
								Log.e("main", "10초");
								interval_time = 10;
							}
							if (twenty_second.isChecked()) {
								Log.e("main", "20초");
								interval_time = 20;
							}
							if (thirty_second.isChecked()) {
								Log.e("main", "30초");
								interval_time = 30;
							}
							if (direct.isChecked()) {
								Log.e("main", "?초");
								interval_time = Integer.parseInt(wished_interval_time.getText().toString());
							}
						Log.e("main","인터벌시간은 : "+interval_time);
							SharedPreferences shared_data = getSharedPreferences("shared_interval_time", 0);
							SharedPreferences.Editor editor = shared_data.edit();
							editor.putInt("interval_time", interval_time+1);
							editor.commit();

							if (screen_toggle.isChecked()) {
								screen_on = true;
							} else {
								screen_on = false;
							}

							SharedPreferences shared_data1 = getSharedPreferences("shared_screen", 0);
							SharedPreferences.Editor edito1r = shared_data1.edit();
							edito1r.putBoolean("screen", screen_on);
							edito1r.commit();
							Log.e("main", "zzz" + interval_time);
							if (sound_toggle.isChecked()) {
								sound = true;
							} else {
								sound = false;
							}
							SharedPreferences shared_data_sound = getSharedPreferences("shared_sound", 0);
							SharedPreferences.Editor editor1 = shared_data_sound.edit();
							editor1.putBoolean("sound", sound);
							editor1.commit();

							if (interval_toggle.isChecked()) {
								interval_to = true;

							} else {
								interval_to = false;
							}

							configure.dismiss();
					}
				});



				break;
		}


		return super.onOptionsItemSelected(item);
	}
*/



	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		lin_back=(LinearLayout)findViewById(R.id.lin_back);
		Log.e("main",",,OnWindowFocusChanged!!"+lin_back.getWidth()+","+lin_back.getHeight());
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity=this;

		requestMe();
		SharedPreferences sd=getSharedPreferences("shared_sound",0);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		RelativeLayout r1=(RelativeLayout)findViewById(R.id.custom_title2);


		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.custome_title);
		setContentView(R.layout.list_view);

		ImageButton plus=(ImageButton)findViewById(R.id.exercise_plus);
		plus.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(),"plus ",Toast.LENGTH_LONG).show();
			}
		});


		Log.e("main","OnCreate왔다"+list_exercise_pass);
		sound=sd.getBoolean("sound",true);
		SharedPreferences sd_interval=getSharedPreferences("shared_interval_time",0);
		interval_time=sd_interval.getInt("interval_time",0);
		Log.e("main","onCreate!!!!!!!!!!!!!!!!"+sound+",,,,운동간인터벌은:"+interval_time);
		SharedPreferences settings=getSharedPreferences(PREFS_NAME,0);
		Log.e("main",settings.getBoolean("my_first_time",true)+",,");
		if(settings.getBoolean("my_first_time",true)){
//theappisbeinglaunchedforfirsttime,dosomething
			Log.d("Comments","Firsttime");

			AlertDialog.Builder introduction=new AlertDialog.Builder(activity);
			introduction.setTitle("튜토리얼");
			introduction.setMessage("환영합니다!\n간단한튜토리얼을보시려면아래확인버튼을눌러주세요.\n튜토리얼은상단메뉴의도움말에서도볼수있습니다.");
			introduction.setPositiveButton("확인", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							help = new Dialog(activity);
							help.setTitle("도움말");
							view_helper = activity.getLayoutInflater().inflate(R.layout.help_desk, null);

							window2 = help.getWindow();
							window2.setLayout(550, 1000);

							help.setContentView(view_helper);
							help.show();
							btn_next = (Button) view_helper.findViewById(R.id.btn_next);
							btn_next.setOnClickListener(new View.OnClickListener() {
								@Override
								public void onClick(View v) {
									help.dismiss();
									final Dialog help = new Dialog(activity);

									View view_helper2 = activity.getLayoutInflater().inflate(R.layout.help_desk_2, null);

/*Windowwindow2=help.getWindow();
window2.setLayout(view_main.getWidth(),view_main.getHeight()*11/10);*/

									help.setContentView(view_helper2);
									help.show();

									btn_end = (Button) view_helper2.findViewById(R.id.btn_end);
									btn_end.setOnClickListener(new View.OnClickListener() {
										@Override
										public void onClick(View v) {
											help.dismiss();
										}
									});
								}
							});
						}
					});


			introduction.show();

			settings.edit().putBoolean("my_first_time",false).commit();
		}
		vibrate=(Vibrator)activity.getSystemService(Context.VIBRATOR_SERVICE);

		helper=new DbHelper(this,"exercise_timer.db",null,1);
		db=helper.getWritableDatabase();
		int exer_id;
		String exer_title;
		int exer_time;
		int exer_rest;
		int exer_interval;
		list_exercise=new ArrayList<Exercise>();
		t1=new TextToSpeech(getApplicationContext(),new TextToSpeech.OnInitListener(){
			@Override
					public void onInit(int status){
				Log.e("main","text:"+status);
				if(status==TextToSpeech.SUCCESS){
					int result=t1.setLanguage(Locale.KOREA);
					if(result==TextToSpeech.LANG_MISSING_DATA
							||result==TextToSpeech.LANG_NOT_SUPPORTED){
						Toast.makeText(getApplicationContext(),"Languagenotsupported",
								Toast.LENGTH_LONG).show();
						Log.e("TTS","Languageisnotsupported");
					}
					else{
					}
				}else{
					Toast.makeText(getApplicationContext(),"TTSInitilizationFailed",Toast.LENGTH_LONG)
							.show();
					Log.e("TTS","InitilizationFailed");
				}
			}


		});

		if(db!=null){
			Cursor cursor=db.rawQuery("select exer_id,title,time,rest,interval from exercise",null);
			while(cursor.moveToNext()){
				exer_id=cursor.getInt(0);
				exer_title=cursor.getString(1);
				exer_time=cursor.getInt(2);
				exer_rest=cursor.getInt(3);
				exer_interval=cursor.getInt(4);
				Exercise exercise=new Exercise();
				exercise.setExer_id(exer_id);
				exercise.setTitle(exer_title);
				exercise.setTime(exer_time);
				exercise.setRest(exer_rest);
				exercise.setInterval(exer_interval);
				list_exercise.add(exercise);
				list_exerciseTitle.add(exercise.getTitle()+" 운동시간 : "+exercise.getTime());
				list_exerciseTime.add(exercise.getTime());
				Log.e("main","운동리스트는 : "+list_exercise+"");
			}
		}
		listView=(DynamicListView)findViewById(R.id.listview);
		ArrayList<String>mCheeseList = new ArrayList<String>();
		for (int i = 0; i < Cheeses.sCheeseStrings.length; ++i) {
			mCheeseList.add(Cheeses.sCheeseStrings[i]);
		}
		listviewadapter=new StableArrayAdapter(this, list_exercise, new StableArrayAdapter.Listener() {
			@Override
			public void onGrab(int position, RelativeLayout row) {
				listView.onGrab(position,row);
			}
		});
		Log.e("main","추가1");

		Log.e("main","추가2");
		Log.e("main","추가3");
		//listviewadapter.setExercise_List(list_exercise);
		listView.setExercise12(list_exercise);
		listView.setAdapter(listviewadapter);
		listView.setListener(new DynamicListView.Listener() {

			@Override
			public void swapElements(int indexOne, int indexTwo) {
				Log.e("main","changing"+list_exercise.get(indexOne)+"을 "+list_exercise.get(indexTwo));

				Exercise temp=list_exercise.get(indexOne);
				list_exercise.set(indexOne,list_exercise.get(indexTwo));
				list_exercise.set(indexTwo,temp);

			}
		});
//listviewadapter.setExercise_List(list_exercise);
/*intdesiredWidth=View.MeasureSpec.makeMeasureSpec(listView.getWidth(),View.MeasureSpec.AT_MOST);
for(inti=0;i<listviewadapter.getCount();i++){
ViewlistItem=listviewadapter.getView(i,null,listView);
listItem.measure(desiredWidth,View.MeasureSpec.UNSPECIFIED);
totalHeight=listItem.getMeasuredHeight();
}
ViewGroup.LayoutParamsparams=listView.getLayoutParams();
params.height=totalHeight+(listView.getDividerHeight()*(listView.getCount()-1));
*/listView.setDividerHeight(6);
/*listView.setLayoutParams(params);
*/listView.setAdapter(listviewadapter);

	}
	class DbHelper extends SQLiteOpenHelper {

		private EditText name;

		public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		/*public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
			super(context, name, factory, version, errorHandler);
		}*/

		@Override
		public void onCreate(SQLiteDatabase db) {
			if(db!=null){
				db.execSQL("create table exercise(" +
						"exer_id integer primary key autoincrement," +
						"title text," +
						"time integer,"+
						"rest integer,"+
						"interval integer,"+
						"selected text)");

			}else{
				Log.e("main","else옴");

			}
		}



		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		}
	}
	protected void requestMe() {
		UserManagement.requestMe(new MeResponseCallback() {
			@Override
			public void onFailure(ErrorResult errorResult) {
				String message = "failed to get user info. msg=" + errorResult;

				ErrorCode result = ErrorCode.valueOf(errorResult.getErrorCode());
				if (result == ErrorCode.CLIENT_ERROR_CODE) {
					finish();
				} else {
				}
			}

			@Override
			public void onSessionClosed(ErrorResult errorResult) {

			}

			@Override
			public void onSuccess(UserProfile userProfile) {
				String kakaoID = String.valueOf(userProfile.getId()); // userProfile에서 ID값을 가져옴
				kakaoNickname = userProfile.getNickname();     // Nickname 값을 가져옴
				String email=userProfile.getEmail();
				String image=userProfile.getThumbnailImagePath();
				Log.e("main",  "d"+userProfile+"nick : "+kakaoNickname);



				TextView title=(TextView)findViewById(R.id.my);
				title.setText(kakaoNickname);
				new GetFoto(activity).execute();
				//  redirectMainActivity(); // 로그인 성공시 MainActivity로


			}

			@Override
			public void onNotSignedUp() {
			}
		});
	}
	protected void redirectLoginActivity() {
		Log.e("main","redirectLoginActivity");
		final Intent intent = new Intent(this, LoginActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		startActivity(intent);
		finish();
	}
}

class getImage extends AsyncTask<byte[],String,Bitmap> {
	Bitmap bm;

	@Override
	protected void onPostExecute(Bitmap bitmap) {
		super.onPostExecute(bitmap);
	}

	@Override
	protected Bitmap doInBackground(byte[]... params) {
		String url="http://mud-kage.kakao.co.kr/14/dn/btqgFs8pUa7/ztkpcXbwQE7C51ak6K4EC1/o.jpg";
		try {
			URLConnection conn = new URL(url).openConnection();
			conn.connect();
			BufferedInputStream bis = new BufferedInputStream(conn.getInputStream(), 8192);
			bm = BitmapFactory.decodeStream(bis);

		} catch (IOException e) {
			e.printStackTrace();
		}



		return bm;
	}
}





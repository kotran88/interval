package scit.org.kakao;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity2 extends AppCompatActivity {
	int time;
	private Activity activity;
	int fin;
	int fin_first;
	boolean Sound=true;
	LinearLayout lin_lay;
	TextView LeftTime;
	private Handler mHandler;
	boolean Interval_true=false;
	int position;
	TextToSpeech t1;
	int restingTime;
	int restingTime_first;
	boolean flag=false;
	boolean flag_end=false;
	int exercise_size;
	ArrayList<Exercise>ex=new ArrayList<Exercise>();
	boolean Interval_Doing_for_Speak=false;
	boolean interval_rest=false;
	String checked="f";
	String third="2";
	String title=null;
	boolean checked_speak=true;
	int count=1;
	TextView view_title;
	TextToSpeech t2;
	TextView view_rest;
	TextView view_interval;
	TextView view_time;
	boolean Interval_Doing=false;
	TextView Stop;
	boolean Interval_check=false;
	TextView total_size;
	int WhatNow=0;
	boolean Just_Go=true;
	int count_first=0;
	int position_first=0;
	boolean run_check=false;
	int Interval=0;
	String jump="";
	Vibrator vibrator;
	boolean Finish=false;
	boolean end_this=false;
	long pattern[]={50,100};
	ArrayList<View> view_collection;
	public enum Rest{SUPERLEAST("10초"),LEAST("20초"),MIDIUM("30초"),LAGEST("40초"),SUPERLARGE("50초");
		private String span;
		Rest(String months){
			span=months;
		}
		public String getSpan(){
			return span;
		}

	};


	/*public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_list_exercise,menu);

		return super.onCreateOptionsMenu(menu);

	}

	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()){
			case R.id.back:

				final AlertDialog.Builder alert=new AlertDialog.Builder(activity);
				alert.setTitle("종료하시겠습니까?");
				alert.setMessage("종료하시면 진행중인 데이터가 사라집니다.");
				alert.setPositiveButton("네", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						activity.finish();
						mHandler.removeCallbacks(mHandlerTimer);
					}
				});
				alert.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});

				alert.show();

				break;

		}

		return true;
	}*/


	private boolean thisisEnd;
	private boolean thhe_end;
	private Runnable mHandlerTimer=new Runnable() {
		@Override
		public void run() {
			if(fin<1){
				Interval_true=false;
				Log.e("main","남은시간 0초"+checked_speak);
				lin_lay = (LinearLayout) findViewById(R.id.Lin_lay);;
				if(flag==false) {
					if(position<=1){
						flag=true;
					}
					Log.e("main","남은시간 0초 flag"+position);
					if(position>1) {
						count_first = 0;
						Log.e("main", WhatNow + "whatnowwww" + checked);


						lin_lay.setBackgroundResource(R.drawable.rest);



						if (checked.equals("f")) {
							Log.e("main", "resttime" + restingTime);
							restingTime_first = restingTime;
							if (restingTime == 0) {


							} else {
								if (Sound == true) {

									String toSpeak = restingTime + "초" + "쉬는시간 시작";
									t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
								} else {
									vibrator.vibrate(1200);
								}
							}
							Log.e("main", "쉬는시간간당" + restingTime + "남은횟수" + position);
							checked = "t";


						}
						LeftTime.setText("휴식\n" + restingTime);
						//쉬는시간 시작
						if (restingTime == 10) {
							if (Sound == true) {

								String toSpeak = "쉬는 시간 종료 10초 전. ";
								t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
							} else {

							}


						}
						restingTime -= 1;

						if (restingTime < 0) {

							Log.e("main", restingTime + ",,,,!!!!" + position);
							if (position < 1) {
								LeftTime.setText(("운동 종료"));
							}
							LeftTime.setText(("휴식 종료"));
							restingTime = restingTime_first;
							flag = true;
						}
					}
				}else{
					//Flag가 True일때~~~




					count+=1;
					Log.e("main","Flag가 True ,,,count : "+count);
					//쉬는시간 끝. position감소 fin초기화
					position-=1;
					//view_interval.setText("반복횟수 : " +ex.get(0).getInterval()+","+position);

					//mHandler.postDelayed(this,10);
					/*String toSpeak="이번인터벌은종료되었습니다";
					t1.speak(toSpeak,TextToSpeech.QUEUE_FLUSH,null);
//횟수종료후,음성나오고나서진행하기위해postDelayed사용.
					mHandler.postDelayed(this,6);*/
					fin=fin_first;

					Log.e("main","카운트"+position+flag+restingTime+",,,"+Finish);
					if(position<1) {
						end_this=true;
						if(Finish){
							view_interval.setVisibility(View.INVISIBLE);
							Log.e("main", "운동종료"+exercise_size+third+Interval_Doing);
							String speak="모든 운동 종료되었습니다.";
							interval_rest=true;
							t1.speak(speak,TextToSpeech.QUEUE_FLUSH,null);
							LeftTime.setText("운동종료");
							mHandler.removeCallbacks(mHandlerTimer);
						}else{
							Log.e("main", "운동종료");
						}
						position_first=0;
						Finish=false;
						if(Interval_Doing==true){
						//운동간 인터벌일때는 운동종료 메세지 보여주지 않음.
						Interval_Doing=false;
						}else{
							view_interval.setVisibility(View.VISIBLE);
							//운동간 인터벌중에는 운동종료가 뜨지않게하기위해서 나눔.
							/*LeftTime.setText("운동 종료");
							synchronized(mHandlerTimer){
								try {
									wait(500);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
							if(Interval_Doing_for_Speak==true){
								Log.e("main","Interval_Doing_for_Speak"+Interval_Doing_for_Speak);
								Interval_Doing_for_Speak=false;
							}else{




							}*/
						}


//						button.setText("start");


						if(exercise_size==1){
						Log.e("main","운동리스트는 : 1");
						}else if(exercise_size==2){
							Log.e("main","운동 2개짜리임. "+interval_rest +flag_end);
							//interval_rest=false;
							WhatNow=1;
							count=1;
							if(interval_rest==false){
								//첫번째운동.
									total_size.setText("운동중 인터벌 휴식구간입니다.");

									Interval_Doing=true;
									view_title.setText("");
									view_rest.setText("");
									view_interval.setText("");
									view_time.setText("");
									Interval_check=true;
									Interval_Doing_for_Speak=true;
									title="운동중 쉬기";
									fin=Interval;

									fin_first=fin;
									position=0;
									restingTime=0;
									mHandler.postDelayed(mHandlerTimer,700);
									interval_rest=true;
							}else if(flag_end==false){
								Log.e("main","Flag_end : "+flag_end);
								//2번째
								Finish=true;
								total_size.setText("총 운동수 "+ex.size()+"가지중 2번째 운동입니다.");
								title=ex.get(1).getTitle();
								view_title.setText(""+ex.get(1).getTitle());
								view_rest.setText("휴식시간 :"+ex.get(1).getRest());
								view_interval.setText("반복횟수 :"+ex.get(1).getInterval());
								Log.e("main","반복회수 : "+ex.get(1).getInterval()+",");
								if(ex.get(1).getTime()>60){
									if(ex.get(1).getTime()%60==0){
										view_time.setText("시간:"+ex.get(1).getTime()/60+"분");
									}else{

										view_time.setText("시간:"+ex.get(1).getTime()/60+"분"+ex.get(1).getTime()%60+"초");
									}
								}else{

									view_time.setText("시간: "+ex.get(1).getTime());
								}
								Log.e("main","운동 사이즈 2개의 운동리스트는 : 2"+ex.get(1));
								fin=ex.get(1).getTime()+1;
								fin_first=fin;
								position=ex.get(1).getInterval();
								restingTime=ex.get(1).getRest();
								checked="f";
								mHandler.postDelayed(mHandlerTimer,0);
								flag_end=true;
								//Finish=true;
								interval_rest=true;
								Log.e("main","ff"+flag_end+","+Finish+","+interval_rest);

							}else{

								Log.e("main","아무것도안함");
							}
						}else if(exercise_size==3){
							WhatNow=2;
							count=1;
							Interval_true=false;
							Log.e("main3","사이즈 3개의 : "+interval_rest+",,,,"+run_check+",,,,"+third);
							if(interval_rest==false){
									total_size.setText("운동중 인터벌 휴식구간입니다.");
									checked_speak=true;
									Interval_Doing=true;
									view_title.setText("");
									view_rest.setText("");
									view_interval.setText("");
									view_time.setText("");
									Interval_check=true;
									Interval_Doing_for_Speak=true;
									title="운동중 쉬기";
									fin=Interval;

									fin_first=fin;
									position=0;
									restingTime=0;
									mHandler.postDelayed(mHandlerTimer,700);
									interval_rest=true;
							}else if(third.equals("2")){
								Log.e("main3","else"+third+",,,"+run_check);
								if(run_check==true){
									Log.e("main3","jump 사이즈3 1번에서 2번으로 가는 휴식");
									Interval_Doing=true;
									total_size.setText("운동중 인터벌 휴식구간입니다.2");
									view_title.setText("");
									view_rest.setText("");
									view_interval.setText("");
									view_time.setText("");
									Log.e("main","third.equal 3의 운동리스트는 : 2");
									view_interval.setVisibility(View.INVISIBLE);
									title="운동중 쉬기";
									fin=Interval;
									fin_first=fin;
									position=0;
									restingTime=0;
									mHandler.postDelayed(mHandlerTimer,0);
									interval_rest=true;
									run_check=false;

								}else{
									Log.e("main3","else");
									WhatNow=1;
									interval_rest=false;
									view_interval.setVisibility(View.VISIBLE);
									total_size.setText("총 운동수 "+ex.size()+"가지중 2번째 운동입니다.");
									title=ex.get(1).getTitle();
									view_title.setText(""+ex.get(1).getTitle());
									view_rest.setText("휴식시간 :"+ex.get(1).getRest());
									view_interval.setText("반복횟수 :"+ex.get(1).getInterval());
									if(ex.get(1).getTime()>60){
										if(ex.get(1).getTime()%60==0){
											view_time.setText("시간:"+ex.get(1).getTime()/60+"분");
										}else{

											view_time.setText("시간:"+ex.get(1).getTime()/60+"분"+ex.get(1).getTime()%60+"초");
										}
									}else{

										view_time.setText("시간: "+ex.get(1).getTime());
									}
									fin=ex.get(1).getTime()+1;
									Log.e("main","운동리스트는 : 3.."+fin);
									fin_first=fin;
									title=ex.get(1).getTitle();
									restingTime=ex.get(1).getRest();
									position=ex.get(1).getInterval();
									mHandler.postDelayed(mHandlerTimer,0);
									third="3";

								}

							}else if(third.equals("3")){
								Log.e("main3","else"+third+",,,"+run_check);
								Finish=true;
								if(run_check==true){
										total_size.setText("운동중 인터벌 휴식구간입니다.");
										checked_speak=true;
										Interval_Doing=true;
										view_title.setText("");
										view_rest.setText("");
										view_interval.setText("");
										view_time.setText("");
										Interval_check=true;
										Interval_Doing_for_Speak=true;
										title="운동중 쉬기";
										fin=Interval;

										fin_first=fin;
										position=0;
										restingTime=0;
										mHandler.postDelayed(mHandlerTimer,700);
										interval_rest=true;
								}else{
									WhatNow=2;
									view_interval.setVisibility(View.VISIBLE);
									total_size.setText("총 운동수 "+ex.size()+"가지중 3번째 운동입니다.");
									title=ex.get(2).getTitle();
									view_title.setText(""+ex.get(2).getTitle());
									view_rest.setText("휴식시간 :"+ex.get(2).getRest());
									view_interval.setText("반복횟수 :"+ex.get(2).getInterval());
									if(ex.get(2).getTime()>60){
										if(ex.get(2).getTime()%60==0){
											view_time.setText("시간:"+ex.get(2).getTime()/60+"분");
										}else{

											view_time.setText("시간:"+ex.get(2).getTime()/60+"분"+ex.get(2).getTime()%60+"초");
										}
									}else{

										view_time.setText("시간: "+ex.get(2).getTime());
									}
									fin=ex.get(2).getTime()+1;
									Log.e("main","운동리스트는 : 3,."+fin);
									fin_first=fin;
									position=ex.get(2).getInterval();
									restingTime=ex.get(2).getRest();
									mHandler.postDelayed(mHandlerTimer,0);
									third="4";
									Finish=true;
								}

							}
						}else if(exercise_size==4){
							WhatNow=3;
							count=1;
							if(interval_rest==false){

									total_size.setText("운동중 인터벌 휴식구간입니다22.");
									checked_speak=true;
									Interval_Doing=true;
									view_title.setText("");
									view_rest.setText("");
									view_interval.setText("");
									view_time.setText("");
									Interval_check=true;
									Interval_Doing_for_Speak=true;
									title="운동중 쉬기";
									fin=Interval;

									fin_first=fin;
									position=0;
									restingTime=0;
									mHandler.postDelayed(mHandlerTimer,700);
									interval_rest=true;

							}else if(third.equals("2")){
								if(run_check==true){
									Log.e("main3","jump 2번에서 3번으로 가는 휴식");

										total_size.setText("운동중 인터벌 휴식구간입니다.");
										checked_speak=true;
										Interval_Doing=true;
										view_title.setText("");
										view_rest.setText("");
										view_interval.setText("");
										view_time.setText("");
										Interval_check=true;
										Interval_Doing_for_Speak=true;
										title="운동중 쉬기";
										fin=Interval;

										fin_first=fin;
										position=0;
										restingTime=0;
										mHandler.postDelayed(mHandlerTimer,700);
										interval_rest=true;
								}else{
									Log.e("main3","2번째 클릭됨 : "+ex.get(1).getTitle());
									WhatNow=1;
									interval_rest=false;
									view_interval.setVisibility(View.VISIBLE);
									total_size.setText("총 운동수 "+ex.size()+"가지중 2번째 운동입니다.");
									title=ex.get(1).getTitle();
									view_title.setText(""+ex.get(1).getTitle());
									view_rest.setText("휴식시간 :"+ex.get(1).getRest());
									view_interval.setText("반복횟수 :"+ex.get(1).getInterval());
									if(ex.get(1).getTime()>60){
										if(ex.get(1).getTime()%60==0){
											view_time.setText("시간:"+ex.get(1).getTime()/60+"분");
										}else{

											view_time.setText("시간:"+ex.get(1).getTime()/60+"분"+ex.get(1).getTime()%60+"초");
										}
									}else{

										view_time.setText("시간: "+ex.get(1).getTime());
									}
									fin=ex.get(1).getTime()+1;
									Log.e("main","운동리스트는 : 3,."+fin);
									fin_first=fin;
									position=ex.get(1).getInterval();
									restingTime=ex.get(1).getRest();
									mHandler.postDelayed(mHandlerTimer,0);
									third="3";
								}

							}else if(third.equals("3")){
								if(run_check==true){
									Log.e("main3","jump 2번에서 3번으로 가는 휴식");

										total_size.setText("운동중 인터벌 휴식구간입니다.");
										checked_speak=true;
										Interval_Doing=true;
										view_title.setText("");
										view_rest.setText("");
										view_interval.setText("");
										view_time.setText("");
										Interval_check=true;
										Interval_Doing_for_Speak=true;
										title="운동중 쉬기";
										fin=Interval;

										fin_first=fin;
										position=0;
										restingTime=0;
										mHandler.postDelayed(mHandlerTimer,700);
										interval_rest=true;
								}else{
									WhatNow=2;
									interval_rest=false;
									Interval_Doing=false;
									view_interval.setVisibility(View.VISIBLE);
									total_size.setText("총 운동수 "+ex.size()+"가지중 3번째 운동입니다.");
									title=ex.get(2).getTitle();
									view_title.setText(""+ex.get(2).getTitle());
									view_rest.setText("휴식시간 :"+ex.get(2).getRest());
									view_interval.setText("반복횟수 :"+ex.get(2).getInterval());
									if(ex.get(2).getTime()>60){
										if(ex.get(2).getTime()%60==0){
											view_time.setText("시간:"+ex.get(2).getTime()/60+"분");
										}else{

											view_time.setText("시간:"+ex.get(2).getTime()/60+"분"+ex.get(2).getTime()%60+"초");
										}
									}else{

										view_time.setText("시간: "+ex.get(2).getTime());
									}
									fin=ex.get(2).getTime()+1;
									Log.e("main","운동리스트는 : 3,."+fin);
									fin_first=fin;
									position=ex.get(2).getInterval();
									restingTime=ex.get(2).getRest();
									mHandler.postDelayed(mHandlerTimer,0);
									third="4";
									//mHandler.removeCallbacks(mHandlerTimer);
								}

							}else if(third.equals("4")){
								Log.e("main3","third 옴"+third+interval_rest);

								if(run_check==true){

										total_size.setText("운동중 인터벌 휴식구간입니다.33");
										checked_speak=true;
										Interval_Doing=true;
										view_title.setText("");
										view_rest.setText("");
										view_interval.setText("");
										view_time.setText("");
										Interval_check=true;
										Interval_Doing_for_Speak=true;
										title="운동중 쉬기";
										fin=Interval;

										fin_first=fin;
										position=0;
										restingTime=0;
										mHandler.postDelayed(mHandlerTimer,700);
										interval_rest=true;
								}else{
									WhatNow=3;
									view_interval.setVisibility(View.VISIBLE);
									Log.e("main3","마지막운동22222222222222");
									total_size.setText("총 운동수 "+ex.size()+"가지중 4번째 운동입니다.");
									title=ex.get(3).getTitle();
									view_title.setText(""+ex.get(3).getTitle());
									view_rest.setText("휴식시간 :"+ex.get(3).getRest());
									view_interval.setText("반복횟수 :"+ex.get(3).getInterval());
									if(ex.get(3).getTime()>60){
										if(ex.get(3).getTime()%60==0){
											view_time.setText("시간:"+ex.get(3).getTime()/60+"분");
										}else{

											view_time.setText("시간:"+ex.get(3).getTime()/60+"분"+ex.get(3).getTime()%60+"초");
										}
									}else{

										view_time.setText("시간: "+ex.get(3).getTime());
									}
									fin=ex.get(3).getTime()+1;
									Log.e("main","운동리스트는 : 3,."+fin);
									fin_first=fin;
									position=ex.get(3).getInterval();
									restingTime=ex.get(3).getRest();
									mHandler.postDelayed(mHandlerTimer,0);
									third="5";
									Finish=true;
									//mHandler.removeCallbacks(mHandlerTimer);
								}

							}
						}else if(exercise_size==5){
							WhatNow=4;
							count=1;
							Log.e("main3",interval_rest+","+thisisEnd+","+third);
							if(interval_rest==false){

									total_size.setText("운동중 인터벌 휴식구간입니다.");
									checked_speak=true;
									Interval_Doing=true;
									view_title.setText("");
									view_rest.setText("");
									view_interval.setText("");
									view_time.setText("");
									Interval_check=true;
									Interval_Doing_for_Speak=true;
									title="운동중 쉬기";
									fin=Interval;

									fin_first=fin;
									position=0;
									restingTime=0;
									mHandler.postDelayed(mHandlerTimer,700);
									interval_rest=true;

							}else if(third.equals("2")){
								if(run_check==true){
									Log.e("main","jump 2번에서 3번으로 가는 휴식");

										total_size.setText("운동중 인터벌 휴식구간입니다.");
										checked_speak=true;
										Interval_Doing=true;
										view_title.setText("");
										view_rest.setText("");
										view_interval.setText("");
										view_time.setText("");
										Interval_check=true;
										Interval_Doing_for_Speak=true;
										title="운동중 쉬기";
										fin=Interval;

										fin_first=fin;
										position=0;
										restingTime=0;
										mHandler.postDelayed(mHandlerTimer,700);
										interval_rest=true;
								}else{
									WhatNow=1;
									interval_rest=false;
									view_interval.setVisibility(View.VISIBLE);
									total_size.setText("총 운동수 "+ex.size()+"가지중 2번째 운동입니다.");
									title=ex.get(1).getTitle();
									view_title.setText(""+ex.get(1).getTitle());
									view_rest.setText("휴식시간 :"+ex.get(1).getRest());
									view_interval.setText("반복횟수 :"+ex.get(1).getInterval());
									if(ex.get(1).getTime()>60){
										if(ex.get(1).getTime()%60==0){
											view_time.setText("시간:"+ex.get(1).getTime()/60+"분");
										}else{

											view_time.setText("시간:"+ex.get(1).getTime()/60+"분"+ex.get(1).getTime()%60+"초");
										}
									}else{

										view_time.setText("시간: "+ex.get(1).getTime());
									}
									fin=ex.get(1).getTime()+1;
									Log.e("main","운동리스트는 : 3,."+fin);
									fin_first=fin;
									position=ex.get(1).getInterval();
									restingTime=ex.get(1).getRest();
									mHandler.postDelayed(mHandlerTimer,0);
									third="3";
								}

							}else if(third.equals("3")){
								if(run_check==true){
									Log.e("main","jump 2번에서 333번으로 가는 휴식");

										total_size.setText("운동중 인터벌 휴식구간입니다.");
										checked_speak=true;
										Interval_Doing=true;
										view_title.setText("");
										view_rest.setText("");
										view_interval.setText("");
										view_time.setText("");
										Interval_check=true;
										Interval_Doing_for_Speak=true;
										title="운동중 쉬기";
										fin=Interval;

										fin_first=fin;
										position=0;
										restingTime=0;
										mHandler.postDelayed(mHandlerTimer,700);
										interval_rest=true;
								}else{
									interval_rest=false;
									WhatNow=2;
									Interval_Doing=false;
									view_interval.setVisibility(View.VISIBLE);
									total_size.setText("총 운동수 "+ex.size()+"가지중 3번째 운동입니다.");
									title=ex.get(2).getTitle();
									view_title.setText(""+ex.get(2).getTitle());
									view_rest.setText("휴식시간 :"+ex.get(2).getRest());
									view_interval.setText("반복횟수 :"+ex.get(2).getInterval());
									if(ex.get(2).getTime()>60){
										if(ex.get(2).getTime()%60==0){
											view_time.setText("시간:"+ex.get(2).getTime()/60+"분");
										}else{

											view_time.setText("시간:"+ex.get(2).getTime()/60+"분"+ex.get(2).getTime()%60+"초");
										}
									}else{

										view_time.setText("시간: "+ex.get(2).getTime());
									}
									fin=ex.get(2).getTime()+1;
									Log.e("main","운동리스트는 : 3,."+fin);
									fin_first=fin;
									position=ex.get(2).getInterval();
									restingTime=ex.get(2).getRest();
									mHandler.postDelayed(mHandlerTimer,0);
									third="4";
								}

							}else if(third.equals("4")){
								Log.e("main3","마지막 : "+third+","+run_check+","+thisisEnd);

								if(run_check==true){
										total_size.setText("운동중 인터벌 휴식구간입니다.");
										checked_speak=true;
										Interval_Doing=true;
										view_title.setText("");
										view_rest.setText("");
										view_interval.setText("");
										view_time.setText("");
										Interval_check=true;
										Interval_Doing_for_Speak=true;
										title="운동중 쉬기";
										fin=Interval;

										fin_first=fin;
										position=0;
										restingTime=0;
										mHandler.postDelayed(mHandlerTimer,700);
										interval_rest=true;
								}else{
									interval_rest=false;
									WhatNow=3;
									view_interval.setVisibility(View.VISIBLE);
									Interval_Doing=false;
									Log.e("main","마지막운동22222222222222");
									total_size.setText("총 운동수 "+ex.size()+"가지중 4번째 운동입니다.");
									title=ex.get(3).getTitle();
									view_title.setText(""+ex.get(3).getTitle());
									view_rest.setText("휴식시간 :"+ex.get(3).getRest());
									view_interval.setText("반복횟수 :"+ex.get(3).getInterval());
									if(ex.get(3).getTime()>60){
										if(ex.get(3).getTime()%60==0){
											view_time.setText("시간:"+ex.get(3).getTime()/60+"분");
										}else{

											view_time.setText("시간:"+ex.get(3).getTime()/60+"분"+ex.get(3).getTime()%60+"초");
										}
									}else{

										view_time.setText("시간: "+ex.get(3).getTime());
									}
									fin=ex.get(3).getTime()+1;
									Log.e("main","운동리스트는 : 3,."+fin);
									fin_first=fin;
									position=ex.get(3).getInterval();
									restingTime=ex.get(3).getRest();
									mHandler.postDelayed(mHandlerTimer,0);
									third="5";
									thhe_end=true;
									//thisisEnd=true;
								}

							}else if(third.equals("5")){
								if(thhe_end){
									thisisEnd=false;
									thhe_end=false;
								}else{
									thisisEnd=true;
								}
								Log.e("main3","마지막22 : "+third+","+run_check+","+thisisEnd);
								if(thisisEnd){}else {
									if (run_check == true) {

										total_size.setText("운동중 인터벌 휴식구간입니다.");
										checked_speak = true;
										Interval_Doing = true;
										view_title.setText("");
										view_rest.setText("");
										view_interval.setText("");
										view_time.setText("");
										Interval_check = true;
										Interval_Doing_for_Speak = true;
										title = "운동중 쉬기";
										fin = Interval;

										fin_first = fin;
										position = 0;
										restingTime = 0;
										mHandler.postDelayed(mHandlerTimer, 700);
										interval_rest = true;
									} else if (run_check == false) {
										WhatNow = 4;
										view_interval.setVisibility(View.VISIBLE);
										Interval_Doing = false;
										Log.e("main", "마지막운동22222222222222");
										total_size.setText("총 운동수 " + ex.size() + "가지중 5번째 운동입니다.");
										title = ex.get(4).getTitle();
										view_title.setText("" + ex.get(4).getTitle());
										view_rest.setText("휴식시간 :" + ex.get(4).getRest());
										view_interval.setText("반복횟수 :" + ex.get(4).getInterval());
										if (ex.get(4).getTime() > 60) {
											if (ex.get(4).getTime() % 60 == 0) {
												view_time.setText("시간:" + ex.get(4).getTime() / 60 + "분");
											} else {

												view_time.setText("시간:" + ex.get(4).getTime() / 60 + "분" + ex.get(4).getTime() % 60 + "초");
											}
										} else {

											view_time.setText("시간: " + ex.get(4).getTime());
										}
										fin = ex.get(4).getTime() + 1;
										Log.e("main", "운동리스트는 : 4,." + fin);
										fin_first = fin;
										position = ex.get(4).getInterval();
										restingTime = ex.get(4).getRest();
										mHandler.postDelayed(mHandlerTimer, 0);
										third = "5";
										Finish = true;
										//thisisEnd=true;
									}

								}
							}
						}
						return;
					}
					//position 감소처리 끝.

					checked_speak = true;
				}


				}else{
				//남은시간이 계속있을때, fin을 줄여줌.
				Log.e("main","게임이번판 시작 ");
				count_first++;

				if(count_first==1){
					fin=fin;
					position_first++;
				}
				if(flag==false){

				}else{
					lin_lay.setBackgroundResource(R.drawable.i_white);
				}
				if(position<1){
					lin_lay.setBackgroundResource(R.drawable.rest);
				}
				checked="f";
				flag=false;
				Log.e("main","speaking..."+checked_speak+",,"+Interval_Doing+",,,,"+Just_Go);
				Log.e("main","스피킹시작준비중"+Interval_check+",,,"+count_first);
				Log.e("main","check_speak "+checked_speak+"Interval_doing:"+Interval_Doing);
				/*if(Interval_Doing==true){
					view_interval.setText("반복횟수(현재) : 휴식");
					Log.e("main","운동간 휴식중");
					String toSpeak="운동간에"+fin+"초 쉽니다.";
					t1.speak(toSpeak,TextToSpeech.QUEUE_FLUSH,null);
					Interval_Doing=false;
				}else */
				if(checked_speak==true&&Interval_Doing==false&&!Just_Go){
					Log.e("main","현재 운동 : "+title+",,,"+fin);
					String toSpeak=title+"를"+(fin-1)+"초 시작합니다.";

					Log.e("main","운동간 휴식기간인지 여부 : "+Interval_Doing);
					Log.e("main","!justgo반복횟수(현재)"+ex.get(WhatNow).getInterval()+"("+(count)+")");
					view_interval.setText("반복횟수(현재) : " +ex.get(WhatNow).getInterval()+"("+(count)+")");
					if(Sound==false){
						Log.e("main","스피킹노노");
						vibrator.vibrate(900);
					}else{
						Log.e("main","스피킹rr");
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
							t1.speak(toSpeak,TextToSpeech.QUEUE_FLUSH,null,null);
						} else {
							t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
						}


					}
					synchronized(mHandlerTimer){
						try {
							wait(900);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					checked_speak=false;

				}else if(checked_speak==true&&Interval_Doing==false&&Just_Go==true){
					Log.e("main","현재 운동111 : "+title+",,,"+fin);
					if(Sound==false){
						vibrator.vibrate(900);
					}else{

						String toSpeak=null;
						if(fin>59&&fin%60==0){
							toSpeak = title + "를" + fin/60 + "분 시작합니다.";
						}else if(fin>59&&fin%60!=0){
							toSpeak = title + "를" + fin/60 + "분 "+fin%60+"초 시작합니다.";
						}else if(fin<60){

							toSpeak = title + "를" + fin%60+"초 시작합니다.";
						}
						Log.e("main","스피킹rrr");
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
							t1.speak(toSpeak,TextToSpeech.QUEUE_FLUSH,null,null);
						} else {
							t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
						}

					}

					Log.e("main","운동간 휴식기간인지 여부 : "+Interval_Doing);
					Log.e("main","justgo반복횟수(현재)"+ex.get(WhatNow).getInterval()+"("+(count)+")");
					view_interval.setText("반복횟수(현재) : " +ex.get(WhatNow).getInterval()+"("+(count)+")");
					Log.e("main","스피킹중_Justgo");
					Just_Go=false;
					synchronized(mHandlerTimer){
						try {
							wait(500);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					checked_speak=false;
				}

				/*if(Interval_Doing==true) {
					Log.e("main", "운동간 ㅎ휴식중!");
					view_interval.setText("운동간 휴식중");
				}else{

					Log.e("main","휴식중이ㄴㄴㄴㄴ아님"+third);
					//
				}*/


				Log.e("main","run check : "+run_check+",,,");
				if(jump.equals("three")){
					Finish=false;
					Log.e("main3","3번째로 점프한다");
					count=1;
					WhatNow=2;
					title=ex.get(2).getTitle();
					fin=ex.get(2).getTime()+1;
					Log.e("main","세번째122 운동 시작한다."+ex.get(2).getTime()+"초");
					//mHandler.removeCallbacks(mHandlerTimer);
					total_size.setText("총 운동수 "+ex.size()+"가지중 3번째 운동입니다.");
					view_title.setText(""+ex.get(2).getTitle());
					view_rest.setText("휴식시간 :"+ex.get(2).getRest());
					Log.e("main","t반복횟수(현재)"+ex.get(2).getInterval()+"("+(count)+")");



					
					if(Sound==false){
						vibrator.vibrate(900);
					}else{
						String toSpeak=null;
						if(fin>59&&fin%60==0){
							toSpeak = title + "를" + fin/60 + "분 시작합니다.";
						}else if(fin>59&&fin%60!=0){
							toSpeak = title + "를" + fin/60 + "분 "+(fin%60-1)+"초 시작합니다.";
						}else if(fin<60){

							toSpeak = title + "를" + (fin%60-1)+"초 시작합니다.";
						}
						Log.e("main","스피킹rrr");
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
							t1.speak(toSpeak,TextToSpeech.QUEUE_FLUSH,null,null);
						} else {
							t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
						}

					}

					view_time.setText("시간: "+ex.get(2).getTime());
					view_interval.setText("반복횟수(현재) : " +ex.get(2).getInterval()+"("+(count)+")");
					if(ex.get(2).getTime()>60){
						if(ex.get(2).getTime()%60==0){
							view_time.setText("시간:"+ex.get(2).getTime()/60+"분");
						}else{

							view_time.setText("시간:"+ex.get(2).getTime()/60+"분"+ex.get(2).getTime()%60+"초");
						}
					}




					synchronized(mHandlerTimer){
						try {
							wait(900);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					if(exercise_size==3){
						Finish=true;
						Log.e("main3","ssss"+interval_rest);
					}
					Log.e("main","운동리스트는 : 3,."+fin);
					fin_first=fin;
					position=ex.get(2).getInterval();
					restingTime=ex.get(2).getRest();
					//mHandler.postDelayed(mHandlerTimer,0);
					third="4";
					jump="";
					run_check=false;
					interval_rest=false;
					//mHandler.removeCallbacks(mHandlerTimer);
				}
				if(jump.equals("four")){
					Finish=false;
					count=1;
					WhatNow=3;
					title=ex.get(3).getTitle();
					fin=ex.get(3).getTime()+1;
					String toSpeak=title+"를"+(fin-1)+"초 시작합니다.";
					if(Sound==false){
						vibrator.vibrate(900);
					}else{
						if(fin>59&&fin%60==0){
							toSpeak = title + "를" + fin/60 + "분 시작합니다.";
						}else if(fin>59&&fin%60!=0){
							toSpeak = title + "를" + fin/60 + "분 "+(fin%60-1)+"초 시작합니다.";
						}else if(fin<60){

							toSpeak = title + "를" + (fin%60-1)+"초 시작합니다.";
						}
						Log.e("main","스피킹rrr");
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
							t1.speak(toSpeak,TextToSpeech.QUEUE_FLUSH,null,null);
						} else {
							t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
						}

					}
					synchronized(mHandlerTimer){
						try {
							wait(900);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					Log.e("main","네번째1 운동 시작한다."+ex.get(3).getTime()+"초");
					//mHandler.removeCallbacks(mHandlerTimer);
					total_size.setText("총 운동수 "+ex.size()+"가지중 4번째 운동입니다.");
					view_title.setText(""+ex.get(3).getTitle());
					view_rest.setText("휴식시간 :"+ex.get(3).getRest());
					Log.e("main","f반복횟수"+ex.get(3).getInterval()+"("+(count)+")");
					view_interval.setText("반복횟수(현재) : " +ex.get(3).getInterval()+"("+(count)+")");
					if(ex.get(3).getTime()>60){
						if(ex.get(3).getTime()%60==0){
							view_time.setText("시간:"+ex.get(3).getTime()/60+"분");
						}else{

							view_time.setText("시간:"+ex.get(3).getTime()/60+"분"+ex.get(3).getTime()%60+"초");
						}
					}else{

						view_time.setText("시간: "+ex.get(3).getTime());
					}
					if(exercise_size==4){
						Finish=true;
					}
					Log.e("main","운동리스트는 : 3,."+fin);
					fin_first=fin;
					position=ex.get(3).getInterval();
					restingTime=ex.get(3).getRest();
					//mHandler.postDelayed(mHandlerTimer,0);
					third="5";
					jump="";
					run_check=false;
					interval_rest=false;
					thhe_end=true;
					//mHandler.removeCallbacks(mHandlerTimer);
				}

				if(jump.equals("five")){
					Finish=false;
					count=1;
					WhatNow=4;
					title=ex.get(4).getTitle();
					fin=ex.get(4).getTime()+1;
					if(Sound==false){
						vibrator.vibrate(900);
					}else{
						String toSpeak=null;
						if(fin>59&&fin%60==0){
							toSpeak = title + "를" + fin/60 + "분 시작합니다.";
						}else if(fin>59&&fin%60!=0){
							toSpeak = title + "를" + fin/60 + "분 "+(fin%60-1)+"초 시작합니다.";
						}else if(fin<60){

							toSpeak = title + "를" + (fin%60-1)+"초 시작합니다.";
						}
						Log.e("main","스피킹rrr");
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
							t1.speak(toSpeak,TextToSpeech.QUEUE_FLUSH,null,null);
						} else {
							t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
						}

					}
					synchronized(mHandlerTimer){
						try {
							wait(900);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					Log.e("main","5번째1 운동 시작한다."+ex.get(4).getTime()+"초");
					//mHandler.removeCallbacks(mHandlerTimer);
					total_size.setText("총 운동수 "+ex.size()+"가지중 5번째 운동입니다.");
					view_title.setText(""+ex.get(4).getTitle());
					view_rest.setText("휴식시간 :"+ex.get(4).getRest());
					Log.e("main","f반복횟수"+ex.get(4).getInterval()+"("+(count)+")");
					view_interval.setText("반복횟수(현재) : " +ex.get(4).getInterval()+"("+(count)+")");
					if(ex.get(4).getTime()>60){
						if(ex.get(4).getTime()%60==0){
							view_time.setText("시간:"+ex.get(4).getTime()/60+"분");
						}else{

							view_time.setText("시간:"+ex.get(4).getTime()/60+"분"+ex.get(4).getTime()%60+"초");
						}
					}else{

						view_time.setText("시간: "+ex.get(4).getTime());
					}
					if(exercise_size==5){
						Finish=true;
					}
					Log.e("main","운동리스트는 : 4,."+fin);
					fin_first=fin;
					position=ex.get(4).getInterval();
					restingTime=ex.get(4).getRest();
					//mHandler.postDelayed(mHandlerTimer,0);
					third="5";
					jump="";
					run_check=false;
					interval_rest=false;
					thisisEnd=true;
					//mHandler.removeCallbacks(mHandlerTimer);
				}
				if(jump.equals("two")){
					Finish=false;
					count=1;
					WhatNow=1;
					title=ex.get(1).getTitle();
					fin=ex.get(1).getTime()+1;
					if(Sound==false){
						vibrator.vibrate(900);
					}else{
						String toSpeak=null;
						if(fin>59&&fin%60==0){
							toSpeak = title + "를" + fin/60 + "분 시작합니다.";
						}else if(fin>59&&fin%60!=0){
							toSpeak = title + "를" + fin/60 + "분 "+(fin%60-1)+"초 시작합니다.";
						}else if(fin<60){
							toSpeak = title + "를" + (fin%60-1)+"초 시작합니다.";
						}
						Log.e("main","스피킹rrr");
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
							t1.speak(toSpeak,TextToSpeech.QUEUE_FLUSH,null,null);
						} else {
							t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
						}
					}
					synchronized(mHandlerTimer){
						try {
							wait(900);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					Log.e("main3","2번째13 운동 시작한다."+ex.get(1).getTime()+"초");
					//mHandler.removeCallbacks(mHandlerTimer);
					total_size.setText("총 운동수 "+ex.size()+"가지중 2번째 운동입니다.");
					if(exercise_size==2){
						Finish=true;
					}
					view_title.setText(""+ex.get(1).getTitle());
					view_rest.setText("휴식시간 :"+ex.get(1).getRest());
					view_interval.setText("반복횟수(현재) : " +ex.get(1).getInterval()+"("+(count)+")");
					if(ex.get(1).getTime()>60){
						if(ex.get(1).getTime()%60==0){
							view_time.setText("시간:"+ex.get(1).getTime()/60+"분");
						}else{

							view_time.setText("시간:"+ex.get(1).getTime()/60+"분"+ex.get(1).getTime()%60+"초");
						}
					}else{

						view_time.setText("시간: "+ex.get(1).getTime());
					}

					Log.e("main","222운동리스트는 : 1,."+fin);
					fin_first=fin;
					position=ex.get(1).getInterval();
					restingTime=ex.get(1).getRest();
					third="3";
					jump="";
					run_check=false;
					flag_end=true;
					interval_rest=false;
					//mHandler.removeCallbacks(mHandlerTimer);
				}
				if(jump.equals("one")){
					count=1;
					WhatNow=0;
					Finish=false;
					title=ex.get(0).getTitle();
					fin=ex.get(0).getTime()+1;
					if(Sound==false){
						vibrator.vibrate(900);
					}else{
						String toSpeak=null;
						if(fin>59&&fin%60==0){
							toSpeak = title + "를" + fin/60 + "분 시작합니다.";
						}else if(fin>59&&fin%60!=0){
							toSpeak = title + "를" + fin/60 + "분 "+(fin%60-1)+"초 시작합니다.";
						}else if(fin<60){

							toSpeak = title + "를" + (fin%60-1)+"초 시작합니다.";
						}
						Log.e("main","스피킹rrr");
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
							t1.speak(toSpeak,TextToSpeech.QUEUE_FLUSH,null,null);
						} else {
							t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
						}

					}
					if(exercise_size==1){
						Finish=true;
					}
					synchronized(mHandlerTimer){
						try {
							wait(900);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					Log.e("main","1번째11111 운동 시작한다."+ex.get(0).getTime()+"초");
					//mHandler.removeCallbacks(mHandlerTimer);
					total_size.setText("총 운동수 "+ex.size()+"가지중 1번째 운동입니다.");
					view_title.setText(""+ex.get(0).getTitle());
					view_rest.setText("휴식시간 :"+ex.get(0).getRest());
					view_interval.setText("반복횟수(현재) : " +ex.get(0).getInterval()+"("+(count)+")");
					if(ex.get(0).getTime()>60){
						if(ex.get(0).getTime()%60==0){
							view_time.setText("시간:"+ex.get(0).getTime()/60+"분");
						}else{

							view_time.setText("시간:"+ex.get(0).getTime()/60+"분"+ex.get(0).getTime()%60+"초");
						}
					}else{

						view_time.setText("시간: "+ex.get(0).getTime());
					}
					Log.e("main","222운동리스트는 : 0,."+fin);
					fin_first=fin;
					position=ex.get(0).getInterval();
					restingTime=ex.get(0).getRest();
					//mHandler.postDelayed(mHandlerTimer,0);
					third="2";
					interval_rest=false;
					run_check=false;
					jump="";
					flag_end=false;
				}
				Log.e("main", fin+",sssss");
				if(Sound==false) {

					if(fin==3){
						Log.e("main","소리낸다. "+fin);
						vibrator.vibrate(100);
					}
					if(fin==2){
						vibrator.vibrate(100);
					}
					if(fin==1){
						vibrator.vibrate(100);
					}

				}else{
					/*MediaPlayer mPlayer=MediaPlayer.create(activity,R.raw.beep_start);
					MediaPlayer mPlayer_end=MediaPlayer.create(activity,R.raw.beep_end);
					if(fin==3){
						Log.e("main","소리낸다. "+fin);

						mPlayer.start();
					}
					if(fin==2){
						mPlayer.start();
					}
					if(fin==1){
						mPlayer_end.start();
					}*/
				}

				fin--;

				if(title.equals("운동중 쉬기")) {
					lin_lay.setBackgroundResource(R.drawable.rest);
					view_interval.setVisibility(View.INVISIBLE);
					String toSpeak="운동간 휴식시작";
					Log.e("main","운동간 휴식시작한당 "+Interval_true);
					if(Interval_true==false){
						Log.e("main","스피킹rrrzzz");
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
							t1.speak(toSpeak,TextToSpeech.QUEUE_FLUSH,null,null);
						} else {
							t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
						}
						Interval_true=true;

					}

				}else{
					view_interval.setVisibility(View.VISIBLE);
				}
				if(fin_first>10){
					if(fin==fin_first/2&&!title.equals("운동중 쉬기")){
						if(Sound==false) {
						}else {
							String speak="운동시간 절반 경과";
							t1.speak(speak,TextToSpeech.QUEUE_FLUSH,null);

						}
					}

				}


				if(fin>60){
					if(fin%60==0){
						LeftTime.setText(fin/60+"분\n");
					}else{
						LeftTime.setText(fin/60+"분\n"+fin%60+"초");
					}

				}else{

					LeftTime.setText(fin+"");
				}


				Log.e("main","count_first : "+count_first+"현재 진행중인 타이틀은 : "+title);
				Log.e("main", fin+","+restingTime+","+flag);



			}

			mHandler.postDelayed(this,1000);

		}
	};


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity=this;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		view_collection=new ArrayList<View>();

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		activity.setTheme(R.style.NO12);
		setContentView(R.layout.activity_main);
		 LeftTime=(TextView)findViewById(R.id.LeftTime);
		vibrator=(Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);
		t1= new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
			@Override
			public void onInit(int status) {
				if(status!=TextToSpeech.ERROR){
					t1.setLanguage(Locale.KOREAN);
				}
			}
		});

		ex= (ArrayList<Exercise>) getIntent().getSerializableExtra("exercise");
		SharedPreferences screen=getSharedPreferences("shared_screen",0);
		boolean screen_on=screen.getBoolean("screen",false);
		if(screen_on){
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		}else{
			getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		}
		SharedPreferences setting=getSharedPreferences("shared_interval_time",0);
		Interval =setting.getInt("interval_time",0);
		SharedPreferences setting_sound=getSharedPreferences("shared_sound",0);
		Sound=setting_sound.getBoolean("sound",true);
		Log.e("main","메인에서 받은 운동간 휴식, 음성 켬/끔 설정은 : "+Interval+"<<<"+Sound+">>>"+screen_on);

		fin=ex.get(0).getTime()+1;
		fin_first=fin;
		title=ex.get(0).getTitle();
		restingTime=ex.get(0).getRest();
		position=ex.get(0).getInterval();
		Log.e("main","Positionnnnnn : "+position);
		exercise_size=ex.size();
		total_size=(TextView)findViewById(R.id.Total_size);
		TextView before=(TextView)findViewById(R.id.before);
		TextView now=(TextView)findViewById(R.id.now);
		TextView future=(TextView)findViewById(R.id.future);
		TextView future_further=(TextView)findViewById(R.id.future_further);
		TextView futureOfFuture=(TextView)findViewById(R.id.futureOfFuture);
		total_size.setText("총 운동수 "+ex.size()+"가지중, 1번째 운동입니다.");
		if(exercise_size==1) {
			Finish=true;
			Log.e("main","사이즈는 1!!!!!!!!!!!!!!1");
			now.setText("");
			future.setText("");
			future_further.setText("");
			futureOfFuture.setText("");
			before.setText(title);
			LinearLayout.LayoutParams param=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,1.0f);

			before.setLayoutParams(param);

			third="1";
		}
		else if(exercise_size==2){
			before.setText(ex.get(0).getTitle());
			now.setText(ex.get(1).getTitle());
			LinearLayout.LayoutParams param=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT,1.0f);
			param.weight=0;
			future.setText("");
			future_further.setText("");
			futureOfFuture.setText("");

			future.setLayoutParams(param);
			future_further.setLayoutParams(param);
			futureOfFuture.setLayoutParams(param);
			//third="2";
		}else if(exercise_size==3){
			before.setText(ex.get(0).getTitle());
			now.setText(ex.get(1).getTitle());
			future.setText(ex.get(2).getTitle());
			future_further.setText("");
			futureOfFuture.setText("");
			LinearLayout.LayoutParams param=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT,1.0f);
			param.weight=0;

			future_further.setLayoutParams(param);
			futureOfFuture.setLayoutParams(param);
		}else if(exercise_size==4){
			before.setText(ex.get(0).getTitle());
			now.setText(ex.get(1).getTitle());
			future.setText(ex.get(2).getTitle());
			future_further.setText(ex.get(3).getTitle());
			futureOfFuture.setText("");
			LinearLayout.LayoutParams param=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT,1.0f);
			param.weight=0;

			futureOfFuture.setLayoutParams(param);
		}else if(exercise_size==5){
			before.setText(ex.get(0).getTitle());
			now.setText(ex.get(1).getTitle());
			future.setText(ex.get(2).getTitle());
			future_further.setText(ex.get(3).getTitle());
			futureOfFuture.setText(ex.get(4).getTitle());
		}
		Stop=(TextView)findViewById(R.id.Stop);
		Stop.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(Stop.getText().equals("일시정지")){
					mHandler.removeCallbacks(mHandlerTimer);
					String toSpeak="일시정지합니다";
					if(Sound==false){
						vibrator.vibrate(500);
					}else{
						t1.speak(toSpeak,TextToSpeech.QUEUE_FLUSH,null);

					}

					Stop.setText("다시시작");

				}else{
					Stop.setText("일시정지");
					mHandler.postDelayed(mHandlerTimer,0);

				}

				Log.e("main","정지됨");
			}
		});
		 view_title=(TextView)findViewById(R.id.view_title);
		view_title.setText(ex.get(0).getTitle());
		 view_rest=(TextView)findViewById(R.id.view_rest);
		view_rest.setText("휴식시간 :"+ex.get(0).getRest());
		 view_interval=(TextView)findViewById(R.id.view_interval);
		Log.e("main","반복횟수 : "+ex.get(0).getInterval());
		view_interval.setText("반복횟수 :"+ex.get(0).getInterval());
		view_time=(TextView)findViewById(R.id.view_time);
		if(ex.get(0).getTime()>60){
			if(ex.get(0).getTime()%60==0){
				view_time.setText("시간:"+ex.get(0).getTime()/60+"분");
			}else{

				view_time.setText("시간:"+ex.get(0).getTime()/60+"분"+ex.get(0).getTime()%60+"초");
			}
		}else{

			view_time.setText("시간: "+ex.get(0).getTime());
		}
		Log.e("main", "시간 : "+fin+"쉬는시간 : "+restingTime+"인터벌 : "+position);
		mHandler=new Handler();
		mHandler.postDelayed(mHandlerTimer,0);




	}
	public void onClick_Exercise(View v){

		String id=new Integer(v.getId()).toString();
		Log.e("main"," 클릭된222 것은 : "+"zzzz"+id);
		if(v.getId()==Integer.parseInt("2131624018")){
			//before
			jump="one";
			Log.e("main","첫번째것 클릭됨");
		}else if(id.equals("2131624020")){
			Log.e("main","3번째것 클릭됨");
			//now
			jump="three";
			Log.e("main","31번째"+third);

		}else if(id.equals("2131624019")){
			Log.e("main","2번째것 클릭됨");
			jump="two";

		}else if(id.equals("2131624021")){
			jump="four";
		}else if(id.equals("2131624022")){
			jump="five";
			Log.e("main","다섯번째 클릭됨 : "+jump);
		}
	}

	public void onBackPressed(){
		final AlertDialog.Builder alert=new AlertDialog.Builder(activity);
		alert.setTitle("종료하시겠습니까?");
		alert.setMessage("종료하시면 진행중인 데이터가 사라집니다.");
		alert.setPositiveButton("네", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				activity.finish();
				MainActivity a=new MainActivity();
				DynamicListView d=new DynamicListView(getApplicationContext());

				view_collection=Exercise_config.view_collection;
				Log.e("main","백누름 : "+view_collection+a.list_exercise_pass);
				Log.e("main",a.list_result+"변경전");
				for(int i=0; i<a.list_result.size(); i++){
					a.list_result.get(i).setSelected(false);
				}

				Log.e("main",a.list_exercise_pass+"남은것");
				d.SetView_List();
				mHandler.removeCallbacks(mHandlerTimer);
			}
		});
		alert.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});

		alert.show();

	}

}







package app.org.scit.timer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static app.org.scit.timer.R.drawable.delete;

/**
 * Created by jpd on 2016-09-13.
 */
public class Exercise_config extends BaseAdapter {
	final int INVALID_ID = -1;
	/*

	HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

	public Exercise_config(Context context, int textViewResourceId, List<String> objects) {
		super(context, textViewResourceId, objects);
		Log.e("main","id : "+textViewResourceId);
		for (int i = 0; i < objects.size(); ++i) {
			mIdMap.put(objects.get(i), i);
			Log.e("main",objects.get(i));
		}
	}

	@Override
	public long getItemId(int position) {
		if (position < 0 || position >= mIdMap.size()) {
			return INVALID_ID;
		}
		String item = getItem(position);
		return mIdMap.get(item);
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}*/
	private Activity activity;
	private ArrayList<String >text;
	private ArrayList<Integer>Rest;
	private ArrayList<Integer>Interval;
	private ArrayList<Integer>Time;
	private ArrayList<Exercise>exercise_list;
	private View view;
	private Exercise exercise;
	private Exercise_config thisis;
	static public ArrayList<View> view_collection=new ArrayList<View>();

	TextView title1;
	private Rect mHoverCellCurrentBounds;
	private LongPressChecker mLongcheck;
	private int mDownX;
	private int mDownY;


	Exercise_config(Activity activity){
		Log.e("main" ,"리스트뷰 생성자 들어왔다. ");
		this.activity = activity;
		text=new ArrayList<String>();
		Rest=new ArrayList<Integer>();
		Interval=new ArrayList<Integer>();
		Time=new ArrayList<Integer>();
		exercise=new Exercise();

		exercise_list=new ArrayList<Exercise>();

	}

	public void setExercise(Exercise ex){
		exercise=ex;
		exercise_list.add(ex);
		text.add(exercise.getTitle());
		Rest.add(exercise.getRest());
		Interval.add(exercise.getInterval());
		Time.add(exercise.getTime());

	}

	public void setExercise_List(ArrayList<Exercise> exercise_listof){
		Log.e("main","setExercise 옴 : "+exercise_listof);
		exercise_list=exercise_listof;
		for(int i=0; i<exercise_list.size(); i++){
			text.add(exercise_list.get(i).getTitle());
			Rest.add(exercise_list.get(i).getRest());
			Interval.add(exercise_list.get(i).getInterval());
			Time.add(exercise_list.get(i).getTime());


		}

	}

	@Override
	public int getCount() {
		Log.e("main", "카운트 : "+text.size());
		return exercise_list.size();
	}

	@Override
	public Object getItem(int position) {
		return exercise_list.get(position);
	}


	@Override
	public long getItemId(int position) {

		if (position < 0 || position >= exercise_list.size()) {
			return INVALID_ID;
		}
		//String item = getItem(position);
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Log.e("main","getView");
		ListViewHolder holder=null;
		TextView title;
		TextView exercise_rest;
		TextView exercise_interval;
		TextView exercise_time;
		final CheckBox checkbox;
		mLongcheck=new LongPressChecker(activity.getApplicationContext());

		if(convertView==null){
			thisis=new Exercise_config(activity);
			Log.e("main","converView들어옴");
			LayoutInflater inflater=(LayoutInflater)activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView=inflater.inflate(R.layout.list_exercise,parent,false);
			title = (TextView) convertView.findViewById(R.id.text_title1);
			exercise_interval=(TextView)convertView.findViewById(R.id.exercise_interval);
			exercise_rest=(TextView)convertView.findViewById(R.id.exercise_rest);
			exercise_time=(TextView)convertView.findViewById(R.id.exercise_time);
			checkbox=(CheckBox)convertView.findViewById(R.id.checkBox);
			holder=new ListViewHolder();

			holder.title=title;
			holder.exercise_rest=exercise_rest;
			holder.exercise_interval=exercise_interval;
			holder.exercise_time=exercise_time;
			holder.checkbox=checkbox;
			holder.checkbox.setClickable(false);
			holder.checkbox.setFocusable(false);
			convertView.setTag(holder);

		}else{
			Log.e("main", "getView!=null");
			// list values get
			//holder = (ListViewHolder) convertView.getTag();
			holder=(ListViewHolder) convertView.getTag();
			title=holder.title;
			exercise_interval=holder.exercise_interval;
			exercise_rest=holder.exercise_rest;
			exercise_time=holder.exercise_time;

			checkbox=holder.checkbox;

		}
		Log.e("main","Position : "+position);
		holder.title.setText(""+text.get(position));
		if(Time.get(position)>60){

			int minute=(exercise_list.get(position).getTime())/60;
			int second=(exercise_list.get(position).getTime())%60;
			Log.e("main",position+"2222222번째 " +minute+second);
			if(second==0){
				holder.exercise_time.setText("운동시간:"+minute+"분");
			}else{

				holder.exercise_time.setText("운동시간:"+minute+"분"+second+"초");
			}
		}else{
			holder.exercise_time.setText("운동시간:"+Time.get(position));
		}
		holder.exercise_interval.setText("반복 횟수:"+Interval.get(position));
		holder.exercise_rest.setText("쉬는시간 :"+Rest.get(position));

		holder.checkbox.setChecked(exercise.isSelected());

		title.setVisibility(View.VISIBLE);
		exercise_interval.setVisibility(View.VISIBLE);
		exercise_rest.setVisibility(View.VISIBLE);
		exercise_time.setVisibility(View.VISIBLE);
		checkbox.setVisibility(View.VISIBLE);
		final ListViewHolder finalHolder1 = holder;

		if(holder.checkbox.isChecked()){
			Log.e("main","체크박스 체크됨!!");
		}
		final ListViewHolder finalHolder2 = holder;
		convertView.setOnTouchListener(new View.OnTouchListener() {


			@Override
			public boolean onTouch(View v, MotionEvent event) {
				view_collection.add(v);
				mLongcheck.deliverMotionEvent(v,event);
				mLongcheck.setOnLongPressListener(new LongPressChecker.OnLongPressListener() {
					@Override
					public void onLongPressed() {
						Log.e("main", exercise_list.get(position)+"" );
						final AlertDialog.Builder delete=new AlertDialog.Builder(activity).setTitle("삭제").setMessage("선택한 운동("+exercise_list.get(position).getTitle()+")을 삭제하시겠습니까?").setPositiveButton("네", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {

								Log.e("main",",,,,,,!!!!!"+exercise_list);
								MainActivity.db.execSQL("delete from exercise where exer_id="+exercise_list.get(position).getExer_id());
								Cursor cursor;
								exercise_list.removeAll(exercise_list);
								Log.e("main","다지워짐"+exercise_list);
								cursor=MainActivity.db.rawQuery("select * from exercise",null);
								while(cursor.moveToNext()){

									int exer_id=cursor.getInt(0);
									String exer_title=cursor.getString(1);
									int exer_time=cursor.getInt(2);
									int exer_rest=cursor.getInt(3);
									int exer_interval=cursor.getInt(4);
									Exercise exercise=new Exercise(exer_id,exer_interval,exer_rest,exer_time,exer_title,false);
									exercise_list.add(exercise);
									Log.e("main","id : "+exer_id+"Dddd"+exer_title+"<<<<"+exer_id+"<<<<");

								}

								Log.e("main",",,,!!!"+exercise_list);



								thisis.setExercise_List(exercise_list);
								MainActivity.listView.setAdapter(thisis);
							}
						})
								.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {

									}
								});

						delete.show();


					}
				});
				switch (event.getActionMasked()){
					case MotionEvent.ACTION_DOWN:
						mDownX = (int)event.getX();
						mDownY = (int)event.getY();
						Log.e("main",exercise_list.get(position)+"");
						boolean flag=exercise_list.get(position).selected;
						exercise_list.get(position).setSelected(!flag);
						Log.e("main",exercise_list.get(position)+"");
						finalHolder1.checkbox.setChecked(exercise_list.get(position).selected);
				Log.e("main","zzzzzzzzzzzzzzzzzzz"+exercise_list.get(position)+""+event.getAction());
						v.setBackgroundResource(R.color.main_clicked);

					break;
					case MotionEvent.ACTION_UP:
						v.setBackgroundColor(Color.WHITE);
						if(finalHolder2.checkbox.isChecked()){
							v.setBackgroundResource(R.color.main_clicked);
						}else{
							v.setBackgroundColor(Color.WHITE);
						}
						break;


			}
				return true;
			}
		});
		convertView.setOnLongClickListener(new View.OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				Log.e("main","longclicked listener");
				return false;
			}
		});

		final ListViewHolder finalHolder = holder;

		convertView.setLayoutParams(new ListView.LayoutParams(ListView.LayoutParams.MATCH_PARENT,200));


		convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {


				exercise.setSelected(!exercise.isSelected());
				exercise.selected=!exercise.isSelected();
				finalHolder.checkbox.setChecked(!exercise.isSelected());
				exercise.setSelected(!exercise.isSelected());
				Cursor cursor;
				cursor=MainActivity.db.rawQuery("select * from exercise",null);
				while(cursor.moveToNext()){
					int exer_id=cursor.getInt(0);
					String exer_title=cursor.getString(1);
					String selected=cursor.getString(5);
					Log.e("main",exer_title+"<<<<"+exer_id+"<<<<"+selected);
				}
				Log.e("main",exercise.isSelected()+","+exercise);

				exercise.selected=!exercise.isSelected();
				finalHolder.checkbox.setChecked(!exercise.isSelected());


			}
		});

		return convertView;
	}


	private class ListViewHolder {
		TextView title;
		TextView exercise_time;
		TextView exercise_rest;
		TextView exercise_interval;
		CheckBox checkbox;

	}
}

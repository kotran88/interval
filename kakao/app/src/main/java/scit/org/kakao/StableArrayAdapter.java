/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package scit.org.kakao;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StableArrayAdapter extends ArrayAdapter<Exercise> {

    final Listener listener;
    final int INVALID_ID = -1;
    public interface Listener{
        void onGrab(int position, RelativeLayout row);
    }
    HashMap<Exercise, Integer> mIdMap = new HashMap<Exercise, Integer>();

  /*  public StableArrayAdapter(Context context, int textViewResourceId, List<String> objects) {
        super(context, textViewResourceId,objects);

        for (int i = 0; i < objects.size(); ++i) {
            Log.e("main",objects.get(i)+"번째");
            mIdMap.put(objects.get(i), i);
        }
    }*/
  public View getView(final int position, View view, ViewGroup parent) {
      Context context = getContext();
      Exercise data = getItem(position);
      int time=data.getTime();
      if(null == view) {

          view = LayoutInflater.from(context).inflate(R.layout.list_row, null);

      }



      final RelativeLayout row = (RelativeLayout) view.findViewById(
              R.id.lytPattern);

      TextView textView = (TextView)view.findViewById(R.id.Exercise_Title);
      textView.setText(data.title);

      TextView timerange = (TextView)view.findViewById(R.id.Exercise_Time12);
      if(time>60){

          int minute=time/60;
          int second=time%60;
          Log.e("main",position+"2222222번째 " +minute+","+second+data.getTitle());
          if(second==0&&minute!=0){
              timerange.setText(minute+"분");
          }


          if(minute!=0&&second!=0)
          {
              timerange.setText(minute+"분"+second+"초");

          }
      }else{
          int second=time%60;
              timerange.setText(second+"초");
      }

      TextView rest_time=(TextView)view.findViewById(R.id.Exercise_RestTime);
      rest_time.setText("휴식시간 : "+data.rest+"");
      TextView interval=(TextView)view.findViewById(R.id.Exercise_IntervalCount);
      interval.setText("반복횟수 : "+data.getInterval()+"");
      view.findViewById(R.id.imageViewGrab)
              .setOnTouchListener(new View.OnTouchListener() {
                  @Override
                  public boolean onTouch(View v, MotionEvent event) {
                      listener.onGrab(position, row);
                      return false;
                  }
              });

      return view;
  }
    public StableArrayAdapter(Context context, List<Exercise>list, Listener listener) {
        super(context,0,list);
        this.listener=listener;
        for(int i=0; i<list.size(); i++){
            mIdMap.put(list.get(i),i);
        }
    }
    public StableArrayAdapter(Context context, List<Exercise>list) {
        super(context,0,list);
        //mIdMap.clear();
        for(int i=0; i<list.size(); i++){
            mIdMap.put(list.get(i),i);
        }
        listener = null;
    }
    public void setmIdMap(ArrayList<Exercise>list){
        mIdMap.clear();
        for(int i=0; i<list.size();i++){
            mIdMap.put(list.get(i),i);

        }
    }
    @Override
    public long getItemId(int position) {
        if (position < 0 || position >= mIdMap.size()) {
            return INVALID_ID;
        }
        Exercise item = getItem(position);
        return mIdMap.get(item);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}

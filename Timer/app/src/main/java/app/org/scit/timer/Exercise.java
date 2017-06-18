package app.org.scit.timer;

import java.io.Serializable;

/**
 * Created by jpd on 2016-09-13.
 */
public class Exercise implements Serializable {
	int exer_id;
	String title;
	int time;
	int rest;
	int interval;
	boolean selected=false;

	public Exercise(){

	}
	public Exercise(int interval, int rest, int time, String title, boolean selected) {
		this.interval = interval;
		this.rest = rest;
		this.time = time;
		this.title = title;
		this.selected=selected;

	}
	public Exercise(int exer_id,int interval, int rest, int time, String title, boolean selected) {
		this.exer_id=exer_id;
		this.interval = interval;
		this.rest = rest;
		this.time = time;
		this.title = title;
		this.selected=selected;

	}

	public int getExer_id() {
		return exer_id;
	}

	public void setExer_id(int exer_id) {
		this.exer_id = exer_id;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public int getRest() {
		return rest;
	}

	public void setRest(int rest) {
		this.rest = rest;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	@Override
	public String toString() {
		return "Exercise{" +
				"exer_id=" + exer_id +
				", title='" + title + '\'' +
				", time=" + time +
				", rest=" + rest +
				", interval=" + interval +
				", selected=" + selected +
				'}';
	}
}

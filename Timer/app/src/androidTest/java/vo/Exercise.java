package vo;

/**
 * Created by jpd on 2016-09-13.
 */
public class Exercise {
	String title;
	int time;
	int rest;
	int interval;

	public Exercise(int interval, int rest, int time, String title) {
		this.interval = interval;
		this.rest = rest;
		this.time = time;
		this.title = title;
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

	@Override
	public String toString() {
		return "Exercise{" +
				"interval=" + interval +
				", title='" + title + '\'' +
				", time=" + time +
				", rest=" + rest +
				'}';
	}
}

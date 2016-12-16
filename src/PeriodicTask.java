
public class PeriodicTask {
	String tn;
	int phase;
	int period;
	int exe_time;
	int deadline;
	int num_res;
	int order;
	int count;
	int jn;
	
	public PeriodicTask(String t, int ph, int p,int e, int d, int n,int o) {
		// TODO Auto-generated constructor stub                      //--------------->Constructor to initialize periodic tasks
		this.tn = t;
		this.phase = ph;
		this.period = p;
		this.exe_time = e;
		this.deadline = d;
		this.num_res = n;
		this.order = o;
		this.count = 0;
		this.jn = 0;
	}

}

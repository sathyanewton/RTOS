import java.util.ArrayList;


public class Task {
	int sim_stop_time;
	int num_tasks;
	
	PeriodicTask p;
	ArrayList<Resource> r;
	int n;
	
	ArrayList <Task> al = new ArrayList<Task>();
	
	public Task()
	{
		this.p = null;
		this.r = null;
	}
	public Task(PeriodicTask pt, ArrayList<Resource> r)         //---------------->Constructor to initialize tasks
	{
		this.p = pt;
		this.r = r;
	}

}

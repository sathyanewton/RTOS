
public class Lock {
	String lock_name;
	int lock_var;
	String task_name;
	
	public Lock(String l,int v)        //------------->Constructor for initializing resource locks
	{
		this.lock_name = l;                   
		this.lock_var = 0;
		this.task_name = "";
	}
	
	public int acquire_lock(String task_name)
	{
		if(lock_var == 0)               //--------->On acquire request,check if the lock variable is 0,then allocate 
		{
			lock_var = 1;
			this.task_name = task_name;  //---->Setting the task name requesting the resource in the lock
			return 1;
		}
		else
			return 0;
		
	}
	
	public int release_lock(String task_name)           //------------>release lock
	{
		if(lock_var == 1 && task_name.equals(get_lock_taskname()))
		{
			this.task_name = "";
			lock_var = 0;
			return 1;
		}
		return 0;
	}
    public String get_lock_taskname()
    {
    	return this.task_name;
    }
}

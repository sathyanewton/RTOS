import java.util.ArrayList;
import java.util.Collections;


public class Scheduler {
	ArrayList<Task> al = new ArrayList<Task>();
	
	int sim_stop;
	int num_tasks;
	static int num_res = 3;
	public Scheduler(ArrayList<Task> a,int s,int n )
	{
		this.al = a;
		this.sim_stop = s;
		this.num_tasks = n;
	}
	@SuppressWarnings("unchecked")
	public int EDF_Scheduler()
	{
		ArrayList<Task> out = new ArrayList<Task>();
		ArrayList<Task>[] res = new ArrayList[num_res];
		for(int k=0; k<num_res; k++)
		{
			res[k] = new ArrayList<Task>();
		}
		
		Task currentprio_task = null;
		
		int[] flag = new int[num_res] ;
		for(int k=0; k<num_res; k++)
		{
			flag[k] = 0;
		}

		Lock[] locks = new Lock[num_res];
		for(int k=0; k<num_res; k++)
		{
			int t = k+1;
			String l = "R" + t;
			locks[k] = new Lock(l,0);
		}
		int flagc = 0;
		
		System.out.println();
		System.out.println("Time\tJob\t\tActions\t\t\tCurrent priority");
		System.out.println("--------------------------------------------------------------------------");
		for(int i=0;i<=sim_stop;i++)
		{
			for(Task t:al)
			{
				if((t.p.jn * t.p.period) + t.p.phase == i)
				{
					out.add(t);                        //----->If phase is equal to time instant,add task to the queue
					Collections.sort(out,new TaskComp());             //------->Sort the task queue 
					currentprio_task = out.get(0);
					t.p.jn++;
				}
			}
			/************************************* EDF Functionality ***************************************/
			/*if(out.size()!=0)
			{
				System.out.println(i + "\t" + out.get(0).p.tn);
				out.get(0).p.count++;
				if(out.get(0).p.count == out.get(0).p.exe_time)
				{
					out.remove(0);
				}
			}
			else
				System.out.println(i + "\t" + "No task running");*/
			/********************* EDF Functionality  + PIP functionality ***************************************/
			if(out.size()!=0)
			{
			  flagc = 0;
			  int flagt =0;
			  for(int k=0; k<num_res; k++)
				{
					flag[k] = 0;
				}
			  int[] start = new int[num_res];
			  for(int k=0; k < num_res ;k++)
			  {
				  try
				  {
					  start[k] = out.get(0).r.get(k).res_start + out.get(0).p.phase;
				  }catch(IndexOutOfBoundsException ie)
				  {
					  start[k] = 7777777;
				  }
			
				 if(i >= start[k] && !(out.get(0).r.get(k).res_count == out.get(0).r.get(k).res_len) && (out.get(0).p.count == out.get(0).r.get(k).res_start))
				   {
							  if(!((ArrayList<Task>) res[k]).contains(out.get(0)))
							  {
								  ((ArrayList<Task>) res[k]).add(out.get(0));
							  }
							  if(locks[k].get_lock_taskname() == "")
							  {
								  flag[k] = locks[k].acquire_lock(out.get(0).p.tn);  //-------->If resource1 is currently free,allocate it 
							  }
							  else 
							  {
								  if(locks[k].get_lock_taskname() == out.get(0).p.tn )
								  {
									 //continue;                            //------>If resource is allocated to the current task,continue 
								  }
								  else
								  {
									  Task temp = null;                    //-->Otherwise, get the task that has the resource and put it in head
									  for(Task t:out)                      //--> of the queue and the highest priority task blocks on the resource
									  {
										  if(t.p.tn.equals(locks[k].get_lock_taskname())) 
											  temp = t;
									  }
									  int index = out.indexOf(temp);
									  out.remove(index);
									  out.add(0, temp);
									 /************************Rescan resource request****************************/
									  for(int j=0; j < num_res ;j++)
									  {
										  try
										  {
											  start[j] = out.get(0).r.get(j).res_start + out.get(0).p.phase;
										  }catch(IndexOutOfBoundsException ie)
										  {
											  start[j] = 7777777;
										  }
										  if(i >= start[j] && !(out.get(0).r.get(j).res_count == out.get(0).r.get(j).res_len) && (out.get(0).p.count == out.get(0).r.get(j).res_start))
										   {
													  if(!((ArrayList<Task>) res[j]).contains(out.get(0)))
													  {
														  ((ArrayList<Task>) res[j]).add(out.get(0));
													  }
													  else
													  {
														/*  int in = ((ArrayList<Task>) res[j]).indexOf(out.get(0));
														  ((ArrayList<Task>) res[j]).remove(in);
														  ((ArrayList<Task>) res[j]).add(0, out.get(0));*/
													  }
													  if(locks[j].get_lock_taskname() == "")
													  {
														  flag[j] = locks[j].acquire_lock(out.get(0).p.tn);  //-------->If resource1 is currently free,allocate it 
													      //System.out.println("\t" + flag[j]);
													  }
													  else 
													  {
														  if(locks[j].get_lock_taskname() == out.get(0).p.tn )
														  {
															 //continue;                            //------>If resource is allocated to the current task,continue 
														  }
											          }
									       }
								     }
							  }
				     }
			      }
			  }
				   System.out.print("|" + i + "|" + "\t" + "|" + out.get(0).p.tn + out.get(0).p.jn + "|");
					  out.get(0).p.count++;
		    for(int k=0; k < num_res ;k++)
			{  
			  try
			   {
				  if(((ArrayList<Task>) res[k]).size()!= 0 )
				  {
					  //System.out.println("k:" + k + "\t" + ((ArrayList<Task>) res[k]).get(0).p.tn);
					  if(((ArrayList<Task>) res[k]).contains(out.get(0)))
					  {
						//  System.out.println("In lock");
						  if(flag[k]==1)
						  {
							 // System.out.println("In lock:" + k);
							  int t = k+1;
							  System.out.print("\t lock R" + t );
							  //flag[k] = 0;
							  flagt =1;
						  }
						//int in = ((ArrayList<Task>) res[k]).indexOf(out.get(0))	;  
						  out.get(0).r.get(k).res_count++;
					  }
				  }
			   }catch(IndexOutOfBoundsException ie)
				  {
					  
				  }
		    }
			  for(int k=0; k < num_res ;k++)
				{  
					  try
					   {
						  if(((ArrayList<Task>) res[k]).get(((ArrayList<Task>) res[k]).indexOf(out.get(0))).r.get(k).res_count == ((ArrayList<Task>) res[k]).get(((ArrayList<Task>) res[k]).indexOf(out.get(0))).r.get(k).res_len)
						   {
							  int t = k+1;
							  System.out.print("\tUnlock R"+t);
							  ((ArrayList<Task>) res[k]).get(0).r.get(k).res_count = 0;
							  locks[k].release_lock(out.get(0).p.tn);
							  ((ArrayList<Task>) res[k]).remove(((ArrayList<Task>) res[k]).indexOf(out.get(0)));
							  flagt=1;
						   }
					  }
			          catch(IndexOutOfBoundsException ie)
			          {
				  
			          }
			    }
		    for(int k=0; k < num_res ;k++)
			{  
			  try
			   {
				  if(!((currentprio_task.p.tn).equals(out.get(0).p.tn)))  //-->If the highest priority task is executing, print it
				  {
					  if(flagc!=1)
					  {
						  if(flagt==1)
							  System.out.println("\t\t\t" + "|" +  currentprio_task.p.tn + currentprio_task.p.jn + "|");
						  else
							  System.out.println("\t\t\t\t\t " + "|" +  currentprio_task.p.tn + currentprio_task.p.jn + "|"); 
					  }
					  
					  flagc = 1;
					  if(((ArrayList<Task>) res[k]).size()!= 0  && ((ArrayList<Task>) res[k]).contains(currentprio_task))
					  {
						  //System.out.println("In res k:" + ((ArrayList<Task>) res[k]).get(0).p.tn + "\t" + k);
						  Collections.sort(out,new TaskComp());             //------->Sort the task queue 
						  if(((ArrayList<Task>) res[k]).get(0).p.tn.equals(currentprio_task.p.tn))
						  {
							  Collections.sort(out,new TaskComp());             //------->Sort the task queue 
						      //System.out.println(out.get(0).p.tn);
						  }
					  }
				  }
			   }
			  catch(IndexOutOfBoundsException ie)
			  {
				  
			  }
			}
			   
			  if(out.get(0).p.count == out.get(0).p.exe_time)
				{
				   out.get(0).p.count = 0;
					out.remove(0);                         //--->If the task execution requirement is done,remove it from queue
					try{
						currentprio_task = out.get(0);            
					}catch(IndexOutOfBoundsException ie)
					{
						//System.out.println("No task running");
					}
				}
      	      System.out.println();
			 }
			
			else
				System.out.println("|" + i + "|" + "\t" + "/---------------No task to run---------------------/");
			
			/****************************To check if a task has completed execution ****************/
			
		}
		return 0;
	}

}

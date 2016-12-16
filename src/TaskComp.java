import java.util.Comparator;


public class TaskComp implements Comparator<Task>{
 /********************This is used for comparing tasks based on deadlines for EDF scheduling**********/	
	public int compare(Task t1,Task t2)
	{
		if((t1.p.deadline + t1.p.phase) > (t2.p.deadline + t2.p.phase))
		{
			return 1;
		}
		else if((t1.p.deadline + t1.p.phase) == (t2.p.deadline + t2.p.phase))
		{
			if(t1.p.phase > t2.p.phase)
			{
				return 1;
			}
			else if(t1.p.phase == t2.p.phase)
			{
				if(t1.p.order > t2.p.order)
				{
					return 1;
				}
				else
					return -1;
			}
			else
				return -1;
		}
		else
			return -1;
	}

}

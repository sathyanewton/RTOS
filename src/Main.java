import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

public class Main {
	//static int num_res = 3;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int sim_stop_time = 0;
		int num_tasks = 0;
		
		int j = 1;
		int k = 1;
		try{
		URL path = ClassLoader.getSystemResource("Trace1.txt");           //---------->Input file
		if(path==null) {
		     System.out.println("Specified input file cannot be found");
		}
		File f = new File(path.toURI());
		
		
		String thisLine = null;
		BufferedReader br = null;
		ArrayList<Task> al = new ArrayList<Task>();
		try { 
			br = new BufferedReader(new FileReader(f));                  //------------->Parsing the input file
			sim_stop_time = Integer.parseInt(br.readLine());
			num_tasks = Integer.parseInt(br.readLine());
			System.out.println("Simulation stop time:" + sim_stop_time);
			System.out.println("Number of tasks:" + num_tasks);
			System.out
					.println("/***************************Input****************************/");
			while ((thisLine = br.readLine()) != null) {
				System.out.println(thisLine);
				String[] sArray = thisLine.trim().split("\\s+|\t");
				j = 1;
			try{
				PeriodicTask pt = new PeriodicTask(sArray[0],                  //---------->Initializing periodic tasks and resources
						Integer.parseInt(sArray[1]),
						Integer.parseInt(sArray[2]),
						Integer.parseInt(sArray[3]),
						Integer.parseInt(sArray[4]),
						Integer.parseInt(sArray[5]), k);
				ArrayList<Resource> rl = new ArrayList<Resource>();
				for (int i = 0; i < Integer.parseInt(sArray[5]); i++) {
					String in = "R" + (i+1);
					//System.out.println(in);
					if(!(sArray[6+i+j-1].equals(in)))
					{
						for(int z = 0; z < (Integer.parseInt(sArray[6].substring(1))-1);z++)
						{
							Resource r = new Resource("0",0,0);
							rl.add(r);
						}
						Resource r1 = new Resource(sArray[5 + i + j],
								Integer.parseInt(sArray[5 + i + j + 1]),
								Integer.parseInt(sArray[5 + i + j + 2]));
						rl.add(r1);
					}
					else
					{
						Resource r1 = new Resource(sArray[5 + i + j],
								Integer.parseInt(sArray[5 + i + j + 1]),
								Integer.parseInt(sArray[5 + i + j + 2]));
						rl.add(r1);
					}	
					j = j + 2;
				}

				Task t = new Task(pt, rl);
				// System.out.println(t.p.order + " " + t.p);
				al.add(t);
				k++;
				
			 }catch(Exception e)
			 {
				System.out.println("^Invalid input task");
				continue;
			 }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println();
		System.out
				.println("/************************** Output **************************/");
		Scheduler sch = new Scheduler(al, sim_stop_time, num_tasks);
	    sch.EDF_Scheduler();                                                       //---------------->Invoking the scheduler
		System.out.println("/*************************** End of execution **************************/");
		}catch(URISyntaxException e)
		{
			System.out.println("Specified input file cannot be found");
		}
	}

}

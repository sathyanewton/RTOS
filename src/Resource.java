
public class Resource {
	
	String res;
	int res_start;
	int res_len;
	int res_count;
	int res_flag;
 
	public Resource(String string, int rs, int rl) {
		// TODO Auto-generated constructor stub         //----------->Constructor to initialize resources associated with periodic tasks
		this.res = string;
		this.res_start = rs;
		this.res_len = rl;
		this.res_count = 0;
		this.res_flag = 0;
	}
}

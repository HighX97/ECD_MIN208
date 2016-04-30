package main;

import java.util.ArrayList;

public class MyArrayListSql extends ArrayList<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	MyArrayListSql()
	{
		super();
	}
	public String toString() {
		String rslt="";
		int i=1;
		for (Object o : this)
		{	
			System.out.println(i);
			System.out.println(this.size());
			if (i == this.size())
			{
				
				rslt +=" "+o+" ";
				System.out.println("coucou : "+rslt);
			}
			else
			{
				
				rslt +=" "+o+",";
				System.out.println(rslt);
			}
			i++;
		}
	    return rslt;
	    } 

}

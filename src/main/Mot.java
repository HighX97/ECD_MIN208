package main;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class Mot
{
	//Attributs
	//Member
	private int id;
	private String value;
	private int tf_max;
	private int tf_min;
	private int tf_cumule;
	private int df;
	private double idf;
	private double tf_idfmax;
	private double tf_idfmin;
	private double tf_idfcumule;
	private int polarite_negative;
	private int polarite_positive;


	private boolean[] boolMod; Our_occurence[] occurrence;
	private int[] tf;
	private double[] tf_idf;
	private int occurrenceTotal;
	
	private int[] polarite;

	//Classe
	private static int count=0;

	//
	Mot()
	{
		this.setId(++count);
		setOccurrenceTotal(0);
		occurrence=new Our_occurence[2000];
		boolMod = new boolean[2000];
		tf=new int[2000];
		tf_idf=new double[2000];
		df=0;
		occurrenceTotal=0;
		value="";
		tf_max=0;
		tf_min=Integer.MAX_VALUE;
		tf_cumule=0;
		idf=0.0;
		tf_idfmax=0.0;
		tf_idfmin=(double) Integer.MAX_VALUE;
		tf_idfcumule =0.0;
		polarite_negative=0;
		polarite_positive=0;
		polarite = new int[2000];
	}

	Mot(String value)
	{
		this();
		this.setValue(value);
	}

	Mot(Mot mot)
	{
		this();
		this.setValue(mot.getValue());
	}
	
	public Mot(int id, String value, int tf_max, int tf_min, int tf_cumule, int df, double idf, double tf_idfmax,double tf_idfcumule, double tf_idfmin, int polarite_negative, int polarite_positive){
		this();
		this.value = value;
		this.tf_max = tf_max;
		this.tf_min = tf_min;
		this.tf_cumule = tf_cumule;
		this.df = df;
		this.idf = idf;
		this.tf_idfmax = tf_idfmax;
		this.tf_idfmin = tf_idfmin;
		this.tf_idfcumule = tf_idfcumule;
		this.polarite_negative = polarite_negative;
		this.polarite_positive = polarite_positive;
	}
	public Mot(String value, int tf_max, int tf_min, int tf_cumule, int df, double idf, double tf_idfmax,double tf_idfcumule, double tf_idfmin, int polarite_negative, int polarite_positive){
		this();
		this.value = value;
		this.tf_max = tf_max;
		this.tf_min = tf_min;
		this.tf_cumule = tf_cumule;
		this.df = df;
		this.idf = idf;
		this.tf_idfmax = tf_idfmax;
		this.tf_idfmin = tf_idfmin;
		this.tf_idfcumule = tf_idfcumule;
		this.polarite_negative = polarite_negative;
		this.polarite_positive = polarite_positive;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int[] getTf() {
		return tf;
	}

	public void setTf(int[] tf,int pos) {
		this.tf = tf;
	}

	public int getTf_Pos(int pos) {
		return tf[pos];
	}

	public void setTf_Pos(int tf,int pos) {
		this.tf[pos] = tf;
	}

	public double getIdf() {
		return idf;
	}

	public void setIdf(double idf) {
		this.idf = idf;
	}

	public Our_occurence[] getOccurrence() {
		return occurrence;
	}

	public Our_occurence getOccurrencePos(int pos) {
		return occurrence[pos];
	}

	public void setOccurrencePos(Our_occurence occurrence, int pos)
	{
		this.occurrence[pos] = occurrence;
		this.setOccurrenceTotal(this.getOccurrenceTotal()+occurrence.getValue());
	}

	public void setOccurrence(Our_occurence[] occurrence) {
		this.occurrence = occurrence;
	}
	public void innOccurrence(int pos)
	{
		this.occurrence[pos].setValue(this.occurrence[pos].getValue() + 1);
		incOccurrenceTotal();
	}
	
	public double[] getTf_idf() {
		return tf_idf;
	}

	public double getTf_idf_Pos(int pos)
	{
		return tf_idf[pos];
	}

	public void setTf_idf(double[] tf_idf) {
		this.tf_idf = tf_idf;
	}
	public void setTf_idf_pos(double tf_idf,int pos) {
		this.tf_idf[pos] = tf_idf;
	}

	public int getOccurrenceTotal() {
		return occurrenceTotal;
	}

	public void setOccurrenceTotal(int occurrenceTotal) {
		this.occurrenceTotal = occurrenceTotal;
	}

	public void incOccurrenceTotal() {
		this.occurrenceTotal++;
	}

	public int getNbDoc() {
		return df;
	}

	public void setNbDoc(int nbDoc) {
		this.df = nbDoc;
	}
	public void incNbDoc() {
		this.df++;
	}

	public boolean[] getBoolMod() {
		return boolMod;
	}

	public int getBoolMod_Pos_Int(int pos) {
		if (boolMod[pos])
		{
			return 1;
		}
		//sinon
		return 0;
	}

	public void setBoolMod(boolean[] boolMod) {
		this.boolMod = boolMod;
	}

		public int[] getPolarite() {
		return polarite;
	}

	public void setPolarite(int[] polarite) {
		this.polarite = polarite;
	}

	public int getTf_max() {
		return tf_max;
	}

	public void setTf_max(int tf_max)
	{
		this.tf_max = tf_max;
	}

	public int calcTf_max()
	{
		int i;
		int tfmax=0;
		for(i=0;i<this.tf.length;i++)
		{
			if (tf[i]>0 & tf[i]>this.tf_max)
			{
				tfmax = tf[i];
				this.setTf_max(tf[i]);
			}
		}
		return tfmax;
	}

	public int getTf_min() {
		return tf_min;
	}

	public void setTf_min(int tf_min) {
		this.tf_min = tf_min;
	}

	public int calcTf_min()
	{
		int i;
		int tfmin=0;
		for(i=0;i<this.tf.length;i++)
		{
			if (tf[i]>0 & tf[i]>this.tf_min)
			{
				tfmin = tf[i];
				this.setTf_min(tf[i]);
			}
		}
		return tfmin;
	}

	public int getTf_cumule() {
		return tf_cumule;
	}

	public void setTf_cumule(int tf_cumule) {
		this.tf_cumule = tf_cumule;
	}

	public int calcTf_cumule()
	{
		int i;
		int tf_cumule=0;
		for(i=0;i<this.tf.length;i++)
		{
				tf_cumule += tf[i];
				this.setTf_cumule(this.getTf_cumule()+tf[i]);
		}
		return tf_cumule;
	}

	public double getTf_idfmin() {
		return tf_idfmin;
	}

	public void setTf_idfmin(double tf_idfmin) {
		this.tf_idfmin = tf_idfmin;
	}

	public int calcTf_idfmin()
	{
		int i;
		int tf_idfmin=0;
		for(i=0;i<this.tf.length;i++)
		{
				tf_idfmin += tf[i];
				this.setTf_idfmin(this.getTf_idfmin()+tf[i]);
		}
		return tf_idfmin;
	}

	public double getTf_idfmax() {
		return tf_idfmax;
	}

	public void setTf_idfmax(double tf_idfmax) {
		this.tf_idfmax = tf_idfmax;
	}

	public int calcTf_idfmax()
	{
		int i;
		int tf_idfmax=0;
		for(i=0;i<this.tf.length;i++)
		{
				tf_idfmax += tf[i];
				this.setTf_idfmax(this.getTf_idfmax()+tf[i]);
		}
		return tf_idfmax;
	}

	public int getpolarite_negative() {
		return polarite_negative;
	}

	public void setpolarite_negative(int polarite_negative) {
		this.polarite_negative = polarite_negative;
	}
	public void addpolarite_negative(int polarite_negative) {
		this.polarite_negative += polarite_negative;
	}

	public int getpolarite_positive() {
		return polarite_positive;
	}

	public void setpolarite_positive(int polarite_positive) {
		this.polarite_positive = polarite_positive;
	}

	public void addpolarite_positive(int polarite_positive) {
		this.polarite_positive += polarite_positive;
	}
	
	static int dfCount =0;
	public int incDf()
	{
		System.out.println("\t\t\t\t\tincDF : "+(++dfCount));
		++df;
		return df;
	}
	
	public double updIDF()
	{
		idf = Math.log(2000/df);
		updTf_Idf();
		return idf;
	}

	private void updTf_Idf() 
	{
		for (int i=0;i<tf_idf.length;i++)
		{
			tf_idf[i] = tf[i]*idf;
		}
		
	}

	//Methode
	public void updtMaxMinCumule()
	{
		for (int i=0;i<tf.length;i++)
		{
			if(tf[i]>tf_max)
			{
				tf_max =tf[i];
			}
			if(tf[i]>0)
			{
				if(tf_min > tf[i])
				{
					tf_min =tf[i];
				}
			}
			if(tf_idf[i]>tf_idfmax)
			{
				tf_idfmax =tf_idf[i];
			}
			if(tf[i]>0)
			{
				if(tf_idfmin > tf_idf[i])
				{
					tf_idfmin =tf_idf[i];
				}
			}
		tf_cumule+=tf[i];
		tf_idfcumule+=tf_idf[i];
		}
	}
	public String toString()
	{
		updtMaxMinCumule();
		String mot_to_string="(";
		//mot_to_string+=id +" , ";
		mot_to_string+="\""+value+"\" , ";
		mot_to_string+=tf_max+" , ";
		//mot_to_string+=tf_min+" , "; Information non pertinente
		mot_to_string+=tf_cumule+" , "; // Si tf_cumule > df alors tf>1 au moins une fois
										// Si tf_cumule = df alors tf==1 toujours
		mot_to_string+=df+" , ";
		mot_to_string+=idf+" , ";
		mot_to_string+=tf_idfmax+" , ";
		//mot_to_string+=tf_idfmin+" , "; Information non pertinente
		mot_to_string+=tf_idfcumule+" , ";
		mot_to_string+=polarite_negative+" , ";
		mot_to_string+=polarite_positive+")";
		return mot_to_string;
	}
	public String insertSql(String nameTable)
	{
		String sql_insert = "INSERT INTO "+nameTable;
		sql_insert +="\n";
		sql_insert +="(value,tf_max,tf_min,tf_cumule,df,idf,tf_idfmax,tf_idfmin,tf_idfcumule,polarite_negative,polarite_positive)";
		sql_insert +="\n";
		sql_insert += " VALUES " + this.toString();
		return sql_insert;
	}

	public int getDf() {
		return df;
	}

	public void incTf_pos(int l) 
	{
		System.out.println("\t\tINCTF ( "+tf[l]+","+l+")");
		setTf_Pos(tf[l]+1, l);
	}

	public double updTf_Idf(int pos) 
	{
		tf_idf[pos] = tf[pos]*idf;
		return tf_idf[pos];
	}

	public void updtDf() 
	{
		int loc_df=0;
		for (int i=0;i<tf.length;i++)
		{
			if (tf[i]>0)
			{
				loc_df++;
			}
		}
		System.out.println("\t\t\t\t\t\t\t\t updtDf : "+loc_df);
		df=loc_df;	
		if(df>0)
		{
			updIDF();
		}
	}





}
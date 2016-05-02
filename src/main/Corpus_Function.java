package main;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.annolab.tt4j.TreeTaggerException;

import db_JDBC.JdbcCorpus;


public class Corpus_Function
{
	//Champs
	public Document[] documents;
	public Map<String, Mot> words;
	public String output_path = "ressources/output";

	//Constructeurs
	Corpus_Function()
	{
		documents = new Document[2000];
		words = new HashMap<String, Mot>();
	}

	Corpus_Function(Document[] documents, Map<String, Mot> words)
	{
		this.documents = documents;
		this.words = words;
	}

	//Méthodes
	/*
	* -------------------------------------------------------------------------------------------------------------------
	* -------------------------------------------------------------------------------------------------------------------
	* -------------------------------------------------------------------------------------------------------------------
	*/

	public void generation_corpus_lem(List<String> csv_paths) throws TreeTaggerException
	{
			BufferedReader br = null;
			String line = "";
			String cvsSplitBy = "\n";
			Lemmatisation lem = new Lemmatisation();
			for(String path : csv_paths )
			{
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~IMPORT DATA FROM DATASET.CSV~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

		//Ouverture d'un bloc try_catch
		try {
			//Import des données brut dans un buffer
			br = new BufferedReader(new FileReader(path));
			//k : numero ligne
			int k=0;
			//Pour chaque ligne du buffer
			List<String> lines_Write_Arff = new ArrayList<String>();
			List<String> excludeTreeTaggerTagsList = new ArrayList<String>();
			String listTag = "";
			for (String s : excludeTreeTaggerTagsList)
			{
				listTag+="_"+s;
			}
			while ((line = br.readLine()) != null)
			{
				k++;
				String delims = "[ ]+";
				String[] tokens = remove_stop_caractere(line.toLowerCase());
				String line_lem ="";
				//Pour chaque mot s dans l'avis du document l			
				System.out.println(k+" : "+line);			
				line_lem = lem.obtenirListLemattiseNotInTagList(line, excludeTreeTaggerTagsList);
				System.out.println(k+" : "+line_lem);
				lines_Write_Arff.add(line_lem);			
			}
			our_file_writer(lines_Write_Arff, "dataset_lem", ".csv", output_path);
		}
		catch (FileNotFoundException e) 	//Document non trouvé : path incorrecte
		{
			e.printStackTrace();
		}
		catch (IOException e) 				//Exception d'entrée sortie
		{
			e.printStackTrace();
		}
			}
	}
	public void generation_corpus_ssw(List<String> csv_paths, List<String> stopwords ) 
	{
		for(String path : csv_paths)
		{
		String csvFileAvis = path;
		BufferedReader brAvis = null;
		String line = "";
		String cvsSplitBy = "\n";

		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~IMPORT DATA FROM DATASET.CSV~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

		//Ouverture d'un bloc try_catch
		try {
			//Import des données brut dans un buffer
			brAvis = new BufferedReader(new FileReader(csvFileAvis));
			//k : numero ligne
			int k=0;
			//Pour chaque ligne du buffer
			List<String> lines_Write_Arff = new ArrayList<String>();
			while ((line = brAvis.readLine()) != null)
			{
				String delims = "[ ]+";
				String[] tokens = remove_stop_caractere(line.toLowerCase());
				//String[] tokens = line.toLowerCase().split(delims);
				String line_ssw ="";
				
				for(int i=0;i<tokens.length;i++)
				{
					if (stopwords.contains(tokens[i]))
					{
//						System.out.println("\t\t\tStopWord");
//						System.out.println(tokens[i]);
					}
					else if (tokens[i].length() < 2)
					{
						
					}
					else
					{
						line_ssw+=tokens[i]+" ";
					}
				}
				//Pour chaque mot s dans l'avis du document l			
				System.out.println(line);
				System.out.println(line_ssw);
				//pause(2);
				lines_Write_Arff.add(line_ssw);			
			}
			our_file_writer(lines_Write_Arff, "dataset_ssw", ".csv", output_path);
		}
		catch (FileNotFoundException e) 	//Document non trouvé : path incorrecte
		{
			e.printStackTrace();
		}
		catch (IOException e) 				//Exception d'entrée sortie
		{
			e.printStackTrace();
		}
		}
	}
	
	//Read input documents
	public  Document[] input(String path)
	{
		String csvFilePolarite = "ressources/input/corpus/labels.csv";
		String csvFileAvis = path;
		BufferedReader brPolarite = null;
		BufferedReader brAvis = null;
		String line = "";
		String cvsSplitBy = "\n";

		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~IMPORT DATA FROM DATASET.CSV~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

		//Ouverture d'un bloc try_catch
		try {
			//Import des données brut dans un buffer
			brAvis = new BufferedReader(new FileReader(csvFileAvis));
			//k : numero ligne
			int k=0;
			//Pour chaque ligne du buffer
			while ((line = brAvis.readLine()) != null)
			{
				//Récupération de l'avis sur le document k[0,2000]
				String[] avis = line.split(cvsSplitBy);
				//Création d'un option à partir de l'avis sur le document k.
				//La polarité de l'avis sera récupépéré lors de la lecture du document label.csv
				documents[k] = new Document(avis[0]);
				//On incremente k pour la prochaine ligne
				k++;
			}
		}
		catch (FileNotFoundException e) 	//Document non trouvé : path incorrecte
		{
			e.printStackTrace();
		}
		catch (IOException e) 				//Exception d'entrée sortie
		{
			e.printStackTrace();
		}
		finally 							//Lors qu'on a lu toutes les lignes du buffer
		{
			if (brAvis != null)
			{
				try
				{
					brAvis.close();		//Fermeture buffer de lecture
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~IMPORT DATA FROM LABEL.CSV~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

		//Ouverture d'un bloc try_catch
		try
		{
			//Import des données brut dans un buffer
			brPolarite = new BufferedReader(new FileReader(csvFilePolarite));
			//k : numero ligne
			int k=0;
			while ((line = brPolarite.readLine()) != null)
			{
				//Récupération de la polarité sur le document k
				String[] polarite = line.split(cvsSplitBy);
				//Affectation de la polarité à l'document k créé ci-dessus
				documents[k].setPolarite(Integer.parseInt(polarite[0]));
				//On incremente k pour la prochaine ligne
				k++;
			}
		}
		catch (FileNotFoundException e) 	//Document non trouvé : path incorrecte
		{
			e.printStackTrace();
		}
		catch (IOException e) 				//Exception d'entrée sortie
		{
			e.printStackTrace();
		}
		finally 							//Lors qu'on a lu toutes les lignes du buffer
		{
			if (brPolarite != null) {
				try {
					brPolarite.close();	//Fermeture buffer de lecture
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("Import suceeded");
		
		return documents;
	}
	/*
	* -------------------------------------------------------------------------------------------------------------------
	* -------------------------------------------------------------------------------------------------------------------
	* -------------------------------------------------------------------------------------------------------------------
	* -------------------------------------------------------------------------------------------------------------------
	*/

	public Map<String, Mot> find_words(Document[] documents)
	{
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~IDENTIFICATION DES MOTS PRÉSENT DANS LES DOCUMENTS DU CORPUS~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		System.out.println("words.size() before : "+words.size());
		this.pause(5);
		//Pour chaque document du texte
		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		Calendar cal = Calendar.getInstance();
		System.out.println(dateFormat.format(cal.getTime())); //2014/08/06 16:00:22
		String mots ="";

		for (int l=0;l<this.documents.length;l++)
		{
			//Pour l'Document du document l
			//Pour l'avis de l'Document du document l
			String phrase = documents[l].getAvis();
			String delims = "[ ]+";
			String[] tokens = phrase.split(delims);

			//Pour chaque mot s dans l'avis du document l
			for(String s : tokens)
			{
				String[] s_remove_stop_caractere = remove_stop_caractere(s);
				for(int i=0;i<s_remove_stop_caractere.length;i++)
				{
				//Si le mot n'est pas encore présent dans le dictionnaire des mots "words"
				if(s_remove_stop_caractere[i].length() > 1)
				{
					if(words.get(s_remove_stop_caractere[i]) == null)
					{
						//						writer.println(s_remove_stop_caractere);
						mots+=s_remove_stop_caractere[i]+"\n";
						//On insert le mot s dans le dictionnaire des mots "words"

						words.put(s_remove_stop_caractere[i],new Mot(s_remove_stop_caractere[i]));
						Mot m = words.get(s_remove_stop_caractere[i]);
						m.setPolarite(new int[2000]);
						m.getPolarite()[l]=this.documents[l].getPolarite();
						m.updtDf();
						m.incTf_pos(l);
						m.updTf_Idf(l);
						if (this.documents[l].getPolarite()==1)
						{
							m.addpolarite_positive(this.documents[l].getPolarite());
							System.out.println(m.getValue()+" : Document positif");
						}
						else
						{
							m.addpolarite_negative(this.documents[l].getPolarite());
							System.out.println(m.getValue()+" : Document negatif");
						}
						m.getBoolMod()[l]=true;
					}
					//Si le mot est présent dans le dictionnaire des mots "words"
					else
					{

						//Récupération de l'objet mot
						Mot m = words.get(s_remove_stop_caractere[i]);
						m.getPolarite()[l]=this.documents[l].getPolarite();
						m.updtDf();
						m.incTf_pos(l);
						m.updTf_Idf(l);
						if (this.documents[l].getPolarite()==1)
						{
							if (m.getTf_Pos(l) == 1)
							{
								System.out.println("========================================================");
								m.addpolarite_positive(this.documents[l].getPolarite());
							}
							System.out.println(m.getValue()+"["+l+"](tf:"+m.getTf_Pos(l)+",df:"+m.getDf()+",idf:"+m.getIdf()+",tf-idf:"+m.getTf_idf_Pos(l)+",pol+:+"+m.getpolarite_positive()+",pol-:"+m.getpolarite_negative()+") : Document positif");
						}
						else
						{
							if (m.getTf_Pos(l) == 1)
							{
								System.out.println("========================================================");
								m.addpolarite_negative(this.documents[l].getPolarite());
							}
							System.out.println(m.getValue()+"["+l+"](tf:"+m.getTf_Pos(l)+",df:"+m.getDf()+",idf:"+m.getIdf()+",tf-idf:"+m.getTf_idf_Pos(l)+",pol+:+"+m.getpolarite_positive()+",pol-:"+m.getpolarite_negative()+") : Document negatif");
						}
					}
				}

			}
			}
		}
		String file_name = "CorpusWord";
		String extention = "txt";
		our_file_writer(mots,file_name,extention,output_path);
		System.out.println("words.size() after : "+words.size());
		return words;
	}

	public String[] remove_stop_caractere(String p_crct)
	{
		System.out.println("mot :"+p_crct);
		String rslt = p_crct.replaceAll("[^a-zA-Z]", " ").toLowerCase().trim();
		String[] rslt_tab = rslt.split(" ");
		return rslt_tab;
	}
	public  void remove_stop_words(List<String> stop_words)
	{
		System.out.println("words.size() :"+words.size());
			for(String s : stop_words)
			{
				words.remove(s);
			}
		System.out.println("words.size() :"+words.size());
	}

	public  void auto_remove_stop_words_by_occ()
	{
		System.out.println("words.size() :"+words.size());
		List<String> stop_words= new ArrayList<>();
		for(Entry<String, Mot> entry_s_m : words.entrySet())
		{
			if (entry_s_m.getValue().getIdf() <2)
			{
				System.out.println(entry_s_m.getKey());
				System.out.println("words.remove("+entry_s_m.getKey()+")");
				System.out.println(words.get(entry_s_m.getKey()));
				stop_words.add(entry_s_m.getKey());
			}
		}
		remove_stop_words(stop_words);
		System.out.println("words.size() :"+words.size());
	}

	final  Charset ENCODING = StandardCharsets.UTF_8;
	void writeLargerTextFile(String aFileName, List<String> aLines) throws IOException
	{
		Path path = Paths.get(aFileName);
		try (BufferedWriter writer = Files.newBufferedWriter(path, ENCODING)){
			for(String line : aLines){
				writer.write(line);
				writer.newLine();
			}
		}
	}

	public static void pause(double seconde)
	{
		try {
			Thread.sleep((long) (seconde*1000));                 //5000 milliseconds is five second.
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}

	public void new_write_Arff(List<String> mots, String tw, String doc_name, String relation_name ) 
	{
		List<String> lines_Write_Arff = new ArrayList<String>();
		String relation = "@relation "+relation_name;
		String attribute = "@attribute";
		String data = "@data";

		lines_Write_Arff.add(relation);	
		if(tw.equalsIgnoreCase("bool"))
		{
			our_file_writer(lines_Write_Arff, doc_name+"_bool", ".arff", output_path);
		}
		else if (tw.equalsIgnoreCase("tf"))
		{
			our_file_writer(lines_Write_Arff, doc_name+"_tf", ".arff", output_path);
		}
		else if (tw.equalsIgnoreCase("df"))
		{
			our_file_writer(lines_Write_Arff, doc_name+"_df", ".arff", output_path);
		}
		else if (tw.equalsIgnoreCase("tf_idf"))
		{
			our_file_writer(lines_Write_Arff, doc_name+"_tf_idf", ".arff", output_path);
		}
	}
	
	
	
	
	
	public void write_Arff(List<String> mots, String tw, String table_name, String doc_name ) {
		System.out.println("Write_Arff() : start succeded");
		String Write_Arff = "";
		List<String> lines_Write_Arff = new ArrayList<String>();

		String relation = "@relation";
		String relationName = "documents";
		String attribute = "@attribute";
		String data = "@data";

		Write_Arff = Write_Arff + relation +" "+ relationName;
		lines_Write_Arff.add(relation +" "+ relationName);

		Write_Arff = Write_Arff + "\n";
		if(mots==null)
		{
			for(String s : words.keySet())
			{
				lines_Write_Arff.add(attribute + " \""+s+"\" NUMERIC");
			}
		}
		else
		{
			for(String s : mots)
			{
				lines_Write_Arff.add(attribute + " \""+s+"\" NUMERIC");
			}
		}
		System.out.println(attribute + " \"polarite\" {-1,1}");
		lines_Write_Arff.add(attribute + " \"polarite\" {-1,1}");
		lines_Write_Arff.add(data);
		int l;
		for (l = 0; l < this.documents.length; l++)
		{
			int i=0;
			String line="";
			for(String s : mots)
			{
				Mot mot = words.get(s);
				double slct_tw=0;
				//---slct_tw : bool,tf , tf_idf
				if(tw.equalsIgnoreCase("bool"))
				{
					if(mot.getTf_Pos(l)==0)
					{
						slct_tw = 0;
					}
					else
					{
						slct_tw = 1;
					}
				}
				else if (tw.equalsIgnoreCase("tf"))
				{
					slct_tw = mot.getTf_Pos(l);
				}
				else if (tw.equalsIgnoreCase("tf_idf"))
				{
					slct_tw = mot.getTf_idf_Pos(l);
				}
				//---
				if (i==0)
				{
					line = line +"\n"+"{";
					line = line + i + " " + slct_tw;
				}
				if (i>0)
				{
					line += ","+ i + " " + slct_tw;
				}
				if (i == mots.size()-1)
				{
					line += ","+ (i+1) + " " + this.documents[l].getPolarite();
					line = line +"}";
				}
				i++;
			}
			lines_Write_Arff.add(line);
			System.out.println(line);
		}
		if(tw.equalsIgnoreCase("bool"))
		{
			our_file_writer(lines_Write_Arff, doc_name+"_bool", ".arff", output_path);
		}
		else if (tw.equalsIgnoreCase("tf"))
		{
			our_file_writer(lines_Write_Arff, doc_name+"_tf", ".arff", output_path);
		}
		else if (tw.equalsIgnoreCase("tf_idf"))
		{
			our_file_writer(lines_Write_Arff, doc_name+"_tf_idf", ".arff", output_path);
		}
	}

	public void our_file_writer(String data,String name,String extention,String path)
	{
		path = our_path_ctrl(path);
		extention = our_extension_ctrl(extention);
		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		Calendar cal = Calendar.getInstance();
		System.out.println(dateFormat.format(cal.getTime())); //2014/08/06 16:00:22
		PrintWriter writer;
		try {
			writer = new PrintWriter(path+name+"_"+dateFormat.format(cal.getTime())+"."+extention, "UTF-8");
			writer.println(data);
			writer.close();
			System.out.println(name+"_"+dateFormat.format(cal.getTime())+"."+extention+" writed in "+path);
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String our_path_ctrl(String path)
	{
		if (path.charAt(path.length()-1) != '/')
		{
			path =path+"/";
		}
		return path;
	}

	public String our_extension_ctrl(String ext)
	{
		if (ext.charAt(0) != '.')
		{
			ext ="."+ext;
		}
		return ext;
	}

	public void our_file_writer(List<String> data, String name, String extention, String path)
	{

		System.out.println("our_file_writer - start");
		pause(5);
		path = our_path_ctrl(path);
		extention = our_extension_ctrl(extention);
		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		Calendar cal = Calendar.getInstance();
		System.out.println(dateFormat.format(cal.getTime())); //2014/08/06 16:00:22
		try {
			System.out.println(path + name+"_"+dateFormat.format(cal.getTime())+extention+"; Do");
			System.out.println(dateFormat.format(cal.getTime())); //2014/08/06 16:00:22
			writeLargerTextFile(path + name+"_"+dateFormat.format(cal.getTime())+extention,data);
			System.out.println(path + name+"_"+dateFormat.format(cal.getTime())+extention+"; Done");
			System.out.println("our_file_writer - stop");
			pause(5);
			for (String s : data)
			{
				System.out.println(s);
	//			pause(0.1);
			}
	//		pause(5);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("our_file_writer - err");
			e.printStackTrace();
		}

	}

	public  void mots_Write(Map<String, Mot> mots)
	{
		List<String> lines = new ArrayList<String>();
		for(Entry<String, Mot> entry_s_m : mots.entrySet())
		{
			Mot mot = entry_s_m.getValue();
			mot.updtDf();
			lines.add(mot.toString());
			System.out.println(mot.toString());
		}
		our_file_writer(lines, "mot_infos", "txt", output_path);
	}

	public  void mots_Write_MYSQL(List<String> mots,String nameTable)
	{
		JdbcCorpus objJdbcCorpus = new JdbcCorpus();
		objJdbcCorpus.testDb();

		int resultInsert;
		for(String m : mots)
		{
			Mot mot = words.get(m);
			resultInsert = objJdbcCorpus.executeUpdate(mot.insertSql(nameTable));
			System.out.println("resultInsert: " + resultInsert);
		}
	}
	public  void mots_Write_MYSQL(Map<String,Mot> mots,String nameTable)
	{
		JdbcCorpus objJdbcCorpus = new JdbcCorpus();
		objJdbcCorpus.testDb();

		int resultInsert;
		for(Entry<String, Mot> entry_s_m : words.entrySet())
		{
			Mot mot = entry_s_m.getValue();
			resultInsert = objJdbcCorpus.executeUpdate(mot.insertSql(nameTable));
			System.out.println("resultInsert: " + resultInsert);
		}
	}

	public  void document_Write_Arff()
	{
		System.out.println("termWeiting_Write_Arff() : start succeded");
		List<String> lines = new ArrayList<String>();
		lines.add("@relation movies");
		lines.add("@attribute att_avis NUMERIC");
		lines.add("@attribute att_thought {-1,1}");
		lines.add("@data");
		int l;
		for (l=0;l<documents.length;l++)
		{
			String line="'"+remove_stop_caractere(documents[l].getAvis())+"'"+","+documents[l].getPolarite()+"\n";
			System.out.println(line);
			lines.add(line);
		}
		our_file_writer(lines, "CorpusMovies", ".arff", output_path);
	}

	public void generation_corpus_lem_morpho(
			List<String> excludeTreeTaggerTagsList, List<String> paths) throws TreeTaggerException {
		for (String path: paths)
		{
		String csvFileAvis = path;
		BufferedReader brAvis = null;
		String line = "";
		String cvsSplitBy = "\n";
		Lemmatisation lem = new Lemmatisation();

		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~IMPORT DATA FROM DATASET.CSV~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

		//Ouverture d'un bloc try_catch
		try {
			//Import des données brut dans un buffer
			brAvis = new BufferedReader(new FileReader(csvFileAvis));
			//k : numero ligne
			int k=0;
			//Pour chaque ligne du buffer
			List<String> lines_Write_Arff = new ArrayList<String>();
			String listTag = "";
			for (String s : excludeTreeTaggerTagsList)
			{
				listTag+="_"+s;
			}
			while ((line = brAvis.readLine()) != null)
			{
				k++;
				String delims = "[ ]+";
				String[] tokens = remove_stop_caractere(line.toLowerCase());
				//String[] tokens = line.toLowerCase().split(delims);
				String line_lem ="";
				//Pour chaque mot s dans l'avis du document l			
				System.out.println(k+" : "+line);	
				//line_lem = lem.obtenirListLemattise(line);
				
				
				line_lem = lem.obtenirListLemattiseNotInTagList(line, excludeTreeTaggerTagsList);
				System.out.println(k+" : "+line_lem);
				//pause(2);
				lines_Write_Arff.add(line_lem);			
			}
			our_file_writer(lines_Write_Arff, "dataset_lem_morpho"+listTag, ".csv", output_path);
		}
		catch (FileNotFoundException e) 	//Document non trouvé : path incorrecte
		{
			e.printStackTrace();
		}
		catch (IOException e) 				//Exception d'entrée sortie
		{
			e.printStackTrace();
		}
		
	}
	}


}

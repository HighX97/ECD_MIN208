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

	public void generation_corpus_lem() throws TreeTaggerException
	{
		String csvFileAvis = "ressources/output/dataset_ssw_2016_05_01_02_37_01.csv";
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
				
				List<String> excludeTreeTaggerTagsList = new ArrayList<String>();
				excludeTreeTaggerTagsList.add("NP");
				excludeTreeTaggerTagsList.add("NN");
				excludeTreeTaggerTagsList.add("NNS");
				excludeTreeTaggerTagsList.add("NPS");
				line_lem = lem.obtenirListLemattiseNotInTagList(line, excludeTreeTaggerTagsList);
			
				 
				System.out.println(k+" : "+line_lem);
				//pause(2);
				lines_Write_Arff.add(line_lem);			
			}
			our_file_writer(lines_Write_Arff, "dataset_lem_morpho", ".csv", output_path);
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
	public void generation_corpus_ssw()
	{
		List<String> stopwords = new ArrayList<String>();
		stopwords.add("a");
		stopwords.add("about");
		stopwords.add("above");
		stopwords.add("above");
		stopwords.add("across");
		stopwords.add("after");
		stopwords.add("afterwards");
		stopwords.add("again");
		stopwords.add("against");
		stopwords.add("all");
		stopwords.add("almost");
		stopwords.add("alone");
		stopwords.add("along");
		stopwords.add("already");
		stopwords.add("also");
		stopwords.add("although");
		stopwords.add("always");
		stopwords.add("am");
		stopwords.add("among");
		stopwords.add("amongst");
		stopwords.add("amoungst");
		stopwords.add("amount");
		stopwords.add("an");
		stopwords.add("and");
		stopwords.add("another");
		stopwords.add("any");
		stopwords.add("anyhow");
		stopwords.add("anyone");
		stopwords.add("anything");
		stopwords.add("anyway");
		stopwords.add("anywhere");
		stopwords.add("are");
		stopwords.add("around");
		stopwords.add("as");
		stopwords.add("at");
		stopwords.add("back");
		stopwords.add("be");
		stopwords.add("became");
		stopwords.add("because");
		stopwords.add("become");
		stopwords.add("becomes");
		stopwords.add("becoming");
		stopwords.add("been");
		stopwords.add("before");
		stopwords.add("beforehand");
		stopwords.add("behind");
		stopwords.add("being");
		stopwords.add("below");
		stopwords.add("beside");
		stopwords.add("besides");
		stopwords.add("between");
		stopwords.add("beyond");
		stopwords.add("bill");
		stopwords.add("both");
		stopwords.add("bottom");
		stopwords.add("but");
		stopwords.add("by");
		stopwords.add("call");
		stopwords.add("can");
		stopwords.add("cannot");
		stopwords.add("cant");
		stopwords.add("co");
		stopwords.add("con");
		stopwords.add("could");
		stopwords.add("couldnt");
		stopwords.add("cry");
		stopwords.add("de");
		stopwords.add("describe");
		stopwords.add("detail");
		stopwords.add("do");
		stopwords.add("done");
		stopwords.add("down");
		stopwords.add("due");
		stopwords.add("during");
		stopwords.add("each");
		stopwords.add("eg");
		stopwords.add("eight");
		stopwords.add("either");
		stopwords.add("eleven");
		stopwords.add("else");
		stopwords.add("elsewhere");
		stopwords.add("empty");
		stopwords.add("enough");
		stopwords.add("etc");
		stopwords.add("even");
		stopwords.add("ever");
		stopwords.add("every");
		stopwords.add("everyone");
		stopwords.add("everything");
		stopwords.add("everywhere");
		stopwords.add("except");
		stopwords.add("few");
		stopwords.add("fifteen");
		stopwords.add("fify");
		stopwords.add("fill");
		stopwords.add("find");
		stopwords.add("fire");
		stopwords.add("first");
		stopwords.add("five");
		stopwords.add("for");
		stopwords.add("former");
		stopwords.add("formerly");
		stopwords.add("forty");
		stopwords.add("found");
		stopwords.add("four");
		stopwords.add("from");
		stopwords.add("front");
		stopwords.add("full");
		stopwords.add("further");
		stopwords.add("get");
		stopwords.add("give");
		stopwords.add("go");
		stopwords.add("had");
		stopwords.add("has");
		stopwords.add("hasnt");
		stopwords.add("have");
		stopwords.add("he");
		stopwords.add("hence");
		stopwords.add("her");
		stopwords.add("here");
		stopwords.add("hereafter");
		stopwords.add("hereby");
		stopwords.add("herein");
		stopwords.add("hereupon");
		stopwords.add("hers");
		stopwords.add("herself");
		stopwords.add("him");
		stopwords.add("himself");
		stopwords.add("his");
		stopwords.add("how");
		stopwords.add("however");
		stopwords.add("hundred");
		stopwords.add("ie");
		stopwords.add("if");
		stopwords.add("in");
		stopwords.add("inc");
		stopwords.add("indeed");
		stopwords.add("interest");
		stopwords.add("into");
		stopwords.add("is");
		stopwords.add("it");
		stopwords.add("its");
		stopwords.add("itself");
		stopwords.add("keep");
		stopwords.add("last");
		stopwords.add("latter");
		stopwords.add("latterly");
		stopwords.add("least");
		stopwords.add("less");
		stopwords.add("ltd");
		stopwords.add("made");
		stopwords.add("many");
		stopwords.add("may");
		stopwords.add("me");
		stopwords.add("meanwhile");
		stopwords.add("might");
		stopwords.add("mill");
		stopwords.add("mine");
		stopwords.add("more");
		stopwords.add("moreover");
		stopwords.add("most");
		stopwords.add("mostly");
		stopwords.add("move");
		stopwords.add("much");
		stopwords.add("must");
		stopwords.add("my");
		stopwords.add("myself");
		stopwords.add("name");
		stopwords.add("namely");
		stopwords.add("neither");
		stopwords.add("never");
		stopwords.add("nevertheless");
		stopwords.add("next");
		stopwords.add("nine");
		stopwords.add("no");
		stopwords.add("nobody");
		stopwords.add("none");
		stopwords.add("noone");
		stopwords.add("nor");
		stopwords.add("not");
		stopwords.add("nothing");
		stopwords.add("now");
		stopwords.add("nowhere");
		stopwords.add("of");
		stopwords.add("off");
		stopwords.add("often");
		stopwords.add("on");
		stopwords.add("once");
		stopwords.add("one");
		stopwords.add("only");
		stopwords.add("onto");
		stopwords.add("or");
		stopwords.add("other");
		stopwords.add("others");
		stopwords.add("otherwise");
		stopwords.add("our");
		stopwords.add("ours");
		stopwords.add("ourselves");
		stopwords.add("out");
		stopwords.add("over");
		stopwords.add("own");
		stopwords.add("part");
		stopwords.add("per");
		stopwords.add("perhaps");
		stopwords.add("please");
		stopwords.add("put");
		stopwords.add("rather");
		stopwords.add("re");
		stopwords.add("same");
		stopwords.add("see");
		stopwords.add("seem");
		stopwords.add("seemed");
		stopwords.add("seeming");
		stopwords.add("seems");
		stopwords.add("serious");
		stopwords.add("several");
		stopwords.add("she");
		stopwords.add("should");
		stopwords.add("show");
		stopwords.add("side");
		stopwords.add("since");
		stopwords.add("sincere");
		stopwords.add("six");
		stopwords.add("sixty");
		stopwords.add("so");
		stopwords.add("some");
		stopwords.add("somehow");
		stopwords.add("someone");
		stopwords.add("something");
		stopwords.add("sometime");
		stopwords.add("sometimes");
		stopwords.add("somewhere");
		stopwords.add("still");
		stopwords.add("such");
		stopwords.add("system");
		stopwords.add("take");
		stopwords.add("ten");
		stopwords.add("than");
		stopwords.add("that");
		stopwords.add("the");
		stopwords.add("their");
		stopwords.add("them");
		stopwords.add("themselves");
		stopwords.add("then");
		stopwords.add("thence");
		stopwords.add("there");
		stopwords.add("thereafter");
		stopwords.add("thereby");
		stopwords.add("therefore");
		stopwords.add("therein");
		stopwords.add("thereupon");
		stopwords.add("these");
		stopwords.add("they");
		stopwords.add("thickv");
		stopwords.add("thin");
		stopwords.add("third");
		stopwords.add("this");
		stopwords.add("those");
		stopwords.add("though");
		stopwords.add("three");
		stopwords.add("through");
		stopwords.add("throughout");
		stopwords.add("thru");
		stopwords.add("thus");
		stopwords.add("to");
		stopwords.add("together");
		stopwords.add("too");
		stopwords.add("top");
		stopwords.add("toward");
		stopwords.add("towards");
		stopwords.add("twelve");
		stopwords.add("twenty");
		stopwords.add("two");
		stopwords.add("un");
		stopwords.add("under");
		stopwords.add("until");
		stopwords.add("up");
		stopwords.add("upon");
		stopwords.add("us");
		stopwords.add("very");
		stopwords.add("via");
		stopwords.add("was");
		stopwords.add("we");
		stopwords.add("well");
		stopwords.add("were");
		stopwords.add("what");
		stopwords.add("whatever");
		stopwords.add("when");
		stopwords.add("whence");
		stopwords.add("whenever");
		stopwords.add("where");
		stopwords.add("whereafter");
		stopwords.add("whereas");
		stopwords.add("whereby");
		stopwords.add("wherein");
		stopwords.add("whereupon");
		stopwords.add("wherever");
		stopwords.add("whether");
		stopwords.add("which");
		stopwords.add("while");
		stopwords.add("whither");
		stopwords.add("who");
		stopwords.add("whoever");
		stopwords.add("whole");
		stopwords.add("whom");
		stopwords.add("whose");
		stopwords.add("why");
		stopwords.add("will");
		stopwords.add("with");
		stopwords.add("within");
		stopwords.add("without");
		stopwords.add("would");
		stopwords.add("yet");
		stopwords.add("you");
		stopwords.add("your");
		stopwords.add("yours");
		stopwords.add("yourself");
		stopwords.add("yourselves");
		stopwords.add("the");

		String csvFileAvis = "ressources/dataset.csv";
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
						System.out.println("\t\t\tStopWord");
						System.out.println(tokens[i]);
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
	
	//Read input documents
	public  Document[] input(List<Our_path_model> path_model)
	{
		String csvFilePolarite = "ressources/labels.csv";
		String csvFileAvis = "ressources/dataset.csv";
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
//						m.incDf();
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
						//pause(1);
						//Création d'un instance d'occument (occ,maxOcc) pour la ligne courante
						Our_occurence occurrence =  new Our_occurence(1, tokens.length);
						//Affection de l'instance d'occurence au mot
						m.setOccurrencePos(occurrence, l);
						//
						m.getBoolMod()[l]=true;
						if(l%500 == 0)
						{
							pause(0.01);
						}

					}
					//Si le mot est présent dans le dictionnaire des mots "words"
					else
					{

						//Récupération de l'objet mot
						Mot m = words.get(s_remove_stop_caractere[i]);
						m.getPolarite()[l]=this.documents[l].getPolarite();
//						m.incDf();
						m.updtDf();
						m.incTf_pos(l);
						m.updTf_Idf(l);
						//Si l'occurence du mot pour la ligne courante est null
						if(m.getOccurrencePos(l) ==null)
						{
							//Création d'un instance d'occument (occ,maxOcc) pour la ligne courante
							Our_occurence occurrence =  new Our_occurence(1, tokens.length);
							//Affection de l'instance d'occurence au mot
							m.setOccurrencePos(occurrence, l);
							//
							m.getBoolMod()[l]=true;
						}
						else
						{
							//Incrémentation de l'instance d'occurence du mot
							m.innOccurrence(l);
						}
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
						if(l%500 == 0)
						{
							pause(0.01);
						}
						//					System.out.println("----");
						//					System.out.println(m.getOccurrencePos(l).getValue());
						//					System.out.println(m.getOccurrenceTotal());
					}
					//					writer.close();
				}

			}
			}
		}
		String file_name = "CorpusWord";
		String extention = "txt";
		our_file_writer(mots,file_name,extention,output_path);
		System.out.println("words.size() after : "+words.size());
		pause(5);
		return words;
	}





	public  void termWeiting_TF()
	{
		for(Entry<String, Mot> entry_s_m : words.entrySet())
		{
			int tf_cumule=0;
			int tf_max=0;
			int tf_min=Integer.MAX_VALUE;
			String s = entry_s_m.getKey();
			Mot m = words.get(s);
			Our_occurence occurrencenull =  new Our_occurence(0, 1);
			for (int l=0;l<2000;l++)
			{
				if (m.getOccurrencePos(l) == null)
				{
					m.setOccurrencePos(occurrencenull, l);
				}
				else
				{
					System.out.println("-------------------------------------------------------");
					System.out.println(m);
					System.out.println(m.getOccurrenceTotal());
					System.out.println(words.get(entry_s_m.getKey()).getOccurrenceTotal());
					System.out.println(m.getOccurrencePos(l).getValue());
					System.out.println(m.getOccurrencePos(l).getMax());
					System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
					pause(1);
				}
				int tf= m.getOccurrencePos(l).getValue(); /// (double) m.getOccurrencePos(l).getMax();
				tf_cumule+=tf;
				m.setTf_cumule(tf_cumule);
				if (tf > tf_max)
				{
					tf_max = tf;
					m.setTf_max(tf_max);
					System.out.println("max"+m);
				}
				if (tf < tf_min && tf > 0)
				{
					tf_min = tf;
					m.setTf_min(tf_min);
					System.out.println("min"+m);
				}
				int pos = l;
				m.setTf_Pos(tf, pos);
				words.replace(s, m);
			}
		}

	}

	public  void termWeiting_IDF()
	{
		int nbDoc;
		int MaxnbDoc=0;
		int MinnbDoc=1975;
		double idfMax = 0;

		double idfMin = 100;
		Mot motMaxOccurence=new Mot();
		motMaxOccurence.setOccurrenceTotal(0);
		Mot motMostPresent=new Mot();

		Mot motMinOccurence=new Mot();
		motMinOccurence.setOccurrenceTotal(21235);
		Mot motLeastPresent=new Mot();

		int nbDocIdfMax=0;
		Mot motIdfMAx=new Mot();
		int nbDocIdfMin=0;
		Mot motIdfMin=new Mot();
		for(Entry<String, Mot> entry_s_m : words.entrySet())
		{
			System.out.println(entry_s_m.getValue().getValue());
			System.out.println(entry_s_m.getValue().getOccurrenceTotal());
			System.out.println(motMaxOccurence.getValue());
			System.out.println(motMaxOccurence.getOccurrenceTotal());

			if(entry_s_m.getValue().getOccurrenceTotal() >0)
			{

			}
			if (motMaxOccurence.getOccurrenceTotal() < entry_s_m.getValue().getOccurrenceTotal())
			{
				System.out.println(entry_s_m.getValue().getValue());
				System.out.println(entry_s_m.getValue().getOccurrenceTotal());
				motMaxOccurence = entry_s_m.getValue();
			}
			if (motMinOccurence.getOccurrenceTotal() > entry_s_m.getValue().getOccurrenceTotal())
			{
				motMinOccurence = entry_s_m.getValue();
			}
			nbDoc=0;
			int g;

			for (g=0;g<2000;g++)
			{
				if (entry_s_m.getValue().getOccurrencePos(g).getValue() >0)
				{
					entry_s_m.getValue().incNbDoc();
					nbDoc++;
				}
			}
			if(nbDoc > MaxnbDoc)
			{

				MaxnbDoc = nbDoc;
				motMostPresent = entry_s_m.getValue();
			}
			if(nbDoc < MinnbDoc)
			{
				MinnbDoc = nbDoc;
				motLeastPresent = entry_s_m.getValue();
			}
			entry_s_m.getValue().setIdf(Math.log((double) 2000 / (double) entry_s_m.getValue().getNbDoc()));

			if (idfMax < Math.log((double) 2000 / (double) nbDoc))
			{
				idfMax = Math.log((double) 2000 / (double) nbDoc);
				motIdfMAx =  entry_s_m.getValue();
				nbDocIdfMax = nbDoc;
			}
			if (idfMin > Math.log((double) 2000 / (double) nbDoc))
			{
				idfMin = Math.log((double) 2000 / (double) nbDoc);
				motIdfMin=  entry_s_m.getValue();
				nbDocIdfMin = nbDoc;
			}
		}
		System.out.println("idfMax : "+idfMax);
		System.out.println("idfMin : "+idfMin);
		System.out.println("Le motMaxOccurence : "+motMaxOccurence);
		System.out.println("Le motMostPresent : "+motMostPresent +"\n"+ MaxnbDoc);
		System.out.println("Le motMinOccurence : "+motMinOccurence);
		System.out.println("Le motLeastPresent : "+motLeastPresent +"\n"+ MinnbDoc);
		System.out.println("Le motIdfMAx : "+motIdfMAx);
		System.out.println("Le nbDocIdfMax : "+nbDocIdfMax);
		System.out.println("Le motIdfMin : "+motIdfMin);
		System.out.println("Le nbDocIdfMin : "+nbDocIdfMin);
	}

	public  void termWeiting_TF_IDF()
	{
		double tfMax = 0;
		double tfMin = 100;
		double tf_idfMax = 0;
		double tf_idfMin = 100;
		int l;
		for(Entry<String, Mot> entry_s_m : words.entrySet())
		{
			Mot mot = entry_s_m.getValue();
			for (l=0;l<2000;l++)
			{
				entry_s_m.getValue().setIdf(words.get(entry_s_m.getKey()).getIdf());
				entry_s_m.getValue().setTf_idf_pos(entry_s_m.getValue().getTf_Pos(l) * entry_s_m.getValue().getIdf(),l);
				if (tfMax < entry_s_m.getValue().getTf_Pos(l))
				{
					tfMax = entry_s_m.getValue().getTf_Pos(l);
				}
				if (entry_s_m.getValue().getTf_Pos(l) >0 && tfMin > entry_s_m.getValue().getTf_Pos(l) )
				{
					tfMin = entry_s_m.getValue().getTf_Pos(l);
				}
				if (tf_idfMax < entry_s_m.getValue().getTf_idf_Pos(l))
				{
					tf_idfMax = entry_s_m.getValue().getTf_idf_Pos(l);
					mot.setTf_idfmax(tf_idfMin);
				}
				if (entry_s_m.getValue().getTf_idf_Pos(l) >0 && tf_idfMin > entry_s_m.getValue().getTf_idf_Pos(l))
				{
					tf_idfMin = entry_s_m.getValue().getTf_idf_Pos(l);
					mot.setTf_idfmin(tf_idfMin);
				}
				if (entry_s_m.getValue().getTf_idf_Pos(l)>0)
				{
					System.out.println(entry_s_m.getValue().getValue()+"["+l+"] : tf_idf"+entry_s_m.getValue().getTf_idf_Pos(l));
				}
			}
		}
		System.out.println("tfMax : "+tfMax);
		System.out.println("tfMin : "+tfMin);
		System.out.println("tf_idfMax : "+tf_idfMax);
		System.out.println("tf_idfMin : "+tf_idfMin);

	}

	public  void termWeiting_Doc_Length_Norlamisation()
	{
		System.out.println("termWeiting_TF_IDF() : TODO\n");
	}


//	public  void termWeiting_Write_Arff()
//	{
//		System.out.println("termWeiting_Write_Arff() : start succeded");
//		String termWeiting_Arff = "";
//		List<String> lines_termWeiting_Arff = new ArrayList<String>();
//
//		String relation = "@relation";
//		String relationName = "documents";
//		String attribute = "@attribute";
//		String data = "@data";
//
//		termWeiting_Arff = termWeiting_Arff + relation +" "+ relationName;
//		lines_termWeiting_Arff.add(relation +" "+ relationName);
//
//		termWeiting_Arff = termWeiting_Arff + "\n";
//		int k=0;
//		for(Entry<String, Mot> entry_s_m : words.entrySet())
//		{
//			k++;
//			System.out.println("Mot["+k+"] : "+entry_s_m.getValue().getValue()+" NUMERIC"+"  "+entry_s_m.getValue().getIdf());
//			termWeiting_Arff = termWeiting_Arff + "\n" + attribute + " \""+entry_s_m.getKey()+"\" NUMERIC";
//			lines_termWeiting_Arff.add(attribute + " \""+entry_s_m.getKey()+"\" NUMERIC");
//		}
//		termWeiting_Arff = termWeiting_Arff + "\n";
//		lines_termWeiting_Arff.add(data);
//		int l;
//		for (l=0;l<10;l++)
//		{
//			int i=0;
//			String line="";
//			for(Entry<String, Mot> entry_s_m : words.entrySet())
//			{
//				if (entry_s_m.getValue().getTf_idf_Pos(l)>0)
//				{
//					System.out.println(entry_s_m.getValue().getValue()+"["+l+"] : tf_idf"+entry_s_m.getValue().getTf_idf_Pos(l));
//				}
//				//System.out.println("Mot["+i+"] : "+entry_s_m.getValue().getValue());
//				if (i==0)
//				{
////					termWeiting_Arff = termWeiting_Arff +"\n"+"{";
////					termWeiting_Arff = termWeiting_Arff + i + " " + entry_s_m.getValue().getTf_idf_Pos(l);
//					line = line +"\n"+"{";
//					line = line + i + " " + entry_s_m.getValue().getTf_idf_Pos(l);
////					System.out.println(line);
//				}
//				if (i>0)
//				{
////					termWeiting_Arff += "," + i + " " + entry_s_m.getValue().getTf_idf_Pos(l);
//					line += ","+ i + " " + entry_s_m.getValue().getTf_idf_Pos(l);
////					if (i%77 == 0)
////					{
////						System.out.println(line);
////					}
//				}
//				if (i == words.size()-1)
//				{
//					pause(2);
//					System.out.println("Coucou les amis");
//					pause(2);
////					termWeiting_Arff = termWeiting_Arff +"}";
//					line = line +"}";
////					System.out.println(line);
//				}
//				i++;
//			}
//			lines_termWeiting_Arff.add(line);
//			System.out.println(line);
//		}
//		our_file_writer(lines_termWeiting_Arff, "Document_terme_weiting", ".arff", output_path);
//
//	}


	public  void boolean_model_Write_Arff(List<String> mots)
	{
		String booleanModel_Arff = "";
		List<String> lines_booleanModel_Arff = new ArrayList<String>();

		String relation = "@relation";
		String relationName = "documents";
		String attribute = "@attribute";
		String data = "@data";

		booleanModel_Arff = booleanModel_Arff + relation +" "+ relationName;
		lines_booleanModel_Arff.add(relation +" "+ relationName);
		//				System.out.println(booleanModel_Arff);
		booleanModel_Arff = booleanModel_Arff + "\n";
		for(Entry<String, Mot> entry_s_m : words.entrySet())
		{
			String mot = entry_s_m.getKey();
			if(mots.contains(mot))
					{
			System.out.println("Mot : "+entry_s_m.getValue().getValue()+" NUMERIC");
			booleanModel_Arff = booleanModel_Arff + "\n" + attribute + " \""+entry_s_m.getKey()+"\" NUMERIC";
			lines_booleanModel_Arff.add(attribute + " \""+entry_s_m.getKey()+"\" NUMERIC");
					}
		}
		lines_booleanModel_Arff.add(("@attribute \"polarite\" {-1,1}"));
		lines_booleanModel_Arff.add(data);

		booleanModel_Arff = booleanModel_Arff + "\n";
		int l;
//		for (l=0;l<1;l++)
		for (l=0;l<documents.length;l++)
		{
			System.out.println(l);
			int i=0;
			String line="";
			for(Entry<String, Mot> entry_s_m : words.entrySet())
			{
				String mot = entry_s_m.getKey();
				if(mots.contains(mot))
				{
				//				if(l%10000 ==0)
				//				{
				//					System.out.println("Mot["+i+"/"+l+"] : "+entry_s_m.getValue().getValue());
				////				System.out.println("Mot["+i+"/"+l+"] : "+entry_s_m.getValue().getValue());
				//				}
				if (i==0)
				{
					booleanModel_Arff = booleanModel_Arff +"\n"+"{";
					booleanModel_Arff = booleanModel_Arff + i + " " + entry_s_m.getValue().getBoolMod_Pos_Int(l);
					line += "\n"+"{";
					if(entry_s_m.getValue().getTf_Pos(l)==0)
					{
						line += i + " 0";
					}
					else
					{
						line += i + " 1";
					}
					//						System.out.println(line);
					//						pause(5);
				}
				if (i>0)
				{
					booleanModel_Arff += "," + i + " " + entry_s_m.getValue().getBoolMod_Pos_Int(l);
					if(entry_s_m.getValue().getTf_Pos(l)==0)
					{
						line +=","+ i + " 0";
					}
					else
					{
						line +=","+ i + " 1";
					}
					if (i%77 == 0)
					{
						//							System.out.println(line);
					}
				}
				if (i == mots.size()-1)
				{
					line += ","+ (i+1) + " " + this.documents[l].getPolarite();
					booleanModel_Arff = booleanModel_Arff +"}";
					line = line +"}";
					//						System.out.println(line);
					//						pause(5);
				}
				i++;
				}
			}
			lines_booleanModel_Arff.add(line);
			System.out.println(line);
		}
		our_file_writer(lines_booleanModel_Arff, "BooleanModel", ".arff", output_path);
	}

	/*
	* -------------------------------------------------------------------------------------------------------------------
	* -------------------------------------------------------------------------------------------------------------------
	* -------------------------------------------------------------------------------------------------------------------
	*/

	/*
	* -------------------------------------------------------------------------------------------------------------------
	* -------------------------------------------------------------------------------------------------------------------
	* -------------------------------------------------------------------------------------------------------------------
	* -------------------------------------------------------------------------------------------------------------------
	*/

	public String[] remove_stop_caractere(String p_crct)
	{
		System.out.println("mot :"+p_crct);
		String rslt = p_crct.replaceAll("[^a-zA-Z]", " ").toLowerCase().trim();
		String[] rslt_tab = rslt.split(" ");
		return rslt_tab;
		//		Pattern p = Pattern.compile("[^a-zA-Z]");
		//		Matcher m = p.matcher(p_crct);
		//		System.out.println("mot :"+p_crct);
		//		if(m.find())
		//		{
		//			//String rslt = p_crct.substring(0, m.end());
		//			//replace regx by " "j.aime" --> "j aime" --> "j" "aime"
		//			//trim to keep "  j.aime " --> "j.aime"
		////			String rslt = p_crct.replaceAll("[^a-zA-Z0-9]", "").toLowerCase().trim();
		//			String rslt = p_crct.replaceAll("[^a-zA-Z]", "").toLowerCase().trim();
		//			System.out.println("Remove_stop_caractere mot :"+rslt);
		//		    return rslt;
		//		}
		//		return p_crct;
	}
	public  void remove_stop_words(List<String> stop_words)
	{
		System.out.println("words.size() :"+words.size());
//		for (Document op : documents)
//		{
			for(String s : stop_words)
			{
				words.remove(s);
			}
//		}
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

	/*
	* -------------------------------------------------------------------------------------------------------------------
	* -------------------------------------------------------------------------------------------------------------------
	* -------------------------------------------------------------------------------------------------------------------
	* -------------------------------------------------------------------------------------------------------------------
	*/




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

	public void termWeiting_tf_idf_Write_Arff(List<String> mots) {
		System.out.println("termWeiting_Write_Arff() : start succeded");
		String termWeiting_Arff = "";
		List<String> lines_termWeiting_Arff = new ArrayList<String>();

		String relation = "@relation";
		String relationName = "documents";
		String attribute = "@attribute";
		String data = "@data";

		termWeiting_Arff = termWeiting_Arff + relation +" "+ relationName;
		lines_termWeiting_Arff.add(relation +" "+ relationName);

		termWeiting_Arff = termWeiting_Arff + "\n";
		int k=0;
		for(Entry<String, Mot> entry_s_m : words.entrySet())
		{
			String mot = entry_s_m.getKey();
			if(mots.contains(mot))
					{
				k++;
				System.out.println("Mot["+k+"] : "+entry_s_m.getValue().getValue()+" NUMERIC"+"  "+entry_s_m.getValue().getIdf());
				termWeiting_Arff = termWeiting_Arff + "\n" + attribute + " \""+entry_s_m.getKey()+"\" NUMERIC";
				lines_termWeiting_Arff.add(attribute + " \""+entry_s_m.getKey()+"\" NUMERIC");
					}
		}
		termWeiting_Arff = termWeiting_Arff + "\n";
		System.out.println(attribute + " \"polarite\" {-1,1}");
		lines_termWeiting_Arff.add(attribute + " \"polarite\" {-1,1}");
		lines_termWeiting_Arff.add(data);
		int l;
		for (l = 0; l < this.documents.length; l++)
		{
			int i=0;
			String line="";
			for(Entry<String, Mot> entry_s_m : words.entrySet())
			{
				String mot = entry_s_m.getKey();
				if(mots.contains(mot))
				{
				if (entry_s_m.getValue().getTf_idf_Pos(l)>0)
				{
					System.out.println(entry_s_m.getValue().getValue()+"["+l+"] : tf_idf"+entry_s_m.getValue().getTf_idf_Pos(l));
				}
				//System.out.println("Mot["+i+"] : "+entry_s_m.getValue().getValue());
				if (i==0)
				{
//					termWeiting_Arff = termWeiting_Arff +"\n"+"{";
//					termWeiting_Arff = termWeiting_Arff + i + " " + entry_s_m.getValue().getTf_idf_Pos(l);
					line = line +"\n"+"{";
					line = line + i + " " + entry_s_m.getValue().getTf_idf_Pos(l);
//					System.out.println(line);
				}
				if (i>0)
				{
//					termWeiting_Arff += "," + i + " " + entry_s_m.getValue().getTf_idf_Pos(l);
					line += ","+ i + " " + entry_s_m.getValue().getTf_idf_Pos(l);
//					if (i%77 == 0)
//					{
//						System.out.println(line);
//					}
				}
				if (i == mots.size()-1)
				{
					//pause(2);
					System.out.println("Coucou les amis");
					//pause(2);
//					termWeiting_Arff = termWeiting_Arff +"}";
					line += ","+ (i+1) + " " + this.documents[l].getPolarite();
					line = line +"}";
//					System.out.println(line);
				}
				i++;
			}
			}
			lines_termWeiting_Arff.add(line);
			System.out.println(line);
		}
		our_file_writer(lines_termWeiting_Arff, "Document_terme_weiting_tf_idf", ".arff", output_path);
	}

	public void termWeiting_tf_Write_Arff(List<String> mots) {
		if(mots != null)
		{
		System.out.println("termWeiting_Write_Arff() : start succeded");
		String termWeiting_Arff = "";
		List<String> lines_termWeiting_Arff = new ArrayList<String>();

		String relation = "@relation";
		String relationName = "documents";
		String attribute = "@attribute";
		String data = "@data";

		termWeiting_Arff = termWeiting_Arff + relation +" "+ relationName;
		lines_termWeiting_Arff.add(relation +" "+ relationName);

		termWeiting_Arff = termWeiting_Arff + "\n";
		int k=0;
		for(String s : mots)
		{
			lines_termWeiting_Arff.add(attribute + " \""+s+"\" NUMERIC");
		}
		termWeiting_Arff = termWeiting_Arff + "\n";
		System.out.println(attribute + " \"polarite\" {-1,1}");
		lines_termWeiting_Arff.add(attribute + " \"polarite\" {-1,1}");
		lines_termWeiting_Arff.add(data);
		int l;
		for (l = 0; l < this.documents.length; l++)
		{
			int i=0;
			String line="";
			for(String s : mots)
			{
				Mot mot = words.get(s);

				if (mot.getTf_Pos(l)>0)
				{
					System.out.println(mot.getValue()+"["+l+"] : tf_idf"+mot.getTf_Pos(l));
				}
				if (i==0)
				{
					line = line +"\n"+"{";
					line = line + i + " " + mot.getTf_Pos(l);
				}
				if (i>0)
				{
					line += ","+ i + " " + mot.getTf_Pos(l);
				}
				if (i == mots.size()-1)
				{
					line += ","+ (i+1) + " " + this.documents[l].getPolarite();
					line = line +"}";
				}
				i++;
			}
			lines_termWeiting_Arff.add(line);
			System.out.println(line);
		}
		our_file_writer(lines_termWeiting_Arff, "Document_terme_weiting_tf", ".arff", output_path);
	}
		else
		{

			System.out.println("termWeiting_Write_Arff() : start succeded");
			String termWeiting_Arff = "";
			List<String> lines_termWeiting_Arff = new ArrayList<String>();

			String relation = "@relation";
			String relationName = "documents";
			String attribute = "@attribute";
			String data = "@data";

			termWeiting_Arff = termWeiting_Arff + relation +" "+ relationName;
			lines_termWeiting_Arff.add(relation +" "+ relationName);

			termWeiting_Arff = termWeiting_Arff + "\n";
			int k=0;
			for(Entry<String, Mot> entry_s_m : words.entrySet())
			{
				k++;
				String mot = entry_s_m.getKey();
				if(mots.contains(mot))
						{
				System.out.println("Mot["+k+"] : "+entry_s_m.getValue()+" NUMERIC"+"  "+entry_s_m.getValue().getIdf());
				termWeiting_Arff = termWeiting_Arff + "\n" + attribute + " \""+entry_s_m.getKey()+"\" NUMERIC";
				lines_termWeiting_Arff.add(attribute + " \""+entry_s_m.getKey()+"\" NUMERIC");
						}
			}
			termWeiting_Arff = termWeiting_Arff + "\n";
			System.out.println(attribute + " \"polarite\" {-1,1}");
			lines_termWeiting_Arff.add(attribute + " \"polarite\" {-1,1}");
			lines_termWeiting_Arff.add(data);
			int l;
			for (l = 0; l < this.documents.length; l++)
			{
				int i=0;
				String line="";
				for(Entry<String, Mot> entry_s_m : words.entrySet())
				{
					String mot = entry_s_m.getKey();
					if(mots.contains(mot))
					{
					if (entry_s_m.getValue().getTf_Pos(l)>0)
					{
						System.out.println(entry_s_m.getValue().getValue()+"["+l+"] : tf_idf"+entry_s_m.getValue().getTf_Pos(l));
					}
					//System.out.println("Mot["+i+"] : "+entry_s_m.getValue().getValue());
					if (i==0)
					{
//						termWeiting_Arff = termWeiting_Arff +"\n"+"{";
//						termWeiting_Arff = termWeiting_Arff + i + " " + entry_s_m.getValue().getTf_Pos(l);
						line = line +"\n"+"{";
						line = line + i + " " + entry_s_m.getValue().getTf_Pos(l);
//						System.out.println(line);
					}
					if (i>0)
					{
//						termWeiting_Arff += "," + i + " " + entry_s_m.getValue().getTf_Pos(l);
						line += ","+ i + " " + entry_s_m.getValue().getTf_Pos(l);
//						if (i%77 == 0)
//						{
//							System.out.println(line);
//						}
					}
					if (i == mots.size()-1)
					{
						//pause(2);
						System.out.println("Coucou les amis");
						//pause(2);
//						termWeiting_Arff = termWeiting_Arff +"}";
						line += ","+ (i+1) + " " + this.documents[l].getPolarite();
						line = line +"}";
//						System.out.println(line);
					}
					i++;
				}
				}
				lines_termWeiting_Arff.add(line);
				System.out.println(line);
			}
			our_file_writer(lines_termWeiting_Arff, "Document_terme_weiting_tf", ".arff", output_path);

		}
	}

	/*
	* -------------------------------------------------------------------------------------------------------------------
	* -------------------------------------------------------------------------------------------------------------------
	* -------------------------------------------------------------------------------------------------------------------
	* -------------------------------------------------------------------------------------------------------------------
	*/

	public void write_Arff(List<String> mots, String tw, String doc_name ) {
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
		int k=0;
		for(String s : mots)
		{
			lines_Write_Arff.add(attribute + " \""+s+"\" NUMERIC");
		}
		Write_Arff = Write_Arff + "\n";
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
				if (mot.getTf_Pos(l)>0)
				{
					System.out.println("\t\t\t\t"+mot.getValue()+"["+l+"] : tf_idf"+slct_tw);
				}
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
			System.out.println("Yaw yaw");
			path =path+"/";
		}
		return path;
	}

	public String our_extension_ctrl(String ext)
	{
		if (ext.charAt(0) != '.')
		{
			System.out.println("Yaw yaw");
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

}

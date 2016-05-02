package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
//import weka.core.Instances;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Callable;

import org.annolab.tt4j.TreeTaggerException;

import db_JDBC.MotModel_Lowx;

//import weka.classifiers.Classifier;
//import weka.classifiers.Evaluation;
//import weka.classifiers.bayes.NaiveBayes;
//import weka.core.Attribute;
//import weka.core.FastVector;
//import weka.core.Instance;
//import weka.core.Instances;
//
public class Corpus
{
	static public Document[] documents;
	static public Map<String, Mot> words;
	static public Corpus_Function crp_fnc;

	public static void main(String [] args) throws IOException, TreeTaggerException
	{
		String csvFileAvis = "ressources/dataset.csv";
		String csvFileAvis_ssw = "ressources/output/dataset_ssw_2016_05_02_03_14_06.csv";
		String csvFileAvis_lem = "ressources/output/dataset_lem_2016_05_01_03_15_39.csv";
		String csvFileAvis_lem_morpho = "ECD_HMIN208/ressources/output/dataset_lem_morpho_test_2016_05_01_20_18_02.csv";
		List <String> listWords =null;
		String tablename = "";
//		String tablename = "Mot";
//		MotModel_Lowx objMotmodel = new MotModel_Lowx();
//		MyArrayListSql select = new MyArrayListSql();
//		select.add("value");
//		MyArrayListSql from = new MyArrayListSql();
//		from.add(tablename);
//		MyArrayListSql where = new MyArrayListSql();
//		where.add("tf_cumule>1");
//		String orderByPos = "+(polarite_positive+polarite_negative) desc, tf_idfcumule desc";
//		String orderByNef = "-(polarite_positive+polarite_negative) desc, tf_idfcumule desc";
//		String sens = "";
//		int limit1 = 0;
//		int limit2 = 1000;
//		listWords = objMotmodel.getListMotsFromDb(select, from, where, orderByPos, sens, limit1, limit2);
//		listWords.addAll((List<String>) objMotmodel.getListMotsFromDb(select, from, where, orderByNef, sens, limit1, limit2));
//		for(String word : listWords ){
//			System.out.println(word);
//		}
		
		
		documents = new Document[2000];
		words = new HashMap<String, Mot>();
		crp_fnc = new Corpus_Function();
		

		List<Our_path_model> p = new ArrayList<Our_path_model>();		
		crp_fnc.generation_corpus_ssw();
		crp_fnc.generation_corpus_lem();
		
		List<String> pathsCorpus = new ArrayList<>();
		pathsCorpus.add("ressources/dataset.csv");
		pathsCorpus.add("ressources/output/dataset_ssw_2016_05_01_20_13_59.csv");
		List<String> excludeTreeTaggerTagsList = new ArrayList<String>();
		excludeTreeTaggerTagsList.add("NP");
		excludeTreeTaggerTagsList.add("NN");
		excludeTreeTaggerTagsList.add("NNS");
		excludeTreeTaggerTagsList.add("NPS");
		excludeTreeTaggerTagsList.add("SYM");
		excludeTreeTaggerTagsList.add("SYM");
		excludeTreeTaggerTagsList.add("JJ");
		excludeTreeTaggerTagsList.add("JJR");
		excludeTreeTaggerTagsList.add("JJS"); 
		crp_fnc.generation_corpus_lem_morpho(excludeTreeTaggerTagsList,pathsCorpus);
		documents = crp_fnc.input(p);
		System.out.println("input() : succeeded\n");

//		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~01_ARFF DOCUMENTS~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//		System.out.println("documentw_Write_Arff() : start\n");
//
//		crp_fnc.pause(5);
//		crp_fnc.document_Write_Arff();
//		System.out.println("documentw_Write_Arff() : succeeded\n");

		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~LOAD AND STORE MOTS~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~01_LOAD AND STORE MOTS~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("find_words() : start\n");

//		crp_fnc.pause(5);
		words = crp_fnc.find_words(documents);
	
		System.out.println("find_words() : succeeded\n");

//		crp_fnc.mots_Write(crp_fnc.words);
//		crp_fnc.mots_Write_MYSQL(crp_fnc.words, "Mots_lem_morpho_2");
//		crp_fnc.mots_Write_MYSQL(crp_fnc.words, "Mots_asw");


//		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~WRITE BOOLEAN MODEL ARFF FILE~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~01_WRITE BOOLEAN MODEL ARFF FILE~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//		crp_fnc.pause(5);
		
//		crp_fnc.termWeiting_tf_Write_Arff(listWords);
//		crp_fnc.termWeiting_tf_idf_Write_Arff(listWords);
////		crp_fnc.boolean_model_Write_Arff(listWords);
		crp_fnc.write_Arff(listWords,"bool", tablename, "bool"+"_corpus");
		crp_fnc.write_Arff(listWords,"tf",tablename, "tf"+"_corpus");
		crp_fnc.write_Arff(listWords,"tf_idf",tablename, "tf_idf"+"_corpus");
////		crp_fnc.mots_Write_MYSQL(crp_fnc.words, "Mots_lem_morpho");
//		System.out.println("Write_Arff() : succeeded\n");
//		crp_fnc.pause(5);
//		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~02_WRITE TERMWEITING ARFF FILE~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~02_WRITE TERMWEITING MODEL ARFF FILE~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~REMOVE STOP WORD~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~00_02_REMOVE STOP WORD~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//		System.out.println("remove_stop_words(stopwords) : start\n");
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

//		crp_fnc.pause(5);
		crp_fnc.remove_stop_words(stopwords);
//		Lemmatisation lem = new Lemmatisation();
//		Map<String,String> rslt = lem.obtenirListLemattise(new ArrayList<String>(crp_fnc.words.keySet()));
//		List<String> words_lem = new ArrayList<String>(rslt.values());
//		crp_fnc.pause(5);
//		crp_fnc.mots_Write_MYSQL(words_lem, "Mots_lem");
		
//		System.out.println("remove_stop_words(stopwords) : succeeded\n");

//		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~02_01_CALCULE TF~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~02_01_CALCULE TERME FREQUENCY~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//		System.out.println("termWeiting_TF() : start\n");
//		crp_fnc.pause(5);
//		crp_fnc.termWeiting_TF();
//		System.out.println("termWeiting_TF() : succeeded\n");
//
//		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~CALCULE IDF~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~02_02_CALCULE INVERSE DOCUMENT FREQUENCY~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//
//		System.out.println("termWeiting_IDF() : start\n");
//		crp_fnc.pause(5);
//		crp_fnc.termWeiting_IDF();
//		System.out.println("termWeiting_IDF() : succeeded\n");
//
//		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~CALCULE TDF_IDF~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~02_03_TF-IDF WEIGHTING~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//		System.out.println("termWeiting_TF_IDF() : start\n");
//		crp_fnc.pause(5);
//		crp_fnc.termWeiting_TF_IDF();
//		System.out.println("termWeiting_TF_IDF() : succeeded\n");
//
//		crp_fnc.mots_Write(crp_fnc.words);
//		//crp_fnc.mots_Write_MYSQL(crp_fnc.words);


//		//		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~DOCUMENT LENGTH NORMALIZATION~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~02_04_DOCUMENT LENGTH NORMALIZATION~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//		crp_fnc.termWeiting_Doc_Length_Norlamisation();
//		crp_fnc.pause(5);

		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~WRITE TERMWEITING ARFF FILE~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~02_05_WRITE TERMWEITING ARFF FILE~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//		System.out.println("termWeiting_Write_Arff() : start\n");
//		crp_fnc.pause(5);
//		crp_fnc.termWeiting_Write_Arff();
//		System.out.println("termWeiting_Write_Arff() : succeeded\n");
		//
		//		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Weka~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~03_WEKA~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//		System.out.println("weka_exemple00() : start\n");
//		crp_fnc.pause(5);
//		 crp_fnc.weka();
//		System.out.println("weka_exemple00() : succeeded\n");


		System.out.println("\n==============================================================" +
				"\t\t" +
				"Fin" +
				"\n==============================================================");

	}

}

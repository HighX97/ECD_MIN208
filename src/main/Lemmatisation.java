package main;
import static java.util.Arrays.asList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.annolab.tt4j.*;
//package org.annolab.tt4j;
/**
https://courses.washington.edu/hypertxt/csar-v02/penntable.html

POS Tag	Description	Example
CC	coordinating conjunction	and, but, or, &
CD	cardinal number	1, three
DT	determiner	the
EX	existential there	there is
FW	foreign word	d'œuvre
IN	preposition/subord. conj.	in,of,like,after,whether
IN/that	complementizer	that
JJ	adjective	green
JJR	adjective, comparative	greener
JJS	adjective, superlative	greenest
LS	list marker	(1),
MD	modal	could, will
NN	noun, singular or mass	table
NNS	noun plural	tables
NP	proper noun, singular	John
NPS	proper noun, plural	Vikings
PDT	predeterminer	both the boys
POS	possessive ending	friend's
PP	personal pronoun	I, he, it
PP$	possessive pronoun	my, his
RB	adverb	however, usually, here, not
RBR	adverb, comparative	better
RBS	adverb, superlative	best
RP	particle	give up
SENT	end punctuation	?, !, .
SYM	symbol	@, +, *, ^, |, =
TO	to	to go, to him
UH	interjection	uhhuhhuhh
VB	verb be, base form	be
VBD	verb be, past	was|were
VBG	verb be, gerund/participle	being
VBN	verb be, past participle	been
VBZ	verb be, pres, 3rd p. sing	is
VBP	verb be, pres non-3rd p.	am|are
VD	verb do, base form	do
VDD	verb do, past	did
VDG	verb do gerund/participle	doing
VDN	verb do, past participle	done
VDZ	verb do, pres, 3rd per.sing	does
VDP	verb do, pres, non-3rd per.	do
VH	verb have, base form	have
VHD	verb have, past	had
VHG	verb have, gerund/participle	having
VHN	verb have, past participle	had
VHZ	verb have, pres 3rd per.sing	has
VHP	verb have, pres non-3rd per.	have
VV	verb, base form	take
VVD	verb, past tense	took
VVG	verb, gerund/participle	taking
VVN	verb, past participle	taken
VVP	verb, present, non-3rd p.	take
VVZ	verb, present 3d p. sing.	takes
WDT	wh-determiner	which
WP	wh-pronoun	who, what
WP$	possessive wh-pronoun	whose
WRB	wh-abverb	where, when
:	general joiner	;, -, --
$	currency symbol	$, £
 * @author jimmymunoz
 *
 */
public class Lemmatisation {
	
	private String treetaggerHome = "ressources/input/tree-tagger-linux-3.2";
	//private String treetaggerHome = "ressources/input/tree-tagger-mac/";
	//private String treetaggerModel = "treetagger/english-par-linux-3.2-utf8.bin";//tt.setModel("/opt/treetagger/models/english.par:iso8859-1");
	private String treetaggerModel = "ressources/input/treetaggerLoic/models/en.par";//tt.setModel("/opt/treetagger/models/english.par:iso8859-1");
	
	
	public Lemmatisation(){
		
	}
	
	public HashMap<String, String> obtenirListLemattise(List<String> listeMots) throws IOException, TreeTaggerException{
		final HashMap<String, String> resultatLemmatisation = new HashMap<String, String>();
		
		System.setProperty("treetagger.home", this.treetaggerHome);
	    TreeTaggerWrapper<String> tt = new TreeTaggerWrapper<String>();
	    try {
	    	tt.setModel(this.treetaggerModel);
	    	tt.setHandler(new TokenHandler<String>() {
	        public void token(String token, String pos, String lemma) {
	        	if(resultatLemmatisation.get(lemma) == null)
	        	{
	        		if(lemma.length() <= 2)
	        		{
	        			System.out.println(token + "\t" + pos + "\t" + lemma);
	        		}
	        		
	        		//words.put(s_remove_stop_caractere,new Mot(s_remove_stop_caractere));
		        	resultatLemmatisation.put(lemma, lemma);
		        }
	        	//resultatLemmatisation.add(lemma);
	        }
	      });
	       tt.process(listeMots);
	    }
	    finally {
	      tt.destroy();
	    }
	    return resultatLemmatisation;
	}
	
	public String obtenirListLemattise(String lineDoc) throws IOException, TreeTaggerException{
		final ArrayList<String> listResult = new ArrayList<String>();
		String resultatLemmatisation = "";
		lineDoc = lineDoc.replaceAll("  ", " ");
		lineDoc = lineDoc.replaceAll("  ", " ");
		String[] listeMots = lineDoc.split(" ");
		
		System.setProperty("treetagger.home", this.treetaggerHome);
	    TreeTaggerWrapper<String> tt = new TreeTaggerWrapper<String>();
	    try {
	    	tt.setModel(this.treetaggerModel);
	    	tt.setHandler(new TokenHandler<String>() {
	        public void token(String token, String pos, String lemma) {
	        	listResult.add(lemma);
		    }
	      });
	      tt.process(listeMots);
	    }
	    finally {
	      tt.destroy();
	    }
	    for (String s : listResult)
	    {
	    	resultatLemmatisation += s + " ";
	    }
	    return resultatLemmatisation;
	}
	
	
	
	
	/**
	 * @param listeMots
	 * @param listeTreeTaggerTags
	 * @return
	 * @throws IOException
	 * @throws TreeTaggerException
	 */
	public HashMap<String, String> obtenirListLemattiseInTagList(List<String> listeMots, final List<String> allowTreeTaggerTagsList) throws IOException, TreeTaggerException{
		final HashMap<String, String> resultatLemmatisation = new HashMap<String, String>();
		
		System.setProperty("treetagger.home", this.treetaggerHome);
	    TreeTaggerWrapper<String> tt = new TreeTaggerWrapper<String>();
	    try {
	    	tt.setModel(this.treetaggerModel);
	    	for(String tag : allowTreeTaggerTagsList ){
				System.out.println(tag);
			}
	    	//System.out.println("asas");
	    	tt.setHandler(new TokenHandler<String>() {
	    		public void token(String token, String pos, String lemma) {
		        	//Include only the tags in the list
		        	if(resultatLemmatisation.get(lemma) == null && allowTreeTaggerTagsList.contains(pos)  ){
		        		System.out.println(token + "\t" + pos + "\t" + lemma);
		        		//words.put(s_remove_stop_caractere,new Mot(s_remove_stop_caractere));
			        	resultatLemmatisation.put(lemma, lemma);
			        }
		        	//resultatLemmatisation.add(lemma);
		        }
	    	}
	       );
	       tt.process(listeMots );
	    }
	    finally {
	      tt.destroy();
	    }
	    return resultatLemmatisation;
	}
	
	public HashMap<String, String> obtenirListLemattiseNotInTagList(List<String> listeMots, final List<String> excludeTreeTaggerTagsList) throws IOException, TreeTaggerException{
		final HashMap<String, String> resultatLemmatisation = new HashMap<String, String>();
		
		System.setProperty("treetagger.home", this.treetaggerHome);
	    TreeTaggerWrapper<String> tt = new TreeTaggerWrapper<String>();
	    try {
	    	tt.setModel(this.treetaggerModel);
	    	tt.setHandler(new TokenHandler<String>() {
	    	
	    	public void token(String token, String pos, String lemma) {
	        	//Include only the tags in the list
	        	if(resultatLemmatisation.get(lemma) == null && (! excludeTreeTaggerTagsList.contains(pos) )  ){
	        		System.out.println(token + "\t" + pos + "\t" + lemma);
	        		//words.put(s_remove_stop_caractere,new Mot(s_remove_stop_caractere));
		        	resultatLemmatisation.put(lemma, lemma);
		        }
	        	//resultatLemmatisation.add(lemma);
	        }
	      });
	       tt.process(listeMots );
	    }
	    finally {
	      tt.destroy();
	    }
	    return resultatLemmatisation;
	}
	
	public String obtenirListLemattiseNotInTagList(String lineDoc, final List<String> excludeTreeTaggerTagsList) throws IOException, TreeTaggerException{
		final ArrayList<String> listResult = new ArrayList<String>();
		String resultatLemmatisation = "";
		lineDoc = lineDoc.replaceAll("  ", " ");
		lineDoc = lineDoc.replaceAll("  ", " ");
		String[] listeMots = lineDoc.split(" ");
		
		System.setProperty("treetagger.home", this.treetaggerHome);
	    TreeTaggerWrapper<String> tt = new TreeTaggerWrapper<String>();
	    try {
	    	tt.setModel(this.treetaggerModel);
	    	tt.setHandler(new TokenHandler<String>() {
	        public void token(String token, String pos, String lemma) {

	        	if( (! excludeTreeTaggerTagsList.contains(pos) )  ){
		        	if(token.length() == 1)
		        	{
		        		System.out.println(token + "\t" + pos + "\t" + lemma);
		        		
		        	}
		        	if(pos.equalsIgnoreCase("NNS"))
		        	{
		        		System.out.println(token + "\t" + pos + "\t" + lemma);
		        	
		        	}
	        		listResult.add(lemma);
	        		
	        	}
	        	else{
	        		//System.out.println("Exclude: " + lemma);
		        }
		    }
	      });
	      tt.process(listeMots);
	    }
	    finally {
	      tt.destroy();
	    }
	    for (String s : listResult)
	    {
	    	resultatLemmatisation += s + " ";
	    }
	    return resultatLemmatisation;
	}
	
	public void test() throws IOException, TreeTaggerException{
		System.setProperty("treetagger.home", this.treetaggerHome);
	    TreeTaggerWrapper<String> tt = new TreeTaggerWrapper<String>();
	    try {
	    	tt.setModel(this.treetaggerModel);
	    	tt.setHandler(new TokenHandler<String>() {
	        public void token(String token, String pos, String lemma) {
	          System.out.println(token + "\t" + pos + "\t" + lemma);
	        }
	      });
	      tt.process(asList(new String[] { "This", "is", "a", "test", "." }));
	    }
	    finally {
	      tt.destroy();
	    }
	}
}

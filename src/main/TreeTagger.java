/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.*;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFileChooser;
import org.w3c.dom.Document;
import org.w3c.dom.*;
import org.annolab.tt4j.*;
import static java.util.Arrays.asList;
import java.util.Enumeration;
import java.util.Hashtable;

public class TreeTagger {
	
	private static String treetaggerHome = "tree-tagger-mac/bin/tree-tagger";
	//private String treetaggerModel = "treetagger/english-par-linux-3.2-utf8.bin";//tt.setModel("/opt/treetagger/models/english.par:iso8859-1");
	private static String treetaggerModel = "treetagger/english.par";//tt.setModel("/opt/treetagger/models/english.par:iso8859-1");
	
	
	//fonction pour ecrire le resultat dans un fichier texte 
    public static void ecrire(String nomFic, String texte) {
        String adressedufichier = "data/results" + nomFic;
        try {

            FileWriter fw = new FileWriter(adressedufichier, true);
            BufferedWriter output = new BufferedWriter(fw);
            output.write(texte);
            output.flush();
            output.close();
        } catch (IOException ioe) {
            System.out.print("Erreur : ");
            ioe.printStackTrace();
        }
    }
    
    //fonction de tt4j TreeTagger for java, elle permet de recuperer pour chauqe mot dans le fichier le token, la catégorie grammaticale et le lemme 
    public static void testTritaggerMashad() throws Exception {
        
    	
    	final String _pathTreeTagger = treetaggerHome;
        InputStreamReader flog = null;
        LineNumberReader llog = null;
        String myLine = null;
        String file = null;

        JFileChooser fileChooser = new JFileChooser(".");
        String path = ".";
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int status = fileChooser.showOpenDialog(null);
        if (status == JFileChooser.APPROVE_OPTION) {
            File folder = new File(fileChooser.getSelectedFile().toString());
            file = fileChooser.getSelectedFile().toString();
            System.out.println(file);
        }

        try {
            flog = new InputStreamReader(new FileInputStream(file));
            llog = new LineNumberReader(flog);
            String doc = "";
            while ((myLine = llog.readLine()) != null) {
            	//& dans notre cas permet de séparer les articles
                if (!myLine.contains("&")) {
                	// recuperer chaque mot du fcihier avec le split
                    String[] result = myLine.split("[-\\s[\"()@|«»?!.,;/]_]");

                    System.setProperty("treetagger.home", treetaggerHome);
                    TreeTaggerWrapper tt = new TreeTaggerWrapper<String>();
                    try {
                        tt.setModel(treetaggerModel);
                        tt.setHandler(new TokenHandler<String>() {

                            public void token(String token, String pos, String lemma) {
                            //recuperer les lemmes des mots qui ont comme catégorie grammaticale Nom ou Adjectif
                            	System.out.println(token + "\t" + pos + "\t" + lemma);
                            	/*
                            	if (pos.contains("NOM") || pos.contains("ADJ")) {
                                // ecrire("result.txt",  token + "\t" ); 
                            	  System.err.println( lemma + " ");
                                    ecrire("corpuslemmatise.txt", lemma + " ");
                               } else {
                            	   //si la catégorie grammatical n'est pas reconnu on recupère pas le lemme
                                 if (lemma.contains("@card@") || lemma.contains("<unknown>")) {
                                	 ecrire("corpuslemmatise.txt", " ");
                                  }
                              }*/

                            }
                        });
                        tt.setExecutableProvider(new ExecutableResolver() {

                            public void setPlatformDetector(PlatformDetector arg0) {
                            }

                            public String getExecutable() throws IOException {
                                return _pathTreeTagger;
                            }

                            public void destroy() {
                            }
                        });

                        for (int x = 0; x < result.length; x++) {

                            tt.process(asList(new String[]{result[x]}));
                        }
                    } finally {
                        tt.destroy();
                    }
                   
                    ecrire("corpuslemmatise.txt","\n");
           } else {
                 ecrire("corpuslemmatise.txt", myLine + "\n");
                   doc = myLine;
                }
            }
        } catch (Exception e) {
            System.err.println("Error : " + e.getMessage());
        }
    }
}

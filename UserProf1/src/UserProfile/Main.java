package UserProfile;



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.FloydWarshallShortestPaths;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.tartarus.snowball.ext.PorterStemmer;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableSet;
public class Main {
	static String strLine;
	static char[] strLine2;
	static char[] chararray1; 
	static String str1;
	static ArrayList<String> globalArray=new ArrayList<String>();
	static ArrayList<Integer> globalCount=new ArrayList<Integer>();

	static ArrayList<String> store=new ArrayList<String>();
 	static ArrayList<Integer> randomNumbers= new ArrayList<Integer>(6);
 	static HashMap<String,Integer> hm=new HashMap<String, Integer>();
 	static HashMap<String,Integer> Dmoz=new HashMap<String, Integer>();
 	static File fsave3=new File("temporary1.doc");
 	static String DmozData="";
 	
 	static ArrayList<String> features= new ArrayList<String>();
 	static ArrayList<Integer> featureFreq= new ArrayList<Integer>();
	
	public static void main(String[] args) throws Exception{
		
		initializeDmozMap();
		
		//Retrieving History URLS and storing it in an array historyitems
		ArrayList<String> historyitems= new ArrayList<String>();
		FileInputStream fis=new FileInputStream("ImpureHistory.doc");
		DataInputStream datain=new DataInputStream(fis);
		BufferedReader buf=new BufferedReader(new InputStreamReader(datain));
		int index=0;
		
		while((strLine = buf.readLine()) != null){
			String s1=" ";
			chararray1=strLine.toCharArray();
			for(index=8;chararray1[index]!='/';index++){
				
			}
			
			for(int i=0;i<index;i++){			
				s1=s1+chararray1[i];
			}
			
			if(!historyitems.contains(s1)){
				historyitems.add(s1);
			}
			
		}
		//System.out.println(historyitems);
		datain.close();
		//Call function to get parsed html pages in form of stop words and frequency 
		//for(int i=0;i<historyitems.size();i++){
			HTMLParse("http://www.bits-pilani.ac.in");
		//}
		//System.out.println(globalArray);
		//System.out.println(globalCount);
		
		//call function to get a string from ODP, which is input to graph formation and clustering
			 Set set = hm.entrySet(); 
			  // Get an iterator 
			  Iterator i = set.iterator(); 
			  // Display elements
			  while(i.hasNext()) { 
				  Map.Entry me = (Map.Entry)i.next();
				  
				  //System.out.print(me.getKey().toString() + ": "); 
				  
				  //System.out.println(me.getValue());
				  getODPString(me.getKey().toString());
				  }
			  System.out.println(DmozData);
			  Writer out3=null;
			  out3=new BufferedWriter(new FileWriter(fsave3));
			  out3.write(DmozData);
			  out3.close();

			  //getODPString(globalArray.get(0));
			 createHashMap(); 
			  
	}
		private String stem(String word)
		{ 
	       PorterStemmer stem = new PorterStemmer(); 
	       stem.setCurrent(word); 
	       stem.stem(); 
	       return stem.getCurrent(); 
	} 


	private static void HTMLParse(String inputUrl) throws Exception{
		Writer out1=null;
		//URL url = new URL("http://facebook.com");
		URL url = new URL(inputUrl);
		Document doc = Jsoup.parse(url,3000);
 
 		//Element docBody = doc.body();
 		String docTitle=doc.title();
 		String docText=doc.text();
 		
 		//System.out.println(docBody.html());
 		//System.out.println(docTitle);
 		//System.out.println(docText);
 
 		Elements bTag = doc.getElementsByTag("b");
 		//System.out.println(bTag.html());
 		
 		String text=docTitle+"\n\n"+docText;
 		String Text=text.toLowerCase();
 		File fsave1=new File(docTitle+ ".txt");
 		out1=new BufferedWriter(new FileWriter(fsave1));
 		out1.write(Text);
 		out1.close();
 		System.out.println("File Created Successfully");
	
 		File file1=new File(docTitle+".txt");
 		Scanner scanfile=new Scanner(new FileReader(file1));
 		String theWord; 
 		String xline="";
	
 		while (scanfile.hasNext()){ 
 			theWord = scanfile.next();
 			if(theWord.startsWith("(")||theWord.startsWith("?")){
 				theWord=" ";
 			}
 			if(theWord.endsWith(")")){
 				theWord=" ";
 			}
 			xline=xline+theWord+" ";
	} 
	//System.out.println(xline);
	char[] chararray2=xline.toLowerCase().toCharArray();
	int a;
	for(a=0;a<xline.length();a++){
		if((int)chararray2[a]<97||(int)chararray2[a]>122)
		{
			chararray2[a]=' ';				
			
		}
	}
	//System.out.println((int) chararray2[5]);
	String strfinal=new String(chararray2);
	Set<String> wordsToRemove = ImmutableSet.of("a","the","an", "for","from","of","to","or","in","out","by","and","on","a","able","about","above","according","accordingly","across","actually","after",
			"afterwards",
			"again",
			"against",
			"ain't",
			"all",
			"allow",
			"allows",
			"almost",
			"alone",
			"along",
			"already",
			"also",
			"although",
			"always",
			"am",
			"among",
			"amongst",
			"an",
			"and",
			"another",
			"any",
			"anybody",
			"anyhow",
			"anyone",
			"anything",
			"anyway",
			"anyways",
			"anywhere",
			"apart",
			"appear",
			"appreciate",
			"appropriate",
			"are",
			"aren't",
			"around",
			"as",
			"aside",
			"ask",
			"asking",
			"associated",
			"at",
			"available",
			"away",
			"awfully",
			"b",
			"be",
			"became",
			"because",
			"become",
			"becomes",
			"becoming",
			"been",
			"before",
			"beforehand",
			"behind",
			"being",
			"believe",
			"below",
			"beside",
			"besides",
			"best",
			"better",
			"between",
			"beyond",
			"both",
			"brief",
			"but",
			"by",
			"c",
			"c'mon",
			"c's",
			"came",
			"can",
			"can't",
			"cannot",
			"cant",
			"cause",
			"causes",
			"certain",
			"certainly",
			"changes",
			"clearly",
			"co",
			"com",
			"come",
			"comes",
			"concerning",
			"consequently",
			"consider",
			"considering",
			"contain",
			"containing",
			"contains",
			"corresponding",
			"could",
			"couldn't",
			"course",
			"create",
			"currently",
			"d",
			"day",
			"definitely",
			"described",
			"despite",
			"did",
			"didn't",
			"different",
			"do",
			"does",
			"doesnt",
			"doing",
			"don't",
			"done",
			"down",
			"downwards",
			"during",
			"e",
			"each",
			"edu",
			"eg",
			"eight",
			"either",
			"else",
			"elsewhere",
			"enough",
			"entirely",
			"especially",
			"et",
			"etc",
			"even",
			"ever",
			"every",
			"everybody",
			"everyone",
			"everything",
			"everywhere",
			"ex",
			"exactly",
			"example",
			"except",
			"f",
			"far",
			"few",
			"fifth",
			"first",
			"five",
			"followed",
			"following",
			"follows",
			"for",
			"former",
			"formerly",
			"forth",
			"four",
			"from",
			"further",
			"furthermore",
			"g",
			"get",
			"gets",
			"getting",
			"given",
			"gives",
			"go",
			"goes",
			"going",
			"gone",
			"got",
			"gotten",
			"greetings",
			"h",
			"had",
			"hadnt",
			"happens",
			"hardly",
			"has",
			"hasn't",
			"have",
			"havent",
			"having",
			"he",
			"he's",
			"hello",
			"help",
			"hence",
			"her",
			"here",
			"here's",
			"hereafter",
			"hereby",
			"herein",
			"hereupon",
			"hers",
			"herself",
			"hi",
			"him",
			"himself",
			"his",
			"hither",
			"hopefully",
			"how",
			"howbeit",
			"however",
			"i",
			"i'd",
			"i'll",
			"i'm",
			"i've",
			"ie",
			"if",
			"ignored",
			"immediate",
			"in",
			"inasmuch",
			"inc",
			"indeed",
			"indicate",
			"indicated",
			"indicates",
			"inner",
			"insofar",
			"instead",
			"into",
			"inward",
			"is",
			"isn't",
			"it",
			"it'd",
			"it'll",
			"it's",
			"its",
			"itself",
			"j",
			"just",
			"januaryfebruarymarchaprilmayjunejulyaugustseptemberoctobernovemberdecember",
			"k",
			"keep",
			"keeps",
			"kept",
			"know",
			"knows",
			"known",
			"l",
			"last",
			"lately",
			"later",
			"latter",
			"latterly",
			"least",
			"less",
			"lest",
			"let",
			"let's",
			"like",
			"liked",
			"likely",
			"little",
			"look",
			"looking",
			"looks",
			"ltd",
			"m",
			"mainly",
			"many",
			"may",
			"maybe",
			"me",
			"mean",
			"meanwhile",
			"merely",
			"might",
			"more",
			"moreover",
			"most",
			"mostly",
			"much",
			"must",
			"my",
			"myself",
			"n",
			"name",
			"namely",
			"nd",
			"near",
			"nearly",
			"necessary",
			"need",
			"needs",
			"neither",
			"never",
			"nevertheless",
			"new",
			"next",
			"nine",
			"no",
			"nobody",
			"non",
			"none",
			"noone",
			"nor",
			"normally",
			"not",
			"nothing",
			"novel",
			"now",
			"nowhere",
			"o",
			"obviously",
			"of",
			"off",
			"often",
			"oh",
			"ok",
			"okay",
			"old",
			"on",
			"once",
			"one",
			"ones",
			"only",
			"onto",
			"or",
			"other",
			"others",
			"otherwise",
			"ought",
			"our",
			"ours",
			"ourselves",
			"out",
			"outside",
			"over",
			"overall",
			"own",
			"p",
			"particular",
			"particularly",
			"per",
			"perhaps",
			"placed",
			"please",
			"plus",
			"possible",
			"presumably",
			"probably",
			"provides",
			"q",
			"que",
			"quite",
			"qv",
			"r",
			"rather",
			"rd",
			"re",
			"really",
			"reasonably",
			"regarding",
			"regardless",
			"regards",
			"relatively",
			"respectively",
			"right",
			"s",
			"said",
			"same",
			"saw",
			"say",
			"saying",
			"says",
			"second",
			"secondly",
			"see",
			"seeing",
			"seem",
			"seemed",
			"seeming",
			"seems",
			"seen",
			"self",
			"selves",
			"sensible",
			"sent",
			"serious",
			"seriously",
			"seven",
			"several",
			"shall",
			"she",
			"should",
			"shouldn't",
			"since",
			"six",
			"so",
			"some",
			"somebody",
			"somehow",
			"someone",
			"something",
			"sometime",
			"sometimes",
			"somewhat",
			"somewhere",
			"soon",
			"sorry",
			"specified",
			"specify",
			"specifying",
			"still",
			"sub",
			"such",
			"sup",
			"sure",
			"t",
			"t's",
			"take",
			"taken",
			"tell",
			"tends",
			"th",
			"than",
			"thank",
			"thanks",
			"thanx",
			"that",
			"that's",
			"thats",
			"the",
			"their",
			"theirs",
			"them",
			"themselves",
			"then",
			"thence",
			"there",
			"there's",
			"thereafter",
			"thereby",
			"therefore",
			"therein",
			"theres",
			"thereupon",
			"these",
			"they",
			"they'd",
			"they'll",
			"they're",
			"they've",
			"think",
			"third",
			"this",
			"thorough",
			"thoroughly",
			"those",
			"though",
			"three",
			"through",
			"throughout",
			"thru",
			"thus",
			"to",
			"together",
			"too",
			"took",
			"toward",
			"towards",
			"tried",
			"tries",
			"truly",
			"try",
			"trying",
			"twice",
			"two",
			"u",
			"un",
			"under",
			"unfortunately",
			"unless",
			"unlikely",
			"until",
			"unto",
			"up",
			"upon",
			"us",
			"use",
			"used",
			"useful",
			"uses",
			"using",
			"usually",
			"uucp",
			"v",
			"value",
			"various",
			"very",
			"vi",
			"via",
			"viz",
			"vs",
			"w",
			"want",
			"wants",
			"was",
			"wasn't",
			"way",
			"we",
			"we'd",
			"we'll",
			"we're",
			"we've",
			"welcome",
			"well",
			"went",
			"were",
			"weren't",
			"what",
			"what's",
			"whatever",
			"when",
			"whence",
			"whenever",
			"where",
			"where's",
			"whereafter",
			"whereas",
			"whereby",
			"wherein",
			"whereupon",
			"wherever",
			"whether",
			"which",
			"while",
			"whither",
			"who",
			"who's",
			"whoever",
			"whole",
			"whom",
			"whose",
			"why",
			"will",
			"willing",
			"wish",
			"with",
			"within",
			"without",
			"won't",
			"wonder",
			"would",
			"would",
			"wouldn't",
			"x",
			"y",
			"yes",
			"yet",
			"you",
			"you'd",
			"you'll",
			"you're",
			"you've",
			"your",
			"yours",
			"yourself",
			"yourselves",
			"z",
			"zero"); 
	 
    // this code will run in a loop reading one line after another from the file 
    // 
    String linex = strfinal.toLowerCase(); 
    StringBuffer outputLine = new StringBuffer(); 
    for (String word : Splitter.on(Pattern.compile(" ")).trimResults().omitEmptyStrings().split(linex)) { 
        if (!wordsToRemove.contains(word)) { 
            if (outputLine.length() > 0) { 
                outputLine.append(' '); 
            } 
            outputLine.append(word); 
        } 
    } 
 
    // 
    System.out.println(outputLine.toString()); 
    Writer out2=null;
    File fsave2=new File("clean.txt");
     try{
    	 out2=new BufferedWriter(new FileWriter(fsave2));
     out2.write(outputLine.toString());
     out2.close();
     }catch(IOException ie){
    	 
     }
   //IMPLEMENTING PORTER'S STEMMING ALGORITHM
     ArrayList<String> stemmedWords=new ArrayList<String>();
     String str3;
     Main stemWords=new Main();
     for (String word1 : Splitter.on(Pattern.compile(" ")).trimResults().omitEmptyStrings().split(outputLine.toString()))
     { 
    	 	str3=stemWords.stem(word1);
    	 	stemmedWords.add(str3);
	        System.out.println(str3);
     } 
   //CALCULATING FREQUENCY OF STOP WORDS, CREATING A HASHMAP AND STORING IT TO A FILE
     String keyWord;
      //HashMap hm=new HashMap();
      Writer outhits=null;
 	    File fsave11=new File("stopWordsfreq"+docTitle+".txt");
 	    outhits=new BufferedWriter(new FileWriter(fsave11));
 	    String newline = System.getProperty("line.separator");
 	    int arraysize=stemmedWords.size();
      for(int i=0;i<=arraysize-1;i++)
      {
     	 int key = 0;
     	 keyWord=stemmedWords.get(i);
     	 for(int j=i;j<(stemmedWords.size()-i);j++){
     		 if(keyWord.matches(stemmedWords.get(j))){
     			 key++;
     			 stemmedWords.remove(j);
     			 
     		 }
     		 arraysize=stemmedWords.size();
     		 
     		 //hm.put(keyWord, new Integer(key));
     		 
     	 }
     	 if(key==0){
     		 key=1;
     	 }
     	 if(globalArray.contains(keyWord)){
     		 int cout=globalArray.indexOf(keyWord);
     		 if(globalCount.get(cout)!=null){
     			 int key1=globalCount.get(cout)+key;
     			 globalCount.add(cout, key1);
     		 }
     		 else
     		 {
     			 globalCount.add(cout, key);
     		 }
     		 
     	 }
     	 else{
     		 globalArray.add(keyWord);
     		 globalCount.add(key);
     	 }
     	
     	
     	 
      }
      
      
      for(int cnt=0;cnt<10;cnt++){
      int highest = globalCount.get(0); // note: don't do this if the array could be empty
      int HighestInd=0;
      for(int k = 0; k < globalCount.size(); k++) {
          if(highest<globalCount.get(k)){ 
        	  HighestInd=k;
        	  highest = globalCount.get(k);
          }
      }
      System.out.println(highest+" "+HighestInd+" "+globalArray.get(HighestInd));
      hm.put(globalArray.get(HighestInd),highest);
      outhits.write(globalArray.get(HighestInd)+ ": " +globalCount.remove(HighestInd)+newline);
      globalArray.remove(HighestInd);
      globalCount.remove(HighestInd);
      }
      outhits.close();
   	}
	private static String getODPString(String searchWord) throws Exception{
		System.out.println(searchWord); 
		
		
		  URL url = new URL("http://www.dmoz.org/search?q="+searchWord);
	   
	    Document doc = Jsoup.parse(url,3000);
	     
	     //Element docBody = doc.body();
	     String docTitle=doc.title();
	     String docText=doc.text();
	     //System.out.println(docBody.html());
	     //System.out.println(docTitle);
	     //System.out.println(docText);
	     
	     Elements bTag = doc.getElementsByTag("strong");
	     String dir=bTag.html().toString();
	     String removeStr1="Open Directory Categories";
	     String removeStr2="Open Directory Sites";
	     if(dir.contains(removeStr1)){
	     String dir1=dir.replace(searchWord, "");
	     dir1=dir1.replace(removeStr1,"");
	     dir1=dir1.replace(removeStr2, "");
	     dir1=dir1.trim();
	     //System.out.println(dir1);
	     
	     String newline = System.getProperty("line.separator");
	     //System.out.println(DmozData);
	     
	     DmozData=DmozData.concat(dir1);
	     DmozData=DmozData.concat(newline);
	     DmozData=DmozData.concat(newline);
	     //System.out.println(DmozData);
	    
	     
	     FileInputStream fstreami=new FileInputStream(fsave3);
	     DataInputStream in1=new DataInputStream(fstreami);
	     BufferedReader brin=new BufferedReader(new InputStreamReader(in1));
	     String strLineOdp=brin.readLine().toLowerCase();
	     if(strLineOdp.contains(searchWord.toLowerCase())){
	    	 //System.out.println(strLineOdp);
	      }
	     String ODPresult="Directory:"+strLineOdp;
	     //System.out.println(ODPresult);
	     
	     return ODPresult;
	    }
	     else{
	    	 return "Directory";
	     }
	}
	private static void initializeDmozMap(){
		Dmoz.put("Arts",0);
		Dmoz.put("Business",0);
		Dmoz.put("Computer",0);
		Dmoz.put("Games",0);
		Dmoz.put("Health",0);
		Dmoz.put("Home",0);
		Dmoz.put("Kids and Teens",0);
		Dmoz.put("News",0);
		Dmoz.put("Recreation",0);
		Dmoz.put("Reference",0);
		Dmoz.put("Regional",0);
		Dmoz.put("Science",0);
		Dmoz.put("Shopping",0);
		Dmoz.put("Society",0);
		Dmoz.put("Sports",0);
		Dmoz.put("World",0);
		
		
	}
	private static void createHashMap(){
		
	}
}	
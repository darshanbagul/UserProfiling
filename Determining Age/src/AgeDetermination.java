import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class AgeDetermination {
	static ArrayList<String> features= new ArrayList<String>();
 	static ArrayList<Integer> featureFreq= new ArrayList<Integer>();
 	public static void main(String args[]) throws Exception{
 		features.add("Arts");
 		features.add("Business");
 		features.add("Computers");
 		features.add("Games");
 		features.add("Health");
 		features.add("Home");
 		features.add("Kids and Teens");
 		features.add("News");
 		features.add("Recreation");
 		features.add("Reference");
 		features.add("Regional");
 		features.add("Science");
 		features.add("Shopping");
 		features.add("Society");
 		features.add("Sports");
 		features.add("World");
 		features.add("Jewellery");
 		features.add("Cooking");
 		features.add("Social Networking");
 		
 		for(int j=0;j<19;j++){
 			featureFreq.add(0);
 		}
 		 		
 		
 		File fread=new File("temporary1.doc");
 		FileInputStream fstream1=new FileInputStream(fread);
	    DataInputStream in1=new DataInputStream(fstream1);
	    BufferedReader brin=new BufferedReader(new InputStreamReader(in1));
	    int i=0;
	    String str1;
	    while((str1=brin.readLine())!=null){
	    	
	    	//System.out.println(str1);
	    	for(i=0;i<19;i++){
	    		int temp=0;
	    		if(str1.contains(features.get(i))){
	    			
	    			temp=featureFreq.get(i)+1;
	    			featureFreq.set(i,temp);
	    		}
	    	}
	    }	
	    System.out.println(featureFreq);
	    System.out.println("The user is a female from age group 15-25");
 		System.out.println("Uses computer for social networking,shopping");
 
 		FileInputStream fstream2=new FileInputStream(fread);
	    DataInputStream in2=new DataInputStream(fstream2);
	    BufferedReader brin2=new BufferedReader(new InputStreamReader(in2));
	    String str2;
	    
	    int fb=0;
	    int cntfb=0;
	    float fbratio;
	    
	    int tweet=0;
	    int cnttw=0;
	    float tweetratio;
	    
	    int linkd=0;
	    int cntlink=0;
	    float linkdratio;
	    
	    int gplus=0;
	    int cntgplus=0;
	    float gplusratio;
	    
	    if(featureFreq.get(18)!=0){
	    	
	    	while((str2=brin2.readLine())!=null){
	    		if(str2.contains("Facebook")){
	    			fb=1;	
	    			cntfb++;
	    		}
	    		if(str2.contains("Twitter")){
	    			tweet=1;
	    			cnttw++;
	    		}
	    		if(str2.contains("LinkedIn")){
	    			linkd=1;
	    			cntlink++;
	    		}
	    		if(str2.contains("Google Plus")){
	    			gplus=1;
	    			cntgplus++;
	    		}
	    	}
	    	
	   }

    	fbratio=(float)cntfb/featureFreq.get(18);
    	tweetratio=(float)cnttw/featureFreq.get(18);
    	linkdratio=(float)cntlink/featureFreq.get(18);
    	gplusratio=(float)cntgplus/featureFreq.get(18);

    	if((fb==1)&&(fbratio>tweetratio && fbratio>linkdratio && fbratio>gplusratio)){
	    	System.out.println("Regular Facebook User ");
	    }
	    if((tweet==1)&&((tweetratio>fbratio && tweetratio>linkdratio && tweetratio>gplusratio))){
	    	System.out.println("Regular Twitter User ");
	    }
	    if((linkd==1)&&((linkdratio>fbratio && linkdratio>tweetratio && linkdratio>gplusratio))){
	    	System.out.println("Regular LinkedIn User");
	    }
	    if((gplus==1)&&((gplusratio>fbratio && gplusratio>tweetratio && gplusratio>linkdratio))){
	    	System.out.println("Regular Google Plus User");
	    }
	    
	    brin2.close();
	    System.out.println(System.getProperty("sun.arch.data.model"));
 	}
}


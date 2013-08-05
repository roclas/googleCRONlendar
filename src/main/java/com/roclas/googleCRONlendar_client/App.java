package com.roclas.googleCRONlendar_client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.google.gdata.data.DateTime;
import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.util.ServiceException;

/**
 * Hello world!
 *
 */
public class App 
{
    private static final String EXECUTE = "execute";
    
    public static String command(String cmd){ 
     String result = "";
     try 
     { 
    	Process p=Runtime.getRuntime().exec(cmd); 
    	p.waitFor(); 
    	BufferedReader reader=new BufferedReader(new InputStreamReader(p.getInputStream())); 
    	String line=reader.readLine(); 
    	while(line!=null) 
    	{ 
    	System.out.println(line); 
    	result+=line;
    	line=reader.readLine(); 
    	} 
	
     } 
     catch(IOException e1) {} 
     catch(InterruptedException e2) {}
	return result; 
    } 

	public static void main( String[] args ) throws IOException, ServiceException
    {
    	String user=null,password=null;
    	if(args.length<2){
    		usage();
    		return;
    	}
    	for(int i=0;i<args.length;i++){
    	    			switch(i){
    	    			case 0:user=args[i];break;
    			  			case 1:password=args[i];break;
    			  			default:break;
    		  			}
    	}
        CalendarOperator operator = new CalendarOperator(user,password);
        CalendarEventEntry event = operator.getCurrentEvent();
        if(event!=null){
        	//if(event.getTitle().toString().trim().toLowerCase().startsWith("execute")){
        	if(event.getPlainTextContent().trim().toLowerCase().startsWith(EXECUTE)){
        		System.out.println( command(event.getPlainTextContent().trim().split(EXECUTE)[1]));
        	    event.getTimes().get(0).setEndTime((new DateTime()).now());
        	    event.update();
        	}else{ System.out.println("No event to run:"+event.getPlainTextContent()+event.getEtag()); }
        }else{ System.out.println("no events to run"); }
    }

	private static void usage() {
		System.out.println("Usage: java -jar googleCRONlendar.jar <myuser@gmail.com> <mypassword>");
	}
}

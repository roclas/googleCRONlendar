package com.roclas.googleCRONlendar_client;

import java.io.IOException;
import com.google.gdata.data.DateTime;
import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.util.ServiceException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException, ServiceException
    {
    	if(args.length<2){
    		usage();
    		return;
    	}
    	String user=null,password=null;
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
        	if(event.getTitle().toString().trim().toLowerCase().startsWith("execute")){
        		System.out.println(event.getTitle()+"-execute-"+event.getPlainTextContent());
        	        		event.getTimes().get(0).setEndTime((new DateTime()).now());
        	        		event.update();
        	}
        }
    }

	private static void usage() {
		System.out.println("Usage: java -jar googleCRONlendar.jar <myuser@gmail.com> <mypassword>");
	}
}

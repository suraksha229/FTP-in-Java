/*
Project created by 
Sanjana B,Shamitha S,Suraksha and Sowmya R,
IT 3rd Sem,
B.Tech,
NITK.
*/

import java.io.*;
import java.net.*;
public class server {
	static ServerSocket s;
	static Socket cs;
	public static void download(String name){
		OutputStream o;
		DataOutputStream od;
		try{
        			o=cs.getOutputStream();
        			od=new DataOutputStream(o);
        			FileReader f=new FileReader(name);
        			String line;
                		BufferedReader br=new BufferedReader(f);
        			while((line=br.readLine())!=null){
        		        		od.writeUTF(line);
        			}
        			br.close();
        		}
       		catch(Exception e){
        			System.out.println(e);
        		}
	}
	public static void upload(String name){
	InputStream i; 
    	DataInputStream di;
    	try{
    		i=cs.getInputStream();
    		di=new DataInputStream(i);
    		File file1=new File(name);
    		FileWriter fw=new FileWriter(file1);
    		BufferedWriter bw=new BufferedWriter(fw);
    		String line;
    		while((line=di.readUTF())!=null)
    		{
    			bw.write(line+"\n");
    			bw.flush();
    		}
    		bw.close();
    	}
    	catch(Exception e){
    		
    	}
	}
    public static void main(String args[]){
    	OutputStream o;
	DataOutputStream od;
    	InputStream i; 
    	DataInputStream di;
        try{
        	s=new ServerSocket(4000);
        	System.out.println("Server started on port 4000");
            	cs=s.accept();
            	System.out.println("Accepted a client" ); 
            	i=cs.getInputStream();
            	di=new DataInputStream(i);
            	o=cs.getOutputStream();
        	od=new DataOutputStream(o);
        	String name,path,absoluteFilePath;
            	int choice=di.read();
            	File file1;
            	switch(choice){
            	case 1:
            	name=di.readUTF();
	path=di.readUTF();
	absoluteFilePath = System.getProperty(path) + File.separator + name;
            	file1=new File(absoluteFilePath);
	boolean n=file1.createNewFile();
	if(n){
		od.writeUTF("File will be uploaded");
		upload(name);
	}	
            	else
	od.writeUTF("File name already exists");
	break;
	case 2:
	name=di.readUTF();
	path=di.readUTF();
	absoluteFilePath = System.getProperty(path) + File.separator + name;
	file1=new File(absoluteFilePath);
	if(file1.exists()){
		od.writeUTF("File will be downloaded");
		download(name);					
	}
	else
		od.writeUTF("File name doesnot exist");
	break;          	
            }
              
            s.close();
            cs.close();
      }
      catch(Exception e){
      System.out.println(e);
   }       
 }
}

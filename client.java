/*
Project created by 
Sanjana B,Shamitha S,Suraksha and Sowmya R,
IT 3rd Sem
B.Tech,
NITK.
*/
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
public class client implements ActionListener{
	Socket cs;
	JButton b3,b;
     	String name1;
	JRadioButton b1,b2;
	JFrame frame,f;
	JLabel namep,passp,ipp,filep,pathp;
	JPanel panel,p;
	JTextField name,ip,t,file,path;
	JPasswordField pass;
	String ip2,fileto,pathto;
	public  String filetoupload(){
		 String name=null;
		 try{
			JFileChooser j=new JFileChooser(System.getProperty("user.dir"));
			int result=j.showOpenDialog(frame);
			if(result==JFileChooser.APPROVE_OPTION){
				File s=j.getSelectedFile();
				name=s.getName();
			}
			else if(result==JFileChooser.CANCEL_OPTION)
			{	
				cs.close();
				System.exit(1);
			}
		}
		catch(Exception e){}
		return name;
	}
	public  void upload(String name){
		try{
			frame.setVisible(false);
			OutputStream o=cs.getOutputStream();
            			DataOutputStream d=new DataOutputStream(o);
            			FileReader f=new FileReader(name);
            			BufferedReader br=new BufferedReader(f);
            			String line;
            			System.out.println("Uploading new file");
            			while((line=br.readLine())!=null){
            			         	d.writeUTF(line);
            			}
                   		br.close();
           	       		cs.close();
            		}
		catch(Exception e){
			System.out.println("No such file exists");
		}
		System.exit(0);
	}
	public  void download(String name){
	        	try{
		        	frame.setVisible(false);
	        		InputStream i=cs.getInputStream();
	        		DataInputStream di=new DataInputStream(i);
	        		String line;
	        	        	File file2=new File(name);
	        	        	FileWriter fw=new FileWriter(file2);
        	   		BufferedWriter bw=new BufferedWriter(fw);
        	  	        	System.out.println("Downloading the file");
	        		while((line=di.readUTF())!=null){
	        		        	bw.write(line+"\n");
	        			bw.flush();
	        		}
        			bw.close();
        			cs.close();
	     	}
		catch(Exception ep){
		}
		System.exit(0);
	}
	
	public void display(){
		frame=new JFrame("FTP Implemenation");
		frame.setSize(400,300);
		frame.setResizable(false);
		frame.setLayout(new GridLayout(2,0,5,5));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel=new JPanel();
		panel.setLayout(new FlowLayout());
		frame.add(panel);
		namep=new JLabel("NAME:",JLabel.RIGHT);
		passp=new JLabel("PASSWORD:",JLabel.RIGHT);
		ipp=new JLabel("IP ADDRESS:",JLabel.LEFT);
		name=new JTextField(30);
		pass=new JPasswordField(26);
		ip=new JTextField(26);
		pathp=new JLabel("PATH:",JLabel.RIGHT);
		path=new JTextField(30);
		filep=new JLabel("FILE:",JLabel.RIGHT);
		file=new JTextField(30);
		panel.add(namep);
		panel.add(name);
		panel.add(passp);
		panel.add(pass);
		panel.add(ipp);
		panel.add(ip);
		panel.add(pathp);
		panel.add(path);
		panel.add(filep);
		panel.add(file);
		b1=new JRadioButton("Upload");
		b1.setSelected(true);
		b2=new JRadioButton("Download");
 		ButtonGroup bg=new ButtonGroup();
		bg.add(b1);
		bg.add(b2);
		panel.add(b1);
		panel.add(b2);
		b3=new JButton("LOGIN");
		panel.add(b3);
		this.b3.addActionListener(this);
		frame.setVisible(true);
		return;
	}
	public void actionPerformed(ActionEvent e){
		if(e.getSource().equals(b3)){
			char[] P=pass.getPassword();
			String N=name.getText();
			String p=new String(P);
			String pa="sowmya";
			String na="name";
			if(p.equals(pa)&&N.equals(na))
			{	 
				frame.setVisible(false);
				try{
				ip2=ip.getText();
				cs=new Socket(ip2,4000);
				System.out.println("Connected to a server");
				InputStream i=cs.getInputStream();
				DataInputStream di=new DataInputStream(i);
				OutputStream out=cs.getOutputStream();
				DataOutputStream ds=new DataOutputStream(out);	
				boolean radioButtonU=b1.isSelected();
				boolean radioButtonD=b2.isSelected();
				int choice=0;					
				if(radioButtonU){
					choice=1;
				}
				if(radioButtonD){
					choice=2;
				}
				String name;
				String flag;int flag2=0;
				ds.write(choice);
				if(choice==1){
					name=filetoupload();	
					fileto=file.getText();
					pathto=path.getText();
					ds.writeUTF(fileto);
					ds.writeUTF(pathto);
					flag=di.readUTF();
					if(flag.equals("File will be uploaded")){
						JOptionPane.showMessageDialog(frame,flag);
						upload(name);			
					}
					else{
						JOptionPane.showMessageDialog(frame,flag);
						System.exit(1);
					}
					
				}
				else{
					fileto=file.getText();
					pathto=path.getText();	
					ds.writeUTF(fileto);
					ds.writeUTF(pathto);
					flag=di.readUTF();
					if(flag.equals("File will be downloaded")){
						JOptionPane.showMessageDialog(frame,flag);
						do{
	                    JFileChooser j=new 	JFileChooser(System.getProperty("user.dir"));
			int result1=j.showSaveDialog(null);
			if(result1==JFileChooser.APPROVE_OPTION){
				File s=j.getSelectedFile();
				name1=s.getName();
				 flag2=0;
				File d=new File(System.getProperty("user.dir"));
				File[] filelist=d.listFiles();
				for(File fs:filelist)
				{
					if(fs.getAbsolutePath().equals(s.getAbsolutePath()))
						flag2=1;
				}
				if(flag2==1)
					JOptionPane.showMessageDialog(frame,"File already exists! Enter a different name");
			}
			else if(result1==JFileChooser.CANCEL_OPTION)
			{
				cs.close();
				System.exit(1);
			}
			
		}
		while(flag2==1);
			download(name1);								
	}
		else{
			JOptionPane.showMessageDialog(frame,flag);
			System.exit(1);
			}
		}
						
	}
	catch(Exception ec){
		}
		
	}
	else
	{
		JOptionPane.showMessageDialog(frame,"Wrong Password or Username!!!");
	}		
	}		
}     
public static void main(String args[]){
        try{
        	BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        	client obj=new client();
               	obj.display();
             	br.close();
      }  
        catch(Exception e){
         }
        
    }
}

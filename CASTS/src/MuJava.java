import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Scanner;

/**
 * Handles all muJava related Operations - SINGLETON
 * @author Jonathan Coffman
 *
 */
class MuJava 
{
	private static MuJava muJava = null;
	private String jarPath = "";
	private String separator = System.getProperty("file.separator");
	private String path;
	private boolean created;
	
	private MuJava() {}
	
	/**
	 * Gets Singleton Emma Object
	 * @return MuJava
	 * @author Jonathan Coffman
	 */
	static MuJava totalMu()
	{
		if (muJava == null)
			muJava = new MuJava();
		return muJava;
	}
	
	/**
	 * Process the muJava object on the requested target
	 * @author Jonathan Coffman
	 */
	void MuRunner()
	{
		boolean loop = true;
		while(loop)
		{
			Scanner scan = new Scanner(System.in);
			//Get the path to the jar file from the user
			System.out.println("What is the ABSOLUTE path to the .jar file?");
			jarPath = scan.nextLine();
			if(DirChecker.getChecker().check(jarPath))
				loop = false;
			else
				System.out.print("File does not exist");
		}
			
		createDir();
		if(DownloadAll())
			//execute the command 
			try {
				MuCommand();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * executes the command for each OS
	 * @author Jonathan Coffman
	 */
	private void MuCommand() throws IOException 
	{
		if(System.getProperty("os.name").matches("Mac OS X"))
			macMuCommand(path, jarPath);
		else
			winMuCommand(path, jarPath);
	}

	/**
	 * This downloads all the files needed for muJava
	 * @author Jonathan Coffman
	 *
	 */
	private boolean DownloadAll()
	{
		if(Download(path + separator, "https://cs.gmu.edu/~offutt/mujava/mujava.jar", "mujava.jar"))
			if(Download(path + separator, "https://cs.gmu.edu/~offutt/mujava/openjava.jar", "openjava.jar"))
				if(Download(path + separator, "https://cs.gmu.edu/~offutt/mujava/muscript/junit.jar", "junit.jar"))
					if(Download(path + separator, "https://cs.gmu.edu/~offutt/mujava/muscript/hamcrest-core-1.3.jar", "hamcrest-core-1.3.jar"))
							if(Download(path + separator, "https://cs.gmu.edu/~offutt/mujava/muscript/commons-io-2.4.jar", "commons-io-2.4.jar"))
								return(makeConfig());
		return false;
	}
	
	/**
	 * This creates the config file based on the chosen jar file and the download path.
	 * @authors Jonathan Coffman, Chai Thoj
	 *
	 */
	private boolean makeConfig() 
	{
		String output = "MuJava_HOME=" + path + "\n Debug_mode=false\n";
		try{
			 Writer w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("mujava.config"), "utf-8"));
			 w.write(output);
			 w.close();
			 return true;
			 }
		catch (IOException e){
			 e.printStackTrace();
			 }
		return false;
	}

	/**
	 * This creates the directory that stores the tools
	 * @author Jonathan Coffman
	 *
	 */
	private void createDir()
	{
		
		path = System.getProperty("user.home") + separator + "Desktop" + 
				separator + "CASTS" + separator + "muJava";
		File dir = new File(path);
		if(!created)
		{
			// attempt to create the directory here
			try {
				dir.mkdir();
			} catch (SecurityException e) {
				e.printStackTrace();
				System.exit(-1);
			}
			created = true;
		}
	}
	
	/**
	 * Downloads the MuJava file to the CASTS folder 
	 * @param downloadPath
	 * @return success
	 * @author Jonathan Coffman
	 */
	private boolean Download(String downloadPath, String url, String name) 
	{
		//if the file exists, dont download
		if(new File(downloadPath + separator + name).exists()) 
			return true;

		System.out.println("Downloading Mu file: " + name);
		URL website;
		FileOutputStream fos;
		try 
		{
			website = new URL(url);
			ReadableByteChannel rbc = Channels.newChannel(website.openStream());
			fos = new FileOutputStream(downloadPath + name);
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			return true;

		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Builds muJava Command for mac, it is assumed that the mujava.config file is in the current directory.
	 * @authors Jonathan Coffman, Chai Thoj
	 *
	 */
	private void macMuCommand(String muPath, String jarPath) throws IOException 
	{
		String[] exec = new String[2];
		exec[0] = "java mujava.gui.GenMutantsMain"; //Start MuJava GUI for generating mutants.
		exec[1] = "java mujava.gui.RunTestMain"; //Start MuJava GUI for running mutant tests options.
		Execute.getExecute().exec(exec);
	}

	/**
	 * This builds the windows muJava command
	 * @authors Jonathan Coffman, Chai Thoj
	 *
	 */
	private void winMuCommand(String muPath, String jarPath) 
	{
		String[] exec = new String[2];
		exec[0] = "java mujava.gui.GenMutantsMain"; //Start MuJava GUI for generating mutants.
		exec[1] = "java mujava.gui.RunTestMain"; //Start MuJava GUI for running mutant tests options.
		Execute.getExecute().exec(exec);
	}
}

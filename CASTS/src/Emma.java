import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Scanner;

/**
 * Handles all Emma related Operations - SINGLETON
 * @author Jonathan Coffman
 *
 */
class Emma 
{
	private static Emma emma = null;
	private String jarPath = "";
	private String separator = System.getProperty("file.separator");
	
	private Emma() {}
	
	/**
	 * Gets Singleton Emma Object
	 * @return Emma
	 * @author Jonathan Coffman
	 */
	static Emma totalEmma()
	{
		if (emma == null)
			emma = new Emma();
		return emma;
	}
	
	/**
	 * Process the Emma object on the requested target
	 * @author Jonathan Coffman
	 */
	void EmmaRunner()
	{
		Scanner scan = new Scanner(System.in);
		//Get the path to the jar file from the user
		System.out.println("What is the ABSOLUTE path to the .jar file?");
		jarPath = scan.nextLine();
		
		//Download EMMA
		DownloadEmma(CASTSDirectory.getCASTSDirectory().getPath());
		
		//unzip the downloaded files based on the OS
		try 
			{ HandleZip(); }  
		catch (IOException e) {
			System.out.println("FAILURE UNZIPPING");
			System.exit(-1);
		}
		
		//execute the command 
		try
			{ EmmaCommand(); } 
		catch (IOException e) {
			System.out.println("FAILURE Executing EMMA Command");
			System.exit(-1);
		}
	}
	
	/**
	 * executes the command for each OS
	 * @author Jonathan Coffman
	 */
	private void EmmaCommand() throws IOException 
	{
		String preCommand = CASTSDirectory.getCASTSDirectory().getPath() + "emma-2.0.5312" + separator + "lib" + separator;
		
		if(System.getProperty("os.name").matches("Mac OS X"))
			macEmmaCommand(preCommand, jarPath);
		else
			winEmmaCommand(preCommand, jarPath);
	}


	/**
	 * Processes the ZIPPED Emma files based on OS.
	 * @return Emma
	 * @author Jonathan Coffman
	 */
	private void HandleZip() throws IOException 
	{
		if(System.getProperty("os.name").matches("Mac OS X"))
			Zipper.getZipper().UnpackMacZip(CASTSDirectory.getCASTSDirectory().getPath(), "emma-2.0.5312.zip");
		else
			Zipper.getZipper().unZip(CASTSDirectory.getCASTSDirectory().getPath() + "emma-2.0.5312.zip", 
						CASTSDirectory.getCASTSDirectory().getPath());
	}
	
	/**
	 * Downloads the Emma files to the CASTS folder 
	 * @param downloadPath
	 * @return success
	 * @author Jonathan Coffman
	 */
	private int DownloadEmma(String downloadPath) 
	{
		//if the file exists, dont download
		if(new File(downloadPath + "emma-2.0.5312").exists()) 
			return 0;

		System.out.println("Downloading emma file...");
		URL website;
		FileOutputStream fos;
		try 
		{
			website = new URL("http://downloads.sourceforge.net/project/emma/emma-release/2.0.5312/emma-2.0.5312.zip?r=https%3A%2F%2Fsourceforge.net%2Fprojects%2Femma%2Ffiles%2Femma-release%2F2.0.5312%2F&ts=1461308089&use_mirror=iweb");
			ReadableByteChannel rbc = Channels.newChannel(website.openStream());
			fos = new FileOutputStream(downloadPath + "emma-2.0.5312.zip");
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);

			return 1;

		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * Builds Emma Command for mac
	 * @author Jonathan Coffman
	 *
	 */
	private void macEmmaCommand(String emmaPath, String jarPath) throws IOException 
	{

		File file = new File(System.getProperty("user.home") + "/Desktop/CASTS/scriptFile.txt");
		file.createNewFile();
		//use buffering
		Writer output = new BufferedWriter(new FileWriter(file));
		try {
			//FileWriter always assumes default encoding is OK!
			output.write("osascript -e \'tell app \"Terminal\" \ndo script \"java -jar "+ emmaPath + 
					"emma.jar -f -r html -Dreport.html.out.file="+
					System.getProperty("user.home") + "/Desktop/coverage.html -jar " + jarPath + "\"\n end tell\'");
		}
		finally {
			output.close();
		}

		String[] chmod = new String[3];
		chmod[0] = "chmod";
		chmod[1] = "+x";
		chmod[2] = System.getProperty("user.home") + "/Desktop/CASTS/scriptFile.txt";

		Execute.getExecute().exec(chmod);

		String[] run = new String[1];
		run[0] = System.getProperty("user.home") + "/Desktop/CASTS/./scriptFile.txt";
		//file.delete(); //TODO

		Execute.getExecute().exec(run);
	}

	/**
	 * This builds the windows Emma command
	 * @author Jonathan Coffman
	 *
	 */
	private void winEmmaCommand(String emmaPath, String jarPath) 
	{

		//start cmd.exe @cmd /k "Command"
		String separator = System.getProperty("file.separator");
		String[] exec = new String[4];
		exec[0] = "cmd.exe";
		exec[1] = "@cmd";
		exec[2] = "/k";
		exec[3] = "\"java -jar " + emmaPath + "emma.jar -f -r html -Dreport.html.out.file="+
				System.getProperty("user.home") + separator + "Desktop" + separator + "coverage.html -jar " + jarPath + "\"";

		Execute.getExecute().exec(exec);
	}
}

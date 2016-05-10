import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Scanner;

/**
 * Handles all Evo Operations with protected access - SINGLETON
 * @author Jonathan Coffman
 */
class Evo 
{
	private static Evo evo = null;

	private Evo() {}

	/**
	 * Gets Singleton Evo Object
	 * @return Evo
	 * @author Jonathan Coffman
	 */
	static Evo totalEvo()
	{
		if(evo == null)
			evo = new Evo();
		return evo;
	}

	/**
	 * Process the Evo object on the requested target
	 * @author Jonathan Coffman
	 */
	void EvoRunner()
	{
		Scanner scan = new Scanner(System.in);
		
		//Download EvoSuite to the casts directory
		DownloadEvoSuite(CASTSDirectory.getCASTSDirectory().getPath());

		//Prompt the user for the path they wish to use
		System.out.println("What is the ABSOLUTE path to the .classpath file or bin?");
		String targetPath = scan.nextLine();


		//build the command after downloading the file
		String[] EvoCommand = buildEvoCommand(CASTSDirectory.getCASTSDirectory().getPath(), targetPath);

		//TODO remove Debugging print
		System.out.println(EvoCommand[0] + EvoCommand[1] + EvoCommand[2] + EvoCommand[3] + EvoCommand[4]);
		System.out.println(targetPath);

		//Execute the EvoSuite Command
		Execute.getExecute().exec(EvoCommand);
	}


	/**
	 * Downloads the evoSuite File to the users desktop
	 * @return int success
	 * @author Jonathan Coffman
	 */
	private int DownloadEvoSuite(String downloadPath) 
	{
		//if the file exists, dont download
		if(new File(downloadPath + "evosuite-1.0.3.jar").exists()) 
			return 0;
		System.out.println("Downloading evoSuite File...");
		URL website;
		FileOutputStream fos;
		try {
			website = new URL("https://github.com/EvoSuite/evosuite/releases/download/v1.0.3/evosuite-1.0.3.jar");
			ReadableByteChannel rbc = Channels.newChannel(website.openStream());
			fos = new FileOutputStream(downloadPath + "evosuite-1.0.3.jar");
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * Builds the Evo Command based on the path
	 * @return String[] EvoCommand
	 * @author Jonathan Coffman
	 */
	private String[] buildEvoCommand(String downloadPath, String targetPath)
	{
		String separator = System.getProperty("file.separator");
		String dir = System.getProperty("user.home") + separator + "Desktop";
		String[] out = new String[6];
		out[0] = "java";
		out[1] = "-jar";
		out[2] = downloadPath + "evosuite-1.0.3.jar";
		out[3] = ("-DOUTPUT_DIR=" + dir.toString() + System.getProperty("file.separator"));
		out[4] = "-target";
		out[5] = targetPath;
		return(out);
	}
}

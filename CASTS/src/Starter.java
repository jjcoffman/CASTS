
public class Starter 
{

	/**
	 * Main creates the download directory for tools and runs each tool.
	 * @author Jonathan Coffman
	 */
	public static void main(String[] args) 
	{
		//Create the working Directory
		CASTSDirectory.getCASTSDirectory().createDir();
		
		
		System.out.println("\n*************************************************************************************\n\n"
				+ "Welcome to the Combined Automated Software Testing Suite.\n"
				+ "Please follow the prompts as EvoSuite, Emma, and MuJava (pulled from this version for \n"
				+ "further development) will combine to execute testing on your code. Be sure to provide\n"
				+ "ABSOLUTE paths to the requested items to prevent failures.\n"
				+ "\n*************************************************************************************\n");
		
		
		Evo.totalEvo().EvoRunner();
		Emma.totalEmma().EmmaRunner();
		MuJava.totalMu().MuRunner();
		
		//TODO this removes the directory, WORK IN PROGRESS
		//CASTSDirectory.getCASTSDirectory().removeDir();
	}
}

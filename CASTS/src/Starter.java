
public class Starter 
{

	/**
	 * Main creates the download directory for tools and runs each tool.
	 * @author Jonathan Coffman
	 */
	public static void main(String[] args) 
	{
		
		CASTSDirectory.getCASTSDirectory().createDir();
		
		//Evo.totalEvo().EvoRunner();
		//Emma.totalEmma().EmmaRunner();
		MuJava.totalMu().MuRunner();
		
		//TODO this removes the directory, WORK IN PROGRESS
		CASTSDirectory.getCASTSDirectory().removeDir();
	}
}

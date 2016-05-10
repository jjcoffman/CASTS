import java.io.File;
/**
 * Verify the existence of the 
 * @author Jonathan Coffman
 */
class DirChecker 
{
	private static DirChecker dirChecker = null;
	
	private DirChecker() {}
	
	static DirChecker getChecker()
	{
		if(dirChecker == null)
			dirChecker = new DirChecker();
		return dirChecker;
	}
	
	boolean check(String path)
	{
		if(path == "")
			return false;
		File file = new File(path);
		return(file.exists());
	}
}

import java.io.File;

/**
	 * Creates a directory on the users Desktop for information - SINGLETON
	 * @author Jonathan Coffman
	 */
class CASTSDirectory 
{
	private static CASTSDirectory dir = null;
	private boolean created = false;
	private String path;
	
	private CASTSDirectory() {}
	
	/**
	 * Returns the CASTSDirectory - SINGLETON
	 * @return CASTSDirectory
	 * @author Jonathan Coffman
	 */
	static CASTSDirectory getCASTSDirectory()
	{
		if(dir == null)
			dir = new CASTSDirectory();
		return dir;
	}
	
	/**
	 * This creates the directory that stores the tools
	 * @author Jonathan Coffman
	 *
	 */
	void createDir()
	{
		path = System.getProperty("user.home") + System.getProperty("file.separator") + "Desktop" + 
				System.getProperty("file.separator") + "CASTS";
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
	 * This deletes the directory that stores the tools
	 * @author Jonathan Coffman
	 *
	 */
	void removeDir() 
	{
		File dir = new File(System.getProperty("user.home") + System.getProperty("file.separator") + "Desktop" + 
				System.getProperty("file.separator") + "CASTS");
		if(created)
		{
			// attempt to create the directory here
			try 
			{
				dir.deleteOnExit();
				created = false;
			} 
			catch (SecurityException e) 
			{
				e.printStackTrace();
			}
		}
	}	
	
	/**
	 * Gets the path to the created directory that items are downloaded to.
	 * @author Jonathan Coffman
	 */
	String getPath()
	{
		if(!created)
			createDir();
		return (path + System.getProperty("file.separator"));
	}
}

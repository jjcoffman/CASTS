import java.io.IOException;
import java.io.InputStream;
import java.lang.ProcessBuilder.Redirect;

/**
 * Execute class to create a new System process that runs the passed command 
 * through a system CLI. Also captures STDOUT and displays within the Java Console.
 * @author Jonathan Coffman
 */
class Execute 
{

	private static Execute execute;
	private Execute() {}
	
	/**
	 * Gets Singleton Execute Object
	 * @return Execute
	 * @author Jonathan Coffman
	 */
	static Execute getExecute()
	{
		if(execute == null)
			execute = new Execute();
		return execute;
	}
	
	/**
	 * Executes the passed String Array with command inside on the system CLI and
	 * redirects the systems stdout to the java console
	 * @author Jonathan Coffman
	 */
	void exec(String[] command)
	{
		try {
			ProcessBuilder pb = new ProcessBuilder(command);
			pb.redirectError(Redirect.INHERIT);
			pb.redirectOutput(Redirect.INHERIT);
			pb.directory(null);
			Process p = pb.start();
			final StringBuilder out = new StringBuilder();
			final Thread reader = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						final InputStream is = p.getInputStream();
						int c;
						while ((c = is.read()) != -1) {
							out.append((char) c);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
			reader.start();
			p.waitFor();
			reader.join();
			p.destroy();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}

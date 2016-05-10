import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ProcessBuilder.Redirect;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Zipper handles the unzipping of zip files for mac and windows
 * @author Jonathan Coffman
 */
public class Zipper 
{
	private static Zipper zipper = null;
	
	private Zipper() {}
	
	/**
	 * Gets the singleton instance of the zipper
	 * @author Jonathan Coffman
	 */
	public static Zipper getZipper()
	{
		if(zipper == null)
			zipper = new Zipper();
		return zipper;
	}
	
	/**
	 * Extracts a zip entry (file entry)
	 * @param zipIn
	 * @param filePath
	 * @throws IOException
	 */
	void unZip(String zipFilePath, String destDirectory) throws IOException {
		File destDir = new File(destDirectory);
		if (!destDir.exists()) {
			destDir.mkdir();
		}
		ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
		ZipEntry entry = zipIn.getNextEntry();
		// iterates over entries in the zip file
		while (entry != null) {
			String filePath = destDirectory + File.separator + entry.getName();
			if (!entry.isDirectory()) {
				// if the entry is a file, extracts it
				extractFile(zipIn, filePath);
			} else {
				// if the entry is a directory, make the directory
				File dir = new File(filePath);
				dir.mkdir();
			}
			zipIn.closeEntry();
			entry = zipIn.getNextEntry();
		}
		zipIn.close();
	}
	
	/**
	 * Extracts a zip entry referenced via unZip(String zipFilePath, String destDirectory) (file entry)
	 * @param zipIn
	 * @param filePath
	 * @throws IOException
	 */
	private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
		byte[] bytesIn = new byte[4096];
		int read = 0;
		while ((read = zipIn.read(bytesIn)) != -1) {
			bos.write(bytesIn, 0, read);
		}
		bos.close();
	} 

	/**
	 * unpacks a zip on a mac
	 * @author Jonathan Coffman
	 *
	 */
	int UnpackMacZip(String path, String file) 
	{
		String[] out = new String[2];
		out[0] = "unzip";
		out[1] = path + file;	
		try
		{
			ProcessBuilder pb = new ProcessBuilder(out);
			pb.redirectError(Redirect.INHERIT);
			pb.redirectOutput(Redirect.INHERIT);
			pb.directory(new File(path));
			Process p = pb.start();
			//System.out.print(p.getInputStream());

			final StringBuilder out1 = new StringBuilder();
			final Thread reader = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						final InputStream is = p.getInputStream();
						int c;
						while ((c = is.read()) != -1) {
							out1.append((char) c);
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
		return 1;
	}
	
	
}

import javax.swing.JFileChooser;
import javax.swing.JFrame;

/**
 * WORK IN PROGRESS - Handles the GUI element of the CASTS program
 * @author Jonathan Coffman
 *
 */
public class Chooser extends JFrame
{
	private static final long serialVersionUID = -255844327078232716L;

	/**
	 * Creates the file chooser window
	 * @author Jonathan Coffman
	 *
	 */
	public Chooser(String type)
	{
		super();
		if(type.equals("jar") || type.equals("bin"))
		{
			JFileChooser window = new JFileChooser();
			this.setTitle("What is the ABSOULUTE Path to your " + type + " file?");
			this.setSize(400, 600);
			this.add(window);
			this.setVisible(true);
		}
		else
		{
			System.exit(0);
		}
	}
}

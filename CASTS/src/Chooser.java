import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class Chooser extends JFrame
{
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

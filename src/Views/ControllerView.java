package Views;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.*;

import CustomDataTypes.MessageConsole;
import States.State;
import Subjects.Observer;
import Subjects.Subject;

// ACTS as GUI Controller for Lift Simulation

public class ControllerView extends Frame implements Observer {

	private static final long serialVersionUID = 1L;
	private Subject subject;
	private JFrame frame = new JFrame("Lift Simulation Controller");

	private static boolean startState = false;
	private enum scenario { S1, S2, S3, ENABLE, DISABLE }
	private static scenario activeScenario = scenario.S1;

	private static ImageIcon startDefault;
	private static ImageIcon startPressed;
	private static ImageIcon stopDefault;
	private static ImageIcon stopPressed;

	private static JButton startstop = new JButton();
	private static JButton s1 = new JButton();
	private static JButton s2 = new JButton();
	private static JButton s3 = new JButton();

	private static JPanel animationView = new JPanel();
	private static JTextArea textView = new JTextArea(50, 120);
	private static MessageConsole mc = new MessageConsole(textView);

	private Map<String, ImageIcon> imageAssets;

	public ControllerView() {
		
		try {			
			//Make Start Stop Graphics Static and Globally Accessible to Class
			startDefault = new ImageIcon(ImageIO.read(getClass()
					.getClassLoader()
					.getResource("ImgAssets/MVCLift_ImgAsset_STARTDefault.png")));

			startPressed = new ImageIcon(ImageIO.read(getClass()
					.getClassLoader()
					.getResource("ImgAssets/MVCLift_ImgAsset_STARTPressed.png")));

			stopDefault = new ImageIcon(ImageIO.read(getClass()
					.getClassLoader()
					.getResource("ImgAssets/MVCLift_ImgAsset_STOPDefault.png")));

			stopPressed = new ImageIcon(ImageIO.read(getClass()
					.getClassLoader()
					.getResource("ImgAssets/MVCLift_ImgAsset_STOPPressed.png")));
			
			//Scenario Buttons in Image Assets Hash Map
			imageAssets = new HashMap<String, ImageIcon>();
			
			//Scenario 1 Button
			imageAssets.put("s1Default", new ImageIcon(ImageIO.read(getClass()
					.getClassLoader()
					.getResource("ImgAssets/MVCLift_ImgAsset_S1Default.png"))));


			imageAssets.put("s1Pressed", new ImageIcon(ImageIO.read(getClass()
					.getClassLoader()
					.getResource("ImgAssets/MVCLift_ImgAsset_S1Pressed.png"))));

			//Scenario 2 Button
			imageAssets.put("s2Default", new ImageIcon(ImageIO.read(getClass()
					.getClassLoader()
					.getResource("ImgAssets/MVCLift_ImgAsset_S2Default.png"))));


			imageAssets.put("s2Pressed", new ImageIcon(ImageIO.read(getClass()
					.getClassLoader()
					.getResource("ImgAssets/MVCLift_ImgAsset_S2Pressed.png"))));

			//Scenario 3 Button
			imageAssets.put("s3Default", new ImageIcon(ImageIO.read(getClass()
					.getClassLoader()
					.getResource("ImgAssets/MVCLift_ImgAsset_S3Default.png"))));


			imageAssets.put("s3Pressed", new ImageIcon(ImageIO.read(getClass()
					.getClassLoader()
					.getResource("ImgAssets/MVCLift_ImgAsset_S3Pressed.png"))));

		} catch (Exception ex) {

			TextView.printError("Icon Loader Error", ex.toString());
		}

		initComponents(imageAssets);

		//Creating the Frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1700, 1000);
		frame.setResizable(false);
		frame.setVisible(true);
	}

	public void initComponents(Map<String, ImageIcon> imageAssets) {		
		JPanel buttonPanel = new JPanel();
		JPanel viewPanel = new JPanel();

		Box buttonBox = Box.createVerticalBox();

		startstop.setSize(100, 40);
		s1.setSize(100, 40);
		s2.setSize(100, 40);
		s3.setSize(100, 40);
		
		activeScenario = scenario.S1;
		scenarioButtonManager(imageAssets, activeScenario);

		//Construct Vertical Button Control Panel Layout
		buttonBox.add(startstop);
		buttonBox.add(s1);
		buttonBox.add(s2);
		buttonBox.add(s3);
		buttonPanel.add(buttonBox);

		//viewPanel.add(animationView);
		viewPanel.setSize(800, 400);
		viewPanel.add(new JScrollPane(textView, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS));

		//Redirect Console Output to Message Console Text View Component in View Panel
		mc.redirectOut(Color.BLACK, null);
		mc.redirectErr(Color.RED, null);
		mc.setMessageLines(1000);

		//Adding Components to the frame
		frame.getContentPane().add(BorderLayout.LINE_END, buttonPanel);
		frame.getContentPane().add(BorderLayout.WEST, viewPanel);

		//Define button Action Listeners
		//StartStop Button
		startstop.addActionListener( new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				//START OR STOP SCENARIO
				TextView.print("Start Stop Requested");
			}
		});

		//StartStop Button
		s1.addActionListener( new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				//SELECT SCENARIO 1
				TextView.print("Scenario 1 Requested");
				activeScenario = scenario.S1;
				scenarioButtonManager(imageAssets, activeScenario);
			}
		});

		//StartStop Button
		s2.addActionListener( new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				//SELECT SCENARIO 2
				TextView.print("Scenario 2 Requested");
				activeScenario = scenario.S2;
				scenarioButtonManager(imageAssets, activeScenario);
			}
		});

		//StartStop Button
		s3.addActionListener( new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				//SELECT SCENARIO 3
				TextView.print("Scenario 3 Requested");
				activeScenario = scenario.S3;
				scenarioButtonManager(imageAssets, activeScenario);
			}
		});

	}

	public static void startStopButton(boolean start) {
		if (start) {
			try {
				startstop.setIcon(stopDefault);
				startstop.setPressedIcon(stopPressed);
			} catch (Exception ex) {

			}
			scenarioButtonManager(scenario.DISABLE);
		} else {
			startstop.setIcon(startDefault);
			startstop.setPressedIcon(startPressed);

			scenarioButtonManager(scenario.ENABLE);
			scenarioButtonManager(activeScenario);
		}
		startState = start;
	}

	public static void scenarioButtonManager(Map<String, ImageIcon> imageAssets, scenario s) {
		activeScenario = s;
		if (activeScenario == scenario.S1) {
			s1.setIcon(imageAssets.get("s1Pressed"));
			s2.setIcon(imageAssets.get("s2Default"));
			s3.setIcon(imageAssets.get("s3Default"));
		} else if (activeScenario == scenario.S2) {
			s1.setIcon(imageAssets.get("s1Default"));
			s2.setIcon(imageAssets.get("s2Pressed"));
			s3.setIcon(imageAssets.get("s3Default"));
		} else if (activeScenario == scenario.S3) {			
			s1.setIcon(imageAssets.get("s1Default"));
			s2.setIcon(imageAssets.get("s2Default"));
			s3.setIcon(imageAssets.get("s3Pressed"));
		} else if (activeScenario == scenario.ENABLE) {
			s1.setEnabled(true);
			s2.setEnabled(true);
			s3.setEnabled(true);
		} else if (activeScenario == scenario.DISABLE) {
			s1.setEnabled(false);
			s2.setEnabled(false);
			s3.setEnabled(false);
		} else {
			TextView.printError("Button Manager", "Invalid Active Scenario Detected.");
		}
		s1.setPressedIcon(imageAssets.get("s1Pressed"));
		s2.setPressedIcon(imageAssets.get("s2Pressed"));
		s3.setPressedIcon(imageAssets.get("s3Pressed"));
	}

	public static void scenarioButtonManager(scenario s) {
		activeScenario = s;
		if (activeScenario == scenario.ENABLE) {
			s1.setEnabled(true);
			s2.setEnabled(true);
			s3.setEnabled(true);
		} else if (activeScenario == scenario.DISABLE) {
			s1.setEnabled(false);
			s2.setEnabled(false);
			s3.setEnabled(false);
		} else {
			TextView.printError("Button Manager",
					"Invalid Active Scenario Detected. Image Assets missing from scenario call. Try \"scenarioButtonManager(imageAssets, scenario)\".");
		}
	}

	@Override
	public void update() {

		Object update = subject.getUpdate(this);

		if (update instanceof State ) {

			//((State) update).doAction(this.running, b);
			TextView.printError("Controller", "State");

		} else {

			TextView.printError("Object State", "Update object pushed, not of State object type.");
		}

	}

	@Override
	public void setSubject(Subject subject) {
		this.subject = subject;
	}

}

package Views;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

	private boolean running = false;
	public enum scenario { S1, S2, S3, ENABLE, DISABLE }
	public scenario activeScenario = scenario.S1; //Default scenario selection

	private static JButton startstop = new JButton();
	private static JButton s1 = new JButton();
	private static JButton s2 = new JButton();
	private static JButton s3 = new JButton();
	
	private static JPanel buttonPanel = new JPanel();
	private static JPanel viewPanel = new JPanel();
	
	private static Box buttonBox = Box.createVerticalBox();
	private static Box viewBox = Box.createVerticalBox();
	
	public static List<AnimationView> animationViews = new ArrayList<AnimationView>();
	
	private static JTextArea textView = new JTextArea(8, 120);
	private static MessageConsole mc = new MessageConsole(textView);
	private static JScrollPane scrollPane = new JScrollPane(textView, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);


	public static Map<String, ImageIcon> imageAssets = new HashMap<String, ImageIcon>();;
	
	public ControllerView() {
		animationViews.add(new AnimationView(0));
		animationViews.add(new AnimationView(1));
		
		try {
			//Button Image Icons in Image Assets Hash Map			
			//Make Start Stop Graphics Static and Globally Accessible to Class
			imageAssets.put("startDefault", new ImageIcon(ImageIO.read(getClass()
					.getClassLoader()
					.getResource("ImgAssets/MVCLift_ImgAsset_STARTDefault.png"))));

			imageAssets.put("startPressed", new ImageIcon(ImageIO.read(getClass()
					.getClassLoader()
					.getResource("ImgAssets/MVCLift_ImgAsset_STARTPressed.png"))));

			imageAssets.put("stopDefault", new ImageIcon(ImageIO.read(getClass()
					.getClassLoader()
					.getResource("ImgAssets/MVCLift_ImgAsset_STOPDefault.png"))));

			imageAssets.put("stopPressed", new ImageIcon(ImageIO.read(getClass()
					.getClassLoader()
					.getResource("ImgAssets/MVCLift_ImgAsset_STOPPressed.png"))));

			startstop.setIcon(imageAssets.get("startDefault"));
			startstop.setPressedIcon(imageAssets.get("startPressed"));

			//Scenario 1 Button
			imageAssets.put("s1Default", new ImageIcon(ImageIO.read(getClass()
					.getClassLoader()
					.getResource("ImgAssets/MVCLift_ImgAsset_S1Default.png"))));


			imageAssets.put("s1Pressed", new ImageIcon(ImageIO.read(getClass()
					.getClassLoader()
					.getResource("ImgAssets/MVCLift_ImgAsset_S1Pressed.png"))));

			s1.setIcon(imageAssets.get("s1Default"));
			s1.setPressedIcon(imageAssets.get("s1Pressed"));

			//Scenario 2 Button
			imageAssets.put("s2Default", new ImageIcon(ImageIO.read(getClass()
					.getClassLoader()
					.getResource("ImgAssets/MVCLift_ImgAsset_S2Default.png"))));


			imageAssets.put("s2Pressed", new ImageIcon(ImageIO.read(getClass()
					.getClassLoader()
					.getResource("ImgAssets/MVCLift_ImgAsset_S2Pressed.png"))));

			s2.setIcon(imageAssets.get("s2Default"));
			s2.setPressedIcon(imageAssets.get("s2Pressed"));

			//Scenario 3 Button
			imageAssets.put("s3Default", new ImageIcon(ImageIO.read(getClass()
					.getClassLoader()
					.getResource("ImgAssets/MVCLift_ImgAsset_S3Default.png"))));


			imageAssets.put("s3Pressed", new ImageIcon(ImageIO.read(getClass()
					.getClassLoader()
					.getResource("ImgAssets/MVCLift_ImgAsset_S3Pressed.png"))));

			s3.setIcon(imageAssets.get("s3Default"));
			s3.setPressedIcon(imageAssets.get("s3Pressed"));

		} catch (Exception ex) {

			TextView.printError("Icon Loader Error", ex.toString());
		}

		initComponents();

		//Creating the Frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1700, 1100);
		frame.setResizable(false);
		frame.setVisible(true);
	}

	public void initComponents() {
		
		startstop.setSize(100, 40);
		s1.setSize(100, 40);
		s2.setSize(100, 40);
		s3.setSize(100, 40);

		scenarioButtonManager(activeScenario);

		//Construct Vertical Button Control Panel Layout
		buttonBox.add(startstop);
		buttonBox.add(s1);
		buttonBox.add(s2);
		buttonBox.add(s3);
		buttonPanel.add(buttonBox);
		
		viewBox.add(animationViews.get(1));
		viewBox.add(animationViews.get(0));
		viewBox.add(scrollPane);
		viewPanel.add(viewBox);
		

		//Redirect Console Output to Message Console Text View Component in View Panel
		mc.redirectOut(Color.BLACK, null);
		mc.redirectErr(Color.BLACK, null);
		mc.setMessageLines(1000);

		//Adding Components to the frame
		frame.getContentPane().add(BorderLayout.LINE_END, buttonPanel);
		frame.getContentPane().add(BorderLayout.WEST, viewPanel);
		
		animationViews.get(0).start();
		animationViews.get(1).start();
		//animationViews.get(0).openDoors();
		//animationViews.get(1).openDoors();

		//Define button Action Listeners
		//StartStop Button
		startstop.addActionListener( new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				//START OR STOP SCENARIO
				if (running) {
					TextView.printManager("Requesting Executor Service Stop");
				} else {
					TextView.printManager("Requesting Executor Service Start");
				}
				switchRunState();
			}
		});

		//StartStop Button
		s1.addActionListener( new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				//SELECT SCENARIO 1
				if (activeScenario != scenario.S1) {
					textView.setText("");
					TextView.printManager("Scenario 1 Requested");
					activeScenario = scenario.S1;
					scenarioButtonManager(scenario.S1);
				}
			}
		});

		//StartStop Button
		s2.addActionListener( new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				//SELECT SCENARIO 2
				if (activeScenario != scenario.S2) {
					textView.setText("");
					TextView.printManager("Scenario 2 Requested");
					activeScenario = scenario.S2;
					scenarioButtonManager(scenario.S2);
				}
			}
		});

		//StartStop Button
		s3.addActionListener( new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				//SELECT SCENARIO 3
				if (activeScenario != scenario.S3) {
					textView.setText("");
					TextView.printManager("Scenario 3 Requested");
					activeScenario = scenario.S3;
					scenarioButtonManager(scenario.S3);
				}
			}
		});
	}	

	public void startStopButton() {
		if (running) {
			startstop.setIcon(imageAssets.get("stopDefault"));
			startstop.setPressedIcon(imageAssets.get("stopPressed"));

			scenarioButtonManager(scenario.DISABLE);
		} else {
			startstop.setIcon(imageAssets.get("startDefault"));
			startstop.setPressedIcon(imageAssets.get("startPressed"));

			scenarioButtonManager(scenario.ENABLE);
			scenarioButtonManager(activeScenario);
		}
	}

	public static void scenarioButtonManager(scenario s) {
		if (s == scenario.S1) {
			s1.setIcon(imageAssets.get("s1Pressed"));
			s2.setIcon(imageAssets.get("s2Default"));
			s3.setIcon(imageAssets.get("s3Default"));
		} else if (s == scenario.S2) {
			s1.setIcon(imageAssets.get("s1Default"));
			s2.setIcon(imageAssets.get("s2Pressed"));
			s3.setIcon(imageAssets.get("s3Default"));
		} else if (s == scenario.S3) {
			s1.setIcon(imageAssets.get("s1Default"));
			s2.setIcon(imageAssets.get("s2Default"));
			s3.setIcon(imageAssets.get("s3Pressed"));
		} else if (s == scenario.ENABLE) {
			s1.setEnabled(true);
			s2.setEnabled(true);
			s3.setEnabled(true);
		} else if (s == scenario.DISABLE) {
			s1.setEnabled(false);
			s2.setEnabled(false);
			s3.setEnabled(false);
		} else {
			TextView.printError("Button Manager", "Invalid Active Scenario Detected.");
		}
	}

	public scenario getScenario() { return activeScenario; }

	public void setRunState(boolean r) { running = r; }
	public boolean getRunState() { return running; }
	public void switchRunState() { running = !running; }

	@Override
	public void update() {
		startStopButton();
	}

	@Override
	public void setSubject(Subject subject) {
		this.subject = subject;
	}

}

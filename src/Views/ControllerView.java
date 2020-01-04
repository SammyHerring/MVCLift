package Views;

import java.awt.*;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;

import CustomDataTypes.MessageConsole;

// ACTS as GUI Controller for Lift Simulation

public class ControllerView extends Frame {

	private JFrame frame = new JFrame("Lift Simulation Controller");

	public ControllerView() {

		initComponents();

		//Creating the Frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1700, 1000);
		frame.setResizable(false);
		frame.setVisible(true);
	}

	public void initComponents() {		
		JPanel buttonPanel = new JPanel();
		JPanel viewPanel = new JPanel();

		Box buttonBox = Box.createVerticalBox();

		JButton startstop = new JButton();
		JButton s1 = new JButton();
		JButton s2 = new JButton();
		JButton s3 = new JButton();
		
		startstop.setSize(100, 40);
		s1.setSize(100, 40);
		s2.setSize(100, 40);
		s3.setSize(100, 40);
		
		try {			
			Image startDefault = ImageIO.read(getClass()
					.getClassLoader()
					.getResource("ImgAssets/MVCLift_ImgAsset_STARTDefault.png"));
			
			Image startPressed = ImageIO.read(getClass()
					.getClassLoader()
					.getResource("ImgAssets/MVCLift_ImgAsset_STARTPressed.png"));
			
			Image stopDefault = ImageIO.read(getClass()
					.getClassLoader()
					.getResource("ImgAssets/MVCLift_ImgAsset_STOPDefault.png"));
			
			Image stopPressed = ImageIO.read(getClass()
					.getClassLoader()
					.getResource("ImgAssets/MVCLift_ImgAsset_STOPPressed.png"));
			
			Image s1Default = ImageIO.read(getClass()
					.getClassLoader()
					.getResource("ImgAssets/MVCLift_ImgAsset_S1Default.png"));
			
			Image s1Pressed = ImageIO.read(getClass()
					.getClassLoader()
					.getResource("ImgAssets/MVCLift_ImgAsset_S1Pressed.png"));
			
			Image s2Default = ImageIO.read(getClass()
					.getClassLoader()
					.getResource("ImgAssets/MVCLift_ImgAsset_S2Default.png"));
			
			Image s2Pressed = ImageIO.read(getClass()
					.getClassLoader()
					.getResource("ImgAssets/MVCLift_ImgAsset_S2Pressed.png"));
			
			Image s3Default  = ImageIO.read(getClass()
					.getClassLoader()
					.getResource("ImgAssets/MVCLift_ImgAsset_S3Default.png"));
			
			Image s3Pressed = ImageIO.read(getClass()
					.getClassLoader()
					.getResource("ImgAssets/MVCLift_ImgAsset_S3Pressed.png"));

			startstop.setIcon(new ImageIcon(startDefault));
			startstop.setPressedIcon(new ImageIcon(startPressed));
			
			s1.setIcon(new ImageIcon(s1Default));
			s1.setPressedIcon(new ImageIcon(s1Pressed));
//			
			s2.setIcon(new ImageIcon(s2Default));
			s2.setPressedIcon(new ImageIcon(s2Pressed));
			
			s3.setIcon(new ImageIcon(s3Default));
			s3.setPressedIcon(new ImageIcon(s3Pressed));
			
		} catch (Exception ex) {
			
			TextView.printError("Icon Loader Error", ex.toString());
			
		}

		buttonBox.add(startstop);
		buttonBox.add(s1);
		buttonBox.add(s2);
		buttonBox.add(s3);

		buttonPanel.add(buttonBox);

		JPanel animationView = new JPanel();
		JTextArea textView = new JTextArea(50, 120);

		//viewPanel.add(animationView);
		viewPanel.setSize(800, 400);
		viewPanel.add(new JScrollPane(textView, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS));

		MessageConsole mc = new MessageConsole(textView);
		mc.redirectOut(Color.BLACK, null);
		mc.redirectErr(Color.RED, null);
		mc.setMessageLines(100);

		//Adding Components to the frame
		frame.getContentPane().add(BorderLayout.LINE_END, buttonPanel);
		frame.getContentPane().add(BorderLayout.WEST, viewPanel);

	}
}

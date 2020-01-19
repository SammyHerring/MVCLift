package Views;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class PersonAnimationView extends JPanel implements ActionListener {

	//ACTS as Live Animation View of Passengers to be layered over Animation View JPanel in LayeredPanel

	private static final long serialVersionUID = 1L;

	public int floor;

	private Timer animator;
	
	public enum frameColour {
		BLUE, GREEN, ORANGE, PURPLE, YELLOW;
		
		//Method to randomly access a frame colour enum upon person frame generation
	    public static frameColour getRandomFrameColor() {
	        Random random = new Random();
	        return values()[random.nextInt(values().length)];
	    }
	}
	
	private List<ImageIcon> blueFrames = new ArrayList<ImageIcon>();
	private List<ImageIcon> greenFrames = new ArrayList<ImageIcon>();
	private List<ImageIcon> orangeFrames = new ArrayList<ImageIcon>();
	private List<ImageIcon> purpleFrames = new ArrayList<ImageIcon>();
	private List<ImageIcon> yellowFrames = new ArrayList<ImageIcon>();
	private List<ImageIcon> activeFrames;
	
	public Map<frameColour, List<ImageIcon>> frames;

	private int delay = 25, totalFrames = 24, currentFrame = 0;

	public PersonAnimationView(int floor) {

		this.floor = floor;
		
		frames = new HashMap<frameColour, List<ImageIcon>>();;

		Dimension size = new Dimension(150, 300);
		this.setPreferredSize(size);
		
		for (int i = 0; i < totalFrames; i++) {
			String frame = String.format ("%02d", i);
			try {
				blueFrames.add(new ImageIcon(ImageIO.read(getClass()
						.getClassLoader()
						.getResource("ImgAssets/PersonFrames/Blue/MVCLift_ImgAsset_PersonAnimation_BLUE" + frame + ".png"))));

				greenFrames.add(new ImageIcon(ImageIO.read(getClass()
						.getClassLoader()
						.getResource("ImgAssets/PersonFrames/Green/MVCLift_ImgAsset_PersonAnimation_GREEN" + frame + ".png"))));
				
				orangeFrames.add(new ImageIcon(ImageIO.read(getClass()
						.getClassLoader()
						.getResource("ImgAssets/PersonFrames/Orange/MVCLift_ImgAsset_PersonAnimation_ORANGE" + frame + ".png"))));
				
				purpleFrames.add(new ImageIcon(ImageIO.read(getClass()
						.getClassLoader()
						.getResource("ImgAssets/PersonFrames/Purple/MVCLift_ImgAsset_PersonAnimation_PURPLE" + frame + ".png"))));
				
				yellowFrames.add(new ImageIcon(ImageIO.read(getClass()
						.getClassLoader()
						.getResource("ImgAssets/PersonFrames/Yellow/MVCLift_ImgAsset_PersonAnimation_YELLOW" + frame + ".png"))));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		frames.put(frameColour.BLUE, blueFrames);
		frames.put(frameColour.GREEN, greenFrames);
		frames.put(frameColour.ORANGE, orangeFrames);
		frames.put(frameColour.PURPLE, purpleFrames);
		frames.put(frameColour.YELLOW, yellowFrames);
		
		try {
			activeFrames = frames.get(frameColour.getRandomFrameColor());
		} catch (Exception ex) {
			TextView.printError("Person Animation", "Active Frames Loading Related Error");
		}

		animator = new Timer(delay, this);
	}

	public void start() {
		animator.start();
	}

	public void stop() {
		animator.stop();
	}

	public void paintComponent(Graphics g) {
		super.paintComponents(g);

		currentFrame++;

		g.drawImage(activeFrames.get(currentFrame).getImage(), 0, 0, getWidth(), getHeight(), null);
	}

	public void actionPerformed(ActionEvent e) {
		repaint();
	}

}

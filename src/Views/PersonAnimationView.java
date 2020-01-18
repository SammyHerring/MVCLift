package Views;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;


public class PersonAnimationView extends JPanel implements ActionListener {

	//ACTS as Live Animation View of Lift Status + Passengers

	private static final long serialVersionUID = 1L;

	public int floor;

	private Timer animator;

	public ImageIcon blueFrames[];
	public ImageIcon greenFrames[];
	public ImageIcon orangeFrames[];
	public ImageIcon purpleFrames[];
	public ImageIcon yellowFrames[];
	private ImageIcon activeFrames[];

	private int delay = 25, totalFrames = 24, currentFrame = 0;

	public PersonAnimationView(int floor) {

		this.floor = floor;

		Dimension size = new Dimension(150, 300);
		this.setPreferredSize(size);

		activeFrames = new ImageIcon[totalFrames];
		
		for (int i = 0; i < totalFrames; i++) {
			String frame = String.format ("%02d", i);
			try {
				blueFrames[i] = new ImageIcon(ImageIO.read(getClass()
						.getClassLoader()
						.getResource("ImgAssets/PersonFrames/Blue/MVCLift_ImgAsset_PersonAnimation_BLUE" + frame + ".png")));

				greenFrames[i] = new ImageIcon(ImageIO.read(getClass()
						.getClassLoader()
						.getResource("ImgAssets/PersonFrames/Green/MVCLift_ImgAsset_PersonAnimation_GREEN" + frame + ".png")));
				
				orangeFrames[i] = new ImageIcon(ImageIO.read(getClass()
						.getClassLoader()
						.getResource("ImgAssets/PersonFrames/Orange/MVCLift_ImgAsset_PersonAnimation_ORANGE" + frame + ".png")));
				
				purpleFrames[i] = new ImageIcon(ImageIO.read(getClass()
						.getClassLoader()
						.getResource("ImgAssets/PersonFrames/Purple/MVCLift_ImgAsset_PersonAnimation_PURPLE" + frame + ".png")));
				
				yellowFrames[i] = new ImageIcon(ImageIO.read(getClass()
						.getClassLoader()
						.getResource("ImgAssets/PersonFrames/Yellow/MVCLift_ImgAsset_PersonAnimation_YELLOW" + frame + ".png")));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		activeFrames = blueFrames;
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

		g.drawImage(activeFrames[currentFrame].getImage(), 0, 0, 1500, getHeight(), null);

	}

	public void actionPerformed(ActionEvent e) {
		repaint();
	}

	public void switchActiveFrames(boolean buttonActive) {
		if (buttonActive) {
			activeFrames = buttonFrames;
		} else {
			activeFrames = noButtonFrames;
		}
	}
}

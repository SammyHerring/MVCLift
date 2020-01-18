package Views;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;


public class AnimationView extends JPanel implements ActionListener {

	//ACTS as Live Animation View of Lift Status + Passengers

	private static final long serialVersionUID = 1L;

	public int floor;

	public enum liftState { OPEN, CLOSED, OPENING, CLOSING }
	public liftState activeState = liftState.CLOSING; //Default scenario selection
	public liftState previous = liftState.OPEN; //Default scenario selection


	private Timer animator;
	private boolean inProgress; //Used to identify if change of state is in progress

	public ImageIcon buttonFrames[];
	private ImageIcon noButtonFrames[];
	private ImageIcon activeFrames[];

	private int delay = 50, totalFrames = 17, currentFrame = 0;

	public AnimationView(int floor) {

		this.floor = floor;

		Dimension size = new Dimension(1400, 466);
		this.setPreferredSize(size);

		if (floor == 0) {
			buttonFrames = new ImageIcon[totalFrames];
			noButtonFrames = new ImageIcon[totalFrames];
			for (int i = 0; i < totalFrames; i++) {
				String frame = String.format ("%02d", i);
				try {
					buttonFrames[i] = new ImageIcon(ImageIO.read(getClass()
							.getClassLoader()
							.getResource("ImgAssets/LiftFrames/Button0/MVCLift_ImgAsset_Scene" + frame + ".jpg")));

					noButtonFrames[i] = new ImageIcon(ImageIO.read(getClass()
							.getClassLoader()
							.getResource("ImgAssets/LiftFrames/NoButton0/MVCLift_ImgAsset_Scene" + frame + ".jpg")));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else if (floor == 1) {
			buttonFrames = new ImageIcon[totalFrames];
			noButtonFrames = new ImageIcon[totalFrames];
			for (int i = 0; i < totalFrames; i++) {
				String frame = String.format ("%02d", i);
				try {
					buttonFrames[i] = new ImageIcon(ImageIO.read(getClass()
							.getClassLoader()
							.getResource("ImgAssets/LiftFrames/Button1/MVCLift_ImgAsset_Scene" + frame + ".jpg")));

					noButtonFrames[i] = new ImageIcon(ImageIO.read(getClass()
							.getClassLoader()
							.getResource("ImgAssets/LiftFrames/NoButton1/MVCLift_ImgAsset_Scene" + frame + ".jpg")));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			TextView.printError("Animation Floor", "Invalid Floor Provided at Initialisation");
		}

		activeFrames = noButtonFrames;
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

		if (activeState == liftState.OPEN) {

			currentFrame = 16;

		} else if (activeState == liftState.CLOSED) {

			currentFrame = 0;

		} else if (activeState == liftState.OPENING ) {
			if (currentFrame >= totalFrames-1) {
				currentFrame = 16;
				activeState = liftState.OPEN;
				inProgress = false;
			} else {
				currentFrame++;
			}

		} else if (activeState == liftState.CLOSING) {

			if (currentFrame > 0) {
				currentFrame--;
			} else if (currentFrame == 0) {
				activeState = liftState.CLOSED;
				inProgress = false;
			}


		} else {
			TextView.printError("Lift " + floor + " Animation State", "Invalid animation state detected.");
		}

		g.drawImage(activeFrames[currentFrame].getImage(), 0, 0, 1500, getHeight(), null);

	}

	public void actionPerformed(ActionEvent e) {
		repaint();
	}

	public void switchActiveFrames() {
		if (activeFrames == buttonFrames) {
			activeFrames = noButtonFrames;
		} else {
			activeFrames = buttonFrames;
		}
	}
	
	public void switchActiveFrames(boolean buttonActive) {
		if (buttonActive) {
			activeFrames = buttonFrames;
		} else {
			activeFrames = noButtonFrames;
		}
	}

	public void openDoors() {
		activeState = liftState.OPENING;
	}
	
	public void closeDoors() {
		activeState = liftState.CLOSING;
	}


}

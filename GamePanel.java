import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class GamePanel extends JPanel implements KeyListener{
	private int x, i;
	private Image ball, hoop;
	private int velX, velY;
	private int ballX, ballY;
	public int lives, makes, misses;
	private boolean MOVING, ENDOFGAME, RESTART; 
	


	public GamePanel(Image ball, Image hoop){
		requestFocus();	
		this.ball = ball;
		this.hoop = hoop;	
		ballX = 0; ballY = 400;
		velX = 5; velY = 10;
		MOVING = true; 
		makes = 0; misses = 0;
		lives = 5;
		setBackground(Color.WHITE);
		ENDOFGAME = false; RESTART = false;
		
		setFocusable(true);
    	addKeyListener(this);
		setVisible(true);
		requestFocus();

	}
	@Override
	protected void paintComponent(Graphics g) {
		requestFocus();	
		super.paintComponent(g);
		for(int i = 0; i < lives; i++){
			g.drawImage(ball, 465 - (i*25), 45, 25, 25, null);
		}
		
		//draws the strings at the end of the game
		if(ENDOFGAME){
			g.setFont(new Font("Times New Roman", Font.BOLD, 40));
			g.drawString("Makes: " + makes, 173, 250);
			g.setFont(new Font("serif", Font.ITALIC, 21));     
			g.drawString("spacebar to restart" , 174, 275);
		}
		g.setFont(new Font("serif", Font.PLAIN, 25)); //counter for makes
		g.drawString("" + makes, 10, 27);
		g.drawImage(hoop, 190, 0 , 125, 125, null);  //draws the hoop image
		g.drawImage(ball, ballX, ballY, 50, 50, null); // draws the basketball
	}
	 
	public void saveGameState(String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
          
            out.writeInt(makes);
            out.writeInt(misses);
            out.writeInt(lives);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadGameState(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            makes = in.readInt();
            misses = in.readInt();
            lives = in.readInt();
            repaint();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	public boolean checkBall(){
		if(ballX >= 200 && ballX <= 255  && ballY < 80)
			return true;
			
		return false;	
		 
	}
	/*public void run() {
		Thread gameThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					if (MOVING) {
						if (ballY < 0) {
							MOVING = false;
							ballY = 400;
							lives--;
							misses++;
						} else {
							ballY -= velY;
						}
					}
					repaint();
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		gameThread.start();
	}*/
	public void playSound(int x){
		
		if(x == 1){
			try {
				Clip clip = AudioSystem.getClip();
				clip.open(AudioSystem.getAudioInputStream(new File("Eminem.wav").getAbsoluteFile()));
				clip.start();
			} catch(Exception ex) {
				ex.printStackTrace();
			}}
			else{
				try {
					Clip clip = AudioSystem.getClip();
					clip.open(AudioSystem.getAudioInputStream(new File("Eminem.wav").getAbsoluteFile()));
					clip.start();
				} catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		
	
	public void run() {
		
		playSound(1);//123
		Thread gameThread = new Thread(new Runnable() {
		
			@Override
			
			public void run() {
			
				
				
				System.out.println("Key pressed: " );
				for(i = 0; i <= (450/velX) * 2; i = i + 1){
				System.out.println(lives);
				System.out.println(makes);
				saveGameState("NBA_FILE.ser");	
					//basketball has been shot
					if(!MOVING){ 
						
						for(int x = 0; x <= 415/velY; x++){
							
							//basketball is missed
							
							if(x == 415/velY){
								
								System.out.println("вфыфвф");
								ballY = 415;
								lives--;
								misses++;
								MOVING = !MOVING;
								break;
							}
							ballY = ballY - velY;
							repaint();
							try{
								Thread.sleep(20);
							} catch(InterruptedException e){
								e.printStackTrace();
							}
							//check for a made basket
							if(checkBall()){
								
								System.out.println("проол");
								MOVING = !MOVING;
								
								makes++;
								try{
									Thread.sleep(50);
								} catch(InterruptedException e){
									e.printStackTrace();
								}
								velX = velX + 1;
								velY = velY + 1;
								i = 0;
								ballX = 0;
								ballY = 400;
								break;
							}
						}
					}
					if(i == (450/velX * 2))
						 // prevents ball for moving alll the way past the rim
						i = 0;
						System.out.println("3234");
					if(i < 450/velX){
						
						System.out.println("221");
						ballX = ballX + velX;
						repaint();
						try{
							Thread.sleep(20);
						} catch(InterruptedException e){
							e.printStackTrace();
						}
					}
						
					else{
							
						System.out.println("132");
						ballX = ballX - velX;
						repaint();
						try{
							Thread.sleep(20);
						} catch(InterruptedException e){
							e.printStackTrace();
						}
					}	
					
					if(lives == 0 && !ENDOFGAME){ //"holds" the loop temporarily so waiting for the player to restart the game
						requestFocus();	
						System.out.println("222");
						ballX = -10000; //to make it seem like the ball dissapears
						
						ENDOFGAME = true;
						 //prevents the buzzer for sounding infinetly until the spacebar is pressed
					}	
					
				}
				
			}
			
		});
		gameThread.start();
	}

	public void setImages(Image ball, Image hoop) {
		this.ball = ball;
		this.hoop = hoop;
		repaint();
	}


	@Override
public void keyPressed(KeyEvent e) {
	
    System.out.println("111");
    if (ENDOFGAME) {
        ballX = 0;
        ballY = 400;
        lives = 5;
        i = 0;
        velX = 5;
        velY = 10;
        makes = 0;
        misses = 0;
        ENDOFGAME = false;
        repaint();
        try {
            Thread.sleep(20);
        } catch (final InterruptedException ex) {
            ex.printStackTrace();
        }
        return;
		
    }
    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
        MOVING = !MOVING;
    
	if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
		closeGame();
	}}
}	
	
	public void closeGame() {
		System.exit(0);
	}
	@Override
	public void keyReleased(KeyEvent e) {
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		
	} 
	
}
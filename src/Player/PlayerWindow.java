package Player;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JTextPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.nio.file.Paths;
import java.awt.Toolkit;
import java.awt.Font;
import jaco.mp3.player.MP3Player;
//import jaco.mp3.player.MP3Player;

public class PlayerWindow extends JFrame {

	private JPanel contentPane;
	//Define MP3Player Class from jaco
	MP3Player player;
	//Define File for Song
	File songFile;
	//Define Current Direcotry home.user will open file chooser
	String currentDirectory = "home.user";
	String currentPath;
	//For Images of the Song
	String imagePath;
	//Repeat of and check
	Boolean repeat = false;
	
	//Check if Window colapsed
	Boolean windowCollapsed = false;
	
	//Mouse x,y seeds, (do I need them?)
	int xMouse, yMouse;

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PlayerWindow frame = new PlayerWindow();
					
					frame.setTitle("Musokify");
					frame.setSize((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()-50), (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()-150)); //get screensize and set it up to it
					frame.setVisible(true);
					frame.setLocationRelativeTo(null); //make it to open in the middle

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PlayerWindow() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(PlayerWindow.class.getResource("/Images/LogoMakr-2AMGq5.png"))); //logomakr.com/2AMGq5
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 762, 483);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		
		JPanel panel = new JPanel();
		panel.setBorder(null);
		panel.setBackground(Color.LIGHT_GRAY);
		contentPane.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblLoop = new JLabel("");
		lblLoop.setIcon(new ImageIcon(PlayerWindow.class.getResource("/Images/RepeatIcon.png")));
		panel.add(lblLoop);
		
		JLabel lblPause = new JLabel("");
		lblPause.setIcon(new ImageIcon(PlayerWindow.class.getResource("/Images/StopIcon.png")));
		panel.add(lblPause);
		
		JLabel lblPlay = new JLabel("");
		lblPlay.setBackground(Color.BLACK);
		lblPlay.setForeground(Color.BLACK);
		lblPlay.setIcon(new ImageIcon(PlayerWindow.class.getResource("/Images/PlayIcon.png")));
		panel.add(lblPlay);
		
		JLabel lblOpen = new JLabel("");
		lblOpen.setIcon(new ImageIcon(PlayerWindow.class.getResource("/Images/FolderIcon.png")));
		panel.add(lblOpen);
		
		JLabel lblShuffle = new JLabel("");
		lblShuffle.setIcon(new ImageIcon(PlayerWindow.class.getResource("/Images/ShuffleIcon.png")));
		panel.add(lblShuffle);
		
		JLabel lblVolDown = new JLabel("");
		lblVolDown.setIcon(new ImageIcon(PlayerWindow.class.getResource("/Images/VolumeDown.png")));
		panel.add(lblVolDown);
		
		JLabel lblVolUp = new JLabel("");
		lblVolUp.setIcon(new ImageIcon(PlayerWindow.class.getResource("/Images/VolumeUp.png")));
		panel.add(lblVolUp);
		
		JLabel lblVolMute = new JLabel("");
		lblVolMute.setIcon(new ImageIcon(PlayerWindow.class.getResource("/Images/VolumeMute.png")));
		panel.add(lblVolMute);
		
		
		JLabel lblSongName = new JLabel("");
		lblSongName.setFont(new Font("Tahoma", Font.PLAIN, 21));
		lblSongName.setForeground(Color.WHITE);
		lblSongName.setBackground(Color.LIGHT_GRAY);
		contentPane.add(lblSongName, BorderLayout.NORTH);
		
		// Probleme mit diesem Abschnitt, möglich, dass die Quelle angegeben werden muss
		songFile = new File("\\MusokV404\\src\\SoundTest\\0012766.mp3");
		// get File name
		String filename = songFile.getName();
		//set song name
		lblSongName.setText(filename);
		
		player = mp3Player();
		//song zu einer Playlist hinzufügen
		player.addToPlayList(songFile);
		//get img Path in strings
		currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
		imagePath = "\\images";
		
		
		//ActionListener
		
		lblLoop.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
			}
		});
		
		lblPause.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				player.stop();
			}
		});
		
		lblPlay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				player.play();
			}
		});
		
		lblOpen.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e){
			}
		});
		
		lblShuffle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		
		lblVolDown.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

			}
		});
		
		lblVolUp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		
		lblVolMute.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		
	}
	
	private MP3Player mp3Player(){
		MP3Player mp3Player = new MP3Player();
		return mp3Player;
	}
	
	//Volume Down Method
	private void volumeDown(Double valueToPlusMinus) {
		//Get Mixerinformation form Audiosystem
		Mixer.Info[] mixers = AudioSystem.getMixerInfo();
		//list all mixers
		for(Mixer.Info mixerInfo : mixers) {
			//get Mixer
			Mixer mixer = AudioSystem.getMixer(mixerInfo);
			//Target line get
			Line.Info[] lineInfos = mixer.getTargetLineInfo();
			//List Lines
			for(Line.Info lineInfo : lineInfos) {
				//Null line
				Line line = null;
				//Bool as opened
				boolean opened = true;
				
				//opening line
				try {
					line = mixer.getLine(lineInfo);
					opened = line.isOpen() || line instanceof Clip;
					//check line not open
					if(!opened) {
						line.open();
					}
					//Flat control
					FloatControl volControl = (FloatControl) line.getControl(FloatControl.Type.VOLUME);
					//Get current Volume
					float currentVolume = volControl.getValue();
					//Make a temp double and store valuePlusMinus
					Double volumeToCut = valueToPlusMinus;
					//float and calc the addition/subtraction
					float changedCalc = (float) ((float)currentVolume-(double)volumeToCut);
					//set changed Value
					volControl.setValue(changedCalc);
					
				} catch (LineUnavailableException lineException) {
				} catch (IllegalArgumentException illException)  {
				} finally {
					if(line != null && !opened) {
						line.close();
					}
				}
				
			}
		}
	}
	
	//Volume Up Method
	private void volumeUp(Double valueToPlusMinus) {
		//Get Mixerinformation form Audiosystem
		Mixer.Info[] mixers = AudioSystem.getMixerInfo();
		//list all mixers
		for(Mixer.Info mixerInfo : mixers) {
			//get Mixer
			Mixer mixer = AudioSystem.getMixer(mixerInfo);
			//Target line get
			Line.Info[] lineInfos = mixer.getTargetLineInfo();
			//List Lines
			for(Line.Info lineInfo : lineInfos) {
				//Null line
				Line line = null;
				//Bool as opened
				boolean opened = true;
				
				//opening line
				try {
					line = mixer.getLine(lineInfo);
					opened = line.isOpen() || line instanceof Clip;
					//check line not open
					if(!opened) {
						line.open();
					}
					//Flat control
					FloatControl volControl = (FloatControl) line.getControl(FloatControl.Type.VOLUME);
					//Get current Volume
					float currentVolume = volControl.getValue();
					//Make a temp double and store valuePlusMinus
					Double volumeToCut = valueToPlusMinus;
					//float and calc the addition/subtraction
					float changedCalc = (float) ((float)currentVolume-(double)volumeToCut);
					//set changed Value
					volControl.setValue(changedCalc);
					
				} catch (LineUnavailableException lineException) {
				} catch (IllegalArgumentException illException)  {
				} finally {
					if(line != null && !opened) {
						line.close();
					}
				}
				
			}
		}
	}
	
	//Volume control Method
	private void volumeControl(Double valueToPlusMinus) {
		//Get Mixerinformation form Audiosystem
		Mixer.Info[] mixers = AudioSystem.getMixerInfo();
		//list all mixers
		for(Mixer.Info mixerInfo : mixers) {
			//get Mixer
			Mixer mixer = AudioSystem.getMixer(mixerInfo);
			//Target line get
			Line.Info[] lineInfos = mixer.getTargetLineInfo();
			//List Lines
			for(Line.Info lineInfo : lineInfos) {
				//Null line
				Line line = null;
				//Bool as opened
				boolean opened = true;
				
				//opening line
				try {
					line = mixer.getLine(lineInfo);
					opened = line.isOpen() || line instanceof Clip;
					//check line not open
					if(!opened) {
						line.open();
					}
					//Flat control
					FloatControl volControl = (FloatControl) line.getControl(FloatControl.Type.VOLUME);
					//Get current Volume
					float currentVolume = volControl.getValue();
					//Make a temp double and store valuePlusMinus
					Double volumeToCut = valueToPlusMinus;
					//float and calc the addition/subtraction
					float changedCalc = (float) ((double)volumeToCut);
					//set changed Value
					volControl.setValue(changedCalc);
					
				} catch (LineUnavailableException lineException) {
				} catch (IllegalArgumentException illException)  {
				} finally {
					if(line != null && !opened) {
						line.close();
					}
				}
				
			}
		}
	}
	
	
}

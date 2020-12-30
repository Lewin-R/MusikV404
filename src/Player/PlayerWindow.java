package Player;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

import java.awt.Color;
import java.awt.Composite;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.nio.file.Paths;
import java.awt.Toolkit;
import java.awt.Font;
import jaco.mp3.player.MP3Player;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;
import javax.swing.JLayeredPane;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
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
	
	Boolean paused = false;
	
	Boolean clicked = false;
	
	Boolean isMuted = false;
	
	
	
	//Check if Window colapsed
	Boolean windowCollapsed = false;
	
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
		panel.setBackground(Color.DARK_GRAY);
		contentPane.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblLoop = new JLabel("");
		lblLoop.setToolTipText("Repeat");
		lblLoop.setIcon(new ImageIcon(PlayerWindow.class.getResource("/Images/RepeatIcon.png")));
		panel.add(lblLoop);
		
		JLabel lblPauseAndPlay = new JLabel("");
		lblPauseAndPlay.setToolTipText("Play / Pause");
		lblPauseAndPlay.setIcon(new ImageIcon(PlayerWindow.class.getResource("/Images/PauseIcon.png")));
		panel.add(lblPauseAndPlay);
		
		JLabel lblOpen = new JLabel("");
		lblOpen.setToolTipText("Open File");
		lblOpen.setIcon(new ImageIcon(PlayerWindow.class.getResource("/Images/FolderIcon.png")));
		panel.add(lblOpen);
		
		JLabel lblShuffle = new JLabel("");
		lblShuffle.setToolTipText("Shuffle");
		lblShuffle.setIcon(new ImageIcon(PlayerWindow.class.getResource("/Images/ShuffleIcon.png")));
		panel.add(lblShuffle);
		
		JLabel lblVolControlAndMute = new JLabel("");
		lblVolControlAndMute.setToolTipText("Mute / Unmute");
		lblVolControlAndMute.setIcon(new ImageIcon(PlayerWindow.class.getResource("/Images/VolumeUp.png")));
		panel.add(lblVolControlAndMute);
		
		// Probleme mit diesem Abschnitt, möglich, dass die Quelle angegeben werden muss
		songFile = new File("");
		// get File name
		String filename = songFile.getName();
		
		
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		
		JLabel lblSongName = new JLabel("");
		lblSongName.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblSongName, BorderLayout.SOUTH);
		lblSongName.setFont(new Font("Tahoma", Font.PLAIN, 21));
		lblSongName.setForeground(Color.BLACK);
		lblSongName.setBackground(Color.DARK_GRAY);
		//set song name
		lblSongName.setText(filename);
		
		player = mp3Player();
		//song zu einer Playlist hinzufügen
		player.addToPlayList(songFile);
		//get img Path in strings
		currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
		imagePath = "\\images";
		
		
		JSlider Volume = new JSlider(JSlider.HORIZONTAL);
		Volume.setToolTipText("Volume");
		Volume.setBackground(Color.DARK_GRAY);
		Volume.setForeground(Color.LIGHT_GRAY);
		panel.add(Volume);
		
		
		//ActionListener
		
		lblLoop.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(repeat == false) {
					repeat = true;
					player.setRepeat(repeat);
					
					String image = currentPath+imagePath+"\\repeat_enabled.png";
				}
				else if(repeat == true) {
					repeat = false;
					player.setRepeat(repeat);
					
					String image = currentPath+imagePath+"\\repeat_disabled.png";
				}
			}
		});
		
		lblPauseAndPlay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				
				if(paused == false) {
					player.pause();
					paused = true;
					lblPauseAndPlay.setIcon(new ImageIcon(PlayerWindow.class.getResource("/Images/PlayIcon.png")));
				}
				else {
					player.play();
					paused = false;
					lblPauseAndPlay.setIcon(new ImageIcon(PlayerWindow.class.getResource("/Images/PauseIcon.png")));
				}
			}
		});
		
		lblOpen.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e){
				JFileChooser openFileChooser = new JFileChooser(currentDirectory);
				openFileChooser.setFileFilter(new FileTypeFilter(".mp3", "Open MP3 Files"));
				int result = openFileChooser.showOpenDialog(null);
				try {
				if(result == openFileChooser.APPROVE_OPTION) {
					songFile = openFileChooser.getSelectedFile();
					player.addToPlayList(songFile);
					player.skipForward();
					currentDirectory = songFile.getAbsolutePath();
					lblSongName.setText(songFile.getName());
					}
				} catch (Exception fce) {
	                JOptionPane.showMessageDialog(null, fce.getMessage(), null, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		lblShuffle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		
		lblVolControlAndMute.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if(clicked == false) {
					volumeControl(0.0);
					clicked = true;
					lblVolControlAndMute.setIcon(new ImageIcon(PlayerWindow.class.getResource("/Images/VolumeMute.png")));
				}
				else {
					volumeControl(0.5);
					lblVolControlAndMute.setIcon(new ImageIcon(PlayerWindow.class.getResource("/Images/VolumeUp.png")));
					clicked = false;
				}
			}
		});
		
		
	
//	lblVolControlAndMute.addMouseListener(new MouseAdapter() {
//		
//		@Override
//		public void mouseEntered(MouseEvent arg0) {
//			System.out.println("entered");
//			layeredPane.add(Volume);
//			
//		}
//		@Override
//		public void mouseExited(MouseEvent e) {
//			System.out.println("exited");
//			layeredPane.remove(Volume);
//			}
//	
//		});
//	}
	
	Volume.addChangeListener(new ChangeListener() {
		public void stateChanged(ChangeEvent e) {
			
			JSlider source = (JSlider)e.getSource();
		    if (!source.getValueIsAdjusting()) {
		        double volume = (double)source.getValue()/100;
		        if (volume == 0.0) {
		        	volumeControl(0.0);
		        	isMuted = true;
		        	lblVolControlAndMute.setIcon(new ImageIcon(PlayerWindow.class.getResource("/Images/VolumeMute.png")));
		        } else {
		        	
		        	if(isMuted = true && volume >= 0.1) {
		        		System.out.println(volume);
		        		volumeControl(volume);
		        		isMuted = false;
		        		lblVolControlAndMute.setIcon(new ImageIcon(PlayerWindow.class.getResource("/Images/VolumeUp.png")));
		        	}
		        	volumeControl(volume);
		        	System.out.println(volume);
		       }
		    }
		}
	});
	
	}

	
	private MP3Player mp3Player(){
		MP3Player mp3Player = new MP3Player();
		return mp3Player;
	}
	
	//Variables for volume
	
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
					float changedCalc = (float) ((float)currentVolume+(double)volumeToCut);
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
	
//	public int getDurationSeconds() {
//		try {
//			AudioInputStream audioStream = AudioSystem.getAudioInputStream(file)
//		}
//	}
	
	
}

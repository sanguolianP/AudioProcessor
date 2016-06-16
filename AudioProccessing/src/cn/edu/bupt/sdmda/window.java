package cn.edu.bupt.sdmda;


import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


import javax.swing.*;


public class window extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private AudioPro opened;
	private AudioPro mixed;
	private AudioPro processed;
	private ArrayList<AudioPro> proList = new ArrayList<AudioPro>();
	private int index;
	
	private JButton openButton;
	private JButton resampleButton;
	private JButton olaButton;
	private JButton volButton;
	private JButton filterButton;
	private JButton mixButton;
	private JButton reverseButton;
	private JButton saveButton;
	private JButton stopButton;
	private JButton resetButton;
	private JButton back;
	private JButton front;
	
	private JButton openPlayButton;
	private JButton resamplePlayButton;
	private JButton olaPlayButton;
	private JButton volPlayButton;
	private JButton filterPlayButton;
	private JButton mixPlayButton;
	private JButton reversePlayButton;
	
	private JTextField resampleRatio;
	private JTextField olaRatio;
	private JTextField volRatio;
	private JTextField filterRatio;
	
	private JLabel openPath;
	private JLabel resampleTip;
	private JLabel olaTip;
	private JLabel volTip;
	private JLabel filterTip;
	private JLabel mixTip;
	private JLabel reverseTip;
	
	private JTextField blank1;
	private JTextField blank2;
	private JTextField blank3;
	
	private JScrollPane sp;
	private JTextArea ta;
	

	
	public window()
	{
		initUI();
		initEventListener();
	}

	
	
	private void initUI() 
	{
		JPanel openPanel = initOpenPanel();
		JPanel reamplePanel = initResamplePanel();
		JPanel olaPanel = initOlaPanel();
		JPanel volPanel = initVolPanel();
		JPanel filterPanel = initFilterPanel();
		JPanel mixPanel = initMixPanel();
		JPanel ReversePanel = initReversePanel();
		JPanel savePanel = initSavePanel();
		JPanel logPanel = initLogPanel();
		
        JPanel panel  = (JPanel) getContentPane();
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		panel.add(openPanel);
		panel.add(reamplePanel);
		panel.add(olaPanel);
		panel.add(volPanel);
		panel.add(filterPanel);
		panel.add(mixPanel);
		panel.add(ReversePanel);
		panel.add(savePanel);
		panel.add(logPanel);
		
		
		setTitle("Audio Process PBH");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
	}
	
	
	

	private JPanel initOpenPanel() {
		
		openButton = new JButton();
		openButton.setText("Open");
		openButton.setPreferredSize(new Dimension(100,30));
		
		openPlayButton = new JButton();
		openPlayButton.setText("Play");
		openPlayButton.setPreferredSize(new Dimension(100,30));
		
		blank1 = new JTextField();
		blank1.setEditable(false);
		blank1.setText("FilePath:");
		blank1.setPreferredSize(new Dimension(100,30));
		
		openPath = new JLabel();
		openPath.setText(" ");
		openPath.setPreferredSize(new Dimension(200,30));
		

		JPanel p1 = new JPanel();
		p1.setLayout(new BoxLayout(p1, BoxLayout.X_AXIS));
		p1.add(openButton);
		p1.add(openPlayButton);
		p1.add(blank1);
		p1.add(openPath);
	
		
		return p1;
	}



	private JPanel initResamplePanel() {
		
		resampleButton = new JButton();
		resampleButton.setText("Resample");
		resampleButton.setPreferredSize(new Dimension(100,30));
		
		resamplePlayButton = new JButton();
		resamplePlayButton.setText("Play");
		resamplePlayButton.setPreferredSize(new Dimension(100,30));
		
		resampleRatio = new JTextField();
		resampleRatio.setText("resampleRatio");
		resampleRatio.setPreferredSize(new Dimension(100,30));
		
		resampleTip = new JLabel();
		resampleTip.setText("float:0.5");
		resampleTip.setPreferredSize(new Dimension(200,30));

		JPanel p2 = new JPanel();
		p2.setLayout(new BoxLayout(p2, BoxLayout.X_AXIS));
		p2.add(resampleButton);
		p2.add(resamplePlayButton);
		p2.add(resampleRatio);
		p2.add(resampleTip);
		
		return p2;
	}



	private JPanel initOlaPanel() {
		
		olaButton = new JButton();
		olaButton.setText("OLA");
		olaButton.setPreferredSize(new Dimension(100,30));

		
		olaPlayButton = new JButton();
		olaPlayButton.setText("Play");
		olaPlayButton.setPreferredSize(new Dimension(100,30));

		
		olaRatio = new JTextField();
		olaRatio.setText("olaRatio");
		olaRatio.setPreferredSize(new Dimension(100,30));

		
		olaTip = new JLabel();
		olaTip.setText("float:0.5");
		olaTip.setPreferredSize(new Dimension(200,30));


		JPanel p3 = new JPanel();
		p3.setLayout(new BoxLayout(p3, BoxLayout.X_AXIS));
		p3.add(olaButton);
		p3.add(olaPlayButton);
		p3.add(olaRatio);
		p3.add(olaTip);
		
		return p3;
	}



	private JPanel initVolPanel() {
		
		volButton = new JButton();
		volButton.setText("Volume");
		volButton.setPreferredSize(new Dimension(100,30));
		
		volPlayButton = new JButton();
		volPlayButton.setText("Play");
		volPlayButton.setPreferredSize(new Dimension(100,30));
		
		volRatio = new JTextField();
		volRatio.setText("volRatio");
		volRatio.setPreferredSize(new Dimension(100,30));
		
		volTip = new JLabel();
		volTip.setText("float:0.5");
		volTip.setPreferredSize(new Dimension(200,30));

		JPanel p4 = new JPanel();
		p4.setLayout(new BoxLayout(p4, BoxLayout.X_AXIS));
		p4.add(volButton);
		p4.add(volPlayButton);
		p4.add(volRatio);
		p4.add(volTip);
		
		return p4;
	}



	private JPanel initFilterPanel() {
		
		filterButton = new JButton();
		filterButton.setText("Filter");
		filterButton.setPreferredSize(new Dimension(100,30));
		
		filterPlayButton = new JButton();
		filterPlayButton.setText("Play");
		filterPlayButton.setPreferredSize(new Dimension(100,30));
		
		filterRatio = new JTextField();
		filterRatio.setText("filterRatio");
		filterRatio.setPreferredSize(new Dimension(100,30));
		
		filterTip = new JLabel();
		filterTip.setText("float:0.2  0.2  0.2  ,   1.0");
		filterTip.setPreferredSize(new Dimension(200,30));

		JPanel p5 = new JPanel();
		p5.setLayout(new BoxLayout(p5, BoxLayout.X_AXIS));
		p5.add(filterButton);
		p5.add(filterPlayButton);
		p5.add(filterRatio);
		p5.add(filterTip);
		
		return p5;
	}



	private JPanel initMixPanel() {
		
		mixButton = new JButton();
		mixButton.setText("Mix");
		mixButton.setPreferredSize(new Dimension(100,30));
		
		mixPlayButton = new JButton();
		mixPlayButton.setText("Play");
		mixPlayButton.setPreferredSize(new Dimension(100,30));
		
		blank2 = new JTextField();
		blank2.setEditable(false);
		blank2.setPreferredSize(new Dimension(100,30));
		
		mixTip = new JLabel();
		mixTip.setText("click mix and choose an audio");
		mixTip.setPreferredSize(new Dimension(200,30));

		JPanel p6 = new JPanel();
		p6.setLayout(new BoxLayout(p6, BoxLayout.X_AXIS));
		p6.add(mixButton);
		p6.add(mixPlayButton);
		p6.add(blank2);
		p6.add(mixTip);
		
		return p6;
	}



	private JPanel initReversePanel() {
		
		reverseButton = new JButton();
		reverseButton.setText("Reverse");
		reverseButton.setPreferredSize(new Dimension(100,30));
		
		reversePlayButton = new JButton();
		reversePlayButton.setText("Play");
		reversePlayButton.setPreferredSize(new Dimension(100,30));
		
		blank3 = new JTextField();
		blank3.setEditable(false);
		blank3.setPreferredSize(new Dimension(100,30));
		
		reverseTip = new JLabel();
		reverseTip.setText("Reverse the audio can be magic");
		reverseTip.setPreferredSize(new Dimension(200,30));

		JPanel p7 = new JPanel();
		p7.setLayout(new BoxLayout(p7, BoxLayout.X_AXIS));
		p7.add(reverseButton);
		p7.add(reversePlayButton);
		p7.add(blank3);
		p7.add(reverseTip);
		
		return p7;
	}



	private JPanel initSavePanel() {
		
		saveButton = new JButton();
		saveButton.setText("Save");
		saveButton.setPreferredSize(new Dimension(100,30));
		
		stopButton = new JButton();
		stopButton.setText("Stop");
		stopButton.setPreferredSize(new Dimension(100,30));
		
		resetButton = new JButton();
		resetButton.setText("Reset");
		resetButton.setPreferredSize(new Dimension(100,30));
		
		back = new JButton();
		back.setText("<");
		back.setPreferredSize(new Dimension(100,30));
		
		front = new JButton();
		front.setText(">");
		front.setPreferredSize(new Dimension(100,30));
		
		JPanel p8 = new JPanel();
		p8.setLayout(new GridLayout());
		p8.add(saveButton);
		p8.add(stopButton);
		p8.add(resetButton);
		p8.add(back);
		p8.add(front);
		
		return p8;
	}



	private JPanel initLogPanel() {
			
		sp = new JScrollPane();
		ta = new JTextArea();
		ta.setEditable(false);
		ta.setColumns(20);  
        ta.setRows(5);
        sp.setViewportView(ta);
		
		JPanel p9 = new JPanel();
		p9.setLayout(new BoxLayout(p9, BoxLayout.X_AXIS));
		p9.add(sp);

		return p9;
		
	}



	private void initEventListener() 
	{
		
		openButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFileChooser fc = new JFileChooser();
				if(JFileChooser.APPROVE_OPTION == fc.showOpenDialog(openButton))
				{
					openPath.setText(fc.getSelectedFile().getAbsolutePath());
				    opened = new AudioPro(fc.getSelectedFile().getAbsolutePath());
				    processed = new AudioPro(opened.getData(),opened.getChannel(),
							opened.getBitDepth(),opened.getSampleRate());
				    proList.add(processed);
				    index = proList.size()-1;
				    setTextArea("openButton");
				}
			}
		});
		
		openPlayButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(null != opened)
				{
					processed.play();
				}
				setTextArea("openPlayButton");
			}
			
		});
		
		resampleButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				if(processed==null)
				{return;}
				String rRatio = resampleRatio.getText();
				float r;
				try{
					r = Float.parseFloat(rRatio);
				}catch (NumberFormatException ee){
					r = 1.0f;
				}
				processed = new AudioPro(processed.resample(r),processed.getChannel(),
						processed.getBitDepth(),processed.getSampleRate());
				proList.add(processed);
				index = proList.size()-1;
				setTextArea("resampleButton");
				
			}

	
		});
		
		resamplePlayButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(null != processed)
				{
					processed.play();
				}
				setTextArea("resamplePlayButton");
			}
		});
		
		resampleRatio.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				resampleRatio.setText("");
			}

			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
        });
		
		saveButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(null != processed)
				{
					processed.write();
				}
				setTextArea("saveButton");
			}
		});
		
		stopButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(null != processed)
				{
					processed.stop();
				}
				setTextArea("stopButton");
			}
		});
		
		resetButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				processed=new AudioPro(opened.getData(),opened.getChannel(),
						processed.getBitDepth(),processed.getSampleRate());
				setTextArea("resetButton");
			}
		});
		
		back.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				index--;
				if(index<0) return;
				processed=proList.get(index);
				setTextArea("<");
			}
		});
		
		front.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				index++;
				if(index>=proList.size()) return;
				processed=proList.get(index);
				setTextArea(">");
			}
		});
		
		olaButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				if(processed==null)
				{return;}
				String oRatio = olaRatio.getText();
				float o;
				try{
					o = Float.parseFloat(oRatio);
				}catch (NumberFormatException e){
					o = 1.0f;
				}
				processed = new AudioPro(processed.ola(o),processed.getChannel(),
						processed.getBitDepth(),processed.getSampleRate());
				proList.add(processed);
				index = proList.size()-1;
				setTextArea("olaButton");
			}

			
		});
		
		olaPlayButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				if(null != processed)
				{
					processed.play();
				}
				setTextArea("olaPlayButton");
			}

			
		});
		
		olaRatio.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				olaRatio.setText("");
			}

			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
        });
		
		volButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				if(processed==null)
				{return;}
				String vRatio = volRatio.getText();
				float v;
				try{
					v = Float.parseFloat(vRatio);
				}catch (NumberFormatException e){
					v = 1.0f;
				}
				processed = new AudioPro(processed.volume(v),processed.getChannel(),
						processed.getBitDepth(),processed.getSampleRate());
				proList.add(processed);
				index = proList.size()-1;
				setTextArea("volButton");
			}

			
		});
		
		volPlayButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(null != processed)
				{
					processed.play();
				}
				setTextArea("volPlayButton");
			}

			
		});
		
		volRatio.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				volRatio.setText("");
			}

			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
        });
		
		filterButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String s = filterRatio.getText();
				float[] b = new float[s.length()];
				float[] a = new float[s.length()];
				RegExp(b,a,s);
				ArrayList<Float> list1=new ArrayList<Float>();
				ArrayList<Float> list2=new ArrayList<Float>();
				for(int i=0;i<b.length;i++)
				{
					if(b[i]!=0)
						list1.add(b[i]);
				}
				for(int i=0;i<a.length;i++)
				{
					if(a[i]!=0)
						list2.add(a[i]);
				}
				float[] b1=new float[list1.size()];
				float[] a1=new float[list2.size()];
				for(int i=0;i<b1.length;i++)
				{
					b1[i]=list1.get(i);
				}
				for(int i=0;i<a1.length;i++)
				{
					a1[i]=list2.get(i);
				}
				
				processed = new AudioPro(processed.filter(b1, a1),processed.getChannel(),
						processed.getBitDepth(),processed.getSampleRate());
				proList.add(processed);
				index = proList.size()-1;
				setTextArea("filterButton");
				for(int i=0;i<b.length;i++)
				{
					System.out.println(b[i]);
				}
				for(int i=0;i<a.length;i++)
				{
					System.out.println(a[i]);
				}
				
			}

			
		});
		
		filterPlayButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(null != processed)
				{
					processed.play();
				}
				setTextArea("filterPlayButton");
			}

			
		});
		
		filterRatio.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				filterRatio.setText("");
			}

			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
        });
		
		mixButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JFileChooser fc = new JFileChooser();
				if(JFileChooser.APPROVE_OPTION == fc.showOpenDialog(mixButton))
				{
				    mixed = new AudioPro(fc.getSelectedFile().getAbsolutePath());
				    processed = new AudioPro(processed.mix(mixed.getData(), 1, 1),processed.getChannel(),
				    		processed.getBitDepth(),processed.getSampleRate());
				    proList.add(processed);
					index = proList.size()-1;
					blank2.setText("mixed:");
					mixTip.setText(fc.getSelectedFile().getAbsolutePath());
					setTextArea("mixButton");
			    }
			}
		});
		
		mixPlayButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(null != processed)
				{
					processed.play();
				}
				setTextArea("mixPlayButton");
			}

			
		});
		
		reverseButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				processed = new AudioPro(processed.reverse(),processed.getChannel(),
						processed.getBitDepth(),processed.getSampleRate());
				proList.add(processed);
				index = proList.size()-1;
				setTextArea("reverseButton");
			}

			
		});
		
		reversePlayButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(null != processed)
				{
					processed.play();
				}
				setTextArea("reversePlayButton");
			}

			
		});
		
	}

	public void RegExp(float[] bb,float[] aa,String str)
	{
		String[] strArray = str.trim().split("\\s*,"); 
		if(strArray.length == 2)
			{
			setTextArea("filter parameter error");
			System.out.println(strArray.length);
			}

		String[] b = strArray[0].trim().split("\\s+");
		String[] a = strArray[1].trim().split("\\s+");
		
		for(int i=0;i<b.length;i++)
		{
			if(b[i].trim().isEmpty()) continue;
			bb[i] = Float.parseFloat(b[i]);
		}
		for(int i=0;i<a.length;i++)
		{
			if(a[i].trim().isEmpty()) continue;
			aa[i] = Float.parseFloat(a[i]);
		}
	}
	
	public void setTextArea(String s)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String temp = ta.getText() + sdf.format(new Date()) + "  " + s +"\r\n";
		ta.setText(temp);
	}
	
	
	
	

	
}

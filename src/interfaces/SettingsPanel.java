package interfaces;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.ColorUIResource;

import vacuumCleaner.AbstractAgent.VisibilityType;
import vacuumCleaner.Environment;
import vacuumCleaner.Environment.Type;

public class SettingsPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public MainJFrame mainFrame;
	
	private JPanel dimensionPanel;
	private JTextField sizeField;
	private JLabel sizeLabel;
	
	private JPanel generationPanel;
	private JTextField dirtField;
	private JLabel dirtLabel;
	private JTextField obstaclesField;
	private JLabel obstaclesLabel;
	
	private JPanel agentPanel;
	private JTextField agentEnergyField;
	private JLabel agentEnergylabel;
	private JLabel agentVisibilityLabel;
	private JComboBox agentVisibilityCombobox;
	
	private JPanel commandPanel;
	private JButton refreshButton;
	JButton controlButton;
	
	private JPanel statusPanel;
	private JLabel pmLabel;
	private JLabel pmValueLabel;
	private JLabel stepsLabel;
	private JLabel stepsValueLabel;
	private JLabel currEnergyLabel;
	private JLabel currEnergyValueLabel;
	
	private int max_dim = 12;
	private int min_dim = 2;

	private JLabel envTypeLabel;
	private JComboBox envTypeCombobox;
	/**
	 * 
	 * @param mainFrame
	 */
	public SettingsPanel(final MainJFrame mainFrame) {
		{
			this.mainFrame = mainFrame;
			GridBagLayout jPanel2Layout = new GridBagLayout();
			jPanel2Layout.rowWeights = new double[] {0.1, 0.1, 0.1, 0.1};
			jPanel2Layout.rowHeights = new int[] {1,1,1,1};
			jPanel2Layout.columnWeights = new double[] {0.1};
			jPanel2Layout.columnWidths = new int[] {1};
			
			DocumentListener refreshListener = (new DocumentListener() {
				
				@Override
				public void removeUpdate(DocumentEvent arg0) {
					refreshButton.setText("Refresh*");
					refreshButton.setBackground(Color.YELLOW);
				}
				
				@Override
				public void insertUpdate(DocumentEvent arg0) {
					refreshButton.setText("Refresh*");
					refreshButton.setBackground(Color.YELLOW);
				}
				
				@Override
				public void changedUpdate(DocumentEvent arg0) {
					refreshButton.setText("Refresh*");
					refreshButton.setBackground(Color.YELLOW);
				}
			});
			
			setLayout(jPanel2Layout);
			{
				dimensionPanel = new JPanel();
				dimensionPanel.setPreferredSize(new Dimension(300,100));
				Border marginOutside = new EmptyBorder(10,10,10,10);        
		        TitledBorder title = BorderFactory.createTitledBorder("Size Settings");
		        CompoundBorder upperBorder = new CompoundBorder(marginOutside, title);
		        Border marginInside = new EmptyBorder(10,10,10,10);
		        dimensionPanel.setBorder(new CompoundBorder(upperBorder, marginInside));
				
		        /*input field to set the size of the floor*/
				add(dimensionPanel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				sizeLabel = new JLabel();
				dimensionPanel.add(sizeLabel);
				sizeLabel.setText("Size");
				sizeField = new JTextField();
				dimensionPanel.add(sizeField);
				sizeField.setText("" + mainFrame.env.floor.length);
				sizeField.setPreferredSize(new Dimension(30, 30));
				sizeField.getDocument().addDocumentListener(refreshListener);
				
				envTypeLabel = new JLabel("Type");

		        Vector<Environment.Type> envTypeVector = new Vector<Environment.Type>();
		        envTypeVector.add(Environment.Type.DYNAMIC);
		        envTypeVector.add(Environment.Type.STATIC);
		        envTypeCombobox = new JComboBox(envTypeVector);
		        envTypeCombobox.setSelectedItem(mainFrame.env.type);
		        envTypeCombobox.addItemListener(new ItemListener() {
					
					@Override
					public void itemStateChanged(ItemEvent arg0) {
						refreshButton.setText("Refresh*");
						refreshButton.setBackground(Color.YELLOW);
					}
				});
		        
		        dimensionPanel.add(envTypeLabel);
		        dimensionPanel.add(envTypeCombobox);
			}
			{
				/*setting input fields*/
				generationPanel = new JPanel();
				generationPanel.setPreferredSize(new Dimension(350,110));
				Border marginOutside = new EmptyBorder(10,10,10,10);        
		        TitledBorder title = BorderFactory.createTitledBorder("Build Settings");
		        CompoundBorder upperBorder = new CompoundBorder(marginOutside, title);
		        Border marginInside = new EmptyBorder(10,10,10,10);
		        generationPanel.setBorder(new CompoundBorder(upperBorder, marginInside));
		        
				add(generationPanel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				{
					/*number of obstacles*/
					obstaclesLabel = new JLabel();
					obstaclesLabel.setText("Obstacles");
					obstaclesField = new JTextField();
					obstaclesField.setText("7");
					obstaclesField.setPreferredSize(new Dimension(30, 30));
					obstaclesField.getDocument().addDocumentListener(refreshListener);
					
					/*number of dirty tiles*/
					dirtLabel = new JLabel();
					dirtLabel.setText("Dirt");
					dirtField = new JTextField();
					dirtField.setText("15");
					dirtField.setPreferredSize(new Dimension(30, 30));
					dirtField.getDocument().addDocumentListener(refreshListener);

					generationPanel.add(obstaclesLabel);
					generationPanel.add(obstaclesField);
					generationPanel.add(dirtLabel);
					generationPanel.add(dirtField);
				}
			}
			{
				agentPanel = new JPanel();
				add(agentPanel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				Border marginOutside = new EmptyBorder(10,10,10,10);        
		        TitledBorder title = BorderFactory.createTitledBorder("Agent's settings");
		        CompoundBorder upperBorder = new CompoundBorder(marginOutside, title);
		        Border marginInside = new EmptyBorder(10,10,10,10);
		        agentPanel.setBorder(new CompoundBorder(upperBorder, marginInside));
		        
		        agentPanel.setLayout(new GridLayout(2,1));
		        
		        JPanel agentEnergyPanel = new JPanel();
		        agentEnergyPanel.setLayout(new FlowLayout());
		        agentPanel.add(agentEnergyPanel);
		        
		        agentEnergylabel = new JLabel("Energy");
		        agentEnergyPanel.add(agentEnergylabel);
		        agentEnergyField = new JTextField("" + mainFrame.agent.opBound);
		        agentEnergyField.setPreferredSize(new Dimension(30, 30));
		        agentEnergyField.getDocument().addDocumentListener(refreshListener);
		        agentEnergyPanel.add(agentEnergyField);
		        
		        JPanel agentVisibilityPanel = new JPanel();
		        
		        agentPanel.add(agentVisibilityPanel);
		        
		        agentVisibilityLabel = new JLabel("Visibility");

		        Vector<VisibilityType> visTypeVector = new Vector<VisibilityType>();
		        visTypeVector.add(VisibilityType.MY_CELL);
		        visTypeVector.add(VisibilityType.MY_NEIGHBOURS);
		        visTypeVector.add(VisibilityType.ALL);
		        agentVisibilityCombobox = new JComboBox(visTypeVector);
		        agentVisibilityCombobox.setSelectedItem(mainFrame.agent.visType);
		        agentVisibilityCombobox.addItemListener(new ItemListener() {
					
					@Override
					public void itemStateChanged(ItemEvent arg0) {
						refreshButton.setText("Refresh*");
						refreshButton.setBackground(Color.YELLOW);
					}
				});
		        
		        agentVisibilityPanel.setLayout(new FlowLayout());
		        agentVisibilityPanel.add(agentVisibilityLabel);
		        agentVisibilityPanel.add(agentVisibilityCombobox);
			}
			{
				commandPanel = new JPanel();
				commandPanel.setPreferredSize(new Dimension(300,110));
				Border marginOutside = new EmptyBorder(10,10,10,10);        
		        TitledBorder title = BorderFactory.createTitledBorder("Commands");
		        CompoundBorder upperBorder = new CompoundBorder(marginOutside, title);
		        Border marginInside = new EmptyBorder(10,10,10,10);
		        commandPanel.setBorder(new CompoundBorder(upperBorder, marginInside));
				
				add(commandPanel, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				{   
					/*Refresh current configuration*/
					refreshButton = new JButton();
					commandPanel.add(refreshButton);
					refreshButton.setText("Refresh");
					refreshButton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent arg0) {
							int size = Integer.parseInt(sizeField.getText());
							int dirt = Integer.parseInt(dirtField.getText());
							int obstacles = Integer.parseInt(obstaclesField.getText());
							int energy = Integer.parseInt(agentEnergyField.getText());
							Environment.Type envType =  (Type) envTypeCombobox.getSelectedItem();
							VisibilityType visType = (VisibilityType) agentVisibilityCombobox.getSelectedItem();
							if(size < min_dim ){
								size = min_dim;
								sizeField.setText("" + min_dim);
								JOptionPane.showMessageDialog(null,"Minimun allowed size is " + min_dim, "Warning", JOptionPane.WARNING_MESSAGE);	
							}
							if(size > max_dim ){
								size = max_dim;
								sizeField.setText("" + max_dim);
								JOptionPane.showMessageDialog(null,"Maximun allowed size is " + max_dim, "Warning", JOptionPane.WARNING_MESSAGE);
							}
							mainFrame.newConfig(size, dirt, obstacles, envType, visType, energy);
							update();
							refreshButton.setText("Refresh");
							refreshButton.setBackground(new ColorUIResource(238,238,238));
						}
					});
					/*Start simulation of agent*/
					controlButton = new JButton();
					commandPanel.add(controlButton);
					controlButton.setText("Start");
					controlButton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent arg0) {
							if(controlButton.getText().equals("Start")){
								controlButton.setText("Stop");
								mainFrame.stopped = false;
								class myThread implements Runnable{
									public void run() {
										mainFrame.mainLoop();
								    }
								}
								new Thread(new myThread()).start();
							}
							else{
								mainFrame.stopped = true;
								mainFrame.env.agent.x = 0;
								mainFrame.env.agent.y = 0;
								mainFrame.gridPanel.update();
								controlButton.setText("Start");
							}								
						}
					});
				}
			}
			{
				statusPanel = new JPanel();
				Border marginOutside = new EmptyBorder(10,10,10,10);        
		        TitledBorder title = BorderFactory.createTitledBorder("Status");
		        CompoundBorder upperBorder = new CompoundBorder(marginOutside, title);
		        Border marginInside = new EmptyBorder(10,10,10,10);
		        statusPanel.setBorder(new CompoundBorder(upperBorder, marginInside));
				statusPanel.setLayout(new GridLayout(3,1));
				add(statusPanel, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				
				JPanel pmPanel = new JPanel();
				statusPanel.add(pmPanel);
				{
					pmLabel = new JLabel("Performance Measure:");
					pmValueLabel = new JLabel("" + mainFrame.env.performanceMeasure());
					pmPanel.add(pmLabel);
					pmPanel.add(pmValueLabel);
				}
				JPanel stepPanel = new JPanel();
				statusPanel.add(stepPanel);
				{
					stepsLabel = new JLabel("Steps Number:");
					stepsValueLabel = new JLabel("" + mainFrame.agent.actionList.size());
					stepPanel.add(stepsLabel);
					stepPanel.add(stepsValueLabel);
				}
				JPanel currEnergyPanel = new JPanel();
				statusPanel.add(currEnergyPanel);
				{
					currEnergyLabel = new JLabel("Agent's energy:");
					currEnergyValueLabel = new JLabel("" + (mainFrame.agent.opBound - mainFrame.agent.actionList.size()));
					currEnergyPanel.add(currEnergyLabel);
					currEnergyPanel.add(currEnergyValueLabel);
				}
			}
		}
	}
	public void update() {
		pmValueLabel.setText("" + mainFrame.env.performanceMeasure());
		stepsValueLabel.setText("" + mainFrame.agent.actionList.size());
		currEnergyValueLabel.setText("" + (mainFrame.agent.opBound - mainFrame.agent.actionList.size()));
	}
}

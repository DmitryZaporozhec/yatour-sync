package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import core.Synchronization;

public class MainWindow extends JFrame {
	private JFileChooser localPathChooser;
	private JFileChooser remotePathChooser;
	private JButton jbOpenLocalContainer;
	private JButton jbOpenRemoteContainer;
	private JButton jbSyncAll = new JButton("Sync Now!");

	private JList<File> outputFilesView = new JList<File>();
	private JList<File> inputFilesView = new JList<File>();

	private List<File> inputFiles = new ArrayList<File>();
	private List<File> outputFiles = new ArrayList<File>();

	public MainWindow() {
		super("Yatour sync");
		this.setResizable(false);
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout(12, 12));
		p.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		p.setPreferredSize(new Dimension(600, 600));
		this.setContentPane(p);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		jbOpenLocalContainer = new JButton("Source");
		jbOpenLocalContainer.setPreferredSize(new Dimension(120, 15));

		jbOpenRemoteContainer = new JButton("Target");
		jbOpenRemoteContainer.setPreferredSize(new Dimension(120, 15));

		inputFilesView.setPreferredSize(new Dimension(250, 500));
		outputFilesView.setPreferredSize(new Dimension(250, 500));
		outputFilesView.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		jbOpenLocalContainer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				File f = onShowChooser(localPathChooser);
				if (f != null) {
					inputFiles.add(f);
					inputFilesView.setListData(inputFiles
							.toArray(new File[inputFiles.size()]));
				}
			}
		});

		jbOpenRemoteContainer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				File f = onShowChooser(remotePathChooser);
				if (f != null) {
					outputFiles.add(f);
					outputFilesView.setListData(outputFiles
							.toArray(new File[outputFiles.size()]));
				}
			}
		});

		jbSyncAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Synchronization sync = new Synchronization();
				try {
					if (inputFilesView.getModel().getSize() == 0) {
						JOptionPane.showMessageDialog(MainWindow.this,
								"Source folder/s is not define!");
						return;
					}
					if (outputFilesView.getModel().getSize() == 0) {
						JOptionPane.showMessageDialog(MainWindow.this,
								"Target folder is not define!");
						return;
					}
					List<File> in = null;
					if (outputFilesView.isSelectionEmpty())
						outputFilesView.setSelectedIndex(0);
					if (inputFilesView.isSelectionEmpty()) {
						in = inputFiles;
					} else {
						in = inputFilesView.getSelectedValuesList();
					}
					sync.sync(in,
							outputFilesView.getSelectedValue());
				} catch (Exception e1) {
					System.out.println("Sync is crashed");
					e1.printStackTrace();
				}
			}
		});

		localPathChooser = initFileChooser();
		remotePathChooser = initFileChooser();

		p.add(jbOpenLocalContainer, BorderLayout.PAGE_START);
		p.add(inputFilesView, BorderLayout.LINE_START);

		p.add(jbOpenRemoteContainer, BorderLayout.PAGE_END);
		p.add(outputFilesView, BorderLayout.LINE_END);

		p.add(jbSyncAll, BorderLayout.CENTER);
		this.pack();
		this.setVisible(true);
	}

	private JFileChooser initFileChooser() {
		JFileChooser pathChooser = new JFileChooser();
		pathChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		return pathChooser;
	}

	public JFileChooser getLocalPathChooser() {
		return localPathChooser;
	}

	public void setLocalPathChooser(JFileChooser localPathChooser) {
		this.localPathChooser = localPathChooser;
	}

	public JFileChooser getRemotePathChooser() {
		return remotePathChooser;
	}

	public void setRemotePathChooser(JFileChooser remotePathChooser) {
		this.remotePathChooser = remotePathChooser;
	}

	public List<File> getInputFiles() {
		return inputFiles;
	}

	public void setInputFiles(List<File> inputFiles) {
		this.inputFiles = inputFiles;
	}

	public List<File> getOutputFiles() {
		return outputFiles;
	}

	public void setOutputFiles(List<File> outputFiles) {
		this.outputFiles = outputFiles;
	}

	private File onShowChooser(JFileChooser chooser) {
		int retVal = chooser.showOpenDialog(this);
		if (retVal == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile();
		} else {
			return null;
		}
	}
}

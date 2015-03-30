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
	private static final long serialVersionUID = 1L;
	private JFileChooser localPathChooser;
	private JFileChooser remotePathChooser;

	private JList<File> outputFilesView = new JList<File>();

	private List<File> outputFiles = new ArrayList<File>();

	public MainWindow() {
		super("Yatour sync");
		LeftMenu leftMenu = new LeftMenu();
		final LibraryMenu libraryMenu = new LibraryMenu();
		this.setResizable(false);
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout(12, 12));
		p.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		p.setPreferredSize(new Dimension(1024, 768));
		this.setContentPane(p);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		outputFilesView.setPreferredSize(new Dimension(250, 500));
		outputFilesView.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		leftMenu.getJbOpenLocalContainer().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				File f = onShowChooser(localPathChooser);
				if (f != null) {
					libraryMenu.getInputFiles().add(f);
					libraryMenu.getInputFilesView().setListData(libraryMenu.getInputFiles()
							.toArray(new File[libraryMenu.getInputFiles().size()]));
				}
			}
		});

		leftMenu.getJbOpenRemoteContainer().addActionListener(new ActionListener() {
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

		leftMenu.getJbSyncAll().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Synchronization sync = new Synchronization();
				try {
					if (libraryMenu.getInputFilesView().getModel().getSize() == 0) {
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
					if (libraryMenu.getInputFilesView().isSelectionEmpty()) {
						in = libraryMenu.getInputFiles();
					} else {
						in = libraryMenu.getInputFilesView().getSelectedValuesList();
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

		p.add(libraryMenu, BorderLayout.CENTER);

		p.add(outputFilesView, BorderLayout.LINE_END);
		p.add(leftMenu, BorderLayout.LINE_START);
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

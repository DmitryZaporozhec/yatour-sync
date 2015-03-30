package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

public class LibraryMenu extends JPanel {
	
	private JList<File> inputFilesView = new JList<File>();
	private List<File> inputFiles = new ArrayList<File>();
	private JLabel jlLibraryLabel;
	public LibraryMenu() {
		super();
		jlLibraryLabel = new JLabel("Библиотека");
		this.setLayout(new BorderLayout(12, 12));
		inputFilesView.addMouseListener(new InputViewListener(inputFilesView,inputFiles));
		inputFilesView.setAutoscrolls(true);
		this.add(jlLibraryLabel,BorderLayout.PAGE_START);
		this.add(inputFilesView,BorderLayout.CENTER);
	}
	public JList<File> getInputFilesView() {
		return inputFilesView;
	}
	public void setInputFilesView(JList<File> inputFilesView) {
		this.inputFilesView = inputFilesView;
	}
	public List<File> getInputFiles() {
		return inputFiles;
	}
	public void setInputFiles(List<File> inputFiles) {
		this.inputFiles = inputFiles;
	}
	
	
	
}

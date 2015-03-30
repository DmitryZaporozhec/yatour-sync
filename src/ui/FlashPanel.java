package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
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

public class FlashPanel extends JPanel {
	
	private File outputFile=null;
	private JLabel jlFlashLabel;
	private JLabel jlFlashPosition;
	public FlashPanel() {
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		this.setPreferredSize(new Dimension(250, 50));
		jlFlashLabel = new JLabel("Текущая флешка: ");
		jlFlashLabel.setFont(new Font("Arial", 0, 18));
		jlFlashPosition =new JLabel("Флешка не выбрана");
		jlFlashPosition.setFont(new Font("Arial", 0, 18));
		this.add(jlFlashLabel);
		this.add(jlFlashPosition);
	}
	public File getOutputFile() {
		return outputFile;
	}
	public void setOutputFile(File outputFile) {
		this.outputFile = outputFile;
	}
	public JLabel getJlFlashPosition() {
		return jlFlashPosition;
	}
	public void setJlFlashPosition(JLabel jlFlashPosition) {
		this.jlFlashPosition = jlFlashPosition;
	}
	
	
	
	
}

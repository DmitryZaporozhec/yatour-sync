package ui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class LeftMenu extends JPanel {
	private JButton jbOpenLocalContainer;
	private JButton jbOpenRemoteContainer;
	private JButton jbSyncAll;
	public LeftMenu() {
		super();
		this.setPreferredSize(new Dimension(200, 300));
		this.setSize(new Dimension(200, 300));
		this.setBounds(new Rectangle(new Dimension(200, 300)));
		this.setLayout(new GridLayout(3,1));
		
		jbSyncAll = new JButton("Поехали!");
		jbSyncAll.setAlignmentX(CENTER_ALIGNMENT);
		
		jbOpenLocalContainer = new JButton("Добавить библиотеку");
		jbOpenLocalContainer.setAlignmentX(CENTER_ALIGNMENT);
		
		jbOpenRemoteContainer = new JButton("Выбрать флешку");
		jbOpenRemoteContainer.setAlignmentX(CENTER_ALIGNMENT);
		this.add(jbOpenLocalContainer);
		this.add(jbOpenRemoteContainer);
		this.add(jbSyncAll);
	}

	public JButton getJbOpenLocalContainer() {
		return jbOpenLocalContainer;
	}

	public void setJbOpenLocalContainer(JButton jbOpenLocalContainer) {
		this.jbOpenLocalContainer = jbOpenLocalContainer;
	}

	public JButton getJbOpenRemoteContainer() {
		return jbOpenRemoteContainer;
	}

	public void setJbOpenRemoteContainer(JButton jbOpenRemoteContainer) {
		this.jbOpenRemoteContainer = jbOpenRemoteContainer;
	}

	public JButton getJbSyncAll() {
		return jbSyncAll;
	}

	public void setJbSyncAll(JButton jbSyncAll) {
		this.jbSyncAll = jbSyncAll;
	}

}

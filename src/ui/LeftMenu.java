package ui;

import java.awt.Dimension;
import java.awt.GridLayout;
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
		this.setPreferredSize(new Dimension(250, 768));
		this.setLayout(new GridLayout(30, 1));
		
		jbSyncAll = new JButton("Поехали!");
		
		jbOpenLocalContainer = new JButton("Добавить в библиотеку");
//		jbOpenLocalContainer.setSize(new Dimension(250, 20));
		jbOpenLocalContainer.setPreferredSize(new Dimension(250, 20));

		jbOpenRemoteContainer = new JButton("Выбрать флешку");
		jbOpenRemoteContainer.setPreferredSize(new Dimension(250, 20));
		
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

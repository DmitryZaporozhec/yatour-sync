package ui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.List;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListModel;

public class InputViewListener implements MouseListener {

	private final String CONFIRM_MESSAGE="Уверены, что хотите удалить выделенные папки?";
	private final String CONFIRM_CAPTION="Удаление из библиотеки";
	private JList<File> inputFilesView;
	private List<File> inputFiles;

	public InputViewListener(JList<File> inputFilesView, List<File> inputFiles) {
		this.inputFilesView = inputFilesView;
		this.inputFiles = inputFiles;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// optionPane.get
		if (e.getModifiers() == 4) {
			List<File> selectedFiles = inputFilesView.getSelectedValuesList();
			if (selectedFiles.size()==0){
				return;
			}
			int n = JOptionPane.showConfirmDialog(inputFilesView.getParent(),
					CONFIRM_MESSAGE, CONFIRM_CAPTION,
					JOptionPane.YES_NO_OPTION);
			if (n == 0) {
				for (File f : inputFilesView.getSelectedValuesList()) {
					inputFiles.remove(f);
				}
				inputFilesView.setListData(inputFiles
						.toArray(new File[inputFiles.size()]));
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}

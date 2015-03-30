package core;

import java.io.File;
import java.util.List;

import javax.swing.JLabel;

public class SyncThread extends Thread implements Runnable {

	private List<File> in;
	private File out;
	private JLabel output;


	public SyncThread(List<File> in, File out, JLabel output) {
		super();
		this.in = in;
		this.out = out;
		this.output = output;
	}


	@Override
	public void run() {
		Synchronization s = new Synchronization();
		try {
			s.sync(in, out,output);
		} catch (Exception e) {
			output.setText(e.getMessage());
		}
	};
}

package core;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JLabel;

public class Synchronization {

	public int sync(List<File> local, File outputFolder, JLabel output)
			throws Exception {
		long time = System.currentTimeMillis();
		// Get fiels from pathes
		File inputFolder = null;

		if (local == null || local.size() == 0) {
			throw new Exception("Local folders don't exists!");
		}

		// if (local == null || local.length() == 0) {
		// throw new Exception("Local folder is empty!");
		// }
		// if (inputFolder == null || outputFolder == null) {
		// throw new Exception("Can't open folders!");
		// }

		if (outputFolder == null) {
			throw new Exception("Remote folder is empty!");
		}
		output.setText("Output folder is=" + outputFolder);

		for (File loc : local) {
			output.setText("Input folder is=" + loc);

			if (loc == null) {
				output.setText("Local folder is empty!");
				continue;
			}

			inputFolder = loc;

			if (!inputFolder.isDirectory() || !outputFolder.isDirectory()) {
				throw new Exception("Specified pathes are not to directory!");
			}

			// make filter to MP3 only
			FilenameFilter filter = new FilenameFilter() {
				public boolean accept(File directory, String fileName) {
					return directory.isDirectory()
							|| directory.getName().endsWith(".mp3");
				}
			};

			// Read Input and output folders
			List<File> inputFiles = getAllFilesFromDirectoryRecursive(
					inputFolder, filter);
			List<File> outputFiles = getAllFilesFromDirectoryRecursive(
					outputFolder, filter);

			if (inputFiles == null || inputFiles.size() == 0) {
				throw new Exception("Нечего делать. Мызыки нет!");
			}

			// show files :
			output.setText("Нашел на компе! Всего=" + inputFiles.size());
			// printFileList(inputFiles);

			output.setText("Нашел на Yatour! Всего=" + outputFiles.size());
			// printFileList(outputFiles);

			// Make sets for checking repeations.
			Set<String> outFileNames = new HashSet<String>();
			for (File f : outputFiles) {
				outFileNames.add(f.getName());
			}
			// Get target folders;
			String folderPreffix = "CD";
			int incremental = 1;
			File[] fldrs = outputFolder.listFiles();
			List<File> folders = new ArrayList<File>();
			for (File folder : fldrs) {
				if (folder.isDirectory() && folder.getName().startsWith("CD")) {
					folders.add(folder);
				}
			}
			boolean fileAdded = false;
			for (File iFile : inputFiles) {
				fileAdded = false;
				if (!iFile.getName().endsWith(".mp3")) {
					output.setText("Файл" + iFile.getName()
							+ " не mp3!");
					continue;
				}
				if (outFileNames.contains(iFile.getName())) {
					output.setText("Файл " + iFile.getName()
							+ " уже в Yatour");
					continue;
				}

				for (File folder : folders) {
					if (folder.listFiles() != null
							&& folder.listFiles().length < 99) {
						// copy
						File to = new File(folder.getPath() + File.separator
								+ iFile.getName());
						output.setText("Копируем в " + to.getAbsolutePath());
						try {
							copyFile(iFile, to);
						} catch (Exception e) {
							output.setText("Can't copy file");
							output.setText(e.getMessage());
						}
						fileAdded = true;
						break;
					} else {
						continue;
					}
				}
				if (!fileAdded) {
					while (true) {
						// try to create dir
						String tempName = folderPreffix
								+ (incremental < 10 ? "0" + incremental
										: incremental);
						incremental++;
						File newFolder = new File(outputFolder.getPath()
								+ File.separator + tempName);
						if (!newFolder.exists()) {
							newFolder.mkdir();
							folders.add(newFolder);
							// copy
							File to = new File(newFolder.getPath()
									+ File.separator + iFile.getName());
							output.setText("Копируем в " + to.getAbsolutePath());
							try {
								copyFile(iFile, to);
							} catch (Exception e) {
								output.setText("Не могу скопировать"+e.getMessage());
							}
							break;
						}
					}
				}
			}
		}
		time = (System.currentTimeMillis() - time) / 1000;
		output.setText("Готово! Время: " + time+ " сек");
		return 1;
	}

	private List<File> getAllFilesFromDirectoryRecursive(File dir,
			FilenameFilter filter) {
		List<File> files = new ArrayList<File>();
		if (dir == null || !dir.isDirectory()) {
			return files;
		} else {
			File[] ff = dir.listFiles(filter);
			for (File f : ff) {
				if (f.isFile()) {
					files.add(f);
				}
				if (f.isDirectory()) {
					files.addAll(getAllFilesFromDirectoryRecursive(f, filter));
				}
			}
		}
		return files;
	}

	private void copyFile(File from, File to) throws Exception {
		if (!to.exists()) {
			to.createNewFile();
		}
		try (FileChannel in = new FileInputStream(from).getChannel();
				FileChannel out = new FileOutputStream(to).getChannel()) {

			out.transferFrom(in, 0, in.size());
		}
	}

}

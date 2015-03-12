package run;

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

public class Run {

	public static void main(String[] args) throws Exception {
		long time = System.currentTimeMillis();
		// Get fiels from pathes
		File inputFolder = null;
		File outputFolder = null;
		if (args != null && args.length == 2) {
			for (String arg : args) {
				if (arg.contains("--local=")) {
					inputFolder = new File(arg.replace("--local=", ""));
				}
				if (arg.contains("--remote=")) {
					outputFolder = new File(arg.replace("--remote=", ""));
				}
			}

			if (inputFolder == null || outputFolder == null) {
				throw new Exception("Can't open folders!");
			}
			if (!inputFolder.isDirectory() || !outputFolder.isDirectory()) {
				throw new Exception("Specified pathes are not to directory!");
			}

			System.out.printf("Input Folder=%s, Output Folder=%s\n",
					inputFolder, outputFolder);

			// make filter to MP3 only
			FilenameFilter filter = new FilenameFilter() {
				public boolean accept(File directory, String fileName) {
					return directory.isDirectory() || directory.getName().endsWith(".mp3");
				}
			};

			// Read Input and output folders
			List<File> inputFiles = getAllFilesFromDirectoryRecursive(
					inputFolder, filter);
			List<File> outputFiles = getAllFilesFromDirectoryRecursive(
					outputFolder, filter);

			if (inputFiles == null || inputFiles.size() == 0) {
				throw new Exception("Nothing to sync!");
			}

			// show files :
			System.out.println("INPUT! TOTAL=" + inputFiles.size());
			// printFileList(inputFiles);

			System.out.println("OutPUT!TOTAL=" + outputFiles.size());
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
					System.out.println("File " + iFile.getName()
							+ " is not mp3!");
					continue;
				}
				if (outFileNames.contains(iFile.getName())) {
					System.out.println("File " + iFile.getName()
							+ " Already Exists");
					continue;
				}

				for (File folder : folders) {
					if (folder.listFiles() != null
							&& folder.listFiles().length < 99) {
						// copy
						File to = new File(folder.getPath() + File.separator
								+ iFile.getName());
						System.out.println("Copy to" + to.getAbsolutePath());
						copyFile(iFile, to);
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
							System.out
									.println("Copy to" + to.getAbsolutePath());
							copyFile(iFile, to);
							break;
						}
					}
				}
			}
		} else {
			throw new Exception(
					"Source or/and Target folders are not specifided");
		}

		time = (System.currentTimeMillis() - time) / 1000;
		System.out.println("Sync time=" + time);
	}

	public static List<File> getAllFilesFromDirectoryRecursive(File dir,
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

	public static void printFileList(List<File> list) {
		for (File f : list) {
			System.out.println(f.getName());
		}
	}

	public static void copyFile(File from, File to) throws Exception {

		if (!to.exists()) {
			to.createNewFile();
		}

		try (FileChannel in = new FileInputStream(from).getChannel();
				FileChannel out = new FileOutputStream(to).getChannel()) {

			out.transferFrom(in, 0, in.size());
		}
	}

}

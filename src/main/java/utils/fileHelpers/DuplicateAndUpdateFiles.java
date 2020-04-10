package utils.fileHelpers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

/**
 * Duplicate And Update Files help update substring in files as well as
 * helps copying folder(s)
 * @author Khurram Waleed Bhatti
 *
 */
public class DuplicateAndUpdateFiles {
	
	protected static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public void updateTextInFile(String filePath, String oldString, String newString) {
		LOGGER.fine("updating text "+oldString+" to "+newString+" in "+filePath);
		File file = new File(filePath);
		String oldContent = "";
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			LOGGER.warning("FileNotFoundException: could not instanciate BufferReader");
			e.printStackTrace();
		}
		String line = null;
		try {
			line = reader.readLine();
			while (line != null) {
				oldContent = oldContent + line + System.lineSeparator();
				line = reader.readLine();
			}
		} catch (IOException e) {
			LOGGER.warning("IOException: could not read text in file");
			e.printStackTrace();
		}	
		String newContent = oldContent.replace(oldString, newString);
		FileWriter writer = null;
		try {
			writer = new FileWriter(file);
			writer.write(newContent);
		} catch (IOException e) {
			LOGGER.warning("IOException: could not write text in file");
			e.printStackTrace();
		}
		try {
			reader.close();
			writer.close();
		} catch (IOException e) {
			LOGGER.warning("IOException: could not close reader or writer");
			e.printStackTrace();
		}
		LOGGER.fine("updated text "+oldString+" to "+newString+" in "+filePath);
	}
	
	/**
	 * This method copies a folder and all the files inside of it
	 * @param sourceDirPath Full path in string for the folder that needs to be copied
	 * @param targerDirPath Full path in string where the copied folder needs to be saved
	 */
	public void copyFolder(String sourceDirPath, String targerDirPath) {
		LOGGER.fine("Copying folder from "+sourceDirPath+" to "+targerDirPath);
		Path sourceDirectory = Paths.get(sourceDirPath);
		Path targerDirectory = Paths.get(targerDirPath);
		copyFolder(sourceDirectory, targerDirectory);
		LOGGER.fine("Copied folder from "+sourceDirPath+" to "+targerDirPath);
	}
	
	/**
	 * This method copies a folder and all the files inside of it
	 * @param sourceDirPath Full path for the folder that needs to be copied
	 * @param targerDirPath Full path where the copied folder needs to be saved
	 */
	public void copyFolder(Path src, Path dest) {
	    try {
	        Files.walk( src ).forEach( s -> {
	            try {
	                Path d = dest.resolve( src.relativize(s) );
	                if( Files.isDirectory( s ) ) {
	                    if( !Files.exists( d ) )
	                        Files.createDirectory( d );
	                    return;
	                }
	                Files.copy( s, d );
	            } catch( Exception e ) {
	    			LOGGER.warning("Exception: copy folder unsuccessful");
	                e.printStackTrace();
	            }
	        });
	    } catch( Exception ex ) {
			LOGGER.warning("Exception: copy folder unsuccessful");
	        ex.printStackTrace();
	    }
	}
	
	public static void main(String[] args) throws Exception {
		String userDir = System.getProperty("user.dir");
		DuplicateAndUpdateFiles fileHelper = new DuplicateAndUpdateFiles();
		fileHelper.updateTextInFile(userDir+"/src/test/resources/unitTestFilesAndFolders/DuplicateMe/UpdateMe.txt", "replace", "replaced");
		//TODO: validate the file has been updated
		fileHelper.updateTextInFile(userDir+"/src/test/resources/unitTestFilesAndFolders/DuplicateMe/UpdateMe.txt", "replaced", "replace");
		fileHelper.copyFolder(userDir+"/src/test/resources/unitTestFilesAndFolders/DuplicateMe", userDir+"/target/DuplicatedMe");
		//TODO: validate the folder has been created
	}

}

package Player;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class FileTypeFilter extends FileFilter {

	//File Extensions String
	private final String extension;
	//file extensions description
	private final String description;
	
	
	public FileTypeFilter(String extension, String description) {
		this.extension = extension;
		this.description = description;
	}
	
	
	@Override
	public boolean accept(File file) {
		//is retunring directory?
		if(file.isDirectory()) {
			return true;
		}
		else {
			return file.getName().endsWith(extension);
		}
	}
	@Override
	public String getDescription() {
		//return file type of the description
		return description + String.format("(*%s)", extension);
	}
	
	
	
}

package org.tech.commons.util;

public class UpLoadFileInfo {
	private Boolean status;
	
	private String saveFileName;
	
	private String readFileName;
	
	private Long readFileSize;
	
	private String saveFilePath;

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}



	public String getSaveFileName() {
		return saveFileName;
	}

	public void setSaveFileName(String saveFileName) {
		this.saveFileName = saveFileName;
	}

	public String getReadFileName() {
		return readFileName;
	}

	public void setReadFileName(String readFileName) {
		this.readFileName = readFileName;
	}
	

	public Long getReadFileSize() {
		return readFileSize;
	}

	public void setReadFileSize(Long readFileSize) {
		this.readFileSize = readFileSize;
	}

	public String getSaveFilePath() {
		return saveFilePath;
	}

	public void setSaveFilePath(String saveFilePath) {
		this.saveFilePath = saveFilePath;
	}
	
	
}

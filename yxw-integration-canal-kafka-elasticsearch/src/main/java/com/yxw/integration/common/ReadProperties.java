package com.yxw.integration.common;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadProperties {

	public static final String FILE_SEPARATOR = System.getProperty("file.separator");

	private String relativePath = "instance/";
	private String propertiesFilename = "service.properties";
	Properties p = new Properties();

	public Properties read() {
		try {
			InputStream inputStream =
					new BufferedInputStream(new FileInputStream(ReadProperties.class.getClassLoader().getResource(propertiesFilename)
							.getPath()));
			try {
				p.load(inputStream);
			} catch (IOException e) {

			}
		} catch (FileNotFoundException e) {

		}
		return p;
	}

	public Properties read(String propertiesFilename) {
		try {
			InputStream inputStream =
					new BufferedInputStream(new FileInputStream(ReadProperties.class.getClassLoader()
							.getResource(relativePath + propertiesFilename).getPath()));
			try {
				p.load(inputStream);
			} catch (IOException e) {

			}
		} catch (FileNotFoundException e) {

		}
		return p;
	}

	/**
	 * @return the propertiesFilename
	 */
	public String getPropertiesFilename() {
		return propertiesFilename;
	}

	/**
	 * @param propertiesFilename the propertiesFilename to set
	 */
	public void setPropertiesFilename(String propertiesFilename) {
		this.propertiesFilename = propertiesFilename;
	}

	/**
	 * @return the relativePath
	 */
	public String getRelativePath() {
		return relativePath;
	}

	/**
	 * @param relativePath the relativePath to set
	 */
	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}

}

/*
 * Title:        CloudSim Toolkit
 * Description:  CloudSim (Cloud Simulation) Toolkit for Modeling and Simulation of Clouds
 * Licence:      GPL - http://www.gnu.org/copyleft/gpl.html
 *
 * Copyright (c) 2009-2012, The University of Melbourne, Australia
 */

package org.cloudbus.cloudsim;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Set;

import org.fog.examples.DataPlacement;

/**
 * The Log class used for performing loggin of the simulation process. It provides the ability to
 * substitute the output stream by any OutputStream subclass.
 *
 * @author Anton Beloglazov
 * @since CloudSim Toolkit 2.0
 */
public class Log {

	/** The Constant LINE_SEPARATOR. */
	private static final String LINE_SEPARATOR = System.getProperty("line.separator");

	/** The output. */
	private static OutputStream output;

	/** The disable output flag. */
	private static boolean disabled;

	/**
	 * Prints the message.
	 *
	 * @param message the message
	 */
	public static void print(String message) {
		if (!isDisabled()) {
			try {
				getOutput().write(message.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Prints the message passed as a non-String object.
	 *
	 * @param message the message
	 */
	public static void print(Object message) {
		if (!isDisabled()) {
			print(String.valueOf(message));
		}
	}

	/**
	 * Prints the line.
	 *
	 * @param message the message
	 */
	public static void printLine(String message) {
		if (!isDisabled()) {
			print(message + LINE_SEPARATOR);
		}
	}

	/**
	 * Prints the empty line.
	 */
	public static void printLine() {
		if (!isDisabled()) {
			print(LINE_SEPARATOR);
		}
	}

	/**
	 * Prints the line passed as a non-String object.
	 *
	 * @param message the message
	 */
	public static void printLine(Object message) {
		if (!isDisabled()) {
			printLine(String.valueOf(message));
		}
	}

	/**
	 * Prints a string formated as in String.format().
	 *
	 * @param format the format
	 * @param args the args
	 */
	public static void format(String format, Object... args) {
		if (!isDisabled()) {
			print(String.format(format, args));
		}
	}

	/**
	 * Prints a line formated as in String.format().
	 *
	 * @param format the format
	 * @param args the args
	 */
	public static void formatLine(String format, Object... args) {
		if (!isDisabled()) {
			printLine(String.format(format, args));
		}
	}

	/**
	 * Sets the output.
	 *
	 * @param _output the new output
	 */
	public static void setOutput(OutputStream _output) {
		output = _output;
	}

	/**
	 * Gets the output.
	 *
	 * @return the output
	 */
	public static OutputStream getOutput() {
		if (output == null) {
			setOutput(System.out);
		}
		return output;
	}

	/**
	 * Sets the disable output flag.
	 *
	 * @param _disabled the new disabled
	 */
	public static void setDisabled(boolean _disabled) {
		disabled = _disabled;
	}

	/**
	 * Checks if the output is disabled.
	 *
	 * @return true, if is disable
	 */
	public static boolean isDisabled() {
		return disabled;
	}

	/**
	 * Disables the output.
	 */
	public static void disable() {
		setDisabled(true);
	}

	/**
	 * Enables the output.
	 */
	public static void enable() {
		setDisabled(false);
	}

	private static Set<String> appendSet = new HashSet<>();

	private static boolean append(String token) {
		if (!appendSet.contains(token)) {
			appendSet.add(token);
			return false;
		}
		return true;
	}

	public static void writeInLogFile(String devName, String msg) {
		java.io.File log = new File("Log");
		if (!log.exists()) log.mkdir();

		FileWriter lpFile;
		try {
			String tag = String.format("%.1f_%.1f_%.1f_%.1f_%.1f_%s_%b", DataPlacement.HGW_Storage_Min_Threshold, DataPlacement.HGW_Storage_Max_Threshold, DataPlacement.HGW_Storage_Compression, DataPlacement.HGW_Compression_Selection, DataPlacement.HGW_Critical_Selection, DataPlacement.storageMode, DataPlacement.offload);
			lpFile = new FileWriter("Log/logFile_"+tag+".txt", append(tag));
//			lpFile = new FileWriter("Log/logFile"+DataPlacement.nb_HGW+".txt", append("logFile"));
			BufferedWriter fw = new BufferedWriter(lpFile);
			fw.write(devName+"\t"+msg+"\n");
			fw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void initializeLogFile() {
		java.io.File log = new File("Log");
		if (!log.exists()) log.mkdir();

		FileWriter lpFile;
		try {
			lpFile = new FileWriter("Log/logFile"+DataPlacement.nb_HGW+".txt");
			BufferedWriter fw = new BufferedWriter(lpFile);
			fw.write("Simulation.....\n");
			fw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}

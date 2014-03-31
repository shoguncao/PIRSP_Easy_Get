package com.example.pirsp_easy_get;

import android.annotation.SuppressLint;
import android.os.Environment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.os.Process;

public class PirspLog {
	// java中没有宏，但对永远为false的语句，编译器将不对条件覆盖的代码生成字节码，所以可以代替预编译宏
	final boolean OPEN_LOG = true;	// 日志开头是否打开
	
	private static PirspLog pirspLog = null;
	
	private FileWriter logFileWriter;
	private PrintWriter logPrintWriter;
	
	public static PirspLog GetInstance() {
		if (null == pirspLog) {
			synchronized (PirspLog.class) {
				if (null == pirspLog) {
					pirspLog = new PirspLog();
				}
			}
		}
		return pirspLog;
	}
	
	private PirspLog() {
		initLog();
	}
	
	protected void finalize() {
		if (null != logPrintWriter) {
			logPrintWriter.close();
		}
		if (null != logFileWriter) {
			try {
				logFileWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@SuppressLint({ "SdCardPath", "SimpleDateFormat" })
	public boolean initLog() {
		if (OPEN_LOG) {
			try {
				String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/PirspPlatform";
				File dir = new File(dirPath);
				if (!dir.exists()) {
					dir.mkdir();
				}
				long time = System.currentTimeMillis();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
				Date date = new Date(time);
				String logPath = dirPath + "/" + dateFormat.format(date) + ".log";
				File file = new File(logPath);
				if (!file.exists()) {
					file.createNewFile();
				}
				logFileWriter = new FileWriter(logPath, true);
				logPrintWriter = new PrintWriter(logFileWriter);
				return true;
			} catch (IOException e) {
				System.out.print(e.getMessage());
				return false;
			}
		} else {
			return false;
		}
	}
	
	public void log(String format, Object ...args) {
		if (OPEN_LOG) {
			if ((null != logFileWriter) && (null != logPrintWriter)) {
				String timeInfo = getTimeInfo();
				String threadInfo = getThreadInfo();
				String methodInfo = getMethodInfo();
				logPrintWriter.printf(timeInfo + threadInfo + methodInfo + format + "\n", args);
				logPrintWriter.flush();
			}
		}
	}
	
	@SuppressLint("SimpleDateFormat")
	private String getTimeInfo() {
		if (OPEN_LOG) {
			long time = System.currentTimeMillis();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			Date date = new Date(time);
			String timeInfo = dateFormat.format(date);
			return timeInfo;
		}
		return null;
	}
	
	private String getThreadInfo() {
		if (OPEN_LOG) {
			String threadInfo = String.format("(pid:(0x%X))(tid:(0x%X))", Process.myPid(), Process.myTid());
			return threadInfo;
		}
		return null;
	}
	
	private String getMethodInfo() {
		if (OPEN_LOG) {
			StackTraceElement traceElement = ((new Exception()).getStackTrace())[1]; 
			StringBuffer toStringBuffer = new StringBuffer("[").append( 
			traceElement.getFileName()).append("|").append( 
			traceElement.getLineNumber()).append("|").append( 
			traceElement.getMethodName()).append("]"); 
			return toStringBuffer.toString(); 
		}
		return null;
	}
}

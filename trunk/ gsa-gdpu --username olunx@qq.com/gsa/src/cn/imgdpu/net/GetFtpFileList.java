/*
 *@author fatkun , Time:2009-4-15
 *
 *Website : http://www.olunx.com
 *
 *This: 获取FTP目录文件
 */

package cn.imgdpu.net;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import cn.imgdpu.util.GeneralMethod;
import cn.imgdpu.util.SqlProcess;

public class GetFtpFileList extends Thread {
	FTPClient ftpClient;
	String server, user, password;
	public ArrayList<String> fileList;// 文件列表
	ArrayList<String> dirQueue;// 文件夹队列
	String dirPre;// 队列,前一个目录
	int fileCount = 0;
	int errCode;// 错误数字
	String errMsg;// 错误信息
	String encode = "GBK";// 编码
	String queFilePath;
	String bufFilePath;

	ArrayList<String> siteList = new ArrayList<String>();
	ArrayList<String> delFileList = new ArrayList<String>();
	public boolean cancel = false;

	public GetFtpFileList(ArrayList<String> siteList) {
		this.siteList = siteList;
	}

	@Override
	public void run() {
		try {
			updateFor: for (int i = 0; i < siteList.size(); i += 3) {
				ArrayList<String> fileListXml = new ArrayList<String>();// 文件列表
				String server = siteList.get(i);
				String user = siteList.get(i + 1);
				String password = siteList.get(i + 2);
				for (int j = 0; j <= 4; j++) {// 出错重试次数
					if (this.isInterrupted() || cancel)
						break updateFor;
					cn.imgdpu.GSAGUI.setStatusAsyn(server + ":开始获取FTP文件列表...");
					if (connectServer(server, user, password)) {
						fileListXml = fileList;
						SqlProcess.setData("update ftpList set updatetime='" + GeneralMethod.getGeneralMethod().getNowTime() + "' where ip='"
								+ server + "'");
						SqlProcess.setData("update ftpList set filetable='" + GeneralMethod.getGeneralMethod().getIpToTableName(server)
								+ "' where ip='" + server + "'");

						cn.imgdpu.util.SqlProcess.setFileList(GeneralMethod.getGeneralMethod().getIpToTableName(server), fileListXml);// 写入数据库

						cn.imgdpu.GSAGUI.setStatusAsyn(server + ":完成获取FTP文件列表!");
						break;
					} else {
						cn.imgdpu.GSAGUI.setStatusAsyn(server + ":错误,10秒后重新连接." + errMsg);
						Thread.sleep(1000 * 10);
					}
				}
			}

			cn.imgdpu.compo.FtpFilesUpdateCompo.readSite();// 读取站点列表
			if (!isInterrupted())
				for (String s : delFileList) {// 删除缓存文件
					deleteFile(cn.imgdpu.util.FileUrlConv.UrlConvIo(s));
				}

		} catch (InterruptedException e) {
			cn.imgdpu.util.CatException.getMethod().catException(e, "线程中断异常");
		}
		if (!cn.imgdpu.GSAGUI.shell.isDisposed())
			cn.imgdpu.GSAGUI.shell.getDisplay().asyncExec(new Runnable() {

				@Override
				public void run() {
					cn.imgdpu.GSAGUI.setStatus("获取FTP文件列表完成!");
					cn.imgdpu.compo.FtpFilesUpdateCompo.setOK();
				}

			});
	}

	public String getExtension(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int i = filename.lastIndexOf('.');
			int j = filename.lastIndexOf('/');

			if ((i > -1) && (i < (filename.length() - 1)) && (i > j)) {
				return filename.substring(i + 1);
			}
		}
		return "-";
	}

	// 得到队列文件
	public ArrayList<String> getQueue(String filePath) {
		return getFileToArr(filePath);// 创建队列文件
	}

	public ArrayList<String> addQueue(ArrayList<String> arr, String str) {
		boolean flag = false;
		for (Iterator<String> iter = arr.iterator(); iter.hasNext();) {
			String element = iter.next();
			if (element.equals(str)) {
				flag = true;
				break;
			}
		}
		if (!flag)
			arr.add(str);
		return arr;// 创建队列文件
	}

	// 删除文件
	public boolean deleteFile(String fileName) {
		File file = new File(fileName);
		if (file.isFile() && file.exists()) {
			file.delete();
			return true;
		} else {

			return false;
		}
	}

	// 文件转换为列表(一行一条数据)
	public ArrayList<String> getFileToArr(String filePath) {
		BufferedReader bufReader;
		ArrayList<String> arr = new ArrayList<String>();

		try {
			bufReader = new BufferedReader(new InputStreamReader(new FileInputStream(cn.imgdpu.util.FileUrlConv.UrlConvIo(filePath))));
			String str;
			while ((str = bufReader.readLine()) != null) {
				arr.add(str);
			}
			bufReader.close();
		} catch (IOException e) {
			cn.imgdpu.util.CatException.getMethod().catException(e, "IO异常");
		}

		return arr;
	}

	// 列表转为文件
	public void setArrToFile(ArrayList<String> arr, String filePath) {
		BufferedWriter bufWriter;
		if (arr != null) {
			try {
				bufWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(cn.imgdpu.util.FileUrlConv.UrlConvIo(filePath))));
				for (Iterator<String> iter = arr.iterator(); iter.hasNext();) {
					String element = iter.next();
					bufWriter.write(element + "\n");
				}
				bufWriter.flush();
				bufWriter.close();
			} catch (IOException e) {
				cn.imgdpu.util.CatException.getMethod().catException(e, "IO异常");
			}
		}
	}

	public boolean connectServer(String server) {
		return connectServer(server, "anonymous", "anonymous");
	}

	// 连接
	public boolean connectServer(String server, String user, String password) {
		boolean result = true;
		try {
			if (user.equals("")) {
				user = "anonymous";
				password = "anonymous";
			}

			this.server = server;
			this.user = user;
			this.password = password;

			ftpClient = new FTPClient();
			ftpClient.setControlEncoding(encode);
			ftpClient.connect(server);
			ftpClient.setSoTimeout(1000 * 30);
			ftpClient.setDefaultTimeout(1000 * 30);
			ftpClient.setConnectTimeout(1000 * 30);
			ftpClient.enterLocalPassiveMode();// 开启被动模式
			ftpClient.login(user, password);

			if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
				ftpClient.disconnect();
				return false;
			}

			// 队列设置
			queFilePath = "data\\" + this.server + ".que";
			bufFilePath = "data\\" + this.server + ".buf";

			startGetList();// 开始遍历
		} catch (java.net.SocketTimeoutException e1) {
			errMsg = ftpClient.getReplyString();
			errCode = ftpClient.getReplyCode();
			result = false;
			setArrToFile(dirQueue, queFilePath);// 保存队列
			setArrToFile(fileList, bufFilePath);// 保存缓存
			cn.imgdpu.util.CatException.getMethod().catException(e1, "连接超时");
		} catch (Exception e) {
			errMsg = ftpClient.getReplyString();
			errCode = ftpClient.getReplyCode();
			result = false;
			setArrToFile(dirQueue, queFilePath);// 保存队列
			setArrToFile(fileList, bufFilePath);// 保存缓存
			cn.imgdpu.util.CatException.getMethod().catException(e, "未知异常");
		} finally {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException ioe) {
					cn.imgdpu.util.CatException.getMethod().catException(ioe, "IO异常");
				}
			}
		}

		return result;
	}

	// 开始遍历文件
	public void startGetList() throws Exception {
		fileList = new ArrayList<String>();
		dirQueue = getQueue(queFilePath);// 得到队列
		if (dirQueue.size() != 0) {
			cn.imgdpu.GSAGUI.setStatusAsyn("载入上次缓存文件...");
			fileList = getFileToArr(bufFilePath);// 得到缓存
			fileCount = fileList.size() / 4;
			dirPre = dirQueue.get(0);
		} else {
			dirQueue.add("/");
		}
		String dirNext;
		while (dirQueue.size() != 0 && !cancel) {// 当队列不为空且不终止
			if (dirPre != null)
				dirQueue.remove(0);
			if (dirQueue.size() > 0) {
				dirNext = dirQueue.get(0);
				listFile(dirNext);
				dirPre = dirNext;
			}
		}
		if (!cancel) {
			delFileList.add(queFilePath);// 添加进删除队列
			delFileList.add(bufFilePath);
		} else {
			setArrToFile(dirQueue, queFilePath);// 保存队列
			setArrToFile(fileList, bufFilePath);// 保存缓存
		}
	}

	// 递归遍历FTP目录
	public ArrayList<String> listFile(String dir) throws Exception {
		cn.imgdpu.GSAGUI.setStatusAsyn(this.server + ":目录(" + fileCount + ") " + dir);
		if (this.isInterrupted()) {// 中断
			cancel = true;
			setArrToFile(dirQueue, queFilePath);// 保存队列
			setArrToFile(fileList, bufFilePath);// 保存缓存
			return null;
		}

		FTPFile[] myftpFiles = null;
		try {
			myftpFiles = ftpClient.listFiles(dir);
		} catch (java.net.BindException e1) {
			cn.imgdpu.util.CatException.getMethod().catException(e1, "未知异常");
			try {
				Thread.sleep(2000);
				myftpFiles = ftpClient.listFiles(dir);
			} catch (InterruptedException e2) {
				cn.imgdpu.util.CatException.getMethod().catException(e2, "线程中断异常");
			}
		}
		for (FTPFile ftpFile : myftpFiles) {

			if (!ftpFile.getName().equals("..") && !ftpFile.getName().equals(".") && ftpFile.isDirectory()) {
				// 如果是目录
				String dirNext = dir + ftpFile.getName() + "/";
				if (dirNext.contains("/xxxxxxx/")) {

				} else {
					addQueue(dirQueue, dirNext);
				}
			} else if (ftpFile.isFile()) {// 如果是文件
				StringBuffer buf = new StringBuffer();
				if (!this.user.equals("anonymous")) {
					buf.append("ftp://").append(this.user).append(":").append(this.password).append("@");
				} else
					buf.append("ftp://");
				fileCount++;
				buf.append(this.server).append(dir).append(ftpFile.getName());
				fileList.add((ftpFile.getName()));
				fileList.add((buf.toString()));
				fileList.add(GeneralMethod.getGeneralMethod().getExtension(ftpFile.getName()));
				fileList.add(String.valueOf(ftpFile.getSize()));

			}
		}
		return fileList;
	}

}

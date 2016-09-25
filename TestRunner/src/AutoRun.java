import java.io.IOException;
import java.io.InputStream;

public class AutoRun 
{
	public static void main(String args[]) throws IOException
	{
		//调用脚本完成xml文件复制和重命名
//		String cmdLocation = "F:\\pipe_request\\GenerateXmlFile.bat";
//		String templateXmlPath = "F:\\pipe_request\\template.xml";
//		int concurrenceNum = 10;
		AutoRun autoRun = new AutoRun();
//		autoRun.createDestXmlFiles(cmdLocation,templateXmlPath,concurrenceNum,"hello");
		autoRun.clearAllTmpFiles("F:\\pipe_request\\ClearTmpFiles.bat", "F:\\pipe_request");
		System.out.println("helloworld");
	}
	
	public void runBat(String cmd)
	{
        try {
            Process ps = Runtime.getRuntime().exec(cmd);
            InputStream in = ps.getInputStream();
            while (in.read() != -1);
            in.close();
            ps.waitFor();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
	}
	
	public void createDestXmlFiles(String locationCmd,String xmlPath,int concurrenceNum,String baseName)
	{
		String cmd = "cmd /c start /b " + locationCmd + " " + xmlPath + " " + concurrenceNum
				+ " " + baseName;
		runBat(cmd);
	}
	
	public void clearAllTmpFiles(String locationCmd,String dirName)
	{
		String cmd = "cmd /c start /b " + locationCmd + " " + dirName;
		runBat(cmd);
	}
}

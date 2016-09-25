import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
public class XmlParser 
{
	private boolean doc2XmlFile(Document document, String xmlFileName) 
	{
       boolean flag = true;
        try {
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();
           
            // transformer.setOutputProperty(OutputKeys.ENCODING, "GB2312");
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(xmlFileName));
            transformer.transform(source, result);
        } catch (Exception ex) {
            flag = false;
            ex.printStackTrace();
        }
        return flag;
	}
	
	//创建xml文档对象
	private Document load(String xmlFileName) {
        Document document = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(new File(xmlFileName));
            document.normalize();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return document;
    }
    
    public void xmlUpdateIndex(String xmlFileName,String index, int count, String value) 
    {
    	//载入xml文件
    	String xmlFilePath = xmlFileName.replaceAll("\\\\","/");
        Document document = load(xmlFilePath);
        
        NodeList sonlist = document.getElementsByTagName(index);
        sonlist.item(count).setTextContent(value);
        //保存更改到xml文件
        doc2XmlFile(document, xmlFilePath);
    }
    
    public String[] GetAllXmlFileFromPath(String path)
    {
    	String dirPath = path.replaceAll("\\\\","/");
    	File file=new File(dirPath);
    	File[] tempList = file.listFiles();
    	if(tempList.length > 0)
    	{
    		String[] xmlFileNameList = new String[tempList.length];
    		for (int i = 0; i < tempList.length; i++) 
    		{
    			if (tempList[i].isFile()) 
    			{
    				xmlFileNameList[i] = tempList[i].getPath();
    			}
    		}
    		return xmlFileNameList;
    	}
    	return null;
    }
    
	public void writeCsv(String csvPath,String[] xmlNameList)throws IOException
	{
		if(csvPath.isEmpty() || xmlNameList.length == 0)
			return;
		File file = new File(csvPath);
		FileOutputStream out = new FileOutputStream(file);
		OutputStreamWriter osw = new OutputStreamWriter(out, "UTF8");
		BufferedWriter bw = new BufferedWriter(osw);
		
		for(int i=0;i<xmlNameList.length;++i)
		{
			bw.write(xmlNameList[i] + "\r\n");
		}
		bw.close();
		osw.close();
		out.close();
	}
	
	public static void main(String args[]) throws IOException
	{
//		XmlParser xmlParser = new XmlParser();
//		xmlParser.xmlUpdateIndex("D:\\JavaProj\\TestRunner\\message.xml", "status", 0,"1000");
		XmlParser xmlParser = new XmlParser();
		String xmlScanPath = "E:\\Jmeter\\test_xml";
		String[] xmlFileNameList = xmlParser.GetAllXmlFileFromPath(xmlScanPath);
		if(null != xmlFileNameList)
		{
//			xmlParser.writeCsv("f:/pipe_request/import.csv", xmlFileNameList);
		}
	}
}


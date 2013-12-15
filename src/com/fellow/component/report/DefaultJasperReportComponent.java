package com.fellow.component.report;

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.util.JRLoader;

public class DefaultJasperReportComponent implements JasperReportComponent{
    private Log logger = LogFactory.getLog(DefaultJasperReportComponent.class);

    @Override
	public JasperReport getJasperReport(InputStream jasper) throws JRException {
		return (JasperReport)JRLoader.loadObject(jasper);
	}

    @Override
	public JasperPrint getJasperPrint(
			JasperReport jasperReport, Map<String, Object> parameters, Connection connection
			) throws JRException{
		return JasperFillManager.fillReport(jasperReport, parameters, connection);
	}

	/**
	 * 
	 * 导出单个报表为rtf或word
	 * 
	 * @param JasperPrint,OutputStream @
	 * 页面设置response.setContentType("application/msword"); @
	 * 页面设置response.setHeader("Content-disposition","attachment;filename=fileName.doc"); @
	 * 页面设置response.setContentLength(bytes.length);
	 * 
	 */
    @Override
	public void exportToRTF(JasperPrint jasperPrint,OutputStream out) throws JRException{
	    JRRtfExporter rtfExporter = new JRRtfExporter();
	    rtfExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
	    rtfExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);

        rtfExporter.exportReport();
        logger.debug("Genertate the RTF report ok! :" + out);
	}
	
	/**
	 * 
	 * 导出单个报表为PDF
	 * 
	 * @param JasperPrint,OutputStream @
	 * 页面设置response.setContentType("application/pdf"); @
	 * 页面设置response.setHeader("Content-disposition","attachment;filename=fileName.pdf"); @
	 * 页面设置response.setContentLength(bytes.length);
	 * 
	 */
    @Override
	public void exportToPDF(JasperPrint jasperPrint,OutputStream out) throws JRException{
	
	   JRPdfExporter pdfExporter = new JRPdfExporter();
	   pdfExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
	   pdfExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);

       pdfExporter.exportReport();
       logger.debug("Genertate the PDF report ok! :" + out);
	}
	 
	
	/**
	 * 
	 * 导出单个报表为XLS
	 * 
	 * @param JasperPrint,OutputStream @
	 * 页面设置response.setContentType("application/vnd.ms-excel"); @
	 * 页面设置response.setHeader("Content-disposition","attachment;filename=fileName.xls"); @
	 * 页面设置response.setContentLength(bytes.length);
	 * 
	 */
    @Override
	public void exportToXLS(JasperPrint jasperPrint,OutputStream out) throws JRException{
	
	   JRXlsExporter xlsExporter = new JRXlsExporter();
	   xlsExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
	   xlsExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
	   xlsExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);

       xlsExporter.exportReport();
       logger.debug("Genertate the XLS report ok! :" + out);
	}
	 
	
	/**
	 * 
	 * 导出单个报表为XLS
	 * 
	 * @param JasperPrint,OutputStream @
	 * 页面设置response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document"); @
	 * 页面设置response.setHeader("Content-disposition","attachment;filename=fileName.docx"); @
	 * 页面设置response.setContentLength(bytes.length);
	 * 
	 */
    @Override
	public void exportToDOCX(JasperPrint jasperPrint,OutputStream out) throws JRException{
	
		JRDocxExporter docxExporter = new JRDocxExporter();
		docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
		docxExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
		
		docxExporter.exportReport();
		logger.debug("Genertate the XLS report ok! :" + out);
	}
	
	 
	
	/**
	 * 
	 * 导出单个报表为HTML
	 * 
	 * @param JasperPrint,OutputStream @ 页面设置response.setContentType("text/html"); @
	 * 页面设置response.setHeader("Content-disposition","attachment;filename=fileName.html"); @
	 * 页面设置response.setContentLength(bytes.length);
	 * 
	 */
    @Override
	public void exportToHTML(JasperPrint jasperPrint,OutputStream out, String imageUri) throws JRException{
	   JRHtmlExporter htmlExporter = new JRHtmlExporter();
	   htmlExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
	   htmlExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
	   htmlExporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.FALSE);
	   htmlExporter.setParameter(JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR, Boolean.FALSE);
	   if(imageUri != null && imageUri.length() > 0){
		   htmlExporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, imageUri);
	   }
       htmlExporter.exportReport();
       logger.debug("Genertate the HTML report ok! :" + out);
	}
	 
	
	/**
	 * 
	 * 导出单个报表为CSV
	 * 
	 * @param JasperPrint,OutputStream
	 * 
	 */
    @Override
	public void exportToCSV(JasperPrint jasperPrint,OutputStream out) throws JRException{
	   JRCsvExporter csvExporter = new JRCsvExporter();
	   csvExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
	   csvExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);

       csvExporter.exportReport();
       logger.debug("Genertate the CSV report ok! :" + out);
	}
	
	 
	
	/**
	 * 
	 * 导出单个报表为TXT
	 * 
	 * @param JasperPrint,OutputStream @ 页面设置response.setContentType("text/html"); @
	 * 页面设置response.setHeader("Content-disposition","attachment;filename=fileName.txt"); @
	 * 页面设置response.setContentLength(bytes.length);
	 * 
	 */
    @Override
	public void exportToTXT(JasperPrint jasperPrint,OutputStream out) throws JRException{
	   JRTextExporter txtExporter = new JRTextExporter();
	   txtExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
	   txtExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
	   txtExporter.setParameter(JRTextExporterParameter.CHARACTER_WIDTH, new Integer(80));
	   txtExporter.setParameter(JRTextExporterParameter.CHARACTER_HEIGHT, new Integer(25));

       txtExporter.exportReport();
       logger.debug("Genertate the TXT report ok! :" + out);
	}
	
	 
	
	/**
	 * 
	 * 导出单个报表为XML
	 * 
	 * @param JasperPrint,OutputStream
	 * 
	 */
    @Override
	public void exportToXML(JasperPrint jasperPrint,OutputStream out) throws JRException{
       JasperExportManager.exportReportToXmlStream(jasperPrint, out);
       logger.debug("Genertate the XML report ok! :" + out);
	}
	
	 
	
	/**
	 * 
	 * 导出报表图片元素
	 * 
	 * @param JasperPrint,OutputStream
	 * 
	 */
    @Override
	public void exportReportImage(JasperPrint jasperPrint,OutputStream out) throws JRException{
		JasperExportManager.exportReportToXmlStream(jasperPrint, out);
	    logger.debug("Genertate the XML report ok! :" + out);
	}
}

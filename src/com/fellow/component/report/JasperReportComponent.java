package com.fellow.component.report;

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

public interface JasperReportComponent {
	JasperReport getJasperReport(InputStream jasper) throws JRException;
	JasperPrint getJasperPrint(JasperReport jasperReport, Map<String, Object> parameters, Connection connection) throws JRException;

	void exportToRTF(JasperPrint jasperPrint,OutputStream out) throws JRException;
	void exportToPDF(JasperPrint jasperPrint,OutputStream out) throws JRException;
	void exportToXLS(JasperPrint jasperPrint,OutputStream out) throws JRException;
	void exportToDOCX(JasperPrint jasperPrint,OutputStream out) throws JRException;
	void exportToHTML(JasperPrint jasperPrint,OutputStream out, String imageUri) throws JRException;
	void exportToCSV(JasperPrint jasperPrint,OutputStream out) throws JRException;
	void exportToTXT(JasperPrint jasperPrint,OutputStream out) throws JRException;
	void exportToXML(JasperPrint jasperPrint,OutputStream out) throws JRException;
	void exportReportImage(JasperPrint jasperPrint,OutputStream out) throws JRException;
}

package com.fellow.web.report;

import java.awt.Dimension;
import java.net.URLEncoder;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JRPrintImage;
import net.sf.jasperreports.engine.JRPropertiesHolder;
import net.sf.jasperreports.engine.JRPropertiesMap;
import net.sf.jasperreports.engine.JRRenderable;
import net.sf.jasperreports.engine.JRWrappingSvgRenderer;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.type.ModeEnum;
import net.sf.jasperreports.engine.util.JRTypeSniffer;

import org.apache.commons.vfs.FileObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fellow.business.EveryCloudBusiness;
import com.fellow.business.ProfileBusiness;
import com.fellow.component.report.JasperReportComponent;
import com.fellow.component.vfs.VFSComponent;
import com.fellow.every.auth.AccessToken;
import com.fellow.every.user.UserAPI;
import com.fellow.every.user.UserInfo;
import com.fellow.profile.RecordBusiness;
import com.fellow.web.filter.LoginUserContextHolder;

@Controller("ReportCtl")
@RequestMapping("/report/report")
public class ReportCtrl {

	public static final String DEFAULT_REPORT_SUBFIX = ".jasper";

	@Resource(name = "vfsComponent")
	private VFSComponent vfsComponent;

	@Resource(name = "jasperReportComponent")
	private JasperReportComponent jasperReportComponent;
	
	@Resource(name="everyCloudBusiness")
	private EveryCloudBusiness everyCloudBusiness;
	
	@Resource(name="profileBusiness")
	private ProfileBusiness profileBusiness;

	@RequestMapping(params = "method=info")
	public ModelAndView info(
	 		HttpServletRequest request, HttpServletResponse response,
	 		String report) throws Exception{
	    
		class ReportParameter{
			private String name;
			private String description;
			private String type;
			private String defaultValue;
			private Map<String, String> properties;
			public String getName() {return name;}
			public void setName(String name) {this.name = name;}
			public String getDescription() {return description;}
			public void setDescription(String description) {this.description = description;}
			public String getType() {return type;}
			public void setType(String type) {this.type = type;}
			public String getDefaultValue() {return defaultValue;}
			public void setDefaultValue(String defaultValue) {this.defaultValue = defaultValue;}
			public Map<String, String> getProperties() {return properties;}
			public void setProperties(Map<String, String> properties) {this.properties = properties;}
		}
		
		JasperReport jasperReport = this.getJasperReport(report);
		
		JRParameter[] parameters = jasperReport.getParameters();
		List<ReportParameter> items = new ArrayList<ReportParameter>();
		for(int i = 0; i < parameters.length; i++){
			if(!parameters[i].isSystemDefined() && parameters[i].isForPrompting()){
				
				//���SESSION_USER_ID
				if("SESSION_USER_ID".equalsIgnoreCase(parameters[i].getName())) continue;
				
				ReportParameter p = new ReportParameter();
				p.setName(parameters[i].getName());
				p.setDescription(parameters[i].getDescription());
				p.setType(parameters[i].getValueClassName());
				if(parameters[i].getDefaultValueExpression() != null){
					p.setDefaultValue(parameters[i].getDefaultValueExpression().getText());
				}
				p.setProperties(new HashMap<String, String>());
				
				String[] names = parameters[i].getPropertiesMap().getPropertyNames();
				for(int j = 0; j < names.length; j++){
					p.getProperties().put(names[j], parameters[i].getPropertiesMap().getProperty(names[j]));
				}
				items.add(p);
			}
		}

		ModelAndView modelAndView = new ModelAndView("jsonView");
		modelAndView.addObject("name", jasperReport.getName());
		modelAndView.addObject("parameters", items);
		return modelAndView;
	}
	

	@RequestMapping(params = "method=html")
	public ModelAndView html(
	 		HttpServletRequest request, HttpServletResponse response,
	 		String report) throws Exception{

		String imageUrl = "report.do?method=image";
		
		JasperPrint jasperPrint = this.getJasperPrint(request, response, report);
		
		Enumeration<String> names = request.getParameterNames();
		while(names.hasMoreElements()){
			String key = names.nextElement();
			if("method".equalsIgnoreCase(key)){
				continue;
			}
			
			String value = request.getParameter(key.toString());
			imageUrl += "&" + key + "=" + value;
		}
		imageUrl += "&image=";

		response.setContentType("text/html"); 
		
		//��ɵ����ļ��������response
		jasperReportComponent.exportToHTML(
				jasperPrint, response.getOutputStream(), imageUrl);
		
		return null;
	}
	

	@RequestMapping(params = "method=image")
	public ModelAndView image(
	 		HttpServletRequest request, HttpServletResponse response,
	 		String report, String image) throws Exception{
		
		JasperPrint jasperPrint = this.getJasperPrint(request, response, report);

		ArrayList<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
		jasperPrintList.add(jasperPrint);
		JRPrintImage printImage = JRHtmlExporter.getImage(jasperPrintList, image);
		

		JRRenderable renderer = printImage.getRenderer();
		if (renderer.getType() == JRRenderable.TYPE_SVG){
			renderer = 
				new JRWrappingSvgRenderer(
					renderer, 
					new Dimension(printImage.getWidth(), printImage.getHeight()),
					ModeEnum.OPAQUE == printImage.getModeValue() ? printImage.getBackcolor() : null
					);
		}

		byte[] imageData = null;
		String imageMimeType = JRTypeSniffer.getImageMimeType(renderer.getImageType());

		try {
			imageData = renderer.getImageData();
		} catch (Exception e) {
			throw new ServletException(e);
		} 

		if (imageData != null && imageData.length > 0){
			if (imageMimeType != null) {
				response.setHeader("Content-Type", imageMimeType);
			}
			response.setContentLength(imageData.length);
			ServletOutputStream ouputStream = response.getOutputStream();
			ouputStream.write(imageData, 0, imageData.length);
			ouputStream.flush();
			ouputStream.close();
		}

		return null;
	}
	

	@RequestMapping(params = "method=docx")
	public ModelAndView docx(
	 		HttpServletRequest request, HttpServletResponse response,
	 		String report) throws Exception{
		
		JasperPrint jasperPrint = this.getJasperPrint(request, response, report);
		
		//����response����
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(jasperPrint.getName(), "UTF-8") + ".docx");
		response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document"); 

		//��ɵ����ļ��������response
		jasperReportComponent.exportToDOCX(jasperPrint, response.getOutputStream());
		
		return null;
	}
	

	@RequestMapping(params = "method=excel")
	public ModelAndView excel(
	 		HttpServletRequest request, HttpServletResponse response,
	 		String report) throws Exception{
		
		JasperPrint jasperPrint = this.getJasperPrint(request, response, report);

		//����response����
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(jasperPrint.getName(), "UTF-8") + ".xls");
		response.setContentType("application/vnd.ms-excel");

		//��ɵ����ļ��������response
		jasperReportComponent.exportToXLS(jasperPrint, response.getOutputStream());
		
		return null;
	}
	

	@RequestMapping(params = "method=pdf")
	public ModelAndView pdf(
	 		HttpServletRequest request, HttpServletResponse response,
	 		String report) throws Exception{
		
		JasperPrint jasperPrint = this.getJasperPrint(request, response, report);

		//����response����
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(jasperPrint.getName(), "UTF-8") + ".pdf");
		response.setContentType("application/pdf"); 

		//��ɵ����ļ��������response
		jasperReportComponent.exportToPDF(jasperPrint, response.getOutputStream());
		
		return null;
	}
	

	@RequestMapping(params = "method=rtf")
	public ModelAndView rtf(
	 		HttpServletRequest request, HttpServletResponse response,
	 		String report) throws Exception{
		
		JasperPrint jasperPrint = this.getJasperPrint(request, response, report);

		//����response����
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(jasperPrint.getName(), "UTF-8") + ".rtf");
		response.setContentType("application/rtf"); 
		
		//��ɵ����ļ��������response
		jasperReportComponent.exportToRTF(jasperPrint, response.getOutputStream());
		
		return null;
	}


	
	protected JasperReport getJasperReport(String report) throws Exception{
		
		FileObject dir = vfsComponent.getFileSystemManager().resolveFile("report://");
		if (!dir.exists()) {
			throw new java.io.FileNotFoundException("report:// not found: " + dir);
		}
		FileObject file = dir.resolveFile(report + DEFAULT_REPORT_SUBFIX);
		if (!file.exists()) {
			throw new java.io.FileNotFoundException("report not found: " + file);
		}
		
		JasperReport jasperReport = jasperReportComponent.getJasperReport(file.getContent().getInputStream());

		//�ر��ļ�
		file.close();
		
		return jasperReport;
	}
	
	protected JasperPrint getJasperPrint(
			HttpServletRequest request, HttpServletResponse response,
			String report) throws Exception{

		String provider = LoginUserContextHolder.getProvider();
	    UserInfo user = LoginUserContextHolder.getUserInfo();
	    if(user == null){
	      throw new RuntimeException("Session User is null");
	    }
	    
	    String userId = user.getId();

	    DataSource datasource = profileBusiness.getDataSource(provider, userId);
		
		JasperReport jasperReport = this.getJasperReport(report);
		
		//������ݿ�����Connection����
		Connection connection = datasource.getConnection();

		Map<String, Object> parameters = this.getParametersFromRequest(request, jasperReport);
		
		System.out.println("Parameters: " + parameters);
		JasperPrint jasperPrint = jasperReportComponent.getJasperPrint(jasperReport, parameters, connection);

		//�ر���ݿ�����
		connection.close();
		
		return jasperPrint;
	}
	
	

	protected Map<String, Object> getParametersFromRequest(
			HttpServletRequest request, JasperReport jasperReport
			)throws Exception{
	    
		Map<String, Object> parameters = new HashMap<String, Object>();
		try {
			for(int i = 0; i < jasperReport.getParameters().length; i++){
				JRParameter paramDef = jasperReport.getParameters()[i];
				String name = paramDef.getName();
				Class<?> clazz = paramDef.getValueClass();
				String valueStr = request.getParameter(name);
				Object value = null;
				if(paramDef.isSystemDefined()){
					continue;
				}
				
				if(valueStr == null || valueStr.length() == 0){
					continue;
				} else if(clazz == java.lang.String.class){
					//value = new String(valueStr.getBytes("iso-8859-1"), "UTF-8");
					value = valueStr;
				} else if(clazz == java.lang.Boolean.class){
					value = java.lang.Boolean.valueOf(valueStr);
				} else if(clazz == java.lang.Integer.class){
					value = java.lang.Integer.valueOf(valueStr);
				} else if(clazz == java.lang.Double.class){
					value = java.lang.Double.valueOf(valueStr);
				} else if(clazz == java.lang.Number.class){
					value = java.lang.Double.valueOf(valueStr);
				} else if(clazz == java.lang.Float.class){
					value = java.lang.Float.valueOf(valueStr);
				} else if(clazz == java.lang.Long.class){
					value = java.lang.Long.valueOf(valueStr);
				} else if(clazz == java.lang.Short.class){
					value = java.lang.Short.valueOf(valueStr);
				} else if(clazz == java.lang.Byte.class){
					value = java.lang.Byte.valueOf(valueStr);
				} else if(clazz == java.math.BigDecimal.class){
					value = java.math.BigDecimal.valueOf(java.lang.Double.parseDouble(valueStr));
				} else if(clazz == java.util.Date.class){
					value =  new SimpleDateFormat("MM/dd/yyyy").parse(valueStr);
				} else if(clazz == java.sql.Time.class){
					value = new java.sql.Time(new SimpleDateFormat("MM/dd/yyyy").parse(valueStr).getTime());
				} else if(clazz == java.sql.Timestamp.class){
					value = new java.sql.Timestamp(new SimpleDateFormat("MM/dd/yyyy").parse(valueStr).getTime());
				} else {
					value = valueStr;
				}
				parameters.put(name, value);
			}
			
		} catch (Exception e) {
			throw new ServletException(e);
		} 
		return parameters;
	}
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reports;

//imported files
import db.DBConnection;

//imported libraries
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;

public class ReportGenerator 
{
    //method to generate Department-Course Enrollment Report
    public static void generateDepartmentCourseEnrollmentReport() throws JRException 
    {
        generateAndShowReport(
            "/reports/DepartmentCourseEnrollmentSummary.jrxml",
            "Department-Course Enrollment Summary",
            "DepartmentCourseEnrollmentSummary.pdf"
        );
    }

    //method to generate Student Enrollment and Demographics Report
    public static void generateStudentEnrollmentAndDemographicsReport() throws JRException 
    {
        generateAndShowReport(
            "/reports/StudentEnrollmentAndDemographicsReport.jrxml",
            "Student Enrollment and Demographics Report",
            "StudentEnrollmentAndDemographicsReport.pdf"
        );
    }

    //method to generate Department Financial Overview Report
    public static void generateDepartmentFinancialOverviewReport() throws JRException 
    {
        generateAndShowReport(
            "/reports/DepartmentFinancialOverview.jrxml",
            "Department Financial Overview",
            "DepartmentFinancialOverview.pdf"
        );
    }

    // ----- Common Method -----
    private static void generateAndShowReport(String jrxmlPath, String reportTitle, String outputFileName) throws JRException 
    {
        Connection conn = DBConnection.getInstance().getConnection();

        InputStream jrxmlStream = ReportGenerator.class.getResourceAsStream(jrxmlPath);
        if (jrxmlStream == null) 
        {
            throw new JRException("Report file not found: " + jrxmlPath);
        }

        //compile and fill the report
        JasperReport report = JasperCompileManager.compileReport(jrxmlStream);
        JasperPrint print = JasperFillManager.fillReport(report, null, conn);

        //show in JasperViewer
        JasperViewer viewer = new JasperViewer(print, false);
        viewer.setTitle(reportTitle);
        viewer.setVisible(true);
        
        // Export to PDF
        File outputDir = new File("/reports/outputs");
        if (!outputDir.exists()) outputDir.mkdirs();

        String outputPath = "/reports/outputs/" + outputFileName;
        JasperExportManager.exportReportToPdfFile(print, outputPath);
        System.out.println("PDF exported to: " + outputPath);
    }
}

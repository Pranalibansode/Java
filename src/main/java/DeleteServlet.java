
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/deleteurl")
public class DeleteServlet extends HttpServlet {
    private final static String query = "delete from user where id = ?";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //get PrintWriter
        PrintWriter pw = res.getWriter();
        //set content type
        res.setContentType("text/html");
        //link the bootstrap
        pw.println("<link rel='stylesheet' href='css/bootstrap.css'></link>");
        pw.println("<div>\r\n"
        		+ "  <nav class=\"navbar navbar-expand-lg\" style=\"background-color: #297290;\">\r\n"
        		+ "    <h2 class=\"text-white card-header\" style=\"background-color: #297290;\">Delete Data</h2>\r\n"
        		+ "  </nav>\r\n"
        		+ "</div>");
        //get the values
        int id = Integer.parseInt(req.getParameter("id"));
        //load the JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(Exception e) {
            e.printStackTrace();
        }
        //generate the connection
        try(Connection con = DriverManager.getConnection("jdbc:mysql:///userdb","root","Pranali@2410");
                PreparedStatement ps = con.prepareStatement(query);){
            //set the values
            ps.setInt(1, id);
            //execute the query
            int count = ps.executeUpdate();
            pw.println("<div class='card border-white' style='margin:auto;width:300px;margin-top:100px'>");
            if(count==1) {
                pw.println("<h2 class='text-success text-center'>Record Deleted Successfully</h2><br><br>");
            }else {
                pw.println("<h2 class='text-danger text-center'>Record Not Deleted</h2><br><br>");
            }
        }catch(SQLException se) {
            pw.println("<h2 class='text-danger text-center'>"+se.getMessage()+"</h2>");
            se.printStackTrace();
        }catch(Exception e) {
            e.printStackTrace();
        }
        pw.println("<a href='home.html'><button class='btn btn-outline-success text-center' style='width:300px'>Home</button></a>");
        pw.println("&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;");
        pw.println("<a href='showdata'><button class='btn btn-outline-success' style='width:300px'>Show User</button></a>");
        pw.println("</div>");
   
        pw.close();
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req,res);
    }
}

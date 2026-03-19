package com.hospital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/db")
public class DatabaseController {

    @Autowired
    private DataSource dataSource;

    @GetMapping("/console")
    public String getDatabaseConsole(Model model) {
        try {
            // Get database metadata
            Connection conn = dataSource.getConnection();
            DatabaseMetaData metadata = conn.getMetaData();
            
            model.addAttribute("dbName", metadata.getDatabaseProductName());
            model.addAttribute("dbVersion", metadata.getDatabaseProductVersion());
            model.addAttribute("driverName", metadata.getDriverName());
            model.addAttribute("driverVersion", metadata.getDriverVersion());
            model.addAttribute("url", metadata.getURL());
            
            // Get table information
            List<Map<String, Object>> tables = getTableInfo(metadata, conn);
            model.addAttribute("tables", tables);
            model.addAttribute("tableCount", tables.size());
            
            conn.close();
        } catch (Exception e) {
            model.addAttribute("error", "Error retrieving database information: " + e.getMessage());
        }
        
        return "db/console";
    }

    @PostMapping("/execute")
    @ResponseBody
    public Map<String, Object> executeQuery(@RequestParam String query) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            if (query == null || query.trim().isEmpty()) {
                response.put("success", false);
                response.put("message", "Query cannot be empty");
                return response;
            }

            Connection conn = dataSource.getConnection();
            Statement stmt = conn.createStatement();
            
            // Prevent DELETE and DROP operations
            String upperQuery = query.toUpperCase().trim();
            if (upperQuery.startsWith("DELETE") || upperQuery.startsWith("DROP") || 
                upperQuery.startsWith("TRUNCATE") || upperQuery.startsWith("ALTER")) {
                response.put("success", false);
                response.put("message", "Operations like DELETE, DROP, TRUNCATE, or ALTER are not allowed for safety");
                conn.close();
                return response;
            }

            if (upperQuery.startsWith("SELECT")) {
                ResultSet rs = stmt.executeQuery(query);
                List<Map<String, Object>> results = new ArrayList<>();
                List<String> columns = new ArrayList<>();
                
                int colCount = rs.getMetaData().getColumnCount();
                for (int i = 1; i <= colCount; i++) {
                    columns.add(rs.getMetaData().getColumnName(i));
                }
                
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    for (int i = 1; i <= colCount; i++) {
                        row.put(columns.get(i - 1), rs.getObject(i));
                    }
                    results.add(row);
                }
                
                response.put("success", true);
                response.put("data", results);
                response.put("columns", columns);
                response.put("rowCount", results.size());
                
                rs.close();
            } else if (upperQuery.startsWith("INSERT") || upperQuery.startsWith("UPDATE")) {
                int rowsAffected = stmt.executeUpdate(query);
                response.put("success", true);
                response.put("message", rowsAffected + " row(s) affected");
                response.put("rowsAffected", rowsAffected);
            } else {
                stmt.execute(query);
                response.put("success", true);
                response.put("message", "Query executed successfully");
            }
            
            stmt.close();
            conn.close();
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error executing query: " + e.getMessage());
        }
        
        return response;
    }

    @GetMapping("/table-stats")
    @ResponseBody
    public Map<String, Object> getTableStats() {
        Map<String, Object> response = new HashMap<>();
        try {
            Connection conn = dataSource.getConnection();
            Statement stmt = conn.createStatement();
            
            List<Map<String, Object>> stats = new ArrayList<>();
            
            String[] tableNames = {"patients", "doctors", "hospital_staff", "appointments", "users"};
            
            for (String tableName : tableNames) {
                try {
                    ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as count FROM " + tableName);
                    if (rs.next()) {
                        Map<String, Object> stat = new HashMap<>();
                        stat.put("table", tableName);
                        stat.put("count", rs.getInt("count"));
                        stats.add(stat);
                    }
                    rs.close();
                } catch (Exception e) {
                    // Table might not exist
                }
            }
            
            response.put("success", true);
            response.put("data", stats);
            stmt.close();
            conn.close();
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
        }
        
        return response;
    }

    private List<Map<String, Object>> getTableInfo(DatabaseMetaData metadata, Connection conn) {
        List<Map<String, Object>> tables = new ArrayList<>();
        try {
            ResultSet rs = metadata.getTables(null, null, "%", new String[]{"TABLE"});
            while (rs.next()) {
                Map<String, Object> tableInfo = new HashMap<>();
                String tableName = rs.getString("TABLE_NAME");
                tableInfo.put("name", tableName);
                
                // Get row count
                try {
                    Statement stmt = conn.createStatement();
                    ResultSet countRs = stmt.executeQuery("SELECT COUNT(*) as count FROM " + tableName);
                    if (countRs.next()) {
                        tableInfo.put("rows", countRs.getInt("count"));
                    }
                    countRs.close();
                    stmt.close();
                } catch (Exception e) {
                    tableInfo.put("rows", 0);
                }
                
                tables.add(tableInfo);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return tables;
    }

    // H2 Console interceptor endpoints
    @GetMapping("/login")
    public String getH2Login() {
        return "db/login";
    }

    @PostMapping("/login")
    public String handleH2Login() {
        return "redirect:/hospital/db/console";
    }
}

package org.lsqt.components.log.db;

import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Arrays;

import javax.sql.DataSource;


/**
 * 数据库层操作类
 * @author Sky
 *
 */
public class SqlExecutor {
	private DataSource dataSource;
	private volatile boolean isBroken = false;
	
	private SqlExecutor(){ 
		
	}
	
	public SqlExecutor(DataSource dataSource){
		this.dataSource=dataSource;
	}
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	
    protected Connection prepareConnection() throws SQLException {
        if (this.dataSource == null) {
            throw new SQLException("SqlExecutor requires a DataSource to be invoked in this way");
        }
        return dataSource.getConnection();
    }
    

    protected PreparedStatement prepareStatement(Connection conn, String sql,Object ... paramValues)  throws SQLException {
    	PreparedStatement stmt=conn.prepareStatement(sql);

        ParameterMetaData pmd = null;
        if (!isBroken) {
            pmd = stmt.getParameterMetaData();
            int stmtCount = pmd.getParameterCount();
            int paramsCount = paramValues == null ? 0 : paramValues.length;

            if (stmtCount != paramsCount) {
                throw new SQLException("Wrong number of parameters: expected " + stmtCount + ", was given " + paramsCount);
            }
        }

       //console.info(" ---- "+sql+"  "+Arrays.asList(paramValues));

        for (int i = 0; i < paramValues.length; i++) {
            if (paramValues[i] != null) {
                stmt.setObject(i + 1, paramValues[i]);
            } else {
            	int sqlType = Types.VARCHAR;
                if (!isBroken) {
                    try {
                        sqlType = pmd.getParameterType(i + 1);
                    } catch (SQLException e) {
                    	isBroken = true;
                    }
                }
                stmt.setNull(i + 1, sqlType);
            }
        }
       return stmt;
    }
    
    public void close(Connection con, Statement stmt, ResultSet rs) {
		try {
			if(rs != null)rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if(stmt != null)stmt.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}finally{
				try {
					if(con != null)con.close();
				} catch (SQLException ec) {
					ec.printStackTrace();
				}
			}
		}
    }
    
	public int executeUpdate(String sql,Object... paramValues){
		Connection con=null; 
		PreparedStatement stmt=null; 
		ResultSet rs=null;
		try{
			con=prepareConnection();
			stmt=prepareStatement(con,sql,paramValues);
			return stmt.executeUpdate();
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			close(con,stmt,rs);
		}
		return 0;
	}
}

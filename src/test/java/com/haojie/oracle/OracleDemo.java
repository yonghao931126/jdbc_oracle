package com.haojie.oracle;

import oracle.jdbc.OracleTypes;
import oracle.jdbc.oracore.OracleType;
import org.junit.Test;

import java.sql.*;

public class OracleDemo {


    @Test
    public void testOracle() throws Exception {
        //准备数据库驱动
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.80.6:1521:orcl", "SCOTT", "tiger");
        PreparedStatement pstm = conn.prepareStatement("select * from emp where empno=?");
        pstm.setObject(1, 7788);
        ResultSet set = pstm.executeQuery();
        while (set.next()) {
            System.out.println(set.getString("ename"));
        }
        set.close();
        pstm.close();
        conn.close();
    }

    /**
     * java 调用存储过程
     *      {? = call <procedure-name> [(<arg1>,<arg2>, ...)]}  调用存储函数
     *      {call <procedure-name> [<arg1>,<arg2>, ...]}        调用存储过程
     * @throws Exception
     */
    @Test
    public void testOracleProcedure() throws Exception {
        //准备数据库驱动
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.80.6:1521:orcl", "iversion", "iversion");
        CallableStatement pstm = conn.prepareCall("{call p_yearsal(?,?)}");
        pstm.setObject(1, 7788);
        pstm.registerOutParameter(2, OracleTypes.NUMBER);
        pstm.execute();
        System.out.println(pstm.getObject(2 ));
        pstm.close();
        conn.close();
    }

    /**
     * java 调用存储过程
     *      {? = call <procedure-name> [(<arg1>,<arg2>, ...)]}  调用存储函数
     *      {call <procedure-name> [<arg1>,<arg2>, ...]}        调用存储过程
     * @throws Exception
     */
    @Test
    public void testOraclefunction() throws Exception {
        //准备数据库驱动
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.80.6:1521:orcl", "iversion", "iversion");
        CallableStatement pstm = conn.prepareCall("{?= call f_yearsal(?)}");
        pstm.setObject(2, 7788);  //第二个问号的赋值
        pstm.registerOutParameter(1, OracleTypes.NUMBER); //第一个文号的赋值
        pstm.execute();
        System.out.println(pstm.getObject(1 ));  //输出的就是第一个
        pstm.close();
        conn.close();
    }
}

package ST.util;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/*
    创建一个工具类，每次要获取SqlSessionFactory对象时，就调用这个工具类里面的方法，
但是工厂（sqlSessionFactory）只能创建一次（如果创建多次增加了负担），所以不能重复创
建，采用静态代码块的方法
 */
public class SqlSessionFactoryUtils {
    private static SqlSessionFactory sqlSessionFactory;
    static {
        /*
         *静态代码块会随着类的加载而自动执行，且只执行一次
         */
        try {
            // 加载mybatis核心配置，创建连接池工厂
            String resource = "mybatis.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static SqlSessionFactory getSqlSessionFactory(){
        return sqlSessionFactory;
    }
}

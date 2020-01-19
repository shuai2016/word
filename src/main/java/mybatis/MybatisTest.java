package mybatis;

import mybatis.config.MybatisConfig;
import mybatis.mapper.BlogMapper;
import mybatis.model.Blog;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;

/**
 * Test
 *
 * @author shuai
 * @date 2020/1/7
 */
public class MybatisTest {
    public static void main(String[] args) {
        try (SqlSession session = MybatisConfig.getSqlSessionFactoryWithXml().openSession()) {
            //Blog blog = session.selectOne("mybatis.mapper.BlogMapper.selectBlog", 101);
            BlogMapper mapper = session.getMapper(BlogMapper.class);
            Blog blog = mapper.selectBlog(101);
            System.out.println(blog.getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

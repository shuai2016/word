package mybatis.mapper;

import mybatis.model.Blog;
import org.apache.ibatis.annotations.Select;

/**
 * BlogMapper
 *
 * @author shuai
 * @date 2020/1/7
 */
public interface BlogMapper {
    //@Select("SELECT * FROM blog WHERE id = #{id}")
    Blog selectBlog(int id);
}

package individual.zh.springai.service.impl;

import individual.zh.springai.entity.po.Course;
import individual.zh.springai.mapper.CourseMapper;
import individual.zh.springai.service.ICourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 学科表 服务实现类
 * </p>
 *
 * @author zh
 * @since 2026-04-29
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {

}

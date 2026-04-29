package individual.zh.springai.service.impl;

import individual.zh.springai.entity.po.School;
import individual.zh.springai.mapper.SchoolMapper;
import individual.zh.springai.service.ISchoolService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 校区表 服务实现类
 * </p>
 *
 * @author zh
 * @since 2026-04-29
 */
@Service
public class SchoolServiceImpl extends ServiceImpl<SchoolMapper, School> implements ISchoolService {

}

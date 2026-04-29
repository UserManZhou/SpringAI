/**
 * ============================================================
 * 版权： 广州市新维数据科技有限公司新架构产品部所有(c) 2026
 * 文件：individual.zh.springai.tools
 * 所含类: CourseTools
 * 文件作用描述 TODO
 * 修改记录：
 * 日期                                      作者         版本     内容
 * =============================================================
 * 2026/4/29  22:02      zh     v1.0.0   新建
 * =============================================================
 */

package individual.zh.springai.tools;

import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import individual.zh.springai.entity.po.Course;
import individual.zh.springai.entity.po.CourseReservation;
import individual.zh.springai.entity.po.School;
import individual.zh.springai.entity.query.CourseQuery;
import individual.zh.springai.service.ICourseReservationService;
import individual.zh.springai.service.ICourseService;
import individual.zh.springai.service.ISchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>Titile:CourseTools</p >
 * <p>ProjectName: </p >
 * <p>Description:TODO()  </p >
 * <p>Copyright: Copyright (c) 2023</p >
 * <p>Company: 新维数据 </p >
 *
 * @BelongsProject: SpringAI
 * @BelongsPackage: individual.zh.springai.tools
 * @Author: zh
 * @CreateTime: 2026/4/29  22:02
 * @Description: TODO
 * @Version: 1.0
 */
@RequiredArgsConstructor
@Component
public class CourseTools {

    private final ICourseService iCourseService;

    private final ISchoolService schoolService;

    private ICourseReservationService courseReservationService;

    /**
     * 查询课程信息
     *
     * @param query
     * @return {@link List< Course>}
     * @throws Exception
     * @title queryCourse
     * @description
     * @author zh
     * @date 2026-04-29 22:06
     *
     **/
    @Tool(description = "根据条件查询课程信息")
    public List<Course> queryCourse(@ToolParam(description = "查询的条件") CourseQuery query) {
        if (query == null) {
            return List.of();
        }
        QueryChainWrapper<Course> le = iCourseService.query()
                .eq(query.getType() != null, "type", query.getType())
                .le(query.getEdu() != null, "edu", query.getEdu());
        if (query.getSorts() != null && !query.getSorts().isEmpty()) {
            for (CourseQuery.Sort sort : query.getSorts()) {
                le.orderBy(true, sort.getAsc(), sort.getField());
            }
        }
        return le.list();
    }

    /**
     * 查询学校信息
     *
     * @param
     * @return {@link List< School>}
     * @throws Exception
     * @title querySchool
     * @description
     * @author zh
     * @date 2026-04-29 22:13
     *
     **/
    @Tool(description = "查询所有校区")
    public List<School> querySchool() {
        return schoolService.list();
    }


    /**
     * 生成预约单
     *
     * @param course
     * @param school
     * @param studentName
     * @param phone
     * @param remark
     * @return {@link Integer}
     * @throws Exception
     * @title createCourseReservation
     * @description
     * @author zh
     * @date 2026-04-29 22:22
     *
     **/
    @Tool(description = "生成预约单,返回预约单号")
    public Integer createCourseReservation(@ToolParam(description = "预约课程") String course,
                                           @ToolParam(description = "预约小区") String school,
                                           @ToolParam(description = "学生姓名") String studentName,
                                           @ToolParam(description = "联系点话") String phone,
                                           @ToolParam(description = "备注", required = false) String remark) {
        CourseReservation courseReservation = new CourseReservation();
        courseReservation.setCourse(course);
        courseReservation.setSchool(school);
        courseReservation.setStudentName(studentName);
        courseReservation.setContactInfo(phone);
        courseReservation.setRemark(remark);
        courseReservationService.save(courseReservation);
        return courseReservation.getId();
    }
}

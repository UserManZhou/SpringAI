/**
 * ============================================================
 * 版权： 广州市新维数据科技有限公司新架构产品部所有(c) 2026
 * 文件：individual.zh.springai.entity.query
 * 所含类: CourseQuery
 * 文件作用描述 TODO
 * 修改记录：
 * 日期                                      作者         版本     内容
 * =============================================================
 * 2026/4/29  22:01      zh     v1.0.0   新建
 * =============================================================
 */

package individual.zh.springai.entity.query;

import lombok.Data;
import org.springframework.ai.tool.annotation.ToolParam;

import java.util.List;

/**
 * <p>Titile:CourseQuery</p >
 * <p>ProjectName: </p >
 * <p>Description:TODO()  </p >
 * <p>Copyright: Copyright (c) 2023</p >
 * <p>Company: 新维数据 </p >
 *
 * @BelongsProject: SpringAI
 * @BelongsPackage: individual.zh.springai.entity.query
 * @Author: zh
 * @CreateTime: 2026/4/29  22:01
 * @Description: TODO
 * @Version: 1.0
 */
@Data
public class CourseQuery {

    @ToolParam(required = false, description = "课程类型：编程、设计、自媒体、其它")
    private String type;

    @ToolParam(required = false, description = "学历要求：0-无、1-初中、2-高中、3-大专、4-本科及本科以上")
    private Integer edu;

    @ToolParam(required = false, description = "排序方式")
    private List<Sort> sorts;

    @Data
    public static class Sort {

        @ToolParam(required = false, description = "排序字段: price或duration")
        private String field;

        @ToolParam(required = false, description = "是否是升序: true/false")
        private Boolean asc;
        
    }

}

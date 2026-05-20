/**
 * ============================================================
 * 版权： 广州市新维数据科技有限公司新架构产品部所有(c) 2026
 * 文件：individual.zh.springai.entity.vo
 * 所含类: Result
 * 文件作用描述 TODO
 * 修改记录：
 * 日期                                      作者         版本     内容
 * =============================================================
 * 2026/5/20  20:33      zh     v1.0.0   新建
 * =============================================================
 */

package individual.zh.springai.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>Titile:Result</p >
 * <p>ProjectName: </p >
 * <p>Description:TODO()  </p >
 * <p>Copyright: Copyright (c) 2023</p >
 * <p>Company: 新维数据 </p >
 *
 * @BelongsProject: SpringAI
 * @BelongsPackage: individual.zh.springai.entity.vo
 * @Author: zh
 * @CreateTime: 2026/5/20  20:33
 * @Description: TODO
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {

    private Integer ok;

    private String msg;

    /**
     * 创建成功结果
     *
     * @param
     * @return {@link Result}
     * @throws Exception
     * @title ok
     * @description
     * @author zh
     * @date 2026-05-20 20:35
     *
     **/
    public static Result ok() {
        return new Result(1, "ok");
    }

    /**
     * 创建失败结果
     *
     * @param msg
     * @return {@link Result}
     * @throws Exception
     * @title fail
     * @description
     * @author zh
     * @date 2026-05-20 20:36
     *
     **/
    public static Result fail(String msg) {
        return new Result(0, msg);
    }

}

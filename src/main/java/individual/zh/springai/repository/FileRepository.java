/**
 * ============================================================
 * 版权： 广州市新维数据科技有限公司新架构产品部所有(c) 2026
 * 文件：individual.zh.springai.repository
 * 所含类: FileRepository
 * 文件作用描述 TODO
 * 修改记录：
 * 日期                                      作者         版本     内容
 * =============================================================
 * 2026/5/20  20:16      zh     v1.0.0   新建
 * =============================================================
 */

package individual.zh.springai.repository;

import org.springframework.core.io.Resource;

import java.io.IOException;

/**
 * <p>Titile:FileRepository</p >
 * <p>ProjectName: </p >
 * <p>Description:TODO()  </p >
 * <p>Copyright: Copyright (c) 2023</p >
 * <p>Company: 新维数据 </p >
 *
 * @BelongsProject: SpringAI
 * @BelongsPackage: individual.zh.springai.repository
 * @Author: zh
 * @CreateTime: 2026/5/20  20:16
 * @Description: TODO
 * @Version: 1.0
 */
public interface FileRepository {

    /**
     * 保存文件
     *
     * @param chatId
     * @param resource
     * @return {@link boolean}
     * @throws Exception
     * @title save
     * @description
     * @author zh
     * @date 2026-05-20 20:16
     *
     **/
    boolean save(String chatId, Resource resource) throws IOException;

    /**
     * 获取文件
     *
     * @param chatId
     * @return {@link Resource}
     * @throws Exception
     * @title getFile
     * @description
     * @author zh
     * @date 2026-05-20 20:17
     *
     **/
    Resource getFile(String chatId);

}

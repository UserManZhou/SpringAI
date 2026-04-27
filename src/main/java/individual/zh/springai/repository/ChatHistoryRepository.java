/**
 * ============================================================
 * 版权： 广州市新维数据科技有限公司新架构产品部所有(c) 2026
 * 文件：individual.zh.springai.repository
 * 所含类: ChatHistoryRepository
 * 文件作用描述 TODO
 * 修改记录：
 * 日期                                      作者         版本     内容
 * =============================================================
 * 2026/4/27  21:24      zh     v1.0.0   新建
 * =============================================================
 */

package individual.zh.springai.repository;

import java.util.List;

/**
 * <p>Titile:ChatHistoryRepository</p >
 * <p>ProjectName: </p >
 * <p>Description:TODO()  </p >
 * <p>Copyright: Copyright (c) 2023</p >
 * <p>Company: 新维数据 </p >
 *
 * @BelongsProject: SpringAI
 * @BelongsPackage: individual.zh.springai.repository
 * @Author: zh
 * @CreateTime: 2026/4/27  21:24
 * @Description: TODO
 * @Version: 1.0
 */
public interface ChatHistoryRepository {

    /**
     * 保存聊天记录
     *
     * @param chatId
     * @param type
     * @return
     * @throws Exception
     * @title save
     * @description
     * @author zh
     * @date 2026-04-27 21:25
     *
     **/
    void save(String chatId, String type);

    /**
     * 删除聊天记录
     *
     * @param chatId
     * @param type
     * @return
     * @throws Exception
     * @title delete
     * @description
     * @author zh
     * @date 2026-04-27 21:26
     *
     **/
    void delete(String chatId, String type);

    /**
     * 修改聊天记录
     *
     * @param chatId
     * @param type
     * @return
     * @throws Exception
     * @title update
     * @description
     * @author zh
     * @date 2026-04-27 21:26
     *
     **/
    void update(String chatId, String type);

    /**
     * 通过id获取聊天记录
     *
     * @param chatId
     * @return {@link List< String>}
     * @throws Exception
     * @title getChatIdsById
     * @description
     * @author zh
     * @date 2026-04-27 21:26
     *
     **/
    List<String> getChatIdsById(String type);

}

/**
 * ============================================================
 * 版权： 广州市新维数据科技有限公司新架构产品部所有(c) 2026
 * 文件：individual.zh.springai.repository
 * 所含类: InMemoryChatHistoryRepository
 * 文件作用描述 TODO
 * 修改记录：
 * 日期                                      作者         版本     内容
 * =============================================================
 * 2026/4/27  21:27      zh     v1.0.0   新建
 * =============================================================
 */

package individual.zh.springai.repository;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Titile:InMemoryChatHistoryRepository</p >
 * <p>ProjectName: </p >
 * <p>Description:TODO()  </p >
 * <p>Copyright: Copyright (c) 2023</p >
 * <p>Company: 新维数据 </p >
 *
 * @BelongsProject: SpringAI
 * @BelongsPackage: individual.zh.springai.repository
 * @Author: zh
 * @CreateTime: 2026/4/27  21:27
 * @Description: TODO
 * @Version: 1.0
 */
@Service("InMemoryChatHistoryRepository")
public class InMemoryChatHistoryRepository implements ChatHistoryRepository {

    /**
     * 聊天记录
     */
    private final Map<String, List<String>> chatHistory = new HashMap<>();

    @Override
    public void save(String chatId, String type) {
        // @author zh @date 2026-04-27 21:32:20 @description 添加聊天记录
        /*if (chatHistory.containsKey(type)) {
            chatHistory.put(type, new ArrayList<>());
        }
        List<String> charIds = chatHistory.get(type);
        if (!charIds.contains(chatId)) {
            return;
        }
        charIds.add(chatId);*/
        // @author zh @date 2026-04-27 21:32:12 @description 添加聊天记录
        List<String> strings = chatHistory.computeIfAbsent(type, k -> new ArrayList<>());
        if (!strings.contains(chatId)) {
            strings.add(chatId);
        }
    }

    @Override
    public void delete(String chatId, String type) {

    }

    @Override
    public void update(String chatId, String type) {

    }

    @Override
    public List<String> getChatIdsById(String type) {
        //List<String> chatIds = chatHistory.get(type);
        return chatHistory.getOrDefault(type, new ArrayList<>());
    }

}

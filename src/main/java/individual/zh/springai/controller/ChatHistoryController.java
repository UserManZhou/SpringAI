/**
 * ============================================================
 * 版权： 广州市新维数据科技有限公司新架构产品部所有(c) 2026
 * 文件：individual.zh.springai.controller
 * 所含类: AiController
 * 文件作用描述 TODO
 * 修改记录：
 * 日期                                      作者         版本     内容
 * =============================================================
 * 2026/4/27  21:21      zh     v1.0.0   新建
 * =============================================================
 */

package individual.zh.springai.controller;

import individual.zh.springai.entity.vo.MessageVo;
import individual.zh.springai.repository.ChatHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>Titile:AiController</p >
 * <p>ProjectName: </p >
 * <p>Description:TODO()  </p >
 * <p>Copyright: Copyright (c) 2023</p >
 * <p>Company: 新维数据 </p >
 *
 * @BelongsProject: SpringAI
 * @BelongsPackage: individual.zh.springai.controller
 * @Author: zh
 * @CreateTime: 2026/4/27  21:21
 * @Description: TODO
 * @Version: 1.0
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/ai/history")
public class ChatHistoryController {

    private final ChatHistoryRepository chatHistoryRepository;

    private final ChatMemory chatMemory;

    @GetMapping("/{type}")
    public List<String> getChatIds(@PathVariable String type) {
        return chatHistoryRepository.getChatIdsById(type);
    }

    @GetMapping("/{type}/{chatId}")
    public List<MessageVo> getChatHistory(@PathVariable String type, @PathVariable String chatId) {
        List<Message> messages = chatMemory.get(chatId, Integer.MAX_VALUE);
        if (messages.isEmpty()) {
            return List.of();
        }
        return messages.stream().map(MessageVo::new).toList();
    }

}

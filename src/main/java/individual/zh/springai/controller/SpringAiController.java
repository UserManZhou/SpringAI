/**
 * ============================================================
 * 版权： 广州市新维数据科技有限公司新架构产品部所有(c) 2026
 * 文件：individual.zh.springai.controller
 * 所含类: SpringAiController
 * 文件作用描述 TODO
 * 修改记录：
 * 日期                                      作者         版本     内容
 * =============================================================
 * 2026/4/14  21:27      zh     v1.0.0   新建
 * =============================================================
 */

package individual.zh.springai.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * <p>Titile:SpringAiController</p >
 * <p>ProjectName: </p >
 * <p>Description:TODO()  </p >
 * <p>Copyright: Copyright (c) 2023</p >
 * <p>Company: 新维数据 </p >
 *
 * @BelongsProject: SpringAI
 * @BelongsPackage: individual.zh.springai.controller
 * @Author: zh
 * @CreateTime: 2026/4/14  21:27
 * @Description: TODO
 * @Version: 1.0
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/springai")
public class SpringAiController {

    private final ChatClient client;

    /**
     * 聊天
     *
     * @param prompt
     * @return {@link String}
     * @throws Exception
     * @title chat
     * @description
     * @author zh
     * @date 2026-04-14 22:34
     *
     **/
    @GetMapping(value = "/chat", produces = "text/html;charset=utf-8")
    public String chat(String prompt) {
        return client.prompt().user(prompt).call().content();
    }

    /**
     * 流式聊天
     *
     * @param prompt
     * @return {@link Flux< String>}
     * @throws Exception
     * @title fluxChat
     * @description
     * @author zh
     * @date 2026-04-14 23:37
     *
     **/
    @RequestMapping(value = "/fluxChat", produces = "text/html;charset=utf-8")
    public Flux<String> fluxChat(String prompt) {
        return client.prompt().user(prompt).stream().content();
    }
}

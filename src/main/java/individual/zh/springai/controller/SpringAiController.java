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

import individual.zh.springai.repository.ChatHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.model.Media;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Objects;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;

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
@RequestMapping("/springAi")
public class SpringAiController {

    /*@Qualifier("chatClient")
    @Autowired
    private final ChatClient client;*/

    @Qualifier("multiChatClient")
    @Autowired
    private final ChatClient client;

    private final ChatHistoryRepository chatHistoryRepository;

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
    /*@GetMapping(value = "/chat", produces = "text/html;charset=utf-8")
    public String chat(String prompt) {
        return client.prompt().user(prompt).call().content();
    }*/

    /**
     * 流式聊天
     *
     * @param prompt
     * @return {@link Flux<String>}
     * @throws Exception
     * @title fluxChat
     * @description
     * @author zh
     * @date 2026-04-14 23:37
     *
     **/
    /*@RequestMapping(value = "/chat", produces = "text/html;charset=utf-8")
    public Flux<String> chat(String prompt, String chatId) {
        // @author zh @date 2026-04-27 21:34:19 @description 保存会话id
        chatHistoryRepository.save(chatId, "chat");
        // @author zh @date 2026-04-27 21:35:11 @description 请求模型
        return client
                .prompt()
                .user(prompt)
                .advisors(advisor -> advisor.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId))
                .stream()
                .content();
    }*/
    @RequestMapping(value = "/chat", produces = "text/html;charset=utf-8")
    public Flux<String> chat(@RequestParam("prompt") String prompt,
                             @RequestParam("chatId") String chatId,
                             @RequestParam(value = "files", required = false) List<MultipartFile> files) {
        // @author zh @date 2026-04-27 21:34:19 @description 保存会话id
        chatHistoryRepository.save(chatId, "chat");
        // @author zh @date 2026-05-21 22:13:10 @description 获取文件
        if (files == null || files.isEmpty()) {
            return textChat(prompt, chatId);
        } else {
            return multiChat(prompt, chatId, files);
        }

    }

    /**
     * 文本聊天
     *
     * @param prompt
     * @param chatId
     * @return {@link Flux<String>}
     * @throws Exception
     * @title textChat
     * @description
     * @author zh
     * @date 2026-05-21 22:18
     *
     **/
    private Flux<String> textChat(String prompt, String chatId) {
        return client
                .prompt()
                .user(prompt)
                .advisors(advisor ->
                        advisor.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId))
                .stream()
                .content();
    }

    /**
     * 多文件聊天
     *
     * @param prompt
     * @param chatId
     * @param files
     * @return {@link Flux<String>}
     * @throws Exception
     * @title multiChat
     * @description
     * @author zh
     * @date 2026-05-21 22:19
     *
     **/
    private Flux<String> multiChat(String prompt, String chatId, List<MultipartFile> files) {
        List<Media> mediaList = files.stream().map(file -> new Media(
                MediaType.valueOf(Objects.requireNonNull(file.getContentType())),
                file.getResource())).toList();
        return client
                .prompt()
                .user(p ->
                        p.text(prompt)
                                .media(mediaList.toArray(Media[]::new)))
                .advisors(advisor ->
                        advisor.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId))
                .stream()
                .content();
    }


    /*@RequestMapping(value = "/chat", produces = "text/html;charset=utf-8")
    public Flux<ChatResponse> chat(String prompt) {
        *//*return chatModel.stream(new Prompt(prompt,
                        OllamaChatOptions.builder()
                                .model("deepseek-r1:8b")
                                .enableThinking()
                                .temperature(0.4)
                                .build()))
                .map(response -> response.getResult().getOutput().getText());*//*
        Prompt prompt2 = new Prompt(
                prompt,
                OllamaChatOptions.builder()
                        .model("deepseek-r1:8b")
                        .enableThinking() // 开启思考
                        .build()
        );
        // 返回流式 Flux 流
        return streamingChatModel.stream(prompt2);
    }*/

}

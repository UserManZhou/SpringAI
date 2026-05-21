/**
 * ============================================================
 * 版权： 广州市新维数据科技有限公司新架构产品部所有(c) 2026
 * 文件：individual.zh.springai.config
 * 所含类: CommonConfiguration
 * 文件作用描述 TODO
 * 修改记录：
 * 日期                                      作者         版本     内容
 * =============================================================
 * 2026/4/14  22:29      zh     v1.0.0   新建
 * =============================================================
 */

package individual.zh.springai.config;

import individual.zh.springai.constants.SystemConstants;
import individual.zh.springai.tools.CourseTools;
import io.micrometer.observation.ObservationRegistry;
import model.AlibabaOpenAiChatModel;
import org.springframework.ai.autoconfigure.openai.OpenAiChatProperties;
import org.springframework.ai.autoconfigure.openai.OpenAiConnectionProperties;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.observation.ChatModelObservationConvention;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.model.SimpleApiKey;
import org.springframework.ai.model.tool.ToolCallingManager;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>Titile:CommonConfiguration</p >
 * <p>ProjectName: </p >
 * <p>Description:TODO()  </p >
 * <p>Copyright: Copyright (c) 2023</p >
 * <p>Company: 新维数据 </p >
 *
 * @BelongsProject: SpringAI
 * @BelongsPackage: individual.zh.springai.config
 * @Author: zh
 * @CreateTime: 2026/4/14  22:29
 * @Description: TODO
 * @Version: 1.0
 */
@Configuration
public class CommonConfiguration {

    /**
     * 创建ChatMemory
     *
     * @param
     * @return {@link ChatMemory}
     * @throws Exception
     * @title chatMemory
     * @description
     * @author zh
     * @date 2026-04-27 20:54
     *
     **/
    @Bean
    public ChatMemory chatMemory() {
        return new InMemoryChatMemory();
    }

    /**
     * 创建OpenAiEmbeddingModel
     *
     * @param openAiEmbeddingModel
     * @return {@link VectorStore}
     * @throws Exception
     * @title vectorStore
     * @description
     * @author zh
     * @date 2026-05-14 22:08
     *
     **/
    @Bean
    public VectorStore vectorStore(OpenAiEmbeddingModel openAiEmbeddingModel) {
        return SimpleVectorStore.builder(openAiEmbeddingModel).build();
    }

    /**
     * 创建ChatClient
     *
     * @param ollamaChatModel
     * @return {@link ChatClient}
     * @throws Exception
     * @title chatClient
     * @description
     * @author zh
     * @date 2026-04-15 20:25
     *
     **/
    @Bean
    public ChatClient chatClient(OllamaChatModel ollamaChatModel, ChatMemory chatMemory) {
        return ChatClient.builder(ollamaChatModel)
                .defaultSystem("你是一个牛马，你的名字叫陈总。用 陈总的身份进行回答问题")
                .defaultAdvisors(
                        // @author zh @date 2026-04-27 20:53:50 @description 添加日志
                        new SimpleLoggerAdvisor(),
                        // @author zh @date 2026-04-27 20:53:53 @description 添加聊天记忆
                        new MessageChatMemoryAdvisor(chatMemory))
                .build();
    }

    @Bean
    public ChatClient multiChatClient(AlibabaOpenAiChatModel openAiChatModel, ChatMemory chatMemory) {
        return ChatClient.builder(openAiChatModel)
                // @author zh @date 2026-05-21 22:06:18 @description 添加模型参数设置
                .defaultOptions(
                        ChatOptions.builder()
                                // @author zh @date 2026-05-21 22:06:25 @description 模型名称
                                .model(SystemConstants.MODEL_NAME)
                                .build())
                .defaultSystem("你是一个牛马，你的名字叫陈总。用 陈总的身份进行回答问题")
                .defaultAdvisors(
                        // @author zh @date 2026-04-27 20:53:50 @description 添加日志
                        new SimpleLoggerAdvisor(),
                        // @author zh @date 2026-04-27 20:53:53 @description 添加聊天记忆
                        new MessageChatMemoryAdvisor(chatMemory))
                .build();
    }

    /**
     * 创建ChatClient
     *
     * @param openAiChatModel
     * @param chatMemory
     * @return {@link ChatClient}
     * @throws Exception
     * @title gameChatClient
     * @description
     * @author zh
     * @date 2026-04-29 19:23
     *
     **/
    @Bean
    public ChatClient gameChatClient(OpenAiChatModel openAiChatModel, ChatMemory chatMemory) {
        return ChatClient.builder(openAiChatModel)
                .defaultSystem(SystemConstants.GAME_SYSTEM_PROMPT)
                .defaultAdvisors(
                        // @author zh @date 2026-04-27 20:53:50 @description 添加日志
                        new SimpleLoggerAdvisor(),
                        // @author zh @date 2026-04-27 20:53:53 @description 添加聊天记忆
                        new MessageChatMemoryAdvisor(chatMemory))
                .build();
    }

    /**
     * 创建ChatClient
     *
     * @param openAiChatModel
     * @param chatMemory
     * @param courseTools
     * @return {@link ChatClient}
     * @throws Exception
     * @title serviceChatClient
     * @description
     * @author zh
     * @date 2026-05-08 21:55
     *
     **/
    @Bean
    public ChatClient serviceChatClient(AlibabaOpenAiChatModel openAiChatModel, ChatMemory chatMemory, CourseTools courseTools) {
        return ChatClient.builder(openAiChatModel)
                .defaultSystem(SystemConstants.SERVICES_SYSTEM_PROMPT)
                .defaultAdvisors(
                        // @author zh @date 2026-04-27 20:53:50 @description 添加日志
                        new SimpleLoggerAdvisor(),
                        // @author zh @date 2026-04-27 20:53:53 @description 添加聊天记忆
                        new MessageChatMemoryAdvisor(chatMemory))
                .defaultTools(courseTools)
                .build();
    }

    /**
     * 创建AlibabaOpenAiChatModel
     *
     * @param commonProperties
     * @param chatProperties
     * @param restClientBuilderProvider
     * @param webClientBuilderProvider
     * @param toolCallingManager
     * @param retryTemplate
     * @param responseErrorHandler
     * @param observationRegistry
     * @param observationConvention
     * @return {@link AlibabaOpenAiChatModel}
     * @throws Exception
     * @title alibabaOpenAiChatModel
     * @description
     * @author zh
     * @date 2026-05-11 22:09
     *
     **/
    @Bean
    public AlibabaOpenAiChatModel alibabaOpenAiChatModel(OpenAiConnectionProperties commonProperties, OpenAiChatProperties chatProperties, ObjectProvider<RestClient.Builder> restClientBuilderProvider, ObjectProvider<WebClient.Builder> webClientBuilderProvider, ToolCallingManager toolCallingManager, RetryTemplate retryTemplate, ResponseErrorHandler responseErrorHandler, ObjectProvider<ObservationRegistry> observationRegistry, ObjectProvider<ChatModelObservationConvention> observationConvention) {
        String baseUrl = StringUtils.hasText(chatProperties.getBaseUrl()) ? chatProperties.getBaseUrl() : commonProperties.getBaseUrl();
        String apiKey = StringUtils.hasText(chatProperties.getApiKey()) ? chatProperties.getApiKey() : commonProperties.getApiKey();
        String projectId = StringUtils.hasText(chatProperties.getProjectId()) ? chatProperties.getProjectId() : commonProperties.getProjectId();
        String organizationId = StringUtils.hasText(chatProperties.getOrganizationId()) ? chatProperties.getOrganizationId() : commonProperties.getOrganizationId();
        Map<String, List<String>> connectionHeaders = new HashMap<>();
        if (StringUtils.hasText(projectId)) {
            connectionHeaders.put("OpenAI-Project", List.of(projectId));
        }

        if (StringUtils.hasText(organizationId)) {
            connectionHeaders.put("OpenAI-Organization", List.of(organizationId));
        }
        RestClient.Builder restClientBuilder = restClientBuilderProvider.getIfAvailable(RestClient::builder);
        WebClient.Builder webClientBuilder = webClientBuilderProvider.getIfAvailable(WebClient::builder);
        OpenAiApi openAiApi = OpenAiApi.builder().baseUrl(baseUrl).apiKey(new SimpleApiKey(apiKey)).headers(CollectionUtils.toMultiValueMap(connectionHeaders)).completionsPath(chatProperties.getCompletionsPath()).embeddingsPath("/v1/embeddings").restClientBuilder(restClientBuilder).webClientBuilder(webClientBuilder).responseErrorHandler(responseErrorHandler).build();
        AlibabaOpenAiChatModel chatModel = AlibabaOpenAiChatModel.builder().openAiApi(openAiApi).defaultOptions(chatProperties.getOptions()).toolCallingManager(toolCallingManager).retryTemplate(retryTemplate).observationRegistry((ObservationRegistry) observationRegistry.getIfUnique(() -> ObservationRegistry.NOOP)).build();
        Objects.requireNonNull(chatModel);
        observationConvention.ifAvailable(chatModel::setObservationConvention);
        return chatModel;
    }

    /**
     * 创建ChatClient
     *
     * @param openAiChatModel
     * @param chatMemory
     * @param vectorStore
     * @return {@link ChatClient}
     * @throws Exception
     * @title pdfChatClient
     * @description
     * @author zh
     * @date 2026-05-20 21:06
     *
     **/
    @Bean
    public ChatClient pdfChatClient(OpenAiChatModel openAiChatModel, ChatMemory chatMemory, VectorStore vectorStore) {
        return ChatClient.builder(openAiChatModel)
                .defaultSystem(SystemConstants.PDF_SYSTEM_PROMPT)
                .defaultAdvisors(
                        // @author zh @date 2026-04-27 20:53:50 @description 添加日志
                        new SimpleLoggerAdvisor(),
                        // @author zh @date 2026-04-27 20:53:53 @description 添加聊天记忆
                        new MessageChatMemoryAdvisor(chatMemory),
                        // @author zh @date 2026-05-20 21:07:04 @description 添加问答
                        new QuestionAnswerAdvisor(vectorStore,
                                // @author zh @date 2026-05-20 21:07:08 @description 创建搜索请求
                                SearchRequest.builder()
                                        // @author zh @date 2026-05-20 21:07:10 @description 设置相似度阈值
                                        .similarityThreshold(0.6d)
                                        // @author zh @date 2026-05-20 21:07:13 @description 设置返回结果数量
                                        .topK(2)
                                        // @author zh @date 2026-05-20 21:07:17 @description 创建搜索请求
                                        .build()
                        )
                )
                .build();
    }

}

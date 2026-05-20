/**
 * ============================================================
 * 版权： 广州市新维数据科技有限公司新架构产品部所有(c) 2026
 * 文件：individual.zh.springai.controller
 * 所含类: PdfController
 * 文件作用描述 TODO
 * 修改记录：
 * 日期                                      作者         版本     内容
 * =============================================================
 * 2026/5/20  20:36      zh     v1.0.0   新建
 * =============================================================
 */

package individual.zh.springai.controller;

import individual.zh.springai.entity.vo.Result;
import individual.zh.springai.repository.ChatHistoryRepository;
import individual.zh.springai.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;

/**
 * <p>Titile:PdfController</p >
 * <p>ProjectName: </p >
 * <p>Description:TODO()  </p >
 * <p>Copyright: Copyright (c) 2023</p >
 * <p>Company: 新维数据 </p >
 *
 * @BelongsProject: SpringAI
 * @BelongsPackage: individual.zh.springai.controller
 * @Author: zh
 * @CreateTime: 2026/5/20  20:36
 * @Description: TODO
 * @Version: 1.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/ai/pdf")
public class PdfController {

    private final FileRepository fileRepository;

    private final VectorStore vectorStore;

    @Qualifier("pdfChatClient")
    @Autowired
    private final ChatClient pdfChatClient;

    private final ChatHistoryRepository chatHistoryRepository;

    @RequestMapping(value = "/chat", produces = "text/html;charset=utf-8")
    public Flux<String> chat(String prompt, String chatId) {
        // @author zh @date 2026-05-20 21:09:59 @description 获取文件
        Resource file = fileRepository.getFile(chatId);
        if (!file.exists()) {
            throw new RuntimeException("会话文件不存在");
        }
        // @author zh @date 2026-04-27 21:34:19 @description 保存会话id
        chatHistoryRepository.save(chatId, "pdf");
        // @author zh @date 2026-04-27 21:35:11 @description 请求模型
        return pdfChatClient
                .prompt()
                .user(prompt)
                // @author zh @date 2026-05-20 21:11:24 @description 添加过滤条件
                .advisors(advisor -> advisor.param(CHAT_MEMORY_CONVERSATION_ID_KEY,
                        chatId))
                // @author zh @date 2026-05-20 21:11:28 @description 添加过滤条件
                .advisors(advisor -> advisor.param(QuestionAnswerAdvisor.FILTER_EXPRESSION,
                        "file_name == '" + file.getFilename() + "'"))
                .stream()
                .content();
    }

    /**
     * 上传pdf文件
     *
     * @param chatId
     * @param file
     * @return {@link Result}
     * @throws Exception
     * @title uploadPdf
     * @description
     * @author zh
     * @date 2026-05-20 20:39
     *
     **/
    @RequestMapping("/upload/{chatId}")
    public Result uploadPdf(@PathVariable String chatId, @RequestParam("file") MultipartFile file) {

        // @author zh @date 2026-05-20 20:38:49 @description 判断文件类型
        if (!Objects.equals(file.getContentType(), "application/pdf")) {
            return Result.fail("请上传pdf文件");
        }

        try {
            // @author zh @date 2026-05-20 20:39:46 @description 保存文件
            boolean success = fileRepository.save(chatId, file.getResource());
            if (!success) {
                return Result.fail("上传失败");
            }

            // @author zh @date 2026-05-20 20:40:35 @description 写入向量库
            writeToVectorStore(file.getResource());
            return Result.ok();
        } catch (IOException e) {
            log.error("Error saving file: ", e);
            return Result.fail("上传失败");
        }
    }

    /**
     * 下载pdf文件
     *
     * @param chatId
     * @return {@link ResponseEntity<Resource>}
     * @throws Exception
     * @title downloadPdf
     * @description
     * @author zh
     * @date 2026-05-20 20:47
     *
     **/
    @GetMapping("/file/{chatId}")
    public ResponseEntity<Resource> downloadPdf(@PathVariable String chatId) {
        // @author zh @date 2026-05-20 20:45:09 @description 获取文件
        Resource resource = fileRepository.getFile(chatId);
        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }
        // @author zh @date 2026-05-20 20:46:34 @description 设置文件名
        String fileName = URLEncoder.encode(Objects.requireNonNull(resource.getFilename()), StandardCharsets.UTF_8);
        // @author zh @date 2026-05-20 20:48:18 @description 返回文件
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }

    /**
     * 写入向量库
     *
     * @param resource
     * @return
     * @throws Exception
     * @title writeToVectorStore
     * @description
     * @author zh
     * @date 2026-05-20 20:41
     *
     **/
    private void writeToVectorStore(Resource resource) {
        // @author zh @date 2026-05-20 20:42:36 @description 创建pdf文档读取器
        PagePdfDocumentReader reader = new PagePdfDocumentReader(resource,
                PdfDocumentReaderConfig.builder()
                        .withPageExtractedTextFormatter(ExtractedTextFormatter.defaults())
                        .withPagesPerDocument(1)
                        .build());
        // @author zh @date 2026-05-20 20:42:54 @description 读取pdf文档
        List<Document> read = reader.read();
        // @author zh @date 2026-05-20 20:43:04 @description 添加到向量库
        vectorStore.add(read);
    }

}

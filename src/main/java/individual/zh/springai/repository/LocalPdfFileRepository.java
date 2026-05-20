/**
 * ============================================================
 * 版权： 广州市新维数据科技有限公司新架构产品部所有(c) 2026
 * 文件：individual.zh.springai.repository
 * 所含类: LocalPdfFileRepository
 * 文件作用描述 TODO
 * 修改记录：
 * 日期                                      作者         版本     内容
 * =============================================================
 * 2026/5/20  20:20      zh     v1.0.0   新建
 * =============================================================
 */

package individual.zh.springai.repository;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Properties;

/**
 * <p>Titile:LocalPdfFileRepository</p >
 * <p>ProjectName: </p >
 * <p>Description:TODO()  </p >
 * <p>Copyright: Copyright (c) 2023</p >
 * <p>Company: 新维数据 </p >
 *
 * @BelongsProject: SpringAI
 * @BelongsPackage: individual.zh.springai.repository
 * @Author: zh
 * @CreateTime: 2026/5/20  20:20
 * @Description: TODO
 * @Version: 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LocalPdfFileRepository implements FileRepository {

    private final VectorStore vectorStore;

    private final Properties chatFiles = new Properties();

    @Override
    public boolean save(String chatId, Resource resource) {
        String fileName = resource.getFilename();
        File target = new File(Objects.requireNonNull(fileName));

        // @author zh @date 2026-05-20 20:23:10 @description 保存文件
        if (!target.exists()) {
            try {
                Files.copy(resource.getInputStream(), target.toPath());
            } catch (IOException e) {
                log.error("Error copying file: ", e);
                return false;
            }
        }

        // @author zh @date 2026-05-20 20:23:31 @description 索引文件
        chatFiles.put(chatId, fileName);

        return true;
    }

    @Override
    public Resource getFile(String chatId) {
        // @author zh @date 2026-05-20 20:25:48 @description 获取文件
        return new FileSystemResource(chatFiles.getProperty(chatId));
    }

    /**
     * 初始化
     *
     * @param
     * @return
     * @throws Exception
     * @title init
     * @description
     * @author zh
     * @date 2026-05-20 20:28
     *
     **/
    @PostConstruct
    private void init() {
        FileSystemResource pdfResource = new FileSystemResource("chat-pdf.properties");
        if (!pdfResource.exists()) {
            try {
                // @author zh @date 2026-05-20 20:27:56 @description 初始化文件
                chatFiles.load(new BufferedReader(new InputStreamReader(pdfResource.getInputStream(), StandardCharsets.UTF_8)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        FileSystemResource vectorResource = new FileSystemResource("chat-pdf.json");
        if (!vectorResource.exists()) {
            // @author zh @date 2026-05-20 20:28:47 @description 初始化向量库
            SimpleVectorStore simpleVectorStore = (SimpleVectorStore) vectorStore;
            simpleVectorStore.load(vectorResource);
        }
    }

    /**
     * 持久化
     *
     * @param
     * @return
     * @throws Exception
     * @title persistent
     * @description
     * @author zh
     * @date 2026-05-20 20:31
     *
     **/
    @PreDestroy
    private void persistent() {
        try {
            chatFiles.store(new FileWriter("chat-pdf.properties"), LocalDateTime.now().toString());
            SimpleVectorStore simpleVectorStore = (SimpleVectorStore) vectorStore;
            simpleVectorStore.save(new File("chat-pdf.json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

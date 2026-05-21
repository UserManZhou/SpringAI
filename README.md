# SpringAI - 智能 AI 应用平台

基于 Spring Boot 3.5.7 和 Spring AI 1.0.0-M6 构建的多功能人工智能应用，集成了聊天对话、PDF 文档问答、角色扮演游戏、智能客服等 AI 能力。

## 项目概述

- **项目名称**: SpringAI
- **Group ID**: `individual.zh`
- **Artifact ID**: `SpringAI`
- **版本**: `0.0.1-SNAPSHOT`
- **打包方式**: WAR
- **Java 版本**: 23
- **作者**: zh 

## 技术栈

### 核心框架
- **Spring Boot**: 3.5.7
- **Spring AI**: 1.0.0-M6
- **MyBatis Plus**: 3.5.10.1
- **Lombok**: 1.18.22

### AI 模型支持
| 模型类型 | 提供商 | 模型名称 | 用途 |
|----------|--------|----------|------|
| 本地模型 | Ollama | deepseek-r1:8b | 基础聊天 |
| 云端模型 | 阿里云 DashScope | qwen3.5-122b-a10b | 客服、游戏 |
| 多模态模型 | 阿里云 DashScope | qwen3.5-omni-plus-2026-03-15 | 多模态聊天 |
| 嵌入模型 | 阿里云 DashScope | text-embedding-v4 | 向量生成（1024 维） |

### 数据库
- **MySQL**: 8.x
- **数据库名**: itheima

## 功能特性

### 1. 通用聊天 (`/springAi/chat`)
- 支持纯文本对话
- 支持多模态输入（图片、文件上传）
- 流式响应输出
- 会话记忆功能
- 使用阿里云 Qwen3.5 多模态模型

### 2. 角色扮演游戏 (`/ai/game`)
- 哄女友开心互动游戏
- 原谅值机制（0-100）
- 情绪反馈系统（5 个等级）
- 游戏通关/失败判定
- 基于 System Prompt 的角色扮演

### 3. 智能客服 (`/ai/service`)
- 黑马程序员课程咨询
- 试听预约服务
- Function Calling 工具调用
- 自动查询课程和校区信息
- 创建预约单
- Prompt 注入防护

### 4. PDF 文档问答 (`/ai/pdf/*`)
- PDF 文件上传和管理
- 基于 RAG（检索增强生成）的问答
- 向量相似度搜索
- 上下文关联回答
- 防止答案编造

### 5. 聊天历史管理 (`/ai/history/*`)
- 按类型存储聊天会话
- 获取聊天记录列表
- 查看指定会话历史

## 项目结构

```
SpringAI/
├── src/main/java/individual/zh/springai/
│   ├── SpringAiApplication.java          # 主启动类
│   ├── config/                           # 配置层
│   │   ├── CommonConfiguration.java      # AI Bean 配置
│   │   └── MvcConfiguration.java         # MVC 跨域配置
│   ├── constants/                        # 常量层
│   │   └── SystemConstants.java          # System Prompt 模板
│   ├── controller/                       # 控制层
│   │   ├── SpringAiController.java       # 通用聊天控制器
│   │   ├── GameController.java           # 游戏控制器
│   │   ├── CustomerServiceController.java# 客服控制器
│   │   ├── PdfController.java            # PDF 聊天控制器
│   │   └── ChatHistoryController.java    # 聊天历史控制器
│   ├── entity/                           # 实体层
│   │   ├── po/                           # 持久化对象
│   │   │   ├── Course.java               # 课程实体
│   │   │   ├── School.java               # 校区实体
│   │   │   └── CourseReservation.java    # 课程预约实体
│   │   ├── query/                        # 查询对象
│   │   │   └── CourseQuery.java          # 课程查询条件
│   │   └── vo/                           # 视图对象
│   │       ├── MessageVo.java            # 消息视图对象
│   │       └── Result.java               # 统一响应结果
│   ├── mapper/                           # MyBatis Mapper
│   │   ├── CourseMapper.java
│   │   ├── SchoolMapper.java
│   │   └── CourseReservationMapper.java
│   ├── repository/                       # 仓储层
│   │   ├── ChatHistoryRepository.java    # 聊天历史接口
│   │   ├── InMemoryChatHistoryRepository.java # 内存实现
│   │   ├── FileRepository.java           # 文件仓储接口
│   │   └── LocalPdfFileRepository.java   # 本地 PDF 实现
│   ├── service/                          # 服务层接口
│   │   ├── ICourseService.java
│   │   ├── ISchoolService.java
│   │   └── ICourseReservationService.java
│   ├── service/impl/                     # 服务层实现
│   │   ├── CourseServiceImpl.java
│   │   ├── SchoolServiceImpl.java
│   │   └── CourseReservationServiceImpl.java
│   ├── tools/                            # AI 工具类
│   │   └── CourseTools.java              # 课程相关 Function Calling
│   └── util/                             # 工具类
│       └── VectorDistanceUtils.java      # 向量距离计算
├── src/main/resources/
│   ├── application.properties            # 应用配置
│   └── mapper/                           # MyBatis XML
│       ├── CourseMapper.xml
│       ├── SchoolMapper.xml
│       └── CourseReservationMapper.xml
├── model/
│   └── AlibabaOpenAiChatModel.java       # 自定义阿里云 OpenAI 模型
├── pom.xml                               # Maven 依赖配置
└── chat-pdf.json                         # 向量库持久化数据
```

## 环境要求

- **JDK**: 23 或更高版本
- **Maven**: 3.6+
- **MySQL**: 8.0+
- **Ollama** (可选): 用于本地模型推理
- **阿里云 API Key**: 用于访问 DashScope 服务

## 快速开始

### 1. 克隆项目

```bash
git clone <repository-url>
cd SpringAI
```

### 2. 配置环境变量

设置阿里云 API Key 环境变量：

```bash
# Linux/Mac
export OPENAI_API_KEY=your-api-key-here

# Windows
set OPENAI_API_KEY=your-api-key-here
```

### 3. 配置数据库

创建 MySQL 数据库并执行初始化脚本：

```sql
CREATE DATABASE itheima DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

修改 `src/main/resources/application.properties` 中的数据库连接信息：

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/itheima?useUnicode=true&characterEncoding=utf-8
spring.datasource.username=root
spring.datasource.password=root
```

### 4. 安装 Ollama (可选)

如需使用本地模型，请安装 Ollama 并拉取模型：

```bash
ollama pull deepseek-r1:8b
```

### 5. 运行项目

```bash
# 使用 Maven Wrapper
./mvnw spring-boot:run

# 或使用 Maven
mvn spring-boot:run
```

应用将在 `http://localhost:8080` 启动。

## API 接口文档

### 通用聊天

**端点**: `POST /springAi/chat`

**请求体**:
```json
{
  "message": "你好",
  "file": "optional-file-upload"
}
```

**响应**: 流式文本响应 (Flux<String>)

---

### 角色扮演游戏

**端点**: `POST /ai/game`

**请求体**:
```json
{
  "message": "我错了，我不该看别的女生",
  "chatId": "optional-chat-id"
}
```

**响应示例**:
```
(微笑)哼，我怎么知道你说的是不是真的？
得分：+10
原谅值：30/100
```

---

### 智能客服

**端点**: `POST /ai/service`

**请求体**:
```json
{
  "message": "我想学习 Java 开发",
  "chatId": "optional-chat-id"
}
```

**功能**:
- 课程咨询（自动查询符合条件的课程）
- 校区查询
- 试听预约（收集姓名、联系方式等信息）

---

### PDF 文档问答

#### 上传 PDF

**端点**: `POST /ai/pdf/upload/{chatId}`

**请求**: multipart/form-data，包含 PDF 文件

#### PDF 聊天

**端点**: `POST /ai/pdf/chat`

**请求体**:
```json
{
  "message": "文档中提到了什么内容？",
  "chatId": "your-chat-id"
}
```

**响应**: 基于 PDF 内容的智能回答

#### 下载 PDF

**端点**: `GET /ai/pdf/file/{chatId}`

---

### 聊天历史

#### 获取聊天 ID 列表

**端点**: `GET /ai/history/{type}`

**参数**:
- `type`: 聊天类型（game, service, pdf 等）

#### 获取聊天记录

**端点**: `GET /ai/history/{type}/{chatId}`

**参数**:
- `type`: 聊天类型
- `chatId`: 会话 ID

## 核心技术说明

### RAG (检索增强生成)

PDF 问答功能使用 RAG 技术：
1. PDF 文档被解析为文本片段
2. 通过嵌入模型转换为向量
3. 存储在 SimpleVectorStore 中
4. 用户提问时，进行向量相似度搜索（阈值 0.6，topK=2）
5. 将检索到的相关上下文提供给 LLM 生成回答

### Function Calling

智能客服场景使用 Function Calling：
- `queryCourse(CourseQuery)`: 查询课程信息
- `querySchool()`: 查询校区列表
- `createCourseReservation(...)`: 创建课程预约单

AI 根据对话内容自动决定何时调用这些工具。

### 会话记忆

使用 `InMemoryChatMemory` 实现多轮对话：
- 每个会话有唯一的 chatId
- 自动维护对话上下文
- 支持不同场景的独立记忆

### 向量相似度计算

提供两种距离计算方法：
- **欧氏距离**: 衡量向量间的直线距离
- **余弦距离**: 衡量向量方向的相似度

## 数据库设计

### course (课程表)

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR | 课程名称 |
| edu | VARCHAR | 学历要求 |
| type | VARCHAR | 课程类型 |
| price | DECIMAL | 价格 |
| duration | INT | 课时 |

### school (校区表)

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| name | VARCHAR | 校区名称 |
| city | VARCHAR | 所在城市 |

### course_reservation (课程预约表)

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| course | VARCHAR | 课程名称 |
| studentName | VARCHAR | 学员姓名 |
| contactInfo | VARCHAR | 联系方式 |
| school | VARCHAR | 校区 |
| remark | VARCHAR | 备注 |

## 配置说明

### application.properties 关键配置

```properties
# Ollama 本地模型配置
spring.ai.ollama.base-url=http://localhost:11434
spring.ai.ollama.chat.model=deepseek-r1:8b

# 阿里云 DashScope 配置
spring.ai.openai.base-url=https://dashscope.aliyuncs.com/compatible-mode
spring.ai.openai.api-key=${OPENAI_API_KEY}
spring.ai.openai.chat.options.model=qwen3.5-122b-a10b
spring.ai.openai.embedding.options.model=text-embedding-v4
spring.ai.openai.embedding.options.dimensions=1024

# 日志级别
logging.level.org.springframework.ai=DEBUG
logging.level.org.springframework.ai.ollama=DEBUG
```

## 开发指南

### 添加新的 AI 场景

1. 在 `SystemConstants` 中定义 System Prompt
2. 在 `CommonConfiguration` 中创建对应的 `ChatClient` Bean
3. 创建 Controller 处理 HTTP 请求
4. （可选）添加工具类供 Function Calling 使用

### 扩展数据库功能

1. 在 `entity/po` 中定义实体类
2. 创建对应的 Mapper 接口
3. （可选）编写 XML 映射文件
4. 创建 Service 接口和实现类

### 测试

运行单元测试：

```bash
./mvnw test
```

现有测试包括：
- 向量存储测试
- 嵌入模型测试
- 向量距离计算测试

## 注意事项

1. **API Key 安全**: 不要将 API Key 硬编码在代码中，使用环境变量
2. **跨域配置**: 当前允许所有来源，生产环境需调整为特定域名
3. **内存限制**: 聊天历史和向量库使用内存存储，重启后数据丢失
4. **Ollama 依赖**: 如不使用本地模型，可注释相关配置
5. **重复依赖**: `pom.xml` 中 `spring-ai-pdf-document-reader` 声明了两次，建议清理

## 常见问题

### Q: 如何切换 AI 模型？

A: 修改 `application.properties` 中的模型配置，或在 `CommonConfiguration` 中调整 ChatClient 的默认选项。

### Q: PDF 问答效果不佳？

A: 检查以下几点：
- PDF 文件是否正确上传和解析
- 向量相似度阈值是否合适（当前 0.6）
- topK 参数是否合理（当前返回 2 条）

### Q: 如何持久化聊天历史？

A: 当前使用内存存储。如需持久化，可实现 `ChatHistoryRepository` 接口，将数据存储到数据库或 Redis。

## 联系方式

如有问题或建议，请联系项目维护者。

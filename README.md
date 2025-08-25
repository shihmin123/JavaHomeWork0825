<h1># 🎵 音樂清單管理系統</h2>

> Java Swing + MySQL 的 MVC 專案，提供帳號管理、音樂清單管理、圖表分析與管理者後台等功能。



<h2>## 📌 專案簡介</h2>
本專案是一個 **音樂清單管理系統**，採用 **MVC 架構**；支援一般使用者與管理者兩種角色，並提供登入/註冊、清單搜尋與新增、播放預覽、統計圖表，以及報表/技術支援等功能。



<h2>## 🚀 功能列表</h2>
- **帳號管理**：使用者登入 / 註冊、權限驗證。
- **使用者中心**：檢視與修改個人資料（暱稱、聯絡方式…）。  
- **音樂清單管理**：瀏覽全部清單，支援**模糊**與**精準**搜尋，並可新增歌曲。
- **播放 / 預覽**：支援 YouTube 預覽播放。
- **統計圖表**：顯示使用或收藏趨勢分析。
- **管理者後台**：檢視與維護所有使用者 / 所有音樂、圖表查詢。
- **報表與技術支援**：可輸出或截圖報表，協助後續維運。

<h2>📂 目錄結構（建議）</h2>
project-root/
├─ controller/                      # Swing UI & 事件流程
│  ├─ LoginFrame.java               # 登入介面
│  ├─ RegisterFrame.java            # 註冊介面
│  ├─ UserCenterFrame.java          # 使用者中心
│  ├─ AddMusicFrame.java            # 搜尋並新增音樂
│  ├─ MusicListFrame.java           # 顯示/重整音樂清單
│  ├─ YoutubePreviewFrame.java      # YouTube 影片播放預覽
│  ├─ EditProfileFrame.java         # 使用者修改個人資料
│  ├─ AdminDashboardFrame.java      # 管理員總覽
│  ├─ AdminMusicFrame.java          # 管理所有音樂
│  └─ AdminUsersFrame.java          # 管理所有使用者

├─ dao/                             # 資料存取介面
│  ├─ UserDao.java                  # 使用者資料存取介面
│  └─ MusicDao.java                 # 音樂資料存取介面

├─ dao/impl/                        # DAO 實作
│  ├─ UserDaoImpl.java              # UserDao 的實作
│  └─ MusicDaoImpl.java             # MusicDao 的實作

├─ model/                           # 資料模型
│  ├─ User.java                     # 對應 User 資料表
│  └─ Music.java                    # 對應 Music 資料表

├─ service/                         # 業務邏輯
│  ├─ UserService.java              # 使用者相關業務邏輯
│  └─ MusicService.java             # 音樂相關業務邏輯

├─ service/impl/                    # Service 實作
│  ├─ UserServiceImpl.java          # UserService 的實作
│  └─ MusicServiceImpl.java         # MusicService 的實作

├─ util/                            # 共用工具
│  ├─ AdminMusicUtil.java           # 管理員操作音樂的工具方法
│  ├─ AdminUsersUtil.java           # 管理員操作使用者的工具方法
│  ├─ Auth.java                     # 登入驗證與權限檢查
│  ├─ DbConnection.java             # MySQL 連線工具
│  ├─ MusicListUtil.java            # 音樂清單操作工具
│  ├─ PasswordUtil.java             # 密碼加密與驗證
│  └─ YouTubeUtil.java              # YouTube 播放與網址解析



<h2>開發環境</h2>

語言：Java

UI：Swing（JFrame, JDialog）

資料庫：MySQL

架構：MVC

連線：DbConnection.java 提供資料庫連線封裝。

<h2>🏁 快速開始（示例）</h2>

依你實際的專案調整；以下為通用步驟。

建立 MySQL 資料庫與帳號，匯入 schema（若有 SQL 檔）。

在專案設定資料中填入 DB 連線資訊（如 DbConnection.java / config.properties）。

以 IDE（Eclipse/IntelliJ）開啟專案並執行。

以預設帳號或自行註冊登入系統。

<h2>🔐 權限與角色</h2>

User：瀏覽 / 搜尋 / 新增到個人清單、播放、修改個資。

Admin：管理所有使用者與音樂、圖表查詢。



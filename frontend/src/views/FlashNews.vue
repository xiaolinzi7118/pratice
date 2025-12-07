<template>
  <div class="flash-news-page">
    <el-card class="preview-card">
      <template #header>
        <div class="card-header">
          <span class="card-title">快讯预览</span>
        </div>
      </template>

      <!-- 手机模型容器 -->
      <div class="phone-container">
        <!-- 手机外框 -->
        <div class="phone-frame">
          <!-- 手机顶部（状态栏区域） -->
          <div class="phone-top">
            <div class="phone-notch"></div>
            <div class="phone-status-bar">
              <span class="status-time">9:41</span>
              <div class="status-icons">
                <span class="signal">📶</span>
                <span class="wifi">📶</span>
                <span class="battery">🔋</span>
              </div>
            </div>
          </div>

          <!-- 手机屏幕内容 -->
          <div class="phone-screen">
            <!-- 快讯列表 -->
            <div class="flash-news-list">
              <div
                v-for="(item, index) in flashNewsList"
                :key="index"
                class="news-item"
              >
                <div class="news-time">{{ item.time }}</div>
                <div class="news-content">
                  <div class="news-title">{{ item.title }}</div>
                  <div class="news-summary">{{ item.summary }}</div>
                </div>
              </div>
            </div>
          </div>

          <!-- 手机底部（导航栏区域） -->
          <div class="phone-bottom">
            <div class="phone-home-indicator"></div>
          </div>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'

// 快讯数据
const flashNewsList = ref([
  {
    time: '2分钟前',
    title: '电气风电：与财务公司签署协议，存款限额56亿、贷款限额73亿，用于金融服务',
    summary: ''
  },
  {
    time: '4分钟前',
    title: '中际旭创：3.2T光模块产品仍在研发中',
    summary: ''
  },
  {
    time: '5分钟前',
    title: '贝宁政变：部分军人发动政变，总统府称局势已控制',
    summary: '12月7日，部分贝宁军人通过国家电视台宣布解除总统塔隆职务，但总统府随后表示塔隆安全，军方已重新控制局势。'
  },
  {
    time: '10分钟前',
    title: '连霍高速事故：应急管理部派工作组赶赴现场',
    summary: '12月6日22时许，G30连霍高速（K3906公里）发生多车追尾事故，造成9人死亡、7人受伤。'
  },
  {
    time: '15分钟前',
    title: '市场动态：A股三大指数集体收涨',
    summary: '今日A股市场表现强劲，三大指数均收涨，成交量较前一交易日有所放大。'
  },
  {
    time: '20分钟前',
    title: '科技新闻：新一代芯片技术取得突破',
    summary: '某科技公司宣布在芯片制造技术上取得重大突破，性能提升30%。'
  }
])
</script>

<style scoped>
.flash-news-page {
  width: 100%;
}

.preview-card {
  min-height: auto;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.card-title {
  font-size: 18px;
  font-weight: 600;
  color: #303133;
}

/* 手机容器 */
.phone-container {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 10px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 20px;
}

/* 手机外框 */
.phone-frame {
  width: 375px;
  height: 730px;
  background: #000;
  border-radius: 40px;
  padding: 8px;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
  position: relative;
}

/* 手机屏幕 */
.phone-screen {
  width: 100%;
  height: calc(100% - 120px);
  background: #f5f5f5;
  border-radius: 32px;
  overflow: hidden;
  position: relative;
}

/* 手机顶部 */
.phone-top {
  width: 100%;
  height: 60px;
  background: #000;
  border-radius: 32px 32px 0 0;
  position: relative;
}

.phone-notch {
  position: absolute;
  top: 0;
  left: 50%;
  transform: translateX(-50%);
  width: 150px;
  height: 30px;
  background: #000;
  border-radius: 0 0 20px 20px;
  z-index: 10;
}

.phone-status-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 8px 20px;
  color: #fff;
  font-size: 14px;
  font-weight: 600;
  height: 100%;
  position: relative;
  z-index: 5;
}

.status-icons {
  display: flex;
  gap: 5px;
  font-size: 12px;
}

/* 手机底部 */
.phone-bottom {
  width: 100%;
  height: 60px;
  background: #000;
  border-radius: 0 0 32px 32px;
  display: flex;
  justify-content: center;
  align-items: center;
  padding-top: 10px;
}

.phone-home-indicator {
  width: 134px;
  height: 5px;
  background: #fff;
  border-radius: 3px;
  opacity: 0.3;
}

/* 快讯列表 */
.flash-news-list {
  padding: 12px 16px;
  height: 100%;
  overflow-y: auto;
  background: #fff;
}

.news-item {
  padding: 12px 0;
  border-bottom: 1px solid #f0f0f0;
  display: flex;
  gap: 12px;
}

.news-item:last-child {
  border-bottom: none;
}

.news-time {
  flex-shrink: 0;
  font-size: 12px;
  color: #999;
  width: 60px;
  padding-top: 2px;
}

.news-content {
  flex: 1;
}

.news-title {
  font-size: 15px;
  color: #333;
  line-height: 22px;
  margin-bottom: 8px;
  font-weight: 500;
}

.news-summary {
  font-size: 13px;
  color: #666;
  line-height: 20px;
  margin-top: 4px;
}

/* 滚动条样式 */
.flash-news-list::-webkit-scrollbar {
  width: 4px;
}

.flash-news-list::-webkit-scrollbar-track {
  background: #f1f1f1;
}

.flash-news-list::-webkit-scrollbar-thumb {
  background: #ccc;
  border-radius: 2px;
}

.flash-news-list::-webkit-scrollbar-thumb:hover {
  background: #999;
}
</style>


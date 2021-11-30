<template>
  <div class="main">
    <el-row>
      <!--左侧用户列表-->
      <el-col :span="4">
        <el-card class="left-card">
          <!--标题-->
          <div class="header">
            <el-button autofocus @click="clickGroupChat">
              群聊</el-button>
            <span v-if="'群聊'===chatUser" class="chatting">
                <i class="el-icon-chat-dot-round" ></i>
                chatting</span>
            <hr>
            <div style="text-align: center">在线用户</div>
          </div>
          <!--用户列表-->
          <div style="height: 200px;overflow: auto">
            <div v-for="(user,index) in users" class="content" :key="index">
              <div v-if="user.username!=='undefined'">
                <el-button @click="chatUser = user.username
              content = ''">
                  <span >{{ user.username }}</span>
                  <!--当用户点击不同聊天图标时，更换当前聊天对象-->
                </el-button>
                <span v-if="user.username===chatUser" class="chatting">
                <i class="el-icon-chat-dot-round" ></i>
                chatting</span>
              </div>
            </div>
          </div>
        </el-card>
      </el-col>

      <!--右侧聊天列表-->
      <el-col :span="20">
        <div class="right-card">
          <div class="header">{{ chatUser }}</div>
          <!--绑定content为此标签的innerHTML，content需要动态渲染上去-->
          <div class="content" v-html="content" style="margin: 10px"></div>
          <div class="footer">
            <textarea class="textarea" v-model="text"></textarea>
            <div class="send">
              <el-button type="primary" size="medium" @click="send">发送</el-button>
            </div>
          </div>
        </div>
      </el-col>
    </el-row>

  </div>
</template>

<script>
let socket
export default {
  name: 'ChatRoom',
  data () {
    return {
      // 在线用户数组
      users: [],
      // 当前聊天的对象
      chatUser: '',
      // innerHTML
      content: '',
      // 与textarea双向绑定的属性
      text: '',
      // 当前用户
      user: {},
      // 存放客户端发送来的消息
      messages: []
    }
  },
  methods: {
    //
    clickGroupChat () {
      this.chatUser = '群聊'
      this.content = ''
    },
    // 组件挂载完毕后进行初始化
    init () {
      // 获取当前对象：如果sessionStorage中user存在，则将其反序列化赋值给user，否则赋值为空对象
      this.user = sessionStorage.getItem('user') ? JSON.parse(sessionStorage.getItem('user')) : {}
      // 获取当前用户名
      const username = this.user.username
      const _this = this
      //
      if (typeof (WebSocket) === 'undefined') {
        this.$message.error('当前浏览器不支持websocket协议')
      } else {
        console.log('当前浏览器支持websocket协议')
        // const socketUrl = 'ws://localhost:8080/chat/' + username
        const socketUrl = 'ws://8.133.163.7:8080/chat/' + username
        if (socket != null) {
          // 还存在socket，那么关闭
          socket.close()
          socket = null
        }
        // 开启一个socket服务
        socket = new WebSocket(socketUrl)
        // 打开事件
        socket.onopen = function () {
          console.log('websocket已打开')
        }
        // 接收消息事件（获得客户端的消息）【注意在此之内的this指socket对象】
        socket.onmessage = function (msg) {
          console.log('收到数据：' + msg.data)
          // 对收到的json数据反序列化 {"users": [{"username": "zhang"},{ "username": "admin"}]}
          const data = JSON.parse(msg.data)
          if (data.users) { // 客户端发来的msg中包含users，表明为群发
            // 获取当前连接的所有用户信息，并且排除自身【filter() 方法创建一个新数组, 其包含通过所提供函数实现的测试的所有元素】
            _this.users = data.users.filter(user => user.username !== username)
          } if (data.to === '群聊') { // from记录的是谁发的，即remoteUser，发送给nowUser（群聊）
            _this.createContent(data.from, '群聊', data.text)
          } else { // 客户端发来的msg中不包含users，那么就不是群发，而是单聊，发送过来的就是聊天文本json数据，比如// {"from": "zhang", "text": "hello"}
            if (data.from === _this.chatUser) { // 发送方from和当前的聊天对象相同
              _this.createContent(data.from, null, data.text)
            }
          }
        }
      }
    },
    send () {
      if (!this.chatUser) {
        return this.$message.warning('请选择聊天对象')
      } else if (!this.text) {
        return this.$message.warning('请输入内容')
      } else if (typeof WebSocket === 'undefined') {
        return this.$message.warning('您的浏览器不支持WebSocket')
      } else if (this.chatUser === '群聊') { // 是群聊
        const message = {
          from: this.user.username,
          to: '群聊',
          text: this.text
        }
        // 将json对象序列化，并发送给服务端
        socket.send(JSON.stringify(message))
        this.text = ''
      } else { // 私聊
        // 组装待发送的消息
        const message = {
          from: this.user.username,
          to: this.chatUser,
          text: this.text
        }
        // 将json对象序列化，并发送给服务端
        socket.send(JSON.stringify(message))
        this.messages.push({
          user: this.user.username,
          text: this.text
        })
        // 构建本人消息
        this.createContent(null, this.user.username, this.text)
        this.text = ''
      }
    },
    // 将json的聊天消息记录转换成HTML
    // nowUser表示是否是当前用户发送的消息
    // 根据不同的用户渲染不同的HTML
    createContent (from, to, text) {
      let html
      if (to === '群聊' && this.chatUser === '群聊') { // 是群聊并且当前是群聊界面
        if (from === this.user.username) { // 是群聊并且是我发的
          html = '<font color="#32cd32">' + '我：' + text + '</font>' + '<hr/>'
        } else { // 是群聊但不是我发的
          html = '<font color="#87cefa">' + from + '：' + text + '</font>' + '<hr/>'
        }
      } else if (to === '群聊' && this.chatUser !== '群聊') { // 是群聊但不是群聊界面
        // 什么都不做
        html = ''
      } else { // 不是群聊
        if (to) { // 是我发的
          html = '<font color="#32cd32">' + '我：' + text + '</font>' + '<hr/>'
        } else if (from) { // 不是我发的
          html = '<font color="#87cefa">' + this.chatUser + '：' + text + '</font>' + '<hr/>'
        }
      }
      this.content += html
    }
  },
  mounted () {
    this.init()
    this.clickGroupChat()
  }
}
</script>

<style scoped lang="less">
.el-row {
  margin-top: 10px;
}

.main {
  //padding: 10px;
  background-color: #fff;
  //margin-bottom: 50px
}

.left-card {
  width: 300px;
  height: 300px;
  color: #333;
  margin:0 30px;

  .header {
    padding-bottom: 10px;
    border-bottom: 1px solid #ccc
  }

  .content {
    padding: 10px 0;
  }
}

.chatting {
  font-size: 12px;
  color: limegreen;
  margin-left: 5px
}

.right-card {
  width: 800px;
  margin: 0 auto;
  background-color: white;
  border-radius: 5px;
  box-shadow: 0 0 10px #ccc;

  .header {
    text-align: center;
    line-height: 50px;
  }

  .content {
    height: 350px;
    overflow: auto; //解决溢出问题
    border-top: 1px solid #ccc
  }

  .footer {
    .textarea {
      height: 160px;
      width: 100%;
      padding: 20px 0;
      outline: none; //隐藏轮廓
      border: none;
      border-top: 1px solid #ccc;
      border-bottom: 1px solid #ccc;
    }

    .send {
      text-align: right;
      padding-right: 10px;
    }
  }
}

.tip {
  color: white;
  text-align: center;
  border-radius: 10px;
  font-family: sans-serif;
  padding: 10px;
  width: auto;
  display: inline;
}

.right {
  background-color: deepskyblue;
}

.left {
  background-color: forestgreen;
}
</style>

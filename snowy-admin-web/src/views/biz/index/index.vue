<template>
    <div class="phone-details">
      <h1>{{ phoneName }} 拆解详情</h1>
      <div class="phone-image">
        <img :src="phoneImage" :alt="phoneName" />
      </div>
      <div class="phone-parts">
        <h2>零部件列表</h2>
        <ul>
          <li v-for="(part, index) in phoneParts" :key="index">{{ part }}</li>
        </ul>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    name: "PhoneDetails",
    data() {
      return {
        phoneName: "iPhone X",
        phoneImage: "/images/iphone-x.png",
        phoneParts: [
          "屏幕",
          "电池",
          "摄像头",
          "麦克风",
          "扬声器",
          "按键",
          "天线",
          "主板"
        ]
      };
    }
  };
  </script>
  
  <style>
  .phone-details {
    max-width: 800px;
    margin: auto;
    padding: 20px;
  }
  
  .phone-image {
    text-align: center;
  }
  
  .phone-image img {
    max-width: 500px;
  }
  
  .phone-parts {
    margin-top: 20px;
  }
  
  .phone-parts ul {
    list-style: none;
    padding: 0;
  }
  
  .phone-parts li {
    margin: 10px 0;
  }
  </style>
  
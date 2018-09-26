<template>
  <div class="hello">
    <el-select v-model="serviceValue" placeholder="请选择服务" @change="serviceValueChange">
      <el-option v-for="service in serviceList" :key="service.value" :label="service.label" :value="service.value">
      </el-option>
    </el-select>

    <el-select v-model="interfaceValue" placeholder="请选择接口" @change="interfaceValueChange">
      <el-option v-for="service in interfaceList" :key="service.value" :label="service.label" :value="service.value">
      </el-option>
    </el-select>

    <el-select v-model="methodValue" placeholder="请选择方法">
      <el-option v-for="service in methodList" :key="service.value" :label="service.label" :value="service.value">
      </el-option>
    </el-select>
  </div>
</template>

<script>
export default {
  name: 'HelloWorld',
  data () {
    return {
      serviceList: [],
      serviceValue: '',
      interfaceList: [],
      interfaceValue: '',
      methodList: [],
      methodValue: ''
    }
  },
  created() {
    this.loadServiceList();
  },
  methods: {
    async loadServiceList() {
      let res = await this.$http.get('/service/list');
      if (res.data.code !== 0) {
        console.error('错误: ' + res.data.data)
        return;
      }

      this.resetSelectValue(["service", "interface", "method"]);
      let data = res.data.data;
      for (let i = 0; i < data.length; i++) {
        this.serviceList.push({"value":data[i], "label":data[i]})
      }
    },
    serviceValueChange() {
      this.loadInterfaceList(this.serviceValue)
    },

    async loadInterfaceList(service) {
      let res = await this.$http.get('/service/interface/list/' + service);
      if (res.data.code !== 0) {
        console.error('错误: ' + res.data.data)
        return;
      }

      this.resetSelectValue(["interface", "method"]);
      let data = res.data.data;
      for (let i = 0; i < data.length; i++) {
        this.interfaceList.push({"value":data[i], "label":data[i]})
      }
    },
    interfaceValueChange() {
      this.loadMethodList(this.serviceValue, this.interfaceValue)
    },

    async loadMethodList(service, inter) {
      let res = await this.$http.get('/service/method/list/' + service + '/' + inter);
      if (res.data.code !== 0) {
        console.error('错误: ' + res.data.data)
        return;
      }

      this.resetSelectValue(["method"]);
      let data = res.data.data;
      for (let i = 0; i < data.length; i++) {
        this.methodList.push({"value":data[i], "label":data[i]})
      }
    },

    /**
     * 重置select & value值
     */
    resetSelectValue(array) {
      for (let i = 0; i < array.length; i++) {
        switch (array[i]) {
          case 'service': {
            this.serviceList = [];
            this.serviceValue = '';
            break;
          }
          case 'interface': {
            this.interfaceList = [];
            this.interfaceValue = '';
            break;
          }
          case 'method': {
            this.methodList = [];
            this.methodValue = '';
            break;
          }
          default: {
            break;
          }
        }
      }
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
a {
  color: #42b983;
}
</style>

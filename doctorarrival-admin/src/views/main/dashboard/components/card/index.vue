<template>
  <div class="card-list">
    <Row v-for="row in list" :key="row.id" :row="row" />
  </div>
</template>

<script lang="js">
import { hospitalStatistics, userStatistics, orderStatistics } from "@/api/statistics";
import { defineComponent, ref } from "vue";
import Row from "./row.vue";
export default defineComponent({
  components: {
    Row
  },
  setup() {
    const list = ref([
      { id: 1, name: "注册用户", data: "0", color: "#4e73df", icon: "sfont system-yonghu" },
      { id: 2, name: "接入医院", data: "0", color: "#1cc88a", icon: "sfont system-xiaoxi" },
      { id: 3, name: "订单总量", data: "0", color: "#36b9cc", icon: "sfont system-shuliang_mianxing" },
      { id: 4, name: "订单金额", data: "0", color: "#f6c23e", icon: "sfont system-jindutiaoshouyidaozhang" }
    ]);
    userStatistics().then(res => {
      list.value[0].data = res.total;
    });
    hospitalStatistics().then(res => {
      list.value[1].data = res.count;
    });
    orderStatistics().then(res => {
      list.value[2].data = res.total;
      list.value[3].data = res.totalAmount;
    });
    return {
      list
    };
  }
});
</script>

<style lang="scss" scoped>
.card-list {
  width: calc(100% + 20px);
  margin-left: -10px;
  display: flex;
  flex-wrap: wrap;
}
</style>

<template>
  <div class="nav-container page-component">
    <!-- 左侧 导航 -->
    <div class="nav left-nav">
      <div class="nav-item selected">
        <span class="v-link selected dark" @click="gotoHospital">预约挂号</span>
      </div>
      <div class="nav-item">
        <span class="v-link clickable dark" @click="gotoHospitalDetail">医院详情</span>
      </div>
      <div class="nav-item">
        <span class="v-link clickable dark" @click="gotoHospitalNotice">预约须知</span>
      </div>
      <div class="nav-item">
        <span class="v-link clickable dark"> 停诊信息 </span>
      </div>
      <div class="nav-item">
        <span class="v-link clickable dark"> 查询/取消 </span>
      </div>
    </div>

    <!-- 右侧 内容 -->
    <div class="page-container">
      <div class="hospital-source-list">
        <!-- 医院名称 / 大科室 / 科室名 -->
        <div class="header-wrapper" style="justify-content: normal">
          <span class="v-link clickable" @click="gotoHospital(hospital.hospitalCode)">{{ hospital.hospitalName }}</span>
          <div class="split"></div>
          <div>{{ department.primaryDepartmentName }}</div>
        </div>
        <div class="title mt20">{{ department.departmentName }}</div>

        <!-- 号源列表 -->
        <div class="mt60">
          <div class="title-wrapper">{{ workDateTips }}</div>
          <!-- 日期 号源 -->
          <div class="calendar-list-wrapper">
            <div class="calendar-item space" style="width: 124px" v-for="schedule in scheduleList.records"
              :key="schedule.workDate"
              :class="{ 'gray': schedule.status < 1, 'selected': schedule.workDate == activeSchedule.workDate }"
              @click="selectDate(schedule)">
              <div class="date-wrapper">
                <span>{{ schedule.workDate }}</span><span class="week">{{ schedule.dayOfWeek }}</span>
              </div>
              <div class="status-wrapper" v-if="schedule.status === 1">
                {{ schedule.availableNumber }} / {{ schedule.reservedNumber }}
              </div>
              <div class="status-wrapper" v-if="schedule.status === 0">停约</div>
              <div class="status-wrapper" v-if="schedule.status === -1">停诊</div>

            </div>
          </div>

          <!-- 分页 -->
          <el-pagination class="pagination" style="justify-content: center;" layout="prev, pager, next"
            :current-page="scheduleList.current" :total="scheduleList.total" :page-size="scheduleList.size"
            @current-change="getSchedulePageList">
          </el-pagination>
        </div>

        <!-- TODO 即将放号 -->

        <!-- 上午号源 -->
        <div class="mt60" v-if="activeScheduleDetail">
          <div class="">
            <div class="list-title">
              <div class="block"></div>
              上午号源
            </div>
            <template v-for="item in activeScheduleDetail">
              <div v-if="item.workTime == 0">
                <div class="list-item">
                  <div class="item-wrapper">
                    <div class="title-wrapper">
                      <div class="title">{{ item.doctorTitle }}</div>
                      <div class="split"></div>
                      <div class="name">{{ item.doctorName }}</div>
                    </div>
                    <div class="special-wrapper">{{ item.skill }}</div>
                  </div>
                  <div class="right-wrapper">
                    <div class="fee">￥{{ item.amount }}</div>
                    <div class="button-wrapper">
                      <div class="v-button" @click="booking(item)"
                        :style="item.availableNumber <= 0 ? 'background-color: #7f828b;' : ''">
                        <span>剩余<span class="number">{{ item.availableNumber }}</span></span>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </template>
          </div>
        </div>

        <!-- 下午号源 -->
        <div class="mt60" v-if="activeScheduleDetail">
          <div class="">
            <div class="list-title">
              <div class="block"></div>
              下午号源
            </div>
            <template v-for="item in activeScheduleDetail">
              <div v-if="item.workTime == 1">
                <div class="list-item">
                  <div class="item-wrapper">
                    <div class="title-wrapper">
                      <div class="title">{{ item.doctorTitle }}</div>
                      <div class="split"></div>
                      <div class="name">{{ item.doctorName }}</div>
                    </div>
                    <div class="special-wrapper">{{ item.skill }}</div>
                  </div>
                  <div class="right-wrapper">
                    <div class="fee">￥{{ item.amount }}</div>
                    <div class="button-wrapper">
                      <div class="v-button" @click="booking(item)"
                        :style="item.availableNumber <= 0 ? 'background-color: #7f828b;' : ''">
                        <span>剩余<span class="number">{{ item.availableNumber }}</span></span>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </template>
          </div>
        </div>
      </div>
    </div>

  </div>
</template>

<script setup>
import {
  findHospitalByHospitalCode,
  getDepartmentByHospitalCodeAndDepartmentCode,
  getSchedulePage,
  getScheduleByHospitalCodeAndDepartmentCodeAndWorkDate
} from '@/api/hospital'

const route = useRoute()
const router = useRouter()
const { hospitalCode, departmentCode } = route.query

const hospital = ref({})
const department = ref({})

const scheduleList = ref([])
const activeSchedule = ref({})

const activeScheduleDetail = ref({})

// 获取医院信息
function initHospital() {
  findHospitalByHospitalCode(hospitalCode).then(res => {
    hospital.value = res
  })
}
initHospital()

// 回到医院页面
function gotoHospital(hospitalCode) {
  router.push(`/hospital/${hospitalCode}`)
}

// 获取科室信息
function initDepartment() {
  getDepartmentByHospitalCodeAndDepartmentCode(hospitalCode, departmentCode).then(res => {
    department.value = res
  })
}
initDepartment()

// 获取号源信息
function getSchedulePageList(page = 1) {
  getSchedulePage(hospitalCode, departmentCode, page).then(res => {
    scheduleList.value = res
    selectDate(res.records[0])
  })
}
getSchedulePageList()

// 计算属性 {{ scheduleList.records[0].workDate }} —— {{ scheduleList.records[scheduleList.records.length - 1].workDate }}
const workDateTips = computed(() => {
  if (scheduleList.value.records) {
    return `${scheduleList.value.records[0].workDate} —— ${scheduleList.value.records[scheduleList.value.records.length - 1].workDate}`
  }
})

// 选择日期
function selectDate(schedule) {
  activeSchedule.value = schedule
  const { hospitalCode, departmentCode, workDate } = schedule
  getScheduleByHospitalCodeAndDepartmentCodeAndWorkDate(hospitalCode, departmentCode, workDate).then(res => {
    activeScheduleDetail.value = res
  })
}

// 预约挂号
function booking(item) {
  ElMessage({
    message: `正在预约 ${item.id}...`,
    type: 'success'
  })
}
</script>

<style scoped>
@import 'assets/css/hospital_personal.css';
@import 'assets/css/hospital.css';
</style>
import json
from datetime import datetime, timedelta

# 从文件中读取JSON数据
with open("./schedules.json", "r", encoding="utf-8") as f:
    schedules = json.load(f)

# 获取第一个医生的工作日期，并计算出与今天的日期差
first_work_date = datetime.strptime(schedules[0]["workDate"], "%Y-%m-%d %H:%M:%S")
date_diff = (datetime.now() - first_work_date).days

# 遍历并修改每个医生的工作日期
for schedule in schedules:
    work_date = datetime.strptime(schedule["workDate"], "%Y-%m-%d %H:%M:%S")
    work_date += timedelta(days=date_diff)
    schedule["workDate"] = work_date.strftime("%Y-%m-%d %H:%M:%S")

# 将修改后的数据转回JSON格式并写入文件
with open("./schedules.json", "w", encoding="utf-8") as f:
    json.dump(schedules, f, indent=2, ensure_ascii=False)

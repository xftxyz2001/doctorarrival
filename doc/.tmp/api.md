# DictAdminController

## POST 导入数据字典

POST /admin/common/dict/import

导入数据字典
接收并解析上传的文件（通常是Excel格式），用于导入系统中的数据字典项。要求上传的文件不能为空，成功导入则返回true，否则可能抛出异常或返回false。

> Body Parameters

```yaml
file: string

```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|body|body|object| no |none|
|» file|body|string(binary)| yes |none|

> Response Examples

> 成功

```json
false
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|boolean|

## POST 导出数据字典

POST /admin/common/dict/export

导出数据字典
导出系统中现有的全部数据字典项至Excel文件。响应体是一个资源实体(Resource)，其内容类型为二进制流(application/octet-stream)，并自动设置了HTTP头以便浏览器识别并下载文件，文件名为'dict.xlsx'。

> Response Examples

> 成功

```json
{}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

# DictApiController

## GET 通过ID获取项

GET /api/common/dict/id/{id}

通过ID获取项
根据传入的数据字典ID（最小值为1）获取对应的字典项信息。

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|id|path|string| yes |none|

> Response Examples

> 成功

```json
{
  "id": 0,
  "parentId": 0,
  "name": "",
  "value": "",
  "dictCode": "",
  "hasChildren": false
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|[Dict](#schemadict)|

## GET 通过父项ID获取子项

GET /api/common/dict/children/id/{parentId}

通过父项ID获取子项
根据传入的父级数据字典ID（最小值为1）获取其所有的子级字典项信息。

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|parentId|path|string| yes |none|

> Response Examples

> 成功

```json
[
  {
    "id": 0,
    "parentId": 0,
    "name": "",
    "value": "",
    "dictCode": "",
    "hasChildren": false
  }
]
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|*anonymous*|[[Dict](#schemadict)]|false|none||none|
|» id|integer¦null|false|none||id 手动输入|
|» parentId|integer¦null|false|none||上级id|
|» name|string¦null|false|none||名称|
|» value|string¦null|false|none||值|
|» dictCode|string¦null|false|none||编码|
|» hasChildren|boolean¦null|false|none||是否包含子节点|

## GET 通过字典编码获取子项

GET /api/common/dict/children/code/{dictCode}

通过字典编码获取子项
根据传入的数据字典编码获取对应的子级字典项信息。

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|dictCode|path|string| yes |none|

> Response Examples

> 成功

```json
[
  {
    "id": 0,
    "parentId": 0,
    "name": "",
    "value": "",
    "dictCode": "",
    "hasChildren": false
  }
]
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|*anonymous*|[[Dict](#schemadict)]|false|none||none|
|» id|integer¦null|false|none||id 手动输入|
|» parentId|integer¦null|false|none||上级id|
|» name|string¦null|false|none||名称|
|» value|string¦null|false|none||值|
|» dictCode|string¦null|false|none||编码|
|» hasChildren|boolean¦null|false|none||是否包含子节点|

## GET 通过字典编码获取子项以Map形式返回

GET /api/common/dict/inner/map/code/{dictCode}

通过字典编码获取子项以Map形式返回
根据传入的数据字典编码，以键值对的形式返回对应的子级字典项信息集合。

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|dictCode|path|string| yes |none|

> Response Examples

> 成功

```json
{
  "": ""
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

## GET 通过省市区编码获取省市区名称

GET /api/common/dict/inner/administrative/divisions/list

通过省市区编码获取省市区名称
根据输入的省、市、区编码，返回对应的省市区名称列表。

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|provinceCode|query|string| yes |none|
|cityCode|query|string| yes |none|
|districtCode|query|string| yes |none|

> Response Examples

> 成功

```json
[
  ""
]
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

# HospitalSetAdminController

## POST 新增医院设置

POST /admin/hospital/set/save

新增医院设置
接收一个非空的HospitalSet对象作为新增数据，成功保存则返回true。

> Body Parameters

```json
{
  "id": 0,
  "hospitalCode": "string",
  "hospitalName": "string",
  "apiUrl": "string",
  "signKey": "string",
  "contactsName": "string",
  "contactsPhone": "string",
  "status": 0,
  "createTime": "string",
  "updateTime": "string",
  "deleted": 0
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|body|body|[HospitalSet](#schemahospitalset)| no |none|

> Response Examples

> 成功

```json
false
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|boolean|

## PUT 修改医院设置

PUT /admin/hospital/set/update

修改医院设置
接收一个非空的HospitalSet对象作为更新数据，根据其ID进行更新操作，成功更新则返回true。

> Body Parameters

```json
{
  "id": 0,
  "hospitalCode": "string",
  "hospitalName": "string",
  "apiUrl": "string",
  "signKey": "string",
  "contactsName": "string",
  "contactsPhone": "string",
  "status": 0,
  "createTime": "string",
  "updateTime": "string",
  "deleted": 0
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|body|body|[HospitalSet](#schemahospitalset)| no |none|

> Response Examples

> 成功

```json
false
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|boolean|

## DELETE 删除医院设置

DELETE /admin/hospital/set/remove/id/{id}

删除医院设置
根据传入的ID（最小值为1）删除对应的医院设置，删除成功则返回true。

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|id|path|string| yes |none|

> Response Examples

> 成功

```json
false
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|boolean|

## DELETE 批量删除医院设置

DELETE /admin/hospital/set/remove/batch

批量删除医院设置
接收一个非空的Long类型的ID列表，批量删除指定的医院设置，删除成功则返回true。

> Body Parameters

```json
[
  0
]
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|body|body|array[integer]| no |none|

> Response Examples

> 成功

```json
false
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|boolean|

## GET 通过ID获取医院设置

GET /admin/hospital/set/id/{id}

通过ID获取医院设置
根据传入的ID（最小值为1）获取对应的医院设置信息。

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|id|path|string| yes |none|

> Response Examples

> 成功

```json
{
  "id": 0,
  "hospitalCode": "",
  "hospitalName": "",
  "apiUrl": "",
  "signKey": "",
  "contactsName": "",
  "contactsPhone": "",
  "status": 0,
  "createTime": "",
  "updateTime": "",
  "deleted": 0
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|[HospitalSet](#schemahospitalset)|

## POST 条件查询医院设置带分页

POST /admin/hospital/set/find

条件查询医院设置带分页
接收一个非空的HospitalSetQueryVO对象作为查询条件，以及可选的当前页码（默认为1，最小值为1）和每页大小（默认为20，最小值为1），返回符合条件的医院设置分页列表。

> Body Parameters

```json
{
  "hospitalName": "string",
  "hospitalCode": "string",
  "createTimeFrom": "string",
  "createTimeTo": "string"
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|current|query|integer| yes |none|
|size|query|integer| yes |none|
|body|body|[HospitalSetQueryVO](#schemahospitalsetqueryvo)| no |none|

> Response Examples

> 成功

```json
{
  "records": [
    {
      "id": 0,
      "hospitalCode": "",
      "hospitalName": "",
      "apiUrl": "",
      "signKey": "",
      "contactsName": "",
      "contactsPhone": "",
      "status": 0,
      "createTime": "",
      "updateTime": "",
      "deleted": 0
    }
  ],
  "total": 0,
  "size": 0,
  "current": 0,
  "orders": [
    {
      "column": "",
      "asc": false
    }
  ],
  "optimizeCountSql": false,
  "searchCount": false,
  "optimizeJoinOfCountSql": false,
  "maxLimit": 0,
  "countId": ""
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

## PUT 设置医院状态

PUT /admin/hospital/set/status/{id}/{status}

设置医院状态
根据传入的医院ID（最小值为1）和状态（0或1）更新医院状态，成功更新则返回true。

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|id|path|string| yes |none|
|status|path|string| yes |none|

> Response Examples

> 成功

```json
false
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|boolean|

## POST 医院数据统计

POST /admin/hospital/set/inner/statistic

医院数据统计
接收一个非空的HospitalStatisticVO对象，根据其中的参数进行医院数据统计计算，返回统计结果。

> Body Parameters

```json
{
  "count": 0,
  "from": "string",
  "to": "string"
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|body|body|[HospitalStatisticVO](#schemahospitalstatisticvo)| no |none|

> Response Examples

> 成功

```json
{
  "count": 0,
  "from": "",
  "to": ""
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|[HospitalStatisticVO](#schemahospitalstatisticvo)|

# HospitalSideApiController

## POST 医院申请加入

POST /api/hospital/side/join

医院申请加入
接收一个经过验证的HospitalJoinVO对象作为医院申请加入的信息，成功申请后返回一个包含密钥的资源实体(Resource)，该资源将以附件形式下载，文件名格式为'hospitalCode.key'。

> Body Parameters

```json
{
  "hospitalCode": "string",
  "hospitalName": "string",
  "apiUrl": "string",
  "contactsName": "string",
  "contactsPhone": "string",
  "hospitalType": "string",
  "provinceCode": "string",
  "cityCode": "string",
  "districtCode": "string",
  "address": "string",
  "logoData": "string",
  "intro": "string",
  "route": "string"
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|body|body|[HospitalJoinVO](#schemahospitaljoinvo)| no |none|

> Response Examples

> 成功

```json
{}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

## GET 更新对称加密密钥

GET /api/hospital/side/key/{hospitalCode}

更新对称加密密钥
根据传入的医院编码更新该医院的对称加密密钥，成功更新后返回新密钥字符串。

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|hospitalCode|path|string| yes |none|

> Response Examples

> 成功

```json
null
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|string|

## POST 更新医院信息

POST /api/hospital/side/hospital

更新医院信息
接收一个非空的EncryptionRequest对象，其中包含加密过的医院信息更新请求，成功更新后返回处理结果字符串。

> Body Parameters

```json
{
  "hospitalCode": "string",
  "data": "string"
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|body|body|[EncryptionRequest](#schemaencryptionrequest)| no |none|

> Response Examples

> 成功

```json
null
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|string|

## POST 更新科室信息

POST /api/hospital/side/department

更新科室信息
接收一个非空的EncryptionRequest对象，其中包含加密过的科室信息更新请求，成功更新后返回处理结果字符串。

> Body Parameters

```json
{
  "hospitalCode": "string",
  "data": "string"
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|body|body|[EncryptionRequest](#schemaencryptionrequest)| no |none|

> Response Examples

> 成功

```json
null
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|string|

## POST 更新排班信息

POST /api/hospital/side/schedule

更新排班信息
接收一个非空的EncryptionRequest对象，其中包含加密过的排班信息更新请求，成功更新后返回处理结果字符串。

> Body Parameters

```json
{
  "hospitalCode": "string",
  "data": "string"
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|body|body|[EncryptionRequest](#schemaencryptionrequest)| no |none|

> Response Examples

> 成功

```json
null
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|string|

## POST 删除科室信息

POST /api/hospital/side/remove/department

删除科室信息
接收一个非空的EncryptionRequest对象，其中包含加密过的科室信息删除请求，成功删除后返回处理结果字符串。

> Body Parameters

```json
{
  "hospitalCode": "string",
  "data": "string"
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|body|body|[EncryptionRequest](#schemaencryptionrequest)| no |none|

> Response Examples

> 成功

```json
null
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|string|

## POST 删除排班信息

POST /api/hospital/side/remove/schedule

删除排班信息
接收一个非空的EncryptionRequest对象，其中包含加密过的排班信息删除请求，成功删除后返回处理结果字符串。

> Body Parameters

```json
{
  "hospitalCode": "string",
  "data": "string"
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|body|body|[EncryptionRequest](#schemaencryptionrequest)| no |none|

> Response Examples

> 成功

```json
null
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|string|

## POST 批量更新科室信息

POST /api/hospital/side/departments

批量更新科室信息
接收一个非空的EncryptionRequest对象，其中包含加密过的批量科室信息更新请求，成功更新后返回处理结果字符串。

> Body Parameters

```json
{
  "hospitalCode": "string",
  "data": "string"
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|body|body|[EncryptionRequest](#schemaencryptionrequest)| no |none|

> Response Examples

> 成功

```json
null
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|string|

## POST 批量更新排班信息

POST /api/hospital/side/schedules

批量更新排班信息
接收一个非空的EncryptionRequest对象，其中包含加密过的批量排班信息更新请求，成功更新后返回处理结果字符串。

> Body Parameters

```json
{
  "hospitalCode": "string",
  "data": "string"
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|body|body|[EncryptionRequest](#schemaencryptionrequest)| no |none|

> Response Examples

> 成功

```json
null
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|string|

## POST 批量删除科室信息

POST /api/hospital/side/remove/departments

批量删除科室信息
接收一个非空的EncryptionRequest对象，其中包含加密过的批量科室信息删除请求，成功删除后返回处理结果字符串。

> Body Parameters

```json
{
  "hospitalCode": "string",
  "data": "string"
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|body|body|[EncryptionRequest](#schemaencryptionrequest)| no |none|

> Response Examples

> 成功

```json
null
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|string|

## POST 批量删除排班信息

POST /api/hospital/side/remove/schedules

批量删除排班信息
接收一个非空的EncryptionRequest对象，其中包含加密过的批量排班信息删除请求，成功删除后返回处理结果字符串。

> Body Parameters

```json
{
  "hospitalCode": "string",
  "data": "string"
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|body|body|[EncryptionRequest](#schemaencryptionrequest)| no |none|

> Response Examples

> 成功

```json
null
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|string|

## POST 提交订单

POST /api/hospital/side/order

提交订单
接收一个非空的OrderInfo对象作为订单提交信息，成功提交后返回true。

> Body Parameters

```json
{
  "id": 0,
  "userId": 0,
  "hospitalCode": "string",
  "hospitalName": "string",
  "departmentCode": "string",
  "departmentName": "string",
  "doctorName": "string",
  "doctorTitle": "string",
  "scheduleId": "string",
  "reserveDate": "string",
  "patientId": 0,
  "patientName": "string",
  "patientPhone": "string",
  "amount": 0,
  "orderStatus": 0,
  "remark": "string",
  "createTime": "string",
  "updateTime": "string",
  "isDeleted": 0
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|body|body|[OrderInfo](#schemaorderinfo)| no |none|

> Response Examples

> 成功

```json
false
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|boolean|

## POST 更新订单

POST /api/hospital/side/order/status

更新订单
接收一个非空的OrderInfo对象作为订单状态更新信息，成功更新后返回true。

> Body Parameters

```json
{
  "id": 0,
  "userId": 0,
  "hospitalCode": "string",
  "hospitalName": "string",
  "departmentCode": "string",
  "departmentName": "string",
  "doctorName": "string",
  "doctorTitle": "string",
  "scheduleId": "string",
  "reserveDate": "string",
  "patientId": 0,
  "patientName": "string",
  "patientPhone": "string",
  "amount": 0,
  "orderStatus": 0,
  "remark": "string",
  "createTime": "string",
  "updateTime": "string",
  "isDeleted": 0
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|body|body|[OrderInfo](#schemaorderinfo)| no |none|

> Response Examples

> 成功

```json
false
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|boolean|

# HospitalApiController

## GET 通过医院名称获取医院列表

GET /api/hospital/find/name

通过医院名称获取医院列表
根据输入的医院名称（不能为空白字符）搜索匹配的医院列表，返回HospitalVO对象列表。

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|hospitalName|query|string| yes |none|

> Response Examples

> 成功

```json
[
  {
    "hospitalCode": "",
    "hospitalName": "",
    "hospitalType": "",
    "province": "",
    "city": "",
    "district": "",
    "address": "",
    "logoData": "",
    "intro": "",
    "route": "",
    "bookingRule": {
      "cycle": 0,
      "releaseTime": "",
      "stopTime": "",
      "quitDay": 0,
      "quitTime": "",
      "rule": [
        ""
      ]
    }
  }
]
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|*anonymous*|[[HospitalVO](#schemahospitalvo)]|false|none||none|
|» hospitalCode|string¦null|false|none||医院编号|
|» hospitalName|string¦null|false|none||医院名称|
|» hospitalType|string¦null|false|none||医院类型|
|» province|string¦null|false|none||省|
|» city|string¦null|false|none||市|
|» district|string¦null|false|none||区|
|» address|string¦null|false|none||详情地址|
|» logoData|string¦null|false|none||医院logo|
|» intro|string¦null|false|none||医院简介|
|» route|string¦null|false|none||坐车路线|
|» bookingRule|[BookingRule](#schemabookingrule)|false|none||预约规则|
|»» cycle|integer¦null|false|none||预约周期|
|»» releaseTime|string¦null|false|none||放号时间|
|»» stopTime|string¦null|false|none||停挂时间|
|»» quitDay|integer¦null|false|none||退号截止天数（如：就诊前一天为-1，当天为0）|
|»» quitTime|string¦null|false|none||退号时间|
|»» rule|[string]¦null|false|none||预约规则|

## GET 通过医院编码获取医院

GET /api/hospital/find/code/{hospitalCode}

通过医院编码获取医院
根据提供的医院编码（不能为空白字符）获取对应医院的详细信息，返回HospitalVO对象。

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|hospitalCode|path|string| yes |none|

> Response Examples

> 成功

```json
{
  "hospitalCode": "",
  "hospitalName": "",
  "hospitalType": "",
  "province": "",
  "city": "",
  "district": "",
  "address": "",
  "logoData": "",
  "intro": "",
  "route": "",
  "bookingRule": {
    "cycle": 0,
    "releaseTime": "",
    "stopTime": "",
    "quitDay": 0,
    "quitTime": "",
    "rule": [
      ""
    ]
  }
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|[HospitalVO](#schemahospitalvo)|

## POST 条件查询医院带分页

POST /api/hospital/find/page

条件查询医院带分页
接收一个非空的HospitalQueryVO对象作为查询条件，以及可选的当前页码（默认为1，最小值为1）和每页大小（默认为10，最小值为1），返回按条件分页查询的医院列表，封装在IPage<HospitalVO>对象中。

> Body Parameters

```json
{
  "hospitalType": "string",
  "provinceCode": "string",
  "cityCode": "string",
  "districtCode": "string"
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|current|query|integer| yes |none|
|size|query|integer| yes |none|
|body|body|[HospitalQueryVO](#schemahospitalqueryvo)| no |none|

> Response Examples

> 成功

```json
{
  "records": [
    {
      "hospitalCode": "",
      "hospitalName": "",
      "hospitalType": "",
      "province": "",
      "city": "",
      "district": "",
      "address": "",
      "logoData": "",
      "intro": "",
      "route": "",
      "bookingRule": {
        "cycle": 0,
        "releaseTime": "",
        "stopTime": "",
        "quitDay": 0,
        "quitTime": "",
        "rule": [
          ""
        ]
      }
    }
  ],
  "total": 0,
  "size": 0,
  "current": 0,
  "orders": [
    {
      "column": "",
      "asc": false
    }
  ],
  "optimizeCountSql": false,
  "searchCount": false,
  "optimizeJoinOfCountSql": false,
  "maxLimit": 0,
  "countId": ""
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

## GET 通过医院编码获取预约规则

GET /api/hospital/find/rule/{hospitalCode}

通过医院编码获取预约规则
根据提供的医院编码（不能为空白字符）获取该医院的预约规则，返回BookingRule对象。

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|hospitalCode|path|string| yes |none|

> Response Examples

> 成功

```json
{
  "cycle": 0,
  "releaseTime": "",
  "stopTime": "",
  "quitDay": 0,
  "quitTime": "",
  "rule": [
    ""
  ]
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|[BookingRule](#schemabookingrule)|

# DepartmentApiController

## GET 通过医院编码获取科室列表

GET /api/hospital/department/hospital/code/{hospitalCode}

通过医院编码获取科室列表
根据传入的医院编码（不能为空白字符）获取该医院下的所有科室列表信息，返回的是DepartmentVO对象列表。

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|hospitalCode|path|string| yes |none|

> Response Examples

> 成功

```json
[
  {
    "hospitalCode": "",
    "departmentCode": "",
    "departmentName": "",
    "intro": "",
    "primaryDepartmentCode": "",
    "primaryDepartmentName": "",
    "children": [
      {
        "hospitalCode": "",
        "departmentCode": "",
        "departmentName": "",
        "intro": "",
        "primaryDepartmentCode": "",
        "primaryDepartmentName": "",
        "children": [
          {
            "hospitalCode": "",
            "departmentCode": "",
            "departmentName": "",
            "intro": "",
            "primaryDepartmentCode": "",
            "primaryDepartmentName": "",
            "children": []
          }
        ]
      }
    ]
  }
]
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|*anonymous*|[[DepartmentVO](#schemadepartmentvo)]|false|none||none|
|» hospitalCode|string¦null|false|none||医院编号|
|» departmentCode|string¦null|false|none||科室编号|
|» departmentName|string¦null|false|none||科室名称|
|» intro|string¦null|false|none||科室描述|
|» primaryDepartmentCode|string¦null|false|none||大科室编号|
|» primaryDepartmentName|string¦null|false|none||大科室名称|
|» children|[[DepartmentVO](#schemadepartmentvo)]¦null|false|none||子科室|
|»» hospitalCode|string¦null|false|none||医院编号|
|»» departmentCode|string¦null|false|none||科室编号|
|»» departmentName|string¦null|false|none||科室名称|
|»» intro|string¦null|false|none||科室描述|
|»» primaryDepartmentCode|string¦null|false|none||大科室编号|
|»» primaryDepartmentName|string¦null|false|none||大科室名称|
|»» children|[[DepartmentVO](#schemadepartmentvo)]¦null|false|none||子科室|

## GET 通过医院编码和科室编码获取科室

GET /api/hospital/department/hospital/department/code

通过医院编码和科室编码获取科室
根据输入的医院编码（不能为空白字符）和科室编码（同样不能为空白字符）获取特定医院下的特定科室信息，返回的是Department对象。

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|hospitalCode|query|string| yes |none|
|departmentCode|query|string| yes |none|

> Response Examples

> 成功

```json
{
  "id": "",
  "createTime": "",
  "updateTime": "",
  "hospitalCode": "",
  "departmentCode": "",
  "departmentName": "",
  "intro": "",
  "primaryDepartmentCode": "",
  "primaryDepartmentName": ""
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|[Department](#schemadepartment)|

# ScheduleApiController

## GET 通过医院编码和科室编码获取排班列表

GET /api/hospital/schedule/hospital/department/code

通过医院编码和科室编码获取排班列表
根据提供的医院编码（不能为空白字符）和科室编码（不能为空白字符），获取该医院和科室下所有的排班信息，返回Schedule对象列表。

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|hospitalCode|query|string| yes |none|
|departmentCode|query|string| yes |none|

> Response Examples

> 成功

```json
[
  {
    "id": "",
    "createTime": "",
    "updateTime": "",
    "hospitalCode": "",
    "departmentCode": "",
    "doctorTitle": "",
    "doctorName": "",
    "skill": "",
    "workDate": "",
    "workTime": "",
    "reservedNumber": 0,
    "availableNumber": 0,
    "amount": 0,
    "status": 0,
    "hospitalScheduleId": ""
  }
]
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|*anonymous*|[[Schedule](#schemaschedule)]|false|none||none|
|» id|string¦null|false|none||id|
|» createTime|string¦null|false|none||创建时间|
|» updateTime|string¦null|false|none||更新时间|
|» hospitalCode|string¦null|false|none||医院编号|
|» departmentCode|string¦null|false|none||科室编号|
|» doctorTitle|string¦null|false|none||职称|
|» doctorName|string¦null|false|none||医生名称|
|» skill|string¦null|false|none||擅长技能|
|» workDate|string¦null|false|none||排班日期|
|» workTime|string¦null|false|none||排班时间|
|» reservedNumber|integer¦null|false|none||可预约数|
|» availableNumber|integer¦null|false|none||剩余预约数|
|» amount|number¦null|false|none||挂号费|
|» status|integer¦null|false|none||排班状态（-1：停诊 0：停约 1：可约）|
|» hospitalScheduleId|string¦null|false|none||排班编号（医院自己的排班主键）|

## GET 分页查询指定医院和科室的排班信息

GET /api/hospital/schedule/page

分页查询指定医院和科室的排班信息
根据提供的医院编码（不能为空白字符）、科室编码（不能为空白字符），以及可选的当前页码（默认为1，最小值为1）和每页大小（默认为7，最小值为1），返回分页后的排班信息列表，封装在IPage<ScheduleVO>对象中。

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|hospitalCode|query|string| yes |none|
|departmentCode|query|string| yes |none|
|current|query|integer| yes |none|
|size|query|integer| yes |none|

> Response Examples

> 成功

```json
{
  "records": [
    {
      "hospitalCode": "",
      "hospitalName": "",
      "departmentCode": "",
      "departmentName": "",
      "doctorTitle": "",
      "doctorName": "",
      "skill": "",
      "workDate": "",
      "dayOfWeek": "",
      "workTime": "",
      "reservedNumber": 0,
      "availableNumber": 0,
      "amount": 0,
      "status": 0,
      "hospitalScheduleId": ""
    }
  ],
  "total": 0,
  "size": 0,
  "current": 0,
  "orders": [
    {
      "column": "",
      "asc": false
    }
  ],
  "optimizeCountSql": false,
  "searchCount": false,
  "optimizeJoinOfCountSql": false,
  "maxLimit": 0,
  "countId": ""
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

## GET 通过医院编码、科室编码和工作日期获取排班

GET /api/hospital/schedule/hospital/department/date

通过医院编码、科室编码和工作日期获取排班
根据提供的医院编码（不能为空白字符）、科室编码（不能为空白字符）和工作日期（不能为空白字符），获取指定日期内该医院和科室的排班信息，返回Schedule对象列表。

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|hospitalCode|query|string| yes |none|
|departmentCode|query|string| yes |none|
|workDate|query|string| yes |none|

> Response Examples

> 成功

```json
[
  {
    "id": "",
    "createTime": "",
    "updateTime": "",
    "hospitalCode": "",
    "departmentCode": "",
    "doctorTitle": "",
    "doctorName": "",
    "skill": "",
    "workDate": "",
    "workTime": "",
    "reservedNumber": 0,
    "availableNumber": 0,
    "amount": 0,
    "status": 0,
    "hospitalScheduleId": ""
  }
]
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|*anonymous*|[[Schedule](#schemaschedule)]|false|none||none|
|» id|string¦null|false|none||id|
|» createTime|string¦null|false|none||创建时间|
|» updateTime|string¦null|false|none||更新时间|
|» hospitalCode|string¦null|false|none||医院编号|
|» departmentCode|string¦null|false|none||科室编号|
|» doctorTitle|string¦null|false|none||职称|
|» doctorName|string¦null|false|none||医生名称|
|» skill|string¦null|false|none||擅长技能|
|» workDate|string¦null|false|none||排班日期|
|» workTime|string¦null|false|none||排班时间|
|» reservedNumber|integer¦null|false|none||可预约数|
|» availableNumber|integer¦null|false|none||剩余预约数|
|» amount|number¦null|false|none||挂号费|
|» status|integer¦null|false|none||排班状态（-1：停诊 0：停约 1：可约）|
|» hospitalScheduleId|string¦null|false|none||排班编号（医院自己的排班主键）|

## GET 通过ID获取排班详情

GET /api/hospital/schedule/id/{id}

通过ID获取排班详情
根据提供的排班ID（不能为空白字符），获取排班详情信息，返回ScheduleVO对象。

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|id|path|string| yes |none|

> Response Examples

> 成功

```json
{
  "hospitalCode": "",
  "hospitalName": "",
  "departmentCode": "",
  "departmentName": "",
  "doctorTitle": "",
  "doctorName": "",
  "skill": "",
  "workDate": "",
  "dayOfWeek": "",
  "workTime": "",
  "reservedNumber": 0,
  "availableNumber": 0,
  "amount": 0,
  "status": 0,
  "hospitalScheduleId": ""
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|[ScheduleVO](#schemaschedulevo)|

## GET 通过ID获取排班详情（内部调用）

GET /api/hospital/schedule/inner/id/{id}

通过ID获取排班详情（内部调用）
内部使用的接口，根据提供的排班ID（不能为空白字符），获取排班详情信息，返回ScheduleVO对象。

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|id|path|string| yes |none|

> Response Examples

> 成功

```json
{
  "hospitalCode": "",
  "hospitalName": "",
  "departmentCode": "",
  "departmentName": "",
  "doctorTitle": "",
  "doctorName": "",
  "skill": "",
  "workDate": "",
  "dayOfWeek": "",
  "workTime": "",
  "reservedNumber": 0,
  "availableNumber": 0,
  "amount": 0,
  "status": 0,
  "hospitalScheduleId": ""
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|[ScheduleVO](#schemaschedulevo)|

# UserInfoAdminController

## POST 新增用户

POST /admin/user/info/save

新增用户
此接口用于接收并保存一个非空的UserInfo对象作为新的用户数据，成功保存到数据库后返回true。

> Body Parameters

```json
{
  "id": 0,
  "phone": "string",
  "openid": "string",
  "nickName": "string",
  "name": "string",
  "certificatesType": 0,
  "certificatesNo": "string",
  "certificatesUrl": "string",
  "authStatus": 0,
  "authTime": "string",
  "status": 0,
  "createTime": "string",
  "updateTime": "string",
  "deleted": 0
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|body|body|[UserInfo](#schemauserinfo)| no |none|

> Response Examples

> 成功

```json
false
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|boolean|

## DELETE 删除用户

DELETE /admin/user/info/remove/id/{id}

删除用户
根据传入的用户ID（最小值为1）从数据库中删除对应用户，删除成功后返回true。

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|id|path|string| yes |none|

> Response Examples

> 成功

```json
false
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|boolean|

## DELETE 批量删除用户

DELETE /admin/user/info/remove/batch

批量删除用户
接收一个非空的用户ID列表，批量从数据库中删除指定的用户，删除成功后返回true。

> Body Parameters

```json
[
  0
]
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|body|body|array[integer]| no |none|

> Response Examples

> 成功

```json
false
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|boolean|

## PUT 修改用户

PUT /admin/user/info/update

修改用户
接收一个非空的UserInfo对象作为更新用户数据，根据其ID进行更新操作，成功更新到数据库后返回true。

> Body Parameters

```json
{
  "id": 0,
  "phone": "string",
  "openid": "string",
  "nickName": "string",
  "name": "string",
  "certificatesType": 0,
  "certificatesNo": "string",
  "certificatesUrl": "string",
  "authStatus": 0,
  "authTime": "string",
  "status": 0,
  "createTime": "string",
  "updateTime": "string",
  "deleted": 0
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|body|body|[UserInfo](#schemauserinfo)| no |none|

> Response Examples

> 成功

```json
false
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|boolean|

## PUT 设置用户状态

PUT /admin/user/info/status/{id}/{status}

设置用户状态
根据传入的用户ID（最小值为1）和状态（0或1）更新用户的启用禁用状态，成功更新到数据库后返回true。

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|id|path|string| yes |none|
|status|path|string| yes |none|

> Response Examples

> 成功

```json
false
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|boolean|

## GET 通过ID获取用户

GET /admin/user/info/id/{id}

通过ID获取用户
根据传入的用户ID（最小值为1）从数据库中获取对应的用户信息。

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|id|path|string| yes |none|

> Response Examples

> 成功

```json
{
  "id": 0,
  "phone": "",
  "openid": "",
  "nickName": "",
  "name": "",
  "certificatesType": 0,
  "certificatesNo": "",
  "certificatesUrl": "",
  "authStatus": 0,
  "authTime": "",
  "status": 0,
  "createTime": "",
  "updateTime": "",
  "deleted": 0
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|[UserInfo](#schemauserinfo)|

## POST 条件查询用户带分页

POST /admin/user/info/find

条件查询用户带分页
接收一个非空的UserInfoQueryVO对象作为查询条件，并指定当前页码（默认为1，最小值为1）和每页大小（默认为20，最小值为1），返回符合条件的用户信息分页列表。

> Body Parameters

```json
{
  "phone": "string",
  "nickName": "string",
  "createTimeFrom": "string",
  "createTimeTo": "string"
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|current|query|integer| yes |none|
|size|query|integer| yes |none|
|body|body|[UserInfoQueryVO](#schemauserinfoqueryvo)| no |none|

> Response Examples

> 成功

```json
{
  "records": [
    {
      "id": 0,
      "phone": "",
      "openid": "",
      "nickName": "",
      "name": "",
      "certificatesType": 0,
      "certificatesNo": "",
      "certificatesUrl": "",
      "authStatus": 0,
      "authTime": "",
      "status": 0,
      "createTime": "",
      "updateTime": "",
      "deleted": 0
    }
  ],
  "total": 0,
  "size": 0,
  "current": 0,
  "orders": [
    {
      "column": "",
      "asc": false
    }
  ],
  "optimizeCountSql": false,
  "searchCount": false,
  "optimizeJoinOfCountSql": false,
  "maxLimit": 0,
  "countId": ""
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

## POST 用户数据统计

POST /admin/user/info/inner/statistic

用户数据统计
接收一个非空的UserStatisticVO对象作为统计条件，根据其中参数对用户数据进行统计计算，返回统计结果。

> Body Parameters

```json
{
  "phone": 0,
  "wx": 0,
  "total": 0,
  "from": "string",
  "to": "string"
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|body|body|[UserStatisticVO](#schemauserstatisticvo)| no |none|

> Response Examples

> 成功

```json
{
  "phone": 0,
  "wx": 0,
  "total": 0,
  "from": "",
  "to": ""
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|[UserStatisticVO](#schemauserstatisticvo)|

# UserInfoApiController

## POST 登录

POST /api/user/info/login

登录
此接口接收一个非空的LoginParam对象作为登录凭证，其中包括用户名和密码等信息。接口验证登录信息有效后，返回一个包含用户身份标识和可能的访问令牌的LoginResponse对象。

> Body Parameters

```json
{
  "phoneNumber": "string",
  "verificationCode": "string"
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|body|body|[LoginParam](#schemaloginparam)| no |none|

> Response Examples

> 成功

```json
{
  "nickName": "",
  "token": ""
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|[LoginResponse](#schemaloginresponse)|

## GET 获取用户基本信息

GET /api/user/info/auth/basic

获取用户基本信息
通过JWT鉴权传递用户ID，接口返回该用户的简要基本信息，如用户ID、昵称等，封装在UserInfoBasic对象中。

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|x-user-id|header|string| yes |none|

> Response Examples

> 成功

```json
{
  "phone": "",
  "openid": "",
  "nickName": ""
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|[UserInfoBasic](#schemauserinfobasic)|

## GET 获取用户详细信息

GET /api/user/info/auth/detail

获取用户详细信息
通过JWT鉴权传递用户ID，接口返回该用户的详细信息，包括但不限于基本信息、实名认证状态、联系方式等，封装在UserInfoVO对象中。

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|x-user-id|header|string| yes |none|

> Response Examples

> 成功

```json
{
  "id": 0,
  "phone": "",
  "openid": "",
  "nickName": "",
  "name": "",
  "certificatesType": 0,
  "certificatesTypeName": "",
  "certificatesNo": "",
  "certificatesUrl": "",
  "authStatus": 0,
  "authTime": "",
  "status": 0,
  "createTime": ""
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|[UserInfoVO](#schemauserinfovo)|

## POST 保存实名认证信息

POST /api/user/info/auth/realname

保存实名认证信息
通过JWT鉴权传递用户ID，并接收一个非空的RealNameParam对象作为实名认证数据，接口将实名信息保存至用户账户中，成功保存后返回true。

> Body Parameters

```json
{
  "name": "string",
  "certificatesType": 0,
  "certificatesNo": "string",
  "certificatesUrl": "string"
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|x-user-id|header|string| yes |none|
|body|body|[RealNameParam](#schemarealnameparam)| no |none|

> Response Examples

> 成功

```json
false
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|boolean|

## POST 更新手机号

POST /api/user/info/auth/phone

更新手机号
通过JWT鉴权传递用户ID，并接收一个非空的LoginParam对象作为新的手机号信息，接口将更新用户的手机号码，成功更新后返回true。注意，这里的LoginParam应包含新的手机号码。

> Body Parameters

```json
{
  "phoneNumber": "string",
  "verificationCode": "string"
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|x-user-id|header|string| yes |none|
|body|body|[LoginParam](#schemaloginparam)| no |none|

> Response Examples

> 成功

```json
false
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|boolean|

## POST 更新昵称

POST /api/user/info/auth/nickname

更新昵称
通过JWT鉴权传递用户ID，并通过请求参数指定新的昵称，接口将更新用户的昵称信息，成功更新后返回true。昵称不能为空。

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|nickName|query|string| yes |none|
|x-user-id|header|string| yes |none|

> Response Examples

> 成功

```json
false
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|boolean|

# WxOpenApiController

## GET 获取登陆二维码相关参数

GET /api/user/wx/qrcode

获取登陆二维码相关参数
此接口用于获取微信开放平台登录二维码的相关参数，这些参数可用于生成微信扫码登录二维码。接口返回一个WxLoginQrCodeParam对象，其中包含了生成二维码所需的必要参数，如临时授权码、随机字符串和登录状态标识等。客户端利用返回的参数生成二维码，用户扫描后进入下一步登录流程。

> Response Examples

> 成功

```json
{
  "self_redirect": false,
  "redirect_uri": "",
  "id": "",
  "appid": "",
  "scope": "",
  "state": "",
  "style": "",
  "href": ""
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|[WxLoginQrCodeParam](#schemawxloginqrcodeparam)|

## GET 登录回调

GET /api/user/wx/callback

登录回调
当用户在微信内扫描二维码并完成授权后，微信服务器将跳转至本接口，并携带code和state两个参数。接口接收到这两个参数后，通过wxOpenService处理登录回调逻辑，验证授权信息并完成用户登录流程。最终，该接口通常会返回一个重定向地址或者处理完登录后的相应信息。

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|code|query|string| yes |none|
|state|query|string| yes |none|

> Response Examples

> 成功

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|string|

# PatientApiController

## GET 获取当前用户添加的所有就诊人

GET /api/user/patient/auth/list

获取当前用户添加的所有就诊人
此接口用于获取经过JWT鉴权的用户ID所关联的所有就诊人信息。接口成功执行后返回一个包含就诊人信息的列表。

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|x-user-id|header|string| yes |none|

> Response Examples

> 成功

```json
[
  {
    "id": 0,
    "userId": 0,
    "name": "",
    "phone": "",
    "certificatesType": 0,
    "certificatesTypeName": "",
    "certificatesNo": "",
    "gender": 0,
    "marry": 0,
    "birthday": "",
    "insured": 0,
    "cardNo": "",
    "contactsName": "",
    "contactsPhone": "",
    "status": 0
  }
]
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|*anonymous*|[[PatientVO](#schemapatientvo)]|false|none||none|
|» id|integer¦null|false|none||id|
|» userId|integer¦null|false|none||用户id|
|» name|string¦null|false|none||姓名|
|» phone|string¦null|false|none||手机号|
|» certificatesType|integer¦null|false|none||证件类型|
|» certificatesTypeName|string¦null|false|none||证件类型（name）|
|» certificatesNo|string¦null|false|none||证件编号|
|» gender|integer¦null|false|none||性别（0：女，1：男）|
|» marry|integer¦null|false|none||是否结婚（0：否，1：是）|
|» birthday|string¦null|false|none||出生日期|
|» insured|integer¦null|false|none||是否有医保（0：否，1：是）|
|» cardNo|string¦null|false|none||就诊卡号|
|» contactsName|string¦null|false|none||联系人姓名|
|» contactsPhone|string¦null|false|none||联系人手机|
|» status|integer¦null|false|none||状态（0：不可用，1：可用）|

## GET 通过就诊人ID获取就诊人

GET /api/user/patient/auth/detail/{patientId}

通过就诊人ID获取就诊人
通过JWT鉴权的用户ID和指定的就诊人ID获取就诊人的详细信息。成功获取后返回一个包含就诊人完整信息的对象。

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|patientId|path|string| yes |none|
|x-user-id|header|string| yes |none|

> Response Examples

> 成功

```json
{
  "id": 0,
  "userId": 0,
  "name": "",
  "phone": "",
  "certificatesType": 0,
  "certificatesTypeName": "",
  "certificatesNo": "",
  "gender": 0,
  "marry": 0,
  "birthday": "",
  "insured": 0,
  "cardNo": "",
  "contactsName": "",
  "contactsPhone": "",
  "status": 0
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|[PatientVO](#schemapatientvo)|

## DELETE 移除就诊人

DELETE /api/user/patient/auth/remove/{patientId}

移除就诊人
根据JWT鉴权的用户ID和指定的就诊人ID，删除该用户关联的就诊人记录。删除成功后返回true。

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|patientId|path|string| yes |none|
|x-user-id|header|string| yes |none|

> Response Examples

> 成功

```json
false
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|boolean|

## POST 添加就诊人

POST /api/user/patient/auth/add

添加就诊人
接收一个非空的Patient对象作为新增就诊人数据，同时通过JWT鉴权的用户ID确定所属关系，成功保存则返回true。

> Body Parameters

```json
{
  "id": 0,
  "userId": 0,
  "name": "string",
  "phone": "string",
  "certificatesType": 0,
  "certificatesNo": "string",
  "gender": 0,
  "marry": 0,
  "birthday": "string",
  "insured": 0,
  "cardNo": "string",
  "contactsName": "string",
  "contactsPhone": "string",
  "status": 0,
  "createTime": "string",
  "updateTime": "string",
  "deleted": 0
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|x-user-id|header|string| yes |none|
|body|body|[Patient](#schemapatient)| no |none|

> Response Examples

> 成功

```json
false
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|boolean|

## PUT 修改就诊人

PUT /api/user/patient/auth/update

修改就诊人
接收一个非空的Patient对象作为更新就诊人数据，通过JWT鉴权的用户ID确认权限，根据患者ID进行更新操作，成功更新则返回true。

> Body Parameters

```json
{
  "id": 0,
  "userId": 0,
  "name": "string",
  "phone": "string",
  "certificatesType": 0,
  "certificatesNo": "string",
  "gender": 0,
  "marry": 0,
  "birthday": "string",
  "insured": 0,
  "cardNo": "string",
  "contactsName": "string",
  "contactsPhone": "string",
  "status": 0,
  "createTime": "string",
  "updateTime": "string",
  "deleted": 0
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|x-user-id|header|string| yes |none|
|body|body|[Patient](#schemapatient)| no |none|

> Response Examples

> 成功

```json
false
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|boolean|

## GET 通过就诊人ID获取就诊人详情

GET /api/user/patient/inner/detail/{patientId}

通过就诊人ID获取就诊人详情
此接口为内部使用，无需用户鉴权，直接通过就诊人ID获取就诊人的原始详细信息，成功获取后返回一个包含就诊人完整信息的对象。

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|patientId|path|string| yes |none|

> Response Examples

> 成功

```json
{
  "id": 0,
  "userId": 0,
  "name": "",
  "phone": "",
  "certificatesType": 0,
  "certificatesNo": "",
  "gender": 0,
  "marry": 0,
  "birthday": "",
  "insured": 0,
  "cardNo": "",
  "contactsName": "",
  "contactsPhone": "",
  "status": 0,
  "createTime": "",
  "updateTime": "",
  "deleted": 0
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|[Patient](#schemapatient)|

# SmsController

## POST 发送验证码

POST /api/sms/send/code/{phoneNumber}

发送验证码
此接口用于向指定手机号发送验证码。通过HTTP POST请求，客户端需在路径参数中提供有效的手机号码，手机号格式必须符合中国大陆手机号码的正则表达式要求，即以1开头，第二位为3-9之间的数字，随后跟随9位数字。若手机号格式不正确，服务端将返回错误提示。调用该接口后，短信服务将会发送一条验证码短信至指定手机，接口返回一个布尔值，表示验证码发送操作是否成功。

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|phoneNumber|path|string| yes |none|

> Response Examples

> 成功

```json
false
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|boolean|

# FileAdminController

## GET 获取OSS管理路径

GET /admin/oss/file/path

获取OSS管理路径
此接口用于获取对象存储服务（OSS）的管理路径，不涉及用户鉴权，直接返回存储系统的根或指定路径字符串。

> Response Examples

> 成功

```json
null
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|string|

## POST 上传文件

POST /admin/oss/file/upload

上传文件
用户通过HTTP POST方式上传单个文件，请求体需符合Multipart/form-data格式，并指定名为'file'的非空文件部分。接口成功执行后返回上传文件在系统内的唯一标识或者存储路径。

> Body Parameters

```yaml
file: string

```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|body|body|object| no |none|
|» file|body|string(binary)| yes |none|

> Response Examples

> 成功

```json
null
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|string|

## POST 批量上传文件

POST /admin/oss/file/upload/batch

批量上传文件
用户通过HTTP POST方式上传多个文件，请求体需符合Multipart/form-data格式，并指定名为'files'的非空文件数组。接口成功执行后返回一个包含每个已上传文件的唯一标识或存储路径的列表。

> Body Parameters

```yaml
files: string

```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|body|body|object| no |none|
|» files|body|string(binary)| yes |none|

> Response Examples

> 成功

```json
[
  ""
]
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

## GET 下载文件

GET /admin/oss/file/download/{yyyy}/{MM}/{dd}/{uuidFileName}

下载文件
根据指定的年（yyyy）、月（MM）、日（dd）及UUID格式的文件名下载单个文件。接口根据给定的路径组合获取文件资源并返回一个包含文件内容的HTTP响应实体，可用于客户端直接下载。

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|yyyy|path|string| yes |none|
|MM|path|string| yes |none|
|dd|path|string| yes |none|
|uuidFileName|path|string| yes |none|

> Response Examples

> 成功

```json
{}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

## POST 批量下载文件

POST /admin/oss/file/download/batch

批量下载文件
用户通过请求体发送一个非空的文件URL列表，接口将这些文件打包成一个单一的下载资源（例如ZIP文件），并返回一个包含打包后文件内容的HTTP响应实体，以支持客户端一次性下载多个文件。

> Body Parameters

```json
[
  "string"
]
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|body|body|array[string]| no |none|

> Response Examples

> 成功

```json
{}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

## DELETE 删除文件

DELETE /admin/oss/file/delete/{yyyy}/{MM}/{dd}/{uuidFileName}

删除文件
根据指定的年（yyyy）、月（MM）、日（dd）及UUID格式的文件名删除单个文件。接口接收到合法的文件路径组合后，执行删除操作并返回一个布尔值，表示删除是否成功。

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|yyyy|path|string| yes |none|
|MM|path|string| yes |none|
|dd|path|string| yes |none|
|uuidFileName|path|string| yes |none|

> Response Examples

> 成功

```json
false
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|boolean|

## DELETE 批量删除文件

DELETE /admin/oss/file/delete/batch

批量删除文件
用户通过请求体发送一个非空的文件URL列表，接口依次删除列表中的所有文件，并返回一个布尔值，表示整个批量删除操作是否全部成功。

> Body Parameters

```json
[
  "string"
]
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|body|body|array[string]| no |none|

> Response Examples

> 成功

```json
false
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|boolean|

## GET 获取文件列表

GET /admin/oss/file/list

获取文件列表
接口返回当前存储空间下符合特定条件的部分文件列表，支持分页加载。用户可以设置可选参数'continuationToken'进行分页查询，以及通过'maxKeys'参数控制每次请求返回的最大文件数量。接口最终返回一个包含文件列表信息的对象。

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|continuationToken|query|string| no |none|
|maxKeys|query|integer| no |none|

> Response Examples

> 成功

```json
{
  "objectSummaries": [
    {
      "name": "",
      "size": 0,
      "lastModified": "",
      "type": ""
    }
  ],
  "keyCount": 0,
  "nextContinuationToken": ""
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|[ListObjectsResultVO](#schemalistobjectsresultvo)|

# FileApiController

## POST 上传文件

POST /api/oss/file/upload

上传文件
此接口接收一个HTTP POST请求，请求体类型为Multipart/form-data，其中包含一个名为'file'的非空文件部分。接口负责处理上传过程并将文件保存至服务器，成功上传后返回文件在系统内部的存储路径或其他唯一标识符。

> Body Parameters

```yaml
file: string

```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|body|body|object| no |none|
|» file|body|string(binary)| yes |none|

> Response Examples

> 成功

```json
null
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|string|

## GET 预览文件

GET /api/oss/file/preview/{yyyy}/{MM}/{dd}/{uuidFileName}

预览文件
该接口用于在线预览存储在服务器上的文件。用户通过URL路径参数指定待预览文件的存储位置，包括年（yyyy）、月（MM）、日（dd）及UUID格式的文件名。接口从指定路径读取文件内容并将其转换为适合预览的格式（如图片或文档预览快照）。接口返回一个HTTP响应实体，其主体包含了预览文件的二进制数据，以便于前端展示预览效果。

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|yyyy|path|string| yes |none|
|MM|path|string| yes |none|
|dd|path|string| yes |none|
|uuidFileName|path|string| yes |none|

> Response Examples

> 成功

```json
[
  0
]
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

# OrderInfoAdminController

## POST 新增订单

POST /admin/order/info/save

新增订单
接收并保存一个完整的OrderInfo对象作为新的订单信息，成功保存则返回true。

> Body Parameters

```json
{
  "id": 0,
  "userId": 0,
  "hospitalCode": "string",
  "hospitalName": "string",
  "departmentCode": "string",
  "departmentName": "string",
  "doctorName": "string",
  "doctorTitle": "string",
  "scheduleId": "string",
  "reserveDate": "string",
  "patientId": 0,
  "patientName": "string",
  "patientPhone": "string",
  "amount": 0,
  "orderStatus": 0,
  "remark": "string",
  "createTime": "string",
  "updateTime": "string",
  "isDeleted": 0
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|body|body|[OrderInfo](#schemaorderinfo)| no |none|

> Response Examples

> 成功

```json
false
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|boolean|

## DELETE 删除订单

DELETE /admin/order/info/remove/id/{id}

删除订单
根据传入的订单ID（最小值为1）删除指定的订单，删除成功则返回true。

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|id|path|string| yes |none|

> Response Examples

> 成功

```json
false
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|boolean|

## DELETE 批量删除订单

DELETE /admin/order/info/remove/batch

批量删除订单
接收一个非空的Long类型的ID列表，批量删除指定的订单，删除成功则返回true。

> Body Parameters

```json
[
  0
]
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|body|body|array[integer]| no |none|

> Response Examples

> 成功

```json
false
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|boolean|

## PUT 修改订单

PUT /admin/order/info/update

修改订单
接收并更新一个完整的OrderInfo对象，根据其ID进行订单信息更新，成功更新则返回true。

> Body Parameters

```json
{
  "id": 0,
  "userId": 0,
  "hospitalCode": "string",
  "hospitalName": "string",
  "departmentCode": "string",
  "departmentName": "string",
  "doctorName": "string",
  "doctorTitle": "string",
  "scheduleId": "string",
  "reserveDate": "string",
  "patientId": 0,
  "patientName": "string",
  "patientPhone": "string",
  "amount": 0,
  "orderStatus": 0,
  "remark": "string",
  "createTime": "string",
  "updateTime": "string",
  "isDeleted": 0
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|body|body|[OrderInfo](#schemaorderinfo)| no |none|

> Response Examples

> 成功

```json
false
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|boolean|

## PUT 设置订单状态

PUT /admin/order/info/status/{id}/{status}

设置订单状态
根据传入的订单ID（最小值为1）和状态值（-1至4之间的整数），更新订单状态，成功更新则返回true。

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|id|path|string| yes |none|
|status|path|string| yes |none|

> Response Examples

> 成功

```json
false
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|boolean|

## GET 通过ID获取订单

GET /admin/order/info/id/{id}

通过ID获取订单
根据传入的订单ID（最小值为1），获取指定订单的详细信息。

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|id|path|string| yes |none|

> Response Examples

> 成功

```json
{
  "id": 0,
  "userId": 0,
  "hospitalCode": "",
  "hospitalName": "",
  "departmentCode": "",
  "departmentName": "",
  "doctorName": "",
  "doctorTitle": "",
  "scheduleId": "",
  "reserveDate": "",
  "patientId": 0,
  "patientName": "",
  "patientPhone": "",
  "amount": 0,
  "orderStatus": 0,
  "remark": "",
  "createTime": "",
  "updateTime": "",
  "isDeleted": 0
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|[OrderInfo](#schemaorderinfo)|

## POST 条件查询订单带分页

POST /admin/order/info/find

条件查询订单带分页
接收一个非空的OrderInfoQueryVO对象作为查询条件，以及可选的当前页码（默认为1，最小值为1）和每页大小（默认为20，最小值为1），返回符合查询条件的订单分页列表。

> Body Parameters

```json
{
  "hospitalCodeOrName": "string",
  "departmentCodeOrName": "string",
  "doctorName": "string",
  "patientIdOrName": "string",
  "orderStatus": 0,
  "createTimeFrom": "string",
  "createTimeTo": "string"
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|current|query|integer| yes |none|
|size|query|integer| yes |none|
|body|body|[OrderInfoQueryVO](#schemaorderinfoqueryvo)| no |none|

> Response Examples

> 成功

```json
{
  "records": [
    {
      "id": 0,
      "userId": 0,
      "hospitalCode": "",
      "hospitalName": "",
      "departmentCode": "",
      "departmentName": "",
      "doctorName": "",
      "doctorTitle": "",
      "scheduleId": "",
      "reserveDate": "",
      "patientId": 0,
      "patientName": "",
      "patientPhone": "",
      "amount": 0,
      "orderStatus": 0,
      "remark": "",
      "createTime": "",
      "updateTime": "",
      "isDeleted": 0
    }
  ],
  "total": 0,
  "size": 0,
  "current": 0,
  "orders": [
    {
      "column": "",
      "asc": false
    }
  ],
  "optimizeCountSql": false,
  "searchCount": false,
  "optimizeJoinOfCountSql": false,
  "maxLimit": 0,
  "countId": ""
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

## POST 订单数据统计

POST /admin/order/info/inner/statistic

订单数据统计
接收一个非空的OrderStatisticVO对象，根据其中的参数进行订单数据统计计算，返回统计结果。

> Body Parameters

```json
{
  "closed": 0,
  "closedAmount": 0,
  "unpaid": 0,
  "unpaidAmount": 0,
  "paid": 0,
  "paidAmount": 0,
  "refunding": 0,
  "refundingAmount": 0,
  "refunded": 0,
  "refundedAmount": 0,
  "completed": 0,
  "completedAmount": 0,
  "total": 0,
  "totalAmount": 0,
  "from": "string",
  "to": "string"
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|body|body|[OrderStatisticVO](#schemaorderstatisticvo)| no |none|

> Response Examples

> 成功

```json
{
  "closed": 0,
  "closedAmount": 0,
  "unpaid": 0,
  "unpaidAmount": 0,
  "paid": 0,
  "paidAmount": 0,
  "refunding": 0,
  "refundingAmount": 0,
  "refunded": 0,
  "refundedAmount": 0,
  "completed": 0,
  "completedAmount": 0,
  "total": 0,
  "totalAmount": 0,
  "from": "",
  "to": ""
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|[OrderStatisticVO](#schemaorderstatisticvo)|

# OrderInfoApiController

## POST 下单（生成订单）

POST /api/order/info/auth/submit

下单（生成订单）
用户通过JWT鉴权传递用户ID，并在请求体中提交必填的SubmitOrderParam参数，完成下单流程并生成新订单，成功后返回新生成的订单ID。

> Body Parameters

```json
{
  "scheduleId": "string",
  "patientId": 0
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|x-user-id|header|string| yes |none|
|body|body|[SubmitOrderParam](#schemasubmitorderparam)| no |none|

> Response Examples

> 成功

```json
0
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|integer|

## GET 通过ID获取订单

GET /api/order/info/auth/detail/{orderId}

通过ID获取订单
用户通过JWT鉴权传递用户ID，并在路径参数中指定订单ID，以获取该用户指定的订单详情信息。

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|orderId|path|string| yes |none|
|x-user-id|header|string| yes |none|

> Response Examples

> 成功

```json
{
  "id": 0,
  "userId": 0,
  "hospitalCode": "",
  "hospitalName": "",
  "departmentCode": "",
  "departmentName": "",
  "doctorName": "",
  "doctorTitle": "",
  "scheduleId": "",
  "reserveDate": "",
  "patientId": 0,
  "patientName": "",
  "patientPhone": "",
  "amount": 0,
  "orderStatus": 0,
  "remark": "",
  "createTime": "",
  "updateTime": "",
  "isDeleted": 0
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|[OrderInfo](#schemaorderinfo)|

## POST 条件查询订单带分页

POST /api/order/info/auth/list

条件查询订单带分页
用户通过JWT鉴权传递用户ID，在请求体中提交非空的OrderInfoQueryParam对象作为查询条件，并指定当前页码（默认为1）和每页大小（默认为10），返回用户符合条件的订单列表分页结果。

> Body Parameters

```json
{
  "patientId": "string",
  "orderStatus": 0,
  "createTimeFrom": "string",
  "createTimeTo": "string"
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|current|query|integer| yes |none|
|size|query|integer| yes |none|
|x-user-id|header|string| yes |none|
|body|body|[OrderInfoQueryParam](#schemaorderinfoqueryparam)| no |none|

> Response Examples

> 成功

```json
{
  "records": [
    {
      "id": 0,
      "userId": 0,
      "hospitalCode": "",
      "hospitalName": "",
      "departmentCode": "",
      "departmentName": "",
      "doctorName": "",
      "doctorTitle": "",
      "scheduleId": "",
      "reserveDate": "",
      "patientId": 0,
      "patientName": "",
      "patientPhone": "",
      "amount": 0,
      "orderStatus": 0,
      "remark": "",
      "createTime": "",
      "updateTime": "",
      "isDeleted": 0
    }
  ],
  "total": 0,
  "size": 0,
  "current": 0,
  "orders": [
    {
      "column": "",
      "asc": false
    }
  ],
  "optimizeCountSql": false,
  "searchCount": false,
  "optimizeJoinOfCountSql": false,
  "maxLimit": 0,
  "countId": ""
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

## PUT 取消订单

PUT /api/order/info/auth/cancel/{orderId}

取消订单
用户通过JWT鉴权传递用户ID，并在路径参数中指定要取消的订单ID，发起取消订单请求，成功取消后返回true。

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|orderId|path|string| yes |none|
|x-user-id|header|string| yes |none|

> Response Examples

> 成功

```json
false
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|boolean|

# AlipayApiController

## GET 获取下单支付页面

GET /api/order/alipay/auth/pay/{orderId}

获取下单支付页面
获取指定订单ID的支付页面URL，需要提供用户ID（通过请求头JwtHelper.X_USER_ID获取）。

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|orderId|path|string| yes |none|
|x-user-id|header|string| yes |none|

> Response Examples

> 成功

```json
null
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|string|

## GET 交易查询

GET /api/order/alipay/auth/query/{orderId}

交易查询
根据用户ID（通过请求头JwtHelper.X_USER_ID获取）和订单ID查询交易状态，返回交易状态码。

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|orderId|path|string| yes |none|
|x-user-id|header|string| yes |none|

> Response Examples

> 成功

```json
0
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|integer|

## PUT 交易关闭

PUT /api/order/alipay/auth/close/{orderId}

交易关闭
根据用户ID（通过请求头JwtHelper.X_USER_ID获取）和订单ID关闭交易，返回操作结果状态码。

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|orderId|path|string| yes |none|
|x-user-id|header|string| yes |none|

> Response Examples

> 成功

```json
0
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|integer|

## PUT 交易退款

PUT /api/order/alipay/auth/refund/{orderId}

交易退款
根据用户ID（通过请求头JwtHelper.X_USER_ID获取）和订单ID发起退款操作，返回退款操作结果状态码。

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|orderId|path|string| yes |none|
|x-user-id|header|string| yes |none|

> Response Examples

> 成功

```json
0
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|integer|

## GET 交易退款查询

GET /api/order/alipay/auth/refund/query/{orderId}

交易退款查询
根据用户ID（通过请求头JwtHelper.X_USER_ID获取）和订单ID查询退款交易状态，返回退款交易状态码。

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|orderId|path|string| yes |none|
|x-user-id|header|string| yes |none|

> Response Examples

> 成功

```json
0
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|integer|

# TaskAdminController

## POST 发送就诊通知

POST /admin/task/manual/visit/notification

发送就诊通知
此接口用于手动触发发送就诊通知的定时任务。一旦调用此接口，服务端将立即执行发送就诊通知的任务逻辑，向待就诊患者发送相应的就诊提醒消息。

> Response Examples

> 200 Response

```json
{}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

## POST 更新订单状态

POST /admin/task/manual/update/order/status

更新订单状态
此接口用于手动触发更新订单状态的定时任务。当调用此接口时，服务端会立即执行该任务逻辑，根据预定的业务规则检查并更新系统中所有订单的状态，例如自动将即将到期的订单状态变更为待确认或其他状态。

> Response Examples

> 200 Response

```json
{}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

# StatisticsAdminController

## POST 医院统计

POST /admin/statistics/analysis/hospital

医院统计
此接口用于对医院的相关数据进行统计分析。客户端需通过HTTP POST请求发送一个非空的HospitalStatisticVO对象，其中包含统计所需的筛选条件或指标定义。服务端接收到请求后，根据提供的参数执行相应的统计计算，然后返回一个包含医院统计数据的HospitalStatisticVO对象。

> Body Parameters

```json
{
  "count": 0,
  "from": "string",
  "to": "string"
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|body|body|[HospitalStatisticVO](#schemahospitalstatisticvo)| no |none|

> Response Examples

> 成功

```json
{
  "count": 0,
  "from": "",
  "to": ""
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|[HospitalStatisticVO](#schemahospitalstatisticvo)|

## POST 用户统计

POST /admin/statistics/analysis/user

用户统计
此接口用于对用户数据进行统计分析。客户端通过HTTP POST方法提交一个非空的UserStatisticVO对象，其中包含了用户统计所需的具体参数。服务器端根据接收到的参数执行用户行为、活跃度或其他相关维度的统计计算，并返回一个包含用户统计数据的UserStatisticVO对象。

> Body Parameters

```json
{
  "phone": 0,
  "wx": 0,
  "total": 0,
  "from": "string",
  "to": "string"
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|body|body|[UserStatisticVO](#schemauserstatisticvo)| no |none|

> Response Examples

> 成功

```json
{
  "phone": 0,
  "wx": 0,
  "total": 0,
  "from": "",
  "to": ""
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|[UserStatisticVO](#schemauserstatisticvo)|

## POST 订单统计

POST /admin/statistics/analysis/order

订单统计
此接口提供了对订单数据进行统计的功能。客户端使用HTTP POST方法发送一个非空的OrderStatisticVO对象作为请求体，该对象中包含了订单统计的各项参数。服务端接收到请求后，根据请求体中的参数进行订单数量、金额、类别等多维度的统计分析，并返回一个包含订单统计数据的OrderStatisticVO对象。

> Body Parameters

```json
{
  "closed": 0,
  "closedAmount": 0,
  "unpaid": 0,
  "unpaidAmount": 0,
  "paid": 0,
  "paidAmount": 0,
  "refunding": 0,
  "refundingAmount": 0,
  "refunded": 0,
  "refundedAmount": 0,
  "completed": 0,
  "completedAmount": 0,
  "total": 0,
  "totalAmount": 0,
  "from": "string",
  "to": "string"
}
```

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|body|body|[OrderStatisticVO](#schemaorderstatisticvo)| no |none|

> Response Examples

> 成功

```json
{
  "closed": 0,
  "closedAmount": 0,
  "unpaid": 0,
  "unpaidAmount": 0,
  "paid": 0,
  "paidAmount": 0,
  "refunding": 0,
  "refundingAmount": 0,
  "refunded": 0,
  "refundedAmount": 0,
  "completed": 0,
  "completedAmount": 0,
  "total": 0,
  "totalAmount": 0,
  "from": "",
  "to": ""
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|[OrderStatisticVO](#schemaorderstatisticvo)|

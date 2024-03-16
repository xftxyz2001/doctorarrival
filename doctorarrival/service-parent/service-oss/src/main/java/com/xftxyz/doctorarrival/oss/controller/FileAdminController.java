package com.xftxyz.doctorarrival.oss.controller;

import com.xftxyz.doctorarrival.oss.service.FileService;
import com.xftxyz.doctorarrival.vo.oss.ListObjectsResultVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "文件管理")
@RequestMapping("/admin/oss/file")
public class FileAdminController {

    private final FileService fileService;

    @Operation(summary = "获取OSS管理路径",
               description = "此接口用于获取对象存储服务（OSS）的管理路径，不涉及用户鉴权，直接返回存储系统的根或指定路径字符串。")
    @GetMapping("/path")
    public String path() {
        return fileService.getAdminPath();
    }

    @Operation(summary = "上传文件",
               description = "用户通过HTTP POST方式上传单个文件，请求体需符合Multipart/form-data格式，并指定名为'file'的非空文件部分。接口成功执行后返回上传文件在系统内的唯一标识或者存储路径。")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String upload(@RequestPart("file") @NotNull MultipartFile file) {
        return fileService.upload(file);
    }

    @Operation(summary = "批量上传文件",
               description = "用户通过HTTP POST方式上传多个文件，请求体需符合Multipart/form-data格式，并指定名为'files'的非空文件数组。接口成功执行后返回一个包含每个已上传文件的唯一标识或存储路径的列表。")
    @PostMapping(value = "/upload/batch", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<String> uploadBatch(@RequestPart("files") @NotEmpty MultipartFile[] files) {
        return fileService.uploadBatch(files);
    }

    @Operation(summary = "下载文件",
               description = "根据指定的年（yyyy）、月（MM）、日（dd）及UUID格式的文件名下载单个文件。接口根据给定的路径组合获取文件资源并返回一个包含文件内容的HTTP响应实体，可用于客户端直接下载。")
    @GetMapping(value = "/download/{yyyy}/{MM}/{dd}/{uuidFileName}")
    public ResponseEntity<Resource> download(@PathVariable("yyyy") @NotBlank String yyyy,
                                             @PathVariable("MM") @NotBlank String MM,
                                             @PathVariable("dd") @NotBlank String dd,
                                             @PathVariable("uuidFileName") @NotBlank String uuidFileName) {
        String fileUrl = yyyy + "/" + MM + "/" + dd + "/" + uuidFileName;
        return fileService.download(fileUrl);
    }

    @Operation(summary = "批量下载文件",
               description = "用户通过请求体发送一个非空的文件URL列表，接口将这些文件打包成一个单一的下载资源（例如ZIP文件），并返回一个包含打包后文件内容的HTTP响应实体，以支持客户端一次性下载多个文件。")
    @PostMapping(value = "/download/batch")
    public ResponseEntity<Resource> downloadBatch(@RequestBody @NotEmpty List<String> fileUrls) {
        return fileService.downloadBatch(fileUrls);
    }

    @Operation(summary = "删除文件",
               description = "根据指定的年（yyyy）、月（MM）、日（dd）及UUID格式的文件名删除单个文件。接口接收到合法的文件路径组合后，执行删除操作并返回一个布尔值，表示删除是否成功。")
    @DeleteMapping(value = "/delete/{yyyy}/{MM}/{dd}/{uuidFileName}")
    public Boolean delete(@PathVariable("yyyy") @NotBlank String yyyy,
                          @PathVariable("MM") @NotBlank String MM,
                          @PathVariable("dd") @NotBlank String dd,
                          @PathVariable("uuidFileName") @NotBlank String uuidFileName) {
        String fileUrl = yyyy + "/" + MM + "/" + dd + "/" + uuidFileName;
        return fileService.delete(fileUrl);
    }

    @Operation(summary = "批量删除文件",
               description = "用户通过请求体发送一个非空的文件URL列表，接口依次删除列表中的所有文件，并返回一个布尔值，表示整个批量删除操作是否全部成功。")
    @DeleteMapping(value = "/delete/batch")
    public Boolean deleteBatch(@RequestBody @NotEmpty List<String> fileUrls) {
        return fileService.deleteBatch(fileUrls);
    }

    @Operation(summary = "获取文件列表",
               description = "接口返回当前存储空间下符合特定条件的部分文件列表，支持分页加载。用户可以设置可选参数'continuationToken'进行分页查询，以及通过'maxKeys'参数控制每次请求返回的最大文件数量。接口最终返回一个包含文件列表信息的对象。")
    @GetMapping("/list")
    public ListObjectsResultVO list(
            @RequestParam(value = "continuationToken", required = false) String continuationToken,
            @RequestParam(value = "maxKeys", required = false, defaultValue = "20") Integer maxKeys) {
        return fileService.list(continuationToken, maxKeys);
    }

}

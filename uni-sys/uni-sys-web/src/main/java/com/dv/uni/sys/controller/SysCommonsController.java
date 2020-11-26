package com.dv.uni.sys.controller;

import com.dv.uni.commons.annotations.IgnoreAuth;
import com.dv.uni.commons.entity.Result;
import com.dv.uni.commons.utils.SecurityUtils;
import com.dv.uni.commons.utils.StringUtils;
import com.dv.uni.commons.utils.ali.oss.OssUtil;
import com.dv.uni.commons.utils.ali.sms.SmsUtils;
import com.dv.uni.commons.utils.ali.vod.VodUtil;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author 张盼
 * @Email zhangpan_soft@163.com
 * @Blog zhangpan_soft
 * @Date 2020/10/26 0026
 */
@RestController
@RequestMapping("sys/commons")
@Api(tags = "工具")
@ApiSupport(author = "zhangpan_soft")
public class SysCommonsController {

    @GetMapping("send/sms/{phone}/{templateCode}/{isLogin}")
    @ApiOperation("发送手机验证码")
    @IgnoreAuth
    public Result sendSms(@ApiParam("手机号") @PathVariable String phone, @ApiParam("模板") @PathVariable String templateCode, @ApiParam("是否是登录") @PathVariable int isLogin) {
        String code = StringUtils.phoneCode(6);
        SmsUtils.send(code, phone, templateCode);
        if (isLogin == 1) {
            SecurityUtils.setCache("phoneCode:" + phone, code, 5 * 60);
        }
        return Result.ok();
    }

    @PostMapping("ali/oss/upload")
    @ApiOperation("上传")
    public Result upload(@RequestParam("file") MultipartFile multipartFile){
        return Result.ok(OssUtil.upload(multipartFile));
    }

    @ApiOperation("批量上传")
    @PostMapping("ali/oss/uploads")
    public Result uploads(@RequestParam("files")MultipartFile[] multipartFiles){
        List<String> list = new ArrayList<>(multipartFiles.length);
        for (MultipartFile multipartFile : multipartFiles) {
            list.add(OssUtil.upload(multipartFile));
        }
        return Result.ok(list);
    }

    @ApiOperation("获取视频播放地址")
    @GetMapping("ali/vod/getPlayInfo/{id}")
    public Result getPlayInfo(@PathVariable String id) {
        return Result.ok(VodUtil.getPlayInfo(id));
    }

    @ApiOperation("获取视频播放凭证")
    @GetMapping("ali/vod/getVideoPlayAuth/{id}")
    public Result getVideoPlayAuth(@PathVariable String id){
        return Result.ok(VodUtil.getVideoPlayAuth(id));
    }

    @ApiOperation("获取视频上传凭证")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title",value = "视频标题",required = true),
            @ApiImplicitParam(name = "filename",value = "视频名称,需要带扩展名",required = true)
    })
    @PostMapping("ali/vod/createUploadVideo")
    public Result createUploadVideo(@RequestBody Map map){
        return Result.ok(VodUtil.createUploadVideo((String) map.get("title"),(String) map.get("filename")));
    }

    @ApiOperation("刷新上传凭证")
    @PutMapping(value = "ali/vod/refreshUploadVideo/{id}",consumes = "application/json")
    public Result refreshUploadVideo(@PathVariable String id){
        return Result.ok(VodUtil.refreshUploadVideo(id));
    }
}

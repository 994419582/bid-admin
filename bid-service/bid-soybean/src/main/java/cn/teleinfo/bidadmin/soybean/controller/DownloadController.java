package cn.teleinfo.bidadmin.soybean.controller;

import cn.hutool.poi.excel.WorkbookUtil;
import cn.teleinfo.bidadmin.soybean.entity.Group;
import cn.teleinfo.bidadmin.soybean.entity.User;
import cn.teleinfo.bidadmin.soybean.service.IClocklnService;
import cn.teleinfo.bidadmin.soybean.service.IGroupService;
import cn.teleinfo.bidadmin.soybean.service.IUserGroupService;
import cn.teleinfo.bidadmin.soybean.vo.ClocklnVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Workbook;
import org.springblade.core.boot.ctrl.BladeController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

// @RestController
@Controller
@AllArgsConstructor
@RequestMapping("/download")
@Api(value = "文件下载", tags = "文件下载")
public class DownloadController extends BladeController {

    private IGroupService groupService;

    private IUserGroupService userGroupService;

    private IClocklnService clocklnService;

    /**
     * 附件
     */
    @GetMapping(value = "/annex.xlsx", produces = "application/msexcel")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "统计数据", notes = "统计数据")
    public void annex(@ApiParam(value = "群组表soybean_group的id") @RequestParam(name = "groupid") Integer groupid,
                      @RequestParam(required = false, name = "from") Date from,
                      @RequestParam(required = false, name = "to") Date to,
                      HttpServletResponse response) {
        Workbook workbook = WorkbookUtil.createBook(true);
        try {
            /**
             * 生成Excel
             *  1. 查取群组下所有人员
             *  2. 根据所查数据分组
             */
            Group group = groupService.getById(groupid);
            if (group == null) {
                return;
            }
            // 组内用户
            List<User> users = userGroupService.findUserByGroupId(groupid);

            // 查出打开记录
            List<ClocklnVO> clocklns = clocklnService.findByUserIdInAndCreatetimeBetween(users.stream().map(User::getId).collect(Collectors.toList()), from, to);

            System.out.println();


            WorkbookUtil.getOrCreateSheet(workbook, "tbc");

            response.setHeader("Content-Disposition", "attachment; filename=" + "annex.xlsx");
            WorkbookUtil.writeBook(workbook, response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

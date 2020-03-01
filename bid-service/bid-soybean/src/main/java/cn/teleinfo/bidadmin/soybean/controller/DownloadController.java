package cn.teleinfo.bidadmin.soybean.controller;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.poi.excel.RowUtil;
import cn.hutool.poi.excel.WorkbookUtil;
import cn.hutool.poi.excel.cell.CellUtil;
import cn.teleinfo.bidadmin.soybean.entity.Group;
import cn.teleinfo.bidadmin.soybean.entity.User;
import cn.teleinfo.bidadmin.soybean.service.IClocklnService;
import cn.teleinfo.bidadmin.soybean.service.IGroupService;
import cn.teleinfo.bidadmin.soybean.service.IUserGroupService;
import cn.teleinfo.bidadmin.soybean.service.IUserService;
import cn.teleinfo.bidadmin.soybean.vo.ClocklnVO;
import cn.teleinfo.bidadmin.soybean.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springblade.core.boot.ctrl.BladeController;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// @RestController
@Controller
@AllArgsConstructor
@RequestMapping("/download")
@Api(value = "文件下载", tags = "文件下载")
public class DownloadController extends BladeController {

    private IUserService userService;

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
                      @RequestParam(required = false, name = "from") @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
                      @RequestParam(required = false, name = "to") @DateTimeFormat(pattern = "yyyy-MM-dd") Date to,
                      @RequestParam(required = false, name = "test", defaultValue = "false") boolean test,
                      HttpServletResponse response) {


        Workbook workbook = WorkbookUtil.createBook(ResourceUtil.getStream("model/annex.xlsx"), false);
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
            from = from == null ? DateUtil.beginOfDay(DateUtil.date()) : DateUtil.beginOfDay(from);
            to = to == null ? DateUtil.endOfDay(DateUtil.date()) : DateUtil.endOfDay(to);
            // 组内用户
            List<UserVO> users = groupService.selectUserByParentId(groupid);

            // 查出打卡记录，隔离记录
            List<ClocklnVO> clocklns = clocklnService.findByUserIdInAndCreatetimeBetween(users.stream().map(UserVO::getId).collect(Collectors.toList()), from, to);
            Map<String, List<ClocklnVO>> clocklnsGroup = clocklns.stream().collect(Collectors.groupingBy(item -> DateUtil.formatDate(Date.from(item.getCreateTime().atZone(ZoneId.systemDefault()).toInstant()))));


            //----------------------------------分析数据--------------------------------
            List<DateTime> rangeDate = DateUtil.rangeToList(from, to, DateField.DAY_OF_YEAR);
            for (DateTime dateItem : rangeDate) {
                List<ClocklnVO> clocklnsToday = clocklnsGroup.get(DateUtil.formatDate(dateItem));
                if (clocklnsToday == null) {
                    continue;
                }

                Sheet sheet = workbook.getSheetAt(0);
                Cell cell = CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 2), 0);
                cell.setCellValue(group.getName());


                List<ClocklnVO> leaveClocklnsToday = clocklnsToday.stream().filter(item -> 2 == item.getLeave()).collect(Collectors.toList());

                // 当日新增来京人员总数(计划当日返京) -----> 已离过京 + 当天打卡地在北京 + 计划返京日期为今天
                long e3B = leaveClocklnsToday.stream().filter(item -> item.getBeijing() == 1 && DateUtil.today().equals(item.getGobacktime())).count();
                CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 2), 1).setCellValue(e3B);

                // 来京人员累计总数(含当日新增) -----> 已离过京 + 当天打卡地在北京
                long e3C = leaveClocklnsToday.stream().filter(item -> item.getBeijing() == 1).count();
                CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 2), 2).setCellValue(e3C);

                // 从湖北省或途径湖北来京员工人数 人数合计(B) -----> 已离过京 + 途径湖北
                long e3D = leaveClocklnsToday.stream().filter(item -> 1 == item.getHubei()).count();
                CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 2), 3).setCellValue(e3D);
                // 从湖北省或途径湖北来京员工人数 其中未返京人数(C) -----> 已离过京 + 途径湖北 && 打卡地不在北京
                long e3E = leaveClocklnsToday.stream().filter(item -> 1 == item.getHubei() && item.getBeijing() == 0).count();
                CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 2), 4).setCellValue(e3E);
                // 从湖北省或途径湖北来京员工人数 其中已返京人数(D) -----> 已离过京 + 途径湖北 && 打卡地在北京
                long e3F = leaveClocklnsToday.stream().filter(item -> 1 == item.getHubei() && item.getBeijing() == 1).count();
                CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 2), 5).setCellValue(e3F);
                // 从湖北省或途径湖北来京员工人数 已返京人员中自行隔离(14天)人数 -----> 已离过京 + 途径湖北 && 打卡地在北京 && 在隔离期
                long e3G = leaveClocklnsToday.stream().filter(item -> 1 == item.getHubei() && item.getBeijing() == 1 && item.getQuarantine() == 1).count();
                CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 2), 6).setCellValue(e3G);
                // 从湖北省或途径湖北来京员工人数 过隔离期人数 -----> 已离过京 + 途径湖北 && 打卡地在北京 && 不在隔离期
                long e3H = leaveClocklnsToday.stream().filter(item -> 1 == item.getHubei() && item.getBeijing() == 1 && item.getQuarantine() != 1).count();
                CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 2), 7).setCellValue(e3H);

                // 从湖北省以外地区来京员工人数 人数合计(E) -----> 已离过京 + 未经湖北
                long e3I = leaveClocklnsToday.stream().filter(item -> 0 == item.getHubei()).count();
                CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 2), 8).setCellValue(e3I);
                // 从湖北省以外地区来京员工人数 其中未返京人数(F) -----> 已离过京 + 未经湖北 && 打卡地不在北京
                long e3J = leaveClocklnsToday.stream().filter(item -> 0 == item.getHubei() && item.getBeijing() == 0).count();
                CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 2), 9).setCellValue(e3J);
                // 从湖北省以外地区来京员工人数 其中已返京人数(G) -----> 已离过京 + 未经湖北 && 打卡地在北京
                long e3K = leaveClocklnsToday.stream().filter(item -> 0 == item.getHubei() && item.getBeijing() == 1).count();
                CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 2), 10).setCellValue(e3K);
                // 从湖北省以外地区来京员工人数 已返京人员中自行隔离(14天)人数 -----> 已离过京 + 未经湖北 && 打卡地在北京 && 在隔离期
                long e3L = leaveClocklnsToday.stream().filter(item -> 0 == item.getHubei() && item.getBeijing() == 1 && item.getQuarantine() == 1).count();
                CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 2), 11).setCellValue(e3L);
                // 从湖北省以外地区来京员工人数 过隔离期人数 -----> 已离过京 + 未经湖北 && 打卡地在北京 && 不在隔离期
                long e3M = leaveClocklnsToday.stream().filter(item -> 0 == item.getHubei() && item.getBeijing() == 1 && item.getQuarantine() != 1).count();
                CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 2), 12).setCellValue(e3M);

                // 获取群主
                User user = userService.getById(group.getCreateUser());
                CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 2), 13).setCellValue(user.getName());
                CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 2), 14).setCellValue(user.getPhone());

                // 写入用户数据
                for (int i = 0; i < users.size(); i++) {
                    User userItem = users.get(i);
                    // 打卡表
                    ClocklnVO clockln = clocklnsToday.stream().filter(item -> userItem.getId().equals(item.getUserId())).findFirst().orElse(null);
                    CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 10 + i), 0).setCellValue(userItem.getName());
                    CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 10 + i), 1).setCellValue(clockln == null ? "否" : "是");
                    CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 10 + i), 2).setCellValue(group.getFullName());
                    CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 10 + i), 3).setCellValue(userItem.getPhone());
                    CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 10 + i), 4).setCellValue(userItem.getHomeAddress() + userItem.getDetailAddress());


                    if (clockln != null) {
                        CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 10 + i), 5).setCellValue(clockln.getLeave() == 1 ? "否" : "是");
                        CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 10 + i), 6).setCellValue(DateUtil.formatDateTime(Date.from(clockln.getCreateTime().atZone(ZoneId.systemDefault()).toInstant())));
                        CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 10 + i), 7).setCellValue(clockln.getAddress());
                        CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 10 + i), 8).setCellValue(clockln.getBeijing() == 0 ? "否" : "是");
                        CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 10 + i), 9).setCellValue(clockln.getQuarantine() == 0 ? "否" : "是");
                        CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 10 + i), 10).setCellValue(clockln.getHospital() == 1 ? "否" : "是");
                        CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 10 + i), 11).setCellValue(clockln.getComfirmed() == 1 ? "否" : "是");
                        CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 10 + i), 12).setCellValue(clockln.getReason());
                        CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 10 + i), 13).setCellValue(clockln.getTemperature());
                        CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 10 + i), 14).setCellValue(clockln.getOtherhealthy());

                        // 无需导出字段，用于测试使用
                        if (test) {
                            CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 10 + i), 15).setCellValue(clockln.getHubei());
                            CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 10 + i), 16).setCellValue(clockln.getBeijing());
                            CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 10 + i), 17).setCellValue(clockln.getId());
                        }
                    }

                }
            }
            response.setHeader("Content-Disposition", "attachment; filename=" + "annex.xlsx");
            WorkbookUtil.writeBook(workbook, response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

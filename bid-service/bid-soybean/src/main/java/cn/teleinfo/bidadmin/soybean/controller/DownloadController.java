package cn.teleinfo.bidadmin.soybean.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.*;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.RowUtil;
import cn.hutool.poi.excel.WorkbookUtil;
import cn.hutool.poi.excel.cell.CellUtil;
import cn.teleinfo.bidadmin.soybean.bo.UserBO;
import cn.teleinfo.bidadmin.soybean.entity.Group;
import cn.teleinfo.bidadmin.soybean.entity.User;
import cn.teleinfo.bidadmin.soybean.entity.UserGroup;
import cn.teleinfo.bidadmin.soybean.service.IClocklnService;
import cn.teleinfo.bidadmin.soybean.service.IGroupService;
import cn.teleinfo.bidadmin.soybean.service.IUserGroupService;
import cn.teleinfo.bidadmin.soybean.service.IUserService;
import cn.teleinfo.bidadmin.soybean.vo.ClocklnVO;
import cn.teleinfo.bidadmin.soybean.vo.UserVO;
import cn.teleinfo.bidadmin.system.entity.Dict;
import cn.teleinfo.bidadmin.system.feign.IDictClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.tool.api.R;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

// @RestController
@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("/download")
@Api(value = "文件下载", tags = "文件下载")
public class DownloadController extends BladeController {

    private IUserService userService;

    private IGroupService groupService;

    private IUserGroupService userGroupService;

    private IClocklnService clocklnService;

    private IDictClient dictClient;

    private Map<String, String> cacheDict = new HashMap<>();

    @GetMapping(value = "/refresh")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "刷新字典", notes = "刷新字典")
    public boolean getValue(@RequestParam(name = "codes") List<String> codes) {
        for (String code : codes) {
            R<List<Dict>> a = dictClient.getList(code);
            if (!a.isSuccess()) {
                return false;
            }
            for (Dict dict : a.getData()) {
                cacheDict.put(code + dict.getDictKey(), dict.getDictValue());
            }
        }
        return true;
    }

    /**
     * 附件
     */
    @GetMapping(value = "/annex.xlsx")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "统计数据", notes = "统计数据")
    public void annex(@ApiParam(value = "群组表soybean_group的id") @RequestParam(name = "groupid") Integer groupid,
                      @RequestParam(required = false, name = "from") @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
                      @RequestParam(required = false, name = "to") @DateTimeFormat(pattern = "yyyy-MM-dd") Date to,
                      @RequestParam(required = false, name = "test", defaultValue = "false") boolean test,
                      HttpServletResponse response) {

        if (test || cacheDict.size() == 0) {
            List<String> dicts = new ArrayList<>();
            dicts.add("nobackreason");
            dicts.add("transport");
            dicts.add("healthy");
            dicts.add("hospital");
            dicts.add("wuhan");
            dicts.add("jobstatus");
            dicts.add("roomPerson");
            dicts.add("roomCompany");
            dicts.add("neighbor");

            if (!getValue(dicts)) {
                log.error("数据字典服务异常");
                return;
            }
        }


        StopWatch sw = new StopWatch();
        Workbook workbook = WorkbookUtil.createBook(ResourceUtil.getStream("model/annex1.xlsx"), false);
        try {
            sw.start("从mysql查询数据");
            Group group = groupService.getById(groupid);
            if (group == null) {
                log.error("群组id不存在");
                return;
            }
            from = from == null ? DateUtil.beginOfDay(DateUtil.date()) : DateUtil.beginOfDay(from);
            to = DateUtil.endOfDay(from);
            // 组内用户
            UserBO ub = groupService.selectUserByParentId(groupid);
            List<UserVO> users = ub.getUsers();
            List<UserGroup> ug = ub.getUserGroups();
            List<Group> groups = ub.getGroups();

            // 查出打卡记录，隔离记录
            List<ClocklnVO> clocklns = clocklnService.findByUserIdInAndCreatetimeBetween(users.stream().map(UserVO::getId).collect(Collectors.toList()), from, to);
            Map<String, List<ClocklnVO>> clocklnsGroup = clocklns.stream().collect(Collectors.groupingBy(item -> DateUtil.formatDate(Date.from(item.getCreateTime().atZone(ZoneId.systemDefault()).toInstant()))));
            sw.stop();

            //----------------------------------分析数据--------------------------------
            List<DateTime> rangeDate = DateUtil.rangeToList(from, to, DateField.DAY_OF_YEAR);
            for (DateTime dateItem : rangeDate) {
                sw.start("写入统计数据: " + DateUtil.formatDate(dateItem));
                List<ClocklnVO> clocklnsToday = clocklnsGroup.getOrDefault(DateUtil.formatDate(dateItem), new ArrayList<>());

                // 设置群组属性
                String destination = group.getAddressName();
                Sheet sheet = this.createSheet(workbook, dateItem, group.getAddressName(), rangeDate.indexOf(dateItem));
                if (StrUtil.isBlank(destination)) {
                    log.warn("群组属性缺失[addressName]为空");
                    CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 0), 0).setCellValue("群组属性缺失[addressName]为空");
                } else {
                    CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 2), 0).setCellValue(group.getName());
                    User user = userService.getById(group.getCreateUser());
                    if (user != null) {
                        CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 2), 13).setCellValue(user.getName());
                        CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 2), 14).setCellValue(user.getPhone());
                    }

                    // 离开过工作地的打卡记录
                    List<ClocklnVO> leaveClocklnsToday = clocklnsToday.stream().filter(item -> 2 == item.getLeave()).collect(Collectors.toList());

                    // 当日新增来京人员总数(计划当日返京) -----> 已离过京 + 当天打卡地在北京 + 计划返京日期为今天
                    long e3B = leaveClocklnsToday.stream().filter(item -> StrUtil.contains(item.getAddress(), destination) && DateUtil.today().equals(item.getGobacktime())).count();
                    CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 2), 1).setCellValue(e3B);

                    // 在京人员累计总数(含当日新增) -----> 当天打卡地在北京
                    long e3C = clocklnsToday.stream().filter(item -> StrUtil.contains(item.getAddress(), destination)).count();
                    CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 2), 2).setCellValue(e3C);

                    // 从湖北省或途径湖北来京员工人数 人数合计(B) -----> 已离过京 + 途径湖北
                    long e3D = leaveClocklnsToday.stream().filter(item -> 1 == item.getHubei()).count();
                    CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 2), 3).setCellValue(e3D);
                    // 从湖北省或途径湖北来京员工人数 其中未返京人数(C) -----> 已离过京 + 途径湖北 && 打卡地不在北京
                    long e3E = leaveClocklnsToday.stream().filter(item -> 1 == item.getHubei() && !StrUtil.contains(item.getAddress(), destination)).count();
                    CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 2), 4).setCellValue(e3E);
                    // 从湖北省或途径湖北来京员工人数 其中已返京人数(D) -----> 已离过京 + 途径湖北 && 打卡地在北京
                    long e3F = leaveClocklnsToday.stream().filter(item -> 1 == item.getHubei() && StrUtil.contains(item.getAddress(), destination)).count();
                    CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 2), 5).setCellValue(e3F);
                    // 从湖北省或途径湖北来京员工人数 已返京人员中自行隔离(14天)人数 -----> 已离过京 + 途径湖北 && 打卡地在北京 && 在隔离期（未确诊 + 计划返京时间差14天）
                    long e3G = leaveClocklnsToday.stream().filter(item -> 1 == item.getHubei() &&
                            StrUtil.contains(item.getAddress(), destination) &&
                            item.getComfirmed() != 2 &&
                            DateUtil.between(DateUtil.parseDate(item.getGobacktime()), DateUtil.endOfDay(DateUtil.date()), DateUnit.DAY) < 14).count();
                    CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 2), 6).setCellValue(e3G);
                    // 从湖北省或途径湖北来京员工人数 过隔离期人数 -----> 已离过京 + 途径湖北 && 打卡地在北京 && 不在隔离期
                    long e3H = leaveClocklnsToday.stream().filter(item -> 1 == item.getHubei() &&
                            StrUtil.contains(item.getAddress(), destination) &&
                            item.getComfirmed() != 2 &&
                            DateUtil.between(DateUtil.parseDate(item.getGobacktime()), DateUtil.endOfDay(DateUtil.date()), DateUnit.DAY) >= 14).count();
                    CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 2), 7).setCellValue(e3H);

                    // 从湖北省以外地区来京员工人数 人数合计(E) -----> 已离过京 + 未经湖北
                    long e3I = leaveClocklnsToday.stream().filter(item -> 0 == item.getHubei()).count();
                    CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 2), 8).setCellValue(e3I);
                    // 从湖北省以外地区来京员工人数 其中未返京人数(F) -----> 已离过京 + 未经湖北 && 打卡地不在北京
                    long e3J = leaveClocklnsToday.stream().filter(item -> 0 == item.getHubei() && !StrUtil.contains(item.getAddress(), destination)).count();
                    CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 2), 9).setCellValue(e3J);
                    // 从湖北省以外地区来京员工人数 其中已返京人数(G) -----> 已离过京 + 未经湖北 && 打卡地在北京
                    long e3K = leaveClocklnsToday.stream().filter(item -> 0 == item.getHubei() && StrUtil.contains(item.getAddress(), destination)).count();
                    CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 2), 10).setCellValue(e3K);
                    // 从湖北省以外地区来京员工人数 已返京人员中自行隔离(14天)人数 -----> 已离过京 + 未经湖北 && 打卡地在北京 && 在隔离期
                    long e3L = leaveClocklnsToday.stream().filter(item -> 0 == item.getHubei() && StrUtil.contains(item.getAddress(), destination) &&
                            item.getComfirmed() != 2 &&
                            DateUtil.between(DateUtil.parseDate(item.getGobacktime()), DateUtil.endOfDay(DateUtil.date()), DateUnit.DAY) < 14).count();
                    CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 2), 11).setCellValue(e3L);
                    // 从湖北省以外地区来京员工人数 过隔离期人数 -----> 已离过京 + 未经湖北 && 打卡地在北京 && 不在隔离期
                    long e3M = leaveClocklnsToday.stream().filter(item -> 0 == item.getHubei() && StrUtil.contains(item.getAddress(), destination) &&
                            item.getComfirmed() != 2 &&
                            DateUtil.between(DateUtil.parseDate(item.getGobacktime()), DateUtil.endOfDay(DateUtil.date()), DateUnit.DAY) >= 14).count();
                    CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 2), 12).setCellValue(e3M);

                }
                sw.stop();

                // 写入用户数据
                sw.start("写入用户数据: " + DateUtil.formatDate(dateItem));
                for (int i = 0; i < users.size(); i++) {
                    User userItem = users.get(i);
                    // 打卡表
                    ClocklnVO clockln = clocklnsToday.stream().filter(item -> userItem.getId().equals(item.getUserId())).findFirst().orElse(null);
                    CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 5 + i), 0).setCellValue(userItem.getName());
                    List<UserGroup> ggs = ug.stream().filter(item -> item.getUserId().equals(userItem.getId())).collect(Collectors.toList());
                    if (CollUtil.isNotEmpty(ggs)) {
                        List<Integer> gids = ggs.stream().map(UserGroup::getGroupId).collect(Collectors.toList());
                        Collection<Group> groupUser = groups.stream().filter(item -> CollUtil.contains(gids, item.getId())).collect(Collectors.toList());
                        CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 5 + i), 2).setCellValue(CollUtil.join(groupUser.stream().map(Group::getName).collect(Collectors.toList()), ","));
                    }
                    CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 5 + i), 4).setCellValue(userItem.getPhone());
                    CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 5 + i), 5).setCellValue(userItem.getHomeAddress() + userItem.getDetailAddress());

                    if (clockln == null) {
                        CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 5 + i), 3).setCellValue("否");
                    } else {
                        CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 5 + i), 3).setCellValue("是");

                        CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 5 + i), 1).setCellValue(DateUtil.formatDateTime(Date.from(clockln.getCreateTime().atZone(ZoneId.systemDefault()).toInstant())));
                        CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 5 + i), 6).setCellValue(clockln.getAddress());
                        CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 5 + i), 7).setCellValue(dictClient.getValue("nobackreason", clockln.getNobackreason()).getData());
                        CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 5 + i), 8).setCellValue(clockln.getGobacktime());

                        CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 5 + i), 9).setCellValue(clockln.getLeave() == 1 ? "否" : "是");
                        if (clockln.getLeave() != 1) {
                            // CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 5 + i), 10).setCellValue("无");
                            CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 5 + i), 11).setCellValue(clockln.getLeavetime());
                            CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 5 + i), 12).setCellValue(cacheDict.get("transport" + clockln.getTransport()));
                            CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 5 + i), 13).setCellValue(clockln.getFlight());
                        }

                        CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 5 + i), 14).setCellValue(clockln.getLeaveCity() == 1 ? "否" : "是");
                        if (clockln.getLeaveCity() != 1) {
                            CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 5 + i), 15).setCellValue(clockln.getLeavetime());
                            CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 5 + i), 16).setCellValue(cacheDict.get("transport" + clockln.getTransport()));
                            CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 5 + i), 17).setCellValue(clockln.getFlight());
                        }
                        CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 5 + i), 18).setCellValue(clockln.getTemperature());
                        CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 5 + i), 19).setCellValue(cacheDict.get("healthy" + clockln.getHealthy()));
                        CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 5 + i), 20).setCellValue(cacheDict.get("hospital" + clockln.getAdmitting()));
                        CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 5 + i), 21).setCellValue(cacheDict.get("wuhan" + clockln.getWuhan()));
                        CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 5 + i), 22).setCellValue(cacheDict.get("jobstatus" + clockln.getJobstatus()));
                        CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 5 + i), 23).setCellValue(cacheDict.get("roomPerson" + clockln.getRoomPerson()));
                        CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 5 + i), 24).setCellValue(cacheDict.get("roomCompany" + clockln.getRoomCompany()));
                        CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 5 + i), 25).setCellValue(cacheDict.get("neighbor" + clockln.getNeighbor()));
                        CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 5 + i), 26).setCellValue(clockln.getRemarks());

                        // 无需导出字段，用于测试使用
                        if (test) {
                            CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, 5 + i), 27).setCellValue(clockln.getId());
                        }
                    }

                }
                sw.stop();
            }
            workbook.removeSheetAt(0);
            response.setHeader("Content-Disposition", "attachment; filename=" + "annex1.xlsx");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-type", "application/octet-stream;charset=UTF-8");
            response.setContentType("application/octet-stream;charset=UTF-8");
            log.info(sw.prettyPrint());
            WorkbookUtil.writeBook(workbook, response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Sheet createSheet(Workbook workbook, DateTime dateItem, String destination, int index) {
        Sheet sheet = workbook.cloneSheet(0);
        workbook.setSheetName(index + 1, DateUtil.formatDate(dateItem));
        return sheet;
//        List<int[]> tmps = new ArrayList<>();
//        if (StrUtil.isNotBlank(destination)) {
//            tmps.add(new int[]{0, 1});
//            tmps.add(new int[]{0, 2});
//            tmps.add(new int[]{0, 3});
//            tmps.add(new int[]{1, 4});
//            tmps.add(new int[]{1, 5});
//            tmps.add(new int[]{1, 6});
//            tmps.add(new int[]{0, 8});
//            tmps.add(new int[]{1, 9});
//            tmps.add(new int[]{1, 10});
//            tmps.add(new int[]{1, 11});
//            tmps.add(new int[]{4, 5});
//            tmps.add(new int[]{4, 7});
//            tmps.add(new int[]{4, 8});
//            tmps.add(new int[]{4, 9});
//            tmps.add(new int[]{4, 10});
//            tmps.add(new int[]{4, 11});
//            tmps.add(new int[]{4, 12});
//            tmps.add(new int[]{4, 13});
//        }
//        for (int[] tmp : tmps) {
//            Cell cell = CellUtil.getOrCreateCell(RowUtil.getOrCreateRow(sheet, tmp[0]), tmp[1]);
//            cell.setCellValue(StrUtil.replace(cell.getStringCellValue(), "工作地", destination));
//        }
    }
}
